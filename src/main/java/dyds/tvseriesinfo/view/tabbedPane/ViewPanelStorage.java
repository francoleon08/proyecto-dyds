package dyds.tvseriesinfo.view.tabbedPane;

import dyds.tvseriesinfo.model.database.crud.SeriesCrudGetter;
import dyds.tvseriesinfo.presenter.Presenter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class ViewPanelStorage extends JPanel implements ViewTabbedPane {
    private JPanel storagePanel;
    private JComboBox seriesComboBox;
    private JTextPane detailsSeries;
    private JPopupMenu storedInfoPopup;
    private JMenuItem deleteItem;
    private JMenuItem saveChangesSeries;

    @Setter
    private Presenter presenterDeleteSeries;
    @Setter
    private Presenter presenterSaveChangesSeries;


    public ViewPanelStorage() {
        initConfig();
        initListeners();
    }

    private void initConfig(){
        detailsSeries.setContentType("text/html");
        initConfigPopupMenu();
    }

    public void initListeners() {
        seriesComboBox.addActionListener(actionEvent ->
                this.setDetailsSeries(SeriesCrudGetter.getDetailsSeriesByTitle(this.getItemSelectedComboBox()))
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
        detailsSeries.setEnabled(!working);
    }

    @Override
    public Container getContet() {
        return this.getContet();
    }

    public boolean isItemSelected() {
        return seriesComboBox.getSelectedIndex() > -1;
    }

    public String getItemSelectedComboBox() {
        return seriesComboBox.getSelectedItem().toString();
    }

    public void setDetailsSeries(String text) {
        detailsSeries.setText(text);
    }

    public String getDetailsSeries() {
        return detailsSeries.getText();
    }

    public void setSeriesComboBox(Object[] series) {
        seriesComboBox.setModel(new DefaultComboBoxModel(series));
    }


    private void initConfigPopupMenu() {
        storedInfoPopup = new JPopupMenu();
        deleteItem = new JMenuItem("Delete!");
        saveChangesSeries = new JMenuItem("Save Changes!");
        storedInfoPopup.add(saveChangesSeries);
        storedInfoPopup.add(deleteItem);
        detailsSeries.setComponentPopupMenu(storedInfoPopup);
    }
}