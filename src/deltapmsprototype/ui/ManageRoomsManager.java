package deltapmsprototype.ui;

import com.hotelmanagement.dao.DataManager;
import com.hotelmanagement.dao.DatabaseConnection;
import com.hotelmanagement.models.Room;
import com.hotelmanagement.models.RoomType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class ManageRoomsManager extends javax.swing.JPanel {

    private List<Room> allRooms = new ArrayList<>();
    private final MainApplicationFrame MainApplication1;

    public ManageRoomsManager(MainApplicationFrame MainApplication) {
        initComponents();
        this.MainApplication1 = MainApplication;
        setupTableCustomization();
        loadRoomsTable();
    }

    private void setupTableCustomization() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Room No", "Type ID", "Bathrooms", "Single Beds", "Double Beds", "Capacity"}
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 1; // Only Type ID is editable; others are derived from Type
            }
        };
        jTable1.setModel(model);

        // Set up JComboBox for Table Editing (Columns 1)
        setupRoomTypeDropdown(jTable1.getColumnModel().getColumn(1));
        
        // Add a listener to detect when a cell is edited
        model.addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                if (column == 1) {
                    try {
                        Object value = model.getValueAt(row, 1);
                        if (value == null) {
                            return;
                        }
                        int newTypeID = Integer.parseInt(value.toString());

                        com.hotelmanagement.models.RoomType foundType = null;
                        for (com.hotelmanagement.models.RoomType rt : com.hotelmanagement.dao.DataManager.getRoomTypes()) {
                            if (rt.getRoomTypeID() == newTypeID) {
                                foundType = rt;
                                break;
                            }
                        }

                        if (foundType != null) {
                            // CREATE COPIES HERE: These are "effectively final"
                            final com.hotelmanagement.models.RoomType finalType = foundType;
                            final int finalRow = row;

                            javax.swing.SwingUtilities.invokeLater(() -> {
                                // Use the final copies inside the lambda
                                model.setValueAt(finalType.getBathrooms(), finalRow, 2);
                                model.setValueAt(finalType.getSingleBeds(), finalRow, 3);
                                model.setValueAt(finalType.getDoubleBeds(), finalRow, 4);
                                model.setValueAt(finalType.getSleepingCapacity(), finalRow, 5);
                            });
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void setupRoomTypeDropdown(javax.swing.table.TableColumn column) {
        JComboBox<Integer> typeCombo = new JComboBox<>();
        // Fetch existing RoomType IDs from DataManager
        for (RoomType rt : DataManager.getRoomTypes()) {
            typeCombo.addItem(rt.getRoomTypeID());
        }
        column.setCellEditor(new DefaultCellEditor(typeCombo));
    }

    private void loadRoomsTable() {
        DataManager.loadDataFromDatabase();
        allRooms = DataManager.getRooms();
        updateTable(allRooms);
    }

    private void updateTable(List<Room> rooms) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        List<RoomType> types = DataManager.getRoomTypes();

        for (Room r : rooms) {
            // Find the matching RoomType object to get details
            RoomType myType = null;
            for (RoomType rt : types) {
                if (rt.getRoomTypeID() == r.getRoomTypeID()) {
                    myType = rt;
                    break;
                }
            }

            model.addRow(new Object[]{
                r.getRoomNo(),
                r.getRoomTypeID(),
                (myType != null) ? myType.getBathrooms() : "N/A",
                (myType != null) ? myType.getSingleBeds() : "N/A",
                (myType != null) ? myType.getDoubleBeds() : "N/A",
                (myType != null) ? myType.getSleepingCapacity() : "N/A"
            });
        }
    }

    private void updateDatabaseAction() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        String sql = "UPDATE Rooms SET RoomTypeID = ? WHERE RoomNo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < model.getRowCount(); i++) {
                int typeId = Integer.parseInt(model.getValueAt(i, 1).toString());
                int roomNo = Integer.parseInt(model.getValueAt(i, 0).toString());

                pstmt.setInt(1, typeId);
                pstmt.setInt(2, roomNo);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            JOptionPane.showMessageDialog(this, "Room updates saved.");
            loadRoomsTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

                              
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(89, 4, 20));
        jPanel1.setForeground(new java.awt.Color(89, 4, 20));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/deltapmsprototype/ui/components/DeltaPMSlogo_nobackground.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel20.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(242, 68, 29)));
        jPanel20.setPreferredSize(new java.awt.Dimension(184, 2));

        jLabel2.setText("Room Search");

        jButton1.setText("Refresh Table");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Execute Query");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Commit changes");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Add new Room");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Delete Room");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Add new Room type");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1686, Short.MAX_VALUE)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 1686, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String query = jTextField1.getText().trim();
        List<Room> filtered = new ArrayList<>();
        for (Room r : allRooms) {
            if (String.valueOf(r.getRoomNo()).contains(query) || 
                String.valueOf(r.getRoomTypeID()).contains(query)) {
                filtered.add(r);
            }
        }
        updateTable(filtered);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTextField1.setText("");
        loadRoomsTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to save changes to the database?",
                "Confirm Save", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            updateDatabaseAction();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JTextField roomNoField = new JTextField();
        JComboBox<Integer> typeCombo = new JComboBox<>();
        
        for (RoomType rt : DataManager.getRoomTypes()) {
            typeCombo.addItem(rt.getRoomTypeID());
        }

        JPanel panel = new JPanel(new java.awt.GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Room Number:"));
        panel.add(roomNoField);
        panel.add(new JLabel("Room Type ID:"));
        panel.add(typeCombo);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Room", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String sql = "INSERT INTO Rooms (RoomNo, RoomTypeID) VALUES (?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setInt(1, Integer.parseInt(roomNoField.getText().trim()));
                pstmt.setInt(2, (Integer)typeCombo.getSelectedItem());
                
                pstmt.executeUpdate();
                loadRoomsTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding room: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            return;
        }

        String roomNo = jTable1.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Delete Room " + roomNo + "?", "Confirm", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Rooms WHERE RoomNo = ?")) {
                pstmt.setString(1, roomNo);
                pstmt.executeUpdate();
                loadRoomsTable();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Delete Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // Create Inputs
        javax.swing.JSpinner bathSpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 0, 10, 1));
        javax.swing.JSpinner singleSpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));
        javax.swing.JSpinner doubleSpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));

        javax.swing.JCheckBox checkDesk = new javax.swing.JCheckBox("Desk");
        javax.swing.JCheckBox checkBath = new javax.swing.JCheckBox("Bath");
        javax.swing.JCheckBox checkShower = new javax.swing.JCheckBox("Shower");
        javax.swing.JCheckBox checkTV = new javax.swing.JCheckBox("TV");
        javax.swing.JCheckBox checkCoffee = new javax.swing.JCheckBox("Coffee Machine");
        javax.swing.JCheckBox checkSafe = new javax.swing.JCheckBox("Deposit Box");

        // Build the UI Panel
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 2, 10, 10));
        panel.add(new javax.swing.JLabel("Bathrooms:"));
        panel.add(bathSpinner);
        panel.add(new javax.swing.JLabel("Single Beds:"));
        panel.add(singleSpinner);
        panel.add(new javax.swing.JLabel("Double Beds:"));
        panel.add(doubleSpinner);
        panel.add(checkTV);
        panel.add(checkDesk);
        panel.add(checkShower);
        panel.add(checkBath);
        panel.add(checkCoffee);
        panel.add(checkSafe);

        int result = javax.swing.JOptionPane.showConfirmDialog(this, panel,
                "Create New Room Type", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);

        if (result == javax.swing.JOptionPane.OK_OPTION) {
            //sql structure

            String sql = "INSERT INTO RoomType (TV, SingleBeds, Shower, DoubleBeds, Desk, DepositBOX, CoffeeMachine, Bathrooms, Bath) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (java.sql.Connection conn = com.hotelmanagement.dao.DatabaseConnection.getConnection(); java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Map inputs to the specific MySQL column order provided
                pstmt.setBoolean(1, checkTV.isSelected());
                pstmt.setInt(2, (int) singleSpinner.getValue());
                pstmt.setBoolean(3, checkShower.isSelected());
                pstmt.setInt(4, (int) doubleSpinner.getValue());
                pstmt.setBoolean(5, checkDesk.isSelected());
                pstmt.setBoolean(6, checkSafe.isSelected());
                pstmt.setBoolean(7, checkCoffee.isSelected());
                pstmt.setInt(8, (int) bathSpinner.getValue());
                pstmt.setBoolean(9, checkBath.isSelected());

                pstmt.executeUpdate();

                // Sync with DataManager and UI
                com.hotelmanagement.dao.DataManager.loadDataFromDatabase();
                setupTableCustomization(); // Refresh dropdowns with the new Type ID

                javax.swing.JOptionPane.showMessageDialog(this, "New Room Type added successfully!");

            } catch (java.sql.SQLException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
