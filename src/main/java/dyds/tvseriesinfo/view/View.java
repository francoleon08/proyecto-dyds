package dyds.tvseriesinfo.view;

import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class View implements BaseView {
    private JPanel contentPane;
    private JTabbedPane SeriesTabbedPane;
    private JPanel searchPanel;
    private JPanel storagePanel;
    @Getter
    private ViewPanelSearch viewPanelSearch;
    @Getter
    private ViewPanelStorage viewPanelStorage;

    public View() {

    }

    public void initView() {
        JFrame frame = new JFrame("TV Series Info Repo");
        configureJFrame(frame);
    }

    private void configureJFrame(JFrame frame) {
        frame.setContentPane(getContet());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public Container getContet() {
        return contentPane;
    }
}
