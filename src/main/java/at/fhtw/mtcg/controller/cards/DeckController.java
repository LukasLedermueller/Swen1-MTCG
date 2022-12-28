package at.fhtw.mtcg.controller.cards;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcg.controller.Controller;
import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.dal.repository.cards.CardRepository;
import at.fhtw.mtcg.dal.repository.cards.DeckRepository;
import at.fhtw.mtcg.dal.repository.users.SessionRepository;
import at.fhtw.mtcg.exception.*;
import at.fhtw.mtcg.model.Card;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeckController extends Controller {
    public DeckController(){}

    public Response getDeckOfUser(Request request) {
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
            String token = request.getHeaderMap().getHeader("Authorization");
            ;
            if (token == null) {
                throw new InvalidTokenException("Token is empty");
            }
            new SessionRepository(unitOfWork).validateToken(token);
            String username = new SessionRepository(unitOfWork).getUsernameFromToken(token);
            List<String> deck = new DeckRepository(unitOfWork).getDeckOfUser(username);
            if(deck.size() == 0) {
                throw new NoCardsException("Empty deck");
            } else if(deck.size() < 4) {
                throw new InvalidDeckSizeException("Wrong deck size");
            }
            System.out.println(deck.size());
            List<Card> deckCards = new ArrayList<>();
            for(String id: deck){
                deckCards.add(new CardRepository(unitOfWork).getCardById(id));
            }
            String deckJson = this.getObjectMapper().writeValueAsString(deckCards);
            unitOfWork.commitTransaction();
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    deckJson
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
                    "Deck is empty"
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
    public Response updateDeckOfUser(Request request) {
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
            String token = request.getHeaderMap().getHeader("Authorization");
            if (token == null) {
                throw new InvalidTokenException("Token is empty");
            }
            if (request.getBody() == null) {
                throw new EmptyRequestBodyException("Request-Body is null");
            }
            new SessionRepository(unitOfWork).validateToken(token);
            String username = new SessionRepository(unitOfWork).getUsernameFromToken(token);
            List<String> deck = this.getObjectMapper().readValue(request.getBody(), ArrayList.class);
            if (deck.size() != 4) {
                throw new InvalidDeckSizeException("Wrong deck size");
            }
            Set<String> set = new HashSet<>(deck);
            if (deck.size() != set.size()) {
                throw new NotAvailableException("Cards are not unique");
            }
            for (String id : deck) {
                new CardRepository(unitOfWork).checkCardOwnership(username, id);
            }
            new DeckRepository(unitOfWork).updateDeckOfUser(username, deck);
            unitOfWork.commitTransaction();
            return new Response(
                    HttpStatus.OK,
                    ContentType.PLAIN_TEXT,
                    "Ok"
            );
        } catch (InvalidTokenException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    ContentType.PLAIN_TEXT,
                    "Authentication information is missing or invalid"
            );
        } catch (InvalidDeckSizeException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.PLAIN_TEXT,
                    "Wrong amount of cards"
            );
        } catch (NotAvailableException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.FORBIDDEN,
                    ContentType.PLAIN_TEXT,
                    "At least one card is not available"
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
