package ui.component.panel;

import model.ExpenseRecording;

import javax.swing.*;

// The condensed information panel for displaying the expense
public class ExpenseInfoPanel extends JPanel {

    private ExpenseRecording expenseRecording;
    private JLabel label;

    // EFFECTS: set up the info panel
    public ExpenseInfoPanel(ExpenseRecording expenseRecording) {
        initLabel(expenseRecording);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initialize the label
    private void initLabel(ExpenseRecording expenseRecording) {
        setExpenseRecording(expenseRecording);
        this.label = new JLabel("Your remaining budget is: " + expenseRecording.getBudget());
        label.setSize(200,200);
        label.setLocation(50,50);
    }

    // MODIFIES: this
    // EFFECTS: initialize the expense recording
    public void setExpenseRecording(ExpenseRecording expenseRecording) {
        this.expenseRecording = expenseRecording;
    }
}
