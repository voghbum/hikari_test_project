package tr.gov.bilgem.tubitak.arch.pools;

import java.io.IOException;
import java.sql.Connection;

public interface CommonPool extends AutoCloseable {
    Connection getConnection() throws Exception;
}
