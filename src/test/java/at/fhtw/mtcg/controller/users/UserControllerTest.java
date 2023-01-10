package at.fhtw.mtcg.controller.users;

import at.fhtw.httpserver.server.HeaderMap;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcg.controller.game.ScoreController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void testUserControllerAddUser() {
        Request request = new Request();
        Response response;

        //empty request = empty requestBody
        response = new UserController().addUser(request);
        assertTrue(response.get().contains("500 Internal Server Error"));

        //wrong format
        request.setBody("{\"Uid\":\"test\"}");
        response = new UserController().addUser(request);
        assertTrue(response.get().contains("500 Internal Server Error"));

        //user already exists
        request.setBody("{\"Username\":\"test\", \"Password\":\"test\"}");
        response = new UserController().addUser(request);
        assertTrue(response.get().contains("409 Conflict"));

        //add user
        request.setBody("{\"Username\":\"addUserTest\", \"Password\":\"test\"}");
        response = new UserController().addUser(request);
        assertTrue(response.get().contains("201 CREATED"));
    }

    @Test
    void testUserControllerGetUser() {
        Request request = new Request();
        Response response;

        //empty request = empty pathpart
        response = new UserController().getUser(request);
        assertTrue(response.get().contains("500 Internal Server Error"));

        //no token
        request.setPathname("/users/admin");
        response = new UserController().getUser(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //token of wrong user
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: test-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new UserController().getUser(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //user not found
        request.setPathname("/users/test123");
        response = new UserController().getUser(request);
        assertTrue(response.get().contains("404 Not Found"));

        //get user
        request.setPathname("/users/test");
        response = new UserController().getUser(request);
        System.out.println(response.get());
        assertTrue(response.get().contains("200 OK") && response.get().contains("testName"));
    }

    @Test
    void updateUser() {
        Request request = new Request();
        Response response;

        //empty request = empty pathpart = empty requestBody
        response = new UserController().updateUser(request);
        assertTrue(response.get().contains("500 Internal Server Error"));

        //no token
        request.setBody("{\"Name\":\"testName\",\"Bio\":\"testBio\",\"Image\":\"testImage\"}");
        request.setPathname("/users/admin");
        response = new UserController().updateUser(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //token of wrong user
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: test-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new UserController().updateUser(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //update user
        request.setPathname("/users/test");
        response = new UserController().updateUser(request);
        System.out.println(response.get());
        assertTrue(response.get().contains("200 OK"));
    }
}