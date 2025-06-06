
// BudgetModel.java
// This class models a simple budget management system with functionalities to add, delete, and retrieve financial entries.
// Last edited by Dmytro on June 7, 2025


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class BudgetModel {
    private static final String DB_URL = "jdbc:mysql://142.3.24.123:44445/budget_tracker_db";
    private static final String DB_USER = "budget";
    private static final String DB_PASSWORD = "Budget1";

    private Connection connection;

    public BudgetModel() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println(" Database connection successful!");
        } catch (SQLException e) {
            System.err.println(" Database connection failed!");
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database");
        }
    }

    // Set of valid categories for financial entries
    private static final Set<String> categories = Set.of("food", "rent", "transport", 
                    "entertainment", "utilities", "healthcare","income", "other");

    // Adds new income or expense entry with validation
    public boolean addFinancialEntry(String type, String categoryOrSource, double amount) {
        if (amount < 0 || (!type.equals("income") && !type.equals("expense"))) {
            return false;
        }
        if (!categories.contains(categoryOrSource)) {
            return false;
        }

        String tableName = type.equals("income") ? "income" : "expenses";
        String columnName = type.equals("income") ? "source" : "category";

        String sql = "INSERT INTO " + tableName + " (amount, " + columnName + ") VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setString(2, categoryOrSource);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete an entry by type and ID
    public boolean deleteEntry(String type, int id) {
        String tableName = type.equals("income") ? "income" : "expenses";
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieves all entries
    public List<FinancialEntry> getEntries() {
        List<FinancialEntry> entries = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rsIncome = stmt.executeQuery("SELECT id, amount, source FROM income");
            while (rsIncome.next()) {
                entries.add(new FinancialEntry("income", rsIncome.getString("source"), rsIncome.getDouble("amount")));
            }

            ResultSet rsExpenses = stmt.executeQuery("SELECT id, amount, category FROM expenses");
            while (rsExpenses.next()) {
                entries.add(new FinancialEntry("expense", rsExpenses.getString("category"), rsExpenses.getDouble("amount")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }

    // Retrieve entries by type and category
    public List<FinancialEntry> getEntriesByTypeAndCategory(String type, String category) {
        List<FinancialEntry> entries = new ArrayList<>();
        if (!type.equals("income") && !type.equals("expense")) {
            return entries;  // invalid type
        }
        if (!categories.contains(category)) {
            return entries;  // invalid category
        }

        String tableName = type.equals("income") ? "income" : "expenses";
        String columnName = type.equals("income") ? "source" : "category";

        String sql = "SELECT id, amount, " + columnName + " FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                double amount = rs.getDouble("amount");
                String catOrSource = rs.getString(columnName);
                entries.add(new FinancialEntry(type, catOrSource, amount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }


    // Calculate total income from entries
    public double getTotalIncome() {
        String sql = "SELECT SUM(amount) FROM income";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Calculate total expenses from entries
    public double getTotalExpenses() {
    String sql = "SELECT SUM(amount) FROM expenses";
    try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
        if (rs.next()) {
            return rs.getDouble(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0.0;
}

    // Calculate net balance (income - expenses)
    public double getNetBalance() {
        return getTotalIncome() - getTotalExpenses();
    }

    

    // Retrieve the predefined categories
    public List<String> getCategories() {
        return new ArrayList<>(categories);
    }

    // Close the database connection
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
