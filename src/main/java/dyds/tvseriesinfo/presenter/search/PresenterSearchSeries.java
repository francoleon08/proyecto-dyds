package dyds.tvseriesinfo.presenter.search;

import dyds.tvseriesinfo.model.apiConsummer.ModelWikipediaAPI;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.entities.Series;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import dyds.tvseriesinfo.presenter.Presenter;
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
        doSearchSeries();
    }

    private void doSearchSeries() {
        try {
            String seriesToSearch = viewPanelSearch.getSeriesToSearchTextField().getText() + TV_SERIES_ARTICLETOPIC_TELEVISION;
            modelWikipediaAPI.searchSeries(seriesToSearch);
        } catch (SeriesSearchException e) {
            hasFinishedOperationError(e.getMessage());
        }
    }

    private void initListener() {
        modelWikipediaAPI.addListener(OperationType.WIKIPEDIA_SEARCH, this);
    }

    @Override
    public void hasFinishedOperationSucces() {
        for (Series series : modelWikipediaAPI.getLastSearchResult()) {
            viewPanelSearch.addOptionSearchResult(series);
            initListenerSearchResult(series);
        }
        viewPanelSearch.showOptionsMenu();
        viewPanelSearch.setWorkingState(false);
    }

    private void initListenerSearchResult(Series series) {
        series.addActionListener(actionEvent -> {
            try {
                updateViewWithSearchResultsSeries(series);
            } catch (Exception e) {
                System.err.println("Error processing response: " + e.getMessage());
            }
        });
    }

    private void updateViewWithSearchResultsSeries(Series series) {
        viewPanelSearch.setSelectedResultTitle(series.getTitle());
        viewPanelSearch.getResultTextToSearchHTML().setText(series.getExtract());
        viewPanelSearch.getResultTextToSearchHTML().setCaretPosition(0);
        viewPanelSearch.setResultTextToSearch(series.getExtract());
    }

    private void hasFinishedOperationError(String messageError) {
        viewPanelSearch.showMessageDialog(messageError);
        viewPanelSearch.setWorkingState(false);
    }
}