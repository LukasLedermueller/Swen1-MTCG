package at.fhtw.mtcg.service.users.users;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcg.controller.Controller;
import at.fhtw.mtcg.model.UserCredentials;

public class UserController extends Controller {
    private UserDAL userDAL;

    public UserController(UserDAL userDAL) {
        this.userDAL = userDAL;
    }

    public Response addUser(Request request) {

        try {
            //System.out.println(request.getBody());
            if (request.getBody() == null) {
                throw new NullPointerException();
            }
            UserCredentials userCredentials = this.getObjectMapper().readValue(request.getBody(), UserCredentials.class);
            if (this.userDAL.addUserCredentials(userCredentials)) {
                System.out.println("new user: " + userCredentials.getUsername());
                return new Response(
                        HttpStatus.CREATED,
                        ContentType.JSON,
                        "{message: \"User successfully created\" }"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response(
                HttpStatus.CONFLICT,
                ContentType.JSON,
                "{message: \"User with same username already registered\" }"
        );
    }

    public Response getUser(String username) {
        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ message: \"Success\" }"
        );
    }

    public Response updateUser(Request request) {
        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{message: \"success\" }"
        );
    }

}
