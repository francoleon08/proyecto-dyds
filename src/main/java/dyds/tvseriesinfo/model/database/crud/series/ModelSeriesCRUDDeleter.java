package dyds.tvseriesinfo.model.database.crud.series;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLCRUD;
import dyds.tvseriesinfo.model.database.SQLmanager.SQLCRUDImpl;
import dyds.tvseriesinfo.model.database.SQLmanager.SQLDelete;
import dyds.tvseriesinfo.model.database.crud.ModelSeriesCRUD;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.exceptions.SeriesDeleteException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import lombok.Setter;

public class ModelSeriesCRUDDeleter extends ModelSeriesCRUD {
    private static ModelSeriesCRUDDeleter instance;
    @Setter
    private SQLCRUD sqlCRUD;

    private ModelSeriesCRUDDeleter() {
        super();
        sqlCRUD = new SQLCRUDImpl();
    }

    public static synchronized ModelSeriesCRUDDeleter getInstance() {
        if (instance == null) {
            instance = new ModelSeriesCRUDDeleter();
        }
        return instance;
    }

    public synchronized void deleteSeriesByTitle(String title) throws SeriesDeleteException, SeriesSearchException {
        sqlCRUD.deleteSeriesByTitle(title);
        notifyListenersSuccess(OperationType.DELETE_SERIES);
        notifyListenersSuccess(OperationType.LOAD_LOCAL_SERIES_AFTER_CHANGE);
    }
}
