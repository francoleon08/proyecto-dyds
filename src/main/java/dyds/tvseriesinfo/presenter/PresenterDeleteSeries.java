package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.database.ListenerModelSeries;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCrudDeleter;
import dyds.tvseriesinfo.model.database.crud.SeriesCrudGetter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;
import lombok.Getter;
import lombok.Setter;

public class PresenterDeleteSeries implements Presenter, ListenerModelSeries {
    @Setter @Getter
    private ViewPanelStorage viewPanelStorage;
    private SeriesCrudDeleter seriesDeleter;
    private SeriesCrudGetter seriesGetter;

    private Thread taskThread;

    public PresenterDeleteSeries(ViewPanelStorage viewPanelStorage, SeriesCrudDeleter seriesDeleter, SeriesCrudGetter seriesGetter) {
        this.viewPanelStorage = viewPanelStorage;
        this.seriesDeleter = seriesDeleter;
        this.seriesGetter = seriesGetter;
        initListener();
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleDeleteSeries);
        taskThread.start();
    }

    private void handleDeleteSeries() {
        viewPanelStorage.setWorkingState(true);
        if(viewPanelStorage.isItemSelected()){
            seriesDeleter.deleteSeriesByTitle(viewPanelStorage.getItemSelectedComboBox());
        }
    }

    private void initListener() {
        seriesDeleter.addListener(OperationType.DELETE, this);
    }

    @Override
    public void hasFinishedOperation() {
        viewPanelStorage.setSeriesComboBox(seriesGetter.getTitlesSeries().stream().sorted().toArray());
        viewPanelStorage.setDetailsSeries("");
        viewPanelStorage.setWorkingState(false);
    }
}
