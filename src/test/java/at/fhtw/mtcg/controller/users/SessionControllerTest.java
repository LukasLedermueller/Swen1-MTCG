package at.fhtw.mtcg.controller.users;

import at.fhtw.httpserver.server.HeaderMap;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcg.controller.game.StatsController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionControllerTest {

    @Test
    void login() {
        Request request = new Request();
        Response response;

        //empty request = empty requestBody
        response = new SessionController().login(request);
        assertTrue(response.get().contains("500 Internal Server Error"));

        //invalid credentials
        request.setBody("{\"Username\":\"test\", \"Password\":\"wrongPassword\"}");
        response = new SessionController().login(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //login user
        request.setBody("{\"Username\":\"test\", \"Password\":\"test\"}");
        response = new SessionController().login(request);
        assertTrue(response.get().contains("200 OK") && response.get().contains("test-mtcgToken"));
    }

    @Test
    void logout() {
        Request request = new Request();
        Response response;

        //empty request = no token
        response = new SessionController().logout(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //logout user
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: logoutUser-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new SessionController().logout(request);
        assertTrue(response.get().contains("200 OK"));
    }
}