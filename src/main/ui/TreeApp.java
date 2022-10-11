package ui;

import model.Expense;
import model.ExpenseRecording;

import java.util.Scanner;

// TreeApp application
public class TreeApp {

    ExpenseRecording expenseRecording;
    private Scanner scan;

    // EFFECTS: runs the tree app
    public TreeApp() {
        runTreeApp();
    }

    // MODIFIES: this
    // EFFECTS: get the user input
    private void runTreeApp() {
        scan = new Scanner(System.in);

        while (true) {
            System.out.println("-----------------------------------Main Page-----------------------------------------");
            System.out.println("Choose which application you want to use?(enter the corresponding entry number)");
            System.out.println("1.ExpenseRecording 2.TimeTable 3.exit");

            int number = scan.nextInt();

            while (number != 1 && number != 2 && number != 3) {
                System.out.println("invalid input, please enter again");
            }

            if (number == 1) {
                runExpenseRecording();
            } else if (number == 2) {
                runTimeTable();
            } else {
                System.out.println("-------------------------");
                System.out.println("Exit successfully");
                break;
            }
        }

    }


    // EFFECTS: run the expenseRecording, user can use this to record the expense
    @SuppressWarnings("methodlength")
    private void runExpenseRecording() {
        System.out.println("---------------ExpenseRecording user interface-------------------------------------------");
        System.out.println("Please set a budget");

        int budget = scan.nextInt();

        expenseRecording = new ExpenseRecording(budget);

        System.out.println("If you want to exit and return back to the main page, enter 6 at anytime");
        System.out.println("Please enter the corresponding number to indicate what you want to do:");
        System.out.println("------------------------------");
        System.out.println("1. add a new expense\n2. Delete an expense by id");
        System.out.println("3. Undo the last add/delete operation\n4. View the expense");
        System.out.println("5. queryExpenseById\n6.exit");

        while (true) {

            int number = scan.nextInt();

            while (number != 1 && number != 2 && number != 3 && number != 4 && number != 5 && number != 6) {
                System.out.println("Input number is not valid, please enter again");
                number = scan.nextInt();
            }

            if (number == 6) {
                System.out.println("---------------exit and return to the main page----------------------------------");
                break;
            }

            switch (number) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    deleteExpense();
                    break;
                case 3:
                    undoExpenseOperation();
                    break;
                case 4:
                    viewExpense();
                    break;
                case 5:
                    queryExpenseById();
                    break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: add the expense, and show the user the expense is added successfully and give the expense id.
    private void addExpense() {
        System.out.println("-----You're now adding the expense, please enter the expense in the following format-----");
        System.out.println("\"amount\" \"category\" (omit the quotation mark)");

        int amount = scan.nextInt();
        while (amount == 0) {
            System.out.println("Amount of the expense could not be 0, please enter again");
            amount = scan.nextInt();
        }
        String category = scan.next();

        Expense expense = new Expense(amount,category);

        expenseRecording.addExpenseInfo(expense);

        System.out.println("This expense is added successfully, the expense id is:" + expense.getId());
    }

    // MODIFIES: this
    // EFFECTS: delete the corresponding expense by id
    private void deleteExpense() {
        System.out.println("-----You're now deleting the expense, please enter the id of the expense-----------------");

        int id = scan.nextInt();

        boolean checkDelete = expenseRecording.deleteExpenseInfo(id);

        if (checkDelete) {
            System.out.println("The corresponding expense is been deleted successfully");
        } else {
            System.out.println("Did not find the corresponding expense, please check the id");
        }
    }

    private void undoExpenseOperation() {
        boolean checkUndo = expenseRecording.undoTheLastOperation();
        if (checkUndo) {
            System.out.println("undo successfully");
        } else {
            System.out.println("undo unsuccessful");
        }
    }

    @SuppressWarnings("methodlength")
    private void viewExpense() {
        System.out.println("------------------------------You're now viewing the expense-----------------------------");
        System.out.println("Please enter the corresponding number to indicate which ");
        System.out.println("1. View the highest cost category");
        System.out.println("2. View the ratio of the cost of categories (percentage)");
        System.out.println("3. View all the expenses");
        System.out.println("4. exit and go back");

        int number = scan.nextInt();

        while (number != 1 && number != 2 && number != 3 && number != 4) {
            System.out.println("The input number is not valid, please enter again");
            number = scan.nextInt();
        }

        while (number != 4) {
            if (number == 1) {
                System.out.println(expenseRecording.viewHighestCostCategory());
            } else if (number == 2) {
                System.out.println(expenseRecording.viewAllCostCategoryInPercentage());
            } else {
                System.out.println(expenseRecording.toString());
            }
            System.out.println("Enter the number again to see different views");
            number = scan.nextInt();
            while (number != 1 && number != 2 && number != 3 && number != 4) {
                System.out.println("The input number is not valid, please enter again");
                number = scan.nextInt();
            }
        }

    }

    private void queryExpenseById() {
        System.out.println("-------------------------------Query expense info by id Page-----------------------------");
        System.out.println("Please enter -1 to go back to the previous page");
        int id = scan.nextInt();
        while (id != -1) {
            System.out.println("Please enter the corresponding expense id:");
            id = scan.nextInt();
            String expenseInfo = expenseRecording.viewInfoById(id);
            if (expenseInfo.equals("")) {
                System.out.println("The system did not find the corresponding expense info, please check the id");
            } else {
                System.out.println(expenseInfo);
            }
        }
    }

    // EFFECTS: run the timetable, user can use this to add and delete intended course
    private void runTimeTable() {

    }


}
