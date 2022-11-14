package ui.component.panel;

import model.TimeTable;
import ui.component.button.AddButton;
import ui.dialog.AddCourseDialog;
import ui.frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The TimeTable Panel
public class TimeTablePanel extends JPanel {

    private MainFrame mainFrame;
    private TimeTable timeTable;
    private TimeTableUpperLeftPanel upperLeftPanel;
    private JPanel upperRightPanel;
    private JPanel lowerLeftPanel;
    private JPanel lowerRightPanel;
    private AddButton upperLeftButton;

    // EFFECTS: initialize the timetable panel
    public TimeTablePanel(TimeTable timeTable, MainFrame mainFrame) {

        this.timeTable = timeTable;
        this.mainFrame = mainFrame;

        this.setLayout(new GridLayout(2,2));

        upperLeftPanel = new TimeTableUpperLeftPanel(mainFrame,timeTable);
        initUpperRightPanel();
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

    // MODIFIES: this
    // EFFECTS: set up the timetable
    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    // EFFECTS: return the upper left panel
    public TimeTableUpperLeftPanel getUpperLeftPanel() {
        return this.upperLeftPanel;
    }
}
