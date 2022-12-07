package at.fhtw.mtcg.service.users.users;

import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.Service;


public class UserService implements Service {
    private final UserController usercontroller;
    public UserService() {
        this.usercontroller = new UserController(new UserDAL());
    }
    @Override
    public Response handleRequest(Request request) {
        return this.usercontroller.addUser(request);
    }
}
