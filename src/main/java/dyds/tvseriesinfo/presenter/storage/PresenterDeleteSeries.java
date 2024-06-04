package dyds.tvseriesinfo.presenter.storage;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDDeleter;
import dyds.tvseriesinfo.model.exceptions.SeriesDeleteException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;
import lombok.Getter;
import lombok.Setter;

public class PresenterDeleteSeries implements Presenter {
    @Setter @Getter
    private ViewPanelStorage viewPanelStorage;
    private final ModelSeriesCRUDDeleter seriesDeleter;
    private Thread taskThread;

    public PresenterDeleteSeries(ViewPanelStorage viewPanelStorage, ModelSeriesCRUDDeleter seriesDeleter) {
        this.viewPanelStorage = viewPanelStorage;
        this.seriesDeleter = seriesDeleter;
        this.viewPanelStorage.setPresenterDeleteSeries(this);
        initListener();
    }

    private void initListener() {
        seriesDeleter.addListener(OperationType.DELETE_SERIES, this);
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleDeleteSeries);
        taskThread.start();
    }

    private void handleDeleteSeries() {
        viewPanelStorage.setWorkingState(true);
        if (thereIsASerieSelected()) {
            doDeleteSeries();
        } else {
            hasFinishedOperationError("You did not select a series to delete.");
        }
    }

    private boolean thereIsASerieSelected() {
        return viewPanelStorage.isItemSelected();
    }

    private void doDeleteSeries() {
        try {
            seriesDeleter.deleteSeriesByTitle(viewPanelStorage.getItemSelectedComboBox());
        } catch (SeriesDeleteException | SeriesSearchException e) {
            hasFinishedOperationError(e.getMessage());
        }
    }

    public void hasFinishedOperationError(String messageError) {
        viewPanelStorage.showMessageDialog(messageError);
        viewPanelStorage.setWorkingState(false);
    }

    @Override
    public void hasFinishedOperationSucces() {
        viewPanelStorage.setDetailsSeries("");
        viewPanelStorage.setWorkingState(false);
        viewPanelStorage.showMessageDialog("The series was deleted successfully.");
    }
}
