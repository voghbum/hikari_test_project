package tr.gov.bilgem.tubitak.arch.pools;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.function.Function;

import static tr.gov.bilgem.tubitak.arch.pools.PropertyKeys.initialSizeKey;
import static tr.gov.bilgem.tubitak.arch.pools.PropertyKeys.passwordKey;
import static tr.gov.bilgem.tubitak.arch.pools.PropertyKeys.urlKey;
import static tr.gov.bilgem.tubitak.arch.pools.PropertyKeys.userKey;
import static tr.gov.bilgem.tubitak.arch.pools.PropertyKeys.maxTotalKey;
import static tr.gov.bilgem.tubitak.arch.pools.PropertyKeys.minEvictableIdleTimeKey;
import static tr.gov.bilgem.tubitak.arch.pools.PropertyKeys.timeBetweenEvictionRunsMillisKey;

class Dbcp2Pool implements CommonPool {
    private BasicDataSource ds;

    public Dbcp2Pool(Properties dbcp2Properties) {

        Function<String, Long> longParser = (key) -> Long.valueOf(dbcp2Properties.getProperty(key));
        Function<String, Integer> intParser = (key) -> Integer.valueOf(dbcp2Properties.getProperty(key));

        ds = new BasicDataSource();
        ds.setUrl(dbcp2Properties.getProperty(urlKey));
        ds.setUsername(dbcp2Properties.getProperty(userKey));
        ds.setPassword(dbcp2Properties.getProperty(passwordKey));

        ds.setMaxTotal(intParser.apply(maxTotalKey));
        ds.setInitialSize(intParser.apply(initialSizeKey));
        ds.setTimeBetweenEvictionRunsMillis(longParser.apply(timeBetweenEvictionRunsMillisKey));
        ds.setMinEvictableIdleTimeMillis(longParser.apply(minEvictableIdleTimeKey));
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
