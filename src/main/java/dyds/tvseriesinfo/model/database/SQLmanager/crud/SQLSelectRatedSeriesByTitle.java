package dyds.tvseriesinfo.model.database.SQLmanager.crud;

import dyds.tvseriesinfo.model.database.SQLmanager.DatabaseConnectionManager;
import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.DatabaseSQLException;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLSelectRatedSeriesByTitle extends SQLSelect {

    public static RatedSeries getRatedSeriesByTitle(String title) throws SearchRatedSeriesException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            return executeGetRatedSeriesByTitle(title, connection);
        } catch (SQLException | DatabaseSQLException e) {
            throw new SearchRatedSeriesException(ERROR_DATABASE_CONNECTION);
        }
    }

    private static RatedSeries executeGetRatedSeriesByTitle(String title, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_RATED_SERIES_BY_TITLE)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return createRatedSeriesOfResultSet(resultSet);
        }
    }
}
