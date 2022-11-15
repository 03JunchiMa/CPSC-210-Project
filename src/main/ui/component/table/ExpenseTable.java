package ui.component.table;

import model.Expense;
import model.ExpenseRecording;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

// The customized table for recording expense
public class ExpenseTable extends DataTable {

    private ExpenseRecording expenseRecording;

    // EFFECTS: initialize the customized table
    public ExpenseTable(ExpenseRecording expenseRecording) {
        super();
        this.expenseRecording = expenseRecording;
        System.out.println(expenseRecording.getExpenseIdList().toString());
    }

    // MODIFIES: this
    // EFFECTS: initialize the model
    @Override
    protected void initModel() {
        model.addColumn("Expense Amount");
        model.addColumn("Category");
        model.addColumn("id");
        model.addColumn("Date");
    }

    // MODIFIES: this
    // EFFECTS: add the corresponding data to row
    @Override
    public void addRow(Object other) {
        Expense expense = (Expense) other;
        Vector<Object> rowData = new Vector<>();
        rowData.add(expense.getAmount());
        rowData.add(expense.getCategory());
        rowData.add(expense.getId());
        rowData.add(getDate());
        model.addRow(rowData);
        System.out.println("added");
    }

    // MODIFIES: super
    // EFFECTS: delete the selected rows from model
    @Override
    public void deleteRowData(int row) {
        int id = Integer.parseInt(this.getValueAt(row,2).toString());
        expenseRecording.deleteExpenseInfo(id);
    }

    // MODIFIES: this
    // EFFECTS: set the expense recording:
    public void setExpenseRecording(ExpenseRecording other) {
        this.expenseRecording = other;
    }

}
