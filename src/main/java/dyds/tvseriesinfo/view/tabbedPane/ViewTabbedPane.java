package dyds.tvseriesinfo.view.tabbedPane;

import javax.swing.*;

public abstract class ViewTabbedPane extends JPanel {
    protected boolean activeMessageDialog = true;

    public abstract void setWorkingState(boolean working);

    public void setActiveMessageDialog(boolean activeMessageDialog) {
        this.activeMessageDialog = activeMessageDialog;
    }

    public void showMessageDialog(String message) {
        if (activeMessageDialog) {
            JOptionPane.showMessageDialog(this, message);
        }
    }
}
