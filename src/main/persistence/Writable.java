package persistence;

import org.json.JSONObject;

// return the json object of the object
public interface Writable {

    // EFFECTS: convert the object to json object when implementing this method
    JSONObject toJson();
}
