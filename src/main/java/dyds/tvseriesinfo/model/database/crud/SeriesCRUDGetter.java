package dyds.tvseriesinfo.model.database.crud;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLSelect;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import lombok.Getter;

import java.util.ArrayList;

public class SeriesCRUDGetter extends SeriesCRUD {
    @Getter
    private ArrayList<String> lastTitlesSeries;
    @Getter
    private String lastSeriesExtactByTitle;
    private static SeriesCRUDGetter instance;

    private SeriesCRUDGetter() {
        super();
        lastTitlesSeries = new ArrayList<>();
    }

    public static synchronized SeriesCRUDGetter getInstance() {
        if (instance == null) {
            instance = new SeriesCRUDGetter();
        }
        return instance;
    }

    public synchronized void getTitlesSeries() throws SeriesSearchException {
        lastTitlesSeries = SQLSelect.getTitlesSeries();
        notifyListenersSuccess(OperationType.LOAD_LOCAL_SERIES);
    }

    public synchronized void getExtractSeriesByTitle(String title) throws SeriesSearchException {
        lastSeriesExtactByTitle = SQLSelect.getExtractSeriesByTitle(title);
        notifyListenersSuccess(OperationType.GET);
    }
}
