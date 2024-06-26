package integration_test.storage_view;

import dyds.tvseriesinfo.model.ModelFactory;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDSaver;
import dyds.tvseriesinfo.model.database.repository.SeriesRepository;
import dyds.tvseriesinfo.presenter.PresenterFactory;
import dyds.tvseriesinfo.presenter.storage.PresenterSaveChangesSeries;
import dyds.tvseriesinfo.view.ViewFactory;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;
import org.junit.Before;
import org.junit.Test;
import stubs.StubSeriesRepository;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;

public class PresenterSaveChangesSeriesTest {
    private static final ViewFactory viewFactory = new ViewFactory();
    private static final ModelFactory modelFactory = new ModelFactory();
    private static final PresenterFactory presenterFactory = new PresenterFactory(viewFactory, modelFactory);

    private static PresenterSaveChangesSeries presenterSaveChangesSeries;
    private static ModelSeriesCRUDSaver seriesSaver;
    private static ViewPanelStorage viewPanelStorage;
    private static SeriesRepository sqlCRUDStub;

    @Before
    public void setUp() {
        sqlCRUDStub = new StubSeriesRepository();
        seriesSaver = modelFactory.getSeriesCRUDSaver();
        seriesSaver.setSeriesRepository(sqlCRUDStub);
        presenterSaveChangesSeries = (PresenterSaveChangesSeries) presenterFactory.createPresenterSaveChangesSeries();
        viewPanelStorage = viewFactory.getViewPanelStorage();
        viewPanelStorage.setActiveMessageDialog(false);
        presenterFactory.createPresenterLoadLocalSeries();
        presenterFactory.createPresenterGetterSeries();
    }

    @Test
    public void testNotifiedSaveChangesSeries() {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        seriesSaver.addListener(OperationType.SAVE_CHANGES, () -> isNotified.set(true));
        String title = "Batman (TV series)";
        viewPanelStorage.getSeriesComboBox().setSelectedItem(title);
        viewPanelStorage.getTextPaneDetailsSeries().setText("<h1>Batman (TV series)</h1><p><i><b>Batman</b></i> is an American live-action television series based on the DC Comics character of the same name. It stars Adam West as Bruce Wayne/Batman and Burt Ward as Dick Grayson/Robin&mdash;two crime-fighting heroes who defend Gotham City from a variety of archvillains. It is known for its camp style and upbeat theme music, as well as its intentionally humorous, simplistic morality aimed at its preteen audience. It was described by executive producer William Dozier at the time as &quot;the only situation comedy on the air without a laugh track&quot;. The 120 episodes aired on the ABC network for three seasons from January 12, 1966, to March 14, 1968, twice weekly during the first two seasons, and weekly for the third. In 2016, television critics Alan Sepinwall and Matt Zoller Seitz ranked <i>Batman</i> as the 82nd greatest American television series of all time. A companion feature film was released in 1966 between the first and second seasons of the TV show.</p><p><i>Batman</i> held the record for the longest-running live-action superhero television series (in terms of episodes) until it was surpassed by <i>Smallville</i> in 2007.</p><a href=\"https://en.wikipedia.org/wiki/Batman_(TV_series)\">OPEN IN BROWSER!</a>\n");
        presenterSaveChangesSeries.onEvent();
        assertTrue(isNotified.get());
    }
}
