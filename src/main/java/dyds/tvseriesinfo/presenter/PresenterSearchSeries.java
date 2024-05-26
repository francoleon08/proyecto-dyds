package dyds.tvseriesinfo.presenter;

import com.google.gson.*;
import dyds.tvseriesinfo.model.apiConsummer.WikipediaAPIService;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCrudGetter;
import dyds.tvseriesinfo.model.entities.SearchResult;
import dyds.tvseriesinfo.model.entities.SearchResultServices;
import dyds.tvseriesinfo.utils.HTMLTextConverter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;

import java.io.IOException;

public class PresenterSearchSeries implements Presenter {
    public static final String TV_SERIES_ARTICLETOPIC_TELEVISION = " (Tv series) articletopic:\"television\"";
    private final ViewPanelSearch viewPanelSearch;
    private final SeriesCrudGetter modelGetter;
    private WikipediaAPIService wikipediaAPIService;
    private Thread taskThread;

    public PresenterSearchSeries(ViewPanelSearch viewPanelSearch) {
        this.viewPanelSearch = viewPanelSearch;
        this.modelGetter = SeriesCrudGetter.getInstance();
        this.viewPanelSearch.setPresenterSearchSeries(this);
        this.wikipediaAPIService = new WikipediaAPIService();
        initListener();
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleSearchSeries);
        taskThread.start();
    }

    public void handleSearchSeries() {
        viewPanelSearch.setWorkingState(true);
        viewPanelSearch.clearOptionsMenu();
        fetchAndProcessSearchResults();
        viewPanelSearch.showOptionsMenu();
        viewPanelSearch.setWorkingState(false);
    }

    private void fetchAndProcessSearchResults() {
        try {
            String seriesToSearch = viewPanelSearch.getSeriesToSearchTextField().getText() + TV_SERIES_ARTICLETOPIC_TELEVISION;
            JsonArray jsonResultsSeries = wikipediaAPIService.searchForTerm(seriesToSearch);
            processSearchResults(jsonResultsSeries);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processSearchResults(JsonArray jsonResultsSeries) {
        for (JsonElement jsonSeries : jsonResultsSeries) {
            JsonObject jsonObject = jsonSeries.getAsJsonObject();
            SearchResult searchResult = SearchResultServices.createSearchResultFromJsonObject(jsonObject);
            initListenerSearchResult(searchResult);
            viewPanelSearch.addOptionSearchResult(searchResult);
        }
    }

    private void initListenerSearchResult(SearchResult searchResult) {
        searchResult.addActionListener(actionEvent -> {
            try {
                String resultTextToSearch = "";
                JsonElement extractElement = wikipediaAPIService.getSeriesExtractByPageID(searchResult.getPageID());
                resultTextToSearch = formatSearchResultText(searchResult, extractElement);
                updateViewWithSearchResultsSeries(searchResult, resultTextToSearch);
            } catch (Exception e) {
                System.err.println("Error processing response: " + e.getMessage());
            }
        });
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

    private void updateViewWithSearchResultsSeries(SearchResult searchResult, String resultTextToSearch) {
        viewPanelSearch.setSelectedResultTitle(searchResult.getTitle());
        viewPanelSearch.getResultTextToSearchHTML().setText(resultTextToSearch);
        viewPanelSearch.getResultTextToSearchHTML().setCaretPosition(0);
        viewPanelSearch.setResultTextToSearch(resultTextToSearch);
    }

    private void initListener() {
        modelGetter.addListener(OperationType.SEARCH, this);
    }

    @Override
    public void hasFinishedOperation() {

    }
}