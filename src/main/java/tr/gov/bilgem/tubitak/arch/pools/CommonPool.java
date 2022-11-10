package tr.gov.bilgem.tubitak.arch.pools;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

public interface CommonPool extends Closeable {
    Connection getConnection() throws SQLException;
    DataSource getDataSource();
}
