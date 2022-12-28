package at.fhtw.mtcg.dal.repository.packages;

import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.exception.NoPackagesException;
import at.fhtw.mtcg.model.Card;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private UnitOfWork unitOfWork;
    public TransactionRepository(UnitOfWork unitOfWork){
        this.unitOfWork = unitOfWork;
    }
    //functions
    public List<String> getCardsAndDeletePackage() throws Exception {
        List<String> cards = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        DELETE FROM packages
                        WHERE id = (SELECT id FROM packages LIMIT 1)
                        RETURNING *;
                    """)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NoPackagesException("No packages available");
            }
            cards.add(resultSet.getString("card1"));
            cards.add(resultSet.getString("card2"));
            cards.add(resultSet.getString("card3"));
            cards.add(resultSet.getString("card4"));
            cards.add(resultSet.getString("card5"));
            return cards;
        } catch (NoPackagesException e) {
            throw new NoPackagesException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
