package dyds.tvseriesinfo.model.database.crud.ratedSeries;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLCRUD;
import dyds.tvseriesinfo.model.database.SQLmanager.SQLCRUDImpl;
import dyds.tvseriesinfo.model.database.SQLmanager.SQLInsert;
import dyds.tvseriesinfo.model.database.crud.ModelSeriesCRUD;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;
import lombok.Setter;

public class RatedModelSeriesCRUDSaver extends ModelSeriesCRUD {
    private final RatedModelSeriesCRUDGetter seriesGetter;
    private static RatedModelSeriesCRUDSaver instance;
    @Setter
    private SQLCRUD sqlCRUD;

    private RatedModelSeriesCRUDSaver() {
        super();
        seriesGetter = RatedModelSeriesCRUDGetter.getInstance();
        sqlCRUD = new SQLCRUDImpl();
    }

    public static synchronized RatedModelSeriesCRUDSaver getInstance() {
        if (instance == null) {
            instance = new RatedModelSeriesCRUDSaver();
        }
        return instance;
    }

    public synchronized void saveRatedSeries(String title, int rated) throws SeriesSaveException, SearchRatedSeriesException {
        sqlCRUD.savePuntuaction(title, rated);
        notifyListenersSuccess(OperationType.SAVE_RATED);
        seriesGetter.getRatedSeries();
    }
}
