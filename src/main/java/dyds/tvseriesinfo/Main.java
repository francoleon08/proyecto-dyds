package dyds.tvseriesinfo;

import com.formdev.flatlaf.FlatDarkLaf;
import dyds.tvseriesinfo.model.ModelFactory;
import dyds.tvseriesinfo.model.database.repository.DatabaseConnectionManager;
import dyds.tvseriesinfo.model.exceptions.DatabaseSQLException;
import dyds.tvseriesinfo.presenter.PresenterFactory;
import dyds.tvseriesinfo.view.ViewFactory;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        setLookAndFel();
        initDatabase();
        initApplication();
    }

    private static void setLookAndFel() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            UIManager.put("OptionPane.messageFont", UIManager.getFont("Label.font").deriveFont(16f));
            UIManager.put("OptionPane.buttonFont", UIManager.getFont("Label.font").deriveFont(16f));
        } catch (Exception ignored) {
        }
    }

    private static void initApplication() {
        ViewFactory viewFactory = new ViewFactory();
        ModelFactory modelFactory = new ModelFactory();
        PresenterFactory presenterFactory = new PresenterFactory(viewFactory, modelFactory);
        presenterFactory.initPresenters();
        SwingUtilities.invokeLater(viewFactory.getGeneralView()::initView);
    }

    private static void initDatabase() {
        try {
            DatabaseConnectionManager.initializeDatabases();
        } catch (DatabaseSQLException e) {
            throw new RuntimeException(e);
        }
    }
}
