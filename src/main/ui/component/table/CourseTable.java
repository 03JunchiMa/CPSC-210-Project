package ui.component.table;

import model.Course;
import model.Expense;
import model.TimeTable;

import java.util.Vector;

// The customized table for adding displaying selected course
public class CourseTable extends DataTable {

    private TimeTable timeTable;

    // EFFECTS: construct a new course
    public CourseTable(TimeTable timeTable) {
        super();
        this.timeTable = timeTable;
    }

    // MODIFIES: super
    // EFFECTS: add the specified column for the course
    @Override
    protected void initModel() {
        super.model.addColumn("Course + Section");
        super.model.addColumn("Start");
        super.model.addColumn("End");
        super.model.addColumn("Weekday");
    }

    // MODIFIES: super
    // EFFECTS: add the row to the table
    @Override
    public void addRow(Object other) {
        Course course = (Course) other;
        Vector<Object> rowData = new Vector<>();
        rowData.add(course.getCourseNameSection());
        rowData.add(toTimeString(course.getStartTime()));
        rowData.add(toTimeString(course.getEndTime()));
        rowData.add(course.getWeekday());
        model.addRow(rowData);
    }

    // MODIFIES: this
    // EFFECTS: delete the corresponding row data in the model
    @Override
    protected void deleteRowData(int row) {
        String courseNameSection = this.getValueAt(row,0).toString() + " " + this.getValueAt(row,1).toString();
        int startTime = getTime(this.getValueAt(row,2).toString());
        int endTime = getTime(this.getValueAt(row,3).toString());
        int weekday = getTime(this.getValueAt(row,4).toString());
        Course course = new Course(courseNameSection,startTime,endTime,weekday);
        timeTable.deleteIntendedCourse(course);
    }

    // EFFECTS: return the string representation of the time
    public static String toTimeString(int time) {
        String temp = "";
        int j = time;
        temp = Integer.toString(j);
        String res;
        if (j < 1000) {
            res = temp.substring(0,1) + ":" + temp.substring(1);
        }  else {
            res = temp.substring(0,2) + ":" + temp.substring(2);
        }
        return res;
    }

    // EFFECTS: return the int representation of the time
    public int getTime(String timeString) {
        int time = 0;
        for (int i = 0; i < timeString.length(); i++) {
            if (timeString.charAt(i) == ':') {
                continue;
            } else {
                time *= 10;
                time += (timeString.charAt(i) - '0');
            }
        }
        return time;
    }

}
