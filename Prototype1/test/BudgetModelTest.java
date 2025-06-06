// BudjetModelTest
// This file verifies the functionalities to add, delete, and retrieve financial entries
// Last Edited by Tolani on June 2, 2025

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BudgetModelTest {

    BudgetModel model = new BudgetModel();

    @Test
    public void testAddFinancialEntry(){

        /**
         * Test for adding finanacial entries with various conditions
         * Valid entries should be added successfully, while invalid entries should not.
         */
        assertTrue(model.addFinancialEntry("income", "income", 1000.0), "Should add valid income entry");
        assertTrue(model.addFinancialEntry("expense", "rent", 500.0), "Should add an expense entry");
        assertFalse(model.addFinancialEntry("income", "income", -200.0), "Should register as invalid entry");
        assertFalse(model.addFinancialEntry("expense", "food", -100.0), "Should register as invalid category");
        assertFalse(model.addFinancialEntry("invalidType", "food", 100.0), "Should register as invalid type");

        // Verify that the entries were added correctly
        assertEquals(2, model.getEntries().size(), "Should have 2 valid entries");
        assertEquals("income", model.getEntries().get(0).type, "First entry should be income");
    }

    /**
     * Test for deleting financial entries
     * Valid entries should be deleted successfully, while invalid indices should not.
     */
    @Test
    public void testDeleteEntry(){
        boolean entry1 = model.addFinancialEntry("income", "income", 1000.0);
        boolean entry2 = model.addFinancialEntry("expense", "rent", 500.0);

        assertTrue(entry1, "Should add valid income entry");
        assertTrue(entry2, "Should add valid expense entry");

        assertTrue(model.deleteEntry(1), "Should delete first entry");
        assertEquals(1, model.getEntries().size(), "Should have 1 entry left after deletion");
        assertTrue(model.deleteEntry(1), "Should delete the last remaining entry");
        assertFalse(model.deleteEntry(1), "Should not delete from an empty list");
        
        assertFalse(model.deleteEntry(5), "Should not delete entry with invalid index");
        assertTrue(model.getEntries().isEmpty(), "Entries list should be empty after deleting all entries");
    }

    /**
     * Test for calculating total income, expenses and net balance
     * Should return correct sums based on the entries added.
     */
    @Test
    public void testGetCashFlow(){
        assertEquals(0.0, model.getTotalIncome(), "Total income should be 0 with no entries");
        assertEquals(0.0, model.getTotalExpenses(), "Total expense be 0 with no entries");
        assertEquals(0.0, model.getNetBalance(), "Net balance should be 0 with no entries");
        model.addFinancialEntry("income", "income", 1500.0);
        model.addFinancialEntry("expense", "healthcare", 800.0);
        model.addFinancialEntry("expense", "entertainment", 300.0);

        double income = model.getTotalIncome();
        double expenses = model.getTotalExpenses();
        double balance = model.getNetBalance();

        assertEquals(1500.0, income, "Total income should be 1500.0");
        assertEquals(1100.0, expenses, "Total expenses should be 1100.0");
        assertEquals(400.0, balance, "Net balance should be 400.0");
        assertTrue(model.getEntries().size() > 0, "Should have entries in the model");
        assertFalse(model.getEntries().isEmpty(), "Entries list should not be empty");
    }

    /**
     * Test for retrieving entries by category
     * Should return only entries that match the specified category.
     */
    @Test
    public void testGetEntriesByCategory(){
        model.addFinancialEntry("income", "income", 9000.0);
        model.addFinancialEntry("expense", "other", 800.0);
        model.addFinancialEntry("expense", "transport", 500.0);
        model.addFinancialEntry("expense", "transport", 900.0);

        // brain check (expected, actual)
        assertEquals(4, model.getEntries().size(), "Should have 4 entries in total");
        assertEquals(1, model.getEntriesByCategory("income").size(), "Should return 1 income entry");
        assertEquals(2, model.getEntriesByCategory("transport").size(), "Should return 2 transport entries");
        assertEquals(1, model.getEntriesByCategory("other").size(), "Should return 1 entry for 'other' category");
    }
}
