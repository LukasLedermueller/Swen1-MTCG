package at.fhtw.mtcg.controller.game;

import at.fhtw.httpserver.server.HeaderMap;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreControllerTest {

    @Test
    void testScoreControllerGetScoreboard() {
        Request request = new Request();
        Response response;

        //empty request = no token
        response = new ScoreController().getScoreboard(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //get scoreboard
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: test-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new ScoreController().getScoreboard(request);
        assertTrue(response.get().contains("200 OK") && response.get().contains("testName"));
    }
}