package dyds.tvseriesinfo.model.entities;

import dyds.tvseriesinfo.utils.HTMLTextConverter;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RatedSeries {
    private String pageID;
    private String title;
    private int rating;
    private String dateModified;
    private String timeModified;

    public String toString() {
        String ratedSeriesDetails = HTMLTextConverter.formatTitle(title + " - " + rating) +
                HTMLTextConverter.formatParagraph(dateModified + " - " + timeModified);
        return HTMLTextConverter.formatTextToHTML(ratedSeriesDetails);
    }
}
