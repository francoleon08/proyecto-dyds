package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.database.ListenerModelSeries;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCrudGetter;
import dyds.tvseriesinfo.model.database.crud.SeriesCrudSaver;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

import java.util.Objects;

public class PresenterSaveSeries implements Presenter, ListenerModelSeries {
    private ViewPanelSearch viewPanelSearch;
    private ViewPanelStorage viewPanelStorage;
    private SeriesCrudSaver seriesSaver;
    private SeriesCrudGetter seriesGetter;
    private Thread taskThread;

    public PresenterSaveSeries(ViewPanelStorage viewPanelStorage, ViewPanelSearch viewPanelSearch, SeriesCrudSaver seriesSaver, SeriesCrudGetter seriesGetter) {
        this.viewPanelSearch = viewPanelSearch;
        this.viewPanelStorage = viewPanelStorage;
        this.seriesSaver = seriesSaver;
        this.seriesGetter = seriesGetter;
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
        if(isValidForSave(resultTextToSearch)){
            seriesSaver.saveSeries(viewPanelSearch.getSelectedResultTitle().replace("'", "`"), resultTextToSearch, OperationType.SAVE);
        }
    }

    private boolean isValidForSave(String resultTextToSearch) {
        return !Objects.equals(resultTextToSearch, "");
    }

    @Override
    public void hasFinishedOperation() {
        viewPanelStorage.setSeriesComboBox(seriesGetter.getTitlesSeries().stream().sorted().toArray());
    }
}
