package util;

import model.Course;
import model.TimeTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.TimeTableAlgorithms.*;

public class TimeTableAlgorithmsTest {
    TimeTable timeTable;
    TimeTableAlgorithms timeTableAlgorithms;

    @BeforeEach
    void runBefore() {
        timeTable = new TimeTable();
        timeTableAlgorithms = new TimeTableAlgorithms();

    }

    @Test
    void testTimeTableNoCourses() {
        timeTable.addIntendedCourse(new Course("CPSC 210 102",1300,1400,135));
        timeTable.addIntendedCourse(new Course("CPSC 310 102",1500,1600,24));
        timeTable.addIntendedCourse(new Course("CPSC 310 102",1400,1500,24));
        timeTable.addIntendedCourse(new Course("CPSC 410 102",1500,1600,24));
        timeTable.addIntendedCourse(new Course("CPSC 410 102",1500,1600,24));
        assertEquals(4, timeTable.getTimetable().size());
        ArrayList<ArrayList<Course>> validTimeTable = getValidTimeTable(timeTable.getTimetable(),0);
        assertEquals(0,validTimeTable.size());
    }

    @Test
    void testGetValidTimeTableSmallNumberOfCourses() {
        Course course1 = new Course("CPSC 210 102",1300,1400,135);
        Course course2 = new Course("CPSC 310 102",1500,1600,24);
        Course course3 = new Course("CPSC 310 102",1400,1500,24);
        Course course4 = new Course("CPSC 410 102",1500,1600,24);
        Course course5 = new Course("CPSC 210 102",1300,1400,135);


        timeTable.addIntendedCourse(course1);
        timeTable.addIntendedCourse(course2);
        timeTable.addIntendedCourse(course3);
        timeTable.addIntendedCourse(course4);
        timeTable.addIntendedCourse(course5);

        ArrayList<ArrayList<Course>> validTimeTable = getValidTimeTable(timeTable.getTimetable(),2);

        ArrayList<Course> courses1 = validTimeTable.get(0);
        assertEquals(course1,courses1.get(0));
        assertEquals(course3,courses1.get(1));

        ArrayList<Course> courses2 = validTimeTable.get(1);
        assertEquals(course1,courses2.get(0));
        assertEquals(course2,courses2.get(1));

        ArrayList<Course> courses3 = validTimeTable.get(2);
        assertEquals(course1,courses3.get(0));
        assertEquals(course4,courses3.get(1));

        ArrayList<Course> courses4 = validTimeTable.get(3);
        assertEquals(course3,courses4.get(0));
        assertEquals(course4,courses3.get(1));


        assertEquals(4,validTimeTable.size());
    }

    @Test
    void testGetTimeTableForLunchTime(){
        timeTable.addIntendedCourse(new Course("A 100 100", 1200,1300,13));
        timeTable.addIntendedCourse(new Course("B 100 100", 1300,1400,24));
        timeTable.addIntendedCourse(new Course("C 100 100", 1200,1400,135));
        timeTable.addIntendedCourse(new Course("D 100 100", 1000,1130,135));
        timeTable.addIntendedCourse(new Course("E 100 100", 900,1230,135));
        timeTable.addIntendedCourse(new Course("F 100 100", 900,1000,135));
        timeTable.addIntendedCourse(new Course("G 100 100", 900,1400,135));
        ArrayList<ArrayList<Course>> validTimeTable = getTimeTableForLunchTime(getValidTimeTable(timeTable.getTimetable(),1));
        assertEquals(2,validTimeTable.size());
    }


}
