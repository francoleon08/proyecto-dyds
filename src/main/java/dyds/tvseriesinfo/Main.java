package dyds.tvseriesinfo;

import dyds.tvseriesinfo.model.database.DatabaseConnectionManager;
import dyds.tvseriesinfo.model.database.crud.SeriesCrudDeleter;
import dyds.tvseriesinfo.model.database.crud.SeriesCrudGetter;
import dyds.tvseriesinfo.model.database.crud.SeriesCrudSaver;
import dyds.tvseriesinfo.presenter.*;
import dyds.tvseriesinfo.view.View;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DatabaseConnectionManager.initializeDatabase();

        View view = new View();
        ViewPanelStorage viewPanelStorage = view.getViewPanelStorage();
        ViewPanelSearch viewPanelSearch = view.getViewPanelSearch();

        SeriesCrudSaver saverSeries = new SeriesCrudSaver();
        SeriesCrudDeleter deleterSeries = new SeriesCrudDeleter();
        SeriesCrudGetter getterSeries = new SeriesCrudGetter();

        Presenter presenterDeleteSeries = new PresenterDeleteSeries(viewPanelStorage, deleterSeries, getterSeries);
        Presenter presenterSaveChangesSeries = new PresenterSaveChangesSeries(viewPanelStorage, saverSeries);
        Presenter presenterSaveSeries = new PresenterSaveSeries(viewPanelStorage, viewPanelSearch, saverSeries, getterSeries);
        Presenter presenterSearchSeries = new PresenterSearchSeries(viewPanelSearch, getterSeries);

        viewPanelSearch.setPresenterSearchSeries(presenterSearchSeries);
        viewPanelSearch.setPresenterSaveSeries(presenterSaveSeries);

        viewPanelStorage.setPresenterDeleteSeries(presenterDeleteSeries);
        viewPanelStorage.setPresenterSaveChangesSeries(presenterSaveChangesSeries);
        viewPanelStorage.setSeriesComboBox(getterSeries.getTitlesSeries().stream().sorted().toArray());

        SwingUtilities.invokeLater(view::initView);
    }
}
