package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

// Represents a reader that reads the information that have been entered in the TreeApp from JSON data stored in file
// Class Credits/Citation: JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: read all the information stored related to TreeApp
    // throws IOException if an error occurs when reading data from file
    public TreeApp read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTreeApp(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: prases parseTreeApp from JSON object and returns it
    private TreeApp parseTreeApp(JSONObject jsonObject) {
        TreeApp treeApp = new TreeApp();
        addExpenseRecording(treeApp,jsonObject);
        addTimeTable(treeApp,jsonObject);
        return treeApp;
    }


    // MODIFIES: expenseRecording
    // EFFECTS: parses expenseInfo from JSON object and add it to expenseRecording
    private void addExpenseRecording(TreeApp treeApp, JSONObject jsonObject1) {
        ExpenseRecording expenseRecording = new ExpenseRecording(0);

        JSONObject jsonObject = jsonObject1.getJSONObject("ExpenseRecording");
        int budget = jsonObject.getInt("Budget");
        int totalIncome = jsonObject.getInt("TotalIncome");
        int totalCost = jsonObject.getInt("TotalCost");
        ArrayList<Integer> expenseIdList = getExpenseIdList(jsonObject);
        Map<String,Integer> categoryCost = getCategoryCost(jsonObject);
        Map<Integer,Expense> idQueryExpense = getIdQueryExpense(jsonObject);
        Operation lastOperation = getLastOperation(jsonObject);
        int idCounter = jsonObject.getInt("IdCounter");

        expenseRecording.setBudget(budget);
        expenseRecording.setTotalIncome(totalIncome);
        expenseRecording.setTotalCost(totalCost);
        expenseRecording.setExpenseIdList(expenseIdList);
        expenseRecording.setCategoryCost(categoryCost);
        expenseRecording.setIdQueryExpense(idQueryExpense);
        expenseRecording.setLastOperation(lastOperation);
        expenseRecording.setIdCounter(idCounter);

        treeApp.setExpenseRecording(expenseRecording);
    }

    private ArrayList<Integer> getExpenseIdList(JSONObject jsonObject) {
        ArrayList<Integer> expenseIdList = new ArrayList<>();

        JSONArray jsonArray = jsonObject.getJSONArray("ExpenseIdList");

        List list = jsonArray.toList();
        for (int i = 0; i < list.size(); i++) {
            expenseIdList.add((int)list.get(i));
        }

        return expenseIdList;
    }

    private Map<String, Integer> getCategoryCost(JSONObject jsonObject) {
        Map<String, Integer> map = new TreeMap<>();

        JSONArray jsonArray = jsonObject.getJSONArray("CategoryCost");

        for (Object json : jsonArray) {
            JSONObject nextCategoryCost = (JSONObject) json;
            String category = nextCategoryCost.getString("Category");
            int categoryCost = nextCategoryCost.getInt("CategoryCost");
            map.put(category,categoryCost);
        }

        return map;
    }

    private Map<Integer, Expense> getIdQueryExpense(JSONObject jsonObject) {
        Map<Integer,Expense> map = new HashMap<>();

        JSONArray jsonArray = jsonObject.getJSONArray("IdQueryExpense");

        for (Object json : jsonArray) {
            JSONObject nextIdQueryExpense = (JSONObject) json;
            int id = nextIdQueryExpense.getInt("Id");
            JSONObject jsonObject1 = nextIdQueryExpense.getJSONObject("Expense");
            int amount = jsonObject1.getInt("Amount");
            String category = jsonObject1.getString("Category");
            int idExpense = jsonObject1.getInt("Id");
            Expense expense = new Expense(amount,category);
            expense.setId(idExpense);

            map.put(id,expense);
        }

        return map;
    }

    private Operation getLastOperation(JSONObject jsonObject) {
        JSONObject json = jsonObject.getJSONObject("LastOperation");
        String operationName = json.getString("OperationName");

        JSONObject jsonObject1 = json.getJSONObject("Expense");
        int amount = jsonObject1.getInt("Amount");
        String category = jsonObject1.getString("Category");
        int idExpense = jsonObject1.getInt("Id");
        Expense expense = new Expense(amount,category);
        expense.setId(idExpense);

        Operation operation = new Operation(operationName,expense);

        return operation;
    }


    // MODIFIES: timeTable
    // EFFECTS: parses timetable information from JSON object and add it to timeTable
    private void addTimeTable(TreeApp treeApp, JSONObject jsonObject) {
        TimeTable timeTable = new TimeTable();
        addCourse(timeTable,jsonObject);
        treeApp.setTimetable(timeTable);
    }

    // EFFECTS: add the course from json object into the timetable
    private void addCourse(TimeTable timeTable, JSONObject jsonObject1) {
        JSONObject jsonObject = jsonObject1.getJSONObject("TimeTable");
        JSONArray jsonArray = jsonObject.getJSONArray("Courses");

        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            String courseNameSeciton = nextCourse.getString("CourseNameSection");
            int startTime = nextCourse.getInt("StartTime");
            int endTime = nextCourse.getInt("EndTime");
            int weekDay = nextCourse.getInt("Weekday");

            Course course = new Course(courseNameSeciton,startTime,endTime,weekDay);
            timeTable.addIntendedCourse(course);
        }
    }

}
