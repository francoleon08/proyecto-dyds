package dyds.tvseriesinfo.model.database.crud;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLSave;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;

public class SeriesCRUDSaver extends SeriesCRUD {
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

    public synchronized void saveSeries(String title, String extract) throws SeriesSaveException {
        SQLSave.saveSeries(title, extract);
        notifyListenersSuccess(OperationType.SAVE);
    }

    public synchronized void saveChangesSeries(String title, String extract) throws SeriesSaveException {
        SQLSave.saveSeries(title, extract);
        notifyListenersSuccess(OperationType.SAVE_CHANGES);
    }
}
