// Last Edited by Tolani on July 20th 2025

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BudgetModelBoundaryTest {
    
    private BudgetModel model;

    @BeforeEach
    public void setUp() {
        model = new BudgetModel();
        model.clearDatabase(); 
    }

    @Test
    public void testCase1() {
        System.out.println("Running test case 1: Amount = -1.0 (min - 1)");
        assertFalse(model.addFinancialEntry("income", "salary", -1.0));
    }

    @Test
    public void testCase2() {
        System.out.println("Running test case 2: Amount = 0.0 (min)");
        assertTrue(model.addFinancialEntry("income", "salary", 0.0));
    }

    @Test
    public void testCase3() {
        System.out.println("Running test case 3: Amount = 1.0 (min + 1)");
        assertTrue(model.addFinancialEntry("income", "salary", 1.0));
    }

    @Test
    public void testCase4() {
        System.out.println("Running test case 4: Amount = 5000.0 (nominal)");
        assertTrue(model.addFinancialEntry("income", "salary", 5000.0));
    }

    @Test
    public void testCase5() {
        System.out.println("Running test case 5: Amount = 9998.0 (max - 1)");
        assertTrue(model.addFinancialEntry("income", "salary", 9998.0));
    }

    @Test
    public void testCase6() {
        System.out.println("Running test case 6: Amount = 9999.0 (max)");
        assertTrue(model.addFinancialEntry("income", "salary", 9999.0));
    }

    @Test
    public void testCase7() {
        System.out.println("Running test case 7: Amount = 10000.0 (max + 1)");
        assertTrue(model.addFinancialEntry("income", "salary", 10000.0)); 
    }

    @Test
    public void testCase8() {
        System.out.println("Running test case 8: Invalid type = 'salary'");
        assertFalse(model.addFinancialEntry("salary", "income", 5000.0));
    }

    @Test
    public void testCase9() {
        System.out.println("Running test case 9: Valid expense 'food'");
        assertTrue(model.addFinancialEntry("expense", "food", 5000.0));
    }

    @Test
    public void testCase10() {
        System.out.println("Running test case 10: Invalid category 'luxury'");
        assertFalse(model.addFinancialEntry("expense", "luxury", 5000.0));
    }

    @Test
    public void testCase11() {
        System.out.println("Running test case 11: Valid income with 'rent' as source");
        assertTrue(model.addFinancialEntry("income", "rent", 5000.0));
    }

    @Test
    public void testCase12() {
        System.out.println("Running test case 12: Valid income with 'other' as source");
        assertTrue(model.addFinancialEntry("income", "other", 5000.0));
    }

    @Test
    public void testCase13() {
        System.out.println("Running test case 13: Valid expense 'transport'");
        assertTrue(model.addFinancialEntry("expense", "transport", 5000.0));
    }

    @Test
    public void testCase14() {
        System.out.println("Running test case 14: Invalid type job");
        assertFalse(model.addFinancialEntry("job", "income", 5000.0));
    }

    @Test
    public void testCase15() {
        System.out.println("Running test case 15: Invalid type 'EXPENSE'");
        assertFalse(model.addFinancialEntry("EXPENSE", "rent", 5000.0));
    }

    @Test
    public void testCase16() {
        System.out.println("Running test case 16: Valid category 'healthcare'");
        assertTrue(model.addFinancialEntry("income", "healthcare", 5000.0));
    }

    @Test
    public void testCase17() {
        System.out.println("Running test case 17: Valid expense 'entertainment'");
        assertTrue(model.addFinancialEntry("expense", "entertainment", 5000.0));
    }

    @Test
    public void testCase18() {
        System.out.println("Running test case 18: Valid income with 'utilities' as source");
        assertTrue(model.addFinancialEntry("income", "utilities", 5000.0));
    }

    @Test
    public void testCase19() {
        System.out.println("Running test case 19: Valid income with 'income' as source");
        assertTrue(model.addFinancialEntry("income", "income", 5000.0));
    }

    @AfterEach
    public void tearDown() {
        model.clearDatabase(); 
    }
}
