package at.fhtw.mtcg.controller.cards;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcg.controller.Controller;
import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.dal.repository.cards.CardRepository;
import at.fhtw.mtcg.dal.repository.users.SessionRepository;
import at.fhtw.mtcg.exception.InvalidTokenException;
import at.fhtw.mtcg.exception.NoCardsException;
import at.fhtw.mtcg.model.Card;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class CardController extends Controller {
    public CardController(){}

    public Response getCardsOfUser(Request request) {
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
        try(unitOfWork) {
            String token = request.getHeaderMap().getHeader("Authorization");;
            if(token == null) {
                throw new InvalidTokenException("Token is empty");
            }
            new SessionRepository(unitOfWork).validateToken(token);
            String username = new SessionRepository(unitOfWork).getUsernameFromToken(token);
            List<Card> cards = new CardRepository(unitOfWork).getCardsOfUser(username);
            if(cards.size() == 0) {
                throw new NoCardsException("User has no cards");
            }
            String cardsJson = this.getObjectMapper().writeValueAsString(cards);
            unitOfWork.commitTransaction();
            System.out.println("got cards of " + username);
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    cardsJson
            );
        } catch (InvalidTokenException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    ContentType.PLAIN_TEXT,
                    "Authentication information is missing or invalid"
            );
        } catch (NoCardsException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.NO_CONTENT,
                    ContentType.PLAIN_TEXT,
                    "User has no cards"
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.PLAIN_TEXT,
                    ""
            );
        }
    }
}
