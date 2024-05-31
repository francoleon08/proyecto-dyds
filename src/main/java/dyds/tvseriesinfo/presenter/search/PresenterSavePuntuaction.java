package dyds.tvseriesinfo.presenter.search;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.ratedSeries.RatedSeriesCRUDSaver;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;

public class PresenterSavePuntuaction implements Presenter {
    private final ViewPanelSearch viewPanelSearch;
    private final ViewPanelPuntuaction viewPanelPuntuaction;
    private RatedSeriesCRUDSaver ratedSeriesCRUDSaver;
    private Thread taskThread;

    public PresenterSavePuntuaction(ViewPanelSearch viewPanelSearch, ViewPanelPuntuaction viewPanelPuntuaction) {
        this.viewPanelSearch = viewPanelSearch;
        this.viewPanelPuntuaction = viewPanelPuntuaction;
        this.ratedSeriesCRUDSaver = RatedSeriesCRUDSaver.getInstance();
        this.viewPanelSearch.setPresenterSavePuntuaction(this);
        this.viewPanelPuntuaction.setPresenterSavePutuaction(this);
        initListener();
    }

    private void initListener() {
        ratedSeriesCRUDSaver.addListener(OperationType.SAVE_RATED, this);
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleSavePuntuaction);
        taskThread.start();
    }

    private void handleSavePuntuaction() {
        viewPanelPuntuaction.setWorkingState(true);
        if (viewPanelSearch.isCheckBoxSelected()) {
            doSavePuntuaction();
        } else {
            hasFinishedOperationError("No seleccionó una serie para guardar la puntuación.");
        }
    }

    private void doSavePuntuaction() {
        try {
            ratedSeriesCRUDSaver.saveRatedSeries(viewPanelSearch.getSelectedResultTitle(), Integer.parseInt(viewPanelSearch.getSelectedPuntuaction()));
        } catch (Exception e) {
            hasFinishedOperationError(e.getMessage());
        }
    }

    private void hasFinishedOperationError(String s) {
        viewPanelPuntuaction.showMessageDialog(s);
        viewPanelPuntuaction.setWorkingState(false);
    }

    @Override
    public void hasFinishedOperationSucces() {
        viewPanelPuntuaction.showMessageDialog("Puntuación guardada con éxito.");
        viewPanelPuntuaction.setWorkingState(false);
    }
}
