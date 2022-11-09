package tr.gov.bilgem.tubitak.arch.pools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

public class ConnectionPoolFactory {
    private static final Map<String, Supplier<CommonPool>> poolMap = new HashMap<>();
    private static CommonPool pool;

    static {
        poolMap.put("Hikari", HikariPool::new);
        poolMap.put("dbcp2", Dbcp2Pool::new);
    }

    public static CommonPool getInstance()  {
        if(pool != null)
            return pool;

        Properties commonProp = new Properties();

        //TODO: tek bir attribute için dosyayı okumak mantıklı değil. Başka bir yol bul ya da sor.
        try {
            commonProp.load(ClassLoader.getSystemResourceAsStream("commondbprop.properties"));
        } catch (IOException e) {
            System.err.println("commondbprop.properties cannot read" + e.getMessage());
        }


        String type = commonProp.getProperty("poolType");

        return poolMap.get(type).get();
    }


}
