package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// TreeAppModel application
public class TreeAppUI {

    private static final String JSON_STORE = "./data/TreeApp.json";

    TreeApp treeApp;
    private Scanner scan;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the tree app
    public TreeAppUI() {
        treeApp = new TreeApp();
        scan = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        treeApp = new TreeApp();
        runTreeApp();
    }

    // MODIFIES: this
    // EFFECTS: get the user input
    @SuppressWarnings("methodlength")
    private void runTreeApp() {

        while (true) {
            System.out.println("-----------------------------------Main Page-----------------------------------------");
            System.out.println("Choose which application you want to use?(enter the corresponding entry number)");
            System.out.println("1. ExpenseRecording\n2. TimeTable\n3. Load from saved data\n4. Save data\n5. Exit");

            int number = scan.nextInt();

            while (number != 1 && number != 2 && number != 3 && number != 4 && number != 5) {
                System.out.println("invalid input, please enter again");
            }

            if (number == 1) {
                runExpenseRecording();
            } else if (number == 2) {
                runTimeTable();
            } else if (number == 3) {
                loadTreeApp();
            } else if (number == 4) {
                saveTreeApp();
            } else {
                System.out.println("-------------------------");
                System.out.println("Before you exit, do you want to save the data? (YES/NO)");
                String check = scan.next();
                if (check.equalsIgnoreCase("YES")) {
                    saveTreeApp();
                } else {
                    System.out.println("Exit successfully");
                }
                break;
            }
        }

    }


    // EFFECTS: run the expenseRecording, user can use this to record the expense
    private void runExpenseRecording() {
        System.out.println("---------------ExpenseRecording User Interface-------------------------------------------");

        while (true) {
            runExpenseRecordingGreeting();
            int number = scan.nextInt();

            while (number != 0 && number != 1 && number != 2 && number != 3
                    && number != 4 && number != 5 && number != 6) {
                System.out.println("Input number is not valid, please enter again");
                number = scan.nextInt();
            }
            if (number == 6) {
                System.out.println("---------------exit and return to the main page----------------------------------");
                break;
            }

            switchNumberInRunExpenseRecording(number);

            System.out.println("---------------Returned back to the expense recording page---------------");
        }
    }

    // EFFECTS: say greetings in the runExpenseRecording page
    public void runExpenseRecordingGreeting() {
        System.out.println("If you want to exit and return back to the main page, enter 6 at anytime");
        System.out.println("Please enter the corresponding number to indicate what you want to do:");
        System.out.println("------------------------------");
        System.out.println("0. set a budget\n1. add a new expense\n2. Delete an expense by id");
        System.out.println("3. Undo the last add/delete operation\n4. View the expense");
        System.out.println("5. queryExpenseById\n6. exit");
    }

