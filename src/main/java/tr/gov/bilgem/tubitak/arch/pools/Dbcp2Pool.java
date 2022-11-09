package tr.gov.bilgem.tubitak.arch.pools;

import org.apache.commons.dbcp2.BasicDataSource;
import tr.gov.bilgem.tubitak.arch.properties.CommonDbProp;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

class Dbcp2Pool implements CommonPool {
    private static final BasicDataSource ds;

    static {
        CommonDbProp commonProp = CommonDbProp.getInstance();

        ds = new BasicDataSource();
        ds.setUrl(commonProp.getUrl());
        ds.setUsername(commonProp.getUser());
        ds.setPassword(commonProp.getPassword());

        ds.setMaxTotal(commonProp.getMaxTotal());
        ds.setInitialSize(commonProp.getInitialSize());
        ds.setTimeBetweenEvictionRunsMillis(commonProp.getTimeBetweenEvictionRunsMillis());
        ds.setMinEvictableIdleTimeMillis(commonProp.getMinEvictableIdleTime());
    }

    public Dbcp2Pool() {
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
        try {
            ds.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
