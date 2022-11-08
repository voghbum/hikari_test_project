package tr.gov.bilgem.tubitak.arch.pools;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import tr.gov.bilgem.tubitak.arch.properties.HikariProp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariPool implements CommonPool {
    private static HikariDataSource ds;
    private HikariPoolMXBean hikariPoolMXBean;
    private HikariConfigMXBean hikariConfigMXBean;
    private final HikariProp prop = new HikariProp();

    @Override
    public void configure() throws IOException {
        Properties properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream("hikaridbprop.properties"));

        HikariConfig config = new HikariConfig(properties);

        config.setUsername(prop.getUser());
        config.setPassword(prop.getPassword());
        config.setDriverClassName(prop.getDriverClassName());
        config.setJdbcUrl(prop.getUrl());



    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public void close() throws IOException {
        ds.close();
    }
}
