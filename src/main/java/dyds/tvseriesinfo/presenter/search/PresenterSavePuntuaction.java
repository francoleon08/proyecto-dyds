package dyds.tvseriesinfo.presenter.search;

import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;

public class PresenterSavePuntuaction implements Presenter {
    private final ViewPanelSearch viewPanelSearch;
    private final ViewPanelPuntuaction viewPanelPuntuaction;
    private Thread taskThread;

    public PresenterSavePuntuaction(ViewPanelSearch viewPanelSearch, ViewPanelPuntuaction viewPanelPuntuaction) {
        this.viewPanelSearch = viewPanelSearch;
        this.viewPanelPuntuaction = viewPanelPuntuaction;
        this.viewPanelSearch.setPresenterSavePuntuaction(this);
    }

    @Override
    public void onEvent() {
        taskThread = new Thread(this::handleSavePuntuaction);
        taskThread.start();
    }

    private void handleSavePuntuaction() {

    }

    @Override
    public void hasFinishedOperationSucces() {

    }
}
