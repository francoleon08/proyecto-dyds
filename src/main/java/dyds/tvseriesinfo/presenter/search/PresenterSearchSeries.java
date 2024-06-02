package dyds.tvseriesinfo.presenter.search;

import dyds.tvseriesinfo.model.apiConsummer.ModelWikipediaAPI;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.entities.Series;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;

import javax.swing.*;

public class PresenterSearchSeries implements Presenter {
    public static final String TV_SERIES_ARTICLETOPIC_TELEVISION = " (Tv series) articletopic:\"television\"";
    public static final int LIMIT_RESULT = 5;
    private final ViewPanelSearch viewPanelSearch;
    private final ModelWikipediaAPI modelWikipediaAPI;
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
            String termToSearch = viewPanelSearch.getSeriesToSearchTextField().getText() + TV_SERIES_ARTICLETOPIC_TELEVISION;
            modelWikipediaAPI.searchAmountOfSeries(termToSearch, LIMIT_RESULT);
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
            setRatedSeries(series);
            viewPanelSearch.addOptionSearchResult(series);
            initListenerSearchResult(series);
        }
        viewPanelSearch.showOptionsMenu();
        viewPanelSearch.setWorkingState(false);
    }

    private void setRatedSeries(Series series) {
        if (series.getRated() != 0) {
            series.setIcon(new ImageIcon("assets/check.png"));
        } else {
            series.setIcon(new ImageIcon("assets/x.png"));
        }
    }

    private void initListenerSearchResult(Series series) {
        series.addActionListener(actionEvent -> {
            updateViewWithSearchResultsSeries(series);
        });
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

    private void hasFinishedOperationError(String messageError) {
        viewPanelSearch.showMessageDialog(messageError);
        viewPanelSearch.setWorkingState(false);
    }
}