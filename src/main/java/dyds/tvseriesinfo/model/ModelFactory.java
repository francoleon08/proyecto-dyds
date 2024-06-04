package dyds.tvseriesinfo.model;

import dyds.tvseriesinfo.model.database.crud.ratedSeries.*;
import dyds.tvseriesinfo.model.database.crud.series.ModelWikipediaAPI;
import dyds.tvseriesinfo.model.database.crud.series.*;

public class ModelFactory {
    public RatedSeriesCRUDGetter getRatedSeriesCRUDGetter() {
        return RatedSeriesCRUDGetter.getInstance();
    }

    public RatedSeriesCRUDSaver getRatedSeriesCRUDSaver() {
        return RatedSeriesCRUDSaver.getInstance();
    }

    public ModelWikipediaAPI getModelWikipediaAPI() {
        return ModelWikipediaAPI.getInstance();
    }

    public SeriesCRUDDeleter getSeriesCRUDDeleter() {
        return SeriesCRUDDeleter.getInstance();
    }

    public SeriesCRUDGetter getSeriesCRUDGetter() {
        return SeriesCRUDGetter.getInstance();
    }

    public SeriesCRUDSaver getSeriesCRUDSaver() {
        return SeriesCRUDSaver.getInstance();
    }
}