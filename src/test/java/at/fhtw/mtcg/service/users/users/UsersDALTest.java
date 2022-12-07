package at.fhtw.mtcg.service.users.users;

import at.fhtw.mtcg.model.UserCredentials;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsersDALTest {

    @Test
    void testAddUserCredentials() {

        UserDAL.addUserCredentials(new UserCredentials("test", "test"));
        //assertEquals(UsersDAL.addUserCredentials(new UserCredentials("test", "test")), true);
        assertEquals(UserDAL.addUserCredentials(new UserCredentials("test", "test")), false);
    }
}