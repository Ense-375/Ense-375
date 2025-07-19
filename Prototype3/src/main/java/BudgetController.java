// BudgetModel.java
// This class models a simple budget management system with functionalities to add, delete, and retrieve financial entries.
// Last edited by Dmytro on July 13, 2025

import java.util.List;


public class BudgetController {
    private final BudgetModel model;
    private final BudgetView view;

    public BudgetController(BudgetModel model, BudgetView view) {
        this.model = model;
        this.view = view;
    }

    // Main control loop
    public void start() {
        while (true) {
            int choice = view.showMenu(); // Get user's menu selection
            switch (choice) {
                case 1 -> addEntry(); // Add new entry
                case 2 -> view.displayEntries(model.getEntries()); // Show all entries
                case 3 -> deleteEntry(); // Delete an entry
                case 4 -> showBalance(); // Show income, expenses, balance
                case 5 -> viewByCategory(); // Filter by category
                case 0 -> {
                    view.displayMessage("Goodbye!");
                    return; // Exit application
                }
                default -> view.displayMessage("Invalid choice. Try again."); // Handle invalid input
            }
        }
    }

    // Add an income or expense entry
    @SuppressWarnings("ConvertToStringSwitch")
    private void addEntry() {
        String type = view.promptString("Enter type (income/expense): ").toLowerCase();
        String categoryOrSource;

        if ("expense".equals(type)) {
            List<String> validCategories = model.getCategories();
            view.displayMessage("Here are the valid categories: " + validCategories);
            categoryOrSource = view.promptString("Enter category: ").toLowerCase();
        } else if ("income".equals(type)) {
            categoryOrSource = view.promptString("Enter source: ").toLowerCase();
        } else {
            view.displayMessage("Invalid type. Please enter 'income' or 'expense'.");
            return;
        }

        double amount = view.promptDouble("Enter amount: ");
        if (model.addFinancialEntry(type, categoryOrSource, amount)) {
            view.displayMessage("Entry added successfully.");
        } else {
            view.displayMessage("Invalid entry.");
        }
    }


    // Delete an entry by index
    private void deleteEntry() {
        String type = view.promptString("Enter type of entry to delete (income/expense): ").toLowerCase();
        int id = view.promptInt("Enter entry ID to delete: ");
        if (model.deleteEntry(type, id)) {
            view.displayMessage("Entry deleted");
        } else {
            view.displayMessage("Invalid entry ID or type.");
        }
    }

    // Show the financial summary
    private void showBalance() {
        double income = model.getTotalIncome();
        double expenses = model.getTotalExpenses();
        double balance = model.getNetBalance();
        view.displayMessage("Total Income: $" + income);
        view.displayMessage("Total Expenses: $" + expenses);
        view.displayMessage("Balance: $" + balance);
    }

    // Show entries filtered by category
    @SuppressWarnings("ConvertToStringSwitch")
    private void viewByCategory() {
        String type = view.promptString("Enter type (income/expense): ").toLowerCase();
        String categoryOrSource;
        if ("expense".equals(type)) {
            List<String> validCategories = model.getCategories();
            view.displayMessage("Here are the valid categories: " + validCategories);
            categoryOrSource = view.promptString("Enter category: ").toLowerCase();
        } else if ("income".equals(type)) {
            categoryOrSource = view.promptString("Enter source: ").toLowerCase();
        } else {
            view.displayMessage("Invalid type.");
            return;
        }

        List<FinancialEntry> filteredEntries = model.getEntriesByTypeAndCategory(type, categoryOrSource);
        if (filteredEntries.isEmpty()) {
            view.displayMessage("No entries found for the specified category/source.");
        } else {
            view.displayEntries(filteredEntries);
        }
    }


}
