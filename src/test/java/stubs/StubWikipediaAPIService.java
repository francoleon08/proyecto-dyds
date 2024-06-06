package stubs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dyds.tvseriesinfo.model.apiConsummer.WikipediaAPIService;

import java.io.IOException;

public class StubWikipediaAPIService extends WikipediaAPIService {

    public JsonArray searchForTerm(String term, int limitResult) throws IOException {
        String jsonResult = "[{\"ns\":0,\"title\":\"The 100 (TV series)\",\"pageid\":39350301,\"size\":116842,\"wordcount\":9021,\"snippet\":\"One Ratings&quot;. <span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">Series</span> Finale. June 13, 2014. Retrieved May 25, 2020. For the second season, see &quot;The 100: Season Two Ratings&quot;. <span class=\\\"searchmatch\\\">TV</span> <span class=\\\"searchmatch\\\">Series</span> Finale. March\",\"timestamp\":\"2024-06-02T20:09:23Z\"}]\n";
        return JsonParser.parseString(jsonResult).getAsJsonArray();
    }

    public JsonElement getSeriesExtractByPageID(String pageID) throws IOException {
        String extractElement = "\"<p class=\\\"mw-empty-elt\\\">\\n</p>\\n\\n<p><i><b>The 100</b></i> (pronounced <i><b>The Hundred</b></i> ) is an American post-apocalyptic science fiction drama television series that premiered on March 19, 2014, on The CW, and ended on September 30, 2020. Developed by Jason Rothenberg, the series is loosely based on the young adult novel series of the same name by Kass Morgan. <i>The 100</i> follows post-apocalyptic survivors from a space habitat, the <i>Ark</i>, who return to Earth nearly a century after a devastating nuclear apocalypse. The first people sent to Earth are a group of juvenile delinquents who encounter descendants of survivors of the nuclear disaster on the ground.\\n</p><p>The juvenile delinquents include Clarke Griffin (Eliza Taylor), Finn Collins (Thomas McDonell), Bellamy Blake (Bob Morley), Octavia Blake (Marie Avgeropoulos), Jasper Jordan (Devon Bostick), Monty Green (Christopher Larkin), and John Murphy (Richard Harmon). Other lead characters include Clarke's mother Dr. Abby Griffin (Paige Turco), Marcus Kane (Henry Ian Cusick), and Chancellor Thelonious Jaha (Isaiah Washington), all of whom are council members on the <i>Ark</i>, and Raven Reyes (Lindsey Morgan), a mechanic aboard the <i>Ark</i>.\\n</p>\"";
        return JsonParser.parseString(extractElement).deepCopy();
    }

    public String getWikipediaURL(String title) {
        return "";
    }
}
