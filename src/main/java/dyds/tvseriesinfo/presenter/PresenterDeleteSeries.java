package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCrudDeleter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;
import lombok.Getter;
import lombok.Setter;

public class PresenterDeleteSeries implements Presenter {
    @Setter
    @Getter
    private ViewPanelStorage viewPanelStorage;
    private final SeriesCrudDeleter seriesDeleter;
    private final PresenterGetterSeries presenterGetterSeries;
    private Thread taskThread;

    public PresenterDeleteSeries(ViewPanelStorage viewPanelStorage, PresenterGetterSeries presenterGetterSeries) {
        this.viewPanelStorage = viewPanelStorage;
        this.seriesDeleter = SeriesCrudDeleter.getInstance();
        this.presenterGetterSeries = presenterGetterSeries;
        this.viewPanelStorage.setPresenterDeleteSeries(this);
        initListener();
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleDeleteSeries);
        taskThread.start();
    }

    private void handleDeleteSeries() {
        viewPanelStorage.setWorkingState(true);
        if (viewPanelStorage.isItemSelected()) {
            seriesDeleter.deleteSeriesByTitle(viewPanelStorage.getItemSelectedComboBox());
        }
    }

    private void initListener() {
        seriesDeleter.addListener(OperationType.DELETE, this);
    }

    @Override
    public void hasFinishedOperation() {
        presenterGetterSeries.loadSeriesInViewPanelStorage();
        viewPanelStorage.setDetailsSeries("");
        viewPanelStorage.setWorkingState(false);
    }
}
