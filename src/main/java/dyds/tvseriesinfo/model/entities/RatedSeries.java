package dyds.tvseriesinfo.model.entities;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RatedSeries {
    private String title;
    private int rating;
    private String dateModified;
    private String timeModified;
}
