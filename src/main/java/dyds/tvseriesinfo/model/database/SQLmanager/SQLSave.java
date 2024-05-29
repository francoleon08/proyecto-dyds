package dyds.tvseriesinfo.model.database.SQLmanager;

import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLSave {
    private static final String REPLACE_INTO_CATALOG = "replace into catalog values(null, ?, ?, 1)";
    public static final String ERROR_DATABASE_CONNECTION = "Error de conexión con la base de datos.";
    public static final String ERROR_SAVE_SERIES = "Error al guardar la serie.";

    public static void saveSeries(String title, String extract) throws SeriesSaveException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            executeSaveSeries(title, extract, connection);
        } catch (SQLException e) {
            throw new SeriesSaveException(ERROR_DATABASE_CONNECTION);
        }
        catch (Exception e) {
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
