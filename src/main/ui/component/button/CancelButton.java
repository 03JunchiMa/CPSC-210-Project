package ui.component.button;

import ui.frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// The cancel button for cancel the dialog
public class CancelButton extends JButton {

    // bidirectional relationship
    private SaveButton saveButton;

    // EFFECTS: initialize the cancel button
    public CancelButton(String title) {
        super(title);

        this.setBackground(new Color(101,101,101));

        setListener();

    }

    // EFFECTS: add the mouse listener to this button
    @SuppressWarnings("methodlength")
    private void setListener() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setBackground(new Color(44,102,210));
                saveButton.setBackground(new Color(101,101,101));
                saveButton.getExitDialog().setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(new Color(44,102,210));
                saveButton.setBackground(new Color(101,101,101));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(new Color(101,101,101));
                saveButton.setBackground(new Color(44,102,210));
                saveButton.getExitDialog().setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(44,102,210));
                saveButton.setBackground(new Color(101,101,101));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(101,101,101));
                saveButton.setBackground(new Color(44,102,210));
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: set the save button
    public void setSaveButton(SaveButton other) {
        if (saveButton != other) {
            if (saveButton != null) {
                removeSaveButton();
            }
            saveButton = other;
            saveButton.setCancelButton(this);
        }
    }


    // MODIFIES: this
    // EFFECTS: remove the save button, also modify the other side
    public void removeSaveButton() {
        if (saveButton != null) {
            SaveButton temp = saveButton;
            saveButton = null;
            temp.removeCancelButton();
        }
    }


}
