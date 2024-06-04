package dyds.tvseriesinfo.model;

import dyds.tvseriesinfo.model.database.crud.ratedSeries.RatedModelSeriesCRUDGetter;
import dyds.tvseriesinfo.model.database.crud.ratedSeries.RatedModelSeriesCRUDSaver;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDDeleter;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDGetter;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDSaver;
import dyds.tvseriesinfo.model.database.crud.series.ModelWikipediaAPI;

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