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
    // EFFECTS: search all the timetables
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

    private void addToTimeTable() {
        ArrayList<Course> tempCourses = new ArrayList<>();
        for (int i = 0; i < currentCourses.size(); i++) {
            tempCourses.add(currentCourses.get(i));
        }
        validTimeTable.add(tempCourses);
    }

    public void addCourseToTimeTable(Course course,int c) {
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

    // get the course name inside the course
    public String getCourseName(Course course) {
        String courseNameSection = course.getCourseNameSection();
        int lastSpace = courseNameSection.lastIndexOf(' ');
        return courseNameSection.substring(0,lastSpace);
    }

    // get the courseSection inside the course
    public String getCourseSection(Course course) {
        String courseNameSection = course.getCourseNameSection();
        int lastSpace = courseNameSection.lastIndexOf(' ');
        return courseNameSection.substring(lastSpace + 1);
    }

//    // EFFECTS: return all the valid combinations of the timetable that have more lunchtime
//    // this means between 11:00 to 13:00, you can have at least 1 hour for lunchtime.
//    public ArrayList<ArrayList<Course>> getTimeTableCanHaveLunchTime(ArrayList<ArrayList<Course>> allCoursesComb) {
//        ArrayList<ArrayList<Course>> timeTable;
//        for (int i = 0; i < allCoursesComb.size(); i++) {
//            ArrayList<Course> courses = allCoursesComb.get(i);
//
//            for (int j = 0; j < courses.size(); j++) {
//
//            }
//        }
//
//        return timeTable;
//    }

    // EFFECTS: return the timetable that have the shortest distance between courses, this means this is the timetable
    // that you have to walk the least between each courses
    public ArrayList<ArrayList<Course>> getTimeTableShortestDistanceBetweenCourses(ArrayList<ArrayList<Course>>
                                                                                           allCoursesComb) {
        return null;
        // stub
    }



    @SuppressWarnings("methodlength")
    public static void main(String[] args) {
        ArrayList<Course> tempCourses = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        tempCourses.add(new Course("CPSC 210 102",1300,1400,135));
        tempCourses.add(new Course("CPSC 310 102",1500,1600,24));
        tempCourses.add(new Course("CPSC 310 102",1400,1500,24));
        tempCourses.add(new Course("CPSC 410 102",1500,1600,24));
//        tempCourses.add(new Course("A 100 102",300,400,135));
//        tempCourses.add(new Course("A 200 102",500,600,24));
//        tempCourses.add(new Course("A 300 102",700,800,24));
//        tempCourses.add(new Course("A 400 102",900,1000,24));
//        tempCourses.add(new Course("B 100 102",300,400,3));
//        tempCourses.add(new Course("B 200 102",500,600,2));
//        tempCourses.add(new Course("B 300 102",700,800,1));
//        tempCourses.add(new Course("B 400 102",900,1000,45));
//        tempCourses.add(new Course("C 100 102",1700,1800,3));
//        tempCourses.add(new Course("C 200 102",1800,1900,24));
//        tempCourses.add(new Course("C 300 102",700,800,1));
//        tempCourses.add(new Course("C 400 102",1100,1200,45));
//        tempCourses.add(new Course("D 100 102",1600,1900,3));
//        tempCourses.add(new Course("D 200 102",1400,1900,24));
//        tempCourses.add(new Course("D 300 102",1300,1500,1));
//        tempCourses.add(new Course("D 400 102",100,200,145));
//        tempCourses.add(new Course("E 100 102",400,500,3));
//        tempCourses.add(new Course("E 200 102",530,630,24));
//        tempCourses.add(new Course("E 300 102",730,830,345));
//        tempCourses.add(new Course("E 400 102",800,900,24));

        TimeTableAlgorithms a = new TimeTableAlgorithms();
        ArrayList<ArrayList<Course>> timetable;
        timetable = a.getValidTimeTable(tempCourses,10);

        int cnt = 0;
        for (ArrayList<Course> courses : timetable) {
            for (Course course : courses) {
                System.out.println(course.toString());
            }
            cnt++;
            System.out.println("-------------------------");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("The execution time is: " + (endTime - startTime) + "ms");
        System.out.println("There are " + cnt + "combinations");
    }

}
