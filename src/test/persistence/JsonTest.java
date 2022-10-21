package persistence;

import model.Course;
import model.Expense;
import model.Operation;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {
    protected void checkExpenseIdList(ArrayList<Integer> expected, ArrayList<Integer> actual) {
        assertEquals(expected.size(),actual.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i),actual.get(i));
        }
    }

    protected void checkCategoryCost(Map<String,Integer> expected, Map<String,Integer> actual) {

        for(Map.Entry<String,Integer> entry : expected.entrySet()) {
            assertTrue(actual.containsKey(entry.getKey()));
            assertEquals(entry.getValue(),actual.get(entry.getKey()));
        }

    }

    protected void checkIdQueryExpense(Map<Integer,Expense> expected, Map<Integer,Expense> actual) {

        for(Map.Entry<Integer,Expense> entry : expected.entrySet()) {
            assertTrue(actual.containsKey(entry.getKey()));
            assertEquals(entry.getValue().toString(),actual.get(entry.getKey()).toString());
        }
    }

    protected void checkLastOperation(Operation expected, Operation actual) {
        assertEquals(expected.toString(),actual.toString());
    }

    protected void checkCourses(ArrayList<Course> expected, ArrayList<Course> actual) {
        assertEquals(expected.size(),actual.size());

        for(int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).toString(),actual.get(i).toString());
        }
    }


}
