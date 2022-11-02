package util;

import model.Course;

import java.util.*;

// providing algorithms for timetable
public class TimeTableAlgorithms {
    private static final int INTERVALLENGTH = 2410;

    // REQUIRES: courses has no duplicates elements
    // EFFECTS: return all the valid combinations of the timetable with given number of courses, it will be returned
    // as sorted based on the start time of the courses, if two courses start with the same time, then the
    // course with less Lexicographical order will be placed first, return empty timetable if  number of courses is 0.
    public static ArrayList<ArrayList<Course>> getValidTimeTable(ArrayList<Course> duplicateCourses,
                                                                 int numberOfCourses) {
        ArrayList<ArrayList<Course>> validTimeTable;
        HashMap<String,Boolean> checkNoDuplicateCourse;
        HashMap<Integer,int[]> timeInterval;
        ArrayList<Course> currentCourses;

        validTimeTable = new ArrayList<>();
        checkNoDuplicateCourse = new HashMap<>();
        currentCourses = new ArrayList<>();
        timeInterval = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            timeInterval.put(i, new int[INTERVALLENGTH]);
        }
        ArrayList courses = unique(duplicateCourses);
        courses = sortCourseByTimeDuration(courses); // optimize search order, starting from the longest time duration
        searchTimeTables(validTimeTable,timeInterval,checkNoDuplicateCourse,
                currentCourses, courses,0,numberOfCourses,0);
        return validTimeTable;
    }

    // EFFECTS: return the courses with no duplicates
    public static ArrayList<Course> unique(ArrayList<Course> duplicateCourses) {
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
    // EFFECTS: search all the valid timetables combinations, if the number of courses is 0, return the empty timetable.
    @SuppressWarnings("methodlength")
    public static void searchTimeTables(ArrayList<ArrayList<Course>> validTimeTable,
                                        HashMap<Integer,int[]> timeInterval,
                                        HashMap<String,Boolean> checkNoDuplicateCourse,
                                        ArrayList<Course> currentCourses, ArrayList<Course> courseForSelection,
                                        int currentPosition, int numberOfCourses, int lastPosition) {
        if (numberOfCourses == 0) {
            return;
        }

        if (currentCourses.size() + (courseForSelection.size() - lastPosition) < numberOfCourses) {
            return;
        }

        if (currentCourses.size() == numberOfCourses) {
            addToTimeTable(currentCourses,validTimeTable);
            return;
        }

        for (int i = lastPosition; i < courseForSelection.size(); i++) {
            Course course = courseForSelection.get(i);
            String courseName = course.getCourseNameSection();
            if (! checkNoDuplicateCourse.containsKey(courseName)) {
                if (checkTimeTable(timeInterval, course)) {
                    addCourseToTimeTable(timeInterval, course,1);
                    checkNoDuplicateCourse.put(courseName,true);
                    currentCourses.add(course);

                    searchTimeTables(validTimeTable,timeInterval, checkNoDuplicateCourse,
                            currentCourses, courseForSelection, currentPosition + 1, numberOfCourses, i + 1);
                    addCourseToTimeTable(timeInterval, course,-1);
                    checkNoDuplicateCourse.remove(courseName);
                    currentCourses.remove(currentCourses.size() - 1);
                }
            }
        }

    }

    // EFFECTS: add a course to the timetable
    private static void addToTimeTable(ArrayList<Course> currentCourses, ArrayList<ArrayList<Course>> validTimeTable) {
        ArrayList<Course> tempCourses = new ArrayList<>();
        for (int i = 0; i < currentCourses.size(); i++) {
            tempCourses.add(currentCourses.get(i));
        }
        tempCourses = sortCourseByStartTime(tempCourses);
        validTimeTable.add(tempCourses);
    }

    // // EFFECTS: add a course to the timetable, so that the time slot is occupied
    public static void addCourseToTimeTable(HashMap<Integer,int[]> timeInterval, Course course, int c) {
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
    public static boolean checkTimeTable(HashMap<Integer,int[]> timeInterval, Course course) {

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
    public static ArrayList<Course> sortCourseByLexicographical(ArrayList<Course> tempCourses) {
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
    public static ArrayList<Course> sortCourseByTimeDuration(ArrayList<Course> tempCourses) {
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
    public static ArrayList<Course> sortCourseByStartTime(ArrayList<Course> tempCourses) {
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

    // EFFECTS: return all the valid combinations of the timetable that don't have any classes between the given
    // time slot (this means in this time slot you are free)
    public static ArrayList<ArrayList<Course>> getTimeTableAvoidTimeSlot(ArrayList<ArrayList<Course>> allCoursesComb,
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
    public static ArrayList<ArrayList<Course>> getTimeTableForLunchTime(ArrayList<ArrayList<Course>> allCoursesComb) {
        return getTimeTableAvoidTimeSlot(allCoursesComb,1100,1300);
    }

//    @SuppressWarnings("methodlength")
//    public static void main(String[] args) {
//        ArrayList<Course> tempCourses = new ArrayList<>();
//
//        long startTime = System.currentTimeMillis();
//        tempCourses.add(new Course("CPSC 210 102",1300,1400,135));
//        tempCourses.add(new Course("CPSC 310 102",1500,1600,24));
//        tempCourses.add(new Course("CPSC 310 102",1400,1500,24));
//        tempCourses.add(new Course("CPSC 410 102",1500,1600,24));
////        tempCourses.add(new Course("A 100 102",300,400,135));
////        tempCourses.add(new Course("A 200 102",500,600,24));
////        tempCourses.add(new Course("A 300 102",700,800,24));
////        tempCourses.add(new Course("A 400 102",900,1000,24));
////        tempCourses.add(new Course("B 100 102",300,400,3));
////        tempCourses.add(new Course("B 200 102",500,600,2));
////        tempCourses.add(new Course("B 300 102",700,800,1));
////        tempCourses.add(new Course("B 400 102",900,1000,45));
////        tempCourses.add(new Course("C 100 102",1700,1800,3));
////        tempCourses.add(new Course("C 200 102",1800,1900,24));
////        tempCourses.add(new Course("C 300 102",700,800,1));
////        tempCourses.add(new Course("C 400 102",1100,1200,45));
////        tempCourses.add(new Course("D 100 102",1600,1900,3));
////        tempCourses.add(new Course("D 200 102",1400,1900,24));
////        tempCourses.add(new Course("D 300 102",1300,1500,1));
////        tempCourses.add(new Course("D 400 102",100,200,145));
////        tempCourses.add(new Course("E 100 102",400,500,3));
////        tempCourses.add(new Course("E 200 102",530,630,24));
////        tempCourses.add(new Course("E 300 102",730,830,345));
////        tempCourses.add(new Course("E 400 102",800,900,24));
//
//        ArrayList<ArrayList<Course>> timetable;
//        timetable = getValidTimeTable(tempCourses,2);
//
//        int cnt = 0;
//        for (ArrayList<Course> courses : timetable) {
//            for (Course course : courses) {
//                System.out.println(course.toString());
//            }
//            cnt++;
//            System.out.println("-------------------------");
//        }
//        long endTime = System.currentTimeMillis();
//        System.out.println("The execution time is: " + (endTime - startTime) + "ms");
//        System.out.println("There are " + cnt + "combinations");
//    }
}
