package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCRUDSaver;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;

import java.util.Objects;

public class PresenterSaveSeries implements Presenter {
    private final ViewPanelSearch viewPanelSearch;
    private final SeriesCRUDSaver seriesSaver;
    private final PresenterGetterSeries presenterGetterSeries;
    private Thread taskThread;

    public PresenterSaveSeries(ViewPanelSearch viewPanelSearch, PresenterGetterSeries presenterGetterSeries) {
        this.viewPanelSearch = viewPanelSearch;
        this.seriesSaver = SeriesCRUDSaver.getInstance();
        this.presenterGetterSeries = presenterGetterSeries;
        this.viewPanelSearch.setPresenterSaveSeries(this);
        initListener();
    }

    private void initListener() {
        seriesSaver.addListener(OperationType.SAVE, this);
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleSaveSeries);
        taskThread.start();
    }

    private void handleSaveSeries() {
        String resultTextToSearch = viewPanelSearch.getResultTextToSearch();
        if (isValidForSave(resultTextToSearch)) {
            seriesSaver.saveSeries(viewPanelSearch.getSelectedResultTitle().replace("'", "`"), resultTextToSearch, OperationType.SAVE);
        }
    }

    private boolean isValidForSave(String resultTextToSearch) {
        return !Objects.equals(resultTextToSearch, "");
    }

    @Override
    public void hasFinishedOperation() {
        presenterGetterSeries.loadSeriesInViewPanelStorage();
    }
}
