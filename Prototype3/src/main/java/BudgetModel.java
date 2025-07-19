
// BudgetModel.java
// This class models a simple budget management system with functionalities to add, delete, and retrieve financial entries.
// Last edited by Dmytro on July 13, 2025


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class BudgetModel {
    private static final String DB_URL = "jdbc:mysql://localhost:3307/dsj361";
    private static final String DB_USER = "dsj361";
    private static final String DB_PASSWORD = "MyData@uni25";
    private Connection connection;
    private List<FinancialEntry> entries = new ArrayList<>();
    

    public BudgetModel() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println(" Connected successfully to university DB!");
        } catch (SQLException e) {
            System.err.println(" Database connection failed!");
            System.err.println("SQLException: " + e.getMessage());
        }

    }

    public Connection getConnection() {
        return this.connection;
    }

    // Set of valid categories for financial entries
    private static final Set<String> categories = Set.of("food", "rent", "transport", 
                    "entertainment", "utilities", "healthcare","income", "other");

    // Adds new income or expense entry with validation
    public boolean addFinancialEntry(String type, String categoryOrSource, double amount) {
        if (amount < 0 || (!type.equals("income") && !type.equals("expense"))) {
            return false;
        }
        if (!categories.contains(categoryOrSource) && type.equals("expense")) {
            System.out.println("Invalid category: " + categoryOrSource);
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
            System.err.println("SQLException: " + e.getMessage());
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
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    // Retrieves all entries
    public List<FinancialEntry> getEntries() {
        List<FinancialEntry> tempEentries = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rsIncome = stmt.executeQuery("SELECT id, amount, source FROM income");
            while (rsIncome.next()) {
                tempEentries.add(new FinancialEntry (rsIncome.getInt("id"),"income", rsIncome.getString("source"),rsIncome.getDouble("amount")));
            }

            ResultSet rsExpenses = stmt.executeQuery("SELECT id, amount, category FROM expenses");
            while (rsExpenses.next()) {
                tempEentries.add(new FinancialEntry(rsExpenses.getInt("id"), "expense", rsExpenses.getString("category"), rsExpenses.getDouble("amount")));
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return tempEentries;
    }

    // Retrieve entries by type and category
    public List<FinancialEntry> getEntriesByTypeAndCategory(String type, String category) {
        List<FinancialEntry> tempEentries = new ArrayList<>();
        if (!type.equals("income") && !type.equals("expense")) {
            return tempEentries;  // invalid type
        }
        if (!categories.contains(category)) {
            return tempEentries;  // invalid category
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
                tempEentries.add(new FinancialEntry(rs.getInt("id"), type, catOrSource, amount));

            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return tempEentries;
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
            System.err.println("SQLException: " + e.getMessage());
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
        System.err.println("SQLException: " + e.getMessage());
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
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void clearDatabase() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM income");
            stmt.executeUpdate("DELETE FROM expenses");
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        // Also clear the in-memory list to keep model consistent
        entries.clear();


    }

    // // Optional: test DB connection directly
    // public static void main(String[] args) {
    //     BudgetModel model = new BudgetModel();
    //     if (model.getConnection() != null) {
    //         System.out.println(" DB test succeeded.");
    //     } else {
    //         System.out.println(" DB test failed.");
    //     }
    // }
    
}
