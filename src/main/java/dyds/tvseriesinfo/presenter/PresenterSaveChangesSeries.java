package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.database.ListenerModelSeries;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCrudSaver;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

public class PresenterSaveChangesSeries implements Presenter, ListenerModelSeries {
    private ViewPanelStorage viewPanelStorage;
    private SeriesCrudSaver modelSaver;
    private Thread taskThread;

    public PresenterSaveChangesSeries(ViewPanelStorage viewPanelStorage, SeriesCrudSaver modelSaver) {
        this.viewPanelStorage = viewPanelStorage;
        this.modelSaver = modelSaver;
        initListener();
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleSaveChangesSeries);
        taskThread.start();
    }

    public void handleSaveChangesSeries() {
        viewPanelStorage.setWorkingState(true);
        modelSaver.saveSeries(viewPanelStorage.getItemSelectedComboBox().replace("'", "`"), viewPanelStorage.getDetailsSeries(), OperationType.SAVE_CHANGES);
    }

    private void initListener() {
        modelSaver.addListener(OperationType.SAVE_CHANGES, this);
    }

    @Override
    public void hasFinishedOperation() {
        viewPanelStorage.setWorkingState(false);
    }
}
