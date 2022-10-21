package persistence;

import model.Expense;
import model.ExpenseRecording;
import model.TimeTable;
import model.TreeApp;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    private void addExpenseRecording(TreeApp treeApp, JSONObject jsonObject) {
        ExpenseRecording expenseRecording = new ExpenseRecording(0);

        int budget = jsonObject.getInt("Budget");
        int totalIncome = jsonObject.getInt("TotalIncome");
        int totalCost = jsonObject.getInt("TotalCost");
    }




    // MODIFIES: timeTable
    // EFFECTS: parses timetable information from JSON object and add it to timeTable
    private void addTimeTable(TreeApp treeApp, JSONObject jsonObject) {
        TimeTable timeTable = (TimeTable) jsonObject.get("TimeTable");
        treeApp.setTimetable(timeTable);
    }

}
