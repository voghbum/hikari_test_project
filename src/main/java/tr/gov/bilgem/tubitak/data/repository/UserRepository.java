package tr.gov.bilgem.tubitak.data.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.gov.bilgem.tubitak.arch.pools.ConnectionPoolFactory;
import tr.gov.bilgem.tubitak.data.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);
    private static final String FIND_ALL_SQL = "select * from users";
    ConnectionPoolFactory connectionPoolFactory = new ConnectionPoolFactory();
    Properties properties = new Properties();

    public UserRepository() {
        LOG.info("user repository eklendi");
    }

    public Iterable<User> findAll() {
        List<User> users;

        try (Connection con = connectionPoolFactory.createPool(properties).getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_SQL);
             ResultSet rs = pst.executeQuery()) {

            users = new ArrayList<>();
            User user;

            while (rs.next()) {
                var id = rs.getInt("id");
                var username = rs.getString("username");
                var message = rs.getString("message");

                user = new User((long) id, username, message);

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
