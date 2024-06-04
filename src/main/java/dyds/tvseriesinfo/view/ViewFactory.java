package dyds.tvseriesinfo.view;

import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

public class ViewFactory {
    private final GeneralView generalView;

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
