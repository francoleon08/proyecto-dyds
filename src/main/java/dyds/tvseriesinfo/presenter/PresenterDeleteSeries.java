package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.database.DataBase;
import dyds.tvseriesinfo.model.database.ListenerModelSeries;
import dyds.tvseriesinfo.model.database.OperationType;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;
import lombok.Getter;
import lombok.Setter;

public class PresenterDeleteSeries implements Presenter, ListenerModelSeries {
    @Setter @Getter
    private ViewPanelStorage viewPanelStorage;
    private DataBase model;
    private Thread taskThread;

    public PresenterDeleteSeries(ViewPanelStorage viewPanelStorage, DataBase model) {
        this.viewPanelStorage = viewPanelStorage;
        this.model = model;
        initListener();
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleDeleteSeries);
        taskThread.start();
    }

    private void handleDeleteSeries() {
        viewPanelStorage.setWorkingState(true);
        if(viewPanelStorage.isItemSelected()){
            model.deleteEntry(viewPanelStorage.getItemSelectedComboBox());
        }
    }

    private void initListener() {
        model.addListener(OperationType.DELETE, this);
    }

    @Override
    public void hasFinishedOperation() {
        viewPanelStorage.setSeriesComboBox(model.getTitles().stream().sorted().toArray());
        viewPanelStorage.setDetailsSeries("");
        viewPanelStorage.setWorkingState(false);
    }
}
