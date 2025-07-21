# Personal Budget Tracker Testing Documentation

### Test Suites and Test Cases

**Note: In order to run any of the tests you must be connected to the database first, check this file for instructions on connecting to the database** [DB instructions](https://github.com/Ense-375/Ense-375/blob/main/Prototype3/src/main/java/commands.txt)

### Test Documentation
Detailed breakdown of all testing methodologies conducted on this application provided below: 
1. [Path Testing](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Personal%20Budget%20tracker%20path%20Testing.pdf)
2. [Data Flow](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Data%20flow%20testing.pdf)
3. [Integration Testing](https://github.com/Ense-375/Ense-375/blob/main/Documents/TestDocs/Personal%20Budget%20tracker%20Integration%20testing.pdf)
4. [Boundary value testing ]()
5. [Equivalence class testing]()
6. [Decision tables testing]()
7. [State transition testing]()
8. [Use case Testing]()




### Boundary value Test

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








