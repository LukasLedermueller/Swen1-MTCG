package at.fhtw.mtcg.dal.repository;

import at.fhtw.mtcg.dal.repository.users.UserRepository;
import at.fhtw.mtcg.model.UserCredentials;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Test
    void addUser() {
        UserRepository userRepository = new UserRepository();
        try {
            userRepository.addUser(new UserCredentials("test", "test"));
            assertFalse(userRepository.addUser(new UserCredentials("test", "test")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}