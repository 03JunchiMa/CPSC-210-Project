package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestTimeTable {
    TimeTable table;

    @BeforeEach
    void init(){
        table = new TimeTable();
    }

    @Test
    void testAddIntendedCourse() {
        Course course1 = new Course("CPSC 210 102",930,1130,24);
        Course course2 = new Course("CPSC 110 101",930,1130,135);
        Course course3 = new Course("CPSC 310 103",1030,1200,24);
        Course course4 = new Course("CPSC 310 104",1300,1500,45);
        ArrayList<Course> temp = new ArrayList<>();

        assertEquals(24,course1.getWeekday());
        assertEquals(135,course2.getWeekday());
        assertEquals(24,course3.getWeekday());

        assertTrue(table.addIntendedCourse(course1));
        temp.add(course1);
        assertTrue(table.compareToCurrentTimeTable(temp));
        assertEquals(1,table.getTotalCourse());
        assertTrue(table.containsCourse(course1));

        assertTrue(table.addIntendedCourse(course2));
        assertTrue(table.addIntendedCourse(course3));
        assertTrue(table.addIntendedCourse(course4));
        temp.add(course2);
        temp.add(course3);
        temp.add(course4);
        assertTrue(table.compareToCurrentTimeTable(temp));
        assertTrue(table.containsCourse(course2));
        assertTrue(table.containsCourse(course3));
        assertTrue(table.containsCourse(course4));
        assertEquals(4,table.getTotalCourse());

        assertFalse(table.addIntendedCourse(course1));
        assertEquals(4,table.getTotalCourse());
    }

    @Test
    void testDeleteIntendedCourse() {
        Course course1 = new Course("CPSC 210 102",930,1130,24);
        Course course2 = new Course("CPSC 110 101",930,1130,135);
        Course course3 = new Course("CPSC 410 103",1500,1600,24);
        Course course4 = new Course("CPSC 210 104",1300,1500,45);
        Course course5 = new Course("CPSC 310 104",1300,1500,45);
        Course course6 = new Course("CPSC 310 103",1300,1500,45);
        ArrayList<Course> temp = new ArrayList<>();


        assertTrue(table.addIntendedCourse(course1));
        assertFalse(table.compareToCurrentTimeTable(temp));
        temp.add(course1);
        assertEquals(1,table.getTotalCourse());
        assertTrue(table.containsCourse(course1));

        assertTrue(table.addIntendedCourse(course2));
        assertTrue(table.addIntendedCourse(course3));
        assertTrue(table.addIntendedCourse(course4));
        temp.add(course2);
        temp.add(course3);
        temp.add(course4);
        assertEquals(4,table.getTotalCourse());
        assertTrue(table.compareToCurrentTimeTable(temp));

        assertTrue(table.deleteIntendedCourse(course1));
        temp.remove(course1);
        assertTrue(table.compareToCurrentTimeTable(temp));
        assertEquals(3,table.getTotalCourse());

        assertTrue(table.deleteIntendedCourse(course2));
        temp.remove(course2);
        assertTrue(table.compareToCurrentTimeTable(temp));
        assertEquals(2,table.getTotalCourse());

        assertFalse(table.deleteIntendedCourse(course2));
        assertEquals(2,table.getTotalCourse());

        assertTrue(table.deleteIntendedCourse(course3));
        temp.remove(course3);
        assertTrue(table.compareToCurrentTimeTable(temp));
        assertTrue(table.deleteIntendedCourse(course4));
        temp.remove(course4);
        assertTrue(table.compareToCurrentTimeTable(temp));

        assertEquals(0,table.getTotalCourse());

        assertFalse(table.deleteIntendedCourse(course4));
        // delete the course that did not been added
        assertFalse(table.deleteIntendedCourse(course5));

        // test the compare
        table.addIntendedCourse(course5);
        temp.add(course6);
        assertFalse(table.compareToCurrentTimeTable(temp));
    }

}
