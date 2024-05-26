package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCrudGetter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

public class PresenterGetterSeries implements Presenter {
    private final ViewPanelStorage viewPanelStorage;
    private final SeriesCrudGetter seriesGetter;
    private Thread taskThread;

    public PresenterGetterSeries(ViewPanelStorage viewPanelStorage) {
        this.viewPanelStorage = viewPanelStorage;
        this.seriesGetter = SeriesCrudGetter.getInstance();
        this.viewPanelStorage.setPresenterGetterSeries(this);
        loadSeriesInViewPanelStorage();
        initListener();
    }

    public void loadSeriesInViewPanelStorage() {
        seriesGetter.getTitlesSeries();
        viewPanelStorage.setSeriesComboBox(seriesGetter.getLastTitlesSeries().stream().sorted().toArray());
    }

    private void initListener() {
        seriesGetter.addListener(OperationType.GET, this);
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleGetExtraxtSeries);
        taskThread.start();
    }

    private void handleGetExtraxtSeries() {
        viewPanelStorage.setWorkingState(true);
        seriesGetter.getExtractSeriesByTitle(viewPanelStorage.getItemSelectedComboBox());
    }

    @Override
    public void hasFinishedOperation() {
        viewPanelStorage.setDetailsSeries(seriesGetter.getLastSeriesExtactByTitle());
        viewPanelStorage.setWorkingState(false);
    }
}
