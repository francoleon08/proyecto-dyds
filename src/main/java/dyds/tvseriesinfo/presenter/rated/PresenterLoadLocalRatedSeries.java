package dyds.tvseriesinfo.presenter.rated;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.RatedSeriesCRUDGetter;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;

public class PresenterLoadLocalRatedSeries implements Presenter {
    private RatedSeriesCRUDGetter ratedSeriesCRUDGetter;
    private ViewPanelPuntuaction viewPanelPuntuaction;
    private Thread taskThread;

    public PresenterLoadLocalRatedSeries(ViewPanelPuntuaction viewPanelPuntuaction) {
        this.ratedSeriesCRUDGetter = RatedSeriesCRUDGetter.getInstance();
        this.viewPanelPuntuaction = viewPanelPuntuaction;
        initListener();
        onEvent();
    }

    private void initListener() {
        ratedSeriesCRUDGetter.addListener(OperationType.LOAD_RATED_SERIES, this);
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleLoadRatedSeries);
        taskThread.start();
    }

    private void handleLoadRatedSeries() {
        try {
            viewPanelPuntuaction.setWorkingState(true);
            ratedSeriesCRUDGetter.getRatedSeries();
        } catch (SearchRatedSeriesException e) {
            hasFinishedOperationError(e.getMessage());
        }
    }

    private void hasFinishedOperationError(String message) {
        viewPanelPuntuaction.setWorkingState(false);
        viewPanelPuntuaction.showMessageDialog(message);
    }

    @Override
    public void hasFinishedOperationSucces() {
        viewPanelPuntuaction.setRatedSeriesComboBox(ratedSeriesCRUDGetter.getLastRatedSeries().toArray());
        viewPanelPuntuaction.setWorkingState(false);
    }
}
