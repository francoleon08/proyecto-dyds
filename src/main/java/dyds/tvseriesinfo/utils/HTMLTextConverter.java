package dyds.tvseriesinfo.utils;

public class HTMLTextConverter {

    public static String textToHtml(String text) {
        StringBuilder builder = new StringBuilder();
        String fixedText = fixText(text);
        builder.append(fixedText);
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

    public static String getURLtoTextHTML(String textHTML) {
        String[] parts = textHTML.split("href=\"");
        String[] parts2 = parts[1].split("\">");
        return parts2[0];
    }
}