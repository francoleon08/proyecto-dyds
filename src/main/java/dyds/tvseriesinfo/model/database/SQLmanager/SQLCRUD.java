package dyds.tvseriesinfo.model.database.SQLmanager;

import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import dyds.tvseriesinfo.model.exceptions.SeriesDeleteException;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;

import java.util.ArrayList;

public interface SQLCRUD {
    void deleteSeriesByTitle(String title) throws SeriesDeleteException;
    void savePuntuaction(String title, int puntuaction) throws SeriesSaveException;
    void saveSeries(String title, String extract) throws SeriesSaveException;
    ArrayList<String> getTitlesSeries() throws SeriesSearchException;
    String getExtractSeriesByTitle(String title) throws SeriesSearchException;
    ArrayList<RatedSeries> getRatedSeries() throws SearchRatedSeriesException;
    RatedSeries getRatedSeriesByTitle(String title) throws SearchRatedSeriesException;
    int setRatedSeriesByTitle(String title);
}
