package model;

// Expense class, provide the struct for the expenseInfo
public class Expense {
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
}
