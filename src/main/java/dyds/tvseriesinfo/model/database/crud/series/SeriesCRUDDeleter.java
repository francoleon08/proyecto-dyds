package dyds.tvseriesinfo.model.database.crud.series;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLDelete;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCRUD;
import dyds.tvseriesinfo.model.exceptions.SeriesDeleteException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;

public class SeriesCRUDDeleter extends SeriesCRUD {
    private static SeriesCRUDDeleter instance;

    private SeriesCRUDDeleter() {
        super();
    }

    public static synchronized SeriesCRUDDeleter getInstance() {
        if (instance == null) {
            instance = new SeriesCRUDDeleter();
        }
        return instance;
    }

    public synchronized void deleteSeriesByTitle(String title) throws SeriesDeleteException, SeriesSearchException {
        SQLDelete.deleteSeriesByTitle(title);
        notifyListenersSuccess(OperationType.DELETE_SERIES);
        notifyListenersSuccess(OperationType.LOAD_LOCAL_SERIES_AFTER_CHANGE);
    }
}
