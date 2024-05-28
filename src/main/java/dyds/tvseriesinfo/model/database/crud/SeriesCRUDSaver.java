package dyds.tvseriesinfo.model.database.crud;

import dyds.tvseriesinfo.model.database.DatabaseConnectionManager;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SeriesCRUDSaver extends SeriesCRUD {
    private static final String REPLACE_INTO_CATALOG = "replace into catalog values(null, ?, ?, 1)";
    public static final String ERROR_DATABASE_CONNECTION = "Error de conexión con la base de datos.";
    public static final String ERROR_SAVE_SERIES = "Error al guardar la serie.";
    private static SeriesCRUDSaver instance;

    private SeriesCRUDSaver() {
        super();
    }

    public static synchronized SeriesCRUDSaver getInstance() {
        if (instance == null) {
            instance = new SeriesCRUDSaver();
        }
        return instance;
    }

    public void saveSeries(String title, String extract) throws SeriesSaveException {
        doSaveSeries(title, extract);
        notifyListenersSuccess(OperationType.SAVE);
    }

    public void saveChangesSeries(String title, String extract) throws SeriesSaveException {
        doSaveSeries(title, extract);
        notifyListenersSuccess(OperationType.SAVE_CHANGES);
    }

    private synchronized void doSaveSeries(String title, String extract) throws SeriesSaveException {
        try (Connection connection = DatabaseConnectionManager.createConnection()) {
            executeSaveSeries(title, extract, connection);
        } catch (SQLException e) {
            throw new SeriesSaveException(ERROR_DATABASE_CONNECTION);
        }
        catch (Exception e) {
            throw new SeriesSaveException(ERROR_SAVE_SERIES);
        }

    }

    private void executeSaveSeries(String title, String extract, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(REPLACE_INTO_CATALOG)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, extract);
            preparedStatement.executeUpdate();
        }
    }
}
