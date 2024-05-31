package dyds.tvseriesinfo.model.entities;

import lombok.Getter;

@Getter
public class RatedSeries {
    private String title;
    private int rating;
    private String dateModified;
    private String timeModified;

    public RatedSeries(String title, int rating, String dateModified, String timeModified) {
        this.title = title;
        this.rating = rating;
        this.dateModified = dateModified;
        this.timeModified = timeModified;
    }
}
