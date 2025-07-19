// Last edited by Tolani on June 17th 2025 

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BudgetController_DataFlowTesting {
    private BudgetModel model;
    private TestableBudgetView view;
    private BudgetController controller;

    @BeforeEach
     public void setUp() {
        model = new BudgetModel();
        view = new TestableBudgetView();
        controller = new BudgetController(model, view);
        model.clearDatabase();
    }

    @Test
    public void testValidTypeInvalidID() { // path 1->2->3->4->5
        model.addFinancialEntry("income", "income", 500);
        view.setInputs(List.of( "income", "9999"));
        controller.start();
        assertTrue(view.getMessages().stream().anyMatch(m -> m.contains("Invalid entry ID or type")));
    }

     @Test
    public void testValidTypeValidId() { // path 1->2->3->4->6
        model.addFinancialEntry("income", "income", 800);
        int id = model.getEntriesByTypeAndCategory("income", "income").get(0).getId();

        view.setInputs(List.of("income", String.valueOf(id)));
        controller.start();

        assertTrue(view.getMessages().contains("Entry deleted"));
    }

    @Test
    public void testDeleteEntry_allDUPaths(){
        // path 2 -> 5 , 3 -> 5
        model.addFinancialEntry("income", "income", 500);
        view.setInputs(List.of( "income", "9999"));
        controller.start();
        assertTrue(view.getMessages().stream().anyMatch(m -> m.contains("Invalid entry ID or type")));

        view.clearOutput();

        // path 2 -> 6, 3 -> 6
        model.addFinancialEntry("income", "income", 800);
        int id = model.getEntriesByTypeAndCategory("income", "income").get(0).getId();

        view.setInputs(List.of("income", String.valueOf(id)));
        controller.start();

        System.out.println("Messages: " + view.getMessages()); //debug
        assertTrue(view.getMessages().contains("Entry deleted"));

    }
    
    @AfterEach
    public void tearDown() {
        model.clearDatabase();
        model.close();
    }

    // Simulated input/output for testing
    private static class TestableBudgetView extends BudgetView {
        private List<String> inputs;
        private int inputIndex = 0;
        private final StringBuilder output = new StringBuilder();

        public void setInputs(List<String> inputs) {
            this.inputs = inputs;
        }

        public List<String> getMessages() {
            return List.of(output.toString().split("\n"));
        }

        @Override
        public String promptString(String message) {
            output.append(message).append("\n");
            return inputs.get(inputIndex++);
        }

        @Override
        public double promptDouble(String message) {
            output.append(message).append("\n");
            return Double.parseDouble(inputs.get(inputIndex++));
        }

        @Override
        public int promptInt(String message) {
            output.append(message).append("\n");
            return Integer.parseInt(inputs.get(inputIndex++));
        }

        @Override
        public void displayMessage(String message) {
            output.append(message).append("\n");
        }

        @Override
        public int showMenu() {
            if (inputIndex == 0) return 3;
            else return 0;
        }

        public void clearOutput() {
            output.setLength(0);
            inputIndex = 0;
        }
    }
}
