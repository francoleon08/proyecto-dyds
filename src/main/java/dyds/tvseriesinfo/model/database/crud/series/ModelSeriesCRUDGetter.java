package dyds.tvseriesinfo.model.database.crud.series;

import dyds.tvseriesinfo.model.database.crud.ModelSeriesCRUD;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.repository.SeriesRepository;
import dyds.tvseriesinfo.model.database.repository.SeriesRepositoryImpl;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class ModelSeriesCRUDGetter extends ModelSeriesCRUD {
    @Getter
    private ArrayList<String> lastTitlesSeries;
    @Getter
    private String lastSeriesExtactByTitle;
    private static ModelSeriesCRUDGetter instance;
    @Setter
    private SeriesRepository seriesRepository;

    private ModelSeriesCRUDGetter() {
        super();
        lastTitlesSeries = new ArrayList<>();
        seriesRepository = new SeriesRepositoryImpl();
    }

    public static synchronized ModelSeriesCRUDGetter getInstance() {
        if (instance == null) {
            instance = new ModelSeriesCRUDGetter();
        }
        return instance;
    }

    public synchronized void getTitlesSeries() throws SeriesSearchException {
        lastTitlesSeries = seriesRepository.getTitlesSeries();
        notifyListenersSuccess(OperationType.LOAD_LOCAL_SERIES);
    }

    public synchronized void getExtractSeriesByTitle(String title) throws SeriesSearchException {
        lastSeriesExtactByTitle = seriesRepository.getExtractSeriesByTitle(title);
        notifyListenersSuccess(OperationType.GET_SERIES);
    }
}
