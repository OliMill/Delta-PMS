package deltapmsprototype;

import deltapmsprototype.ui.MainApplicationFrame;
import com.hotelmanagement.dao.DataManager; 
import deltapmsprototype.ui.MainApplicationFrame;
import javax.swing.JOptionPane; 

public class DeltaPMSPrototype {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            //attempt to load all data from database
            DataManager.loadDataFromDatabase();
            //load GUI when complete
            java.awt.EventQueue.invokeLater(() -> {
                new MainApplicationFrame().setVisible(true);
            });
            System.out.println(DataManager.getBookings());

        } catch (Exception e) {
            // If any error occurs during connection or loading
            System.err.println("Application failed to start: " + e.getMessage());
            
            // Show a user-friendly error dialog
            JOptionPane.showMessageDialog(
                null,
                "Failed to load application data.\nThe application will now close.\n\nError: " + e.getMessage(), // Message
                "Application Error",
                JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }
    }
}