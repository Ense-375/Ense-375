import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class FinancialEntryTest {

    @Test
    public void testConstructorAndGetters() {
        FinancialEntry entry = new FinancialEntry(1, "income", "Salary", 2000.0);
        assertEquals(1, entry.getId());
        assertEquals("income", entry.getType());
        assertEquals("Salary", entry.getCategory());
        assertEquals(2000.0, entry.getAmount());
    }

    @Test
    public void testSetters() {
        FinancialEntry entry = new FinancialEntry(2, "expense", "Food", 100.0);
        entry.setType("income");
        entry.setCategory("Bonus");
        entry.setAmount(500.0);

        assertEquals("income", entry.getType());
        assertEquals("Bonus", entry.getCategory());
        assertEquals(500.0, entry.getAmount());
    }

    @Test
    public void testNegativeAmount() {
        FinancialEntry entry = new FinancialEntry(3, "expense", "Utilities", -100.0);
        assertEquals(-100.0, entry.getAmount());
        // Depending on your design, you might want to assert validation or log warning instead
    }

    @Test
    public void testToStringFormat() {
        FinancialEntry entry = new FinancialEntry(4, "income", "Gift", 300.0);
        String result = entry.toString();
        assertTrue(result.contains("income"));
        assertTrue(result.contains("Gift"));
        assertTrue(result.contains("300.0"));
    }

    @Test
    public void testEquality() {
        FinancialEntry e1 = new FinancialEntry(5, "income", "Freelance", 750.0);
        FinancialEntry e2 = new FinancialEntry(5, "income", "Freelance", 750.0);
        assertEquals(e1.getId(), e2.getId());
        assertEquals(e1.getType(), e2.getType());
        assertEquals(e1.getCategory(), e2.getCategory());
        assertEquals(e1.getAmount(), e2.getAmount());
    }
}