package integration_test.puntuaction_view;

import dyds.tvseriesinfo.model.ModelFactory;
import dyds.tvseriesinfo.model.database.SQLmanager.SQLCRUD;
import dyds.tvseriesinfo.model.database.crud.ratedSeries.RatedModelSeriesCRUDGetter;
import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.presenter.PresenterFactory;
import dyds.tvseriesinfo.view.ViewFactory;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;
import org.junit.Before;
import org.junit.Test;
import stubs.StubSQLCRUD;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

public class PresenterLoadLocalRatedSeriesTest {
    private static final ViewFactory viewFactory = new ViewFactory();
    private static final ModelFactory modelFactory = new ModelFactory();
    private static final PresenterFactory presenterFactory = new PresenterFactory(viewFactory, modelFactory);

    private Presenter presenterLoadLocalRatedSeries;
    private RatedModelSeriesCRUDGetter ratedSeriesCRUDGetter;
    private ViewPanelPuntuaction viewPanelPuntuaction;

    private static SQLCRUD sqlcrudStub;

    @Before
    public void setUp() {
        sqlcrudStub = new StubSQLCRUD();
        ratedSeriesCRUDGetter = modelFactory.getRatedSeriesCRUDGetter();
        ratedSeriesCRUDGetter.setSqlCRUD(sqlcrudStub);
        viewPanelPuntuaction = viewFactory.getViewPanelPuntuaction();
        viewPanelPuntuaction.setActiveMessageDialog(false);
        presenterLoadLocalRatedSeries = presenterFactory.createPresenterLoadLocalRatedSeries();
    }

    @Test
    public void testLoadLocalRatedSeries() {
        int expectedRatedSeriesSize = 3;
        presenterLoadLocalRatedSeries.onEvent();
        ListModel<RatedSeries> ratedSeriesListModel = viewPanelPuntuaction.getListRatedSeriesPanel().getModel();
        int actualRatedSeriesSize = ratedSeriesListModel.getSize();
        assertEquals(expectedRatedSeriesSize, actualRatedSeriesSize);
    }
}
