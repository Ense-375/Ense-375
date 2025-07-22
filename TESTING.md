# Personal Budget Tracker Testing Documentation

### Test Suites and Test Cases

**Note: In order to run any of the tests you must be connected to the database first, check this file for instructions on connecting to the database** [DB instructions](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/main/java/commands.txt)

### Test Documentation
Detailed breakdown of all testing methodologies conducted on this application provided below: 
1. **Path Testing:** [Path Testing](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Personal%20Budget%20tracker%20path%20Testing.pdf), Implementation: [code](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/test/java/BudgetController_PathTesting.java)
2. **Data flow Testing:** [Data Flow](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Data%20flow%20testing.pdf), Implementation: [code](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/test/java/BudgetController_DataFlowTesting.java)
3. **Integration Testing:** [Integration Testing](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Personal%20Budget%20tracker%20Integration%20testing.pdf), Implementation: [code](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/test/java/IntegrationTest.java)
4. **Boundary value Testiing:** [Boundary value testing ](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Personal%20Budget%20tracker%20Boundary%20value%20analysis.pdf), Implementation: [code](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/test/java/BudgetModelBoundaryTest.java)
5. **Equivalence class Testing:** [Equivalence class testing](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Personal%20Budget%20tracker%20Equivalence%20class%20testing.pdf), Implementation: [code](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/test/java/BudgetModelEquivalenceTest.java)
6. **Decision tables Testing:** [Decision tables testing](), Implementation: [code]()
7. **State transition Testing:** [State transition testing](), Implementation: [code]()
8. **Use case Testing:** [Use case Testing](), Implementation: [code]()

### Boundary value Test
---
To perform boundary value analysis on our application, we will conduct  **robust boundary value analysis**, as it provides a more thorough validation compared to ordinary BVA. since our application handles financial data, focusing on just values within the valid range would not be sufficient so its important that we test both valid and invalid boundaries to avoid logic errors. 

**Assumptions**
1. Minimum amount is $0
2. Maximum amount is $9999 (Assumed for testing)
3. Categories must match predefined list
4. Inputs are case-sensitive

**BVA Table**
| case | amount | Type | categoryOrSource | Expected outcome |
| ----------- | ----------- | ----------- | ----------- | ----------- |
| 1 | -1.0 | income | income | Rejected invalid amount |
| 2	| 0.0 | income | income | Accepted edge case |
| 3 | 1.0 |	income | income | Accepted $1 | 
| 4	| 5000.0 | income | income | Accepted nominal |
| 5	| 9998.0 | income | income | Accepted $9998 |
| 6	| 9999.0 | income | income | Accepted upper bound |
| 7	| 10000.0 | income | income | Reject invalid amount |
| 8	| 5000.0 | Salary | income | Accepted valid Type |
| 9 | 5000.0 | expense | food | Accepted valid category |
| 10 | 	5000.0 | expense | Luxury | Rejected invalid category |
| 11 | 5000.0 | income | rent | Rejected invalid category |
| 12 | 5000.0 | income | other | Rejected invalid category |
| 13 | 5000.0 | expense | transport | Accepted valid category |
| 14 | 5000.0 | job | Income | Rejected invalid type |
| 15 | 5000.0 | EXPENSE | rent | Rejected invalid type |
| 16 | 5000.0 | income | vacation | Accepted valid category |
| 17 | 5000.0 | expense | healthcare | Accepted valid category |
| 18 | 5000.0 | income | utilities | Rejected invalid category |
| 19 | 5000.0 | income | income | Accepted nominal value |

### Equivalence class test
---
Similar to boundary value testing, we’ll need to identify the valid and invalid input classes for each variable. Using equivalence class testing we’ll divide these inputs into sets of valid and invalid values where each set is expected to exhibit similar behaviour.

**ECT Table**
| case | Type | categoryOrSource | Amount | Output |
| ----------- | ----------- | ----------- | ----------- | ----------- |
| 1 | income | income | 800.0 | valid Accepted |
| 2 | INCOME | " " | 3000.0 | invalid type |
| 3 | income | income | 9000.0 | valid accepted |
| 4 | expense | food | 150.0 | valid accepted |
| 5 | 123 | CAR | 10000.0 | invalid rejected |
| 6 | other | rent | 300.0 | invalid type rejected |
| 7 | expense| transport | 0.0 | valid amount |
| 8 | income | income | -50.0 | invalid amount |
| 9 | expense | 98 | 500.0 | invalid | invalid category/source |
| 10 | expense | healthcare | 700.0 | valid amount |
| 11 | income | income | 8000.0 | valid accepted |

### Decision tables test
---






