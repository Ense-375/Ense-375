// Last Edited by Tolani on June 14, 2025

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BudgetController_PathTesting {
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
    public void testDeleteEntry_InvalidPath_1_2_3_4() {
        view.setInputs(List.of("invalidType", "999"));
        controller.start(); 
        assertTrue(view.getMessages().contains("Invalid entry ID or type.") ||
                   view.getMessages().contains("Invalid type."));
    }

    @Test
    public void testDeleteEntry_ValidPath_1_2_3_5() {
        boolean added = model.addFinancialEntry("income", "job", 1000);
        assertTrue(added);

        List<FinancialEntry> entries = model.getEntriesByTypeAndCategory("income", "job");
        assertFalse(entries.isEmpty());
        int idToDelete = entries.get(0).getId();

        view.setInputs(List.of("income", String.valueOf(idToDelete)));
        controller.start();

        assertTrue(view.getMessages().contains("Entry deleted."));
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
    }
}

