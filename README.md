# My Personal Project - Tree Application

## Project description

This will be the course project for CPSC 210. This project will be focusing on developing a **Living and studying app**, called **Tree**

## Target audience

The target audience will be the university students, since most of the features will be related to them.

## What will this application do? 

This application will focus on providing university students with different tools, such as

small example:
- record and manage your own living expense
- course selection tool
- timetable viewer
- pomodoro timer
- play music

try to interact with different things and see how the tree grows. 

## User Stories

> Phase 1
- As a user, I want to be able to record my living expense (add my living expense to a category, and delete the expense, those operations should be done fast).
- As a user, I want to be able to view my living expense in different forms (the highest living expense, percentage etc, the query operation should be done fast).
- As a user, I want to query the specific living expense. 
- As a user, I want to be able to undo the last operation that I did in modifying the living expense.
- As a user, I want to be able to add the course to the timetable
- As a user, I want to delete the course in the timetable
- As a user, I want to view the timetable

> Phase 2
- As a user, I want to be able to store the Expense information and the course information that I have entered. 
- As a user, I want to be able to load the expense information and the course information that I entered previously.

> Phase 3 
- As a user, I want to be able to add and view the timetables in a graphical user interface 
- As a user, I want to be able to have the option to load and save files in a graphical user interface

# Phase 3 instructions for grader
- You can add the expense by first selecting the expense page in the left bar, and then click on the add button to add it.
- You can also add the course by first selecting the timetable page, then click on the add button to add it.
- You can locate my visual component in the main page (the tree, the menu bar, and the setting), in the expense page (pie graph), in the timetable page.
- You can save the state of my application by either selecting the file (manually save), or by selecting exit (there will be dialog for asking whether you want to save your progress or not)
- You can reload the state of your application by selecting synchronize in the setting (menu bar)
- You can also delete the entry that you added in the expense page or course page by right click (there will be the deletion pop up menu)
- You can change the appearance of the application by selecting different look and feel in the setting menu
- You can generate the valid timetable by clicking on the button "Generate valid timetables" in the timetable page after you adding the course

# Phase 4: Task 2

Representative event log
1. Sun Nov 27 10:09:16 PST 2022: Added Expense Info: -100 education 1
2. Sun Nov 27 10:09:22 PST 2022: Added course: CPSC 210 102800900135
3. Sun Nov 27 10:09:51 PST 2022: Deleted Expense Info: -100 education 1
4. Sun Nov 27 10:10:30 PST 2022: Unsuccessful try of deleting Expense Info
5. Sun Nov 27 10:10:43 PST 2022: Unsuccessful adding the course: CPSC 210 102800900135
6. Sun Nov 27 10:10:57 PST 2022: Removed course: CPSC 210 102800900135
7. Sun Nov 27 10:11:05 PST 2022: Unsuccessful deleting the course: CPSC 210 102800900135

# Phase 4: Task 3

Reflection :there are many places in the project I can refactor so that the design can be proved. 

1. Notice that "TimeTableUpperLeftPanel" is the associated with the type "CourseTable" and the type "Timetable". However,
the type "CourseTable" is also associated with the type "Timetable". Here, the Coupling is very strong, we could make only
the "CourseTable" to have the "TimeTable" field, and set up the "getTimeTable()" method in the "CourseTable" so that the Coupling is reduced.
2. I believe the overall Cohesion is strong, every class is doing its own specified job. However, there are some method, such as "getTime()" and 
"toTimeString()" inside the "CourseTable" class which can be moved to the "TimeTableAlgorithms" class, so that "CourseTable" can have strong Cohesion.
3. Also, in the "MainFrame" class located in the ui package, the "MainFrame" is doing the job for setting up the listener for the TopMenuBar,
which is clearly not MainFrame's job, which need to be refactored into the TopMenuBar so that the Cohesion can be improved.
4. Also notice that all the three class "ExpenseMainPanel", "TimeTablePanel", "MainFrame" is associated with the "TreeApp", however, the MainFrame also contains the 
field of the type "ExpenseMainPanel" and "TimeTablePanel", so we could only make the "MainFrame" associated with the "TreeApp" to reduce the coupling, and pass the "TreeApp" field in the constructor.
5. Observer pattern can be applied to the "CourseTable" and the "ExpenseTable" with corresponding panel, so that all the different components can get the 
message of the adding the deleting row operation. 
6. I applied the Singleton pattern to the "TopMenuBar", because we only want one TopMenuBar instance to exit and all the class can have access to the same TopMenuBar.
7. In the "CancelButton" class, in the add listener method, there are many similar operations when setting the color, this is low cohesion, we could pull the similar operation out so that it's easy to modify it.