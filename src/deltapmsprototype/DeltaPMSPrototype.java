package deltapmsprototype;

import deltapmsprototype.ui.MainApplicationFrame;
import com.hotelmanagement.dao.DataManager; 
import deltapmsprototype.ui.MainApplicationFrame;
import java.time.LocalDate;
import javax.swing.JOptionPane; 
/*
* EXAMPLE CUSTOMER LOGINS FOR TESTING
*1	James	Williams	1978-05-10	james.w@email.com	jwilliams	cust_hash_1
*2	Maria	Garcia	1985-11-23	maria.g@email.com	mgarcia	cust_hash_2
*EXAMPLE STAFF LOGINS FOR TESTING
*1	John	Smith	1985-03-15	john.smith@hotel.com	jsmith	hashed_pass_1	Manager
*2	Sarah	Johnson	1990-07-22	sarah.j@hotel.com	sjohnson	hashed_pass_2	Receptionist
*/
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
            System.out.print(DataManager.getAvailableRooms(LocalDate.parse("2025-12-01"), LocalDate.parse("2025-12-02")));

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