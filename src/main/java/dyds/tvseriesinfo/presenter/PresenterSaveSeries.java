package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.SeriesCRUDSaver;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;

import java.util.Objects;

public class PresenterSaveSeries implements Presenter {
    private final ViewPanelSearch viewPanelSearch;
    private final SeriesCRUDSaver seriesSaver;
    private final PresenterGetterSeries presenterGetterSeries;
    private Thread taskThread;

    public PresenterSaveSeries(ViewPanelSearch viewPanelSearch, PresenterGetterSeries presenterGetterSeries) {
        this.viewPanelSearch = viewPanelSearch;
        this.seriesSaver = SeriesCRUDSaver.getInstance();
        this.presenterGetterSeries = presenterGetterSeries;
        this.viewPanelSearch.setPresenterSaveSeries(this);
        initListener();
    }

    private void initListener() {
        seriesSaver.addListener(OperationType.SAVE, this);
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleSaveSeries);
        taskThread.start();
    }

    private void handleSaveSeries() {
        String resultTextToSearch = viewPanelSearch.getResultTextToSearch();
        if (isValidForSave(resultTextToSearch)) {
            doSaveSeries(resultTextToSearch);
        } else {
            hasFinishedOperationError("No seleccionó ninguna serie para guardar");
        }
    }

    private boolean isValidForSave(String resultTextToSearch) {
        return !Objects.equals(resultTextToSearch, "");
    }

    private void doSaveSeries(String resultTextToSearch) {
        try {
            seriesSaver.saveSeries(getSeriesTitle(), resultTextToSearch);
        } catch (SeriesSaveException e) {
            hasFinishedOperationError(e.getMessage());
        }
    }

    private String getSeriesTitle() {
        return viewPanelSearch.getSelectedResultTitle().replace("'", "`");
    }

    @Override
    public void hasFinishedOperationSucces() {
        presenterGetterSeries.loadSeriesInViewPanelStorage();
        viewPanelSearch.showMessageDialog("La serie se ha guardado correctamente.");
    }

    private void hasFinishedOperationError(String messageError) {
        viewPanelSearch.showMessageDialog(messageError);
    }
}