package ui.component.panel;

import af.swing.layout.VLayout;
import model.Course;
import model.Expense;
import model.TimeTable;
import ui.Main;
import ui.component.button.AddButton;
import ui.component.table.CourseTable;
import ui.dialog.AddCourseDialog;
import ui.frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeTableUpperLeftPanel extends JPanel {

    private MainFrame mainFrame;
    private TimeTable timeTable;
    private AddButton upperLeftButton;
    private CourseTable courseTable;

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
        initUpperLeftButton();
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
    public void initUpperLeftButton() {
        upperLeftButton = new AddButton(("Add the course"));
        upperLeftButton.setSize(30,30);
        this.add(upperLeftButton,BorderLayout.NORTH);
        addListener();
    }

    // MODIFIES: this
    // EFFECTS: add the action listener to the upper left button
    public void addListener() {
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

    // MODIFIES: this
    // EFFECTS: reload the course data into the table
    public void loadData() {
        try {
            for (Course course : timeTable.getTimetable()) {
                courseTable.addRow(course);
                System.out.println(course.getCourseNameSection() + " added");
            }
        } catch (Exception e) {
            System.out.println("Exception when load the data in expense panel");
        }
    }

    // EFFECTS: set up the timetable
    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

}
