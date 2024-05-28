package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCRUDDeleter;
import dyds.tvseriesinfo.model.exceptions.SeriesDeleteException;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;
import lombok.Getter;
import lombok.Setter;

public class PresenterDeleteSeries implements Presenter {
    @Setter
    @Getter
    private ViewPanelStorage viewPanelStorage;
    private final SeriesCRUDDeleter seriesDeleter;
    private final PresenterGetterSeries presenterGetterSeries;
    private Thread taskThread;

    public PresenterDeleteSeries(ViewPanelStorage viewPanelStorage, PresenterGetterSeries presenterGetterSeries) {
        this.viewPanelStorage = viewPanelStorage;
        this.seriesDeleter = SeriesCRUDDeleter.getInstance();
        this.presenterGetterSeries = presenterGetterSeries;
        this.viewPanelStorage.setPresenterDeleteSeries(this);
        initListener();
    }

    private void initListener() {
        seriesDeleter.addListener(OperationType.DELETE, this);
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleDeleteSeries);
        taskThread.start();
    }

    private void handleDeleteSeries() {
        viewPanelStorage.setWorkingState(true);
        if (viewPanelStorage.isItemSelected()) {
            doDeleteSeries();
        } else {
            hasFinishedOperationError("No seleccionó una serie para eliminar.");
        }
    }

    private void doDeleteSeries() {
        try {
            seriesDeleter.deleteSeriesByTitle(viewPanelStorage.getItemSelectedComboBox());
        } catch (SeriesDeleteException e) {
            hasFinishedOperationError(e.getMessage());
        }
    }

    public void hasFinishedOperationError(String messageError) {
        viewPanelStorage.showMessageDialog(messageError);
        viewPanelStorage.setWorkingState(false);
    }

    @Override
    public void hasFinishedOperationSucces() {
        presenterGetterSeries.loadSeriesInViewPanelStorage();
        viewPanelStorage.setDetailsSeries("");
        viewPanelStorage.setWorkingState(false);
        viewPanelStorage.showMessageDialog("La serie se ha eliminado correctamente.");
    }
}
