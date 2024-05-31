package dyds.tvseriesinfo.model.apiConsummer;

import com.google.gson.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.Map;

public class WikipediaAPIService {
    private static final String BASE_URL = "https://en.wikipedia.org/w/";
    private static final String BASE_WIKI_URL = "https://en.wikipedia.org/wiki/";
    private final WikipediaSearchAPI searchAPI;
    private final WikipediaPageAPI pageAPI;

    public WikipediaAPIService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        searchAPI = retrofit.create(WikipediaSearchAPI.class);
        pageAPI = retrofit.create(WikipediaPageAPI.class);
    }

    public JsonArray searchForTerm(String term) throws IOException {
        Response<String> response = searchAPI.searchForTerm(term).execute();
        JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);
        JsonObject query = jsonObject.get("query").getAsJsonObject();
        return query.get("search").getAsJsonArray();
    }

    public JsonElement getSeriesExtractByPageID(String pageID) throws IOException {
        Response<String> response = pageAPI.getExtractByPageID(pageID).execute();
        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
        return jsonResponse.getAsJsonObject("query")
                .getAsJsonObject("pages")
                .entrySet()
                .stream()
                .findFirst()
                .map(Map.Entry::getValue)
                .map(pageJson -> pageJson.getAsJsonObject().get("extract"))
                .orElse(null);
    }

    public String getWikipediaURL(String title){
        return BASE_WIKI_URL + title.replace(' ', '_');
    }
}
