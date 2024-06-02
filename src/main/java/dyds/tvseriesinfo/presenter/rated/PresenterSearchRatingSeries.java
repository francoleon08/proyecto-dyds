package dyds.tvseriesinfo.presenter.rated;

import dyds.tvseriesinfo.model.apiConsummer.ModelWikipediaAPI;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.entities.Series;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.View;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;

public class PresenterSearchRatingSeries implements Presenter {
    private final ModelWikipediaAPI modelWikipediaAPI;
    private final ViewPanelPuntuaction viewPanelPuntuaction;
    private final ViewPanelSearch viewPanelSearch;
    private Thread taskThread;
    private final View view;

    public PresenterSearchRatingSeries(View view) {
        this.modelWikipediaAPI = ModelWikipediaAPI.getInstance();
        this.view = view;
        this.viewPanelPuntuaction = view.getViewPanelPuntuaction();
        this.viewPanelSearch = view.getViewPanelSearch();
        this.viewPanelPuntuaction.setPresenterSearchRatingSeries(this);
        initListener();
    }

    private void initListener() {
        modelWikipediaAPI.addListener(OperationType.GET_RATED_SERIES_BY_TITLE, this);
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleGetExtractSeries);
        taskThread.start();
    }

    private void handleGetExtractSeries() {
        viewPanelPuntuaction.setWorkingState(true);
        doGetExtractSeries();
    }

    private void doGetExtractSeries() {
        try {
            modelWikipediaAPI.searchOneSeries(viewPanelPuntuaction.getTitleRatedSeriesSelected());
        } catch (SeriesSearchException e) {
            hasFinishedOperationError("Error, intente de nuevo mas tarde.");
        }
    }

    private void hasFinishedOperationError(String message) {
        viewPanelPuntuaction.showMessageDialog(message);
        viewPanelPuntuaction.setWorkingState(false);
    }

    @Override
    public void hasFinishedOperationSucces() {
        Series series = modelWikipediaAPI.getLastSearchResult().iterator().next();
        updateViewWithSearchResultsSeries(series);
        view.setSelectTab(0);
        viewPanelPuntuaction.setWorkingState(false);
    }

    private void updateViewWithSearchResultsSeries(Series series) {
        viewPanelSearch.setSelectedResultTitle(series.getTitle());
        viewPanelSearch.getResultTextToSearchHTML().setText(series.getExtract());
        viewPanelSearch.getResultTextToSearchHTML().setCaretPosition(0);
        viewPanelSearch.setResultTextToSearch(series.getExtract());
        setRatedSeriesWithSearchView(series);
    }

    private void setRatedSeriesWithSearchView(Series series) {
        if (series.getRated() != 0) {
            viewPanelSearch.setRatedSeries(series.getRated() - 1);
        }
    }
}