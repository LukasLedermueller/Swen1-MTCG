package at.fhtw.mtcg.dal.repository.users;

import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.exception.NoMoneyException;
import at.fhtw.mtcg.exception.UserAlreadyExistsException;
import at.fhtw.mtcg.exception.UserNotFoundException;
import at.fhtw.mtcg.model.UserCredentials;
import at.fhtw.mtcg.model.UserData;
import at.fhtw.mtcg.model.UserStats;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    private UnitOfWork unitOfWork;
    public UserRepository(UnitOfWork unitOfWork){
        this.unitOfWork = unitOfWork;
    }
    public void addUser(UserCredentials userCredentials) throws Exception {

        //System.out.println(userCredentials.getUsername() + " : " + userCredentials.getPassword());
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        INSERT INTO users (username, password)
                        VALUES (?,?);
                    """))
        {
            preparedStatement.setString(1, userCredentials.getUsername());
            preparedStatement.setString(2, userCredentials.getPassword());
            preparedStatement.execute();
        } catch (SQLException e) {
            if(e.getSQLState().equals("23505")) {
                throw new UserAlreadyExistsException("User already exists");
            }
            throw new SQLException(e.getMessage());
        }
    }

    public UserData getUserInfo(String username) throws Exception {

        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        SELECT * FROM users
                        WHERE username = ?;
                    """))
        {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String name = resultSet.getString("name");
                String bio = resultSet.getString("bio");
                String image = resultSet.getString("image");
                return new UserData(name, bio, image);
            } else {
                throw new UserNotFoundException("User not found");
            }
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void updateUserInfo(String username, UserData userData) throws Exception {

        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                                 UPDATE users
                                 SET name = ?, bio = ?, image = ?
                                 WHERE username = ?;
                             """)) {
            preparedStatement.setString(1, userData.getName());
            preparedStatement.setString(2, userData.getBio());
            preparedStatement.setString(3, userData.getImage());
            preparedStatement.setString(4, username);

            if (preparedStatement.executeUpdate() != 1) {
                throw new UserNotFoundException("User not found");
            }

        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void subtractCoins(String username, int amount) throws Exception {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                                 UPDATE users
                                 SET coins = coins -?
                                 WHERE username = ?
                             """)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, username);
            if (preparedStatement.executeUpdate() != 1) {
                throw new UserNotFoundException("User not found");
            }
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (SQLException e) {
            if(e.getSQLState().equals("23514")) {
                throw new NoMoneyException("User has not enough money");
            }
            throw new SQLException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public UserStats getUserStats(String username) throws Exception {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        SELECT * FROM users
                        WHERE username = ?;
                    """))
        {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return new UserStats(resultSet.getString("Name"), resultSet.getInt("Elo"),
                        resultSet.getInt("wins"), resultSet.getInt("losses"));
            } else {
                throw new UserNotFoundException("User not found");
            }
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}

