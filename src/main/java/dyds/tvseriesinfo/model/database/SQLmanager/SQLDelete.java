package dyds.tvseriesinfo.model.database.SQLmanager;

import dyds.tvseriesinfo.model.exceptions.SeriesDeleteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLDelete {
    private static final String DELETE_FROM_CATALOG_WHERE_TITLE = "DELETE FROM catalog WHERE title = ?";
    public static final String ERROR_DELETE_SERIES = "Error al eliminar la serie";

    public static void deleteSeriesByTitle(String title) throws SeriesDeleteException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            executeUpdateSeries(title, connection);
        } catch (SQLException e) {
            throw new SeriesDeleteException(ERROR_DELETE_SERIES);
        }
    }

    private static void executeUpdateSeries(String title, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_CATALOG_WHERE_TITLE)) {
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();
        }
    }
}