package unit_tests.model.wikiApi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dyds.tvseriesinfo.model.apiConsummer.WikipediaAPIService;
import dyds.tvseriesinfo.model.database.crud.series.ModelWikipediaAPI;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;


public class ModelWikipediaAPITest {
    private ModelWikipediaAPI modelWikipediaAPI;
    @Mock
    private WikipediaAPIService wikipediaAPIServiceMock;

    @Before
    public void setUp() {
        wikipediaAPIServiceMock = mock(WikipediaAPIService.class);
        modelWikipediaAPI = ModelWikipediaAPI.getInstance();
        modelWikipediaAPI.setWikipediaAPIService(wikipediaAPIServiceMock);
    }

    @Test
    public void testSearchEmptyTerm() throws IOException {
        String term = "";
        int limit = 5;
        String jsonResult = "[]";
        JsonArray jsonResultsSeries = JsonParser.parseString(jsonResult).getAsJsonArray();
        Mockito.when(wikipediaAPIServiceMock.searchForTerm(anyString(), anyInt())).thenReturn(jsonResultsSeries);
        try {
            modelWikipediaAPI.searchMultipleOfSeries(term, limit);
        } catch (SeriesSearchException ignored) {
        }
    }

    @Test
    public void testSearchValidTerm() throws IOException, SeriesSearchException {
        String term = "The 100 (TV series)";
        String actual;
        String jsonResult = "[{\"ns\":0,\"title\":\"The 100 (TV series)\",\"pageid\":39350301,\"size\":116842,\"wordcount\":9021,\"snippet\":\"One Ratings&quot;. <span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">Series</span> Finale. June 13, 2014. Retrieved May 25, 2020. For the second season, see &quot;The 100: Season Two Ratings&quot;. <span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">Series</span> Finale. March\",\"timestamp\":\"2024-06-02T20:09:23Z\"}]\n";
        String extractElement = "\"<p class=\\\"mw-empty-elt\\\">\\n</p>\\n\\n<p><i><b>The 100</b></i> (pronounced <i><b>The Hundred</b></i> ) is an American post-apocalyptic science fiction drama television series that premiered on March 19, 2014, on The CW, and ended on September 30, 2020. Developed by Jason Rothenberg, the series is loosely based on the young adult novel series of the same name by Kass Morgan. <i>The 100</i> follows post-apocalyptic survivors from a space habitat, the <i>Ark</i>, who return to Earth nearly a century after a devastating nuclear apocalypse. The first people sent to Earth are a group of juvenile delinquents who encounter descendants of survivors of the nuclear disaster on the ground.\\n</p><p>The juvenile delinquents include Clarke Griffin (Eliza Taylor), Finn Collins (Thomas McDonell), Bellamy Blake (Bob Morley), Octavia Blake (Marie Avgeropoulos), Jasper Jordan (Devon Bostick), Monty Green (Christopher Larkin), and John Murphy (Richard Harmon). Other lead characters include Clarke's mother Dr. Abby Griffin (Paige Turco), Marcus Kane (Henry Ian Cusick), and Chancellor Thelonious Jaha (Isaiah Washington), all of whom are council members on the <i>Ark</i>, and Raven Reyes (Lindsey Morgan), a mechanic aboard the <i>Ark</i>.\\n</p>\"";
        JsonArray jsonResultsSeries = JsonParser.parseString(jsonResult).getAsJsonArray();
        JsonElement jsonElement = JsonParser.parseString(extractElement).deepCopy();
        Mockito.when(wikipediaAPIServiceMock.searchForTerm(anyString(), anyInt())).thenReturn(jsonResultsSeries);
        Mockito.when(wikipediaAPIServiceMock.getSeriesExtractByPageID(anyString())).thenReturn(jsonElement);
        modelWikipediaAPI.searchSingleSeries(term);
        actual = modelWikipediaAPI.getLastSearchResult().iterator().next().getTitle();
        assertEquals(term, actual);
    }

