package dyds.tvseriesinfo.model.database.repository;

import dyds.tvseriesinfo.model.exceptions.DatabaseSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionManager {
    private static final String DATABASE_URL = "jdbc:sqlite:./dictionary.db";
    private static final int QUERY_TIMEOUT = 30;
    private static final String DATABASE_CATALOG = "CREATE TABLE IF NOT EXISTS catalog (title TEXT PRIMARY KEY, extract TEXT)";
    private static final String DATABASE_PUNTACTION = "CREATE TABLE IF NOT EXISTS rated_series (title TEXT PRIMARY KEY, puntaction INTEGER, rating_date TEXT, rating_time TEXT)";
    private static final String ERROR_CREATING_DATABASE_CONNECTION = "Error creating database connection: ";

    public static Connection createConnection() throws DatabaseSQLException {
        try {
            return DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            throw new DatabaseSQLException(ERROR_CREATING_DATABASE_CONNECTION + e.getMessage());
        }
    }

    public static Statement createStatement(Connection connection) throws DatabaseSQLException {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(QUERY_TIMEOUT);
            return statement;
        } catch (SQLException e) {
            throw new DatabaseSQLException(ERROR_CREATING_DATABASE_CONNECTION + e.getMessage());
        }
    }

    public static void initializeDatabases() throws DatabaseSQLException {
        initializeDatabaseCatalog(DATABASE_CATALOG);
        initializeDatabaseCatalog(DATABASE_PUNTACTION);
    }

    private static void initializeDatabaseCatalog(String databaseName) throws DatabaseSQLException {
        try (Connection connection = createConnection();
             Statement statement = createStatement(connection)) {
            statement.executeUpdate(databaseName);
        } catch (SQLException e) {
            throw new DatabaseSQLException(ERROR_CREATING_DATABASE_CONNECTION + e.getMessage());
        }
    }
}
