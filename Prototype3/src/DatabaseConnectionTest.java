import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    private static final String DB_URL = "jdbc:mysql://142.3.24.123:44445/budget_tracker_db";
    private static final String DB_USER = "budget";
    private static final String DB_PASSWORD = "Budget1";
    // This class tests the connection to the MySQL database for the budget tracker application.

    public static void main(String[] args) {
        System.out.println("Attempting to connect to the database...");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("✅ Connection successful!");
        } catch (SQLException e) {
            System.err.println("❌ Connection failed!");
            e.printStackTrace();
        }
    }
}