package dyds.tvseriesinfo.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dyds.tvseriesinfo.model.apiConsummer.WikipediaPageAPI;
import dyds.tvseriesinfo.model.apiConsummer.WikipediaSearchAPI;
import dyds.tvseriesinfo.model.database.crud.SeriesCrudGetter;
import dyds.tvseriesinfo.model.entities.SearchResult;
import dyds.tvseriesinfo.model.database.ListenerModelSeries;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.utils.HTMLTextConverter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PresenterSearchSeries implements Presenter, ListenerModelSeries {
    private ViewPanelSearch viewPanelSearch;
    private WikipediaSearchAPI searchAPI;
    private WikipediaPageAPI pageAPI;
    private SeriesCrudGetter modelGetter;
    private Thread taskThread;

    public PresenterSearchSeries(ViewPanelSearch viewPanelSearch, SeriesCrudGetter modelGetter) {
        this.viewPanelSearch = viewPanelSearch;
        this.modelGetter = modelGetter;
        initListener();
        configureAPIs();
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleSearchSeries);
        taskThread.start();
    }

    // EVENTO PARA BUSCAR SERIE EN LA WIKI
    public void handleSearchSeries() {
        viewPanelSearch.setWorkingState(true);
        Response<String> callForSearchResponse;
        try {
            callForSearchResponse = searchAPI.searchForTerm(viewPanelSearch.getSeriesToSearchTextField().getText() + " (Tv series) articletopic:\"television\"").execute();
            JsonArray jsonResults = parserResponse(callForSearchResponse);

            viewPanelSearch.clearOptionsMenu();
            for (JsonElement je : jsonResults) {
                JsonObject searchResult = je.getAsJsonObject();
                String searchResultTitle = searchResult.get("title").getAsString();
                String searchResultPageId = searchResult.get("pageid").getAsString();
                String searchResultSnippet = searchResult.get("snippet").getAsString();

                SearchResult sr = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
                viewPanelSearch.addOptionSearchResult(sr);

                initEventSearchResult(sr);
            }
            viewPanelSearch.showOptionsMenu();
            viewPanelSearch.setWorkingState(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void initEventSearchResult(SearchResult sr) {

        sr.addActionListener(actionEvent -> {
            try {
                String resultTextToSearch = "";

                Gson gson = new Gson();
                //Now fetch the info of the select page
                Response<String> callForPageResponse = pageAPI.getExtractByPageID(sr.pageID).execute();

                //toAlberto: This is similar to the code above, but here we parse the wikipage answer.
                //For more details on Gson look for very important coment 1, or just google it :P
                JsonObject jobj2 = gson.fromJson(callForPageResponse.body(), JsonObject.class);
                JsonObject query2 = jobj2.get("query").getAsJsonObject();
                JsonObject pages = query2.get("pages").getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
                Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
                JsonObject page = first.getValue().getAsJsonObject();
                JsonElement searchResultExtract2 = page.get("extract");
                if (searchResultExtract2 == null) {
                    resultTextToSearch = "No Results";
                } else {
                    resultTextToSearch = "<h1>" + sr.title + "</h1>";
                    viewPanelSearch.setSelectedResultTitle(sr.title);
                    resultTextToSearch += searchResultExtract2.getAsString().replace("\\n", "\n");
                    resultTextToSearch = HTMLTextConverter.textToHtml(resultTextToSearch);
                }

                viewPanelSearch.getResultTextToSearchHTML().setText(resultTextToSearch);
                viewPanelSearch.getResultTextToSearchHTML().setCaretPosition(0);
                viewPanelSearch.setResultTextToSearch(resultTextToSearch);
            } catch (Exception e12) {
                System.out.println(e12.getMessage());
            }
        });
    }

    private JsonArray parserResponse(Response<String> callForSearchResponse) {
        Gson gson = new Gson();
        JsonObject jobj = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
        JsonObject query = jobj.get("query").getAsJsonObject();
        Iterator<JsonElement> resultIterator = query.get("search").getAsJsonArray().iterator();
        return query.get("search").getAsJsonArray();
    }

    private void configureAPIs() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        searchAPI = retrofit.create(WikipediaSearchAPI.class);
        pageAPI = retrofit.create(WikipediaPageAPI.class);
    }

    private void initListener() {
        modelGetter.addListener(OperationType.SEARCH, this);
    }

    @Override
    public void hasFinishedOperation() {

    }
}