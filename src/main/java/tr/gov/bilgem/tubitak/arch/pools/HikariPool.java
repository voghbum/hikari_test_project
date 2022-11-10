package tr.gov.bilgem.tubitak.arch.pools;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

class HikariPool implements CommonPool {
    private HikariDataSource ds;

    public HikariPool(Properties hikaripoolProperties) {
        HikariConfig config = new HikariConfig(hikaripoolProperties);
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
