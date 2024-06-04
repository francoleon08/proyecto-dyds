package dyds.tvseriesinfo.model;

import dyds.tvseriesinfo.model.database.crud.ratedSeries.*;
import dyds.tvseriesinfo.model.database.crud.series.ModelWikipediaAPI;
import dyds.tvseriesinfo.model.database.crud.series.*;

public class ModelFactory {
    public RatedModelSeriesCRUDGetter getRatedSeriesCRUDGetter() {
        return RatedModelSeriesCRUDGetter.getInstance();
    }

    public RatedModelSeriesCRUDSaver getRatedSeriesCRUDSaver() {
        return RatedModelSeriesCRUDSaver.getInstance();
    }

    public ModelWikipediaAPI getModelWikipediaAPI() {
        return ModelWikipediaAPI.getInstance();
    }

    public ModelSeriesCRUDDeleter getSeriesCRUDDeleter() {
        return ModelSeriesCRUDDeleter.getInstance();
    }

    public ModelSeriesCRUDGetter getSeriesCRUDGetter() {
        return ModelSeriesCRUDGetter.getInstance();
    }

    public ModelSeriesCRUDSaver getSeriesCRUDSaver() {
        return ModelSeriesCRUDSaver.getInstance();
    }
}