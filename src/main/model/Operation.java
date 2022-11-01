package model;

import org.json.JSONObject;
import persistence.Writable;

// Operation class
public class Operation implements Writable {
    private String operationName;
    private Expense expense;

    // EFFECTS: initializing a new operation, specified by the operationName and expense
    public Operation(String operationName, Expense expense) {
        this.operationName = operationName;
        this.expense = expense;
    }

    // EFFECTS: return the opration name
    public String getOperationName() {
        return this.operationName;
    }

    // EFFECTS: return the expense
    public Expense getExpense() {
        return this.expense;
    }

    // EFFECTS: compare;
    @Override
    public String toString() {
        return this.operationName + " " + this.expense.toString();
    }

    // EFFECTS: convert the operation to json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("OperationName",operationName);
        json.put("Expense",expense.toJson());
        return json;
    }
}
