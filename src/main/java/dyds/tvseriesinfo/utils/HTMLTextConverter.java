package dyds.tvseriesinfo.utils;

import com.google.gson.JsonElement;

public class HTMLTextConverter {

    private static final String FONT_FACE_ARIAL = "<font face=\"arial\">";
    private static final String FONT_END = "</font>";

    public static String textToHtml(String text) {
        StringBuilder builder = new StringBuilder();
        builder.append(FONT_FACE_ARIAL);

        String fixedText = fixText(text);
        builder.append(fixedText);

        builder.append(FONT_END);
        return builder.toString();
    }

    private static String fixText(String text) {
        return text.replace("'", "`");
    }

    public static String formatTitle(String title) {
        return "<h1>" + title + "</h1>";
    }

    public static String formatContent(String extractElement) {
        return extractElement.replace("\\n", "\n");
    }

    public static String formatImage(String url) {
        return "<img src=\"" + url + "\">";
    }

    public static String formatHyperlink(String url, String text) {
        return "<a href=\"" + url + "\">" + text + "</a>";
    }
}