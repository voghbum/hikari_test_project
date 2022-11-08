package tr.gov.bilgem.tubitak.arch.properties;

import java.io.IOException;
import java.util.Properties;

public class CommonDbProp {
    private String user;
    private String password;
    private String driverClassName;
    private String url;
    private String poolType;

    public CommonDbProp(Properties commonProp) {
        parseInProperties(commonProp);
    }

    public CommonDbProp() {
        Properties commonProp = new Properties();

        try {
            commonProp.load(ClassLoader.getSystemResourceAsStream("hikaridbprop.properties"));
        } catch (IOException e) {
            throw new RuntimeException("hikaridbprop.properties cannot found or load", e);
        }

        parseInProperties(commonProp);
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPoolType(String poolType) {
        if (poolType.equals("Hikari") || poolType.equals("dbcp2"))
            this.poolType = poolType;
        else
            throw new RuntimeException("poolType argument not supported");
    }

    private void parseInProperties(Properties commonProp) {
        setUser(commonProp.getProperty("user"));
        setPassword(commonProp.getProperty("password"));
        setDriverClassName(commonProp.getProperty("driverClassName"));
        setUrl(commonProp.getProperty("url"));
        setPoolType(commonProp.getProperty("poolType"));
    }
}
