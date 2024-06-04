package dyds.tvseriesinfo.model.database.SQLmanager;

import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;

import java.sql.*;
import java.util.ArrayList;

public class SQLSelect {
    private static final String SELECT_FROM_CATALOG = "select * from catalog";
    private static final String SELECT_FROM_CATALOG_BY_TITLE = "select * from catalog WHERE title = ?";
    private static final String SELECT_FROM_RATED_SERIES = "select * from rated_series";
    private static final String SELECT_FROM_RATED_SERIES_BY_TITLE = "select * from rated_series WHERE  title = ?";
    private static final String COLUMN_LABEL_EXTRACT = "extract";
    private static final String ERROR_DATABASE_CONNECTION = "Error de conexión con la base de datos.";

    public static ArrayList<String> getTitlesSeries() throws SeriesSearchException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            return executeGetterSeries(connection, SELECT_FROM_CATALOG);
        } catch (SQLException e) {
            throw new SeriesSearchException(ERROR_DATABASE_CONNECTION);
        }
    }

    private static ArrayList<String> executeGetterSeries(Connection connection, String query) throws SQLException {
        ArrayList<String> titles = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_CATALOG_BY_TITLE)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString(COLUMN_LABEL_EXTRACT);
        }
    }

    public static ArrayList<RatedSeries> getRatedSeries() throws SearchRatedSeriesException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            return executeGetterRatedSeries(connection, SELECT_FROM_RATED_SERIES);
        } catch (SQLException e) {
            throw new SearchRatedSeriesException(ERROR_DATABASE_CONNECTION);
        }
    }

    private static ArrayList<RatedSeries> executeGetterRatedSeries(Connection connection, String query) throws SQLException {
        ArrayList<RatedSeries> ratedSeries = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                RatedSeries ratedSerie = RatedSeries.builder()
                        .title(resultSet.getString("title"))
                        .rating(resultSet.getInt("puntaction"))
                        .dateModified(resultSet.getString("rating_date"))
                        .timeModified(resultSet.getString("rating_time"))
                        .build();
                ratedSeries.add(ratedSerie);
            }
            return ratedSeries;
        }
    }

    public static RatedSeries getRatedSeriesByTitle(String title) throws SearchRatedSeriesException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            return executeGetRatedSeriesByTitle(title, connection);
        } catch (SQLException e) {
            throw new SearchRatedSeriesException(ERROR_DATABASE_CONNECTION);
        }
    }

    private static RatedSeries executeGetRatedSeriesByTitle(String title, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_RATED_SERIES_BY_TITLE)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return RatedSeries.builder()
                    .title(resultSet.getString("title"))
                    .rating(resultSet.getInt("puntaction"))
                    .dateModified(resultSet.getString("rating_date"))
                    .timeModified(resultSet.getString("rating_time"))
                    .build();
        }
    }

    public static int setRatedSeriesByTitle(String title) {
        try {
            RatedSeries ratedSeries = getRatedSeriesByTitle(title);
            if (ratedSeries.getTitle() != null)
                return ratedSeries.getRating();
            return 0;
        } catch (SearchRatedSeriesException e) {
            return 0;
        }
    }
}
