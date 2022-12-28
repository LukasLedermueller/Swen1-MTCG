package at.fhtw.mtcg.dal.repository.users;

import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.exception.InvalidCredentialsException;
import at.fhtw.mtcg.exception.InvalidTokenException;
import at.fhtw.mtcg.exception.UserAlreadyExistsException;
import at.fhtw.mtcg.model.UserCredentials;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionRepository {
    private UnitOfWork unitOfWork;
    public SessionRepository(UnitOfWork unitOfWork){
        this.unitOfWork = unitOfWork;
    }
    //functions
    public void validateUserCredentials(UserCredentials userCredentials) throws Exception {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        SELECT * from users
                        WHERE username = ? AND
                        password = ?;
                    """))
        {
            preparedStatement.setString(1, userCredentials.getUsername());
            preparedStatement.setString(2, userCredentials.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                throw new InvalidCredentialsException("Invalid Credentials");
            }
        } catch (InvalidCredentialsException e) {
            throw new InvalidCredentialsException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    public void saveToken(String username, String token) throws Exception {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        INSERT INTO tokens (username, token)
                        VALUES (?,?);                        
                    """))
        {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, token);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void validateToken(/*String username, */String token) throws Exception {
        token = checkStartsWithBasic(token);
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        SELECT * from tokens
                        WHERE token = ?;
                    """))
        {
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                throw new InvalidTokenException("Invalid token");
            }
        } catch (InvalidTokenException e) {
            throw new InvalidTokenException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public String getUsernameFromToken(String token) throws Exception {
        token = checkStartsWithBasic(token);
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""                  
                        SELECT username from tokens
                        WHERE token = ?;
                    """))
        {
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                throw new InvalidTokenException("Invalid token");
            }
            String username = resultSet.getString("username");
            return username;
        } catch (InvalidTokenException e) {
            throw new InvalidTokenException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    private String checkStartsWithBasic(String token) throws InvalidTokenException {
        if (token.startsWith("Basic")) {
            token = token.split(" ")[1];
            if (token == null) {
                throw new InvalidTokenException("Empty token");
            }
        }
        return token;
    }
}
