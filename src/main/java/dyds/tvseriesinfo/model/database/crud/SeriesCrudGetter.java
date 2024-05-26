package dyds.tvseriesinfo.model.database.crud;

import dyds.tvseriesinfo.model.database.DatabaseConnectionManager;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;

public class SeriesCrudGetter extends SeriesCrudDatabase {

    private static final String SELECT_FROM_CATALOG = "select * from catalog";
    private static final String SELECT_FROM_CATALOG_WHERE_TITLE = "select * from catalog WHERE title = ?";
    private static final String COLUMN_LABEL_EXTRACT = "extract";
    @Getter
    private ArrayList<String> lastTitlesSeries;
    @Getter
    private String lastSeriesExtactByTitle;
    private static SeriesCrudGetter instance;

    private SeriesCrudGetter() {
        super();
        lastTitlesSeries = new ArrayList<>();
    }

    public static synchronized SeriesCrudGetter getInstance() {
        if (instance == null) {
            instance = new SeriesCrudGetter();
        }
        return instance;
    }

    public synchronized void getTitlesSeries() {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            lastTitlesSeries = executeGetterSeries(connection);
            notifyListenersSuccess(OperationType.GET);
        } catch (SQLException e) {
            System.err.println("Get titles error " + e.getMessage());
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

    public void getExtractSeriesByTitle(String title) {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            lastSeriesExtactByTitle = executeGetExtractSeriesByTitle(title, connection);
            notifyListenersSuccess(OperationType.GET);
        } catch (SQLException e) {
            System.err.println("Get title error " + e.getMessage());
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
