package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.database.DataBase;
import dyds.tvseriesinfo.model.database.ListenerModelSeries;
import dyds.tvseriesinfo.model.database.OperationType;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

import java.util.Objects;

public class PresenterSaveSeries implements Presenter, ListenerModelSeries {
    private ViewPanelSearch viewPanelSearch;
    private ViewPanelStorage viewPanelStorage;
    private DataBase model;
    private Thread taskThread;

    public PresenterSaveSeries(ViewPanelStorage viewPanelStorage, ViewPanelSearch viewPanelSearch, DataBase model) {
        this.viewPanelSearch = viewPanelSearch;
        this.viewPanelStorage = viewPanelStorage;
        this.model = model;
        initListener();
    }

    private void initListener() {
        model.addListener(OperationType.SAVE, this);
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleSaveSeries);
        taskThread.start();
    }

    private void handleSaveSeries() {
        String resultTextToSearch = viewPanelSearch.getResultTextToSearch();
        if(isValidForSave(resultTextToSearch)){
            model.saveInfo(viewPanelSearch.getSelectedResultTitle().replace("'", "`"), resultTextToSearch, OperationType.SAVE);
        }
    }

    private boolean isValidForSave(String resultTextToSearch) {
        return !Objects.equals(resultTextToSearch, "");
    }

    @Override
    public void hasFinishedOperation() {
        viewPanelStorage.setSeriesComboBox(model.getTitles().stream().sorted().toArray());
    }
}
