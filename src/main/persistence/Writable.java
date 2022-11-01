package persistence;

import org.json.JSONObject;

// return the json object of the object
public interface Writable {
    JSONObject toJson();
}
