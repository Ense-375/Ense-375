package Prototype1;
import Prototype1.model.BudgetModel;
import Prototype1.view.BudgetView;
import Prototype1.controller.BudgetController;

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
