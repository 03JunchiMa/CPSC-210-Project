package ui.component.panel;

import model.Course;
import model.TimeTable;
import ui.component.button.AddButton;
import ui.component.table.CourseTable;
import ui.dialog.AddCourseDialog;
import ui.frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The upper left panel for the timetable
public class TimeTableUpperLeftPanel extends JPanel {

    protected MainFrame mainFrame;
    protected TimeTable timeTable;
    protected AddButton upperLeftButton;
    protected CourseTable courseTable;

    // EFFECTS: initialize the upper left panel of the time table panel
    public TimeTableUpperLeftPanel(MainFrame mainFrame, TimeTable timeTable) {
        this.mainFrame = mainFrame;
        this.timeTable = timeTable;

        initUpperLeftPanel();
        initCourseTable();
    }

    // MODIFIES: this
    // EFFECTS: initialize the upper left panel
    public void initUpperLeftPanel() {
        this.setLayout(new BorderLayout());
        initButton();
    }

    // MODIFIES: this
    // EFFECTS: initialize the course table
    public void initCourseTable() {
        courseTable = new CourseTable(timeTable);
        this.add(courseTable,BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(courseTable);
        this.add(scrollPane,BorderLayout.LINE_START);
    }

    // MODIFIES: this
    // EFFECTS: initialize the upper left upperLeftButton
    protected void initButton() {
        upperLeftButton = new AddButton(("Add the course"));
        upperLeftButton.setSize(30,30);
        this.add(upperLeftButton,BorderLayout.NORTH);
        addListener();
    }

    // MODIFIES: this
    // EFFECTS: add the action listener to the upper left button
    protected void addListener() {
        upperLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCourseDialog courseDialogDialog = new AddCourseDialog(mainFrame);
                if (courseDialogDialog.exec("Course")) {
                    Course course = courseDialogDialog.getEnteredValue();
                    timeTable.addIntendedCourse(course);
                    courseTable.addRow(course);
                }
            }
        });
    }

    // EFFECTS: set up the timetable
    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    // EFFECTS: return the course table
    public CourseTable getCourseTable() {
        return courseTable;
    }
}
