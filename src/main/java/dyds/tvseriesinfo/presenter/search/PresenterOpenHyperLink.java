package dyds.tvseriesinfo.presenter.search;

import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.utils.HTMLTextConverter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;

import java.io.IOException;

public class PresenterOpenHyperLink implements Presenter {
    private final ViewPanelSearch viewPanelSearch;

    public PresenterOpenHyperLink(ViewPanelSearch viewPanelSearch) {
        this.viewPanelSearch = viewPanelSearch;
        this.viewPanelSearch.setPresenterOpenHyperLink(this);
    }

    @Override
    public void onEvent() {
        String URL = HTMLTextConverter.getURLtoTextHTML(viewPanelSearch.getResultTextToSearchHTML().getText());
        doOpeningDefaultBrowser(URL);
    }

    private void doOpeningDefaultBrowser(String URL) {
        try {
            new ProcessBuilder("xdg-open", URL).start();
            hasFinishedOperationSucces();
        } catch (IOException e) {
            hasFinishedOperationError("Error opening the browser: " + e.getMessage());
        }
    }

    private void hasFinishedOperationError(String messageError) {
        viewPanelSearch.showMessageDialog(messageError);
    }

    @Override
    public void hasFinishedOperationSucces() {
        viewPanelSearch.showMessageDialog("Opening the browser...");
    }
}
