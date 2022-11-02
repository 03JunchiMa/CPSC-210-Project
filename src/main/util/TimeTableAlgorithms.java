package util;

import model.Course;

import java.util.*;

// providing algorithms for timetable
public class TimeTableAlgorithms {
    private static final int INTERVALLENGTH = 2410;
    private ArrayList<ArrayList<Course>> validTimeTable;
    private HashMap<String,Boolean> checkNoDuplicateCourse;
    private HashMap<Integer,int[]> timeInterval;
    private ArrayList<Course> currentCourses;

    // REQUIRES: courses has no duplicates elements
    // EFFECTS: return all the valid combinations of the timetable
    public ArrayList<ArrayList<Course>> getValidTimeTable(ArrayList<Course> duplicateCourses, int numberOfCourses) {
        validTimeTable = new ArrayList<>();
        checkNoDuplicateCourse = new HashMap<>();
        currentCourses = new ArrayList<>();
        timeInterval = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            timeInterval.put(i, new int[INTERVALLENGTH]);
        }
        ArrayList courses = unique(duplicateCourses);
        courses = sortCourseByTimeDuration(courses); // optimize search order, starting from the longest time duration
        searchTimeTables(courses,0,numberOfCourses,0);
        return validTimeTable;
    }

    // EFFECTS: return the courses with no duplicates
    public ArrayList<Course> unique(ArrayList<Course> duplicateCourses) {
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Course> sortedCourses;
        sortedCourses = sortCourseByLexicographical(duplicateCourses);

        for (int i = 0; i < sortedCourses.size(); i++) {
            if (i == 0 || !sortedCourses.get(i).toString().equals(sortedCourses.get(i - 1).toString())) {
                courses.add(sortedCourses.get(i));
            }
        }
        return courses;
    }

    // MODOFIES: ArrayList<ArrayList<Course>>
    // EFFECTS: search all the valid timetables combinations
    public void searchTimeTables(ArrayList<Course> courseForSelection, int u, int n, int last) {
        if (currentCourses.size() + (courseForSelection.size() - last) < n) {
            return;
        }

        if (currentCourses.size() == n) {
            addToTimeTable();
            return;
        }

        for (int i = last; i < courseForSelection.size(); i++) {
            Course course = courseForSelection.get(i);
            String courseName = course.getCourseNameSection();
            if (! checkNoDuplicateCourse.containsKey(courseName)) {
                if (checkTimeTable(course)) {
                    addCourseToTimeTable(course,1);
                    checkNoDuplicateCourse.put(courseName,true);
                    currentCourses.add(course);

                    searchTimeTables(courseForSelection, u + 1, n, i + 1);
                    addCourseToTimeTable(course,-1);
                    checkNoDuplicateCourse.remove(courseName);
                    currentCourses.remove(currentCourses.size() - 1);
                }
            }
        }

    }

    // EFFECTS: add a course to the timetable
    private void addToTimeTable() {
        ArrayList<Course> tempCourses = new ArrayList<>();
        for (int i = 0; i < currentCourses.size(); i++) {
            tempCourses.add(currentCourses.get(i));
        }
        validTimeTable.add(tempCourses);
    }

    // // EFFECTS: add a course to the timetable, so that the time slot is occupied
    public void addCourseToTimeTable(Course course, int c) {
        int weekday = course.getWeekday();
        while (weekday > 0) {
            int day = weekday % 10;
            weekday /= 10;

            int startTime = course.getStartTime();
            int endTime = course.getEndTime();

            int[] arr = timeInterval.get(day);

            for (int i = startTime; i <= endTime; i++) {
                arr[i] += c;
            }
        }
    }

    // EFFECTS: check whether a class can be added into the timetable
    public boolean checkTimeTable(Course course) {

        int weekday = course.getWeekday();
        boolean check = true;
        while (weekday > 0) {
            int day = weekday % 10;
            weekday /= 10;

            int startTime =  course.getStartTime();
            int endTime = course.getEndTime();

            int[] arr = timeInterval.get(day);

            for (int i = startTime + 1; i < endTime; i++) {
                if (arr[i] != 0) {
                    check = false;
                    break;
                }
            }
        }
        return check;
    }


    // EFFECTS: sort Course by lexicographical order
    public ArrayList<Course> sortCourseByLexicographical(ArrayList<Course> tempCourses) {
        ArrayList<Course> courses = new ArrayList<>();

        for (int i = 0; i < tempCourses.size(); i++) {
            courses.add(tempCourses.get(i));
        }

        Collections.sort(courses, new Comparator<Course>() {
            @Override
            public int compare(Course c1, Course c2) {
                return c1.toString().compareTo(c2.toString());
            }
        });

        return courses;
    }

    // EFFECTS: sort Course by time interval length order
    public ArrayList<Course> sortCourseByTimeDuration(ArrayList<Course> tempCourses) {
        ArrayList<Course> courses = new ArrayList<>();

        for (int i = 0; i < tempCourses.size(); i++) {
            courses.add(tempCourses.get(i));
        }

        Collections.sort(courses, new Comparator<Course>() {
            @Override
            public int compare(Course c1, Course c2) {
                return (c2.getEndTime() - c2.getStartTime()) - (c1.getEndTime() - c1.getStartTime());

            }
        });
        return courses;
    }

    // EFFECTS: sort Course by start time
    public ArrayList<Course> sortCourseByStartTime(ArrayList<Course> tempCourses) {
        ArrayList<Course> courses = new ArrayList<>();

        for (int i = 0; i < tempCourses.size(); i++) {
            courses.add(tempCourses.get(i));
        }

        Collections.sort(courses, new Comparator<Course>() {
            @Override
            public int compare(Course c1, Course c2) {
                return c1.getStartTime() - c2.getStartTime();

            }
        });
        return courses;
    }

    // EFFECTS: get the course name inside the course
    public String getCourseName(Course course) {
        String courseNameSection = course.getCourseNameSection();
        int lastSpace = courseNameSection.lastIndexOf(' ');
        return courseNameSection.substring(0,lastSpace);
    }

    // EFFECTS: get the courseSection inside the course
    public String getCourseSection(Course course) {
        String courseNameSection = course.getCourseNameSection();
        int lastSpace = courseNameSection.lastIndexOf(' ');
        return courseNameSection.substring(lastSpace + 1);
    }

    // EFFECTS: return all the valid combinations of the timetable that don't have any classes between the given
    // time slot (this means in this time slot you are free)
    public ArrayList<ArrayList<Course>> getTimeTableAvoidTimeSlot(ArrayList<ArrayList<Course>> allCoursesComb,
                                                                  int startTime, int endTime) {
        ArrayList<ArrayList<Course>> timeTableAvoidedTimeSlot = new ArrayList<>();
        for (ArrayList<Course> courses : allCoursesComb) {
            boolean check = true;
            for (Course course : courses) {
                int courseStart = course.getStartTime();
                int courseEnd = course.getEndTime();
                if (courseStart < startTime) {
                    if (courseEnd > startTime && courseEnd < endTime) {
                        check = false;
                        break;
                    }
                } else if (courseStart < endTime) {
                    check = false;
                    break;
                }
            }
            if (check) {
                timeTableAvoidedTimeSlot.add(courses);
            }
        }
        return timeTableAvoidedTimeSlot;
    }

    // EFFECTS: return all the valid combination of the timetable that can have lunchtime, which means there
    // are no courses between 11:00 and 13:00
    public ArrayList<ArrayList<Course>> getTimeTableForLunchTime(ArrayList<ArrayList<Course>> allCoursesComb) {
        return getTimeTableAvoidTimeSlot(allCoursesComb,1100,1300);
    }

}
