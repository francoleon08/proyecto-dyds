package dyds.tvseriesinfo.model.database.SQLmanager.SQLSelect;

import dyds.tvseriesinfo.model.database.SQLmanager.DatabaseConnectionManager;
import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.DatabaseSQLException;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLSelectRatedSeries extends SQLSelect {
    public static ArrayList<RatedSeries> getRatedSeries() throws SearchRatedSeriesException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            return executeGetterRatedSeries(connection, SELECT_FROM_RATED_SERIES);
        } catch (SQLException | DatabaseSQLException e) {
            throw new SearchRatedSeriesException(ERROR_DATABASE_CONNECTION);
        }
    }

    private static ArrayList<RatedSeries> executeGetterRatedSeries(Connection connection, String query) throws SQLException {
        ArrayList<RatedSeries> ratedSeries = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ratedSeries.add(createRatedSeriesOfResultSet(resultSet));
            }
            return ratedSeries;
        }
    }
}
