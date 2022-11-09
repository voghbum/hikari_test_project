package tr.gov.bilgem.tubitak.arch.pools;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import tr.gov.bilgem.tubitak.arch.properties.CommonDbProp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariPool implements CommonPool {
    private static final HikariDataSource ds;

    static {
        CommonDbProp prop = new CommonDbProp();

        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("hikaridbprop.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HikariConfig config = new HikariConfig(properties);

        config.setUsername(prop.getUser());
        config.setPassword(prop.getPassword());
        config.setJdbcUrl(prop.getUrl());

        ds = new HikariDataSource(config);
    }


    public static HikariDataSource getDs() {
        return ds;
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
