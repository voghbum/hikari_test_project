package tr.gov.bilgem.tubitak.arch.properties;

import com.zaxxer.hikari.HikariConfig;

import java.io.IOException;
import java.util.Properties;

public class HikariProp extends CommonDbProp {
    private boolean registerMBeans;
    private String poolName;
    private boolean allowPoolSuspension;
    private boolean autoCommit;
    private long connectionTimeout;
    private long idleTimeout;
    private long minimumIdle;
    private long maximumPoolSize;
    private long maximumLifetime;
    private long keepLiveTime;

    private void parseInProperties(Properties prop) {
        setRegisterMBeans(Boolean.parseBoolean(prop.getProperty("registerMbeans")));
        setPoolName(prop.getProperty("poolName"));
        setAllowPoolSuspension(Boolean.parseBoolean(prop.getProperty("allowPoolSuspension")));
        setAutoCommit(Boolean.parseBoolean(prop.getProperty("autoCommit")));
        setConnectionTimeout(Long.parseLong(prop.getProperty("connectionTimeout")));
        setIdleTimeout(Long.parseLong(prop.getProperty("idleTimeout")));
        setMinimumIdle(Long.parseLong(prop.getProperty("minimumIdle")));
        setMaximumLifetime(Long.parseLong(prop.getProperty("maximumLifetime")));
        setMaximumPoolSize(Long.parseLong(prop.getProperty("maximumPoolSize")));
        setKeepLiveTime(Long.parseLong(prop.getProperty("keepLiveTime")));
    }

    public HikariProp(Properties commonProp, Properties hikariProp) {
        super(commonProp);
        parseInProperties(hikariProp);
    }

    public HikariProp() {
        Properties hikariProp = new Properties();

        try {
            hikariProp.load(ClassLoader.getSystemResourceAsStream("dbcp2prop.properties"));
        } catch (IOException e) {
            throw new RuntimeException("hikaridbprop.properties cannot found or load", e);
        }

        parseInProperties(hikariProp);
    }

    public void setRegisterMBeans(boolean registerMBeans) {
        this.registerMBeans = registerMBeans;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public void setAllowPoolSuspension(boolean allowPoolSuspension) {
        this.allowPoolSuspension = allowPoolSuspension;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setIdleTimeout(long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public void setMinimumIdle(long minimumIdle) {
        this.minimumIdle = minimumIdle;
    }

    public void setMaximumPoolSize(long maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public void setMaximumLifetime(long maximumLifetime) {
        this.maximumLifetime = maximumLifetime;
    }

    public void setKeepLiveTime(long keepLiveTime) {
        this.keepLiveTime = keepLiveTime;
    }
}
