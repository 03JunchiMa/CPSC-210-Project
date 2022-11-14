package ui.dialog;

import af.swing.LayoutBox;
import af.swing.layout.VLayout;
import ui.frame.MainFrame;

import javax.swing.*;

// The dialog for asking the information about the generating valid timetable
public class GenerateCourseDialog extends AddDialog {

    private JTextField numCourses;

    // EFFECTS: initialize the dialog, distribute it to the owner
    public GenerateCourseDialog(MainFrame owner) {
        super(owner);
    }

    // MODIFIES: this
    // EFFECTS: initialize the field corresponding to the number of courses
    @Override
    protected JComponent initField() {
        LayoutBox innerPanel = new LayoutBox().layout(new VLayout(4));
        innerPanel.padding(5);

        numCourses = new JTextField();
        innerPanel.add(createField("Number of courses you want to select?",numCourses));

        return innerPanel;
    }

    // EFFECTS: return the number of courses obtained from the field
    @Override
    public Object getEnteredValue() {
        int number = Integer.parseInt(numCourses.getText().toString());

        return number;
    }

}
