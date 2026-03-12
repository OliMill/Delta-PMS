package deltapmsprototype;

import com.hotelmanagement.dao.DataManager; 
import deltapmsprototype.ui.MainApplicationFrame;
import javax.swing.JOptionPane; 
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialDarkerIJTheme;
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
                FlatMTMaterialDarkerIJTheme.setup();
            } catch (Exception e) {
                System.err.println("Couldn't load FlatLaf: " + e.getMessage());
            }

            java.awt.EventQueue.invokeLater(() -> {
                new MainApplicationFrame().setVisible(true);
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