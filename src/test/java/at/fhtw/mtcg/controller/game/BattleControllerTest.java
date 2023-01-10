package at.fhtw.mtcg.controller.game;

import at.fhtw.httpserver.server.HeaderMap;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleControllerTest {

    @Test
    void startBattle() {
        Request request = new Request();
        Response response;

        //empty request = no token
        response = new BattleController().startBattle(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //no user found
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: test-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new BattleController().startBattle(request);
        assertTrue(response.get().contains("404 Not Found"));
    }
}