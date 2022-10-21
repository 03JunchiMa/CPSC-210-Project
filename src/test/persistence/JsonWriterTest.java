package persistence;

import java.io.IOException;
import java.util.ArrayList;

import model.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {

        try {
            TreeApp treeApp = new TreeApp();
            JsonWriter writer = new JsonWriter("./data/\0illigal:filename.json");
            writer.open();
            fail("Expected IOException to be thrown");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTreeApp() {
        JsonWriter writer = new JsonWriter("./data/testWriterEmptyTreeApp.json");

        try {
            TreeApp treeAppEmpty = new TreeApp();
            writer.open();
            writer.write(treeAppEmpty);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTreeApp.json");
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

            Operation expectedOperation = new Operation("undoTheLastOperation",new Expense(0,"null"));
            expectedOperation.getExpense().setId(0);
            checkLastOperation(expectedOperation,treeApp.getExpenseRecording().getLastOperation());

            assertEquals(1,treeApp.getExpenseRecording().getIdCounter());

            ArrayList<Course> expectedTimeTable = new ArrayList<>();
            checkCourses(expectedTimeTable,treeApp.getTimeTable().getTimetable());

        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    void testWriterGeneralTreeApp() {
        JsonWriter writer = new JsonWriter("./data/testWriterGeneralTreeApp.json");

        try {
            TreeApp treeApp = new TreeApp();
            ExpenseRecording expenseRecording = new ExpenseRecording(0);
            expenseRecording.getExpenseIdList().add(1);
            expenseRecording.getExpenseIdList().add(2);
            expenseRecording.getCategoryCost().put("education",-112931);
            expenseRecording.getCategoryCost().put("food",-120);
            expenseRecording.getCategoryCost().put("income",100);
            Expense expense = new Expense(-100,"food");
            expense.setId(0);
            expenseRecording.getIdQueryExpense().put(0,expense);
            Operation operation = new Operation("addExpenseInfo",new Expense(-200,"food"));
            operation.getExpense().setId(0);
            expenseRecording.setLastOperation(operation);
            treeApp.setExpenseRecording(expenseRecording);
            Course course1 = new Course("CPSC 100 100", 1100,1200,123);
            Course course2 = new Course("CPSC 210 102",1300,1400,135);
            treeApp.getTimeTable().addIntendedCourse(course1);
            treeApp.getTimeTable().addIntendedCourse(course2);

            writer.open();
            writer.write(treeApp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTreeApp.json");
            treeApp = reader.read();

            assertEquals(0,treeApp.getExpenseRecording().getBudget());
            assertEquals(0,treeApp.getExpenseRecording().getTotalIncome());
            assertEquals(0,treeApp.getExpenseRecording().getTotalCost());

            ArrayList<Integer> expectedExpenseIdList = new ArrayList<>();
            expectedExpenseIdList.add(1);
            expectedExpenseIdList.add(2);
            checkExpenseIdList(expectedExpenseIdList,treeApp.getExpenseRecording().getExpenseIdList());

            Map<String,Integer> expectedCategoryCost = new TreeMap<>();
            expectedCategoryCost.put("education",-112931);
            expectedCategoryCost.put("food",-120);
            expectedCategoryCost.put("income",100);
            checkCategoryCost(expectedCategoryCost,treeApp.getExpenseRecording().getCategoryCost());

            Map<Integer,Expense> expectedIdQueryExpense = new HashMap<>();
            Expense expense1 = new Expense(-100,"food");
            expense.setId(0);
            expectedIdQueryExpense.put(0,expense1);
            checkIdQueryExpense(expectedIdQueryExpense,treeApp.getExpenseRecording().getIdQueryExpense());

            Operation expectedOperation = new Operation("addExpenseInfo",new Expense(-200,"food"));
            expectedOperation.getExpense().setId(0);
            checkLastOperation(expectedOperation,treeApp.getExpenseRecording().getLastOperation());

            assertEquals(1,treeApp.getExpenseRecording().getIdCounter());

            ArrayList<Course> expectedTimeTable = new ArrayList<>();
            Course course11 = new Course("CPSC 100 100", 1100,1200,123);
            Course course22 = new Course("CPSC 210 102",1300,1400,135);
            expectedTimeTable.add(course11);
            expectedTimeTable.add(course22);
            checkCourses(expectedTimeTable,treeApp.getTimeTable().getTimetable());

        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

}
