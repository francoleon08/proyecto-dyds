package dyds.tvseriesinfo.presenter.rated;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.ratedSeries.RatedSeriesCRUDGetter;
import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;

import javax.swing.*;
import java.util.ArrayList;

public class PresenterLoadLocalRatedSeries implements Presenter {
    private final RatedSeriesCRUDGetter ratedSeriesCRUDGetter;
    private final ViewPanelPuntuaction viewPanelPuntuaction;
    private Thread taskThread;

    public PresenterLoadLocalRatedSeries(ViewPanelPuntuaction viewPanelPuntuaction, RatedSeriesCRUDGetter ratedSeriesCRUDGetter) {
        this.ratedSeriesCRUDGetter = ratedSeriesCRUDGetter;
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
        ArrayList<RatedSeries> ratedSeries = ratedSeriesCRUDGetter.getLastRatedSeries();
        DefaultListModel<RatedSeries> listModel = createItemsToRatedSeriesList(ratedSeries);
        viewPanelPuntuaction.setRatedSeriesList(listModel);
        viewPanelPuntuaction.setWorkingState(false);
    }

    private DefaultListModel<RatedSeries> createItemsToRatedSeriesList(ArrayList<RatedSeries> ratedSeries) {
        DefaultListModel<RatedSeries> listModel = new DefaultListModel<>();
        for (RatedSeries ratedSerie : ratedSeries) {
            listModel.addElement(ratedSerie);
        }
        return listModel;
    }
}
