package dyds.tvseriesinfo.model.entities;

import com.google.gson.JsonObject;

public class SeriesServices {

    public static Series createSearchResultFromJsonObject(JsonObject jsonObject) {
        String title = jsonObject.get("title").getAsString();
        String pageId = jsonObject.get("pageid").getAsString();
        String snippet = jsonObject.get("snippet").getAsString();
        String itemText = cleanHTMLText(title, snippet);
        Series series = Series.builder()
                .title(title)
                .pageID(pageId)
                .snippet(snippet)
                .build();
        series.setText(itemText);
        return series;
    }

    private static String cleanHTMLText(String title, String snippet) {
        String cleanText = "<html><font face=\"arial\">" + title + ": " + snippet;
        cleanText = cleanText.replace("<span class=\"searchmatch\">", "")
                .replace("</span>", "");
        return cleanText;
    }
}
