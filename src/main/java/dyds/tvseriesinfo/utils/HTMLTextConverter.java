package dyds.tvseriesinfo.utils;

public class HTMLTextConverter {

    public static String textToHtml(String text) {
        StringBuilder builder = new StringBuilder();
        String fixedText = fixText(text);
        builder.append(fixedText);
        return builder.toString();
    }

    public static String formatTextToHTML(String text) {
        return "<html>" + text + "</html>";
    }

    private static String fixText(String text) {
        return text.replace("'", "`");
    }

    public static String formatTitle(String title) {
        return "<h1>" + title + "</h1>";
    }

    public static String formatParagraph(String paragraph) {
        return "<p>" + paragraph + "</p>";
    }

    public static String formatContent(String extractElement) {
        return extractElement.replace("\\n", "\n");
    }

    public static String formatHyperlink(String url, String text) {
        return "<a href=\"" + url + "\">" + text + "</a>";
    }

    public static String getURLWikipediaToTextHTML(String textHTML) {
        String[] splitTextLeftHref = textHTML.split("href=\"");
        String[] splitTextRightHref = splitTextLeftHref[1].split("\">");
        return splitTextRightHref[0];
    }
}