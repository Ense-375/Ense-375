
// FinancialEntry.java
// This class represents a financial entry in a budget management system.
// Last edited by Dmytro on July 13, 2025

public class FinancialEntry {
    private int id;
    private String type;
    private String categoryOrSource;
    private double amount;

    // Constructor without id (for inserting new entries)
    public FinancialEntry(String type, String categoryOrSource, double amount) {
        this.type = type;
        this.categoryOrSource = categoryOrSource;
        this.amount = amount;
    }

    // Default constructor without id (for inserting new entries)
    public FinancialEntry() {
        this.type = null;
        this.categoryOrSource = null;
        this.amount = 0.0;
    }

    // Constructor with id (for retrieving existing entries)
    public FinancialEntry(int id, String type, String categoryOrSource, double amount) {
        this.id = id;
        this.type = type;
        this.categoryOrSource = categoryOrSource;
        this.amount = amount;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getCategoryOrSource() {
        return categoryOrSource;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return categoryOrSource;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.categoryOrSource = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    @Override
    public String toString() {
        return String.format("[%s] %s: %.2f", type, categoryOrSource, amount);
    }
}
