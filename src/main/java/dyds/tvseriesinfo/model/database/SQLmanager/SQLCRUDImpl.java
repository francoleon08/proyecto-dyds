package dyds.tvseriesinfo.model.database.SQLmanager;

import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import dyds.tvseriesinfo.model.exceptions.SeriesDeleteException;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;

import java.util.ArrayList;

public class SQLCRUDImpl implements SQLCRUD {
    @Override
    public void deleteSeriesByTitle(String title) throws SeriesDeleteException {
        SQLDelete.deleteSeriesByTitle(title);
    }

    @Override
    public void savePuntuaction(String title, int puntuaction) throws SeriesSaveException {
        SQLInsert.savePuntuaction(title, puntuaction);
    }

    @Override
    public void saveSeries(String title, String extract) throws SeriesSaveException {
        SQLInsert.saveSeries(title, extract);
    }

    @Override
    public ArrayList<String> getTitlesSeries() throws SeriesSearchException {
        return SQLSelectManager.getTitlesSeries();
    }

    @Override
    public String getExtractSeriesByTitle(String title) throws SeriesSearchException {
        return SQLSelectManager.getExtractSeriesByTitle(title);
    }

    @Override
    public ArrayList<RatedSeries> getRatedSeries() throws SearchRatedSeriesException {
        return SQLSelectManager.getRatedSeries();
    }

    @Override
    public RatedSeries getRatedSeriesByTitle(String title) throws SearchRatedSeriesException {
        return SQLSelectManager.getRatedSeriesByTitle(title);
    }

    @Override
    public int setRatedSeriesByTitle(String title) {
        return SQLSelectManager.setRatedSeriesByTitle(title);
    }
}
