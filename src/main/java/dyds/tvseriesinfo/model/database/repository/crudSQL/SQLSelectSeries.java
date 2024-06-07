package dyds.tvseriesinfo.model.database.repository.crudSQL;

import dyds.tvseriesinfo.model.database.repository.DatabaseConnectionManager;
import dyds.tvseriesinfo.model.exceptions.DatabaseSQLException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLSelectSeries extends SQLSelect {

    public static ArrayList<String> getTitlesSeries() throws SeriesSearchException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            return executeGetterSeries(connection, SELECT_FROM_CATALOG);
        } catch (SQLException | DatabaseSQLException e) {
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
}
