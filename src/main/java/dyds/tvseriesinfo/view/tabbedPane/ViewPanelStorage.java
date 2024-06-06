package dyds.tvseriesinfo.view.tabbedPane;

import dyds.tvseriesinfo.presenter.Presenter;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class ViewPanelStorage extends ViewTabbedPane {
    private JPanel storagePanel;
    @Getter
    private JComboBox seriesComboBox;
    @Getter
    private JTextPane textPaneDetailsSeries;
    private JPopupMenu storedInfoPopup;
    private JMenuItem deleteItem;
    private JMenuItem saveChangesSeries;

    @Setter
    private Presenter presenterDeleteSeries;
    @Setter
    private Presenter presenterSaveChangesSeries;
    @Setter
    private Presenter presenterGetterSeries;


    public ViewPanelStorage() {
        initConfig();
        initListeners();
    }

    private void initConfig() {
        textPaneDetailsSeries.setContentType("text/html");
        initConfigPopupMenu();
    }

    public void initListeners() {
        seriesComboBox.addActionListener(actionEvent ->
                presenterGetterSeries.onEvent()
        );
        deleteItem.addActionListener(actionEvent ->
                presenterDeleteSeries.onEvent()
        );
        saveChangesSeries.addActionListener(actionEvent ->
                presenterSaveChangesSeries.onEvent()
        );
    }

    @Override
    public void setWorkingState(boolean working) {
        for (Component component : this.storagePanel.getComponents()) {
            component.setEnabled(!working);
        }
        textPaneDetailsSeries.setEnabled(!working);
    }

    public boolean isItemSelected() {
        return seriesComboBox.getSelectedIndex() > -1;
    }

    public String getItemSelectedComboBox() {
        return seriesComboBox.getSelectedItem().toString();
    }

    public void setDetailsSeries(String text) {
        textPaneDetailsSeries.setText(text);
    }

    public String getDetailsSeries() {
        return textPaneDetailsSeries.getText();
    }

    public void setSeriesComboBox(Object[] series) {
        seriesComboBox.setModel(new DefaultComboBoxModel<>(series));
    }


    private void initConfigPopupMenu() {
        storedInfoPopup = new JPopupMenu();
        deleteItem = new JMenuItem("Delete!");
        saveChangesSeries = new JMenuItem("Save Changes!");
        storedInfoPopup.add(saveChangesSeries);
        storedInfoPopup.add(deleteItem);
        textPaneDetailsSeries.setComponentPopupMenu(storedInfoPopup);
    }
}
