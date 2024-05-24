package dyds.tvseriesinfo.view;

import dyds.tvseriesinfo.model.dataAccess.DataBase;
import dyds.tvseriesinfo.presenter.PresenterView;

import javax.swing.*;

public class ViewPanelStorage extends JPanel {
    private JPanel storagePanel;
    private JComboBox seriesComboBox;
    private JTextPane detailsSeries;
    private JPopupMenu storedInfoPopup;
    private JMenuItem deleteItem;
    private JMenuItem saveChangesSeries;
    private PresenterView presenterView;


    public ViewPanelStorage() {
        initConfig();
        initListeners();
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

    private void initConfig(){
        presenterView = PresenterView.getIntance();
        presenterView.setViewPanelStorage(this);
        seriesComboBox.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
        detailsSeries.setContentType("text/html");
        initConfigPopupMenu();
    }

    private void initConfigPopupMenu() {
        storedInfoPopup = new JPopupMenu();
        deleteItem = new JMenuItem("Delete!");
        saveChangesSeries = new JMenuItem("Save Changes!");
        storedInfoPopup.add(saveChangesSeries);
        storedInfoPopup.add(deleteItem);
        detailsSeries.setComponentPopupMenu(storedInfoPopup);
    }

    private void initListeners() {
        seriesComboBox.addActionListener(actionEvent ->
                presenterView.onEventSelectSeriesStorage()
        );
        deleteItem.addActionListener(actionEvent ->
                presenterView.onEventDeleteSeries()
        );
        saveChangesSeries.addActionListener(actionEvent ->
                presenterView.onEventSaveChangesSeries()
        );
    }
}
