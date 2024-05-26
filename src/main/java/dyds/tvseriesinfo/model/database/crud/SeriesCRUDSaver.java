package dyds.tvseriesinfo.model.database.crud;

import dyds.tvseriesinfo.model.database.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SeriesCRUDSaver extends SeriesCRUD {
    private static final String REPLACE_INTO_CATALOG = "replace into catalog values(null, ?, ?, 1)";
    private static SeriesCRUDSaver instance;

    private SeriesCRUDSaver() {
        super();
    }

    public static synchronized SeriesCRUDSaver getInstance() {
        if (instance == null) {
            instance = new SeriesCRUDSaver();
        }
        return instance;
    }

    public synchronized void saveSeries(String title, String extract, OperationType operationType) {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            executeSaveSeries(title, extract, connection);
            notifyListenersSuccess(operationType);
        } catch (SQLException e) {
            System.err.println("Error saving " + e.getMessage());
        }

    }

    private void executeSaveSeries(String title, String extract, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(REPLACE_INTO_CATALOG)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, extract);
            preparedStatement.executeUpdate();
        }
    }
}