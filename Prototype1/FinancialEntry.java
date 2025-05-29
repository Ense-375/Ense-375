// FinancialEntry.java
// This class represents a financial entry in a budget management system.
// Last edited by Dmytro on May 29, 2025

public class FinancialEntry {
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
