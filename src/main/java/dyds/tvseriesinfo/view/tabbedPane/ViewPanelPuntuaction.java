package dyds.tvseriesinfo.view.tabbedPane;

import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.presenter.Presenter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class ViewPanelPuntuaction extends ViewTabbedPane {
    private JPanel puntuactionPanel;
    private JList<RatedSeries> listRatedSeriesPanel;
    @Setter
    private Presenter presenterSearchRatingSeries;

    @Override
    public void setWorkingState(boolean working) {
        for (Component component : this.puntuactionPanel.getComponents()) {
            component.setEnabled(!working);
        }
    }

    public void setRatedSeriesList(DefaultListModel<RatedSeries> listModel) {
        listRatedSeriesPanel.setModel(listModel);
        initListeners();
    }

    private void initListeners() {
        listRatedSeriesPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    presenterSearchRatingSeries.onEvent();
                }
            }
        });
    }

    public String getTitleRatedSeriesSelected() {
        return listRatedSeriesPanel.getSelectedValue().getTitle();
    }
}
