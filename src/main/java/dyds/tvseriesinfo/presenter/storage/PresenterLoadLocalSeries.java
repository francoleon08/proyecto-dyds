package dyds.tvseriesinfo.presenter.storage;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCRUDGetter;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

public class PresenterLoadLocalSeries implements Presenter {
    private final ViewPanelStorage viewPanelStorage;
    private final SeriesCRUDGetter seriesGetter;
    private Thread taskThread;

    public PresenterLoadLocalSeries(ViewPanelStorage viewPanelStorage) {
        this.viewPanelStorage = viewPanelStorage;
        this.seriesGetter = SeriesCRUDGetter.getInstance();
        initListener();
        onEvent();
    }

    private void initListener() {
        seriesGetter.addListener(OperationType.LOAD_LOCAL_SERIES, this);
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleLoadLocalSeries);
        taskThread.start();
    }

    private void handleLoadLocalSeries() {
        try {
            seriesGetter.getTitlesSeries();
        } catch (SeriesSearchException e) {
            hasFinishedOperationError(e.getMessage());
        }
    }

    @Override
    public void hasFinishedOperationSucces() {
        viewPanelStorage.setSeriesComboBox(seriesGetter.getLastTitlesSeries().stream().sorted().toArray());
    }

    private void hasFinishedOperationError(String messageError) {
        viewPanelStorage.showMessageDialog(messageError);
    }
}