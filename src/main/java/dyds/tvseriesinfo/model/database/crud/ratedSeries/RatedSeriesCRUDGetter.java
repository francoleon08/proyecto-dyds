package dyds.tvseriesinfo.model.database.crud.ratedSeries;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLSelect;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCRUD;
import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import lombok.Getter;

import java.util.ArrayList;

public class RatedSeriesCRUDGetter extends SeriesCRUD {
    @Getter
    private ArrayList<String> lastRatedSeries;
    @Getter
    private RatedSeries lastRatedSerieByTitle;
    private static RatedSeriesCRUDGetter instance;

    private RatedSeriesCRUDGetter() {
        super();
        lastRatedSeries = new ArrayList<>();
    }

    public static synchronized RatedSeriesCRUDGetter getInstance() {
        if (instance == null) {
            instance = new RatedSeriesCRUDGetter();
        }
        return instance;
    }

    public void getRatedSeries() throws SearchRatedSeriesException {
        lastRatedSeries = SQLSelect.getRatedSeries();
        notifyListenersSuccess(OperationType.LOAD_RATED_SERIES);
    }

    public void getRatedSeriesByTitle(String title) throws SearchRatedSeriesException {
        lastRatedSerieByTitle = SQLSelect.getRatedSeriesByTitle(title);
        notifyListenersSuccess(OperationType.GET_RATED_SERIES);
    }
}