    // EFFECTS: switch the number case and determine which method to execute in the runExpenseRecording page
    public void switchNumberInRunExpenseRecording(int number) {
        switch (number) {
            case 0:
                setBudget();
                break;
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

    // MODIFIES: this
    // EFFECTS: set the budget of the expenseRecording
    private void setBudget() {
        System.out.println("Please set a budget");

        int budget = scan.nextInt();

        treeApp.getExpenseRecording().setBudget(budget);

        System.out.println("Budget has been set to " + budget);
    }

    // MODIFIES: this
    // EFFECTS: add the expense, and show the user the expense is added successfully and give the expense id.
    private void addExpense() {
        System.out.println("-----You're now adding the expense, please enter the expense in the following format-----");
        System.out.println("\"amount\" \"category\" (omit the quotation mark)");
        System.out.println("If it's a expense, please put the negative amount");
        System.out.println("If it is an income,please use positive amount, you can put any category for the income");

        int amount = scan.nextInt();
        while (amount == 0) {
            System.out.println("Amount of the expense could not be 0, please enter again");
            amount = scan.nextInt();
        }
        String category = scan.next();

        Expense expense = new Expense(amount,category);

        treeApp.getExpenseRecording().addExpenseInfo(expense);

        System.out.println("This expense is added successfully, the expense id is:" + expense.getId());
    }

    // MODIFIES: this
    // EFFECTS: delete the corresponding expense by id
    private void deleteExpense() {
        System.out.println("-----You're now deleting the expense, please enter the id of the expense-----------------");

        int id = scan.nextInt();

        boolean checkDelete = treeApp.getExpenseRecording().deleteExpenseInfo(id);

        if (checkDelete) {
            System.out.println("The corresponding expense is been deleted successfully");
        } else {
            System.out.println("Did not find the corresponding expense, please check the id");
        }
    }

    // MODIFIES: this
    // EFFECTS: undo the operation
    private void undoExpenseOperation() {
        boolean checkUndo = treeApp.getExpenseRecording().undoTheLastOperation();
        if (checkUndo) {
            System.out.println("undo successfully");
        } else {
            System.out.println("undo unsuccessful");
        }
    }

    // EFFECTS: show the expense
    private void viewExpense() {
        viewExpenseGreeting();

        int number = scan.nextInt();

        while (number != 1 && number != 2 && number != 3 && number != 4) {
            System.out.println("The input number is not valid, please enter again");
            number = scan.nextInt();
        }

        while (number != 4) {
            if (number == 1) {
                System.out.println(treeApp.getExpenseRecording().viewHighestCostCategory());
            } else if (number == 2) {
                System.out.println(treeApp.getExpenseRecording().viewAllCostCategoryInPercentage());
            } else {
                System.out.println(treeApp.getExpenseRecording().toString());
            }
            System.out.println("Enter the number again to see different views");
            number = scan.nextInt();
            while (number != 1 && number != 2 && number != 3 && number != 4) {
                System.out.println("The input number is not valid, please enter again");
                number = scan.nextInt();
            }
        }

    }

    // EFFECTS: say greetings in the view expense
    public void viewExpenseGreeting() {
        System.out.println("------------------------------You're now viewing the expense-----------------------------");
        System.out.println("Please enter the corresponding number to indicate which ");
        System.out.println("1. View the highest cost category");
        System.out.println("2. View the ratio of the cost of categories (percentage)");
        System.out.println("3. View all the expenses");
        System.out.println("4. exit and go back");
    }

    // EFFECTS: query the expense by id
    private void queryExpenseById() {
        System.out.println("-------------------------------Query expense info by id Page-----------------------------");
        System.out.println("Please enter -1 to go back to the previous page");
        int id = scan.nextInt();
        while (id != -1) {
            System.out.println("Please enter the corresponding expense id:");
            id = scan.nextInt();
            String expenseInfo = treeApp.getExpenseRecording().viewInfoById(id);
            if (expenseInfo.equals("")) {
                System.out.println("The system did not find the corresponding expense info, please check the id");
            } else {
                System.out.println(expenseInfo);
            }
        }
    }

    // EFFECTS: run the timetable, user can use this to add and delete intended course
    private void runTimeTable() {
        timeTableGreeting();

        int number = scan.nextInt();

        while (!(number >= 1 && number <= 4)) {
            System.out.println("The input is not valid, please enter again");
            number = scan.nextInt();
        }

        while (number != 4) {

            if (number == 1) {
                addCourse();
            } else if (number == 2) {
                deleteCourse();
            } else {
                viewTimeTable();
            }

            timeTableGreeting();

            number = scan.nextInt();
        }
    }

    public void timeTableGreeting() {
        System.out.println("---------------TimeTable User Interface-------------------------------------------");
        System.out.println("Please enter the corresponding number to indicate what you want to do:");
        System.out.println("1. Add a new course");
        System.out.println("2. Delete a course");
        System.out.println("3. View the timetable");
        System.out.println("4. Exit");
    }

    // MODIFIES: this
    // EFFECTS: add a new course to timetable
    private void addCourse() {
        System.out.println("---------------You are now adding the course---------------------------------------------");
        System.out.println("please enter the course in the following format:");
        System.out.println("\"CourseName \" \"section number\" \"startTime\" \"endTime\" \"weekday\"");
        System.out.println("Note for the startTime, you should use 24-hours clock, and omit the semicolon");
        System.out.println("This means 17:00 should be entered as 1700");
        System.out.println("Note the for weekday, if you have this class on Monday, Tuesday and Wednesday, enter 123");

        String courseName = "";

        courseName += scan.next() + " ";
        courseName += scan.next() + " ";
        courseName += scan.next();

        int startTime;
        int endTime;
        int weekday;
        startTime = scan.nextInt();
        endTime = scan.nextInt();
        weekday = scan.nextInt();

        Course course = new Course(courseName,startTime,endTime,weekday);
        boolean checkAddCourse = treeApp.getTimeTable().addIntendedCourse(course);
        if (checkAddCourse) {
            System.out.println("Course has been added successfully");
        } else {
            System.out.println("The course has already exit in the timetable, please check again");
        }
    }

    // MODIFIES: this
    // EFFECTS: delete a course
    private void deleteCourse() {
        System.out.println("-----------------------------------You are now deleting the course-----------------------");
        System.out.println("please enter the course in the following format:");
        System.out.println("\"CourseName \" \"section number\" \"startTime\" \"endTime\" \"weekday\"");
        System.out.println("Note for the startTime, you should use 24-hours clock, and omit the semicolon");
        System.out.println("This means 17:00 should be entered as 1700");
        System.out.println("Note the for weekday, if you have this class on Monday, Tuesday and Wednesday, enter 123");

        String courseName = "";

        courseName += scan.next() + " ";
        courseName += scan.next() + " ";
        courseName += scan.next();

        int startTime;
        int endTime;
        int weekday;
        startTime = scan.nextInt();
        endTime = scan.nextInt();
        weekday = scan.nextInt();

        Course course = new Course(courseName,startTime,endTime,weekday);
        boolean checkDeleteCourse = treeApp.getTimeTable().deleteIntendedCourse(course);
        if (checkDeleteCourse) {
            System.out.println("Course has been deleted successfully");
        } else {
            System.out.println("The system did not find the corresponding course, please check again");
        }
    }

    // EFFECTS: view the timetable
    private void viewTimeTable() {

        int number = 0;
        while (number != -1) {
            System.out.println("--------------------------You are currently viewing the timetable--------------------");
            System.out.println("Enter -1 to exit");
            ArrayList<Course> courseList = treeApp.getTimeTable().getTimetable();
            for (int i = 1; i <= 5; i++) {
                String weekday = intToWeekday(i);
                System.out.println("------" + weekday + "------");

                ArrayList<String> courseByDay = new ArrayList<>();
                courseByDayArrange(courseList, i, courseByDay);
                Collections.sort(courseByDay);

                for (String s : courseByDay) {
                    System.out.println(s);
                }
            }
            number = scan.nextInt();
        }
        System.out.println("back to the main page");
    }

    // EFFECTS: arrange the course from day to day
    private static void courseByDayArrange(ArrayList<Course> courseList, int i, ArrayList<String> courseByDay) {
        for (int j = 0; j < courseList.size(); j++) {
            Course course = courseList.get(j);
            int day = course.getWeekday();

            while (day > 0) {
                int singleDay = day % 10;
                day /= 10;
                if (singleDay == i) {
                    courseByDay.add(course.getStartTime() + "~"
                            + course.getEndTime() + ": " + course.getCourseNameSection());
                }
            }
        }
    }

    // EFFECTS: return the string representation of the weekday
    private String intToWeekday(int number) {
        if (number == 1) {
            return "Monday";
        } else if (number == 2) {
            return "Tuesday";
        } else if (number == 3) {
            return "Wednesday";
        } else if (number == 4) {
            return "Thursday";
        } else {
            return "Friday";
        }
    }

    // EFFECTS: save the TreeApp
    private void saveTreeApp() {
        try {
            jsonWriter.open();;
            jsonWriter.write(treeApp);
            jsonWriter.close();
            System.out.println("Saved the data to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: load the TreeApp from previous saved data
    private void loadTreeApp() {
        try {
            treeApp = jsonReader.read();
            System.out.println("Loaded successfully from previous saved data");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
