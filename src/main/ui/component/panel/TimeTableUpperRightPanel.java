package ui.component.panel;

import model.Course;
import model.TimeTable;
import ui.component.button.AddButton;
import ui.component.table.CourseTable;
import ui.dialog.AddCourseDialog;
import ui.dialog.GenerateCourseDialog;
import ui.frame.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static util.TimeTableAlgorithms.getValidTimeTable;

// The class for the upper right panel of the timetable panel, which will be used to show all the valid combinations
public class TimeTableUpperRightPanel extends TimeTableUpperLeftPanel {

    // EFFECTS: initialize the upper right panel
    public TimeTableUpperRightPanel(MainFrame mainFrame, TimeTable timeTable) {
        super(mainFrame,timeTable);
    }

    // MODIFIES: super
    // EFFECTS: initialize the customized button for generate timetables
    @Override
    public void initButton() {
        upperLeftButton = new AddButton(("Generate valid timetables"));
        upperLeftButton.setSize(30,30);
        this.add(upperLeftButton, BorderLayout.NORTH);
        addListener();
    }

    // MODIFIES: super
    // EFFECTS: add the listener to the button so that when the user clicks, all the valid combinations of the course
    // will appear in the table
    @Override
    protected void addListener() {
        upperLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerateCourseDialog dialog = new GenerateCourseDialog(mainFrame);
                if (dialog.exec("Number of Courses you want to select")) {
                    addTheTimeTable((Integer)dialog.getEnteredValue());
                }
            }
        });
    }

    // MODIFIE: super
    // EFFECTS: add all the valid timetables into the model
    public void addTheTimeTable(int numCourses) {
        ArrayList<ArrayList<Course>> timetable = getValidTimeTable(timeTable.getTimetable(),numCourses);
        System.out.println("Time table length " + timeTable.getTimetable().size());

        for (ArrayList<Course> courses : timetable) {
            for (Course course : courses) {
                super.courseTable.addRow(course);
            }
            super.courseTable.addRow(new Course("Separator",0,0,0));
        }
    }


}
