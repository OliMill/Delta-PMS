/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package deltapmsprototype.ui;

/**
 *
 * @author Oli
 */
public class CommunicateCustomersStaff extends javax.swing.JPanel {

    /**
     * Creates new form CommunicateCustomersStaff
     */
    private final javax.swing.DefaultListModel<String> listModel;
    private final MainApplicationFrame MainApplication1;
    
    public CommunicateCustomersStaff(MainApplicationFrame MainApplication) {
        initComponents();
        
        MainApplication1 = MainApplication;
        listModel = new javax.swing.DefaultListModel<>();
        customerList.setModel(listModel);

        

    }

    /**
     * Opens a popup window to search and select customers from the database.
     */
    private void showSearchCustomerDialog() {
        // Setup the Dialog
        javax.swing.JDialog dialog = new javax.swing.JDialog(
                (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this),
                "Search Customer",
                true //blocks the main window
        );
        dialog.setSize(600, 400);
        dialog.setLayout(new java.awt.BorderLayout(10, 10));

        // Search Bar
        javax.swing.JTextField searchField = new javax.swing.JTextField();
        searchField.setBorder(javax.swing.BorderFactory.createTitledBorder("Type name or email to search..."));

        // Results Table
        javax.swing.table.DefaultTableModel tableModel = new javax.swing.table.DefaultTableModel(
                new Object[]{"ID", "First Name", "Last Name", "Email"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        javax.swing.JTable resultTable = new javax.swing.JTable(tableModel);

        // Add Button
        javax.swing.JButton selectBtn = new javax.swing.JButton("Add Selected Customer");
        selectBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        selectBtn.setBackground(new java.awt.Color(89, 4, 20));
        selectBtn.setForeground(java.awt.Color.WHITE);

        // Update table when user types
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                updateSearchTable(tableModel, searchField.getText());
            }
        });

        // Add selected person when button clicked
        selectBtn.addActionListener(e -> {
            int row = resultTable.getSelectedRow();
            if (row != -1) {
                String fName = (String) tableModel.getValueAt(row, 1);
                String lName = (String) tableModel.getValueAt(row, 2);
                String email = (String) tableModel.getValueAt(row, 3);

                // Format: "John Doe <john@email.com>"
                String listEntry = fName + " " + lName + " <" + email + ">";

                if (!listModel.contains(listEntry)) {
                    listModel.addElement(listEntry);
                }
                dialog.dispose(); // Close popup
            }
        });

        // Layout Components
        dialog.add(searchField, java.awt.BorderLayout.NORTH);
        dialog.add(new javax.swing.JScrollPane(resultTable), java.awt.BorderLayout.CENTER);
        dialog.add(selectBtn, java.awt.BorderLayout.SOUTH);

        // Load initial data
        updateSearchTable(tableModel, "");

