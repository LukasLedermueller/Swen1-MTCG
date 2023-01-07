package at.fhtw.mtcg.service.game;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.Service;
import at.fhtw.mtcg.controller.game.BattleController;
import at.fhtw.mtcg.controller.game.ScoreController;

public class BattleService implements Service {

    private final BattleController battleController;
    public BattleService() {
        this.battleController = new BattleController();
    }
    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.POST && request.getPathParts().size() == 1) {
            return this.battleController.startBattle(request);
        } else {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.PLAIN_TEXT,
                    ""
            );
        }
    }
}
