package at.fhtw.mtcg.service.users;

import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest {

    @Test
    void testSessionServiceLogin() {
        Request request = new Request();
        Response response = new SessionService().handleRequest(request);
        assertTrue(response.get().contains("400 Bad Request"));
        request.setMethod(Method.POST);
        request.setPathname("/sessions");
        request.setBody("{\"username\":\"test\",\"password\":\"test123\"}");
        response = new SessionService().handleRequest(request);
        assertTrue(response.get().contains("401 Unauthorized"));
        request.setBody("{\"username\":\"test\",\"password\":\"test\"}");
        response = new SessionService().handleRequest(request);
        assertTrue(response.get().contains("test-mtcgToken"));
    }
}