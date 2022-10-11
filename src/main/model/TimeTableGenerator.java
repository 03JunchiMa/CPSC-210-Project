package model;

import java.util.ArrayList;

// generate all the possible timetables based on the user input
public class TimeTableGenerator {

    private ArrayList<Course> timetable;

    // EFFECT: start with a new timetable generator
    public TimeTableGenerator() {
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
            return false;
        } else {
            timetable.add(course);
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: delete the specific course, if the course is deleted successfully, return true
    // otherwise return false
    public boolean deleteIntendedCourse(Course course) {
        if (!timetable.contains(course)) {
            return false;
        } else {
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
}
