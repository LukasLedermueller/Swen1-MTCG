package at.fhtw.mtcg.controller.trading;

import at.fhtw.httpserver.server.HeaderMap;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcg.controller.game.StatsController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TradingControllerTest {

    @Test
    void testTradingControllerGetTrades() {
        Request request = new Request();
        Response response;

        //empty request = no token
        response = new TradingController().getTrades(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //get tradings
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: test-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new TradingController().getTrades(request);
        assertTrue(response.get().contains("200 OK") && response.get().contains("monster"));
    }

    @Test
    void testTradingControllerCreateTrading() {
        Request request = new Request();
        Response response;

        //empty request = empty requestBody
        response = new TradingController().createTrading(request);
        assertTrue(response.get().contains("500 Internal Server Error"));

        //no token
        request.setBody("{\"Id\":\"1\",\"CardToTrade\": \"50\",\"Type\": \"monster\",\"MinimumDamage\": 10}");
        response = new TradingController().createTrading(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //create trading
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: test-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new TradingController().createTrading(request);
        assertTrue(response.get().contains("201 CREATED"));
    }

    @Test
    void testTradingControllerDeleteTrading() {
        Request request = new Request();
        Response response;

        //empty request = empty token
        response = new TradingController().deleteTrading(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //tradingDealId empty
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: test-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new TradingController().deleteTrading(request);
        assertTrue(response.get().contains("500 Internal Server Error"));

        //deal not found
        request.setPathname("/tradings/5");
        response = new TradingController().deleteTrading(request);
        assertTrue(response.get().contains("404 Not Found"));

        //deal not owned
        request.setPathname("/tradings/4");
        response = new TradingController().deleteTrading(request);
        assertTrue(response.get().contains("403 Forbidden"));

        //delete deal
        request.setPathname("/tradings/2");
        response = new TradingController().deleteTrading(request);
        assertTrue(response.get().contains("200 OK"));
    }

    @Test
    void testTradingControllerPerformTrading() {
        Request request = new Request();
        Response response;

        //empty request = empty token
        response = new TradingController().performTrading(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //tradingDealId empty
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: admin-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new TradingController().performTrading(request);
        assertTrue(response.get().contains("500 Internal Server Error"));

        //requestBody empty
        request.setPathname("/tradings/4");
        response = new TradingController().performTrading(request);
        assertTrue(response.get().contains("500 Internal Server Error"));

        //deal not found
        request.setBody("52");
        request.setPathname("/tradings/5");
        response = new TradingController().performTrading(request);
        assertTrue(response.get().contains("404 Not Found"));

        //offered card not owned
        request.setBody("1");
        request.setPathname("/tradings/3");
        response = new TradingController().performTrading(request);
        assertTrue(response.get().contains("403 Forbidden"));

        //damage too low
        request.setBody("52");
        response = new TradingController().performTrading(request);
        assertTrue(response.get().contains("403 Forbidden"));

        //perform trading
        request.setPathname("/tradings/3");
        request.setBody("53");
        response = new TradingController().performTrading(request);
        assertTrue(response.get().contains("200 OK"));
    }
}