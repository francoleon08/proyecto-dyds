package dyds.tvseriesinfo.presenter.storage;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDGetter;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

public class PresenterGetterSeries implements Presenter {
    private final ViewPanelStorage viewPanelStorage;
    private final ModelSeriesCRUDGetter seriesGetter;

    public PresenterGetterSeries(ViewPanelStorage viewPanelStorage, ModelSeriesCRUDGetter seriesGetter) {
        this.viewPanelStorage = viewPanelStorage;
        this.seriesGetter = seriesGetter;
        this.viewPanelStorage.setPresenterGetterSeries(this);
        initListener();
    }

    private void initListener() {
        seriesGetter.addListener(OperationType.GET_SERIES, this);
    }

    @Override
    public void onEvent() {
        viewPanelStorage.setWorkingState(true);
        doGetExtractSeries();
    }

    private void doGetExtractSeries() {
        try {
            seriesGetter.getExtractSeriesByTitle(viewPanelStorage.getItemSelectedComboBox());
        } catch (SeriesSearchException e) {
            hasFinishedOperationError(e.getMessage());
        }
    }

    @Override
    public void hasFinishedOperationSucces() {
        viewPanelStorage.setDetailsSeries(seriesGetter.getLastSeriesExtactByTitle());
        viewPanelStorage.setWorkingState(false);
    }

    private void hasFinishedOperationError(String messageError) {
        viewPanelStorage.showMessageDialog(messageError);
    }

}
