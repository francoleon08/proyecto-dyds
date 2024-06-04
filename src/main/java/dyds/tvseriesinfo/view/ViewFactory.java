package dyds.tvseriesinfo.view;

import dyds.tvseriesinfo.view.tabbedPane.*;

public class ViewFactory {
    private GeneralView generalView;

    public ViewFactory() {
        this.generalView = new GeneralView();
    }

    public ViewPanelStorage getViewPanelStorage() {
        return generalView.getViewPanelStorage();
    }

    public ViewPanelSearch getViewPanelSearch() {
        return generalView.getViewPanelSearch();
    }

    public ViewPanelPuntuaction getViewPanelPuntuaction() {
        return generalView.getViewPanelPuntuaction();
    }

    public GeneralView getGeneralView() {
        return this.generalView;
    }
}
