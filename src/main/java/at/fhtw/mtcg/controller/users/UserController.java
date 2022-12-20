package at.fhtw.mtcg.controller.users;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcg.controller.Controller;
import at.fhtw.mtcg.dal.repository.users.UserRepository;
import at.fhtw.mtcg.model.UserCredentials;

public class UserController extends Controller {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response addUser(Request request) {

        try {
            if (request.getBody() == null) {
                throw new NullPointerException("Requestbody is null");
            }
            UserCredentials userCredentials = this.getObjectMapper().readValue(request.getBody(), UserCredentials.class);
            if (this.userRepository.addUser(userCredentials)) {
                System.out.println("new user: " + userCredentials.getUsername());
                return new Response(
                        HttpStatus.CREATED,
                        ContentType.JSON,
                        "{message: \"User successfully created\" }"
                );
            } else {
                System.out.println("username " + userCredentials.getUsername() + " already exists");
                return new Response(
                        HttpStatus.CONFLICT,
                        ContentType.JSON,
                        "{message: \"User with same username already registered\" }"
                );
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    ""
            );
        }
    }

    public Response getUser(String username) {
        //chech if string is null
        userRepository.getUser(username);
        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ message: \"Success\" }"
        );
    }

    public Response updateUser(Request request) {
        /*check if request is null;
        UserData userData = this.getObjectMapper().readValue(request.getBody(), UserData.class);*/
        userRepository.updateUser();
        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{message: \"success\" }"
        );
    }

}
