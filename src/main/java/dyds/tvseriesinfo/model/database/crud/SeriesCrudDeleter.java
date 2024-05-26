package dyds.tvseriesinfo.model.database.crud;

import dyds.tvseriesinfo.model.database.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SeriesCrudDeleter extends SeriesCrudDatabase {

    public SeriesCrudDeleter() {
        super();
    }

    public void deleteSeriesByTitle(String title) {

        Connection connection = null;
        try {
            connection = DatabaseConnectionManager.createConnection();
            connection = DatabaseConnectionManager.createConnection();
            Statement statement = DatabaseConnectionManager.createStatement(connection);

            statement.executeUpdate("DELETE FROM catalog WHERE title = '" + title + "'");

        } catch (SQLException e) {
            System.err.println("Get title error " + e.getMessage());
        } finally {
            DatabaseConnectionManager.closeConnection(connection);
        }

        notifyListenersSuccess(OperationType.DELETE);
    }
}
