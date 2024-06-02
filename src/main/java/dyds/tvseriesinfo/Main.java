package dyds.tvseriesinfo;

import com.formdev.flatlaf.FlatDarkLaf;
import dyds.tvseriesinfo.model.database.SQLmanager.DatabaseConnectionManager;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.presenter.rated.PresenterLoadLocalRatedSeries;
import dyds.tvseriesinfo.presenter.rated.PresenterSearchRatingSeries;
import dyds.tvseriesinfo.presenter.search.PresenterOpenHyperLink;
import dyds.tvseriesinfo.presenter.search.PresenterSavePuntuaction;
import dyds.tvseriesinfo.presenter.search.PresenterSaveSeries;
import dyds.tvseriesinfo.presenter.search.PresenterSearchSeries;
import dyds.tvseriesinfo.presenter.storage.PresenterDeleteSeries;
import dyds.tvseriesinfo.presenter.storage.PresenterGetterSeries;
import dyds.tvseriesinfo.presenter.storage.PresenterLoadLocalSeries;
import dyds.tvseriesinfo.presenter.storage.PresenterSaveChangesSeries;
import dyds.tvseriesinfo.view.GeneralView;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        setLookAndFel();
        DatabaseConnectionManager.initializeDatabases();

        GeneralView generalView = new GeneralView();
        ViewPanelStorage viewPanelStorage = generalView.getViewPanelStorage();
        ViewPanelSearch viewPanelSearch = generalView.getViewPanelSearch();
        ViewPanelPuntuaction viewPanelPuntuaction = generalView.getViewPanelPuntuaction();

        Presenter presenterSavePuntuaction = new PresenterSavePuntuaction(viewPanelSearch, viewPanelPuntuaction);
        Presenter presenterGetterSeries = new PresenterGetterSeries(viewPanelStorage);
        Presenter presenterLoadLocalSeries = new PresenterLoadLocalSeries(viewPanelStorage);
        Presenter presenterDeleteSeries = new PresenterDeleteSeries(viewPanelStorage);
        Presenter presenterSaveChangesSeries = new PresenterSaveChangesSeries(viewPanelStorage);
        Presenter presenterSaveSeries = new PresenterSaveSeries(viewPanelSearch);
        Presenter presenterSearchSeries = new PresenterSearchSeries(viewPanelSearch);
        Presenter prestererGetterRatedSeries = new PresenterSearchRatingSeries(generalView);
        Presenter presenterLoadLocalRatedSeries = new PresenterLoadLocalRatedSeries(viewPanelPuntuaction);
        Presenter presenterOpenHyperLink = new PresenterOpenHyperLink(viewPanelSearch);

        SwingUtilities.invokeLater(generalView::initView);
    }

    private static void setLookAndFel() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            UIManager.put("OptionPane.messageFont", UIManager.getFont("Label.font").deriveFont(16f));
            UIManager.put("OptionPane.buttonFont", UIManager.getFont("Label.font").deriveFont(16f));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
