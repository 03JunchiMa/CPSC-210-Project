package model;

import org.json.JSONObject;
import persistence.Writable;

// model for treeApp, including all components
public class TreeApp implements Writable {

    private ExpenseRecording expenseRecording;
    private TimeTable timetable;

    // EFFECTS: initialize the TreeApp
    public TreeApp() {

    }

    // EFFECTS: set the expenseRecording
    public void setExpenseRecording(ExpenseRecording expenseRecording) {
        this.expenseRecording = expenseRecording;
    }

    // EFFECTS: set the timeTable
    public void setTimetable(TimeTable timetable) {
        this.timetable = timetable;
    }

    // EFFECTS: return this.expenseRecording
    public ExpenseRecording getExpenseRecording() {
        return this.expenseRecording;
    }

    // EFFECTS: return this.timeTable
    public TimeTable getTimeTable() {
        return this.timetable;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ExpenseRecording",expenseRecording.toJson());
        json.put("TimeTable",timetable.toJson());
        return json;
    }

}
