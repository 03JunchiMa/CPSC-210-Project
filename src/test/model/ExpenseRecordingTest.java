package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

// Test ExpenseRecording class' functionality
public class ExpenseRecordingTest {
    ExpenseRecording expenseRecording;

    @BeforeEach
    void init() {
        expenseRecording = new ExpenseRecording(0);
    }

    @Test
    void testAddExpenseInfo() {

        Expense expense1 = new Expense(-100,"education");
        expenseRecording.addExpenseInfo(expense1);
        assertEquals(1, expense1.getId());
        assertEquals("-100 education\n", expenseRecording.viewInfoById(1));
        assertEquals(expense1,expenseRecording.getInfoById(1));
        assertEquals(0, expenseRecording.getTotalIncome());
        assertEquals(100, expenseRecording.getTotalCost());
        assertEquals(-100, expenseRecording.getBudget());
        assertEquals("education", expenseRecording.viewHighestCostCategory());
        assertEquals("education 100.0%\n", expenseRecording.viewAllCostCategoryInPercentage());
        assertEquals(new Operation("addExpenseInfo",expense1).toString(),expenseRecording.getLastOperation().toString());

        // add cost multiple times
        Expense expense2 = new Expense(-200,"food");
        expenseRecording.addExpenseInfo(expense2);
        assertEquals("-100 education\n", expenseRecording.viewInfoById(1));
        assertEquals(2, expense2.getId());
        assertEquals("-200 food\n", expenseRecording.viewInfoById(2));
        assertEquals(expense2,expenseRecording.getInfoById(2));
        assertEquals(0, expenseRecording.getTotalIncome());
        assertEquals(300, expenseRecording.getTotalCost());
        assertEquals(-300, expenseRecording.getBudget());
        assertEquals("food", expenseRecording.viewHighestCostCategory());
        assertEquals("education 33.3%\nfood 66.7%\n", expenseRecording.viewAllCostCategoryInPercentage());
        assertEquals(new Operation("addExpenseInfo",expense2).toString(),expenseRecording.getLastOperation().toString());

        assertEquals("education -100\nfood -200\n",expenseRecording.toString());

        // test add the income
        Expense expense3 = new Expense(50,"income");
        expenseRecording.addExpenseInfo(expense3);
        assertEquals(3,expense3.getId());
        assertEquals("50 income\n", expenseRecording.viewInfoById(3));
        assertEquals(expense3,expenseRecording.getInfoById(3));
        assertEquals(50,expenseRecording.getTotalIncome());
        assertEquals(300,expenseRecording.getTotalCost());
        assertEquals(-300,expenseRecording.getBudget());
        assertEquals("food", expenseRecording.viewHighestCostCategory());
        assertEquals("education 33.3%\nfood 66.7%\n", expenseRecording.viewAllCostCategoryInPercentage());
        assertEquals(new Operation("addExpenseInfo",expense3).toString(),expenseRecording.getLastOperation().toString());

        // extreme case, test add the cost
        Expense expense4 = new Expense(-1,"education");
        expenseRecording.addExpenseInfo(expense4);
        assertEquals(4, expense4.getId());
        assertEquals("-1 education\n", expenseRecording.viewInfoById(4));
        assertEquals(expense4,expenseRecording.getInfoById(4));
        assertEquals(50, expenseRecording.getTotalIncome());
        assertEquals(301, expenseRecording.getTotalCost());
        assertEquals(-301, expenseRecording.getBudget());
        assertEquals("education 33.6%\nfood 66.4%\n", expenseRecording.viewAllCostCategoryInPercentage());
        assertEquals(new Operation("addExpenseInfo",expense4).toString(),expenseRecording.getLastOperation().toString());

        assertEquals("education -100\nfood -200\nincome 50\neducation -1\n",expenseRecording.toString());
    }


