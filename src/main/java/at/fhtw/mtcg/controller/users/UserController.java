package at.fhtw.mtcg.controller.users;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcg.controller.Controller;
import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.dal.repository.cards.DeckRepository;
import at.fhtw.mtcg.dal.repository.users.SessionRepository;
import at.fhtw.mtcg.dal.repository.users.UserRepository;
import at.fhtw.mtcg.exception.EmptyRequestBodyException;
import at.fhtw.mtcg.exception.InvalidTokenException;
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
                    ContentType.PLAIN_TEXT,
                    ""
            );
        }
        try (unitOfWork){
            if (request.getBody() == null) {
                throw new EmptyRequestBodyException("Request-body is null");
            }
            UserCredentials userCredentials = this.getObjectMapper().readValue(request.getBody(), UserCredentials.class);
            new UserRepository(unitOfWork).addUser(userCredentials);
            new DeckRepository(unitOfWork).createDeck(userCredentials.getUsername());
            unitOfWork.commitTransaction();

            System.out.println("new user: " + userCredentials.getUsername());
            return new Response(
                    HttpStatus.CREATED,
                    ContentType.PLAIN_TEXT,
                    ""
            );
        } catch (UserAlreadyExistsException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.CONFLICT,
                    ContentType.PLAIN_TEXT,
                    "User with same username already registered"
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
                return new Response(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ContentType.PLAIN_TEXT,
                        ""
                );
        }
    }

    public Response getUser(Request request) {

        UnitOfWork unitOfWork;
        try {
            unitOfWork = new UnitOfWork();
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.PLAIN_TEXT,
                    ""
            );
        }
        try (unitOfWork) {
            String username = request.getPathParts().get(1);
            if(username.isEmpty()) {
                throw new NullPointerException("Username is empty");
            }
            String token = request.getHeaderMap().getHeader("Authorization");;
            if(token == null) {
                throw new InvalidTokenException("Token is empty");
            }
            //HERE: check authorization
            new SessionRepository(unitOfWork).validateToken(token);
            UserData userData = new UserRepository(unitOfWork).getUserInfo(username);
            String userDataJSON = this.getObjectMapper().writeValueAsString(userData);
            unitOfWork.commitTransaction();
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    userDataJSON
            );
        } catch (InvalidTokenException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    ContentType.PLAIN_TEXT,
                    "Authentication information is missing or invalid"
            );
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.NOT_FOUND,
                    ContentType.PLAIN_TEXT,
                    "User not found"
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.PLAIN_TEXT,
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
                    ContentType.PLAIN_TEXT,
                    ""
            );
        }
        try (unitOfWork) {
            if (request.getBody() == null || request.getPathParts().get(1) == null){
                throw new NullPointerException();
            }
            String username = request.getPathParts().get(1);
            String token = request.getHeaderMap().getHeader("Authorization");;
            if(token == null) {
                throw new InvalidTokenException("Token is empty");
            }
            //HERE: check authorization
            new SessionRepository(unitOfWork).validateToken(token);
            UserData userData = this.getObjectMapper().readValue(request.getBody(), UserData.class);
            new UserRepository(unitOfWork).updateUserInfo(username, userData);
            unitOfWork.commitTransaction();
                return new Response(
                        HttpStatus.OK,
                        ContentType.PLAIN_TEXT,
                        ""
                );
        } catch (InvalidTokenException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    ContentType.PLAIN_TEXT,
                    "Authentication information is missing or invalid"
            );
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.NOT_FOUND,
                    ContentType.PLAIN_TEXT,
                    "User not found"
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.PLAIN_TEXT,
                    ""
            );
        }
    }
}
