package dyds.tvseriesinfo.model.database.SQLmanager.SQLSelect;

import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;

import java.util.ArrayList;

public class SQLSelectManager {

    public static ArrayList<String> getTitlesSeries() throws SeriesSearchException {
        return SQLSelectSeries.getTitlesSeries();
    }

    public static String getExtractSeriesByTitle(String title) throws SeriesSearchException {
        return SQLSelectExtractByTitle.getExtractSeriesByTitle(title);
    }

    public static ArrayList<RatedSeries> getRatedSeries() throws SearchRatedSeriesException {
        return SQLSelectRatedSeries.getRatedSeries();
    }

    public static RatedSeries getRatedSeriesByTitle(String title) throws SearchRatedSeriesException {
        return SQLSelectRatedSeriesByTitle.getRatedSeriesByTitle(title);
    }

    public static int setRatedSeriesByTitle(String title) {
        try {
            RatedSeries ratedSeries = getRatedSeriesByTitle(title);
            if (ratedSeries.getTitle() != null)
                return ratedSeries.getRating();
            return 0;
        } catch (SearchRatedSeriesException e) {
            return 0;
        }
    }
}