        dialog.setLocationRelativeTo(this); // Center on screen
        dialog.setVisible(true);
    }

    /**
     * Queries the database and updates the popup table.
     */
    private void updateSearchTable(javax.swing.table.DefaultTableModel model, String query) {
        model.setRowCount(0); // Clear existing rows in the UI table

        // Convert the search query to lowercase once for efficiency
        String lowerQuery = query.toLowerCase();

        // Get the full list from your DataManager
        com.hotelmanagement.dao.DataManager.loadDataFromDatabase();
        java.util.List<com.hotelmanagement.models.Customer> allCustomers = com.hotelmanagement.dao.DataManager.getCustomers();

        // Standard for-loop to filter and add rows
        for (com.hotelmanagement.models.Customer c : allCustomers) {

            // Check if the query matches First Name, Last Name, or Email
            boolean matchesFirstName = c.getFirstName().toLowerCase().contains(lowerQuery);
            boolean matchesLastName = c.getLastName().toLowerCase().contains(lowerQuery);
            boolean matchesEmail = c.getEmail().toLowerCase().contains(lowerQuery);

            if (matchesFirstName || matchesLastName || matchesEmail) {
                model.addRow(new Object[]{
                    c.getCustomerID(),
                    c.getFirstName(),
                    c.getLastName(),
                    c.getEmail()
                });
            }
        }
    }

    /**
     * Loops through the JList and sends the email to everyone.
     */
    private void performBulkEmail() {
        String subject = subjectHeader.getText();
        String body = "<html><body>" + emailBody.getText().replace("\n", "<br>") + "</body></html>";
        
        if (listModel.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please add customers to the list first.");
            return;
        }
        if (subject.isEmpty() || body.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Subject and Body cannot be empty.");
            return;
        }

        // Disable button so they don't click twice
        sendEmailButton.setEnabled(false);
        sendEmailButton.setText("Sending...");

        new Thread(() -> {
            int successCount = 0;

            for (int i = 0; i < listModel.size(); i++) {
                String entry = listModel.get(i);
                try {
                    // Extract email from "Name <email@domain.com>"
                    int start = entry.indexOf("<") + 1;
                    int end = entry.indexOf(">");

                    if (start > 0 && end > start) {
                        String email = entry.substring(start, end);
                        com.deltapms.utils.EmailService.sendEmail(email, subject, body);
                        successCount++;
                    }

                    // Small pause to be polite to the server
                    Thread.sleep(500);

                } catch (Exception e) {
                    System.err.println("Failed to send to: " + entry);
                }
            }

            // UI Updates must happen on the Event Dispatch Thread
            final int finalCount = successCount;
            javax.swing.SwingUtilities.invokeLater(() -> {
                javax.swing.JOptionPane.showMessageDialog(this, "Sent " + finalCount + " emails successfully!");
                sendEmailButton.setText("Send Email");
                sendEmailButton.setEnabled(true);
            });

        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        subjectHeader = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        emailBody = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        customerList = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        sendEmailButton = new javax.swing.JButton();
        addCustomerButton = new javax.swing.JButton();
        removeCustomerButton = new javax.swing.JButton();
        removeAllCustomerButton = new javax.swing.JButton();
        AddAllCustomerButton = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(89, 4, 20));
        jPanel1.setForeground(new java.awt.Color(89, 4, 20));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/deltapmsprototype/ui/components/DeltaPMSlogo_nobackground.png"))); // NOI18N

        jPanel9.setBackground(new java.awt.Color(89, 4, 20));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        jButton14.setBackground(new java.awt.Color(104, 21, 35));
        jButton14.setFont(new java.awt.Font("Arial Narrow", 0, 24)); // NOI18N
        jButton14.setForeground(new java.awt.Color(242, 68, 29));
        jButton14.setText("Return");
        jButton14.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel9.add(jButton14, gridBagConstraints);

        jPanel11.setBackground(new java.awt.Color(89, 4, 20));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel9.add(jPanel11, gridBagConstraints);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 621, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(374, 374, 374))
        );

        jPanel2.setLayout(new java.awt.GridBagLayout());

        subjectHeader.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        subjectHeader.setToolTipText("");
        subjectHeader.setBorder(javax.swing.BorderFactory.createTitledBorder("Subject Header"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 0.5;
        jPanel2.add(subjectHeader, gridBagConstraints);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(0, 0));

        emailBody.setColumns(20);
        emailBody.setRows(5);
        emailBody.setBorder(javax.swing.BorderFactory.createTitledBorder("Email Body"));
        jScrollPane1.setViewportView(emailBody);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 2.0;
        jPanel2.add(jScrollPane1, gridBagConstraints);

        customerList.setPreferredSize(new java.awt.Dimension(1, 1));
        jScrollPane3.setViewportView(customerList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 2.0;
        jPanel2.add(jScrollPane3, gridBagConstraints);

        jPanel3.setPreferredSize(new java.awt.Dimension(1, 1));
        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWidths = new int[] {0, 15, 0, 15, 0, 15, 0, 15, 0};
        jPanel3Layout.rowHeights = new int[] {0, 10, 0};
        jPanel3.setLayout(jPanel3Layout);

        sendEmailButton.setText("Send Email");
        sendEmailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendEmailButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(sendEmailButton, gridBagConstraints);

        addCustomerButton.setText("Add Customer");
        addCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCustomerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        jPanel3.add(addCustomerButton, gridBagConstraints);

        removeCustomerButton.setText("Remove Customer");
        removeCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCustomerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        jPanel3.add(removeCustomerButton, gridBagConstraints);

        removeAllCustomerButton.setText("Remove All Customer");
        removeAllCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllCustomerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        jPanel3.add(removeAllCustomerButton, gridBagConstraints);

        AddAllCustomerButton.setText("Add All Customers");
        AddAllCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddAllCustomerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        jPanel3.add(AddAllCustomerButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 0.5;
        jPanel2.add(jPanel3, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void removeCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeCustomerButtonActionPerformed
        // Get Selected Indices (allows removing multiple at once)
        int[] selectedIndices = customerList.getSelectedIndices();
        if (selectedIndices.length > 0) {
            // Remove from the bottom up to avoid index shifting errors
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                listModel.remove(selectedIndices[i]);
            }
        }
    }//GEN-LAST:event_removeCustomerButtonActionPerformed

    private void addCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCustomerButtonActionPerformed
        showSearchCustomerDialog();
    }//GEN-LAST:event_addCustomerButtonActionPerformed

    private void removeAllCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAllCustomerButtonActionPerformed
        listModel.clear();
    }//GEN-LAST:event_removeAllCustomerButtonActionPerformed

    private void AddAllCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddAllCustomerButtonActionPerformed
        // Confirm before adding everyone
        int choice = javax.swing.JOptionPane.showConfirmDialog(this,
                "Are you sure you want to add ALL customers from the current list?",
                "Bulk Add Warning", javax.swing.JOptionPane.YES_NO_OPTION);

        if (choice == javax.swing.JOptionPane.YES_OPTION) {
            listModel.clear();

            // Use the DataManager's in-memory list
            com.hotelmanagement.dao.DataManager.loadDataFromDatabase();
            java.util.List<com.hotelmanagement.models.Customer> allCustomers = com.hotelmanagement.dao.DataManager.getCustomers();

            for (com.hotelmanagement.models.Customer c : allCustomers) {
                String entry = c.getFirstName() + " " + c.getLastName() + " <" + c.getEmail() + ">";
                listModel.addElement(entry);
            }
        }
    }//GEN-LAST:event_AddAllCustomerButtonActionPerformed

    private void sendEmailButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendEmailButtonActionPerformed

        if (listModel.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "The recipient list is empty. Please add customers first.",
                    "No Recipients",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Show the Confirmation Popup
        int recipientCount = listModel.getSize();
        int choice = javax.swing.JOptionPane.showConfirmDialog(this,
                "You are about to send this email to " + recipientCount + " customer(s).\n"
                + "Are you sure you want to proceed?",
                "Confirm Bulk Email",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE);

        // 3. If they clicked 'Yes', run the email logic
        if (choice == javax.swing.JOptionPane.YES_OPTION) {
            performBulkEmail();
        }
    }//GEN-LAST:event_sendEmailButtonActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        MainApplication1.switcher.returnPanel();
    }//GEN-LAST:event_jButton14ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddAllCustomerButton;
    private javax.swing.JButton addCustomerButton;
    private javax.swing.JList<String> customerList;
    private javax.swing.JTextArea emailBody;
    private javax.swing.JButton jButton14;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton removeAllCustomerButton;
    private javax.swing.JButton removeCustomerButton;
    private javax.swing.JButton sendEmailButton;
    private javax.swing.JTextField subjectHeader;
    // End of variables declaration//GEN-END:variables
}
