package model;

import org.json.JSONObject;
import persistence.Writable;

// Expense class, provide the struct for the expenseInfo
public class Expense implements Writable {
    private int amount;
    private String category;
    private int id;

    // REQUIRES: amount does not equal to 0
    // EFFECTS: initialize a new expenseInfo
    // each time when an expenseInfo been initialized, it will be provided a unique id.
    // if the amount is greater than 0, then it's an income, if it's less than 0 than it's a cost
    public Expense(int amount, String category) {
        this.amount = amount;
        if (amount > 0) {
            this.category = "income";
        } else {
            this.category = category;
        }
    }

    // EFFECTS: return id
    public int getId() {
        return this.id;
    }

    // EFFECTS: return amount
    public int getAmount() {
        return this.amount;
    }

    // EFFECTS: return category
    public String getCategory() {
        return this.category;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.amount + " " + category + " " + id;
    }

    // EFFECTS: convert the expense to json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Amount",amount);
        json.put("Category",category);
        json.put("Id",id);
        return json;
    }

}
