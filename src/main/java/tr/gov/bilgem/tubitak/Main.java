package tr.gov.bilgem.tubitak;

import tr.gov.bilgem.tubitak.arch.connections.ConnectionPoolFactory;
import tr.gov.bilgem.tubitak.arch.pools.CommonPool;
import tr.gov.bilgem.tubitak.data.repository.UserRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        CommonPool commonPool = ConnectionPoolFactory.createPool("dbcp2");
        UserRepository userRepository = new UserRepository();

        try (var connection = commonPool.getConnection()) {
            System.out.println(userRepository.findAll());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
