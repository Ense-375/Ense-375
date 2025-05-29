// Main.java
// This class serves as the entry point for the Personal Budget Tracker application.
// It initializes the model, view, and controller, and starts the application loop.
// Last edited by Dmytro on May 29, 2025

public class Main {
    public static void main(String[] args) {
        // Create the model, view, and controller
        BudgetModel model = new BudgetModel();
        BudgetView view = new BudgetView();
        BudgetController controller = new BudgetController(model, view);

        // Start the application
        controller.start();
    }
}
