package at.fhtw.mtcg.controller.users;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcg.controller.Controller;
import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.dal.repository.users.UserRepository;
import at.fhtw.mtcg.exception.UserAlreadyExistsException;
import at.fhtw.mtcg.exception.UserNotFoundException;
import at.fhtw.mtcg.model.UserCredentials;
import at.fhtw.mtcg.model.UserData;

public class UserController extends Controller {


    public UserController() {}

    public Response addUser(Request request) {

        UnitOfWork unitOfWork;
        try {
            unitOfWork = new UnitOfWork();
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    ""
            );
        }
        try (unitOfWork){
            if (request.getBody() == null) {
                throw new NullPointerException("Request-body is null");
            }
            UserCredentials userCredentials = this.getObjectMapper().readValue(request.getBody(), UserCredentials.class);
            new UserRepository(unitOfWork).addUser(userCredentials);
            unitOfWork.commitTransaction();

            System.out.println("new user: " + userCredentials.getUsername());
            return new Response(
                    HttpStatus.CREATED,
                    ContentType.JSON,
                    "{message: \"User successfully created\" }"
            );
        } catch (UserAlreadyExistsException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.CONFLICT,
                    ContentType.JSON,
                    "{message: \"User with same username already registered\" }"
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
                return new Response(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ContentType.JSON,
                        ""
                );
        }
    }

    public Response getUser(String username) {

        UnitOfWork unitOfWork;
        try {
            unitOfWork = new UnitOfWork();
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    ""
            );
        }
        try (unitOfWork) {
            if(username.isEmpty()) {
                throw new NullPointerException("Username is empty");
            }
            UserData userData = new UserRepository(unitOfWork).getUser(username);
            String userDataJSON = this.getObjectMapper().writeValueAsString(userData);
            unitOfWork.commitTransaction();
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    userDataJSON
            );
        } catch (UserNotFoundException e) {
            unitOfWork.rollbackTransaction();
            System.out.println(e.getMessage());
            return new Response(
                    HttpStatus.NOT_FOUND,
                    ContentType.JSON,
                    "User not found"
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    ""
            );
        }
    }
    public Response updateUser(Request request) {

        UnitOfWork unitOfWork;
        try {
            unitOfWork = new UnitOfWork();
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    ""
            );
        }
        try (unitOfWork) {
            if (request.getBody() == null || request.getPathParts().get(1) == null){
                throw new NullPointerException();
            }
            //HERE: check authorization
            String username = request.getPathParts().get(1);
            UserData userData = this.getObjectMapper().readValue(request.getBody(), UserData.class);
            new UserRepository(unitOfWork).updateUser(username, userData);
            unitOfWork.commitTransaction();
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        "{message: \"success\" }"
                );
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.NOT_FOUND,
                    ContentType.JSON,
                    "User not found"
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    ""
            );
        }
    }
}
