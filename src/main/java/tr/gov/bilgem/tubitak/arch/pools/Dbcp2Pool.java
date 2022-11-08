package tr.gov.bilgem.tubitak.arch.pools;

import org.apache.commons.dbcp2.BasicDataSource;
import tr.gov.bilgem.tubitak.arch.properties.Dbcp2Prop;

import java.sql.Connection;
import java.sql.SQLException;

public class Dbcp2Pool implements CommonPool {
    private static BasicDataSource ds;

    @Override
    public void configure() {

    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public void close() throws Exception {
        try {
            ds.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
