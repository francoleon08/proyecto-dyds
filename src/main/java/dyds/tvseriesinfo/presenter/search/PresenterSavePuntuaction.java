package dyds.tvseriesinfo.presenter.search;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.ratedSeries.RatedModelSeriesCRUDSaver;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;

public class PresenterSavePuntuaction implements Presenter {
    private final ViewPanelSearch viewPanelSearch;
    private final ViewPanelPuntuaction viewPanelPuntuaction;
    private final RatedModelSeriesCRUDSaver ratedSeriesCRUDSaver;
    private Thread taskThread;

    public PresenterSavePuntuaction(ViewPanelSearch viewPanelSearch, ViewPanelPuntuaction viewPanelPuntuaction, RatedModelSeriesCRUDSaver ratedSeriesCRUDSaver) {
        this.viewPanelSearch = viewPanelSearch;
        this.viewPanelPuntuaction = viewPanelPuntuaction;
        this.ratedSeriesCRUDSaver = ratedSeriesCRUDSaver;
        this.viewPanelSearch.setPresenterSavePuntuaction(this);
        this.viewPanelPuntuaction.setPresenterSearchRatingSeries(this);
        initListener();
    }

    private void initListener() {
        ratedSeriesCRUDSaver.addListener(OperationType.SAVE_RATED, this);
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleSaveRating);
        taskThread.start();
    }

    private void handleSaveRating() {
        viewPanelPuntuaction.setWorkingState(true);
        if (viewPanelSearch.isCheckBoxSelected()) {
            doSaveRating();
        } else {
            hasFinishedOperationError("You did not select a series to save the rating.");
        }
    }

    private void doSaveRating() {
        try {
            ratedSeriesCRUDSaver.saveRatedSeries(viewPanelSearch.getSelectedResultTitle(), Integer.parseInt(viewPanelSearch.getSelectedPuntuaction()));
        } catch (Exception e) {
            hasFinishedOperationError(e.getMessage());
        }
    }

    private void hasFinishedOperationError(String messageError) {
        viewPanelPuntuaction.showMessageDialog(messageError);
        viewPanelPuntuaction.setWorkingState(false);
    }

    @Override
    public void hasFinishedOperationSucces() {
        viewPanelPuntuaction.showMessageDialog("The rating was saved successfully.");
        viewPanelPuntuaction.setWorkingState(false);
    }
}
