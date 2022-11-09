package tr.gov.bilgem.tubitak.arch.pools;

import org.apache.commons.dbcp2.BasicDataSource;
import tr.gov.bilgem.tubitak.arch.properties.CommonDbProp;

import java.sql.Connection;
import java.sql.SQLException;

public class Dbcp2Pool implements CommonPool {
    private static final BasicDataSource ds;

    static {
        CommonDbProp commonProp = new CommonDbProp();

        ds = new BasicDataSource();
        ds.setUrl(commonProp.getUrl());
        ds.setUsername(commonProp.getUser());
        ds.setPassword(commonProp.getPassword());
    }

    public Dbcp2Pool() {
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public BasicDataSource getDs() {
        return ds;
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
