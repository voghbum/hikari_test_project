package tr.gov.bilgem.tubitak.arch.connections;

import tr.gov.bilgem.tubitak.arch.pools.CommonPool;
import tr.gov.bilgem.tubitak.arch.pools.Dbcp2Pool;
import tr.gov.bilgem.tubitak.arch.pools.HikariPool;

import java.io.IOException;
import java.util.Properties;

public class ConnectionPoolFactory {
    public static CommonPool createAndConfigurePool() {
        CommonPool pool;

        Properties commonProp = new Properties();

        //TODO: tek bir attribute için dosyayı okumak mantıklı değil. Başka bir yol bul ya da sor.
        try {
            commonProp.load(ClassLoader.getSystemResourceAsStream("commondbprop.properties"));
        } catch (IOException e) {
            throw new RuntimeException("'commondbprop.properties' cannot found or load", e);
        }

        String type = commonProp.getProperty("poolType");

        switch (type) {
            case "Hikari":
                pool = new HikariPool();
                break;

            case "dbcp2":
                pool = new Dbcp2Pool();
                break;

            default:
                throw new RuntimeException("unknown pool Type");
        }

        return pool;
    }

    public static CommonPool createAndConfigurePool(String type) {
        switch (type) {
            case "Hikari":
                return new HikariPool();
            case "dbcp2":
                return new Dbcp2Pool();
            default:
                throw new RuntimeException("unknown pool Type");
        }
    }
}
