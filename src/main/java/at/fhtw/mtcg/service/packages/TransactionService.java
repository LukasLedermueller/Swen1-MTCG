package at.fhtw.mtcg.service.packages;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.Service;
import at.fhtw.mtcg.controller.packages.TransactionController;

public class TransactionService implements Service {

    private final TransactionController transactionController;
    public TransactionService() {
        this.transactionController = new TransactionController();
    }
    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.POST && request.getPathParts().size() == 2 && request.getPathParts().get(1).equals("packages")) {
            return this.transactionController.performTransaction(request);
        } else {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.PLAIN_TEXT,
                    ""
            );
        }
    }
}
