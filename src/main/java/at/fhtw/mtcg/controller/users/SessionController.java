package at.fhtw.mtcg.controller.users;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcg.controller.Controller;
import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.dal.repository.users.SessionRepository;
import at.fhtw.mtcg.exception.InvalidCredentialsException;
import at.fhtw.mtcg.model.UserCredentials;

public class SessionController extends Controller {
    public SessionController(){}

    public Response login(Request request) {

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
            if (request.getBody() == null) {
                throw new NullPointerException("Request-body is null");
            }
            UserCredentials userCredentials = this.getObjectMapper().readValue(request.getBody(), UserCredentials.class);
            new SessionRepository(unitOfWork).validateUserCredentials(userCredentials);
            //generate Token
            String token = userCredentials.getUsername() + "-mtcgToken";
            new SessionRepository(unitOfWork).saveToken(userCredentials.getUsername(), token);
            unitOfWork.commitTransaction();
            return new Response(
                    HttpStatus.OK,
                    ContentType.PLAIN_TEXT,
                    token
            );
        } catch (InvalidCredentialsException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    ContentType.PLAIN_TEXT,
                    "Invalid credentials"
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
