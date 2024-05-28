package dyds.tvseriesinfo.model.database.crud;

import dyds.tvseriesinfo.model.database.DatabaseConnectionManager;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;

public class SeriesCRUDGetter extends SeriesCRUD {

    private static final String SELECT_FROM_CATALOG = "select * from catalog";
    private static final String SELECT_FROM_CATALOG_WHERE_TITLE = "select * from catalog WHERE title = ?";
    private static final String COLUMN_LABEL_EXTRACT = "extract";
    public static final String ERROR_DATABASE_CONNECTION = "Error de conexión con la base de datos.";
    @Getter
    private ArrayList<String> lastTitlesSeries;
    @Getter
    private String lastSeriesExtactByTitle;
    private static SeriesCRUDGetter instance;

    private SeriesCRUDGetter() {
        super();
        lastTitlesSeries = new ArrayList<>();
    }

    public static synchronized SeriesCRUDGetter getInstance() {
        if (instance == null) {
            instance = new SeriesCRUDGetter();
        }
        return instance;
    }

    public synchronized void getTitlesSeries() throws SeriesSearchException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            lastTitlesSeries = executeGetterSeries(connection);
            notifyListenersSuccess(OperationType.GET);
        } catch (SQLException e) {
            throw new SeriesSearchException(ERROR_DATABASE_CONNECTION);
        }
    }

    private ArrayList<String> executeGetterSeries(Connection connection) throws SQLException {
        ArrayList<String> titles = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_FROM_CATALOG);
            while (resultSet.next())
                titles.add(resultSet.getString("title"));
            return titles;
        }
    }

    public void getExtractSeriesByTitle(String title) throws SeriesSearchException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            lastSeriesExtactByTitle = executeGetExtractSeriesByTitle(title, connection);
            notifyListenersSuccess(OperationType.GET);
        } catch (SQLException e) {
            throw new SeriesSearchException(ERROR_DATABASE_CONNECTION);
        }
    }

    private String executeGetExtractSeriesByTitle(String title, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_CATALOG_WHERE_TITLE)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString(COLUMN_LABEL_EXTRACT);
        }
    }
}
