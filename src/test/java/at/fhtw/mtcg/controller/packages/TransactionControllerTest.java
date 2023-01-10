package at.fhtw.mtcg.controller.packages;

import at.fhtw.httpserver.server.HeaderMap;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionControllerTest {

    @Test
    void testTransactionControllerPerformTransaction() {
        Request request = new Request();
        Response response;

        //empty request = no token
        response = new TransactionController().performTransaction(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //no money
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: emptyUser-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new TransactionController().performTransaction(request);
        assertTrue(response.get().contains("403 Forbidden"));

        //buy cards
        headerMap = new HeaderMap();
        headerMap.ingest("Authorization: test-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new TransactionController().performTransaction(request);
        assertTrue(response.get().contains("200 OK"));
    }
}