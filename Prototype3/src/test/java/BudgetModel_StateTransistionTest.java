// Last Edited by Tolani on July 25th 2025

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BudgetModel_StateTransistionTest {
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
    public void testAddEntry_validEntry(){ 
        // Test that adds entry and verifies that added entry is valid (path s2 -> s3)
        view.setInputs(List.of("1", "expense", "food", "50", "0"));
        controller.start();
        List<String> messages = view.getMessages();

        assertTrue(messages.contains("Entry added successfully."), "Should confirm entry was added.");
        assertEquals(1, model.getEntries().size(), "There should be one entry in the model.");
    }

    @Test
    public void testAddEntry_InvalidEntry(){
        // Test that adds an invalid entry and verifies that it is not added (path s2 -> s4)
        view.setInputs(List.of("1", "expense", "invalidCategory", "50", "0"));
        controller.start();
        List<String> messages = view.getMessages();

        assertTrue(messages.stream().anyMatch(m -> m.contains("Invalid entry")), "Should indicate invalid entry.");
        assertTrue(model.getEntries().isEmpty(), "There should be no entries in the model");
    }

    @Test
    public void testDeleteEntry_validEntry() {
        // Test that deletes an entry and verifies that it is removed (path s5 -> s6)
        model.addFinancialEntry("income", "salary", 1000);
        int id = model.getEntries().get(0).getId();
        
        view.setInputs(List.of("3", "income", String.valueOf(id), "0"));
        controller.start();
        List<String> messages = view.getMessages();

        assertTrue(messages.stream().anyMatch(m -> m.contains("Entry deleted")), "Should confirm entry was deleted.");
        assertTrue(model.getEntries().isEmpty(), "There should be no entries in the model.");
    }

    @Test
    public void testDeleteEntry_invalidEntry() {
        // Test that attempts to delete an entry that does not exist (path s5 -> s7)
        view.setInputs(List.of("3", "income", "8888", "0"));
        controller.start();
        List<String> messages = view.getMessages();

        assertTrue(messages.stream().anyMatch(m -> m.contains("Invalid entry ID or type.")), "Should indicate entry not found.");
        assertEquals(0, model.getEntries().size(), "There should be no entries in the model.");
    }
    @Test
    public void testViewBalance() {
        // Test that views the balance (path s1 -> s8)
        model.addFinancialEntry("income", "income", 1000);
        model.addFinancialEntry("expense", "food", 200);

        view.setInputs(List.of("4", "0"));
        controller.start();
        List<String> messages = view.getMessages();

        assertTrue(messages.stream().anyMatch(m -> m.contains("Total Income: $")), "Should show total income.");
        assertTrue(messages.stream().anyMatch(m -> m.contains("Total Expenses: $")), "Should show total expenses.");
    }

    @AfterEach
    public void tearDown() {
        model.clearDatabase();
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
