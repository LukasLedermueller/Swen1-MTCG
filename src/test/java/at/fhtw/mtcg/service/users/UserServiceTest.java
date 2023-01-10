package at.fhtw.mtcg.service.users;

import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.HeaderMap;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.utils.RequestBuilder;
import at.fhtw.mtcg.controller.users.SessionController;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.dal.repository.users.SessionRepository;
import at.fhtw.mtcg.model.UserCredentials;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Test
    void testUserServiceAddUser() throws Exception {

        Request request = new Request();
        Response response = new UserService().handleRequest(request);
        assertTrue(response.get().contains("400 Bad Request"));
        request.setMethod(Method.POST);
        request.setPathname("/users");
        request.setBody("{\"username\":\"test\",\"password\":\"test\"}");
        new UserService().handleRequest(request);
        response = new UserService().handleRequest(request);
        assertTrue(response.get().contains("409 Conflict"));;
    }
    @Test
    void testUserServiceUpdateUser() throws Exception {
        Request request = new Request();
        Response response = new UserService().handleRequest(request);
        assertTrue(response.get().contains("400 Bad Request"));
        request.setMethod(Method.PUT);
        request.setPathname("/users/test");
        request.setBody("{\"name\":\"testName\",\"bio\":\"testBio\",\"image\":\"testImage\"}");
        response = new UserService().handleRequest(request);
        assertTrue(response.get().contains("401 Unauthorized"));
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: test-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new UserService().handleRequest(request);
        assertTrue(response.get().contains("OK"));
    }
    @Test
    void testUserServiceGetUser() throws Exception {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("GET /users/test HTTP/1.1",
                "Content-Type: text/plain",
                "Content-Length: 8",
                "Accept: */*",
                "Authorization: test-mtcgToken",
                "");
        Request request = new RequestBuilder().buildRequest(reader);
        Response response = new UserService().handleRequest(request);
        assertTrue(response.get().contains("testName"));
        assertTrue(response.get().contains("testBio"));
        assertTrue(response.get().contains("testImage"));
    }
}