package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// set a budget and record the living cost (income) to different categories and view it
public class ExpenseRecording implements Writable {

    private int budget;
    private int totalIncome;
    private int totalCost;
    private ArrayList<Integer> expenseIdList;
    private Map<String,Integer> categoryCost;
    private Map<Integer,Expense> idQueryExpense;
    private Operation lastOperation;
    private int idCounter = 1;

    // EFFECTS: create a new living expense recording and new budget in cents (for one month);
    public ExpenseRecording(int budget) {
        expenseIdList = new ArrayList<>();
        categoryCost = new TreeMap<>();
        idQueryExpense = new HashMap<>();
        setBudget(budget);

    }

    // REQUIRES: the amount in the expense should not be equals to 0
    // MODIFIES: this
    // EFFECTS: Depends on the last operation, the system will either Add a new expense, or the id that is recorded
    // in the last operation. If it's a new expense, if the amount of the expense is less than 0, then it's a cost.
    // the recorded number will be negative (which means the string representation will show the negative number),
    // and it will be subtracted from the budget and the corresponding category.
    // If the amount of the expense is greater than 0, than it's an income, the recorded number will be positive
    // it won't be added to the budget, however, it will be added to the total income, value will be positive
    // The corresponding category of the cost will also be recorded
    // for the income you can set any category, the program only record the income to the category "income"
    // If the category of the expense is present, the expense will be added to the category.
    // If the category is not present, the recording will open a new category and add the information to it.
    //  update the lastOperation operation (record the lastOperation id)
    public void addExpenseInfo(Expense expense) {

        if (lastOperation != null && lastOperation.getExpense() != null
                && lastOperation.getExpense().getId() == expense.getId()) {
            expense.setId(lastOperation.getExpense().getId());
        } else {
            expense.setId(idCounter++);
        }

        idQueryExpense.put(expense.getId(),expense);
        expenseIdList.add(expense.getId());

        if (expense.getAmount() < 0) {
            totalCost += Math.abs(expense.getAmount());
            decreaseBudget(expense.getAmount());
        } else {
            totalIncome += expense.getAmount();
        }

        if (categoryCost.containsKey(expense.getCategory())) {
            categoryCost.put(expense.getCategory(), categoryCost.get(expense.getCategory()) + expense.getAmount());
        } else {
            categoryCost.put(expense.getCategory(), expense.getAmount());
        }
        lastOperation = new Operation("addExpenseInfo",expense);

        EventLog.getInstance().logEvent(new Event("Added Expense Info: " + expense.toString()));
    }

