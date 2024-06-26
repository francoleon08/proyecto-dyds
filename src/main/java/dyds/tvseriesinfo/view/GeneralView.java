package dyds.tvseriesinfo.view;

import dyds.tvseriesinfo.view.tabbedPane.ViewPanelPuntuaction;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class GeneralView implements BaseView {
    private JPanel contentPane;
    @Getter
    private JTabbedPane SeriesTabbedPane;
    private JPanel searchPanel;
    private JPanel storagePanel;
    private JPanel punctuactionPanel;
    @Getter
    private ViewPanelSearch viewPanelSearch;
    @Getter
    private ViewPanelStorage viewPanelStorage;
    @Getter
    private ViewPanelPuntuaction viewPanelPuntuaction;

    public void initView() {
        JFrame frame = new JFrame("TV Series Info Repo");
        configureJFrame(frame);
    }

    private void configureJFrame(JFrame frame) {
        frame.setContentPane(getContet());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Container getContet() {
        return contentPane;
    }

    public void setSelectTab(int index) {
        SeriesTabbedPane.setSelectedIndex(index);
    }
}