    @Test
    void testDeleteExpenseInfoByMultipleTimes() {
        Expense expense1 = new Expense(-1,"education");
        Expense expense2 = new Expense(-2,"rental");
        Expense expense3 = new Expense(-3,"food");
        Expense expense4 = new Expense(50,"income");

        expenseRecording.addExpenseInfo(expense1);
        expenseRecording.addExpenseInfo(expense2);
        expenseRecording.addExpenseInfo(expense3);
        expenseRecording.addExpenseInfo(expense4);

        assertEquals(expense1,expenseRecording.getInfoById(1));
        assertEquals(expense2,expenseRecording.getInfoById(2));
        assertEquals(expense3,expenseRecording.getInfoById(3));
        assertEquals(expense4,expenseRecording.getInfoById(4));
        assertEquals(6,expenseRecording.getTotalCost());
        assertEquals(50, expenseRecording.getTotalIncome());
        assertEquals(-6,expenseRecording.getBudget());
        assertEquals("food",expenseRecording.viewHighestCostCategory());
        assertEquals("education 16.7%\nfood 50.0%\nrental 33.3%\n",expenseRecording.viewAllCostCategoryInPercentage());

        // delete one expense
        assertTrue(expenseRecording.deleteExpenseInfo(1));
        assertEquals(null,expenseRecording.getInfoById(1));
        assertEquals(expense2,expenseRecording.getInfoById(2));
        assertEquals(expense3,expenseRecording.getInfoById(3));
        assertEquals(expense4,expenseRecording.getInfoById(4));
        assertEquals(5,expenseRecording.getTotalCost());
        assertEquals(-5,expenseRecording.getBudget());
        assertEquals("food",expenseRecording.viewHighestCostCategory());
        assertEquals("food 60.0%\nrental 40.0%\n",expenseRecording.viewAllCostCategoryInPercentage());

        // delete multiple times (delete the item associated corresponding category)
        assertTrue(expenseRecording.deleteExpenseInfo(2));
        assertEquals(null,expenseRecording.getInfoById(2));
        assertEquals(3,expenseRecording.getTotalCost());
        assertEquals(-3,expenseRecording.getBudget());
        assertEquals("food",expenseRecording.viewHighestCostCategory());
        assertEquals("food 100.0%\n",expenseRecording.viewAllCostCategoryInPercentage());

        assertTrue(expenseRecording.deleteExpenseInfo(3));
        assertEquals(null,expenseRecording.getInfoById(3));
        assertEquals(0,expenseRecording.getTotalCost());
        assertEquals(0,expenseRecording.getBudget());
        assertEquals("",expenseRecording.viewHighestCostCategory());
        assertEquals("",expenseRecording.viewAllCostCategoryInPercentage());

        // unsuccessful delete
        assertFalse(expenseRecording.deleteExpenseInfo(1));
        assertEquals(0,expenseRecording.getTotalCost());
        assertEquals(0,expenseRecording.getBudget());
        assertEquals("",expenseRecording.viewHighestCostCategory());
        assertEquals("",expenseRecording.viewAllCostCategoryInPercentage());

        // delete income
        assertTrue(expenseRecording.deleteExpenseInfo(4));
        assertEquals(null,expenseRecording.getInfoById(4));
        assertEquals(0,expenseRecording.getTotalCost());
        assertEquals(0,expenseRecording.getBudget());
        assertEquals(0,expenseRecording.getTotalIncome());
        assertEquals("",expenseRecording.viewHighestCostCategory());
        assertEquals("",expenseRecording.viewAllCostCategoryInPercentage());
    }

    @Test // test the case that the corresponding category will not be deleted
    void testDeleteExpenseInfoDoesNotDeleteCCategory() {
        Expense expense1 = new Expense(-1,"education");
        Expense expense2 = new Expense(-2,"education");

        expenseRecording.addExpenseInfo(expense1);
        expenseRecording.addExpenseInfo(expense2);

        assertEquals(3,expenseRecording.getTotalCost());
        assertEquals(-3,expenseRecording.getBudget());
        assertEquals("education",expenseRecording.viewHighestCostCategory());

        expenseRecording.deleteExpenseInfo(1);

        assertEquals(2,expenseRecording.getTotalCost());
        assertEquals(-2,expenseRecording.getBudget());
        assertEquals("education",expenseRecording.viewHighestCostCategory());
        assertEquals(null,expenseRecording.getInfoById(1));
    }

    @Test // first undo add then undo delete
    void testUndoTheLastOperationAddDelete() {
        Expense expense1 = new Expense(-1,"education");
        Expense expense2 = new Expense(-2,"rental");
        Expense expense3 = new Expense(-3,"food");


        expenseRecording.addExpenseInfo(expense1);
        expenseRecording.addExpenseInfo(expense2);
        expenseRecording.addExpenseInfo(expense3);

        assertEquals(expense1,expenseRecording.getInfoById(1));
        assertEquals(expense2,expenseRecording.getInfoById(2));
        assertEquals(expense3,expenseRecording.getInfoById(3));
        assertEquals(6,expenseRecording.getTotalCost());
        assertEquals(-6,expenseRecording.getBudget());
        assertEquals("food",expenseRecording.viewHighestCostCategory());
        assertEquals("education 16.7%\nfood 50.0%\nrental 33.3%\n",expenseRecording.viewAllCostCategoryInPercentage());

        // undo add
        assertTrue(expenseRecording.undoTheLastOperation());

        assertEquals(expense1,expenseRecording.getInfoById(1));
        assertEquals(expense2,expenseRecording.getInfoById(2));
        assertEquals(null,expenseRecording.getInfoById(3));
        assertEquals(3,expenseRecording.getTotalCost());
        assertEquals(-3,expenseRecording.getBudget());
        assertEquals("rental",expenseRecording.viewHighestCostCategory());
        assertEquals("education 33.3%\nrental 66.7%\n",expenseRecording.viewAllCostCategoryInPercentage());

        assertFalse(expenseRecording.undoTheLastOperation());

        // undo delete
        expenseRecording.deleteExpenseInfo(2);

        assertEquals(null,expenseRecording.getInfoById(2));
        assertEquals(1,expenseRecording.getTotalCost());
        assertEquals(-1,expenseRecording.getBudget());
        assertEquals("education",expenseRecording.viewHighestCostCategory());
        assertEquals("education 100.0%\n",expenseRecording.viewAllCostCategoryInPercentage());

        assertTrue(expenseRecording.undoTheLastOperation());

        assertEquals(expense1,expenseRecording.getInfoById(1));
        assertEquals(expense2,expenseRecording.getInfoById(2));
        assertEquals(null,expenseRecording.getInfoById(3));
        assertEquals(3,expenseRecording.getTotalCost());
        assertEquals(-3,expenseRecording.getBudget());
        assertEquals("rental",expenseRecording.viewHighestCostCategory());
        assertEquals("education 33.3%\nrental 66.7%\n",expenseRecording.viewAllCostCategoryInPercentage());

        assertFalse(expenseRecording.undoTheLastOperation());
    }

