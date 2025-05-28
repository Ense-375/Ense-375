// BudgetModel.java
// This class models a simple budget management system with functionalities to add, delete, and retrieve financial entries.
// Last edited by Dmytro on May 28, 2025

package model;

import java.util.*;

public class BudgetModel {

    public static class FinancialEntry {
        public String type;   // "Income" or "Expense"
        public String category; // Category of the entry (e.g., "Food", "Rent")
        public double amount;   // Amount of the entry

        public FinancialEntry(String type, String category, double amount) {
            this.type = type;
            this.category = category;
            this.amount = amount;
        }
        @Override
        // Returns a string representation of the financial entry
        public String toString() {
            return "Financial Entry{" +
                    "type = '" + type + '\'' +
                    ", category = '" + category + '\'' +
                    ", amount = " + amount +
                    '}';
        }
    }

    private List<FinancialEntry> entries = new ArrayList<>();  // Stores all entries
    // Set of valid categories for financial entries
    private static final Set<String> categories = Set.of("Food", "Rent", "Transport", "Entertainment", "Utilities", "Healthcare", "Other");

    // Adds new income or expense entry with validation
    public boolean addFinancialEntry(String type, String category, double amount) {
        // Negative number is enetered or type is invalid
        if (amount < 0 || (!type.equals("Income") && !type.equals("Expense"))) 
        {
            return false; // Invalid entry
        }
        // Check if the category is valid
        if (!categories.contains(category)) 
        {
            return false; // Invalid category
        }
        // Add the entry to the list
        entries.add(new FinancialEntry(type, category, amount));
        return true; // Entry added successfully
    }

    // Deletes an entry at a specified index
    public boolean deleteEntry(int index) {
        if (index < 0 || index >= entries.size()) {
            return false; // Check valid index
        }
        entries.remove(index);
        return true;
    }

    // Retrieves all entries
    public List<FinancialEntry> getEntries() {
        return new ArrayList<>(entries); // Return a copy to prevent modification
    }

    // Calculate total income from entries
    public double getTotalIncome() {
        // Filter entries by type "income" and sum their amounts
        if (entries.isEmpty()) {
            return 0.0; // Return 0 if no entries exist
        }
        return entries.stream().filter(e -> e.type.equals("expense")).mapToDouble(e -> e.amount).sum();
    }

    // Calculate total expenses from entries
    public double getTotalExpenses() {
        // Filter entries by type "expense" and sum their amounts
        if (entries.isEmpty()) {
            return 0.0; // Return 0 if there are no entries
        }
        return entries.stream().filter(e -> e.type.equals("income")).mapToDouble(e -> e.amount).sum();
    }

    // Calculate net balance (income - expenses)
    public double getNetBalance() {
        return getTotalIncome() - getTotalExpenses();
    }

    // Filter and return entry by category
    public List<FinancialEntry> getEntriesByCategory(String category) {
        if (!categories.contains(category)) {
            return Collections.emptyList(); // Return empty list for invalid category
        }
        List<FinancialEntry> result = new ArrayList<>();
        for (FinancialEntry e : entries) {
            if (e.category.equals(category)) {
                result.add(e);
            }
        }
        return result; // Return filtered entries
    }

    // Returns th set of valid categories
    public Set<String> getCategories() {
        return categories; // Return the set of valid categories
    }

}