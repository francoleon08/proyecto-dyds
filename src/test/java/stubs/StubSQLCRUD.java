package stubs;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLCRUD;
import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import dyds.tvseriesinfo.model.exceptions.SeriesDeleteException;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;

import java.util.ArrayList;

public class StubSQLCRUD implements SQLCRUD {
    @Override
    public void deleteSeriesByTitle(String title) throws SeriesDeleteException {

    }

    @Override
    public void savePuntuaction(String title, int puntuaction) throws SeriesSaveException {
    }

    @Override
    public void saveSeries(String title, String extract) throws SeriesSaveException {
    }

    @Override
    public ArrayList<String> getTitlesSeries() throws SeriesSearchException {
        ArrayList<String> titlesSeriesTest = new ArrayList<>();
        titlesSeriesTest.add("The 100 (TV series)");
        titlesSeriesTest.add("Rubí (2020 TV series)");
        titlesSeriesTest.add("Batman (TV series)");
        titlesSeriesTest.add("24 (TV series)");
        titlesSeriesTest.add("Monk (TV series)");
        return titlesSeriesTest;
    }

    @Override
    public String getExtractSeriesByTitle(String title) throws SeriesSearchException {
        return "";
    }

    @Override
    public ArrayList<RatedSeries> getRatedSeries() throws SearchRatedSeriesException {
        ArrayList<RatedSeries> ratedSeriesTest = new ArrayList<>();
        ratedSeriesTest = new ArrayList<>();
        ratedSeriesTest.add(RatedSeries.builder()
                .pageID("123")
                .title("The 100 (TV series)")
                .rating(3)
                .dateModified("")
                .timeModified("")
                .build()
        );
        ratedSeriesTest.add(RatedSeries.builder()
                .pageID("123")
                .title("Batman (TV series)")
                .rating(5)
                .dateModified("")
                .timeModified("")
                .build()
        );
        ratedSeriesTest.add(RatedSeries.builder()
                .pageID("123")
                .title("Monk (TV series)")
                .rating(2)
                .dateModified("")
                .timeModified("")
                .build()
        );
        return ratedSeriesTest;
    }

    @Override
    public RatedSeries getRatedSeriesByTitle(String title) throws SearchRatedSeriesException {
        return null;
    }

    @Override
    public int setRatedSeriesByTitle(String title) {
        return 0;
    }
}
