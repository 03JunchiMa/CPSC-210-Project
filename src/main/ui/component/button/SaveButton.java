package ui.component.button;

import ui.dialog.ExitDialog;
import ui.frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// The save button for saving the progress
public class SaveButton extends JButton {

    // bidirectional relationship
    private CancelButton cancelButton;

    private ExitDialog exitDialog;

    // EFFECTS: intialize the save button
    public SaveButton(String title) {
        super(title);

        this.setBackground(new Color(44,102,210));

        addListener();
    }

    // EFFECTS: add the listener to this
    private void addListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                exitDialog.getMainFrame().saveTreeApp();
                exitDialog.setVisible(false);
                exitDialog.getMainFrame().setVisible(false);
                System.exit(0);
            }


            @Override
            public void mouseReleased(MouseEvent e) {
                exitDialog.getMainFrame().saveTreeApp();
                exitDialog.setVisible(false);
                exitDialog.getMainFrame().setVisible(false);
                System.exit(0);
            }

        });
    }

    // MODIFIES: this
    // EFFECTS: add the cancel button
    public void setCancelButton(CancelButton other) {
        if (cancelButton != other) {
            if (cancelButton != null) {
                removeCancelButton();
            }
            cancelButton = other;
            cancelButton.setSaveButton(this);
        }
    }

    // MODIFIES: this
    // EFFECTS: remove the cancel button
    public void removeCancelButton() {
        if (cancelButton != null) {
            CancelButton temp = cancelButton;
            this.cancelButton = null;
            temp.removeSaveButton();
        }
    }

    // MODIFIES: this
    // EFFECTS: set the dialog
    public void setExitDialog(ExitDialog dialog) {
        this.exitDialog = dialog;
    }

    // EFFECTS: return exit dialog
    public Dialog getExitDialog() {
        return this.exitDialog;
    }

}
