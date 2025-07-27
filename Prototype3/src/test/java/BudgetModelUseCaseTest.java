// Last Edited by Tolani on July 26th 2025

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BudgetModelUseCaseTest {
    private BudgetModel model;

    @BeforeEach
    public void setUp() {   
        model = new BudgetModel();
        model.clearDatabase();
    }

    @Test
    public void testAddValidIncomeEntry() {
        boolean val = model.addFinancialEntry("income", "income", 5000.0);
        assertTrue(val, "Expected to add a valid income entry");
    }
    @Test
    public void testAddInvalidType(){
        boolean val = model.addFinancialEntry("profit", "income", 5000.0);
        assertFalse(val, "Invalid type should not be added");
    }

    @Test
    public void testAddInvalidCategory() {
        boolean val = model.addFinancialEntry("expense", "vacation", 200.0);
        assertFalse(val, "Invalid category should not be added");
    }

    @Test
    public void testViewAllEntries() {
        model.addFinancialEntry("income", "income", 3000.0);
        model.addFinancialEntry("expense", "food", 200.0);

        List<FinancialEntry> entries = model.getEntries();
        assertEquals(2, entries.size(), "Should get 2 entries");
    }

    @Test
    public void testViewAllEntriesEmptyDB() {
        List<FinancialEntry> entries = model.getEntries();
        assertTrue(entries.isEmpty(), "Should return empty list");
    }

    @Test 
    public void testDeleteValidEntry(){
        model.addFinancialEntry("income", "income", 8000.0);
        List<FinancialEntry> entries = model.getEntries();
        assertFalse(entries.isEmpty());

        FinancialEntry entry = entries.get(0);
        boolean delete = model.deleteEntry("income", entry.getId());
        assertTrue(delete, "Valid entry should be deleted");
    }

    @Test 
    public void testDeleteInvalidEntry() {
        boolean delete = model.deleteEntry("income", 8888);
        assertFalse(delete, "Invalid entry ID should not be deleted");
    }

    @Test 
    public void testViewBalanceAfterEntries() {
        model.addFinancialEntry("income", "income", 1500.0);
        model.addFinancialEntry("expense", "rent", 700.0);

        double income = model.getTotalIncome();
        double expenses = model.getTotalExpenses();
        double balance = model.getNetBalance();

        assertEquals(1500.0, income);
        assertEquals(700.0, expenses);
        assertEquals(800.0, balance);
    }

    @Test 
    public void testViewValidCategory(){
        model.addFinancialEntry("expense", "food", 100.0);
        model.addFinancialEntry("expense", "transport", 50.0);

        List<FinancialEntry> entries = model.getEntriesByTypeAndCategory("expense", "food");
        assertEquals(1, entries.size(), "Should return 1 entry for 'food' category");
        assertEquals("food", entries.get(0).getCategoryOrSource());
    }

    @Test 
    public void testViewInvalidCategory() {
        List<FinancialEntry> entries = model.getEntriesByTypeAndCategory("expense", "vacation");
        assertTrue(entries.isEmpty(), "Should return empty list for invalid category");
    }
    
    @AfterEach
    public void tearDown() {
        model.clearDatabase();
    }
}
