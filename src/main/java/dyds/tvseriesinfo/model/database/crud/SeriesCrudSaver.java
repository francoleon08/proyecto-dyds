package dyds.tvseriesinfo.model.database.crud;

import dyds.tvseriesinfo.model.database.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SeriesCrudSaver extends SeriesCrudDatabase {

    public SeriesCrudSaver() {
        super();
    }

    public void saveSeries(String title, String extract, OperationType operationType) {
        Connection connection = null;
        try {
            connection = DatabaseConnectionManager.createConnection();
            Statement statement = DatabaseConnectionManager.createStatement(connection);

            statement.executeUpdate("replace into catalog values(null, '" + title + "', '" + extract + "', 1)");

            notifyListenersSuccess(operationType);
        } catch (SQLException e) {
            System.err.println("Error saving " + e.getMessage());
        } finally {
            DatabaseConnectionManager.closeConnection(connection);
        }

    }
}
