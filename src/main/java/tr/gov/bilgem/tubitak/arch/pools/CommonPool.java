package tr.gov.bilgem.tubitak.arch.pools;

import java.sql.Connection;

public interface CommonPool extends AutoCloseable {
    void configure();
    Connection getConnection() throws Exception;
}
