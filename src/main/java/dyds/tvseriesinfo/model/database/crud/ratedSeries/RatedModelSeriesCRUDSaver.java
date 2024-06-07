package dyds.tvseriesinfo.model.database.crud.ratedSeries;

import dyds.tvseriesinfo.model.database.crud.ModelSeriesCRUD;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.repository.SeriesRepository;
import dyds.tvseriesinfo.model.database.repository.SeriesRepositoryImpl;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;
import lombok.Setter;

public class RatedModelSeriesCRUDSaver extends ModelSeriesCRUD {
    private final RatedModelSeriesCRUDGetter seriesGetter;
    private static RatedModelSeriesCRUDSaver instance;
    @Setter
    private SeriesRepository seriesRepository;

    private RatedModelSeriesCRUDSaver() {
        super();
        seriesGetter = RatedModelSeriesCRUDGetter.getInstance();
        seriesRepository = new SeriesRepositoryImpl();
    }

    public static synchronized RatedModelSeriesCRUDSaver getInstance() {
        if (instance == null) {
            instance = new RatedModelSeriesCRUDSaver();
        }
        return instance;
    }

    public synchronized void saveRatedSeries(String title, int rated) throws SeriesSaveException, SearchRatedSeriesException {
        seriesRepository.savePuntuaction(title, rated);
        notifyListenersSuccess(OperationType.SAVE_RATED);
        seriesGetter.getRatedSeries();
    }
}
