// Last Edited by Tolani on June 6, 2025

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
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
        List<FinancialEntry> entries = model.getEntries();
        entries.forEach(e -> System.out.println("ID: " + e.getId() + ", Type: " + e.getType() + ", Category: " + e.getCategory()));

        int incomeId = model.getEntries().stream()
        .filter(e -> e.getType().equals("income"))
        .findFirst()
        .map(FinancialEntry::getId)
        .orElseThrow(() -> new AssertionError("No income entry found"));
        int expenseId = model.getEntries().stream()
        .filter(e -> e.getType().equals("expense"))
        .findFirst()
        .map(FinancialEntry::getId)
        .orElseThrow(() -> new AssertionError("No expense entry found"));

        assertTrue(model.deleteEntry("income", incomeId), "Should delete first income entry");
        assertEquals(1, model.getEntries().size(), "Should have 1 entry left after deleting income");

        assertTrue(model.deleteEntry("expense", expenseId), "Should delete the last remaining expense entry");
        assertFalse(model.deleteEntry("income", incomeId), "Should not delete non-existent income entry");
        assertFalse(model.deleteEntry("invalid", incomeId), "Should not delete with invalid type");

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
        model.addFinancialEntry("income", "food", 500.0);
        model.addFinancialEntry("income", "food", 100.0);
        model.addFinancialEntry("expense", "rent", 300.0);
        model.addFinancialEntry("expense", "transport", 200.0);

        // Valid case: income + Food
        List<FinancialEntry> incomeFood = model.getEntriesByTypeAndCategory("income", "food");
        assertEquals(2, incomeFood.size(), "Should return 2 income entries for Food category");

        // Valid case: expense + Rent
        List<FinancialEntry> expenseRent = model.getEntriesByTypeAndCategory("expense", "rent");
        assertEquals(1, expenseRent.size(), "Should return 1 expense entry for Rent category");

        // Invalid type
        List<FinancialEntry> invalidType = model.getEntriesByTypeAndCategory("invalid", "food");
        assertTrue(invalidType.isEmpty(), "Invalid type should return an empty list");

        // Invalid category
        List<FinancialEntry> invalidCategory = model.getEntriesByTypeAndCategory("income", "vacation");
        assertTrue(invalidCategory.isEmpty(), "Invalid category should return an empty list");

        // Valid type + category with no matches
        List<FinancialEntry> emptyCombo = model.getEntriesByTypeAndCategory("expense", "utilities");
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
