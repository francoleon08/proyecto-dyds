package dyds.tvseriesinfo.presenter.search;

import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.utils.HTMLTextConverter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class PresenterOpenHyperLink implements Presenter {
    private final ViewPanelSearch viewPanelSearch;

    public PresenterOpenHyperLink(ViewPanelSearch viewPanelSearch) {
        this.viewPanelSearch = viewPanelSearch;
        this.viewPanelSearch.setPresenterOpenHyperLink(this);
    }

    @Override
    public void onEvent() {
        String URL = HTMLTextConverter.getURLWikipediaToTextHTML(viewPanelSearch.getResultTextToSearchHTML().getText());
        doOpeningDefaultBrowser(URL);
    }

    private void doOpeningDefaultBrowser(String URL) {
        try {
            handleOpeningBrowser(URL);
            hasFinishedOperationSucces();
        } catch (Exception e) {
            hasFinishedOperationError("Error opening the browser: " + e.getMessage());
        }
    }

    private void handleOpeningBrowser(String URL) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        if(isDesktopBrowseSupported(desktop)) {
            desktop.browse(URI.create(URL));
        } else {
            new ProcessBuilder("xdg-open", URL).start();
        }
    }

    private boolean isDesktopBrowseSupported(Desktop desktop) {
        return Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE);
    }

    private void hasFinishedOperationError(String messageError) {
        viewPanelSearch.showMessageDialog(messageError);
    }

    @Override
    public void hasFinishedOperationSucces() {
        viewPanelSearch.showMessageDialog("Opening the browser...");
    }
}
