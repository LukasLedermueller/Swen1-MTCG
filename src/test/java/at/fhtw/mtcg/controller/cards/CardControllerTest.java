package at.fhtw.mtcg.controller.cards;

import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.HeaderMap;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardControllerTest {

    @Test
    void testCardControllerGetCardsOfUser() {
        Request request = new Request();
        Response response;

        //empty response = no token
        response = new CardController().getCardsOfUser(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //no cards
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: emptyUser-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new CardController().getCardsOfUser(request);
        assertTrue(response.get().contains("204 No Content"));

        //get cards
        headerMap = new HeaderMap();
        headerMap.ingest("Authorization: test-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new CardController().getCardsOfUser(request);
        assertTrue(response.get().contains("200 OK") &&
                response.get().contains("{\"id\":\"1\",\"name\":\"Dragon\",\"damage\":10.0}"));
    }
}