package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.apiConsummer.ModelWikipediaAPI;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.entities.SearchResult;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;

public class PresenterSearchSeries implements Presenter {
    public static final String TV_SERIES_ARTICLETOPIC_TELEVISION = " (Tv series) articletopic:\"television\"";
    private final ViewPanelSearch viewPanelSearch;
    private ModelWikipediaAPI modelWikipediaAPI;
    private Thread taskThread;

    public PresenterSearchSeries(ViewPanelSearch viewPanelSearch) {
        this.viewPanelSearch = viewPanelSearch;
        this.viewPanelSearch.setPresenterSearchSeries(this);
        this.modelWikipediaAPI = ModelWikipediaAPI.getInstance();
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
        String seriesToSearch = viewPanelSearch.getSeriesToSearchTextField().getText() + TV_SERIES_ARTICLETOPIC_TELEVISION;
        modelWikipediaAPI.searchSeries(seriesToSearch);
    }

    private void initListener() {
        modelWikipediaAPI.addListener(OperationType.WIKIPEDIA_SEARCH, this);
    }

    @Override
    public void hasFinishedOperation() {
        for (SearchResult searchResult : modelWikipediaAPI.getLastSearchResult()) {
            viewPanelSearch.addOptionSearchResult(searchResult);
            initListenerSearchResult(searchResult);
        }
        viewPanelSearch.showOptionsMenu();
        viewPanelSearch.setWorkingState(false);
    }

    private void initListenerSearchResult(SearchResult searchResult) {
        searchResult.addActionListener(actionEvent -> {
            try {
                updateViewWithSearchResultsSeries(searchResult);
            } catch (Exception e) {
                System.err.println("Error processing response: " + e.getMessage());
            }
        });
    }

    private void updateViewWithSearchResultsSeries(SearchResult searchResult) {
        viewPanelSearch.setSelectedResultTitle(searchResult.getTitle());
        viewPanelSearch.getResultTextToSearchHTML().setText(searchResult.getExtract());
        viewPanelSearch.getResultTextToSearchHTML().setCaretPosition(0);
        viewPanelSearch.setResultTextToSearch(searchResult.getExtract());
    }
}