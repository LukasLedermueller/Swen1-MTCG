package at.fhtw.mtcg.dal.repository.cards;

import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.exception.NoDeckException;
import at.fhtw.mtcg.model.Card;
import at.fhtw.mtcg.model.CardName;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeckRepository {
    private UnitOfWork unitOfWork;
    public DeckRepository(UnitOfWork unitOfWork){
        this.unitOfWork = unitOfWork;
    }

    public List<String> getDeckOfUser(String username) throws Exception {
        List<String> deck = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        SELECT * from decks
                        WHERE owner = ?;
                    """)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                throw new NoDeckException("User has no deck");
            }
            for(int i = 2; i <= 5; i++) {
                if (resultSet.getString(i) != null) {
                    deck.add(resultSet.getString(i));
                }
            }
            return deck;
        } catch (NoDeckException e) {
            throw new NoDeckException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void createDeck(String username) throws Exception {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        INSERT INTO decks (owner)
                        VALUES (?);
                    """)) {
            preparedStatement.setString(1, username);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void updateDeckOfUser(String username, List<String> deck) throws Exception {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        UPDATE decks
                        SET card1 = ?, card2 = ?, card3 = ?, card4 = ?
                        WHERE owner = ?;
                    """)) {
            preparedStatement.setString(1, deck.get(0));
            preparedStatement.setString(2, deck.get(1));
            preparedStatement.setString(3, deck.get(2));
            preparedStatement.setString(4, deck.get(3));
            preparedStatement.setString(5, username);
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
