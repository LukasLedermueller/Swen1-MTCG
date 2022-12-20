package at.fhtw.mtcg.service.users;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.Service;
import at.fhtw.mtcg.controller.users.UserController;
import at.fhtw.mtcg.dal.repository.users.UserRepository;


public class UserService implements Service {
    private final UserController usercontroller;
    public UserService() {
        this.usercontroller = new UserController(new UserRepository());
    }
    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.POST && request.getPathParts().size() == 1) {
            return this.usercontroller.addUser(request);
        } else if (request.getMethod() == Method.GET && request.getPathParts().size() == 2) {
            return this.usercontroller.getUser(request.getPathParts().get(1));
        } else if (request.getMethod() == Method.PUT && request.getPathParts().size() == 2) {
            return this.usercontroller.updateUser(request);
        } else {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.JSON,
                    ""
            );
        }
    }
}
