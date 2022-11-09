package tr.gov.bilgem.tubitak.arch.pools;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import tr.gov.bilgem.tubitak.arch.properties.CommonDbProp;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

class HikariPool implements CommonPool {
    private static final HikariDataSource ds;

    static {
        CommonDbProp prop = CommonDbProp.getInstance();

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

        config.setMaximumPoolSize(prop.getMaxTotal());
        ds = new HikariDataSource(config);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public DataSource getDataSource() {
        return ds;
    }

    @Override
    public void close() throws IOException {
        ds.close();
    }
}
