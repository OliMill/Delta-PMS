package deltapmsprototype.ui;

import com.hotelmanagement.dao.DataManager;
import com.hotelmanagement.models.Room;
import com.hotelmanagement.models.RoomType;
import deltapms.session.UserSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.hotelmanagement.models.Customer;
import java.util.ArrayList;
import java.util.List;

public class NewBookingCustomer extends javax.swing.JPanel {

    private final MainApplicationFrame MainApplication;
    private List<Room> currentAvailableRooms = new ArrayList<>();
    private int selectedRoomRow = -1;
    private com.toedter.calendar.JCalendar dateCalendar;
    private javax.swing.JPopupMenu calendarPopup;
    private com.toedter.calendar.JCalendar dateCalendar2;
    private javax.swing.JPopupMenu calendarPopup2;
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    int finalSelectedId = -1;
    
    public NewBookingCustomer(MainApplicationFrame MainApplication) {
        initComponents();
        this.MainApplication = MainApplication;

        // Add mouse click listeners for calendar popups
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        // Add action listener for search button
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        // Add mouse listener for confirm button
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        // Check if user is logged in
        // CALENDAR INITIALIZATION LOGIC START
        dateCalendar = new com.toedter.calendar.JCalendar();
        calendarPopup = new javax.swing.JPopupMenu();

        // 1. Add the JCalendar to the JPopupMenu
        calendarPopup.add(dateCalendar);

        // Set initial button text to today's date
        jButton2.setText("Select Date: " + DATE_FORMAT.format(new java.util.Date()));

        // 2. Add the listener to handle date selection
        dateCalendar.addPropertyChangeListener("day", new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if ("calendar".equals(evt.getPropertyName())) {
                    java.util.Date selectedDate = dateCalendar.getDate();

                    // Update the button's text
                    jButton2.setText("Selected Date: " + DATE_FORMAT.format(selectedDate));

                    // Hide the popup
                    calendarPopup.setVisible(false);
                }
            }
        });

        dateCalendar2 = new com.toedter.calendar.JCalendar();
        calendarPopup2 = new javax.swing.JPopupMenu();

        // 1. Add the JCalendar to the JPopupMenu
        calendarPopup2.add(dateCalendar2);

        // Set initial button text to tomorrow's date
        Date tomorrow = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        jButton3.setText("Select Date: " + DATE_FORMAT.format(tomorrow));

        // 2. Add the listener to handle date selection
        dateCalendar2.addPropertyChangeListener("day", new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if ("calendar".equals(evt.getPropertyName())) {
                    java.util.Date selectedDate2 = dateCalendar2.getDate();

                    // Update the button's text
                    jButton3.setText("Selected Date: " + DATE_FORMAT.format(selectedDate2));

                    // Hide the popup
                    calendarPopup2.setVisible(false);
                }
            }
        });

        // Initialize table with proper column names
        initializeTable();
    }

    private void initializeTable() {
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.getTableHeader().setResizingAllowed(false);
        
        String[] columnNames = {
            "Room No",
            "Room Type",
            "Bathrooms",
            "Single Beds",
            "Double Beds",
            "Desk",
            "Bath",
            "Shower",
            "TV",
            "Coffee",
            "Safe"
        };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Set proper column classes for boolean columns
                if (columnIndex >= 5 && columnIndex <= 10) {
                    return Boolean.class;
                }
                return Object.class;
            }
        };

        jTable1.setModel(model);
        jTable1.setRowHeight(25);
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRoomRow = jTable1.getSelectedRow();
                jButton4.setEnabled(selectedRoomRow >= 0);
            }
        });

        // Initially disable confirm button until a room is selected
        jButton4.setEnabled(false);
    }

    private void populateTableWithRooms(List<Room> rooms) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing rows

        if (rooms.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No rooms found matching your criteria.",
                    "No Results",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Room room : rooms) {
            // Get room type details
            RoomType type = getRoomTypeById(room.getRoomTypeID());
            if (type != null) {
                Object[] row = new Object[]{
                    room.getRoomNo(),
                    getRoomTypeName(type),
                    type.getBathrooms(),
                    type.getSingleBeds(),
                    type.getDoubleBeds(),
                    type.isHasDesk(),
                    type.isHasBath(),
                    type.isHasShower(),
                    type.isHasTV(),
                    type.isHasCoffee(),
                    type.isHasDepositBox()
                };
                model.addRow(row);
            }
        }

        currentAvailableRooms = rooms;
        JOptionPane.showMessageDialog(this,
                "Found " + rooms.size() + " available rooms.",
                "Search Results",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private RoomType getRoomTypeById(int typeId) {
        List<RoomType> roomTypes = DataManager.getRoomTypes();
        for (RoomType type : roomTypes) {
            if (type.getRoomTypeID() == typeId) {
                return type;
            }
        }
        return null;
    }

    private String getRoomTypeName(RoomType type) {
        if (type == null) {
            return "Unknown";
        }

        return switch (type.getRoomTypeID()) {
            case 1 ->
                "Standard";
            case 2 ->
                "Deluxe";
            case 3 ->
                "Family Suite";
            case 4 ->
                "Business Suite";
            case 5 ->
                "Executive Suite";
            case 6 ->
                "Economy";
            case 7 ->
                "Penthouse";
            default ->
                "Type " + type.getRoomTypeID();
        };
    }

    private LocalDate convertDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {
        int x = 0;
        int y = jButton2.getHeight();
        // Show the popup relative to jButton2
        calendarPopup.show(jButton2, x, y);
    }

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {
        int x = 0;
        int y = jButton3.getHeight();
        // Show the popup relative to jButton3
        calendarPopup2.show(jButton3, x, y);
    }

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {
        int customerId = -1;
        if (selectedRoomRow >= 0 && selectedRoomRow < currentAvailableRooms.size()) {
            Room selectedRoom = currentAvailableRooms.get(selectedRoomRow);

            // Get selected dates
            LocalDate checkIn = convertDate(dateCalendar.getDate());
            LocalDate checkOut = convertDate(dateCalendar2.getDate());

            // Validate dates
            if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
                JOptionPane.showMessageDialog(this,
                        "Check-out date must be after check-in date.",
                        "Invalid Dates",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate number of nights
            long nights = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);

            // Get room number
            int roomNo = selectedRoom.getRoomNo();

            // Get room type name for display
            RoomType roomType = getRoomTypeById(selectedRoom.getRoomTypeID());
            String roomTypeName = roomType != null ? getRoomTypeName(roomType) : "Unknown";

            // Get current date for DateMade
            LocalDate dateMade = LocalDate.now();

            // Calculate price based on room capacity
            double pricePerNight = 50.0;
            if (roomType != null) {
                // Using standard math based on bed counts from your RoomType class
                pricePerNight += (roomType.getDoubleBeds() * 40.0) + (roomType.getSingleBeds() * 20.0);
            }
            double totalPrice = pricePerNight * nights;

            //if current user is member of staff confirm which user theyre booking for:
            if ("Staff".equals(deltapms.session.UserSession.getUserRole()) || "Manager".equals(deltapms.session.UserSession.getUserRole())) {

                String searchName = JOptionPane.showInputDialog(this, "Enter Customer First or Last Name:");

                if (searchName != null && !searchName.trim().isEmpty()) {
                    String query = searchName.toLowerCase();
                    List<Customer> foundCustomers = new ArrayList<>();

                    for (Customer c : DataManager.getCustomers()) {
                        if (c.getFirstName().toLowerCase().contains(query) || c.getLastName().toLowerCase().contains(query)) {
                            foundCustomers.add(c);
                        }
                    }

                    if (foundCustomers.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No users found.");
                        return;
                    } else {
                        Object[] names = new Object[foundCustomers.size()];
                        for (int i = 0; i < foundCustomers.size(); i++) {
                            Customer c = foundCustomers.get(i);
                            names[i] = c.getCustomerID() + ": " + c.getFirstName() + " " + c.getLastName();
                        }

                        Object selection = JOptionPane.showInputDialog(this, "Select User:", "Confirm",
                                JOptionPane.QUESTION_MESSAGE, null, names, names[0]);

                        if (selection != null) {
                            // Extract just the ID from the String "123: John Doe"
                            String selectedString = (String) selection;
                            String idPart = selectedString.split(":")[0];
                            customerId = Integer.parseInt(idPart);

                        } else {
                            return;
                        }
                    }
                } else {
                    return;
                }
            } else {
                //Get current user ID from UserSession
                customerId = deltapms.session.UserSession.getUserId();

            }

            // Show confirmation dialog with ALL booking details
            int confirm;
            confirm = JOptionPane.showConfirmDialog(this,
                    String.format("""
                                                            CONFIRM BOOKING DETAILS
                                                                                                                    ROOM INFORMATION:
                                                              Room Number: %d
                                                              Room Type: %s
                                                                                                                    STAY INFORMATION:
                                                              Check-in Date: %s
                                                              Check-out Date: %s
                                                              Duration: %d night%s
                                                                                                                    PRICE INFORMATION:
                                                              Total Price: £%.2f
                                                                                                                    BOOKING INFORMATION:
                                                              Booking Date: %s
                                                              Customer ID: %d
                                                              Deposit Status: Not Paid
                                                                                                                    Please confirm all details are correct.""",
                            roomNo,
                            roomTypeName,
                            checkIn.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy")),
                            checkOut.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy")),
                            nights,
                            nights != 1 ? "s" : "",
                            totalPrice,
                            dateMade.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy")),
                            customerId
                    ),
                    "Confirm Booking",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Note: Price parameter is placeholder for future implementation

                // Save booking to database
                boolean success = DataManager.createBooking(
                        customerId,
                        roomNo,
                        checkIn,
                        checkOut,
                        totalPrice
                );

                if (success) {
                    JOptionPane.showMessageDialog(this,
                            String.format("""
                                          BOOKING CONFIRMED SUCCESSFULLY!
                                          
                                          BOOKING SUMMARY:
                                            Booking Reference: Customer ID %d
                                            Total Price: £%.2f
                                            Booking Date: %s
                                          
                                          ROOM DETAILS:
                                            Room Number: %d
                                            Room Type: %s
                                          
                                          STAY DETAILS:
                                            Check-in: %s
                                            Check-out: %s
                                            Duration: %d night%s
                                          
                                          PAYMENT STATUS:
                                            Deposit: Not Paid (to be paid at check-in)
                                          
                                          Thank you for your booking!
                                          A confirmation has been saved to our system.""",
                                    customerId,
                                    totalPrice,
                                    dateMade.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                    roomNo,
                                    roomTypeName,
                                    checkIn.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                    checkOut.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                    nights,
                                    nights != 1 ? "s" : ""
                            ),
                            "Booking Successful",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Clear selection and table
                    selectedRoomRow = -1;
                    jButton4.setEnabled(false);
                    jTable1.clearSelection();

                    // Clear the table
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.setRowCount(0);
                    currentAvailableRooms.clear();

                    // Reset date buttons to defaults
                    jButton2.setText("Select Date: " + DATE_FORMAT.format(new java.util.Date()));
                    Date tomorrow = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
                    jButton3.setText("Select Date: " + DATE_FORMAT.format(tomorrow));

                    //Reset filters to defaults
                    jSpinner1.setValue(1);
                    jSpinner2.setValue(1);

                    //Go back to user dashboard or stay on page
                    if ("Customer".equals(deltapms.session.UserSession.getUserRole())){
                        MainApplication.showPanel("UserSystem");
                    }
                    if ("Staff".equals(deltapms.session.UserSession.getUserRole())){
                        MainApplication.showPanel("StaffSystem");
                    }
                    if ("Manager".equals(deltapms.session.UserSession.getUserRole())){
                        // TO DO CHANGE TO MANAGER SYSTEM
                        MainApplication.showPanel("StaffSystem");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, """
                                                            BOOKING FAILED
                                                            
                                                            Failed to create booking. Possible reasons:
                                                            \u2022 Database connection issue
                                                            \u2022 Room no longer available
                                                            \u2022 System error
                                                            
                                                            Please try again or contact support.""",
                            "Booking Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a room from the table first.",
                    "No Room Selected",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // Check login when trying to search
        if (!UserSession.isLoggedIn()) {
            JOptionPane.showMessageDialog(this,
                    "Please login first to search for rooms.",
                    "Access Denied",
                    JOptionPane.WARNING_MESSAGE);
            MainApplication.showPanel("UserSystem"); // or whatever your login panel is
            return;
        }

        // Search for rooms button clicked
        // Get selected dates
        Date startDate = dateCalendar.getDate();
        Date endDate = dateCalendar2.getDate();

        if (startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select both check-in and check-out dates.",
                    "Missing Dates",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate checkIn = convertDate(startDate);
        LocalDate checkOut = convertDate(endDate);

        // Validate date range
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            JOptionPane.showMessageDialog(this,
                    "Check-out date must be after check-in date.",
                    "Invalid Dates",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get filter criteria
        int minBathrooms = (Integer) jSpinner1.getValue();
        int minBeds = (Integer) jSpinner2.getValue();

        // Get all available rooms for the date range
        List<Room> allAvailableRooms = DataManager.getAvailableRooms(checkIn, checkOut);

        // Apply additional filters
        List<Room> filteredRooms = new ArrayList<>();
        for (Room room : allAvailableRooms) {
            RoomType type = getRoomTypeById(room.getRoomTypeID());
            if (type != null) {
                int totalBeds = type.getSingleBeds() + (type.getDoubleBeds() * 2);
                if (type.getBathrooms() >= minBathrooms && totalBeds >= minBeds) {
                    filteredRooms.add(room);
                }
            }
        }

        // Display results in table
        populateTableWithRooms(filteredRooms);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(89, 4, 20));
        jPanel1.setForeground(new java.awt.Color(89, 4, 20));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/deltapmsprototype/ui/components/DeltaPMSlogo_nobackground.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(242, 68, 29)));
        jPanel20.setPreferredSize(new java.awt.Dimension(184, 2));

        jButton1.setText("Search for rooms");

        jButton2.setText("Select start date");
        jButton2.setActionCommand("Select Date");

        jLabel3.setText("Beds");

        jLabel4.setText("Bathrooms");
        jLabel4.setToolTipText("");

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(1, 1, 6, 1));

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(1, 1, 6, 1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSpinner1)
                    .addComponent(jSpinner2))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSpinner2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSpinner1))
                .addContainerGap())
        );

        jButton3.setText("Select end date");
        jButton3.setActionCommand("Select Date");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Confirm Selected Room");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1376, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel2, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

}
