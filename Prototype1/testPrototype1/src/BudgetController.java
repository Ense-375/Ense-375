
// BudgetModel.java
// This class models a simple budget management system with functionalities to add, delete, and retrieve financial entries.
// Last edited by Dmytro on May 29, 2025

import java.util.Set;


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
    private void addEntry() {
        String type = view.promptString("Enter type (income/expense): ");
        type = type.toLowerCase(); // Normalize input to lowercase
        String category = "income";
        // Prompt for category only if type is expense
        if ("expense".equals(type)) {
            Set<String> validCategories = model.getCategories(); // Get valid categories from model
            view.displayMessage("Here are the valid categories: " + validCategories);
            category = view.promptString("Enter category: ");
            category = category.toLowerCase(); // Normalize input to lowercase
        }
        double amount = view.promptDouble("Enter amount: ");
        if (model.addFinancialEntry(type, category, amount)) {
            view.displayMessage("Entry added successfully.");
        } else {
            view.displayMessage("Invalid entry.");
        }
    }


    // Delete an entry by index
    private void deleteEntry() {
        int index = view.promptInt("Enter entry index to delete: ");
        if (model.deleteEntry(index)) {
            view.displayMessage("Entry deleted.");
        } else {
            view.displayMessage("Invalid index.");
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
    private void viewByCategory() {
        String category = view.promptString("Enter category: ");
        view.displayEntries(model.getEntriesByCategory(category));
    }


}
