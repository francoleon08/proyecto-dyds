package dyds.tvseriesinfo.model.database.crud.series;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLDelete;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.ModelSeriesCRUD;
import dyds.tvseriesinfo.model.exceptions.SeriesDeleteException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;

public class ModelSeriesCRUDDeleter extends ModelSeriesCRUD {
    private static ModelSeriesCRUDDeleter instance;

    private ModelSeriesCRUDDeleter() {
        super();
    }

    public static synchronized ModelSeriesCRUDDeleter getInstance() {
        if (instance == null) {
            instance = new ModelSeriesCRUDDeleter();
        }
        return instance;
    }

    public synchronized void deleteSeriesByTitle(String title) throws SeriesDeleteException, SeriesSearchException {
        SQLDelete.deleteSeriesByTitle(title);
        notifyListenersSuccess(OperationType.DELETE_SERIES);
        notifyListenersSuccess(OperationType.LOAD_LOCAL_SERIES_AFTER_CHANGE);
    }
}
