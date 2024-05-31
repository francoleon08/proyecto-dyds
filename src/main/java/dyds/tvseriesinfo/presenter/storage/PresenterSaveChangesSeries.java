package dyds.tvseriesinfo.presenter.storage;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCRUDSaver;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;
import dyds.tvseriesinfo.presenter.Presenter;
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

    private void initListener() {
        seriesSaver.addListener(OperationType.SAVE_CHANGES, this);
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleSaveChangesSeries);
        taskThread.start();
    }

    public void handleSaveChangesSeries() {
        viewPanelStorage.setWorkingState(true);
        doSaveChangesSeries();
    }

    private void doSaveChangesSeries() {
        try {
            seriesSaver.saveChangesSeries(viewPanelStorage.getItemSelectedComboBox().replace("'", "`"), viewPanelStorage.getDetailsSeries());
        } catch (SeriesSaveException e) {
            hasFinishedOperationError(e.getMessage());
        } catch (Exception e) {
            hasFinishedOperationError("No seleccionó una serie para guardar.");
        }
    }

    @Override
    public void hasFinishedOperationSucces() {
        viewPanelStorage.setWorkingState(false);
        viewPanelStorage.showMessageDialog("Los cambios se han guardado correctamente.");
    }

    public void hasFinishedOperationError(String messageError) {
        viewPanelStorage.setWorkingState(false);
        viewPanelStorage.showMessageDialog(messageError);
    }
}
