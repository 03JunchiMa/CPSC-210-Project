package persistence;

import model.TreeApp;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of the corresponding object to file
// Class Credits/Citation: JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: open printWriter output stream; throws FileNotFoundException if destination file cannot
    // it also throws IOException if any input and output exception happen
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: write JSON representation of TreeApp to file
    public void write(TreeApp treeApp) {
        JSONObject json = treeApp.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: close printWriter
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.write(json);
    }

}
