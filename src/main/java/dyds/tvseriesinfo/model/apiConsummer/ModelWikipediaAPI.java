package dyds.tvseriesinfo.model.apiConsummer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCRUD;
import dyds.tvseriesinfo.model.entities.SearchResult;
import dyds.tvseriesinfo.model.entities.SearchResultServices;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import dyds.tvseriesinfo.utils.HTMLTextConverter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModelWikipediaAPI extends SeriesCRUD {
    public static final String SEARCH_FAILED = "No fue posible realizar la búsqueda. Intente nuevamente.";
    private WikipediaAPIService wikipediaAPIService;
    private Map<String, SearchResult> lastSearchResult;
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

    public Collection<SearchResult> getLastSearchResult() {
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
            SearchResult searchResult = SearchResultServices.createSearchResultFromJsonObject(jsonObject);
            JsonElement extractElement = getSeriesExtractByPageID(searchResult.getPageID());
            addSearchResultToMap(searchResult, extractElement);
        }
    }

    private JsonElement getSeriesExtractByPageID(String id) throws SeriesSearchException {
        try {
            return wikipediaAPIService.getSeriesExtractByPageID(id);
        } catch (IOException e) {
            throw new SeriesSearchException(SEARCH_FAILED);
        }
    }

    private void addSearchResultToMap(SearchResult searchResult, JsonElement extractElement) {
        String resultTextToSearch = formatSearchResultText(searchResult, extractElement);
        searchResult.setExtract(resultTextToSearch);
        lastSearchResult.put(searchResult.getPageID(), searchResult);
    }

    private String formatSearchResultText(SearchResult searchResult, JsonElement extractElement) {
        String title = HTMLTextConverter.formatTitle(searchResult.getTitle());
        String content = HTMLTextConverter.formatContent(extractElement.getAsString());
        return HTMLTextConverter.textToHtml(title + content);
    }
}
