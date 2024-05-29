package dyds.tvseriesinfo;

import dyds.tvseriesinfo.model.database.SQLmanager.DatabaseConnectionManager;
import dyds.tvseriesinfo.presenter.*;
import dyds.tvseriesinfo.view.View;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DatabaseConnectionManager.initializeDatabase();

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        View view = new View();
        ViewPanelStorage viewPanelStorage = view.getViewPanelStorage();
        ViewPanelSearch viewPanelSearch = view.getViewPanelSearch();

        PresenterGetterSeries presenterGetterSeries = new PresenterGetterSeries(viewPanelStorage);

        Presenter presenterDeleteSeries = new PresenterDeleteSeries(viewPanelStorage, presenterGetterSeries);
        Presenter presenterSaveChangesSeries = new PresenterSaveChangesSeries(viewPanelStorage);
        Presenter presenterSaveSeries = new PresenterSaveSeries(viewPanelSearch, presenterGetterSeries);
        Presenter presenterSearchSeries = new PresenterSearchSeries(viewPanelSearch);

        SwingUtilities.invokeLater(view::initView);
    }
}
