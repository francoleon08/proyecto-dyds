package dyds.tvseriesinfo.presenter.search;

import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.utils.HTMLTextConverter;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PresenterOpenHyperLink implements Presenter {
    private final ViewPanelSearch viewPanelSearch;

    public PresenterOpenHyperLink(ViewPanelSearch viewPanelSearch) {
        this.viewPanelSearch = viewPanelSearch;
        this.viewPanelSearch.setPresenterOpenHyperLink(this);
    }

    @Override
    public void onEvent() {
        String URL = HTMLTextConverter.getURLtoTextHTML(viewPanelSearch.getResultTextToSearchHTML().getText());
    }

    private void hasFinishedOperationError(String messageError){
        viewPanelSearch.showMessageDialog(messageError);
    }

    @Override
    public void hasFinishedOperationSucces() {
        viewPanelSearch.showMessageDialog("Opening the browser...");
    }
}
