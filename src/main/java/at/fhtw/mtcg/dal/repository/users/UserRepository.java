package at.fhtw.mtcg.dal.repository.users;

import at.fhtw.mtcg.dal.DataAccessException;
import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.model.UserCredentials;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository {

    public UserRepository(){}
    public boolean addUser(UserCredentials userCredentials) throws Exception {
        UnitOfWork unitOfWork;
        try {
            unitOfWork = new UnitOfWork();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        //System.out.println(userCredentials.getUsername() + " : " + userCredentials.getPassword());
        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""                  
                        INSERT INTO users (username, password)
                        VALUES (?,?);
                    """))
        {
            preparedStatement.setString(1, userCredentials.getUsername());
            preparedStatement.setString(2, userCredentials.getPassword());
            preparedStatement.execute();
            unitOfWork.commitTransaction();
        } catch (SQLException e) {
            if(e.getSQLState().equals("23505")) {
                System.out.println(e.getMessage());
                unitOfWork.rollbackTransaction();
                return false;
            }
            unitOfWork.rollbackTransaction();
            throw new Exception(e.getMessage());
        } catch (DataAccessException e) {
            unitOfWork.rollbackTransaction();
            throw new Exception(e.getMessage());
        }
        unitOfWork.finishWork();
        return true;
    }

    public void getUser(String username){
        return;
    }

    public void updateUser(/*UserData userData*/){
        return;
    }
}

