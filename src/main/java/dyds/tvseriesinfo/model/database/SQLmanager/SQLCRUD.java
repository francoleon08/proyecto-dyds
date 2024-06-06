package dyds.tvseriesinfo.model.database.SQLmanager;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLSelect.SQLSelectExtractByTitle;
import dyds.tvseriesinfo.model.database.SQLmanager.SQLSelect.SQLSelectRatedSeries;
import dyds.tvseriesinfo.model.database.SQLmanager.SQLSelect.SQLSelectRatedSeriesByTitle;
import dyds.tvseriesinfo.model.database.SQLmanager.SQLSelect.SQLSelectSeries;
import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import dyds.tvseriesinfo.model.exceptions.SeriesDeleteException;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;

import java.util.ArrayList;

public interface SQLCRUD {
    public void deleteSeriesByTitle(String title) throws SeriesDeleteException;
    public void savePuntuaction(String title, int puntuaction) throws SeriesSaveException;
    public void saveSeries(String title, String extract) throws SeriesSaveException;
    public ArrayList<String> getTitlesSeries() throws SeriesSearchException;
    public String getExtractSeriesByTitle(String title) throws SeriesSearchException;
    public ArrayList<RatedSeries> getRatedSeries() throws SearchRatedSeriesException;
    public RatedSeries getRatedSeriesByTitle(String title) throws SearchRatedSeriesException;
    public int setRatedSeriesByTitle(String title);
}
