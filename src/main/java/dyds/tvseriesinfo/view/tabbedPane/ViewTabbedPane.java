package dyds.tvseriesinfo.view.tabbedPane;

import javax.swing.*;

public abstract class ViewTabbedPane extends JPanel {

    public abstract void setWorkingState(boolean working);

    public void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void setSelectedTab(int index) {

    }
}
