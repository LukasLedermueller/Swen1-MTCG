package at.fhtw.mtcg.dal.repository.packages;

import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.exception.InvalidCredentialsException;
import at.fhtw.mtcg.exception.InvalidTokenException;
import at.fhtw.mtcg.model.Card;
import at.fhtw.mtcg.model.UserCredentials;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PackageRepository {
    private UnitOfWork unitOfWork;
    public PackageRepository(UnitOfWork unitOfWork){
        this.unitOfWork = unitOfWork;
    }
    //functions
    public void createCardPackage(List<Card> newPackage) throws Exception {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        INSERT INTO packages (card1, card2, card3, card4, card5)
                        VALUES (?,?,?,?,?);
                    """)) {
            preparedStatement.setString(1, newPackage.get(0).getId());
            preparedStatement.setString(2, newPackage.get(1).getId());
            preparedStatement.setString(3, newPackage.get(2).getId());
            preparedStatement.setString(4, newPackage.get(3).getId());
            preparedStatement.setString(5, newPackage.get(4).getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
