package dyds.tvseriesinfo;

import dyds.tvseriesinfo.model.database.DataBase;
import dyds.tvseriesinfo.model.database.DatabaseConnectionManager;
import dyds.tvseriesinfo.presenter.*;
import dyds.tvseriesinfo.view.View;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DatabaseConnectionManager.loadDatabase();

        View view = new View();
        ViewPanelStorage viewPanelStorage = view.getViewPanelStorage();
        ViewPanelSearch viewPanelSearch = view.getViewPanelSearch();

        DataBase model = new DataBase();

        Presenter presenterDeleteSeries = new PresenterDeleteSeries(viewPanelStorage, model);
        Presenter presenterSaveChangesSeries = new PresenterSaveChangesSeries(viewPanelStorage, model);
        Presenter presenterSaveSeries = new PresenterSaveSeries(viewPanelStorage, viewPanelSearch, model);
        Presenter presenterSearchSeries = new PresenterSearchSeries(viewPanelSearch, model);

        viewPanelSearch.setPresenterSearchSeries(presenterSearchSeries);
        viewPanelSearch.setPresenterSaveSeries(presenterSaveSeries);

        viewPanelStorage.setPresenterDeleteSeries(presenterDeleteSeries);
        viewPanelStorage.setPresenterSaveChangesSeries(presenterSaveChangesSeries);
        viewPanelStorage.setSeriesComboBox(model.getTitles().stream().sorted().toArray());

        SwingUtilities.invokeLater(view::initView);
    }
}