    @Test
    public void testSearchLimitSeries() throws IOException, SeriesSearchException {
        String term = "The 100 (TV series)";
        int limit = 5;
        String jsonResult = "[{\"ns\":0,\"title\":\"The 100 (TV series)\",\"pageid\":39350301,\"size\":116842,\"wordcount\":9021,\"snippet\":\"One Ratings&quot;. <span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">Series</span> Finale. June 13, 2014. Retrieved May 25, 2020. For the second season, see &quot;The 100: Season Two Ratings&quot;. <span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">Series</span> Finale. March\",\"timestamp\":\"2024-06-02T20:09:23Z\"},{\"ns\":0,\"title\":\"Monk (TV series)\",\"pageid\":419373,\"size\":47275,\"wordcount\":3637,\"snippet\":\"media related to Monk (<span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">series</span>). Wikiquote has quotations related to Monk (<span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">series</span>). Official website Monk on USA from the <span class=\\\"searchmatch\\\">TV</span> Guide website Monk at\",\"timestamp\":\"2024-06-01T08:48:32Z\"},{\"ns\":0,\"title\":\"Batman (TV series)\",\"pageid\":298705,\"size\":98519,\"wordcount\":12105,\"snippet\":\"American television <span class=\\\"searchmatch\\\">series</span> of all time. A companion feature film was released in 1966 between the first and second seasons of the <span class=\\\"searchmatch\\\">TV</span> show. Batman held the\",\"timestamp\":\"2024-05-08T19:28:14Z\"},{\"ns\":0,\"title\":\"Shameless (American TV series)\",\"pageid\":29108688,\"size\":105842,\"wordcount\":6363,\"snippet\":\"Ratings&quot;. <span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">Series</span> Finale. April 7, 2012. Retrieved November 19, 2019. For the third season, see &quot;Shameless: Season Three Ratings&quot;. <span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">Series</span> Finale. April\",\"timestamp\":\"2024-05-19T21:28:02Z\"},{\"ns\":0,\"title\":\"Castle (TV series)\",\"pageid\":20522030,\"size\":85038,\"wordcount\":5688,\"snippet\":\" 2016. Retrieved June 16, 2016. Wikiquote has quotations related to Castle (<span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">series</span>). Castle at IMDb Castle at <span class=\\\"searchmatch\\\">TV</span> Guide Castle at <span class=\\\"searchmatch\\\">TV</span> by the Numbers\",\"timestamp\":\"2024-05-23T05:40:39Z\"}]\n";
        String extractElement = "\"<p class=\\\"mw-empty-elt\\\">\\n</p>\\n\\n<p><i><b>The 100</b></i> (pronounced <i><b>The Hundred</b></i> ) is an American post-apocalyptic science fiction drama television series that premiered on March 19, 2014, on The CW, and ended on September 30, 2020. Developed by Jason Rothenberg, the series is loosely based on the young adult novel series of the same name by Kass Morgan. <i>The 100</i> follows post-apocalyptic survivors from a space habitat, the <i>Ark</i>, who return to Earth nearly a century after a devastating nuclear apocalypse. The first people sent to Earth are a group of juvenile delinquents who encounter descendants of survivors of the nuclear disaster on the ground.\\n</p><p>The juvenile delinquents include Clarke Griffin (Eliza Taylor), Finn Collins (Thomas McDonell), Bellamy Blake (Bob Morley), Octavia Blake (Marie Avgeropoulos), Jasper Jordan (Devon Bostick), Monty Green (Christopher Larkin), and John Murphy (Richard Harmon). Other lead characters include Clarke's mother Dr. Abby Griffin (Paige Turco), Marcus Kane (Henry Ian Cusick), and Chancellor Thelonious Jaha (Isaiah Washington), all of whom are council members on the <i>Ark</i>, and Raven Reyes (Lindsey Morgan), a mechanic aboard the <i>Ark</i>.\\n</p>\"";
        JsonArray jsonResultsSeries = JsonParser.parseString(jsonResult).getAsJsonArray();
        JsonElement jsonElement = JsonParser.parseString(extractElement).deepCopy();
        Mockito.when(wikipediaAPIServiceMock.searchForTerm(anyString(), anyInt())).thenReturn(jsonResultsSeries);
        Mockito.when(wikipediaAPIServiceMock.getSeriesExtractByPageID(anyString())).thenReturn(jsonElement);
        modelWikipediaAPI.searchMultipleOfSeries(term, limit);
        assertEquals(limit, modelWikipediaAPI.getLastSearchResult().size());
    }

    @Test
    public void testSearchSingleSeries() throws IOException, SeriesSearchException {
        String term = "The 100 (TV series)";
        String jsonResult = "[{\"ns\":0,\"title\":\"The 100 (TV series)\",\"pageid\":39350301,\"size\":116842,\"wordcount\":9021,\"snippet\":\"One Ratings&quot;. <span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">Series</span> Finale. June 13, 2014. Retrieved May 25, 2020. For the second season, see &quot;The 100: Season Two Ratings&quot;. <span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">Series</span> Finale. March\",\"timestamp\":\"2024-06-02T20:09:23Z\"}]\n";
        String extractElement = "\"<p class=\\\"mw-empty-elt\\\">\\n</p>\\n\\n<p><i><b>The 100</b></i> (pronounced <i><b>The Hundred</b></i> ) is an American post-apocalyptic science fiction drama television series that premiered on March 19, 2014, on The CW, and ended on September 30, 2020. Developed by Jason Rothenberg, the series is loosely based on the young adult novel series of the same name by Kass Morgan. <i>The 100</i> follows post-apocalyptic survivors from a space habitat, the <i>Ark</i>, who return to Earth nearly a century after a devastating nuclear apocalypse. The first people sent to Earth are a group of juvenile delinquents who encounter descendants of survivors of the nuclear disaster on the ground.\\n</p><p>The juvenile delinquents include Clarke Griffin (Eliza Taylor), Finn Collins (Thomas McDonell), Bellamy Blake (Bob Morley), Octavia Blake (Marie Avgeropoulos), Jasper Jordan (Devon Bostick), Monty Green (Christopher Larkin), and John Murphy (Richard Harmon). Other lead characters include Clarke's mother Dr. Abby Griffin (Paige Turco), Marcus Kane (Henry Ian Cusick), and Chancellor Thelonious Jaha (Isaiah Washington), all of whom are council members on the <i>Ark</i>, and Raven Reyes (Lindsey Morgan), a mechanic aboard the <i>Ark</i>.\\n</p>\"";
        JsonArray jsonResultsSeries = JsonParser.parseString(jsonResult).getAsJsonArray();
        JsonElement jsonElement = JsonParser.parseString(extractElement).deepCopy();
        Mockito.when(wikipediaAPIServiceMock.searchForTerm(anyString(), anyInt())).thenReturn(jsonResultsSeries);
        Mockito.when(wikipediaAPIServiceMock.getSeriesExtractByPageID(anyString())).thenReturn(jsonElement);
        modelWikipediaAPI.searchSingleSeries(term);
        assertEquals(1, modelWikipediaAPI.getLastSearchResult().size());
    }
}
