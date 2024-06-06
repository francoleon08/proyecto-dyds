package dyds.tvseriesinfo.presenter.search;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.series.ModelWikipediaAPI;
import dyds.tvseriesinfo.model.entities.Series;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

public class PresenterSearchSeries implements Presenter {
    public static final int LIMIT_RESULT = 5;
    @Setter
    private ViewPanelSearch viewPanelSearch;
    @Getter
    private ModelWikipediaAPI modelWikipediaAPI;

    public PresenterSearchSeries(ViewPanelSearch viewPanelSearch, ModelWikipediaAPI modelWikipediaAPI) {
        this.viewPanelSearch = viewPanelSearch;
        this.viewPanelSearch.setPresenterSearchSeries(this);
        this.modelWikipediaAPI = modelWikipediaAPI;
        initListener();
    }

    @Override
    public void onEvent() {
        viewPanelSearch.setWorkingState(true);
        viewPanelSearch.clearOptionsMenu();
        doSearchSeries();
    }

    private void doSearchSeries() {
        try {
            String termToSearch = viewPanelSearch.getSeriesToSearchTextField().getText();
            if (isTermToSearchInvalid(termToSearch)) {
                throw new SeriesSearchException("The term to search is invalid. Please enter a valid term.");
            }
            modelWikipediaAPI.searchMultipleOfSeries(termToSearch, LIMIT_RESULT);
        } catch (SeriesSearchException e) {
            hasFinishedOperationError(e.getMessage());
        }
    }

    private static boolean isTermToSearchInvalid(String termToSearch) {
        return termToSearch.isEmpty() || termToSearch.length() > 50;
    }

    private void initListener() {
        modelWikipediaAPI.addListener(OperationType.WIKIPEDIA_SEARCH, this);
        modelWikipediaAPI.addListener(OperationType.GET_RATED_SERIES_BY_TITLE, this::handleLoadRatedSeriesByTitle);
    }

    private void handleLoadRatedSeriesByTitle() {
        Series series = modelWikipediaAPI.getLastSearchResult().iterator().next();
        updateViewWithSearchResultsSeries(series);
    }

    @Override
    public void hasFinishedOperationSucces() {
        for (Series series : modelWikipediaAPI.getLastSearchResult()) {
            configureRatedSeries(series);
        }
        viewPanelSearch.showOptionsMenu();
        viewPanelSearch.setWorkingState(false);
    }

    private void configureRatedSeries(Series series) {
        setRatedSeries(series);
        viewPanelSearch.addOptionSearchResult(series);
        initListenerSearchResult(series);
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