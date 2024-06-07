package dyds.tvseriesinfo.model.database.repository.crudSQL;

import dyds.tvseriesinfo.model.database.repository.DatabaseConnectionManager;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;

import java.sql.*;

public class SQLInsert {
    private static final String REPLACE_INTO_CATALOG = "replace into catalog values(?, ?)";
    private static final String REPLACE_INTO_RATED_SERIES = "replace into rated_series values(?,?,?,?)";
    private static final String ERROR_DATABASE_CONNECTION = "Database connection error.";
    private static final String ERROR_SAVE_SERIES = "Error saving the series.";

    public static void savePuntuaction(String title, int puntuaction) throws SeriesSaveException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            executeSavePuntuaction(title, puntuaction, connection);
        } catch (SQLException e) {
            throw new SeriesSaveException(ERROR_DATABASE_CONNECTION + e.getMessage());
        } catch (Exception e) {
            throw new SeriesSaveException(ERROR_SAVE_SERIES);
        }
    }

    private static void executeSavePuntuaction(String title, int puntuaction, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(REPLACE_INTO_RATED_SERIES)) {
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, puntuaction);
            preparedStatement.setString(3, Date.valueOf(java.time.LocalDate.now()).toString());
            preparedStatement.setString(4, Time.valueOf(java.time.LocalTime.now()).toString());
            preparedStatement.executeUpdate();
        }
    }

    public static void saveSeries(String title, String extract) throws SeriesSaveException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            executeSaveSeries(title, extract, connection);
        } catch (SQLException e) {
            throw new SeriesSaveException(ERROR_DATABASE_CONNECTION);
        } catch (Exception e) {
            throw new SeriesSaveException(ERROR_SAVE_SERIES);
        }
    }

    private static void executeSaveSeries(String title, String extract, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(REPLACE_INTO_CATALOG)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, extract);
            preparedStatement.executeUpdate();
        }
    }
}
