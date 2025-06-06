
// BudgetModel.java
// This class models a simple budget management system with functionalities to add, delete, and retrieve financial entries.
// Last edited by Dmytro on May 29, 2025


import java.util.*;


public class BudgetView {
    private final Scanner scanner = new Scanner(System.in); // Scanner for user input

    // Displays the main menu and returns the user's choice

    public int showMenu() {
        System.out.println("\n=== Personal Budget Tracker ===");
        System.out.println("1. Add Entry");
        System.out.println("2. View Entries");
        System.out.println("3. Delete Entry");
        System.out.println("4. View Balance");
        System.out.println("5. View Entries by Category");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
        return scanner.nextInt();
    }

    // Prompts user for a string input
    public String promptString(String message) {
        System.out.print(message);
        return scanner.next();
    }

    // Prompts user for a double input
    public double promptDouble(String message) {
        System.out.print(message);
        return scanner.nextDouble();
    }

    // Prompts user for an integer input
    public int promptInt(String message) {
        System.out.print(message);
        return scanner.nextInt();
    }

    // Displays a list of financial entries
    public void displayEntries(List<FinancialEntry> entries) {
        if (entries.isEmpty()) {
            System.out.println("No entries found.");
        } else {
            System.out.println("\n=== Financial Entries ===");
            for (int i = 0; i < entries.size(); i++) {
                System.out.println((i + 1) + ". " + entries.get(i));
            }
        }
    }

    // Display a message to the user
    public void displayMessage(String message) {
        System.out.println(message);
    }

    // Displays the total balance
    public void displayBalance(double balance) {
        System.out.printf("Total Balance: %.2f%n", balance);
    }


}
