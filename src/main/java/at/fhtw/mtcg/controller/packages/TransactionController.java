package at.fhtw.mtcg.controller.packages;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mtcg.controller.Controller;
import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.dal.repository.cards.CardRepository;
import at.fhtw.mtcg.dal.repository.packages.TransactionRepository;
import at.fhtw.mtcg.dal.repository.users.SessionRepository;
import at.fhtw.mtcg.dal.repository.users.UserRepository;
import at.fhtw.mtcg.exception.*;

import java.util.List;

public class TransactionController extends Controller {
    public TransactionController(){}

    public Response performTransaction(Request request) {
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
            if (token == null) {
                throw new InvalidTokenException("Token is empty");
            }
            new SessionRepository(unitOfWork).validateToken(token);
            String username = new SessionRepository(unitOfWork).getUsernameFromToken(token);
            List<String> cardsFromPackage = new TransactionRepository(unitOfWork).getCardsAndDeletePackage();
            new UserRepository(unitOfWork).subtractCoins(username, 5);
            for(String cardId: cardsFromPackage) {
                new CardRepository(unitOfWork).changeOwnerById(username, cardId);
            }
            unitOfWork.commitTransaction();
            System.out.println(username + "performed a transaction");
            return new Response(
                    HttpStatus.OK,
                    ContentType.PLAIN_TEXT,
                    "OK"
            );
        } catch (InvalidTokenException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    ContentType.PLAIN_TEXT,
                    "Authentication information is missing or invalid"
            );
        } catch (NoMoneyException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.FORBIDDEN,
                    ContentType.PLAIN_TEXT,
                    "Not enough money"
            );
        } catch (NoPackagesException e) {
            System.out.println(e.getMessage());
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.NOT_FOUND,
                    ContentType.PLAIN_TEXT,
                    "No packages available"
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
