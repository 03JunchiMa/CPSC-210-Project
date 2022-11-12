package ui.dialog;

import ui.component.button.CancelButton;
import ui.component.button.SaveButton;
import ui.frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

// The dialog for the exit menu item located in the menu bar in the main frame
public class ExitDialog extends JDialog {

    private MainFrame mainFrame;
    private JPanel exitDialogPanel;
    private CancelButton cancelButton;
    private SaveButton saveButton;

    // EFFECTS: initialize the dialog for the exit menu item, the dialog will be set to the round and undecorated
    public ExitDialog(MainFrame owner) {
        super(owner);

        try {
            this.mainFrame = owner;
        } catch (Exception e) {
            System.out.println("Wrong type");
        }

        this.setBounds(150,83,300,200);
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);

        this.setShape(new RoundRectangle2D.Double(0,0,300,200,30,30));
        this.setSize(300,200);

        initPanel();
        initButton();

        this.setModal(true);
        this.setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: initialize the exit dialog panel
    public void initPanel() {
        exitDialogPanel = new JPanel();
        this.add(exitDialogPanel,BorderLayout.CENTER);
        this.setLayout(new BorderLayout());
    }

    // MODIFIES: this
    // EFFECTS: initialize the cancel and save button
    public void initButton() {
        cancelButton = new CancelButton("Cancel");
        saveButton = new SaveButton("Save");


        saveButton.setExitDialog(this);
        cancelButton.setSaveButton(saveButton);

        this.add(cancelButton,BorderLayout.WEST);
        this.add(saveButton,BorderLayout.EAST);
    }

    // EFFECTS: return the main frame
    public MainFrame getMainFrame() {
        return this.mainFrame;
    }


    // MODIFIES: this
    // EFFECTS: redraw the dialog
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(135,135,135));
        g.setColor(new Color(135,135,135));
        g.drawString("Unsaved Progress",this.getWidth() / 3, 20);
    }

}
