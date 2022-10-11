package model;

// Course class, provide the struct for the course
public class Course {
    private String courseNameSection;
    private int sectionNumber;
    private int startTime;
    private int endTime;
    private int weekday;

    // REQUIRES: the entered startTime should be translated into an integer, for example (9:30 should be entered as 930)
    // the weekday should be also be entered as an integer, if there's multiple workdays, enter the combined integer
    // for example, if the weekday is Tuesday and Thursday, then the user should enter 24.
    // the courseNameSection should be entered as the format "CPSC 210 102", the last one is the section number
    // EFFECTS: create a new course with coursename, course section, course start time and end time.
    // the startTime and endTime is in 24-hour clock,

    public Course(String courseNameSection, int startTime, int endTime, int weekday) {
        this.courseNameSection = courseNameSection;
        this.sectionNumber = sectionNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekday = weekday;
    }
}
