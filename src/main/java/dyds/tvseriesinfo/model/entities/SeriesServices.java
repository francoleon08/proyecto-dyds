package dyds.tvseriesinfo.model.entities;

import com.google.gson.JsonObject;

public class SeriesServices {

    public static Series createSearchResultFromJsonObject(JsonObject jsonObject) {
        String title = jsonObject.get("title").getAsString();
        String pageId = jsonObject.get("pageid").getAsString();
        String snippet = jsonObject.get("snippet").getAsString();
        return new Series(title, pageId, snippet);
    }
}
