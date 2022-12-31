package at.fhtw.mtcg.service.trading;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.Service;
import at.fhtw.mtcg.controller.game.ScoreController;
import at.fhtw.mtcg.controller.trading.TradingController;

public class TradingService implements Service {

    private final TradingController tradingController;
    public TradingService() {
        this.tradingController = new TradingController();
    }
    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.GET && request.getPathParts().size() == 1) {
            return this.tradingController.getTrades(request);
        } else if (request.getMethod() == Method.POST && request.getPathParts().size() == 1) {
            return this.tradingController.createTrading(request);
        } else if (request.getMethod() == Method.DELETE && request.getPathParts().size() == 2) {
            return this.tradingController.deleteTrading(request);
        } else if (request.getMethod() == Method.POST && request.getPathParts().size() == 2) {
            return this.tradingController.performTrading(request);
        } else {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.PLAIN_TEXT,
                    ""
            );
        }
    }
}
