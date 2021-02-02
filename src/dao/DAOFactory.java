package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:banana.db");
    }

}
