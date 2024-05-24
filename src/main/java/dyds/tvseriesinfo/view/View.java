package dyds.tvseriesinfo.view;

import java.awt.*;

import javax.swing.*;

import dyds.tvseriesinfo.model.dataAccess.DataBase;

public class View {
  // ventana principal
  private JPanel contentPane;
  private JTabbedPane SeriesTabbedPane;

  private JPanel searchPanel;
  private JPanel storagePanel;
  private ViewPanelSearch viewPanelSearch;
  private ViewPanelStorage viewPanelStorage;


  public View() {
    try {
      // Set System L&F
      UIManager.put("nimbusSelection", new Color(247,248,250));

      for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    }
    catch (Exception e) {
      System.out.println("Something went wrong with UI!");
    }
  }

  public void show() {
    JFrame frame = new JFrame("TV Series Info Repo");

    frame.setContentPane(new View().contentPane);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}
