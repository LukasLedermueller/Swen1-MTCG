package at.fhtw.mtcg.dal.repository.cards;

import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.exception.InvalidTokenException;
import at.fhtw.mtcg.exception.NoCardsException;
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
            String id;
            String name;
            float damage;
            while (resultSet.next()) {
                cards.add(new Card(resultSet.getString("id"), CardName.valueOf(resultSet.getString("name")), resultSet.getFloat("damage")));
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
}
