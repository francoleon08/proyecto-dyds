package dyds.tvseriesinfo.model.database.crud.series;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLDelete;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCRUD;
import dyds.tvseriesinfo.model.exceptions.SeriesDeleteException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;

public class SeriesCRUDDeleter extends SeriesCRUD {
    private static SeriesCRUDDeleter instance;
    private SeriesCRUDGetter seriesGetter;

    private SeriesCRUDDeleter() {
        super();
        seriesGetter = SeriesCRUDGetter.getInstance();
    }

    public static synchronized SeriesCRUDDeleter getInstance() {
        if (instance == null) {
            instance = new SeriesCRUDDeleter();
        }
        return instance;
    }

    public synchronized void deleteSeriesByTitle(String title) throws SeriesDeleteException, SeriesSearchException {
        SQLDelete.deleteSeriesByTitle(title);
        notifyListenersSuccess(OperationType.DELETE);
        seriesGetter.getTitlesSeries();
    }
}
