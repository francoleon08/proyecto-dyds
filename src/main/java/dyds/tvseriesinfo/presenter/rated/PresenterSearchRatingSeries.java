package dyds.tvseriesinfo.presenter.rated;

import dyds.tvseriesinfo.model.database.crud.series.ModelWikipediaAPI;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.entities.Series;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.GeneralView;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;

public class PresenterSearchRatingSeries implements Presenter {
    private final ModelWikipediaAPI modelWikipediaAPI;
    private final ViewPanelPuntuaction viewPanelPuntuaction;
    private final ViewPanelSearch viewPanelSearch;
    private Thread taskThread;
    private final GeneralView generalView;

    public PresenterSearchRatingSeries(GeneralView generalView) {
        this.modelWikipediaAPI = ModelWikipediaAPI.getInstance();
        this.generalView = generalView;
        this.viewPanelPuntuaction = generalView.getViewPanelPuntuaction();
        this.viewPanelSearch = generalView.getViewPanelSearch();
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
        generalView.setSelectTab(0);
        viewPanelPuntuaction.setWorkingState(false);
    }
}