package dyds.tvseriesinfo.view.tabbedPane;

import dyds.tvseriesinfo.presenter.Presenter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class ViewPanelPuntuaction extends ViewTabbedPane {
    private JPanel puntuactionPanel;
    private JComboBox seriesPuntuactionComboBox;
    private JTextField puntuactionSeries;
    private JTextField lastModificationSeries;
    private JTextField titleSeries;

    @Setter
    private Presenter presenterGetterRatedSeries;

    public ViewPanelPuntuaction() {
        initListeners();
    }

    @Override
    public void setWorkingState(boolean working) {
        for (Component component : this.puntuactionPanel.getComponents()) {
            component.setEnabled(!working);
        }
    }

    private void initListeners() {
        seriesPuntuactionComboBox.addActionListener(e ->
                presenterGetterRatedSeries.onEvent()
        );
    }

    public boolean isItemSelected() {
        return seriesPuntuactionComboBox.getSelectedIndex() > -1;
    }

    public String getItemSelectedComboBox() {
        return seriesPuntuactionComboBox.getSelectedItem().toString();
    }

    public void setRatedSeriesComboBox(Object[] ratedSeries) {
        seriesPuntuactionComboBox.setModel(new DefaultComboBoxModel<>(ratedSeries));
    }

    public void setTitleRatedSeries(String title) {
        titleSeries.setText(title);
    }

    public void setPuntuactionRadetSeries(String puntuaction) {
        puntuactionSeries.setText(puntuaction);
    }

    public void setLastModificationRatedSeries(String lastModification) {
        lastModificationSeries.setText(lastModification);
    }
}
