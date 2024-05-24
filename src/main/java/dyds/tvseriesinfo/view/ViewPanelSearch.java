package dyds.tvseriesinfo.view;

import dyds.tvseriesinfo.model.entities.SearchResult;
import dyds.tvseriesinfo.presenter.PresenterView;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class ViewPanelSearch extends JPanel {
    private JPanel searchPanel;
    private JTextField seriesToSearchTextField;
    private JTextPane resultTextToSearchHTML;
    private JButton searchButton;
    private JButton saveLocallyButton;

    String resultTextToSearch = "";
    String selectedResultTitle = null;

    private PresenterView presenterView;

    private JPopupMenu searchOptionsMenu;

    public ViewPanelSearch() {
        initConfig();
        initListeners();
    }

    private void initConfig() {
        presenterView = PresenterView.getIntance();
        presenterView.setViewPanelSearch(this);
        searchOptionsMenu = new JPopupMenu("Search Results");
        resultTextToSearchHTML.setContentType("text/html");
    }


    private void initListeners() {
        searchButton.addActionListener(e -> {
            presenterView.onEventSearchSeries();
        });

        saveLocallyButton.addActionListener(actionEvent -> {
            presenterView.onEventSaveSeries();
        });
    }

    public void showOptionsMenu() {
        searchOptionsMenu.show(seriesToSearchTextField, seriesToSearchTextField.getX(), seriesToSearchTextField.getY());
    }

    public void clearOptionsMenu() {
        searchOptionsMenu.removeAll();
    }

    public void addOptionSearchResult(SearchResult sr) {
        searchOptionsMenu.add(sr);
    }

    public void setWorkingStatus() {
        for(Component c: this.searchPanel.getComponents()) c.setEnabled(false);
        resultTextToSearchHTML.setEnabled(false);
    }

    public void setWatingStatus() {
        for(Component c: this.searchPanel.getComponents()) c.setEnabled(true);
        resultTextToSearchHTML.setEnabled(true);
    }
}
