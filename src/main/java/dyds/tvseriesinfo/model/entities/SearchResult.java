package dyds.tvseriesinfo.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
public class SearchResult extends JMenuItem {
    private String title;
    private String pageID;
    private String snippet;
    @Setter
    private String extract;

    public SearchResult(String title, String pageID, String snippet) {
        String itemText = "<html><font face=\"arial\">" + title + ": " + snippet;
        itemText = itemText.replace("<span class=\"searchmatch\">", "")
                .replace("</span>", "");
        this.setText(itemText);
        this.title = title;
        this.pageID = pageID;
        this.snippet = snippet;
    }


}
