package at.fhtw.mtcg.controller.cards;

import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.HeaderMap;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckControllerTest {

    @Test
    void testDeckControllerGetDeckOfUser() {
        Request request = new Request();
        Response response;

        //empty request = no token
        response = new DeckController().getDeckOfUser(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //empty deck
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: emptyUser-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new DeckController().getDeckOfUser(request);
        assertTrue(response.get().contains("204 No Content"));

        //get deck
        headerMap = new HeaderMap();
        headerMap.ingest("Authorization: test-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new DeckController().getDeckOfUser(request);
        assertTrue(response.get().contains("200 OK"));
    }

    @Test
    void updateDeckOfUser() {
        Request request = new Request();
        Response response;

        //empty response = no token
        response = new DeckController().updateDeckOfUser(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //empty requestBody
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: emptyUser-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new DeckController().updateDeckOfUser(request);
        assertTrue(response.get().contains("500 Internal Server Error"));

        //user is not owner of card
        request.setBody("[\"1\",\"2\",\"3\",\"4\"]");
        response = new DeckController().updateDeckOfUser(request);
        assertTrue(response.get().contains("403 Forbidden"));

        //cards are not unique
        headerMap = new HeaderMap();
        headerMap.ingest("Authorization: test-mtcgToken");
        request.setHeaderMap(headerMap);
        request.setBody("[\"1\",\"1\",\"3\",\"4\"]");
        response = new DeckController().updateDeckOfUser(request);
        assertTrue(response.get().contains("403 Forbidden"));

        //wrong deck size
        request.setBody("[\"1\",\"2\",\"3\"]");
        response = new DeckController().updateDeckOfUser(request);
        assertTrue(response.get().contains("400 Bad Request"));

        //update deck
        request.setBody("[\"1\",\"2\",\"3\",\"4\"]");
        response = new DeckController().updateDeckOfUser(request);
        assertTrue(response.get().contains("200 OK"));
    }
}