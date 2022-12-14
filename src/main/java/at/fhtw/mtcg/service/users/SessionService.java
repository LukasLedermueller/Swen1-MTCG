package at.fhtw.mtcg.service.users;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.Service;
import at.fhtw.mtcg.controller.users.SessionController;
import at.fhtw.mtcg.model.Card;
import at.fhtw.mtcg.model.CardName;


import java.util.UUID;

public class SessionService implements Service {

    private final SessionController sessionController;
    public SessionService() {
        this.sessionController = new SessionController();
    }
    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.POST && request.getPathParts().size() == 1) {
            return this.sessionController.login(request);
        } else if (request.getMethod() == Method.DELETE && request.getPathParts().size() == 1) {
            return this.sessionController.logout(request);
        } else {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.PLAIN_TEXT,
                    ""
            );
        }
    }
}
