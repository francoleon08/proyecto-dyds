package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.database.DataBase;
import dyds.tvseriesinfo.model.database.ListenerModelSeries;
import dyds.tvseriesinfo.model.database.OperationType;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

public class PresenterSaveChangesSeries implements Presenter, ListenerModelSeries {
    private ViewPanelStorage viewPanelStorage;
    private DataBase model;
    private Thread taskThread;

    public PresenterSaveChangesSeries(ViewPanelStorage viewPanelStorage, DataBase model) {
        this.viewPanelStorage = viewPanelStorage;
        this.model = model;
        initListener();
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleSaveChangesSeries);
        taskThread.start();
    }

    public void handleSaveChangesSeries() {
        viewPanelStorage.setWorkingState(true);
        model.saveInfo(viewPanelStorage.getItemSelectedComboBox().replace("'", "`"), viewPanelStorage.getDetailsSeries(), OperationType.SAVE_CHANGES);
    }

    private void initListener() {
        model.addListener(OperationType.SAVE_CHANGES, this);
    }

    @Override
    public void hasFinishedOperation() {
        viewPanelStorage.setWorkingState(false);
    }
}
