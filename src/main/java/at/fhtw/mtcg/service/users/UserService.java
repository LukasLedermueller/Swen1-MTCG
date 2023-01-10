package at.fhtw.mtcg.service.users;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.Service;
import at.fhtw.mtcg.controller.users.UserController;


public class UserService implements Service {
    private final UserController userController;
    public UserService() {
        this.userController = new UserController();
    }
    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.POST && request.getPathParts().size() == 1) {
            return this.userController.addUser(request);
        } else if (request.getMethod() == Method.GET && request.getPathParts().size() == 2) {
            return this.userController.getUser(request);
        } else if (request.getMethod() == Method.GET && request.getPathParts().size() == 1) {
            return this.userController.getUsers(request);
        } else if (request.getMethod() == Method.PUT && request.getPathParts().size() == 2) {
            return this.userController.updateUser(request);
        } else {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.PLAIN_TEXT,
                    ""
            );
        }
    }
}
