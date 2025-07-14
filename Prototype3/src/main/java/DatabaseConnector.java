

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3307/dsj361";
    private static final String USER = "dsj361";
    private static final String PASSWORD = "MyData@uni25";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection conn = connect()) {
            System.out.println("Connected successfully to university DB!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// public class DatabaseConnectorTest {
//     public static void main(String[] args) {
//         BudgetModel model = new BudgetModel();
//         if (model.getConnection() != null) {
//             System.out.println("✅ Connection established.");
//         } else {
//             System.out.println("❌ Failed to connect.");
//         }
//     }
// }