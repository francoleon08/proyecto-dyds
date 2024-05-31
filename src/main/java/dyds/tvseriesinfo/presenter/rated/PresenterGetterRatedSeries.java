package dyds.tvseriesinfo.presenter.rated;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.ratedSeries.RatedSeriesCRUDGetter;
import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;

public class PresenterGetterRatedSeries implements Presenter {
    private RatedSeriesCRUDGetter ratedSeriesCRUDGetter;
    private ViewPanelPuntuaction viewPanelPuntuaction;
    private Thread taskThread;

    public PresenterGetterRatedSeries(ViewPanelPuntuaction viewPanelPuntuaction) {
        this.ratedSeriesCRUDGetter = RatedSeriesCRUDGetter.getInstance();
        this.viewPanelPuntuaction = viewPanelPuntuaction;
        this.viewPanelPuntuaction.setPresenterGetterRatedSeries(this);
        initListener();
    }

    private void initListener() {
        ratedSeriesCRUDGetter.addListener(OperationType.GET_RATED_SERIES, this);
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleGetRatedSeries);
        taskThread.start();
    }

    private void handleGetRatedSeries() {
        try {
            viewPanelPuntuaction.setWorkingState(true);
            ratedSeriesCRUDGetter.getRatedSeriesByTitle(viewPanelPuntuaction.getItemSelectedComboBox());
        } catch (SearchRatedSeriesException e) {
            hasFinishedOperationError(e.getMessage());
        }
    }

    private void hasFinishedOperationError(String message) {
        viewPanelPuntuaction.showMessageDialog(message);
        viewPanelPuntuaction.setWorkingState(false);
    }

    @Override
    public void hasFinishedOperationSucces() {
        RatedSeries ratedSeries = ratedSeriesCRUDGetter.getLastRatedSerieByTitle();
        viewPanelPuntuaction.setTitleRatedSeries(ratedSeries.getTitle());
        viewPanelPuntuaction.setPuntuactionRadetSeries(String.valueOf(ratedSeries.getRating()));
        viewPanelPuntuaction.setLastModificationRatedSeries(ratedSeries.getDateModified() + " - " + ratedSeries.getTimeModified());
        viewPanelPuntuaction.setWorkingState(false);
    }
}
