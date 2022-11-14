package ui.dialog;

import af.swing.LayoutBox;
import af.swing.layout.HLayout;
import af.swing.layout.VLayout;
import model.Course;
import ui.component.combobox.TimeSectionComboBox;
import ui.frame.MainFrame;

import javax.swing.*;

// The customized dialog for adding the timetable
public class AddCourseDialog extends AddDialog {

    JTextField nameField;
    JTextField sectionField;
    TimeSectionComboBox startTimeField;
    TimeSectionComboBox endTimeField;
    JTextField weekdayField;

    // EFFECTS: distribute this to the owner
    public AddCourseDialog(MainFrame owner) {
        super(owner);
    }

    // MODIFIES: this
    // EFFECTS: initialize the dialog field
    @Override
    public JComponent initField() {
        LayoutBox innerPanel = new LayoutBox().layout(new VLayout(4));
        innerPanel.padding(5);

        nameField = new JTextField();
        sectionField = new JTextField();
        startTimeField = new TimeSectionComboBox();
        endTimeField = new TimeSectionComboBox();
        weekdayField = new JTextField();

        innerPanel.add(createField("Course Name (CPSC 210):",nameField));
        innerPanel.add(createField("Section:",sectionField));
        innerPanel.add(createField("Start Time:",startTimeField));
        innerPanel.add(createField("End Time:",endTimeField));
        innerPanel.add(createField("Week Day (Mon/Tue/Fri = 125):",weekdayField));

        return innerPanel;
    }

    // EFFECTS: create the field with the label and the JComponent
    @Override
    public JComponent createField(String label, JComponent comp) {
        LayoutBox panel = new LayoutBox().layout(new HLayout(4));
        panel.padding(4).preferredHeight(32);
        panel.add(new JLabel(label), "200px");
        panel.add(comp, "1w");
        return panel;
    }

    // EFFECTS: return entered course
    @Override
    public Course getEnteredValue() {
        String courseName = nameField.getText().trim();
        String section = sectionField.getText().trim();
        int startTime = startTimeField.getSelectedTime();
        int endTime = endTimeField.getSelectedTime();
        int weekday = Integer.parseInt(weekdayField.getText());

        Course course = new Course(courseName + " " + section, startTime,endTime,weekday);

        return course;
    }

}
