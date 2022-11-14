package ui.dialog;

import af.swing.LayoutBox;
import af.swing.layout.HLayout;
import model.Expense;
import ui.frame.MainFrame;

import javax.swing.*;
import java.awt.*;

public abstract class AddDialog extends JDialog {

    protected JPanel rootPanel;

    protected boolean checkAdd;

    // EFFECTS: initialize the expense dialog
    public AddDialog(MainFrame owner) {
        super(owner);
        initRootPanel();

        rootPanel.add(initField(), BorderLayout.CENTER);
        rootPanel.add(initConfirmButton(), BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: initialize the root panel
    public void initRootPanel() {
        rootPanel = new JPanel();
        this.setContentPane(rootPanel);
        rootPanel.setLayout(new BorderLayout());
    }

    // MODIFIES: this
    // EFFECTS: initialize the field for user input
    protected abstract JComponent initField();


    // EFFECTS: set up the text field
    protected JComponent createField(String label, JComponent comp) {
        LayoutBox panel = new LayoutBox().layout(new HLayout(4));
        panel.padding(4).preferredHeight(32);
        panel.add(new JLabel(label), "270px");
        panel.add(comp, "1w");
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: initialize the confirm button
    protected JComponent initConfirmButton() {
        LayoutBox panel = new LayoutBox().layout(new HLayout(4));
        panel.padding(4).preferredHeight(32);

        JButton okButton = new JButton("Confirm");
        panel.add(new JLabel(), "1w");
        panel.add(okButton);

        okButton.addActionListener((e) -> {
            checkAdd = true;
            setVisible(false);  // close the add expense dialog
        });
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: show the add expense dialog
    public boolean exec(String addedItem) {
        this.setTitle("Add the " + addedItem);
        this.setModal(true);
        this.setSize(350, 250);
        // modal
        this.setVisible(true);

        return checkAdd; // return true when the user clicked the confirm add
    }

    // EFFECTS: convert the user entered data into Expense, and return it
    public abstract Object getEnteredValue();

}
