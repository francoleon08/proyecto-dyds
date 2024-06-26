package dyds.tvseriesinfo.model.database.crud.ratedSeries;

import dyds.tvseriesinfo.model.database.crud.ModelSeriesCRUD;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.repository.SeriesRepository;
import dyds.tvseriesinfo.model.database.repository.SeriesRepositoryImpl;
import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class RatedModelSeriesCRUDGetter extends ModelSeriesCRUD {
    @Getter
    private ArrayList<RatedSeries> lastRatedSeries;
    @Getter
    private RatedSeries lastRatedSerieByTitle;
    private static RatedModelSeriesCRUDGetter instance;
    @Setter
    private SeriesRepository seriesRepository;

    private RatedModelSeriesCRUDGetter() {
        super();
        lastRatedSeries = new ArrayList<>();
        seriesRepository = new SeriesRepositoryImpl();
    }

    public static synchronized RatedModelSeriesCRUDGetter getInstance() {
        if (instance == null) {
            instance = new RatedModelSeriesCRUDGetter();
        }
        return instance;
    }

    public void getRatedSeries() throws SearchRatedSeriesException {
        lastRatedSeries = seriesRepository.getRatedSeries();
        orderRatedSeriesByRating();
        notifyListenersSuccess(OperationType.LOAD_RATED_SERIES);
    }

    private void orderRatedSeriesByRating() {
        lastRatedSeries = lastRatedSeries.stream().sorted(
                        Comparator.comparingInt(RatedSeries::getRating))
                .collect(Collectors.toCollection(ArrayList::new)
                );
    }
}
