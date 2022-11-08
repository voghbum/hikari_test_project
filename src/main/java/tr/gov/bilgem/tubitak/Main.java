package tr.gov.bilgem.tubitak;

import tr.gov.bilgem.tubitak.dbcp2.configuration.Dbcp2Conf;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try(Connection con = Dbcp2Conf.getConnection()) {
            System.out.println(con.getSchema());
        }catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
