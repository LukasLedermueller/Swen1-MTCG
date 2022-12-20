package at.fhtw.mtcg.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DatabaseManager {
    INSTANCE;

    public Connection getConnection()
    {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/swe1db",
                    "swe1user",
                    "swe1pw");
        } catch (SQLException e) {
            throw new DataAccessException("Can't connect to database", e);
        }
    }
}
