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

import java.util.List;

public class DeckController extends Controller {
    public DeckController(){}

    public Response getDeckOfUser(Request request) {
        return new Response(
                HttpStatus.OK,
                ContentType.PLAIN_TEXT,
                "Ok"
        );
    }
    public Response updateDeckOfUser(Request request) {
        return new Response(
                HttpStatus.OK,
                ContentType.PLAIN_TEXT,
                "Ok"
        );
    }
}
