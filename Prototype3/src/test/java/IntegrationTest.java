// Last Editied by Tolani on July 18th 2025

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegrationTest {
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
    public void controllerAndViewIntegration() {
        // Add entry: 1 (menu), income, income, 1000, 0 (exit)
        view.setInputs(List.of("1", "income", "income", "1000", "0"));
        controller.start();
        assertTrue(view.getMessages().stream().anyMatch(m -> m.contains("Entry added successfully.")));

        // Delete entry: get ID first
        int id = model.getEntriesByTypeAndCategory("income", "income").get(0).getId();
        view.setInputs(List.of("3", "income", String.valueOf(id), "0"));
        controller.start();
        assertTrue(view.getMessages().stream().anyMatch(m -> m.contains("Entry deleted")));

        // View balance: 4 (menu), 0 (exit)
        view.setInputs(List.of("4", "0"));
        controller.start();
        assertTrue(view.getMessages().stream().anyMatch(m -> m.contains("Total Income")));
    }
    @Test
    public void controllerandModelIntegration() {
        model.addFinancialEntry("income", "income", 1000);
        model.addFinancialEntry("expense", "invalidCategory", 100);

        List<FinancialEntry> entries = model.getEntries();
        assertEquals(1, entries.size());
        assertEquals(1000, entries.get(0).getAmount());

        int id = entries.get(0).getId();
        model.deleteEntry("income", id);
        assertTrue(model.getEntries().isEmpty());
    }

    @Test
    public void modelAndDBIntegration() {
        model.addFinancialEntry("income", "income", 1000);
        
        List<FinancialEntry> entries = model.getEntries();
        assertEquals(1, entries.size());
        assertEquals(1000, entries.get(0).getAmount());

        int id = entries.get(0).getId();
        model.deleteEntry("income", id);
        assertTrue(model.getEntries().isEmpty());
    }

    @AfterEach
    public void tearDown() {
        model.clearDatabase();
        model.close();
    }

    // Simulated input/output for testing
    private static class TestableBudgetView extends BudgetView {
        private final StringBuilder output = new StringBuilder();
        private Queue<String> inputQueue = new LinkedList<>();

        public void setInputs(List<String> inputs) {
            inputQueue.clear();
            inputQueue.addAll(inputs);
            output.setLength(0);
        }

        public List<String> getMessages() {
            return List.of(output.toString().split("\n"));
        }

        @Override
        public int showMenu() {
            return Integer.parseInt(inputQueue.poll());
        }

        @Override
        public String promptString(String message) {
            output.append(message).append("\n");
            return inputQueue.poll();
        }

        @Override
        public double promptDouble(String message) {
            output.append(message).append("\n");
            return Double.parseDouble(inputQueue.poll());
        }

        @Override
        public int promptInt(String message) {
            output.append(message).append("\n");
            return Integer.parseInt(inputQueue.poll());
        }


        @Override
        public void displayMessage(String message) {
            output.append(message).append("\n");
        }
    }
}
