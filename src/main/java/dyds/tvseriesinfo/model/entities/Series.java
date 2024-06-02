package dyds.tvseriesinfo.model.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Builder
@Getter
public class Series extends JMenuItem {
    private String title;
    private String pageID;
    private String snippet;
    @Setter
    private String extract;
    @Setter
    private int rated;
}
