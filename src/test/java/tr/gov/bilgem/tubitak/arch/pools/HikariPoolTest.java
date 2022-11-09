package tr.gov.bilgem.tubitak.arch.pools;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import com.zaxxer.hikari.util.UtilityElf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tr.gov.bilgem.tubitak.arch.pools.HikariPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class HikariPoolTest {
    private HikariDataSource hikariDataSource;
    private HikariPoolMXBean hikariPoolMXBean;
    private HikariConfigMXBean hikariConfigMXBean;
    private List<Connection> connections;

    @BeforeEach
    public void initTestEnvironment() {
        connections = new ArrayList<>();
        hikariDataSource = (HikariDataSource) CommonPool.commonPool.getDataSource();
                //(HikariDataSource) ((HikariPool) CommonPool.commonPool.getDataSource());
        hikariConfigMXBean = hikariDataSource.getHikariConfigMXBean();
        hikariPoolMXBean = hikariDataSource.getHikariPoolMXBean();
    }

    @AfterEach
    public void cleanTestEnvironment() {
        connections.forEach((connection) -> {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        connections = null;
        hikariDataSource = null;
        hikariPoolMXBean = null;
        hikariConfigMXBean = null;
    }

    @Test
    public void testAllPropertiesIsTrue() throws SQLException, IOException {
        Properties properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream("hikaridbprop.properties"));

        connections.add(hikariDataSource.getConnection());

        assertEquals(properties.getProperty("maxLifetime"), String.valueOf(hikariConfigMXBean.getMaxLifetime()));
        assertEquals(properties.getProperty("maximumPoolSize"), String.valueOf(hikariConfigMXBean.getMaximumPoolSize()));
        assertEquals(properties.getProperty("poolName"), String.valueOf(hikariConfigMXBean.getPoolName()));
        assertEquals(properties.getProperty("connectionTimeout"), String.valueOf(hikariConfigMXBean.getConnectionTimeout()));
        assertEquals(properties.getProperty("idleTimeout"), String.valueOf(hikariConfigMXBean.getIdleTimeout()));
        assertEquals(properties.getProperty("minimumIdle"), String.valueOf(hikariConfigMXBean.getMinimumIdle()));

        connections.get(connections.size() - 1).close();
        assertEquals(0, hikariDataSource.getHikariPoolMXBean().getActiveConnections());
    }

    @Test
    public void isMaxConnectionPropertiesTrue() {
        //MaksimumPoolSize'ın 10 olduğundan emin olalım.
        int tempMaxPoolSize = hikariConfigMXBean.getMaximumPoolSize();
        hikariConfigMXBean.setMaximumPoolSize(10);
        hikariDataSource = new HikariDataSource((HikariConfig) hikariConfigMXBean);

        assertEquals(10000, hikariConfigMXBean.getConnectionTimeout());

        long tempTimeOut = hikariConfigMXBean.getConnectionTimeout();

        hikariPoolMXBean.suspendPool();
        //ConnectionTimeOut'u 5sn ayarladım.
        hikariConfigMXBean.setConnectionTimeout(5000);
        hikariPoolMXBean.resumePool();

        //10 connection oluşacak. Burada Exception atmaması lazım.
        IntStream.range(0, 10).forEach((elem) -> {
            try {
                connections.add(hikariDataSource.getConnection());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // Burada 5 sn bekleyip exception alacağız.
        Instant start = Instant.now();
        Exception ex = assertThrows(java.sql.SQLTransientConnectionException.class, () -> {
            connections.add(hikariDataSource.getConnection());
        });
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        assertTrue(ex.getMessage().startsWith("HikariPool-1 - Connection is not available, request timed out after"));
        assertTrue(timeElapsed > 5000 - 400 && timeElapsed < 5000 + 400);

        connections.forEach((con) -> {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        hikariConfigMXBean.setMaximumPoolSize(tempMaxPoolSize);
        hikariConfigMXBean.setConnectionTimeout(tempTimeOut);
    }

    @Test
    public void isThrowExceptionIfPoolSuspendedAndIfThrowSuspendedPropertyTrue() throws SQLException {
        System.setProperty("com.zaxxer.hikari.throwIfSuspended", "true");

        assertEquals(0, hikariPoolMXBean.getActiveConnections());

        connections.add(hikariDataSource.getConnection());
        // con.close();

        hikariDataSource.getHikariPoolMXBean().suspendPool();
        // Kalıyo burada...

        assertThrows(java.sql.SQLException.class, hikariDataSource::getConnection);
        hikariDataSource.getHikariPoolMXBean().resumePool();
    }

    @Test
    public void isWaitingThreadIfPoolSuspendedAndIfThrowSuspendedPropertyFalse() throws SQLException {
        System.setProperty("com.zaxxer.hikari.throwIfSuspended", "false");

        assertEquals(0, hikariPoolMXBean.getActiveConnections());

        connections.add(hikariDataSource.getConnection());

        hikariDataSource.getHikariPoolMXBean().suspendPool();

        assertNotEquals(Optional.empty(), getAllWaitingThreadIfExist());

        hikariDataSource.getHikariPoolMXBean().resumePool();
    }

    @Test
    public void isOKChangePoolConfigurationInRuntime() {
        assertEquals(40000, hikariDataSource.getHikariConfigMXBean().getMaxLifetime());

        //poolMXBean.suspendPool();
        long tempMaxLifetime = hikariDataSource.getMaxLifetime();
        hikariDataSource.setMaxLifetime(70000);
        //poolMXBean.resumePool();

        assertEquals(70000, hikariDataSource.getHikariConfigMXBean().getMaxLifetime());
        hikariConfigMXBean.setMaxLifetime(tempMaxLifetime);
    }

    @Test
    public void isOKChangePoolConfigurationInRuntimeBetweenSuspendAndResume() {
        HikariDataSource datasource = hikariDataSource;
        HikariPoolMXBean poolMXBean = datasource.getHikariPoolMXBean();

        assertEquals(40000, datasource.getHikariConfigMXBean().getMaxLifetime());


        poolMXBean.suspendPool();
        long tempMaxLifeTime = datasource.getMaxLifetime();
        datasource.setMaxLifetime(30000);
        poolMXBean.resumePool();

        assertEquals(30000, datasource.getHikariConfigMXBean().getMaxLifetime());
        datasource.setMaxLifetime(tempMaxLifeTime);
    }

    @Test
    public void isEvictConnectionsAfterSuspendAndResume() throws SQLException {
        System.setProperty("com.zaxxer.hikari.throwIfSuspended", "true");

        for (int i = 0; i < 5; i++)
            connections.add(hikariDataSource.getConnection());

        assertEquals(5, hikariPoolMXBean.getTotalConnections());

        hikariPoolMXBean.suspendPool();
        assertThrows(java.sql.SQLException.class, hikariDataSource::getConnection);
        UtilityElf.quietlySleep(4_000);
        hikariPoolMXBean.resumePool();
        assertEquals(5, hikariDataSource.getHikariPoolMXBean().getTotalConnections());
    }

    private Optional<Set<Thread>> getAllWaitingThreadIfExist() {
        var threadSet = Thread.getAllStackTraces().keySet();

        //threadSet.forEach((elem) -> System.out.println(elem.getName()));

        var blockedThreads = threadSet.stream().filter((elem) -> elem.getState() == Thread.State.WAITING).collect(Collectors.toSet());

        return blockedThreads.isEmpty() ? Optional.empty() : Optional.of(blockedThreads);
    }
}
