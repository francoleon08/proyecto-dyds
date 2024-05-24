package dyds.tvseriesinfo.view;

import javax.swing.*;

import dyds.tvseriesinfo.view.tabbedPane.ViewPanelSearch;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;
import lombok.Getter;

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
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void initView() {
    JFrame frame = new JFrame("TV Series Info Repo");
    frame.setContentPane(getContet());
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  public Container getContet() {
    return contentPane;
  }
}
