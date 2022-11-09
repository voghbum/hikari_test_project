package tr.gov.bilgem.tubitak.arch.pools;

import java.sql.Connection;
import java.sql.SQLException;

public interface CommonPool extends AutoCloseable {
    Connection getConnection() throws SQLException;

    //Connection getConnection() throws Exception;
}
