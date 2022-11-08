package tr.gov.bilgem.tubitak.arch.pools;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import tr.gov.bilgem.tubitak.arch.properties.HikariProp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class HikariPool implements CommonPool {
    private static HikariDataSource ds;
    private HikariPoolMXBean hikariPoolMXBean;
    private HikariConfigMXBean hikariConfigMXBean;
    private final HikariProp prop = new HikariProp();



    @Override
    public void configure() {
        HikariConfig config = new HikariConfig();

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
