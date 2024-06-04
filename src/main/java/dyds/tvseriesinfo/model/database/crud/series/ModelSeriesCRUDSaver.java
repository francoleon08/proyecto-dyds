package dyds.tvseriesinfo.model.database.crud.series;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLInsert;
import dyds.tvseriesinfo.model.database.crud.ModelSeriesCRUD;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;

public class ModelSeriesCRUDSaver extends ModelSeriesCRUD {
    private static ModelSeriesCRUDSaver instance;

    private ModelSeriesCRUDSaver() {
        super();
    }

    public static synchronized ModelSeriesCRUDSaver getInstance() {
        if (instance == null) {
            instance = new ModelSeriesCRUDSaver();
        }
        return instance;
    }

    public synchronized void saveSeries(String title, String extract) throws SeriesSaveException, SeriesSearchException {
        SQLInsert.saveSeries(title, extract);
        notifyListenersSuccess(OperationType.SAVE_SERIES);
        notifyListenersSuccess(OperationType.LOAD_LOCAL_SERIES_AFTER_CHANGE);
    }

    public synchronized void saveChangesSeries(String title, String extract) throws SeriesSaveException {
        SQLInsert.saveSeries(title, extract);
        notifyListenersSuccess(OperationType.SAVE_CHANGES);
    }
}
