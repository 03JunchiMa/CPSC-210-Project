package model;

// Operation class
public class Operation {
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
}
