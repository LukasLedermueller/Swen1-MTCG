package at.fhtw.mtcg.service.game;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.Service;
import at.fhtw.mtcg.controller.game.StatsController;
import at.fhtw.mtcg.controller.users.SessionController;

public class StatsService implements Service {

    private final StatsController statsController;
    public StatsService() {
        this.statsController = new StatsController();
    }
    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.GET && request.getPathParts().size() == 1) {
            return this.statsController.getStatsOfUser(request);
        } else {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.PLAIN_TEXT,
                    ""
            );
        }
    }
}
