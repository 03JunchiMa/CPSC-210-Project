package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// generate all the possible timetables based on the user input
public class TimeTable implements Writable {

    private ArrayList<Course> timetable;

    // EFFECT: start with a new timetable generator
    public TimeTable() {
        timetable = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a new course to the course generator
    // if the course is added successfully,return true
    // exact same course can't be added multiple times, exact means they have same courseNameSection number and time,
    // and workday
    // otherwise return false
    public boolean addIntendedCourse(Course course) {
        if (timetable.contains(course)) {
            EventLog.getInstance().logEvent(new Event("Unsuccessful adding the course: " + course));
            return false;
        } else {
            EventLog.getInstance().logEvent(new Event("Added course: " + course));
            timetable.add(course);
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: delete the specific course, if the course is deleted successfully, return true
    // otherwise return false
    public boolean deleteIntendedCourse(Course course) {
        if (!timetable.contains(course)) {
            EventLog.getInstance().logEvent(new Event("Unsuccessful deleting the course: " + course));
            return false;
        } else {
            EventLog.getInstance().logEvent(new Event("Removed course: " + course));
            timetable.remove(course);
            return true;
        }
    }


    // EFFECTS: return true if a course is being added, return false otherwise
    public boolean containsCourse(Course course) {
        return timetable.contains(course);
    }

    // EFFECTS: return the number of the selected course
    public int getTotalCourse() {
        return timetable.size();
    }

    // EFFECTS: return the timetable
    public ArrayList<Course> getTimetable() {
        return this.timetable;
    }

    // EFFECTS: compare two timetable, return true if they are equals (two timetables have the same course element)
    // return false otherwise, this method is used for testing the getTimeTable method
    public boolean compareToCurrentTimeTable(ArrayList<Course> timetable) {
        if (getTimetable().size() != timetable.size()) {
            return false;
        }

        for (int i = 0; i < getTimetable().size(); i++) {
            boolean check = false;
            for (int j = 0; j < timetable.size(); j++) {
                if (getTimetable().get(i).toString().equals(timetable.get(j).toString())) {
                    check = true;
                }
            }
            if (!check) {
                return false;
            }
        }

        return true;
    }

    // EFFECTS: convert the timetable to json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Courses",coursesToJson());
        return json;
    }

    // EFFECTS: return courses as a JSON array
    public JSONArray coursesToJson() {
        JSONArray jsonArr = new JSONArray();

        for (Course course : this.timetable) {
            jsonArr.put(course.toJson());
        }

        return jsonArr;
    }

}
