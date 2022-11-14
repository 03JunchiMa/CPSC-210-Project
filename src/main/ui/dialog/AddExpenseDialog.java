package ui.dialog;

import af.swing.LayoutBox;
import af.swing.layout.HLayout;
import af.swing.layout.VLayout;
import model.Expense;
import ui.frame.MainFrame;

import javax.swing.*;
import java.awt.*;

// The pop-up dialog for adding the expense
// Class reference: https://study.163.com/course/courseLearn.htm?courseId=1208935832#/learn/video?lessonId=1283449103&courseId=1208935832
public class AddExpenseDialog extends AddDialog {

    private JTextField amountField;
    private JTextField categoryField;

    // EFFECTS: initialize the expense dialog
    public AddExpenseDialog(MainFrame owner) {
        super(owner);
    }

    // MODIFIES: this
    // EFFECTS: initialize the field for user input
    @Override
    protected JComponent initField() {
        LayoutBox innerPanel = new LayoutBox().layout(new VLayout(4));
        innerPanel.padding(4);

        amountField = new JTextField();
        categoryField = new JTextField();

        innerPanel.add(createField("Expense Amount (+/- for Income/Expense)",amountField));
        innerPanel.add(createField("Category",categoryField));

        return innerPanel;
    }

    // EFFECTS: convert the user entered data into Expense, and return it
    @Override
    public Expense getEnteredValue() {
        int amount = Integer.parseInt(amountField.getText().trim());
        String category = categoryField.getText().trim();

        return new Expense(amount,category);
    }

}


