package integration_test.search_view;

import dyds.tvseriesinfo.model.ModelFactory;
import dyds.tvseriesinfo.model.database.SQLmanager.SQLCRUD;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDGetter;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDSaver;
import dyds.tvseriesinfo.presenter.PresenterFactory;
import dyds.tvseriesinfo.presenter.search.PresenterSaveSeries;
import dyds.tvseriesinfo.view.ViewFactory;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

import org.junit.Before;

import org.junit.Test;
import stubs.StubSQLCRUD;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;

public class PresenterSaveSeriesTest {
    private static final ViewFactory viewFactory = new ViewFactory();
    private static final ModelFactory modelFactory = new ModelFactory();
    private static final PresenterFactory presenterFactory = new PresenterFactory(viewFactory, modelFactory);

    private static PresenterSaveSeries presenterSaveSeries;
    private static ModelSeriesCRUDSaver seriesSaver;
    private static ModelSeriesCRUDGetter seriesGetter;
    private static ViewPanelSearch viewPanelSearch;
    private static ViewPanelStorage viewPanelStorage;
    private static SQLCRUD sqlcrudStub;

    @Before
    public void setUp() {
        sqlcrudStub = new StubSQLCRUD();
        seriesGetter = modelFactory.getSeriesCRUDGetter();
        seriesSaver = modelFactory.getSeriesCRUDSaver();
        seriesGetter.setSqlCRUD(sqlcrudStub);
        seriesSaver.setSqlCRUD(sqlcrudStub);
        presenterSaveSeries = (PresenterSaveSeries) presenterFactory.createPresenterSaveSeries();
        viewPanelSearch = viewFactory.getViewPanelSearch();
        viewPanelStorage = viewFactory.getViewPanelStorage();
        viewPanelSearch.setActiveMessageDialog(false);
        viewPanelStorage.setActiveMessageDialog(false);
        presenterFactory.createPresenterLoadLocalSeries();
    }

    @Test
    public void testSaveSeriesThenLoadComboBoxStorage() {
        String title = "Batman (TV series)";
        viewPanelSearch.setSelectedResultTitle(title);
        viewPanelSearch.getResultTextToSearchHTML().setText("<h1>Batman (TV series)</h1><p><i><b>Batman</b></i> is an American live-action television series based on the DC Comics character of the same name. It stars Adam West as Bruce Wayne/Batman and Burt Ward as Dick Grayson/Robin&mdash;two crime-fighting heroes who defend Gotham City from a variety of archvillains. It is known for its camp style and upbeat theme music, as well as its intentionally humorous, simplistic morality aimed at its preteen audience. It was described by executive producer William Dozier at the time as &quot;the only situation comedy on the air without a laugh track&quot;. The 120 episodes aired on the ABC network for three seasons from January 12, 1966, to March 14, 1968, twice weekly during the first two seasons, and weekly for the third. In 2016, television critics Alan Sepinwall and Matt Zoller Seitz ranked <i>Batman</i> as the 82nd greatest American television series of all time. A companion feature film was released in 1966 between the first and second seasons of the TV show.</p><p><i>Batman</i> held the record for the longest-running live-action superhero television series (in terms of episodes) until it was surpassed by <i>Smallville</i> in 2007.</p><a href=\"https://en.wikipedia.org/wiki/Batman_(TV_series)\">OPEN IN BROWSER!</a>\n");

        presenterSaveSeries.onEvent();
        JComboBox<String> comboBoxStorage = viewPanelStorage.getSeriesComboBox();
        for (int i = 0; i < comboBoxStorage.getItemCount(); i++) {
            if (comboBoxStorage.getItemAt(i).equals(title)) {
                assert true;
                return;
            }
        }
    }
}
