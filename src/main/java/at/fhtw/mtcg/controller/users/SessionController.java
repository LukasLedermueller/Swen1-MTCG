package at.fhtw.mtcg.controller.users;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcg.controller.Controller;

public class SessionController extends Controller {
    public SessionController(){}

    public Response login(Request request) {
        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "okay"
        );
    }
}
