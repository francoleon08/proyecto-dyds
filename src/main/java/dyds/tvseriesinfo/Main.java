package dyds.tvseriesinfo;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import dyds.tvseriesinfo.model.database.SQLmanager.DatabaseConnectionManager;
import dyds.tvseriesinfo.presenter.*;
import dyds.tvseriesinfo.view.View;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        setLookAndFel();
        DatabaseConnectionManager.initializeDatabase();

        View view = new View();
        ViewPanelStorage viewPanelStorage = view.getViewPanelStorage();
        ViewPanelSearch viewPanelSearch = view.getViewPanelSearch();
        ViewPanelPuntuaction viewPanelPuntuaction = view.getViewPanelPuntuaction();

        Presenter presenterGetterSeries = new PresenterGetterSeries(viewPanelStorage);
        Presenter presenterLoadLocalSeries = new PresenterLoadLocalSeries(viewPanelStorage);
        Presenter presenterDeleteSeries = new PresenterDeleteSeries(viewPanelStorage);
        Presenter presenterSaveChangesSeries = new PresenterSaveChangesSeries(viewPanelStorage);
        Presenter presenterSaveSeries = new PresenterSaveSeries(viewPanelSearch);
        Presenter presenterSearchSeries = new PresenterSearchSeries(viewPanelSearch);

        SwingUtilities.invokeLater(view::initView);
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
