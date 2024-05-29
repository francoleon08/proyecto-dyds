package dyds.tvseriesinfo.model.database.SQLmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionManager {
    private static final String DATABASE_URL = "jdbc:sqlite:./dictionary.db";
    private static final int QUERY_TIMEOUT = 30;

    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            return null;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing the database connection: " + e.getMessage());
            }
        }
    }

    public static Statement createStatement(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(QUERY_TIMEOUT);
            return statement;
        } catch (SQLException e) {
            System.err.println("Error creating statement: " + e.getMessage());
            return null;
        }
    }

    public static void initializeDatabase() {
        try (Connection connection = createConnection();
             Statement statement = createStatement(connection)) {
            if (statement != null) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS catalog (id INTEGER PRIMARY KEY, title TEXT, extract TEXT, source INTEGER)");
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}
