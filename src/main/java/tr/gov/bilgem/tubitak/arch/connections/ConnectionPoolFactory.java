package tr.gov.bilgem.tubitak.arch.connections;

import tr.gov.bilgem.tubitak.arch.pools.CommonPool;
import tr.gov.bilgem.tubitak.arch.pools.Dbcp2Pool;
import tr.gov.bilgem.tubitak.arch.pools.HikariPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

public class ConnectionPoolFactory {
    private static final Map<String, Supplier<CommonPool>> map = new HashMap<>();

    static {
        map.put("Hikari", HikariPool::new);
        map.put("dbcp2", Dbcp2Pool::new);
    }

    public static CommonPool createPool() {
        Properties commonProp = new Properties();

        //TODO: tek bir attribute için dosyayı okumak mantıklı değil. Başka bir yol bul ya da sor.
        try {
            commonProp.load(ClassLoader.getSystemResourceAsStream("commondbprop.properties"));
        } catch (IOException e) {
            throw new RuntimeException("'commondbprop.properties' cannot found or load", e);
        }

        String type = commonProp.getProperty("poolType");

        return map.get(type).get();
    }

    public static CommonPool createPool(String type) {
        var val = map.get(type);

        if (val == null)
            throw new RuntimeException("unknown Pool Type");

        return val.get();
    }
}
