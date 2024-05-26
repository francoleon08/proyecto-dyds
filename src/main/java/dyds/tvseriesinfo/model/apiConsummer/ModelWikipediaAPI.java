package dyds.tvseriesinfo.model.apiConsummer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCRUD;
import dyds.tvseriesinfo.model.entities.SearchResult;
import dyds.tvseriesinfo.model.entities.SearchResultServices;
import dyds.tvseriesinfo.utils.HTMLTextConverter;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModelWikipediaAPI extends SeriesCRUD {
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

    public void searchSeries(String seriesToSearch) {
        try {
            lastSearchResult.clear();
            JsonArray jsonResultsSeries = wikipediaAPIService.searchForTerm(seriesToSearch);
            processSearchResults(jsonResultsSeries);
            notifyListenersSuccess(OperationType.WIKIPEDIA_SEARCH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processSearchResults(JsonArray jsonResultsSeries) {
        for (JsonElement jsonSeries : jsonResultsSeries) {
            JsonObject jsonObject = jsonSeries.getAsJsonObject();
            SearchResult searchResult = SearchResultServices.createSearchResultFromJsonObject(jsonObject);
            addSearchResultToMap(searchResult);
        }
    }

    private void addSearchResultToMap(SearchResult searchResult) {
        try {
            String resultTextToSearch = "";
            JsonElement extractElement = wikipediaAPIService.getSeriesExtractByPageID(searchResult.getPageID());
            resultTextToSearch = formatSearchResultText(searchResult, extractElement);
            searchResult.setExtract(resultTextToSearch);
            lastSearchResult.put(searchResult.getPageID(), searchResult);

        } catch (Exception e) {
            System.err.println("Error processing response: " + e.getMessage());
        }
    }

    private String formatSearchResultText(SearchResult searchResult, JsonElement extractElement) {
        String resultTextToSearch;
        if (extractElement != null) {
            resultTextToSearch = "<h1>" + searchResult.getTitle() + "</h1>"
                    + extractElement.getAsString().replace("\\n", "\n");
            resultTextToSearch = HTMLTextConverter.textToHtml(resultTextToSearch);
        } else {
            resultTextToSearch = "No Results";
        }
        return resultTextToSearch;
    }
}
