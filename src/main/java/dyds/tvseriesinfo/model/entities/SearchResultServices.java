package dyds.tvseriesinfo.model.entities;

import com.google.gson.JsonObject;

public class SearchResultServices {

    public static SearchResult createSearchResultFromJsonObject(JsonObject jsonObject) {
        String title = jsonObject.get("title").getAsString();
        String pageId = jsonObject.get("pageid").getAsString();
        String snippet = jsonObject.get("snippet").getAsString();
        return new SearchResult(title, pageId, snippet);
    }
}
