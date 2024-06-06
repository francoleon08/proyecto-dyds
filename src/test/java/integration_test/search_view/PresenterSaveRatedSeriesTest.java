package integration_test.search_view;

import dyds.tvseriesinfo.model.ModelFactory;
import dyds.tvseriesinfo.model.database.SQLmanager.SQLCRUD;
import dyds.tvseriesinfo.model.database.crud.ratedSeries.RatedModelSeriesCRUDGetter;
import dyds.tvseriesinfo.model.database.crud.ratedSeries.RatedModelSeriesCRUDSaver;
import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.presenter.PresenterFactory;
import dyds.tvseriesinfo.presenter.search.PresenterSavePuntuaction;
import dyds.tvseriesinfo.view.ViewFactory;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import org.junit.Before;
import org.junit.Test;
import stubs.StubSQLCRUD;

import javax.swing.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PresenterSaveRatedSeriesTest {
    private static final ViewFactory viewFactory = new ViewFactory();
    private static final ModelFactory modelFactory = new ModelFactory();
    private static final PresenterFactory presenterFactory = new PresenterFactory(viewFactory, modelFactory);

    private static PresenterSavePuntuaction presenterSavePuntuaction;
    private static RatedModelSeriesCRUDSaver seriesSaver;
    private static RatedModelSeriesCRUDGetter seriesGetter;
    private static ViewPanelSearch viewPanelSearch;
    private static ViewPanelPuntuaction viewPanelPuntuaction;
    private static SQLCRUD sqlcrudStub;

    @Before
    public void setUp() {
        sqlcrudStub = new StubSQLCRUD();
        seriesGetter = modelFactory.getRatedSeriesCRUDGetter();
        seriesSaver = modelFactory.getRatedSeriesCRUDSaver();
        seriesGetter.setSqlCRUD(sqlcrudStub);
        seriesSaver.setSqlCRUD(sqlcrudStub);

        presenterSavePuntuaction = (PresenterSavePuntuaction) presenterFactory.createPresenterSavePuntuaction();
        viewPanelSearch = viewFactory.getViewPanelSearch();
        viewPanelPuntuaction = viewFactory.getViewPanelPuntuaction();
        viewPanelSearch.setActiveMessageDialog(false);
        viewPanelPuntuaction.setActiveMessageDialog(false);
        presenterFactory.createPresenterLoadLocalRatedSeries();
    }

    @Test
    public void testSaveRatedSeriesThenLoadListRatedSeries() {
        String title = "Batman (TV series)";
        viewPanelSearch.setSelectedResultTitle(title);
        viewPanelSearch.getResultTextToSearchHTML().setText("<h1>Batman (TV series)</h1><p><i><b>Batman</b></i> is an American live-action television series based on the DC Comics character of the same name. It stars Adam West as Bruce Wayne/Batman and Burt Ward as Dick Grayson/Robin&mdash;two crime-fighting heroes who defend Gotham City from a variety of archvillains. It is known for its camp style and upbeat theme music, as well as its intentionally humorous, simplistic morality aimed at its preteen audience. It was described by executive producer William Dozier at the time as &quot;the only situation comedy on the air without a laugh track&quot;. The 120 episodes aired on the ABC network for three seasons from January 12, 1966, to March 14, 1968, twice weekly during the first two seasons, and weekly for the third. In 2016, television critics Alan Sepinwall and Matt Zoller Seitz ranked <i>Batman</i> as the 82nd greatest American television series of all time. A companion feature film was released in 1966 between the first and second seasons of the TV show.</p><p><i>Batman</i> held the record for the longest-running live-action superhero television series (in terms of episodes) until it was surpassed by <i>Smallville</i> in 2007.</p><a href=\"https://en.wikipedia.org/wiki/Batman_(TV_series)\">OPEN IN BROWSER!</a>\n");
        viewPanelSearch.getCheckBoxEnablePuntuaction().setSelected(true);
        viewPanelSearch.getComboBoxPuntaction().setSelectedIndex(5);

        presenterSavePuntuaction.onEvent();
        JList<RatedSeries> comboBoxStorage = viewPanelPuntuaction.getListRatedSeriesPanel();
        assertEquals(3, comboBoxStorage.getModel().getSize());
    }

    @Test
    public void testSaveRatedSeriesThenLoadListRatedSeriesOrdered() {
        String title = "Batman (TV series)";
        viewPanelSearch.setSelectedResultTitle(title);
        viewPanelSearch.getResultTextToSearchHTML().setText("<h1>Batman (TV series)</h1><p><i><b>Batman</b></i> is an American live-action television series based on the DC Comics character of the same name. It stars Adam West as Bruce Wayne/Batman and Burt Ward as Dick Grayson/Robin&mdash;two crime-fighting heroes who defend Gotham City from a variety of archvillains. It is known for its camp style and upbeat theme music, as well as its intentionally humorous, simplistic morality aimed at its preteen audience. It was described by executive producer William Dozier at the time as &quot;the only situation comedy on the air without a laugh track&quot;. The 120 episodes aired on the ABC network for three seasons from January 12, 1966, to March 14, 1968, twice weekly during the first two seasons, and weekly for the third. In 2016, television critics Alan Sepinwall and Matt Zoller Seitz ranked <i>Batman</i> as the 82nd greatest American television series of all time. A companion feature film was released in 1966 between the first and second seasons of the TV show.</p><p><i>Batman</i> held the record for the longest-running live-action superhero television series (in terms of episodes) until it was surpassed by <i>Smallville</i> in 2007.</p><a href=\"https://en.wikipedia.org/wiki/Batman_(TV_series)\">OPEN IN BROWSER!</a>\n");
        viewPanelSearch.getCheckBoxEnablePuntuaction().setSelected(true);
        viewPanelSearch.getComboBoxPuntaction().setSelectedIndex(5);

        presenterSavePuntuaction.onEvent();
        JList<RatedSeries> comboBoxStorage = viewPanelPuntuaction.getListRatedSeriesPanel();
        assertTrue(isListSortedByRating(comboBoxStorage.getModel()));
    }

    private boolean isListSortedByRating(ListModel<RatedSeries> listModel) {
        for (int i = 1; i < listModel.getSize(); i++) {
            if (listModel.getElementAt(i - 1).getRating() > listModel.getElementAt(i).getRating()) {
                return false;
            }
        }
        return true;
    }
}
