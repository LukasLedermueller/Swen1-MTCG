package at.fhtw.mtcg.dal.repository.cards;

import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.exception.DuplicateCardException;
import at.fhtw.mtcg.exception.IsOwnedException;
import at.fhtw.mtcg.exception.NoCardsException;
import at.fhtw.mtcg.exception.NotAvailableException;
import at.fhtw.mtcg.model.Card;
import at.fhtw.mtcg.model.CardName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {
    private UnitOfWork unitOfWork;
    public CardRepository(UnitOfWork unitOfWork){
        this.unitOfWork = unitOfWork;
    }

    public List<Card> getCardsOfUser(String username) throws Exception {
        List<Card> cards = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        SELECT * from cards
                        WHERE owner = ?;
                    """)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cards.add(new Card(resultSet.getString("id"), resultSet.getString("name"), resultSet.getFloat("damage")));
            }
            return cards;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
        //CardName cardName = CardName.valueOf("Ork");
    }
    public Card getCardById(String id) throws Exception {
        List<Card> cards = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        SELECT * from cards
                        WHERE id = ?;
                    """)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NoCardsException("Card doesn't exist");
            }
            Card card = new Card(resultSet.getString("id"), resultSet.getString("name"), resultSet.getFloat("damage"));
            return card;
        } catch (NoCardsException e) {
            throw new NoCardsException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void checkCardOwnership(String username, String id) throws Exception {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        SELECT * from cards
                        WHERE owner = ? AND id = ?;
                    """)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotAvailableException("Card is not owned by user");
            }
        } catch (NotAvailableException e) {
            throw new NotAvailableException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void checkCardNotOwned(String username, String id) throws Exception {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        SELECT * from cards
                        WHERE owner = ? AND id = ?;
                    """)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                throw new IsOwnedException("Card is owned by user");
            }
        } catch (IsOwnedException e) {
            throw new NotAvailableException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    public void createNewCard(Card card) throws Exception {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        INSERT INTO cards
                        VALUES (?,?,?);
                    """)) {
            preparedStatement.setString(1,card.getId());
            preparedStatement.setString(2,card.getName());
            preparedStatement.setFloat(3,card.getDamage());
            preparedStatement.execute();
        } catch (SQLException e) {
            if(e.getSQLState().equals("23505")) {
                throw new DuplicateCardException("CardId already exists");
            }
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void changeOwnerById(String username, String id) throws Exception {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        UPDATE cards
                        SET owner = ?
                        WHERE id = ?;
                    """)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, id);
            if(preparedStatement.executeUpdate() != 1) {
                throw new SQLException("Update failed");
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
