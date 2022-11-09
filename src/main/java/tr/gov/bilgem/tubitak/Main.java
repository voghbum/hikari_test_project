package tr.gov.bilgem.tubitak;

import tr.gov.bilgem.tubitak.data.repository.UserRepository;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        UserRepository userRepository = new UserRepository();

        System.out.println(userRepository.findAll());
    }
}
