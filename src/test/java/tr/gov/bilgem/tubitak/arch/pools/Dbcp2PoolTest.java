package tr.gov.bilgem.tubitak.arch.pools;


import org.junit.jupiter.api.Test;
import tr.gov.bilgem.tubitak.arch.connections.ConnectionPoolFactory;

import java.sql.Connection;

public class Dbcp2PoolTest {
    @Test
    public void isOkgettingConnection() throws Exception {
        Connection con;

        try (CommonPool pool = ConnectionPoolFactory.createPool()) {
            con = pool.getConnection();
        }


    }
}
