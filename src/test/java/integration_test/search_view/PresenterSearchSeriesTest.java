package integration_test.search_view;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dyds.tvseriesinfo.model.ModelFactory;
import dyds.tvseriesinfo.model.apiConsummer.WikipediaAPIService;
import dyds.tvseriesinfo.model.database.crud.series.ModelWikipediaAPI;
import dyds.tvseriesinfo.presenter.PresenterFactory;
import dyds.tvseriesinfo.presenter.search.PresenterSearchSeries;
import dyds.tvseriesinfo.view.ViewFactory;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

public class PresenterSearchSeriesTest {
    private static final ViewFactory viewFactory = new ViewFactory();
    private static final ModelFactory modelFactory = new ModelFactory();
    private static final PresenterFactory presenterFactory = new PresenterFactory(viewFactory, modelFactory);

    private PresenterSearchSeries presenterSearchSeries;
    private ModelWikipediaAPI modelWikipediaAPI;
    @Mock
    private WikipediaAPIService wikipediaAPIServiceMock;
    private ViewPanelSearch viewPanelSearch;



    @Before
    public void setUp() {
        modelWikipediaAPI = modelFactory.getModelWikipediaAPI();
        wikipediaAPIServiceMock = mock(WikipediaAPIService.class);
        modelWikipediaAPI.setWikipediaAPIService(wikipediaAPIServiceMock);

        viewPanelSearch =  viewFactory.getViewPanelSearch();
        viewPanelSearch.setActiveMessageDialog(false);

        presenterSearchSeries = (PresenterSearchSeries) presenterFactory.createPresenterSearchSeries();
        presenterSearchSeries.setViewPanelSearch(viewPanelSearch);
    }

    @Test
    public void testUpdateViewBeforeSearchSeries() throws IOException {
        String termToSearch = "Batman";
        int lengthOriginalHTMLText = viewPanelSearch.getResultTextToSearchHTML().getText().length();
        int lengthHTMLTextBeforeSearch;
        String jsonResult = "[{\"ns\":0,\"title\":\"Batman (TV series)\",\"pageid\":298705,\"size\":98992,\"wordcount\":12183,\"snippet\":\"<span class=\\\"searchmatch\\\">Batman</span> is an American live-action television <span class=\\\"searchmatch\\\">series</span> based on the DC Comics character of the same name. It stars Adam West as Bruce Wayne/<span class=\\\"searchmatch\\\">Batman</span> and Burt\",\"timestamp\":\"2024-06-06T03:30:54Z\"},{\"ns\":0,\"title\":\"The Batman (TV series)\",\"pageid\":1241786,\"size\":30254,\"wordcount\":3121,\"snippet\":\"The <span class=\\\"searchmatch\\\">Batman</span> is an American animated television <span class=\\\"searchmatch\\\">series</span> based on the DC Comics superhero <span class=\\\"searchmatch\\\">Batman</span>. Developed by Michael Goguen and Duane Capizzi, and produced\",\"timestamp\":\"2024-05-10T14:40:29Z\"},{\"ns\":0,\"title\":\"Batman: The Animated Series\",\"pageid\":171478,\"size\":143935,\"wordcount\":13420,\"snippet\":\"<span class=\\\"searchmatch\\\">Batman</span>: The Animated <span class=\\\"searchmatch\\\">Series</span> (often shortened as <span class=\\\"searchmatch\\\">Batman</span> TAS or BTAS) is an American animated superhero television <span class=\\\"searchmatch\\\">series</span> based on the DC Comics superhero\",\"timestamp\":\"2024-06-05T16:48:39Z\"},{\"ns\":0,\"title\":\"List of Batman: The Animated Series episodes\",\"pageid\":5320361,\"size\":68237,\"wordcount\":551,\"snippet\":\"<span class=\\\"searchmatch\\\">Batman</span>: The Animated <span class=\\\"searchmatch\\\">Series</span> is an American television <span class=\\\"searchmatch\\\">series</span> produced by Warner Bros. Animation based on the DC Comics superhero <span class=\\\"searchmatch\\\">Batman</span>. Originally, 85\",\"timestamp\":\"2024-05-04T00:46:29Z\"},{\"ns\":0,\"title\":\"Londinium (Batman)\",\"pageid\":12525445,\"size\":7444,\"wordcount\":857,\"snippet\":\"The television <span class=\\\"searchmatch\\\">series</span> <span class=\\\"searchmatch\\\">Batman</span> aired a three-part <span class=\\\"searchmatch\\\">series</span> of episodes in 1967 during its third season: &quot;The Londinium Larcenies&quot;, &quot;The Foggiest Notion&quot;,\",\"timestamp\":\"2024-03-06T12:00:04Z\"}]\n";
        String extractElement = "\"<p class=\\\"mw-empty-elt\\\">\\n</p>\\n\\n<p><i><b>The 100</b></i> (pronounced <i><b>The Hundred</b></i> ) is an American post-apocalyptic science fiction drama television series that premiered on March 19, 2014, on The CW, and ended on September 30, 2020. Developed by Jason Rothenberg, the series is loosely based on the young adult novel series of the same name by Kass Morgan. <i>The 100</i> follows post-apocalyptic survivors from a space habitat, the <i>Ark</i>, who return to Earth nearly a century after a devastating nuclear apocalypse. The first people sent to Earth are a group of juvenile delinquents who encounter descendants of survivors of the nuclear disaster on the ground.\\n</p><p>The juvenile delinquents include Clarke Griffin (Eliza Taylor), Finn Collins (Thomas McDonell), Bellamy Blake (Bob Morley), Octavia Blake (Marie Avgeropoulos), Jasper Jordan (Devon Bostick), Monty Green (Christopher Larkin), and John Murphy (Richard Harmon). Other lead characters include Clarke's mother Dr. Abby Griffin (Paige Turco), Marcus Kane (Henry Ian Cusick), and Chancellor Thelonious Jaha (Isaiah Washington), all of whom are council members on the <i>Ark</i>, and Raven Reyes (Lindsey Morgan), a mechanic aboard the <i>Ark</i>.\\n</p>\"";
        JsonArray jsonResultsSeries = JsonParser.parseString(jsonResult).getAsJsonArray();
        JsonElement jsonElement = JsonParser.parseString(extractElement).deepCopy();
        Mockito.when(wikipediaAPIServiceMock.searchForTerm(anyString(), anyInt())).thenReturn(jsonResultsSeries);
        Mockito.when(wikipediaAPIServiceMock.getSeriesExtractByPageID(anyString())).thenReturn(jsonElement);

        viewPanelSearch.getSeriesToSearchTextField().setText(termToSearch);
        executedSearchSeries();
        simulateClickJPopupMenu();
        lengthHTMLTextBeforeSearch = viewPanelSearch.getResultTextToSearchHTML().getText().length();

        assertEquals(termToSearch, viewPanelSearch.getSeriesToSearchTextField().getText());
        assertNotEquals(lengthOriginalHTMLText, lengthHTMLTextBeforeSearch);
    }

    private void simulateClickJPopupMenu() {
        JPopupMenu searchOptionsMenu = viewPanelSearch.getSearchOptionsMenu();
        JMenuItem firstItem = (JMenuItem) searchOptionsMenu.getComponent(0);
        firstItem.doClick();
    }

    private void executedSearchSeries() {
        try {
            presenterSearchSeries.onEvent();
        } catch (IllegalComponentStateException ignored) {}
    }
}
