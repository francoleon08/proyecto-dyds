package dyds.tvseriesinfo.view.tabbedPane;

import dyds.tvseriesinfo.model.entities.Series;
import dyds.tvseriesinfo.presenter.Presenter;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewPanelSearch extends ViewTabbedPane {
    private JPanel searchPanel;
    @Getter
    private JTextField seriesToSearchTextField;
    @Getter
    private JTextPane resultTextToSearchHTML;
    private JButton searchButton;
    private JButton saveLocallyButton;
    private JPanel punctuationPanel;
    @Getter
    private JCheckBox checkBoxEnablePuntuaction;
    @Getter
    private JComboBox comboBoxPuntaction;
    private JButton savePuntuactionButton;
    @Getter
    private JPopupMenu searchOptionsMenu;

    @Setter
    String resultTextToSearch = "";
    @Setter
    @Getter
    String selectedResultTitle = null;

    @Setter
    private Presenter presenterSearchSeries;
    @Setter
    private Presenter presenterSaveSeries;
    @Setter
    private Presenter presenterSavePuntuaction;
    @Setter
    private Presenter presenterOpenHyperLink;

    public ViewPanelSearch() {
        initConfig();
        initListeners();
    }

    private void initConfig() {
        searchOptionsMenu = new JPopupMenu("Search Results");
        resultTextToSearchHTML.setContentType("text/html");
        comboBoxPuntaction.setEnabled(false);
    }


    public void initListeners() {
        searchButton.addActionListener(e ->
                presenterSearchSeries.onEvent()
        );

        seriesToSearchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenterSearchSeries.onEvent();
            }
        });

        saveLocallyButton.addActionListener(e ->
                presenterSaveSeries.onEvent()
        );

        checkBoxEnablePuntuaction.addActionListener(e -> {
            comboBoxPuntaction.setEnabled(checkBoxEnablePuntuaction.isSelected());
            savePuntuactionButton.setEnabled(checkBoxEnablePuntuaction.isSelected());
        });

        savePuntuactionButton.addActionListener(e ->
                presenterSavePuntuaction.onEvent()
        );

        resultTextToSearchHTML.addHyperlinkListener(e -> {
            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                presenterOpenHyperLink.onEvent();
            }
        });
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

    public void addOptionSearchResult(Series series) {
        searchOptionsMenu.add(series);
    }

    public boolean isCheckBoxSelected() {
        return checkBoxEnablePuntuaction.isSelected();
    }

    public void setRatedSeries(int rated) {
        comboBoxPuntaction.setSelectedIndex(rated);
    }

    public String getSelectedPuntuaction() {
        return comboBoxPuntaction.getSelectedItem().toString();
    }
}
