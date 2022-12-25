package at.fhtw.mtcg.dal.repository;

import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.dal.repository.users.UserRepository;
import at.fhtw.mtcg.exception.UserAlreadyExistsException;
import at.fhtw.mtcg.model.UserCredentials;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Test
    void addUser() {
        String username = "test";
        UnitOfWork unitOfWork = new UnitOfWork();
        try{
            new UserRepository(unitOfWork).addUser(new UserCredentials(username, "test"));
        } catch (UserAlreadyExistsException e) {
            System.out.println("ok");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Throwable exception = assertThrows(UserAlreadyExistsException.class, () -> new UserRepository(unitOfWork).addUser(new UserCredentials(username, "test")));
            assertEquals("User already exists", exception.getMessage());
            unitOfWork.finishWork();
        }
    }
}