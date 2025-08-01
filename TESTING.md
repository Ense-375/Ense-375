# Personal Budget Tracker Testing Documentation

### Test Suites and Test Cases

**Note: In order to run any of the tests you must be connected to the database first, check this file for instructions on connecting to the database:** [DB instructions](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/main/java/commands.txt)
1. For running these tests (BudgetViewTest.java, BudgetModelTest.java, FinancialEntry.java) Run them through maven using "mvn test" for them to execute correctly
2. For the remanining tests they can be ran using JUNIT

### Test Documentation
Detailed documentation/breakdown of all testing methodologies refer to these documents: 
1. **Path Testing:** [Path Testing](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Personal%20Budget%20tracker%20path%20Testing.pdf), Implementation: [code](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/test/java/BudgetController_PathTesting.java)
2. **Data flow Testing:** [Data Flow](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Data%20flow%20testing.pdf), Implementation: [code](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/test/java/BudgetController_DataFlowTesting.java)
3. **Integration Testing:** [Integration Testing](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Personal%20Budget%20tracker%20Integration%20testing.pdf), Implementation: [code](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/test/java/IntegrationTest.java)
4. **Boundary value Testiing:** [Boundary value testing ](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Personal%20Budget%20tracker%20Boundary%20value%20analysis.pdf), Implementation: [code](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/test/java/BudgetModelBoundaryTest.java)
5. **Equivalence class Testing:** [Equivalence class testing](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Personal%20Budget%20tracker%20Equivalence%20class%20testing.pdf), Implementation: [code](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/test/java/BudgetModelEquivalenceTest.java)
6. **Decision tables Testing:** [Decision tables testing](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Personal%20Budget%20tracker%20Decision%20tables%20test.pdf), Implementation: [code](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/test/java/BudgetModel_DecisionTableTest.java)
7. **State transition Testing:** [State transition testing](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Personal%20Budget%20tracker%20State%20Transistion%20test.pdf), Implementation: [code](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/test/java/BudgetModel_StateTransistionTest.java)
8. **Use case Testing:** [Use case Testing](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Personal%20Budget%20tracker%20Use%20case%20testing.pdf), Implementation: [code](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/test/java/BudgetModelUseCaseTest.java)

---
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
| 1 | income | income | 800.0 | valid accepted |
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
**Conditions**
| conditions | Rule1 | Rule2 | Rule3 | Rule4 | Rule5 | Rule6 | Rule7 |
| ----------- | ----------- | ----------- | ----------- | ----------- | ----------- | ----------- | ----------- |
| C1 | T | F | F | F | F | F | F |
| C2 | F | T | F | T | T | T | T |
| C3 | F | F | T | F | F | F | F |
| C4 | - | T | - | F | T | T | F |
| C5 | T | T | - | T | F | T | T |
| C6 | T | T | - | F | F | T | F |

**Rules**
| conditions | Rule1 | Rule2 | Rule3 | Rule4 | Rule5 | Rule6 | Rule7 |
| ----------- | ----------- | ----------- | ----------- | ----------- | ----------- | ----------- | ----------- |
| A1 | - | - | X | - | - | - | - |
| A2 | - | - | - | X | X | - | X |
| A3 | X | X | - | - | - | X | - |
| A4 | - | X | - | X | X | X | X |
| A5 | X | X | - | X | X | X | X |
| A6 | X | X | - | X | X | X | X |

### Use case Testing
---
**Actors**
1. U = User
2. S = System

**UC1**
| Steps | Description |
| ----------- | ----------- |
| Main success scenario |  |
| 1    | U: selects addEntry |
| 2    | S: prompts user for type |
| 3    | U: enters type |
| 4    | S: validates type |
| 5    | U: enters category, source & amount |
| 6    | S: validates inputs & adds entry to DB |
| Extensions |  |
| 3a   | U: enters wrong type |
|      | S: displays error and prevents entry |
| 5a   | U: enters invalid category, source, amount |
|      | S: displays error and prevents entry |

**UC2**
| Steps | Description |
| ----------- | ----------- |
| Main success scenario |  |
| 1    | U: selects viewAllEntries |
| 2    | S: displays all Entries in DB |
| Extensions |  |
| 1a   | U: enters viewAllEntries |
|      | S: displays error if no entries present |

**UC3**
| Steps | Description |
| ----------- | ----------- |
| Main success scenario |  |
| 1    | U: selects deleteEntry |
| 2    | S: prompts user for type and ID |
| 3    | U: enters type and ID |
| 4    | S: deletes entry and confirms |
| Extensions |  |
| 3a   | U: enters invalid type or ID |
|      | S: displays error for invalid ID or type |

**UC4**
| Steps | Description |
| ----------- | ----------- |
| Main success scenario |  |
| 1    | U: selects view balance |
| 2    | S: obtains total incoe=me and expenses |
| 3    | S: calculates and displays net balance |

**UC5**
| Steps | Description |
| ----------- | ----------- |
| Main success scenario |  |
| 1    | U: selects view entry by category |
| 2    | S: prompts user for type and category |
| 3    | U: enters type and category |
| 4    | S: validates and searches for entry |
| 5    | S: displays entry |
| Extensions |  |
| 3a   | U: enters invalid type or category |
|      | S: displays error message |
| 5a   | S: no entries found |
|      | S: displays "no entries found" |

