package dyds.tvseriesinfo.presenter.search;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDSaver;
import dyds.tvseriesinfo.model.exceptions.SeriesSaveException;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import lombok.Setter;

import java.util.Objects;

@Setter
public class PresenterSaveSeries implements Presenter {
    private ViewPanelSearch viewPanelSearch;
    private ModelSeriesCRUDSaver seriesSaver;

    public PresenterSaveSeries(ViewPanelSearch viewPanelSearch, ModelSeriesCRUDSaver seriesSaver) {
        this.viewPanelSearch = viewPanelSearch;
        this.seriesSaver = seriesSaver;
        this.viewPanelSearch.setPresenterSaveSeries(this);
        initListener();
    }

    private void initListener() {
        seriesSaver.addListener(OperationType.SAVE_SERIES, this);
    }

    @Override
    public void onEvent() {
        String resultTextToSearch = viewPanelSearch.getResultTextToSearchHTML().getText();
        if (isValidForSave(resultTextToSearch)) {
            doSaveSeries(resultTextToSearch);
        } else {
            hasFinishedOperationError("You did not select any series to save.");
        }
    }

    private boolean isValidForSave(String resultTextToSearch) {
        return !Objects.equals(resultTextToSearch, "");
    }

    private void doSaveSeries(String resultTextToSearch) {
        try {
            seriesSaver.saveSeries(getSeriesTitle(), resultTextToSearch);
        } catch (SeriesSaveException | SeriesSearchException e) {
            hasFinishedOperationError(e.getMessage());
        }
    }

    private String getSeriesTitle() {
        return viewPanelSearch.getSelectedResultTitle().replace("'", "`");
    }

    @Override
    public void hasFinishedOperationSucces() {
        viewPanelSearch.showMessageDialog("The series has been saved successfully.");
    }

    private void hasFinishedOperationError(String messageError) {
        viewPanelSearch.showMessageDialog(messageError);
    }
}