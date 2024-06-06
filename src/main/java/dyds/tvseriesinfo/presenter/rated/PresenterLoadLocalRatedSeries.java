package dyds.tvseriesinfo.presenter.rated;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.ratedSeries.RatedModelSeriesCRUDGetter;
import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;

import javax.swing.*;
import java.util.ArrayList;

public class PresenterLoadLocalRatedSeries implements Presenter {
    private final RatedModelSeriesCRUDGetter ratedSeriesCRUDGetter;
    private final ViewPanelPuntuaction viewPanelPuntuaction;

    public PresenterLoadLocalRatedSeries(ViewPanelPuntuaction viewPanelPuntuaction, RatedModelSeriesCRUDGetter ratedSeriesCRUDGetter) {
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
        try {
            viewPanelPuntuaction.setWorkingState(true);
            ratedSeriesCRUDGetter.getRatedSeries();
        } catch (SearchRatedSeriesException e) {
            hasFinishedOperationError(e.getMessage());
        }
    }

    private void hasFinishedOperationError(String messageError) {
        viewPanelPuntuaction.setWorkingState(false);
        viewPanelPuntuaction.showMessageDialog(messageError);
    }

    @Override
    public void hasFinishedOperationSucces() {
        ArrayList<RatedSeries> ratedSeries = ratedSeriesCRUDGetter.getLastRatedSeries();
        DefaultListModel<RatedSeries> listModel = createModelListToRatedSeriesList(ratedSeries);
        viewPanelPuntuaction.setRatedSeriesList(listModel);
        viewPanelPuntuaction.setWorkingState(false);
    }

    private DefaultListModel<RatedSeries> createModelListToRatedSeriesList(ArrayList<RatedSeries> ratedSeries) {
        DefaultListModel<RatedSeries> listModel = new DefaultListModel<>();
        for (RatedSeries itemRatedSeries : ratedSeries) {
            listModel.addElement(itemRatedSeries);
        }
        return listModel;
    }
}
