package persistence;

import java.io.IOException;
import java.util.ArrayList;

import model.Course;
import model.Expense;
import model.Operation;
import model.TreeApp;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.fail;

// Test JsonReader's functionality
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TreeApp treeApp = reader.read();
            fail("Expected IOException to be thrown");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTreeApp() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTreeApp.json");
        try {
            TreeApp treeApp = reader.read();
            assertEquals(0,treeApp.getExpenseRecording().getBudget());
            assertEquals(0,treeApp.getExpenseRecording().getTotalIncome());
            assertEquals(0,treeApp.getExpenseRecording().getTotalCost());

            ArrayList<Integer> expectedExpenseIdList = new ArrayList<>();
            checkExpenseIdList(expectedExpenseIdList,treeApp.getExpenseRecording().getExpenseIdList());

            Map<String,Integer> expectedCategoryCost = new TreeMap<>();
            checkCategoryCost(expectedCategoryCost,treeApp.getExpenseRecording().getCategoryCost());

            Map<Integer,Expense> expectedIdQueryExpense = new HashMap<>();
            checkIdQueryExpense(expectedIdQueryExpense,treeApp.getExpenseRecording().getIdQueryExpense());

            Operation expectedOperation = new Operation("",new Expense(0,""));
            expectedOperation.getExpense().setId(0);
            checkLastOperation(expectedOperation,treeApp.getExpenseRecording().getLastOperation());

            assertEquals(1,treeApp.getExpenseRecording().getIdCounter());

            ArrayList<Course> expectedTimeTable = new ArrayList<>();
            checkCourses(expectedTimeTable,treeApp.getTimeTable().getTimetable());

        } catch (IOException e) {
            fail("Couldn't find the existent file");
        }
    }

    @Test
    void testReaderGeneralTreeApp() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTreeApp.json");

        try {
            TreeApp treeApp = reader.read();
            assertEquals(100,treeApp.getExpenseRecording().getBudget());
            assertEquals(1300,treeApp.getExpenseRecording().getTotalIncome());
            assertEquals(300,treeApp.getExpenseRecording().getTotalCost());

            ArrayList<Integer> expectedExpenseIdList = new ArrayList<>();
            expectedExpenseIdList.add(0);
            expectedExpenseIdList.add(0);
            expectedExpenseIdList.add(0);
            checkExpenseIdList(expectedExpenseIdList,treeApp.getExpenseRecording().getExpenseIdList());

            Map<String,Integer> expectedCategoryCost = new TreeMap<>();
            expectedCategoryCost.put("education1",-1000);
            expectedCategoryCost.put("food",-120);
            expectedCategoryCost.put("income",100);
            checkCategoryCost(expectedCategoryCost,treeApp.getExpenseRecording().getCategoryCost());

            Map<Integer,Expense> expectedIdQueryExpense = new HashMap<>();
            Expense expense = new Expense(-200,"food");
            expense.setId(0);
            expectedIdQueryExpense.put(0,expense);
            checkIdQueryExpense(expectedIdQueryExpense,treeApp.getExpenseRecording().getIdQueryExpense());

            Operation expectedOperation = new Operation("addExpenseInfo",new Expense(-200,"food"));
            expectedOperation.getExpense().setId(0);
            checkLastOperation(expectedOperation,treeApp.getExpenseRecording().getLastOperation());

            assertEquals(1,treeApp.getExpenseRecording().getIdCounter());

            ArrayList<Course> expectedTimeTable = new ArrayList<>();
            Course course1 = new Course("CPSC 100 100", 1100,1200,123);
            Course course2 = new Course("CPSC 210 102",1300,1400,135);
            expectedTimeTable.add(course1);
            expectedTimeTable.add(course2);
            checkCourses(expectedTimeTable,treeApp.getTimeTable().getTimetable());
        } catch (IOException e) {
            fail("Couldn't find the existent file");
        }
    }

}
