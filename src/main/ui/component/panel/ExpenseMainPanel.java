package ui.component.panel;

import model.Expense;
import model.ExpenseRecording;
import ui.component.table.ExpenseTable;
import ui.component.button.AddButton;
import ui.dialog.AddExpenseDialog;
import ui.frame.MainFrame;
import ui.graph.PieChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

// The panel for recording and viewing expenses
public class ExpenseMainPanel extends JPanel {

    private static final int P = (int)1e9 + 10;

    private MainFrame mainFrame;
    private PieChart pieChart;
    private ExpenseTable expenseTable;
    private ExpenseRecording expenseRecording;
    private AddButton addButton;

    // EFFECTS: initialize the expense panel
    public ExpenseMainPanel(ExpenseRecording expenseRecording) {
        this.expenseRecording = expenseRecording;
        this.setLayout(new BorderLayout());

        initExpenseTable();
        initButton();
        initPieChart();

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

        pieChart.addPart(1e-18,"1",getColor());
    }

    // MODIFIES: this
    // EFFECTS: initialize the expense table
    public void initExpenseTable() {
        expenseTable = new ExpenseTable(expenseRecording);
        this.add(expenseTable,BorderLayout.WEST);

        JScrollPane scrollPane = new JScrollPane(expenseTable);
        this.add(scrollPane,BorderLayout.LINE_START);
    }

    // EFFECTS: return the color corresponding to the
    public Color getColor() {
        return new Color(255,255,255);
    }

    // MODIFIES: this
    // EFFECTS: add the listener to the button
    public void addButtonListener() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddExpenseDialog expenseDialog = new AddExpenseDialog(mainFrame);
                if (expenseDialog.exec("Expense")) {
                    Expense expense = expenseDialog.getEnteredValue();
                    expenseRecording.addExpenseInfo(expense);
                    expenseTable.addRow(expense);
                    drawPieChart();
                }
            }
        });
    }

    // EFFECTS: return the positive int mod by rgb
    private int toPositiveIntByRGB(int x, int mod) {
        return ((x % mod) + mod) % mod;
    }

    // EFFECTS: draw the pie chart according to the category cost
    private void drawPieChart() {
        pieChart.repaint();
        for (Map.Entry<String,Integer> entry : expenseRecording.getCategoryCost().entrySet()) {
            int r = toPositiveIntByRGB((int)(Math.random() * 1000),255);
            int g = toPositiveIntByRGB((int)(Math.random() * 1000),255);
            int b = toPositiveIntByRGB((int)(Math.random() * 1000),255);
            pieChart.addPart(toPositiveIntByRGB(entry.getValue(),P),entry.getKey(),new Color(r,g,b));
        }
        pieChart.repaint();
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

    // MODIFIES: this
    // EFFECTS: load the data, and redraw the corresponding items in the expense panel
    public void loadData() {
        try {
            for (int id : expenseRecording.getExpenseIdList()) {
                Expense expense = expenseRecording.getInfoById(id);
                expenseTable.addRow(expense);
                drawPieChart();
            }
        } catch (Exception e) {
            System.out.println("Exception when load the data in expense panel");
        }
    }

}
