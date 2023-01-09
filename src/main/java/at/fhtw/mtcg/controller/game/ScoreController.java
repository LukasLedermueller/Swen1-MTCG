package at.fhtw.mtcg.controller.game;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcg.controller.Controller;
import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.dal.repository.users.SessionRepository;
import at.fhtw.mtcg.dal.repository.users.UserRepository;
import at.fhtw.mtcg.exception.InvalidTokenException;
import at.fhtw.mtcg.model.UserStats;

import java.util.List;

public class ScoreController extends Controller {
    public ScoreController(){}

    public Response getScoreboard(Request request) {

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
            String token = request.getHeaderMap().getHeader("Authorization");;
            if(token == null) {
                throw new InvalidTokenException("Token is empty");
            }
            new SessionRepository(unitOfWork).validateToken(token);
            List<UserStats> scoreboard = new UserRepository(unitOfWork).getScoreboard();
            String scoreboardJSON = this.getObjectMapper().writeValueAsString(scoreboard);
            unitOfWork.commitTransaction();
            System.out.println("got scoreboard");
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    scoreboardJSON
            );
        } catch (InvalidTokenException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    ContentType.PLAIN_TEXT,
                    "Authentication information is missing or invalid"
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
