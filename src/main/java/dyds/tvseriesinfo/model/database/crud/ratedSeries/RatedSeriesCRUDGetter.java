package dyds.tvseriesinfo.model.database.crud.ratedSeries;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLSelect;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCRUD;
import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class RatedSeriesCRUDGetter extends SeriesCRUD {
    @Getter
    private ArrayList<RatedSeries> lastRatedSeries;
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
        orderRatedSeriesByRating();
        notifyListenersSuccess(OperationType.LOAD_RATED_SERIES);
    }

    private void orderRatedSeriesByRating() {
        lastRatedSeries = lastRatedSeries.stream().sorted(Comparator.comparingInt(RatedSeries::getRating)).collect(Collectors.toCollection(ArrayList::new));
    }
}
