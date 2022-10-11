package ui;

import model.Course;
import model.Expense;
import model.ExpenseRecording;
import model.TimeTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

// TreeApp application
public class TreeApp {

    TimeTable timeTable;
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
        System.out.println("---------------ExpenseRecording User Interface-------------------------------------------");
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
            System.out.println("Returned back to the expense recording page");
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

    // MODIFIES: this
    // EFFECTS: undo the operation
    private void undoExpenseOperation() {
        boolean checkUndo = expenseRecording.undoTheLastOperation();
        if (checkUndo) {
            System.out.println("undo successfully");
        } else {
            System.out.println("undo unsuccessful");
        }
    }

    // EFFECTS: show the expense
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

    // EFFECTS: query the expense by id
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

    @SuppressWarnings("methodlength")
    // EFFECTS: run the timetable, user can use this to add and delete intended course
    private void runTimeTable() {
        System.out.println("---------------TimeTable User Interface-------------------------------------------");
        System.out.println("Please enter the corresponding number to indicate what you want to do:");
        System.out.println("1. Add a new course");
        System.out.println("2. Delete a course");
        System.out.println("3. View the timetable");
        System.out.println("4. Exit");

        timeTable = new TimeTable();

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
            System.out.println("---------------TimeTable User Interface-------------------------------------------");
            System.out.println("Please enter the corresponding number to indicate what you want to do:");
            System.out.println("1. Add a new course");
            System.out.println("2. Delete a course");
            System.out.println("3. View the timetable");
            System.out.println("4. Exit");
            number = scan.nextInt();
        }
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
        boolean checkAddCourse = timeTable.addIntendedCourse(course);
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
        boolean checkDeleteCourse = timeTable.deleteIntendedCourse(course);
        if (checkDeleteCourse) {
            System.out.println("Course has been deleted successfully");
        } else {
            System.out.println("The system did not find the corresponding course, please check again");
        }
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: view the timetable
    private void viewTimeTable() {

        int number = 0;
        while (number != -1) {
            System.out.println("--------------------------You are currently viewing the timetable--------------------");
            System.out.println("Enter -1 to exit");
            ArrayList<Course> courseList = timeTable.getTimetable();
            for (int i = 1; i <= 5; i++) {
                String weekday = intToWeekday(i);
                System.out.println("------" + weekday + "------");

                ArrayList<String> courseByDay = new ArrayList<>();
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
                Collections.sort(courseByDay);

                for (String s : courseByDay) {
                    System.out.println(s);
                }
            }
            number = scan.nextInt();
        }
        System.out.println("back to the main page");


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



}