    @Test // first undo the delete then undo add
    void testUndoTheLastOperationDeleteAdd() {
        Expense expense1 = new Expense(-1,"education");
        Expense expense2 = new Expense(-2,"rental");
        Expense expense3 = new Expense(-3,"food");


        expenseRecording.addExpenseInfo(expense1);
        expenseRecording.addExpenseInfo(expense2);
        expenseRecording.addExpenseInfo(expense3);

        assertEquals(expense1,expenseRecording.getInfoById(1));
        assertEquals(expense2,expenseRecording.getInfoById(2));
        assertEquals(expense3,expenseRecording.getInfoById(3));
        assertEquals(6,expenseRecording.getTotalCost());
        assertEquals(-6,expenseRecording.getBudget());
        assertEquals("food",expenseRecording.viewHighestCostCategory());
        assertEquals("education 16.7%\nfood 50.0%\nrental 33.3%\n",expenseRecording.viewAllCostCategoryInPercentage());

        // undo delete
        expenseRecording.deleteExpenseInfo(2);

        assertEquals(null,expenseRecording.getInfoById(2));
        assertEquals(4,expenseRecording.getTotalCost());
        assertEquals(-4,expenseRecording.getBudget());
        assertEquals("food",expenseRecording.viewHighestCostCategory());
        assertEquals("education 25.0%\nfood 75.0%\n",expenseRecording.viewAllCostCategoryInPercentage());

        assertTrue(expenseRecording.undoTheLastOperation());

        assertEquals(expense1,expenseRecording.getInfoById(1));
        assertEquals(expense2,expenseRecording.getInfoById(2));
        assertEquals(expense3,expenseRecording.getInfoById(3));
        assertEquals(6,expenseRecording.getTotalCost());
        assertEquals(-6,expenseRecording.getBudget());
        assertEquals("food",expenseRecording.viewHighestCostCategory());
        assertEquals("education 16.7%\nfood 50.0%\nrental 33.3%\n",expenseRecording.viewAllCostCategoryInPercentage());

        assertFalse(expenseRecording.undoTheLastOperation());
    }

    @Test
    void testAddExpenseInfoAfterUndo() {
        Expense expense1 = new Expense(-3,"education");
        Expense expense2 = new Expense(-6,"rental");
        Expense expense3 = new Expense(-9,"food");


        expenseRecording.addExpenseInfo(expense1);
        expenseRecording.addExpenseInfo(expense2);
        expenseRecording.addExpenseInfo(expense3);

        assertEquals(expense1,expenseRecording.getInfoById(1));
        assertEquals(expense2,expenseRecording.getInfoById(2));
        assertEquals(expense3,expenseRecording.getInfoById(3));
        assertEquals(18,expenseRecording.getTotalCost());
        assertEquals(-18,expenseRecording.getBudget());
        assertEquals("food",expenseRecording.viewHighestCostCategory());
        assertEquals("education 16.7%\nfood 50.0%\nrental 33.3%\n",expenseRecording.viewAllCostCategoryInPercentage());

        // undo add
        assertTrue(expenseRecording.undoTheLastOperation());

        assertEquals(expense1,expenseRecording.getInfoById(1));
        assertEquals(expense2,expenseRecording.getInfoById(2));
        assertEquals(null,expenseRecording.getInfoById(3));
        assertEquals(9,expenseRecording.getTotalCost());
        assertEquals(-9,expenseRecording.getBudget());
        assertEquals("rental",expenseRecording.viewHighestCostCategory());
        assertEquals("education 33.3%\nrental 66.7%\n",expenseRecording.viewAllCostCategoryInPercentage());

        // add after undo

        expenseRecording.addExpenseInfo(expense3);
        assertEquals(expense1,expenseRecording.getInfoById(1));
        assertEquals(expense2,expenseRecording.getInfoById(2));
        assertEquals(expense3,expenseRecording.getInfoById(4));
        assertEquals(18,expenseRecording.getTotalCost());
        assertEquals(-18,expenseRecording.getBudget());
        assertEquals("food",expenseRecording.viewHighestCostCategory());
        assertEquals("education 16.7%\nfood 50.0%\nrental 33.3%\n",expenseRecording.viewAllCostCategoryInPercentage());
    }

}
