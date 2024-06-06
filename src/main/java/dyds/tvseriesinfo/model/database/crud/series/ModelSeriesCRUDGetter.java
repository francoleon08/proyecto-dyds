package dyds.tvseriesinfo.model.database.crud.series;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLCRUD;
import dyds.tvseriesinfo.model.database.SQLmanager.SQLCRUDImpl;
import dyds.tvseriesinfo.model.database.SQLmanager.SQLSelectManager;
import dyds.tvseriesinfo.model.database.crud.ModelSeriesCRUD;
import dyds.tvseriesinfo.model.database.crud.OperationType;
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
    private SQLCRUD sqlCRUD;

    private ModelSeriesCRUDGetter() {
        super();
        lastTitlesSeries = new ArrayList<>();
        sqlCRUD = new SQLCRUDImpl();
    }

    public static synchronized ModelSeriesCRUDGetter getInstance() {
        if (instance == null) {
            instance = new ModelSeriesCRUDGetter();
        }
        return instance;
    }

    public synchronized void getTitlesSeries() throws SeriesSearchException {
        lastTitlesSeries = sqlCRUD.getTitlesSeries();
        notifyListenersSuccess(OperationType.LOAD_LOCAL_SERIES);
    }

    public synchronized void getExtractSeriesByTitle(String title) throws SeriesSearchException {
        lastSeriesExtactByTitle = sqlCRUD.getExtractSeriesByTitle(title);
        notifyListenersSuccess(OperationType.GET_SERIES);
    }
}
