// Last Edited by Tolani on July 22nd 2025

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class BudgetModel_DecisionTableTest {
    private BudgetModel model;

    @BeforeEach
    public void setUp() {
        model = new BudgetModel();
        model.clearDatabase(); 
    }

    @Test
    public void rule1(){
        System.out.println("Rule 1: Adding valid income");
        assertTrue(model.addFinancialEntry("income", "income", 1000.0));
    }

    @Test
    public void rule2(){
        System.out.println("Rule 2: Adding valid expense");
        assertTrue(model.addFinancialEntry("expense", "utilities", 300.0));
    }

    @Test
    public void rule3(){
        System.out.println("Rule 3: Adding invalid type");
        assertFalse(model.addFinancialEntry("investment", "crypto", 800.0));
    }

    @Test
    public void rule4(){
        System.out.println("Rule 4: Adding invalid category");
        assertFalse(model.addFinancialEntry("expense", "luxury", 700.0));
    }

    @Test
    public void rule5(){
        System.out.println("Rule 5: Reject negative income");
        assertFalse(model.addFinancialEntry("income", "income", -8000.0));
    }

    @Test
    public void rule6(){
        System.out.println("Rule 6: Adding valid expense");
        assertTrue(model.addFinancialEntry("expense", "transport", 330.0));
    }

    @Test
    public void rule7(){
        System.out.println("Rule 7: expense with invalid category");
        assertFalse(model.addFinancialEntry("expense", "util", 10.0));
    }

    @AfterEach
    public void tearDown() {
        model.clearDatabase(); 
    }
}
