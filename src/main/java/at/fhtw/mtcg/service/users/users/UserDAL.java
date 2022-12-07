package at.fhtw.mtcg.service.users.users;

import at.fhtw.mtcg.model.UserCredentials;

import java.sql.*;

public class UserDAL {
    public static boolean addUserCredentials(UserCredentials userCredentials) {

        //hier connection aufbauen
        //unit of work für jede transaction - vielzahl an statements, die als ganzes fungieren, kleinste einheit, damit Daten konsistent bleiben

        System.out.println(userCredentials.getUsername() + " : " + userCredentials.getPassword());
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/swe1db", "swe1user", "swe1pw");
             PreparedStatement statement1 = connection.prepareStatement("""
                    SELECT COUNT(*) FROM users
                    WHERE username = (?);
                    """);
             PreparedStatement statement2 = connection.prepareStatement("""
                    INSERT INTO users (username, password)
                    VALUES(?,?);
                    """);
        )
        {
            statement1.setString(1, userCredentials.getUsername());
            ResultSet set1 = statement1.executeQuery();
            set1.next();
            if(set1.getInt(1) > 0){
                return false;
            } else {
                statement2.setString(1, userCredentials.getUsername());
                statement2.setString(2, userCredentials.getPassword());
                boolean check = statement2.execute();
            }

        } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
        }
        return true;
    }
}
