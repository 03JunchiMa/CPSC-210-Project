package ui.dialog;

import af.swing.LayoutBox;
import af.swing.layout.HLayout;
import af.swing.layout.VLayout;
import model.Expense;
import ui.component.panel.ExpensePanel;
import ui.frame.MainFrame;

import javax.swing.*;
import java.awt.*;

// The pop-up dialog for adding the expense
// Class reference: https://study.163.com/course/courseLearn.htm?courseId=1208935832#/learn/video?lessonId=1283449103&courseId=1208935832
public class AddExpenseDialog extends JDialog {

    private JPanel rootPanel;
    private JTextField amountField;
    private JTextField categoryField;

    private boolean checkAdd;

    // EFFECTS: initialize the expense dialog
    public AddExpenseDialog(MainFrame owner) {
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
    public JComponent initField() {
        LayoutBox innerPanel = new LayoutBox().layout(new VLayout(4));
        innerPanel.padding(4);

        amountField = new JTextField();
        categoryField = new JTextField();

        innerPanel.add(createField("Expense Amount (+/- for Income/Expense)",amountField));
        innerPanel.add(createField("Category",categoryField));

        return innerPanel;
    }


    // EFFECTS: set up the text field
    private JComponent createField(String label, JComponent comp) {
        LayoutBox panel = new LayoutBox().layout(new HLayout(4));
        panel.padding(4).preferredHeight(32);
        panel.add(new JLabel(label), "270px");
        panel.add(comp, "1w");
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: initialize the confirm button
    private JComponent initConfirmButton() {
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
    public boolean exec() {
        this.setTitle("Add the expense");
        this.setModal(true);
        this.setSize(350, 250);
        // modal
        this.setVisible(true);

        return checkAdd; // return true when the user clicked the confirm add
    }

    // EFFECTS: convert the user entered data into Expense, and return it
    public Expense getEnteredExpense() {
        int amount = Integer.parseInt(amountField.getText().trim());
        String category = categoryField.getText().trim();

        return new Expense(amount,category);
    }

}


