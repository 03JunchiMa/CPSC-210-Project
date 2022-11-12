package ui.component.panel;

import model.Expense;
import model.ExpenseRecording;
import ui.component.ExpenseTable;
import ui.component.button.AddButton;
import ui.dialog.AddExpenseDialog;
import ui.frame.MainFrame;
import ui.graph.PieChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The panel for recording and viewing expenses
public class ExpensePanel extends JPanel {

    private MainFrame mainFrame;
    private PieChart pieChart;
    private ExpenseTable expenseTable;
    private ExpenseRecording expenseRecording;
    private AddButton addButton;

    // EFFECTS: initialize the expense panel
    public ExpensePanel() {
        this.setLayout(new BorderLayout());

        initPieChart();
        initExpenseTable();
        initButton();

        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initialize the button
    private void initButton() {
        addButton = new AddButton("Add an expense");
        this.add(addButton,BorderLayout.NORTH);

        addButtonListener();
    }

    // MODIFIES: this
    // EFFECTS: initialize the pie chart
    public void initPieChart() {
        pieChart = new PieChart();
        this.add(pieChart,BorderLayout.CENTER);

        pieChart.addPart(100,"1",getColor());
        pieChart.addPart(30,"2",getColor());
    }

    // MODIFIES: this
    // EFFECTS: initialize the expense table
    public void initExpenseTable() {
        expenseTable = new ExpenseTable();
        this.add(expenseTable,BorderLayout.WEST);

        JScrollPane scrollPane = new JScrollPane(expenseTable);
        this.add(scrollPane,BorderLayout.LINE_START);
    }

    // EFFECTS: return the color corresponding to the
    public Color getColor() {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: add the listener to the button
    public void addButtonListener() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddExpenseDialog expenseDialog = new AddExpenseDialog(mainFrame);
                if (expenseDialog.exec()) {
                    Expense expense = expenseDialog.getEnteredExpense();
                    expenseRecording.addExpenseInfo(expense);
                    expenseTable.addRow(expense);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: set the main frame
    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    // MODIFIES: this
    // EFFECTS: set the expense recording
    public void setExpenseRecording(ExpenseRecording expenseRecording) {
        this.expenseRecording = expenseRecording;
    }

}