    // MODIFIES: this
    // EFFECTS: delete certain expense info by id.
    // (corresponding entry of id will disapear after deleting, other info's id won't change)
    // Update the recorded number for budget, cost, or income,
    // also update each category's information.
    // each operation will be recorded into an operation stack, if the operation is successful then record,
    // if the operation is not successful, then set the operation to the undoTheLastOperation
    // if it succesfully deletes the corresponding id expenseInfo, then return true
    // otherwise, return false
    public boolean deleteExpenseInfo(int id) {
        int position = binarySearchIdPlace(id,0,expenseIdList.size() - 1);
        if (expenseIdList.get(position) == id) {
            Expense expense = idQueryExpense.get(id);
            categoryCost.put(expense.getCategory(), categoryCost.get(expense.getCategory()) - expense.getAmount());
            if (categoryCost.get(expense.getCategory()) == 0) {
                categoryCost.remove(expense.getCategory());
            }
            if (expense.getCategory().equals("income")) {
                totalIncome -= Math.abs(expense.getAmount());
            } else {
                totalCost -= Math.abs(expense.getAmount());
                increaseBudget(expense.getAmount());
            }
            idQueryExpense.remove(id);
            expenseIdList.remove(position);
            lastOperation = new Operation("deleteExpenseInfo",expense);

            EventLog.getInstance().logEvent(new Event("Deleted Expense Info: " + expense.toString()));

            return true;
        } else {
            EventLog.getInstance().logEvent(new Event("Unsuccessful try of deleting Expense Info"));
            lastOperation = new Operation("undoTheLastOperation",null);
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: undo the very lastOperation adding or delete operation.
    // Update the recorded number for budget, cost, or income
    // also update each category,
    // after the undo operation, the lastOperation operation will be updated to the undo operation
    // if the operation is undoTheLastOperation, then return false
    // otherwise undo the corresponding operation and return true.
    public boolean undoTheLastOperation() {

        if (lastOperation.getOperationName().equals("deleteExpenseInfo")) {
            addExpenseInfo(lastOperation.getExpense());
            lastOperation = new Operation("undoTheLastOperation",null);
            return true;
        } else if (lastOperation.getOperationName().equals("addExpenseInfo")) {
            deleteExpenseInfo(lastOperation.getExpense().getId());
            lastOperation = new Operation("undoTheLastOperation",null);
            return true;
        } else {
            return false;
        }

    }

    // MODIFIES: this
    // EFFECTS: binary search the id information, return the index of corresponding id in the list
    public int binarySearchIdPlace(int id, int left, int right) {

        while (left < right) {
            int mid = (left + right + 1) >> 1;
            if (expenseIdList.get(mid) <= id) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }

    // EFFECTS: return the highest cost category, if no expense info is present, return empty string
    // the format should be "amount category"
    public String viewHighestCostCategory() {

        int cost = Integer.MAX_VALUE;
        String highestCostCategory = "";
        for (Map.Entry<String, Integer> entry : categoryCost.entrySet()) {
            if (entry.getKey().equals("income")) {
                continue;
            }
            if (entry.getValue() < cost) {
                cost = entry.getValue();
                highestCostCategory = entry.getKey();
            }
        }

        return highestCostCategory;
    }

    // EFFECTS: return the string of the cost information regarding each category in percentage form
    // the order of the representation will be sorted from left to right, highest to lowermost
    // since income category is the income, it will not be showed
    // if no expense info is present, return empty string
    public String viewAllCostCategoryInPercentage() {
        double totalCost = 0;

        String rep = "";
        for (Map.Entry<String, Integer> entry : categoryCost.entrySet()) {
            if (entry.getKey().equals("income")) {
                continue;
            }
            totalCost += entry.getValue();
        }

        for (Map.Entry<String, Integer> entry : categoryCost.entrySet()) {
            if (entry.getKey().equals("income")) {
                continue;
            }
            rep = rep + entry.getKey() + " " + String.format("%.1f%%",entry.getValue() / totalCost * 100) + "\n";
        }

        return rep;
    }

    // EFFECTS: return the string representation of a specific id
    // string representation format will be the "amount categoryName"
    // the sign of the amount will depend on whether it's the cost or income
    // if no expense info is present, return empty string
    // the format will be arranged in "amount category"
    public String viewInfoById(int id) {
        Expense expense = idQueryExpense.get(id);
        String rep = expense.getAmount() + " " + expense.getCategory() + "\n";
        return rep;
    }

    // MODIFIES: this
    // EFFECTS: set the budget to amount
    public void setBudget(int amount) {
        this.budget = amount;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public void setExpenseIdList(ArrayList<Integer> expenseIdList) {
        this.expenseIdList = expenseIdList;
    }

    public void setCategoryCost(Map<String,Integer> categoryCost) {
        this.categoryCost = categoryCost;
    }

    public void setIdQueryExpense(Map<Integer,Expense> idQueryExpense) {
        this.idQueryExpense = idQueryExpense;
    }

    public void setLastOperation(Operation lastOperation) {
        this.lastOperation = lastOperation;
    }

    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
    }

    // MODIFIES: this
    // EFFECTS: decrease the budget by amount
    public void decreaseBudget(int amount) {
        this.budget -= Math.abs(amount);
    }

    // MODIFIES: this
    // EFFECTS: increase the budget by amount
    public void increaseBudget(int amount) {
        this.budget += Math.abs(amount);
    }

    // EFFECTS: return budget
    public int getBudget() {
        return this.budget;
    }

    // EFFECTS: return income
    public int getTotalIncome() {
        return this.totalIncome;
    }

    // EFFECTS: return cost
    public int getTotalCost() {
        return this.totalCost;
    }

    // EFFECTS: return expenseIdList
    public ArrayList<Integer> getExpenseIdList() {
        return this.expenseIdList;
    }

    // EFFECTS: return CategoryCost
    public Map<String,Integer> getCategoryCost() {
        return this.categoryCost;
    }

    // EFFECTS: return IdQueryExpense
    public Map<Integer,Expense> getIdQueryExpense() {
        return this.idQueryExpense;
    }

    // EFFECTS: return the corresponding expense by id, if the corresponding id does not exist, return null.
    public Expense getInfoById(int id) {
        return idQueryExpense.get(id);
    }

    // EFFECTS: return the lastOperation operation
    public Operation getLastOperation() {
        return lastOperation;
    }

    // EFFECTS: return the idCounter
    public int getIdCounter() {
        return this.idCounter;
    }

    // EFFECTS: return a string with the representation of information of living expense in a compact form.
    // the compact form show item in
    // "category1 amount1\ncategory2 amount2\ncategory3 amount3\n...categoryN amountN\n"
    // will be printed in the lexicographical order
    @Override
    public String toString() {
        String rep = "";

        for (Map.Entry<Integer,Expense> entry : idQueryExpense.entrySet()) {
            rep = rep + entry.getValue().getCategory() + " " + entry.getValue().getAmount() + "\n";
        }

        return rep;
    }

    // EFFECTS: convert the expenserecording to json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Budget",budget);
        json.put("TotalIncome",totalIncome);
        json.put("TotalCost",totalCost);
        json.put("ExpenseIdList",expenseIdListToJson());
        json.put("CategoryCost",categoryCostToJson());
        json.put("IdQueryExpense",idQueryExpenseToJson());
        json.put("LastOperation",lastOperation.toJson());
        json.put("IdCounter",idCounter);
        return json;
    }

    // EFFECTS: return expenseIdList as JSON array
    public JSONArray expenseIdListToJson() {
        JSONArray jsonArr = new JSONArray();

        for (Integer i : expenseIdList) {
            jsonArr.put(i);
        }

        return jsonArr;
    }

    // EFFECTS: return categoryCost as JSON array
    public JSONArray categoryCostToJson() {
        JSONArray jsonArr = new JSONArray();

        for (Map.Entry<String,Integer> entry : categoryCost.entrySet()) {
            JSONObject json = new JSONObject();
            json.put("Category",entry.getKey());
            json.put("CategoryCost",entry.getValue());
            jsonArr.put(json);
        }

        return jsonArr;
    }

    // EFFECTS: return idQueryExpense as JSON array
    public JSONArray idQueryExpenseToJson() {
        JSONArray jsonArr = new JSONArray();

        for (Map.Entry<Integer,Expense> entry : idQueryExpense.entrySet()) {
            JSONObject json = new JSONObject();
            json.put("Id",entry.getKey());
            json.put("Expense",entry.getValue().toJson());
            jsonArr.put(json);
        }
        return jsonArr;
    }

}
