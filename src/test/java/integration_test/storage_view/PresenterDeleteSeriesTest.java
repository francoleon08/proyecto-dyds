package integration_test.storage_view;

import dyds.tvseriesinfo.model.ModelFactory;
import dyds.tvseriesinfo.model.database.SQLmanager.SQLCRUD;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDDeleter;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDGetter;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.presenter.PresenterFactory;
import dyds.tvseriesinfo.view.ViewFactory;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;
import org.junit.Before;
import org.junit.Test;
import stubs.StubSQLCRUD;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class PresenterDeleteSeriesTest {
    private static final ViewFactory viewFactory = new ViewFactory();
    private static final ModelFactory modelFactory = new ModelFactory();
    private static final PresenterFactory presenterFactory = new PresenterFactory(viewFactory, modelFactory);
    private Presenter presenterDeleteSeries;
    private ModelSeriesCRUDDeleter seriesDeleter;
    private ModelSeriesCRUDGetter seriesGetter;
    private ViewPanelStorage viewPanelStorage;
    private SQLCRUD sqlCRUDStub;

    @Before
    public void setUp() {
        sqlCRUDStub = new StubSQLCRUD();
        seriesDeleter = modelFactory.getSeriesCRUDDeleter();
        seriesGetter = modelFactory.getSeriesCRUDGetter();
        seriesDeleter.setSqlCRUD(sqlCRUDStub);
        seriesGetter.setSqlCRUD(sqlCRUDStub);
        viewPanelStorage = viewFactory.getViewPanelStorage();
        presenterDeleteSeries = presenterFactory.createPresenterDeleteSeries();
        presenterFactory.createPresenterLoadLocalSeries();
        presenterFactory.createPresenterGetterSeries();
    }

    @Test
    public void testNotifiedListenerAfterDeleteSeries() {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        seriesGetter.addListener(OperationType.LOAD_LOCAL_SERIES_AFTER_CHANGE, () -> isNotified.set(true));
        viewPanelStorage.getSeriesComboBox().setSelectedIndex(0);
        presenterDeleteSeries.onEvent();
        assertTrue(isNotified.get());
    }

    @Test
    public void testLoadSeriesAfterDeleteSeries() {
        int actual = 0;
        viewPanelStorage.getSeriesComboBox().setSelectedIndex(0);
        presenterDeleteSeries.onEvent();
        actual = viewPanelStorage.getSeriesComboBox().getItemCount();
        assertEquals(5, actual);
    }

    @Test
    public void testCleanJTextFieldAfterDeleteSeries() {
        int expected, actual;
        String detailsSeries = "Details series 1";
        viewPanelStorage.setDetailsSeries(detailsSeries);
        expected = viewPanelStorage.getDetailsSeries().length();
        viewPanelStorage.getSeriesComboBox().setSelectedIndex(0);
        presenterDeleteSeries.onEvent();
        actual = viewPanelStorage.getDetailsSeries().length();
        assertNotEquals(expected, actual);
    }
}
