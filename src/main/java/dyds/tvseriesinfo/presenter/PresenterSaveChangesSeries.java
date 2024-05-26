package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCRUDSaver;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

public class PresenterSaveChangesSeries implements Presenter {
    private final ViewPanelStorage viewPanelStorage;
    private final SeriesCRUDSaver seriesSaver;
    private Thread taskThread;

    public PresenterSaveChangesSeries(ViewPanelStorage viewPanelStorage) {
        this.viewPanelStorage = viewPanelStorage;
        this.seriesSaver = SeriesCRUDSaver.getInstance();
        this.viewPanelStorage.setPresenterSaveChangesSeries(this);
        initListener();
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleSaveChangesSeries);
        taskThread.start();
    }

    public void handleSaveChangesSeries() {
        viewPanelStorage.setWorkingState(true);
        seriesSaver.saveSeries(viewPanelStorage.getItemSelectedComboBox().replace("'", "`"), viewPanelStorage.getDetailsSeries(), OperationType.SAVE_CHANGES);
    }

    private void initListener() {
        seriesSaver.addListener(OperationType.SAVE_CHANGES, this);
    }

    @Override
    public void hasFinishedOperation() {
        viewPanelStorage.setWorkingState(false);
    }
}
