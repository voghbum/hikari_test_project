package tr.gov.bilgem.tubitak.arch.properties;

import java.io.IOException;
import java.util.Properties;
/*
    MAX_TOTAL=10
    INITIAL_SIZE=5
    TIME_BETWEEN_EVICTION_RUNS_MILLIS=2
    MIN_EVICTABLE_IDLE_TIME=1000*60*30
 */

public class CommonDbProp {
    private String user;
    private String password;
    private String url;
    private String poolType;

    private int maxTotal;
    private int initialSize;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTime;
    private static CommonDbProp instance;

    public static CommonDbProp getInstance() {
        if(instance != null)
            return instance;

        return instance = new CommonDbProp();
    }

    public static CommonDbProp getInstance(Properties commonProp) {
        if(instance != null)
            return instance;

        return instance = new CommonDbProp(commonProp);
    }


    CommonDbProp(Properties commonProp) {
        parseInProperties(commonProp);
    }

    CommonDbProp() {
        Properties commonProp = new Properties();

        try {
            commonProp.load(ClassLoader.getSystemResourceAsStream("commondbprop.properties"));
        } catch (IOException e) {
            throw new RuntimeException("'commondbprop.properties' cannot found or load", e);
        }

        parseInProperties(commonProp);
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public void setMinEvictableIdleTime(long minEvictableIdleTime) {
        this.minEvictableIdleTime = minEvictableIdleTime;
    }

    public static void setInstance(CommonDbProp instance) {
        CommonDbProp.instance = instance;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getMaxTotal() {
        return maxTotal;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public long getMinEvictableIdleTime() {
        return minEvictableIdleTime;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getPoolType() {
        return poolType;
    }

    private void parseInProperties(Properties commonProp) {
        setUser(commonProp.getProperty("user"));
        setPassword(commonProp.getProperty("password"));
        setUrl(commonProp.getProperty("url"));
        setPoolType(commonProp.getProperty("poolType"));
        setMaxTotal(Integer.parseInt(commonProp.getProperty("MAX_TOTAL")));
        setInitialSize(Integer.parseInt(commonProp.getProperty("INITIAL_SIZE")));
        setTimeBetweenEvictionRunsMillis(Long.parseLong(commonProp.getProperty("TIME_BETWEEN_EVICTION_RUNS_MILLIS")));
        setMinEvictableIdleTime(Long.parseLong(commonProp.getProperty("MIN_EVICTABLE_IDLE_TIME")));
    }
}
