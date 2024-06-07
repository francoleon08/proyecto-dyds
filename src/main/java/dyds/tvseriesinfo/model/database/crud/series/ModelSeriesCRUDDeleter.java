package dyds.tvseriesinfo.model.database.crud.series;

import dyds.tvseriesinfo.model.database.crud.ModelSeriesCRUD;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.repository.SeriesRepository;
import dyds.tvseriesinfo.model.database.repository.SeriesRepositoryImpl;
import dyds.tvseriesinfo.model.exceptions.SeriesDeleteException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import lombok.Setter;

public class ModelSeriesCRUDDeleter extends ModelSeriesCRUD {
    private static ModelSeriesCRUDDeleter instance;
    @Setter
    private SeriesRepository seriesRepository;

    private ModelSeriesCRUDDeleter() {
        super();
        seriesRepository = new SeriesRepositoryImpl();
    }

    public static synchronized ModelSeriesCRUDDeleter getInstance() {
        if (instance == null) {
            instance = new ModelSeriesCRUDDeleter();
        }
        return instance;
    }

    public synchronized void deleteSeriesByTitle(String title) throws SeriesDeleteException, SeriesSearchException {
        seriesRepository.deleteSeriesByTitle(title);
        notifyListenersSuccess(OperationType.DELETE_SERIES);
        notifyListenersSuccess(OperationType.LOAD_LOCAL_SERIES_AFTER_CHANGE);
    }
}
