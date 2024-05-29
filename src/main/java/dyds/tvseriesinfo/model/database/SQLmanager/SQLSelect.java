package dyds.tvseriesinfo.model.database.SQLmanager;

import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import java.sql.*;
import java.util.ArrayList;

public class SQLSelect {
    private static final String SELECT_FROM_CATALOG = "select * from catalog";
    private static final String SELECT_FROM_CATALOG_WHERE_TITLE = "select * from catalog WHERE title = ?";
    private static final String COLUMN_LABEL_EXTRACT = "extract";
    public static final String ERROR_DATABASE_CONNECTION = "Error de conexión con la base de datos.";

    public static ArrayList<String> getTitlesSeries() throws SeriesSearchException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            return executeGetterSeries(connection);
        } catch (SQLException e) {
            throw new SeriesSearchException(ERROR_DATABASE_CONNECTION);
        }
    }

    private static ArrayList<String> executeGetterSeries(Connection connection) throws SQLException {
        ArrayList<String> titles = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_FROM_CATALOG);
            while (resultSet.next())
                titles.add(resultSet.getString("title"));
            return titles;
        }
    }

    public static String getExtractSeriesByTitle(String title) throws SeriesSearchException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            return executeGetExtractSeriesByTitle(title, connection);
        } catch (SQLException e) {
            throw new SeriesSearchException(ERROR_DATABASE_CONNECTION);
        }
    }

    private static String executeGetExtractSeriesByTitle(String title, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_CATALOG_WHERE_TITLE)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString(COLUMN_LABEL_EXTRACT);
        }
    }
}
