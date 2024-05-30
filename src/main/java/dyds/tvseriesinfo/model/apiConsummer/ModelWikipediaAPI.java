package dyds.tvseriesinfo.model.apiConsummer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCRUD;
import dyds.tvseriesinfo.model.entities.Series;
import dyds.tvseriesinfo.model.entities.SeriesServices;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import dyds.tvseriesinfo.utils.HTMLTextConverter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModelWikipediaAPI extends SeriesCRUD {
    public static final String SEARCH_FAILED = "No fue posible realizar la búsqueda. Intente nuevamente.";
    private WikipediaAPIService wikipediaAPIService;
    private Map<String, Series> lastSearchResult;
    private static ModelWikipediaAPI instance;

    private ModelWikipediaAPI() {
        super();
        wikipediaAPIService = new WikipediaAPIService();
        lastSearchResult = new HashMap<>();
    }

    public static synchronized ModelWikipediaAPI getInstance() {
        if (instance == null) {
            instance = new ModelWikipediaAPI();
        }
        return instance;
    }

    public Collection<Series> getLastSearchResult() {
        return lastSearchResult.values();
    }

    public void searchSeries(String seriesToSearch) throws SeriesSearchException {
        try {
            lastSearchResult.clear();
            JsonArray jsonResultsSeries = wikipediaAPIService.searchForTerm(seriesToSearch);
            processSearchResults(jsonResultsSeries);
            notifyListenersSuccess(OperationType.WIKIPEDIA_SEARCH);
        } catch (IOException e) {
            throw new SeriesSearchException(SEARCH_FAILED);
        }
    }

    private void processSearchResults(JsonArray jsonResultsSeries) throws SeriesSearchException {
        for (JsonElement jsonSeries : jsonResultsSeries) {
            JsonObject jsonObject = jsonSeries.getAsJsonObject();
            Series series = SeriesServices.createSearchResultFromJsonObject(jsonObject);
            JsonElement extractElement = getSeriesExtractByPageID(series.getPageID());
            addSearchResultToMap(series, extractElement);
        }
    }

    private JsonElement getSeriesExtractByPageID(String id) throws SeriesSearchException {
        try {
            return wikipediaAPIService.getSeriesExtractByPageID(id);
        } catch (IOException e) {
            throw new SeriesSearchException(SEARCH_FAILED);
        }
    }

    private void addSearchResultToMap(Series series, JsonElement extractElement) {
        String resultTextToSearch = formatSearchResultText(series, extractElement);
        series.setExtract(resultTextToSearch);
        lastSearchResult.put(series.getPageID(), series);
    }

    private String formatSearchResultText(Series series, JsonElement extractElement) {
        String title = HTMLTextConverter.formatTitle(series.getTitle());
        String content = HTMLTextConverter.formatContent(extractElement.getAsString());
        return HTMLTextConverter.textToHtml(title + content);
    }
}
