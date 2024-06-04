package dyds.tvseriesinfo.model.database.crud.ratedSeries;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLInsert;
import dyds.tvseriesinfo.model.database.crud.ModelSeriesCRUD;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;

public class RatedModelSeriesCRUDSaver extends ModelSeriesCRUD {
    private final RatedModelSeriesCRUDGetter seriesGetter;
    private static RatedModelSeriesCRUDSaver instance;

    private RatedModelSeriesCRUDSaver() {
        super();
        seriesGetter = RatedModelSeriesCRUDGetter.getInstance();
    }

    public static synchronized RatedModelSeriesCRUDSaver getInstance() {
        if (instance == null) {
            instance = new RatedModelSeriesCRUDSaver();
        }
        return instance;
    }

    public synchronized void saveRatedSeries(String title, int rated) throws SeriesSaveException, SearchRatedSeriesException {
        SQLInsert.savePuntuaction(title, rated);
        notifyListenersSuccess(OperationType.SAVE_RATED);
        seriesGetter.getRatedSeries();
    }
}
