package deltapmsprototype;

// **NEW:** Import the necessary Java SQL classes
import java.sql.Connection; 
import java.sql.SQLException;

import com.hotelmanagement.dao.DatabaseConnection;

public class DeltaPMSPrototype {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // **FIX 1:** Declare and initialize the Connection object here
        Connection connection = null; 

        // Create and show the main application frame
        java.awt.EventQueue.invokeLater(() -> {
            // **FIX 2:** You need to define or import the MainApplicationFrame class.
            // Assuming this class exists elsewhere in your project:
            // new MainApplicationFrame().setVisible(true); 
        });

        // The try-catch block for database connection check
        try {
            // 1. Attempt to get the connection
            connection = DatabaseConnection.getConnection();
            
            // ... (rest of the connection check code)
            
            // 2. If no exception is thrown, the connection was successful!
            System.out.println("✅ Database Connection SUCCESSFUL!");
            
            // Optional: Check connection validity (best practice, but adds a small delay)
            if (connection.isValid(2)) { 
                System.out.println("✅ Connection is actively valid and ready for use.");
            } else {
                System.out.println("⚠️ Connection was established but may not be valid (e.g., server closed connection quickly).");
            }
            

        } catch (SQLException e) {
            // 3. If an exception is thrown, the connection failed.
            System.err.println("❌ Database Connection FAILED!");
            
            // Print the error details to diagnose the exact issue
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            
            // The message is the most helpful for quick diagnosis:
            System.err.println("Error Message: " + e.getMessage());

        } finally {
            // 4. Always close the connection in the finally block
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
}