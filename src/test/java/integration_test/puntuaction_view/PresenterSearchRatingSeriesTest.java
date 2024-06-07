package integration_test.puntuaction_view;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dyds.tvseriesinfo.model.ModelFactory;
import dyds.tvseriesinfo.model.apiConsummer.WikipediaAPIService;
import dyds.tvseriesinfo.model.database.crud.series.ModelWikipediaAPI;
import dyds.tvseriesinfo.model.database.repository.SeriesRepository;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.presenter.PresenterFactory;
import dyds.tvseriesinfo.view.ViewFactory;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import stubs.StubSeriesRepository;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

public class PresenterSearchRatingSeriesTest {
    private static final ViewFactory viewFactory = new ViewFactory();
    private static final ModelFactory modelFactory = new ModelFactory();
    private static final PresenterFactory presenterFactory = new PresenterFactory(viewFactory, modelFactory);

    private Presenter presenterSearchRatingSeries;
    private ModelWikipediaAPI modelWikipediaAPI;
    @Mock
    private WikipediaAPIService wikipediaAPIServiceMock;
    private ViewPanelPuntuaction viewPanelPuntuaction;
    private ViewPanelSearch viewPanelSearch;

    private SeriesRepository seriesRepository;

    @Before
    public void setUp() {
        seriesRepository = new StubSeriesRepository();
        modelFactory.getRatedSeriesCRUDGetter().setSeriesRepository(seriesRepository);

        modelWikipediaAPI = modelFactory.getModelWikipediaAPI();
        wikipediaAPIServiceMock = mock(WikipediaAPIService.class);
        modelWikipediaAPI.setWikipediaAPIService(wikipediaAPIServiceMock);

        viewPanelPuntuaction = viewFactory.getViewPanelPuntuaction();
        viewPanelSearch = viewFactory.getViewPanelSearch();
        viewPanelPuntuaction.setActiveMessageDialog(false);
        viewPanelSearch.setActiveMessageDialog(false);
        presenterSearchRatingSeries = presenterFactory.createPresenterGetterRatedSeries();
        presenterFactory.createPresenterSearchSeries();
        presenterFactory.createPresenterLoadLocalRatedSeries();
    }

    @Test
    public void testLoadRatedSeriesInViewSearch() throws IOException {
        String actual;
        String expected = "<html><head></head><body><h1>The100(TVseries)</h1><pclass=\"mw-empty-elt\"></p><p><i><b>The100</b></i>(pronounced<i><b>TheHundred</b></i>&#8202;)isanAmericanpost-apocalypticsciencefictiondramatelevisionseriesthatpremieredonMarch19,2014,onTheCW,andendedonSeptember30,2020.DevelopedbyJasonRothenberg,theseriesislooselybasedontheyoungadultnovelseriesofthesamenamebyKassMorgan.<i>The100</i>followspost-apocalypticsurvivorsfromaspacehabitat,the<i>Ark</i>,whoreturntoEarthnearlyacenturyafteradevastatingnuclearapocalypse.ThefirstpeoplesenttoEarthareagroupofjuveniledelinquentswhoencounterdescendantsofsurvivorsofthenucleardisasterontheground.</p><p>ThejuveniledelinquentsincludeClarkeGriffin(ElizaTaylor),FinnCollins(ThomasMcDonell),BellamyBlake(BobMorley),OctaviaBlake(MarieAvgeropoulos),JasperJordan(DevonBostick),MontyGreen(ChristopherLarkin),andJohnMurphy(RichardHarmon).OtherleadcharactersincludeClarke`smotherDr.AbbyGriffin(PaigeTurco),MarcusKane(HenryIanCusick),andChancellorTheloniousJaha(IsaiahWashington),allofwhomarecouncilmembersonthe<i>Ark</i>,andRavenReyes(LindseyMorgan),amechanicaboardthe<i>Ark</i>.</p><ahref=\"null\">OPENINBROWSER!</a></body></html>";

        String jsonResult = "[{\"ns\":0,\"title\":\"The 100 (TV series)\",\"pageid\":39350301,\"size\":116842,\"wordcount\":9021,\"snippet\":\"One Ratings&quot;. <span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">Series</span> Finale. June 13, 2014. Retrieved May 25, 2020. For the second season, see &quot;The 100: Season Two Ratings&quot;. <span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">Series</span> Finale. March\",\"timestamp\":\"2024-06-02T20:09:23Z\"}]\n";
        String extractElement = "\"<p class=\\\"mw-empty-elt\\\">\\n</p>\\n\\n<p><i><b>The 100</b></i> (pronounced <i><b>The Hundred</b></i> ) is an American post-apocalyptic science fiction drama television series that premiered on March 19, 2014, on The CW, and ended on September 30, 2020. Developed by Jason Rothenberg, the series is loosely based on the young adult novel series of the same name by Kass Morgan. <i>The 100</i> follows post-apocalyptic survivors from a space habitat, the <i>Ark</i>, who return to Earth nearly a century after a devastating nuclear apocalypse. The first people sent to Earth are a group of juvenile delinquents who encounter descendants of survivors of the nuclear disaster on the ground.\\n</p><p>The juvenile delinquents include Clarke Griffin (Eliza Taylor), Finn Collins (Thomas McDonell), Bellamy Blake (Bob Morley), Octavia Blake (Marie Avgeropoulos), Jasper Jordan (Devon Bostick), Monty Green (Christopher Larkin), and John Murphy (Richard Harmon). Other lead characters include Clarke's mother Dr. Abby Griffin (Paige Turco), Marcus Kane (Henry Ian Cusick), and Chancellor Thelonious Jaha (Isaiah Washington), all of whom are council members on the <i>Ark</i>, and Raven Reyes (Lindsey Morgan), a mechanic aboard the <i>Ark</i>.\\n</p>\"";
        JsonArray jsonResultsSeries = JsonParser.parseString(jsonResult).getAsJsonArray();
        JsonElement jsonElement = JsonParser.parseString(extractElement).deepCopy();
        Mockito.when(wikipediaAPIServiceMock.searchForTerm(anyString(), anyInt())).thenReturn(jsonResultsSeries);
        Mockito.when(wikipediaAPIServiceMock.getSeriesExtractByPageID(anyString())).thenReturn(jsonElement);

        viewPanelPuntuaction.getListRatedSeriesPanel().setSelectedIndex(0);
        presenterSearchRatingSeries.onEvent();
        actual = viewPanelSearch.getResultTextToSearchHTML().getText().replaceAll("\\s+", "");
        assertEquals(expected, actual);
    }
}
