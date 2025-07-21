// Last Edited by Tolani on July 21st 2025

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BudgetModelEquivalenceTest {
    private BudgetModel model;

    @BeforeEach
    public void setUp() {
        model = new BudgetModel();
        model.clearDatabase(); 
    }

    @Test
    public void testCase1() {
        System.out.println("case 1: valid case income");
        assertTrue(model.addFinancialEntry("income", "income", 800.0));
    }

    @Test
    public void testCase2() {
        System.out.println("case 2: invalid case income");
        assertFalse(model.addFinancialEntry("INCOME", " ", 3000.0));
    }

    @Test
    public void testCase3(){
        System.out.println("case 3: valid case income");
        assertTrue(model.addFinancialEntry("income", "income", 9000.0));
    }

    @Test
    public void testCase4(){
        System.out.println("case 4: valid case expense");
        assertTrue(model.addFinancialEntry("expense", "food", 150.0));
    }

    @Test
    public void testCase5(){
        System.out.println("case 5: invalid type/category case");
        assertFalse(model.addFinancialEntry("123", "CAR", 10000.0));
    }

    @Test
    public void testCase6(){
        System.out.println("case 6: invalid case");
        assertFalse(model.addFinancialEntry("other", "rent", 300.0));
    }

    @Test
    public void testCase7(){
        System.out.println("case 7: invalid amount case (0)");
        assertTrue(model.addFinancialEntry("expense", "transport", 0.0));
    }

    @Test
    public void testCase8(){
        System.out.println("case 8: invalid amount case (-50.0)");
        assertFalse(model.addFinancialEntry("income", "income", -50.0));
    }

    @Test
    public void testCase9(){
        System.out.println("case 9: invalid category/source case");
        assertFalse(model.addFinancialEntry("expense", "98", 500.0));
    }

    @Test
    public void testCase10(){
        System.out.println("case 10: valid amount case");
        assertTrue(model.addFinancialEntry("expense", "healthcare", 700.0));
    }

    @Test
    public void testCase11(){
        System.out.println("case 11: valid case");
        assertTrue(model.addFinancialEntry("income", "income", 8000.0));
    }

    @AfterEach
    public void tearDown() {
        model.clearDatabase();
    }
}
