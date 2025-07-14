// Last Edited by Tolani on June 6, 2025

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BudgetModelTest {

    private BudgetModel model;

    @BeforeEach
    public void setUp() {
        model = new BudgetModel();
        model.clearDatabase();
    }

    @Test
    public void testAddFinancialEntry() {
        assertTrue(model.addFinancialEntry("income", "income", 1000.0), "Should add valid income entry");
        assertTrue(model.addFinancialEntry("expense", "rent", 500.0), "Should add an expense entry");
        assertFalse(model.addFinancialEntry("income", "income", -200.0), "Should reject negative income");
        assertFalse(model.addFinancialEntry("expense", "invalidCategory", 100.0), "Should reject invalid category");
        assertFalse(model.addFinancialEntry("invalidType", "food", 100.0), "Should reject invalid type");

        List<FinancialEntry> entries = model.getEntries();
        assertEquals(2, entries.size(), "Should have 2 valid entries");
    }

    @Test
    public void testDeleteEntry() {
        model.clearDatabase(); 
        model.addFinancialEntry("income", "income", 1000.0);
        model.addFinancialEntry("expense", "rent", 500.0);

        assertTrue(model.deleteEntry("income", 32), "Should delete first income entry");
        assertEquals(1, model.getEntries().size(), "Should have 1 entry left after deleting income");

        assertTrue(model.deleteEntry("expense", 40), "Should delete the last remaining expense entry");
        assertFalse(model.deleteEntry("income", 1), "Should not delete non-existent income entry");
        assertFalse(model.deleteEntry("invalid", 5), "Should not delete with invalid type");

        assertTrue(model.getEntries().isEmpty(), "Entries list should be empty after all deletions");
    }

    @Test
    public void testGetCashFlow() {
        assertEquals(0.0, model.getTotalIncome());
        assertEquals(0.0, model.getTotalExpenses());
        assertEquals(0.0, model.getNetBalance());

        model.addFinancialEntry("income", "income", 1500.0);
        model.addFinancialEntry("expense", "healthcare", 800.0);
        model.addFinancialEntry("expense", "entertainment", 300.0);

        assertEquals(1500.0, model.getTotalIncome());
        assertEquals(1100.0, model.getTotalExpenses());
        assertEquals(400.0, model.getNetBalance());
    }

    @Test
    public void testGetEntriesByCategory() {
        model.addFinancialEntry("income", "income", 9000.0);
        model.addFinancialEntry("expense", "other", 800.0);
        model.addFinancialEntry("expense", "transport", 500.0);
        model.addFinancialEntry("expense", "transport", 900.0);

        assertEquals(4, model.getEntries().size());

        assertEquals(1, model.getEntriesByTypeAndCategory("income", "income").size());
        assertEquals(2, model.getEntriesByTypeAndCategory("expense", "transport").size());
        assertEquals(1, model.getEntriesByTypeAndCategory("expense", "other").size());
    }

    @Test
    public void testGetCategories() {
        List<String> expected = Arrays.asList("food", "rent", "transport", 
                    "entertainment", "utilities", "healthcare","income", "other");
        List<String> actual = model.getCategories();

        
        assertTrue(actual.containsAll(expected), "Returned categories should contain all expected values");

        // Check that modifying the returned list does not affect the original
        actual.add("NewCategory");
        assertFalse(model.getCategories().contains("NewCategory"), "Modifying returned list should not affect internal list");
    }

    @Test
    public void testGetEntriesByTypeAndCategory() {
        model.clearDatabase();

        // Valid entries
        model.addFinancialEntry("income", "Food", 500.0);
        model.addFinancialEntry("income", "Food", 100.0);
        model.addFinancialEntry("expense", "Rent", 300.0);
        model.addFinancialEntry("expense", "Transport", 200.0);

        // Valid case: income + Food
        List<FinancialEntry> incomeFood = model.getEntriesByTypeAndCategory("income", "Food");
        assertEquals(2, incomeFood.size(), "Should return 2 income entries for Food category");

        // Valid case: expense + Rent
        List<FinancialEntry> expenseRent = model.getEntriesByTypeAndCategory("expense", "Rent");
        assertEquals(1, expenseRent.size(), "Should return 1 expense entry for Rent category");

        // Invalid type
        List<FinancialEntry> invalidType = model.getEntriesByTypeAndCategory("invalid", "Food");
        assertTrue(invalidType.isEmpty(), "Invalid type should return an empty list");

        // Invalid category
        List<FinancialEntry> invalidCategory = model.getEntriesByTypeAndCategory("income", "Vacation");
        assertTrue(invalidCategory.isEmpty(), "Invalid category should return an empty list");

        // Valid type + category with no matches
        List<FinancialEntry> emptyCombo = model.getEntriesByTypeAndCategory("expense", "Utilities");
        assertTrue(emptyCombo.isEmpty(), "Should return empty list if no entries match type and category");
    }

    @Test
    public void testCloseDatabaseConnection() throws Exception {
        // Ensure the connection is open before calling close
        assertNotNull(model.getConnection(), "Connection should not be null before closing");
        assertFalse(model.getConnection().isClosed(), "Connection should be open before calling close");

        // Call the close method
        model.close();

        // Verify the connection is closed
        assertTrue(model.getConnection().isClosed(), "Connection should be closed after calling close");
    }
}
