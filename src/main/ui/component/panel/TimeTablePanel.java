package ui.component.panel;

import model.Course;
import model.TimeTable;
import model.TreeApp;
import ui.component.button.AddButton;
import ui.component.table.CourseTable;
import ui.dialog.AddCourseDialog;
import ui.frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The TimeTable Panel
public class TimeTablePanel extends JPanel {

    private MainFrame mainFrame;
    private TreeApp treeApp;
    private TimeTableUpperLeftPanel upperLeftPanel;
    private JPanel upperRightPanel;
    private JPanel lowerLeftPanel;
    private JPanel lowerRightPanel;

    // EFFECTS: initialize the timetable panel
    public TimeTablePanel(TreeApp treeApp, MainFrame mainFrame) {

        this.treeApp = treeApp;
        this.mainFrame = mainFrame;

        this.setLayout(new GridLayout(2,2));

        upperLeftPanel = new TimeTableUpperLeftPanel(mainFrame,treeApp.getTimeTable());
        upperRightPanel = new TimeTableUpperRightPanel(mainFrame,treeApp.getTimeTable());
        initLowerLeftPanel();
        initLowerRightPanel();

        this.add(upperLeftPanel);
        this.add(upperRightPanel);
        this.add(lowerLeftPanel);
        this.add(lowerRightPanel);

        setVisible(true);
    }


    // MODIFIES: this
    // EFFECTS: initialize the upper right panel
    public void initUpperRightPanel() {
        upperRightPanel = new JPanel();

        upperRightPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initialize the lower left panel
    public void initLowerLeftPanel() {
        lowerLeftPanel = new JPanel();

        lowerLeftPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initialize the lower right panel
    public void initLowerRightPanel() {
        lowerRightPanel = new JPanel();

        lowerRightPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: set up the main frame
    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }


    // EFFECTS: return the upper left panel
    public TimeTableUpperLeftPanel getUpperLeftPanel() {
        return this.upperLeftPanel;
    }

    // MODIFIES: this
    // EFFECTS: reload the course data into the table
    public void loadData() {
        try {
            for (Course course : treeApp.getTimeTable().getTimetable()) {
                upperLeftPanel.getCourseTable().addRow(course);
                System.out.println(course.getCourseNameSection() + " added");
            }
        } catch (Exception e) {
            System.out.println("Exception when load the data in expense panel");
        }
    }

    // MODIFIES: this
    // EFFECTS: set the timetable
    public void setTimeTable(TimeTable other) {
        this.treeApp.setTimetable(other);
    }




}
