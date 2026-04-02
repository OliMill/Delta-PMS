package deltapmsprototype;

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

            try {
                // Load the theme

                javax.swing.UIManager.put("TitlePane.unifiedBackground", false);
                javax.swing.UIManager.put("TitlePane.background", new javax.swing.plaf.ColorUIResource(89, 4, 20));
                javax.swing.UIManager.put("TitlePane.foreground", new javax.swing.plaf.ColorUIResource(255, 255, 255));
                javax.swing.UIManager.put("TitlePane.buttonHoverBackground", new javax.swing.plaf.ColorUIResource(242, 68, 29));
                
            } catch (Exception e) {
                System.err.println("Couldn't load FlatLaf: " + e.getMessage());
            }

            java.awt.EventQueue.invokeLater(() -> {
                MainApplicationFrame frame = new MainApplicationFrame();
                frame.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
                frame.setVisible(true);
            });

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