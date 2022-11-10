package tr.gov.bilgem.tubitak.arch.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

public class ConnectionPoolFactory {
    private static final Map<String, Function<Properties, CommonPool>> poolMap = new HashMap<>();

    static {
        poolMap.put("Hikari", HikariPool::new);
        poolMap.put("dbcp2", Dbcp2Pool::new);
    }

    public CommonPool createPool(Properties poolProperties)  {
        String type = poolProperties.getProperty("poolType");

        return poolMap.get(type).apply(poolProperties);
    }
}
