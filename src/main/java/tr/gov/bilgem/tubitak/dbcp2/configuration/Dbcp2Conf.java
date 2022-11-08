package tr.gov.bilgem.tubitak.dbcp2.configuration;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class Dbcp2Conf{
    private static BasicDataSource dataSource = null;

    static {
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("dbcp2.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:~/test_db");
        var propString = properties.toString();
        propString = propString.replaceAll(",",";");
        propString = propString.replaceAll(" ","");
        propString = propString.substring(1, propString.length() - 1);

        System.out.println(propString);
        dataSource.setConnectionProperties(propString);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}



/*public class Dbcp2Conf {
    private static DataSource dataSource = null;

    static {
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("dbcp2.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory("jdbc:h2:~/test_db",
                properties);

        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);

        GenericObjectPoolConfig<PoolableConnection> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(25);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory, config);
        poolableConnectionFactory.setPool(connectionPool);

        dataSource = new PoolingDataSource<>(connectionPool);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}*/



/*
public class Dbcp2Conf {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:h2:~/test_db");
        ds.setUsername("sa");
        ds.setPassword("password");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private Dbcp2Conf(){ }
}
*/