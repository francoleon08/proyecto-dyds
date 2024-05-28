package dyds.tvseriesinfo.view.tabbedPane;

import dyds.tvseriesinfo.model.entities.SearchResult;
import dyds.tvseriesinfo.presenter.Presenter;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class ViewPanelSearch extends ViewTabbedPane {
    private JPanel searchPanel;
    @Getter
    private JTextField seriesToSearchTextField;
    @Getter
    private JTextPane resultTextToSearchHTML;
    private JButton searchButton;
    private JButton saveLocallyButton;
    private JPopupMenu searchOptionsMenu;

    @Setter
    @Getter
    String resultTextToSearch = "";
    @Setter
    @Getter
    String selectedResultTitle = null;

    @Setter
    private Presenter presenterSearchSeries;
    @Setter
    private Presenter presenterSaveSeries;

    public ViewPanelSearch() {
        initConfig();
        initListeners();
    }

    private void initConfig() {
        searchOptionsMenu = new JPopupMenu("Search Results");
        resultTextToSearchHTML.setContentType("text/html");
    }


    public void initListeners() {
        searchButton.addActionListener(e ->
                presenterSearchSeries.onEvent()
        );

        saveLocallyButton.addActionListener(actionEvent ->
                presenterSaveSeries.onEvent()
        );
    }

    @Override
    public void setWorkingState(boolean working) {
        for (Component component : this.searchPanel.getComponents())
            component.setEnabled(!working);
        resultTextToSearchHTML.setEnabled(!working);
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
}
