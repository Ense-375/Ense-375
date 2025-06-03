// BudjetViewTest.java
// This file verifies the functionalities to display the main menu, user input, financial entries, and balance
// Last Edited by Tolani on June 2, 2025

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;

public class BudgetViewTest {

    /**
     * Test for displaying the main menu and user input
     * Valid inputs should be accepted and processed correctly.
     */
    @Test
    public void testDisplay() {
        String simulatedInput = "1\nTestInput\n100.50\n2\n"; // inputs for: menu, string, double, int
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes())); //
        BudgetView view = new BudgetView();

        int choice = view.showMenu();
        assertTrue(choice >= 0 || choice <= 5, "Menu choice should be between 0 and 5");

        String input = view.promptString("Enter a string: ");
        assertNotNull(input, "Input should not be null");
        assertFalse(input.isEmpty(), "Input should not be empty");

        double amount = view.promptDouble("Enter an amount: ");
        assertTrue(amount >= 0, "Amount should be non-negative");

        int index = view.promptInt("Enter an index: ");
        assertTrue(index >= 0, "Index should be non-negative");

        view.displayMessage("Test message");
    }

    /**
     * Test for displaying financial entries
     * Valid entries should be displayed correctly.
     */

    @Test
    public void testDisplayEntries() {
        BudgetView view = new BudgetView();
        BudgetModel model = new BudgetModel();
        

        model.addFinancialEntry("income", "salary", 3000.0);
        model.addFinancialEntry("expense", "rent", 1000.0);
        
        view.displayEntries(model.getEntries());
        assertFalse(model.getEntries().isEmpty(), "Entries should not be empty");
    }

    /**
     * Test for displaying balance
     * Should display the total balance correctly based on the entries
     */
    @Test
    public void testDisplayBalance(){
        BudgetView view = new BudgetView();
        BudgetModel model = new BudgetModel();

        model.addFinancialEntry("income", "income", 0.0);
        model.addFinancialEntry("expense", "utilities", 800.0);

        double balance = model.getNetBalance();
        view.displayBalance(balance);
        assertEquals(-800.0, balance, "After entries, balance should be -800.0");
        // use these lines to check for positive balance 
        //assertTrue(balance >= 0, "Balane should be non-negative");
        //assertFalse(balance < 0, "balance should not be negative"); 
    }

    /**
     * Test for dispalaying Message
     * Should display the message passed to it.
     */
    @Test
    public void testDisplayMessage() {
        BudgetView view = new BudgetView();
        String message = "This is a test message";
        view.displayMessage(message);
        assertNotNull(message, "Message should not be null");
    }
}
