package HikariPool.data.repository;

import org.junit.jupiter.api.Test;
import tr.gov.bilgem.tubitak.data.entity.User;
import tr.gov.bilgem.tubitak.data.repository.UserRepository;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryTest {

    @Test
    public void checkLengthOfTable() {
        UserRepository repo = new UserRepository();

        Iterable<User> list = repo.findAll();
        List<User> users = StreamSupport.stream(list.spliterator(), false).toList();

        assertEquals(3, users.size());
    }
}
