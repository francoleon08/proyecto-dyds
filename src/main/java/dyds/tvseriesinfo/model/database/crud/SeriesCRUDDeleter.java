package dyds.tvseriesinfo.model.database.crud;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLDelete;
import dyds.tvseriesinfo.model.exceptions.SeriesDeleteException;

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

    public synchronized void deleteSeriesByTitle(String title) throws SeriesDeleteException {
        SQLDelete.deleteSeriesByTitle(title);
        notifyListenersSuccess(OperationType.DELETE);
    }
}
