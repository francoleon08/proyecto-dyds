package dyds.tvseriesinfo.model.database.crud;

import dyds.tvseriesinfo.model.database.DatabaseConnectionManager;
import dyds.tvseriesinfo.model.exceptions.SeriesDeleteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SeriesCRUDDeleter extends SeriesCRUD {
    private static final String DELETE_FROM_CATALOG_WHERE_TITLE = "DELETE FROM catalog WHERE title = ?";
    public static final String ERROR_DELETE_SERIES = "Error al eliminar la serie";
    private static SeriesCRUDDeleter instance;

    private SeriesCRUDDeleter() {
        super();
    }

    public static synchronized SeriesCRUDDeleter getInstance() {
        if (instance == null) {
            instance = new SeriesCRUDDeleter();
        }
        return instance;
    }

    public synchronized void deleteSeriesByTitle(String title) throws SeriesDeleteException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            executeUpdateSeries(title, connection);
            notifyListenersSuccess(OperationType.DELETE);
        } catch (SQLException e) {
            throw new SeriesDeleteException(ERROR_DELETE_SERIES);
        }
    }

    private void executeUpdateSeries(String title, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_CATALOG_WHERE_TITLE)) {
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();
        }
    }
}
