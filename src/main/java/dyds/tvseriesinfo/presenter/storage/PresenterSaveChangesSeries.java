package dyds.tvseriesinfo.presenter.storage;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.series.SeriesCRUDSaver;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

public class PresenterSaveChangesSeries implements Presenter {
    private final ViewPanelStorage viewPanelStorage;
    private final SeriesCRUDSaver seriesSaver;
    private Thread taskThread;

    public PresenterSaveChangesSeries(ViewPanelStorage viewPanelStorage, SeriesCRUDSaver seriesSaver) {
        this.viewPanelStorage = viewPanelStorage;
        this.seriesSaver = seriesSaver;
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
            seriesSaver.saveChangesSeries(getTitleSeriesForSave(), getDetailsSeriesForSave());
        } catch (SeriesSaveException e) {
            hasFinishedOperationError(e.getMessage());
        } catch (Exception e) {
            hasFinishedOperationError("You did not select a series to save.");
        }
    }

    private String getDetailsSeriesForSave() {
        return viewPanelStorage.getDetailsSeries();
    }

    private String getTitleSeriesForSave() {
        return viewPanelStorage.getItemSelectedComboBox().replace("'", "`");
    }

    @Override
    public void hasFinishedOperationSucces() {
        viewPanelStorage.setWorkingState(false);
        viewPanelStorage.showMessageDialog("The changes have been saved successfully.");
    }

    public void hasFinishedOperationError(String messageError) {
        viewPanelStorage.setWorkingState(false);
        viewPanelStorage.showMessageDialog(messageError);
    }
}
