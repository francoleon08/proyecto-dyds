package dyds.tvseriesinfo;

import com.formdev.flatlaf.FlatDarkLaf;
import dyds.tvseriesinfo.model.ModelFactory;
import dyds.tvseriesinfo.model.database.SQLmanager.DatabaseConnectionManager;
import dyds.tvseriesinfo.presenter.PresenterFactory;
import dyds.tvseriesinfo.view.ViewFactory;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        setLookAndFel();
        DatabaseConnectionManager.initializeDatabases();

        ViewFactory viewFactory = new ViewFactory();
        ModelFactory modelFactory = new ModelFactory();
        PresenterFactory presenterFactory = new PresenterFactory(viewFactory, modelFactory);
        presenterFactory.initPresenters();

        SwingUtilities.invokeLater(viewFactory.getGeneralView()::initView);
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
