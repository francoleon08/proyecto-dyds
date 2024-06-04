package dyds.tvseriesinfo.model.database.SQLmanager.SQLSelect;

import dyds.tvseriesinfo.model.database.SQLmanager.DatabaseConnectionManager;
import dyds.tvseriesinfo.model.exceptions.DatabaseSQLException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLSelectExtractByTitle extends SQLSelect {

    public static String getExtractSeriesByTitle(String title) throws SeriesSearchException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            return executeGetExtractSeriesByTitle(title, connection);
        } catch (SQLException | DatabaseSQLException e) {
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
}
