package dyds.tvseriesinfo.model.database.crud.ratedSeries;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLInsert;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCRUD;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;

public class RatedSeriesCRUDSaver extends SeriesCRUD {
    private RatedSeriesCRUDGetter seriesGetter;
    private static RatedSeriesCRUDSaver instance;

    private RatedSeriesCRUDSaver() {
        super();
        seriesGetter = RatedSeriesCRUDGetter.getInstance();
    }

    public static synchronized RatedSeriesCRUDSaver getInstance() {
        if (instance == null) {
            instance = new RatedSeriesCRUDSaver();
        }
        return instance;
    }

    public synchronized void saveRatedSeries(String title, int puntuaction) throws SeriesSaveException, SearchRatedSeriesException {
        SQLInsert.savePuntuaction(title, puntuaction);
        notifyListenersSuccess(OperationType.SAVE_RATED);
        seriesGetter.getRatedSeries();
    }
}
