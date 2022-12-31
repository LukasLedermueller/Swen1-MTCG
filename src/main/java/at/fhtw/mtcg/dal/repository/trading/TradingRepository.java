package at.fhtw.mtcg.dal.repository.trading;

import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.exception.DuplicateCardException;
import at.fhtw.mtcg.exception.DuplicateTradingException;
import at.fhtw.mtcg.exception.NoTradesException;
import at.fhtw.mtcg.model.CardName;
import at.fhtw.mtcg.model.TradingDeal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TradingRepository {
    private UnitOfWork unitOfWork;
    public TradingRepository(UnitOfWork unitOfWork){
        this.unitOfWork = unitOfWork;
    }
    //functions
    public List<TradingDeal> getTradings() throws Exception {
        List<TradingDeal> tradings = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        SELECT * FROM tradings;
                    """)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                tradings.add(new TradingDeal(resultSet.getString("id"), resultSet.getString("cardToTrade"),
                        resultSet.getString("type"), resultSet.getFloat("minimumDamage")));
            }
            if(tradings.size() == 0) {
                throw new NoTradesException("No tradings available");
            }
            return tradings;
        } catch (NoTradesException e) {
            throw new NoTradesException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void createTradingDeal(TradingDeal tradingDeal) throws Exception {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        INSERT INTO tradings
                        VALUES (?,?,?,?);
                    """)) {
            preparedStatement.setString(1,tradingDeal.getId());
            preparedStatement.setString(2,tradingDeal.getCardToTrade());
            preparedStatement.setString(3,tradingDeal.getType());
            preparedStatement.setFloat(4,tradingDeal.getMinimumDamage());
            preparedStatement.execute();
        } catch (SQLException e) {
            if(e.getSQLState().equals("23505")) {
                throw new DuplicateTradingException("TradingId already exists");
            }
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public TradingDeal deleteTrading(String tradingDealId) throws Exception {

        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        DELETE FROM tradings
                        WHERE id = ?
                        RETURNING *;
                    """)) {
            preparedStatement.setString(1, tradingDealId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                throw new NoTradesException("No trading found");
            }
            TradingDeal tradingDeal = new TradingDeal(resultSet.getString("id"), resultSet.getString("cardToTrade"),
                    resultSet.getString("type"), resultSet.getFloat("minimumDamage"));
            return tradingDeal;
        } catch (NoTradesException e) {
            throw new NoTradesException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
