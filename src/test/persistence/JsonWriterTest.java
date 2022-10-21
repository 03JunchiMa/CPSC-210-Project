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

}
