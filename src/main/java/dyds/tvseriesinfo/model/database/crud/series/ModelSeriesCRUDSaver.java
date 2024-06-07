package dyds.tvseriesinfo.model.database.crud.series;

import dyds.tvseriesinfo.model.database.crud.ModelSeriesCRUD;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.repository.SeriesRepository;
import dyds.tvseriesinfo.model.database.repository.SeriesRepositoryImpl;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import lombok.Setter;

public class ModelSeriesCRUDSaver extends ModelSeriesCRUD {
    private static ModelSeriesCRUDSaver instance;
    @Setter
    private SeriesRepository seriesRepository;

    private ModelSeriesCRUDSaver() {
        super();
        seriesRepository = new SeriesRepositoryImpl();
    }

    public static synchronized ModelSeriesCRUDSaver getInstance() {
        if (instance == null) {
            instance = new ModelSeriesCRUDSaver();
        }
        return instance;
    }

    public synchronized void saveSeries(String title, String extract) throws SeriesSaveException, SeriesSearchException {
        seriesRepository.saveSeries(title, extract);
        notifyListenersSuccess(OperationType.SAVE_SERIES);
        notifyListenersSuccess(OperationType.LOAD_LOCAL_SERIES_AFTER_CHANGE);
    }

    public synchronized void saveChangesSeries(String title, String extract) throws SeriesSaveException {
        seriesRepository.saveSeries(title, extract);
        notifyListenersSuccess(OperationType.SAVE_CHANGES);
    }
}
