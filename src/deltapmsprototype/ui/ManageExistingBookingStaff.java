package deltapmsprototype.ui;

import com.hotelmanagement.models.Booking;
import com.hotelmanagement.models.Customer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import com.hotelmanagement.dao.DataManager;
import com.hotelmanagement.models.Room;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;


/**
 *
 * @author Oli
 */
public class ManageExistingBookingStaff extends javax.swing.JPanel {

    private final MainApplicationFrame MainApplication1;

    // Maps Customer ID -> Full Name (for quick lookup in the table)
    private Map<Integer, String> customerNameMap;
    private LocalDate startDateDisplay;
    
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public ManageExistingBookingStaff(MainApplicationFrame MainApplication) {
        initComponents();
        MainApplication1 = MainApplication;

        // 1. Load Data from DB
        DataManager.loadDataFromDatabase();

        // 2. Cache Customer Names for display (Optimization)
        cacheCustomerNames();

        // 3. Setup Table
        setupCustomTable();

        // CALENDAR INITIALIZATION LOGIC START
        dateCalendar = new com.toedter.calendar.JCalendar();
        calendarPopup = new javax.swing.JPopupMenu();

        // 1. Add the JCalendar to the JPopupMenu
        calendarPopup.add(dateCalendar);

        // Set initial button text to today's date
        jButton2.setText("Select Date: " +  DATE_FORMAT.format(new java.util.Date()));

        // 2. Add the listener to handle date selection
        dateCalendar.addPropertyChangeListener("day", (java.beans.PropertyChangeEvent evt) -> {
            // This block only runs when a specific DAY is clicked
            java.util.Date selectedDate = dateCalendar.getDate();

            if (selectedDate != null) {
                // 1. Update the button's text
                jButton2.setText("Selected Date: " + DATE_FORMAT.format(selectedDate));

                // 3. Hide the popup now that a final selection is made
                calendarPopup.setVisible(false);

                // 4. Refresh your table to show the new dates
                setupCustomTable();
            }
        });
    }

    private void cacheCustomerNames() {
    customerNameMap = new HashMap<>();
    for (Customer c : DataManager.getCustomers()) {
        customerNameMap.put(c.getCustomerID(), c.getFirstName() + " " + c.getLastName());
    }
}

    private void setupCustomTable() {
    // Set start date and convert to local date
    // 1. Ensure the start date is initialized
    if (startDateDisplay == null) {
        startDateDisplay = LocalDate.now(); 
    } else {
        startDateDisplay = dateCalendar.getDate().toInstant()
                                 .atZone(java.time.ZoneId.systemDefault())
                                 .toLocalDate();
    }
    // Apply Model using DataManager lists
    List<Room> rooms = DataManager.getRooms();
    List<Booking> bookings = DataManager.getBookings();

    
    BookingTableModel model = new BookingTableModel(rooms, bookings, startDateDisplay, jSlider1.getValue());
    jTable1.setModel(model);

    // Apply Renderer
        jTable1.setDefaultRenderer(Object.class, new BookingRenderer());

        // Formatting
        jTable1.setRowHeight(40);
        jTable1.setShowGrid(true);
        jTable1.setGridColor(new Color(230, 230, 230));
        jTable1.getTableHeader().setReorderingAllowed(false);

        // Mouse Listener for "Clicking each stay"
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = jTable1.rowAtPoint(e.getPoint());
                int col = jTable1.columnAtPoint(e.getPoint());

                // Get the object at this cell
                Object data = jTable1.getValueAt(row, col);

                if (data instanceof Booking booking) {
                    showBookingPopup(e, booking);
                }
            }
        });
    }
    private void showBookingPopup(MouseEvent e, Booking booking) {
        String guestName = customerNameMap.getOrDefault(booking.getCustomerID(), "Unknown");

        JPopupMenu popup = new JPopupMenu();
        popup.add(new JMenuItem("Edit Booking for " + guestName));
        popup.add(new JMenuItem("Cancel Booking (ID: " + booking.getBookingID() + ")"));
        popup.show(e.getComponent(), e.getX(), e.getY());
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
        jPanel2 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jSlider1 = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(89, 4, 20));
        jPanel1.setForeground(new java.awt.Color(89, 4, 20));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/deltapmsprototype/ui/components/DeltaPMSlogo_nobackground.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 692, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(242, 68, 29)));
        jPanel20.setPreferredSize(new java.awt.Dimension(184, 2));
        java.awt.GridBagLayout jPanel20Layout = new java.awt.GridBagLayout();
        jPanel20Layout.columnWidths = new int[] {0, 30, 0, 30, 0, 30, 0, 30, 0};
        jPanel20Layout.rowHeights = new int[] {0, 10, 0};
        jPanel20.setLayout(jPanel20Layout);

        jSlider1.setMajorTickSpacing(7);
        jSlider1.setMaximum(56);
        jSlider1.setPaintLabels(true);
        jSlider1.setPaintTicks(true);
        jSlider1.setSnapToTicks(true);
        jSlider1.setToolTipText("Viewing Period");
        jSlider1.setValue(14);
        jSlider1.setMajorTickSpacing(7);
        jSlider1.setMinorTickSpacing(1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        jPanel20.add(jSlider1, gridBagConstraints);

        jLabel2.setText("Viewing Period");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        jPanel20.add(jLabel2, gridBagConstraints);

        jButton1.setText("jButton1");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        jPanel20.add(jButton1, gridBagConstraints);

        jButton2.setText("jButton2");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        jPanel20.add(jButton2, gridBagConstraints);

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1229, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        int x = 0;
        int y = jButton2.getHeight();
        // Show the popup relative to jButton2
        calendarPopup.show(jButton2, x, y);
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        setupCustomTable();
    }//GEN-LAST:event_jButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    private com.toedter.calendar.JCalendar dateCalendar;
    private javax.swing.JPopupMenu calendarPopup;
    
    // CUSTOM TABLE MODEL
    class BookingTableModel extends AbstractTableModel {

        private final List<Room> rooms;
        private final List<Booking> bookings;
        private final LocalDate startDate;
        private final String[] columnNames;

        public BookingTableModel(List<Room> rooms, List<Booking> bookings, LocalDate startDate, int daysToShow) {
            this.rooms = rooms;
            this.bookings = bookings;
            this.startDate = startDate;
            this.columnNames = new String[daysToShow + 1];

            // Set Headers
            columnNames[0] = "Room";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
            for (int i = 0; i < daysToShow; i++) {
                columnNames[i + 1] = startDate.plusDays(i).format(formatter);
            }
        }

        @Override
        public int getRowCount() {
            return rooms.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Room room = rooms.get(rowIndex);

            // Col 0: Room Number
            if (columnIndex == 0) {
                return String.valueOf(room.getRoomNo());
            }

            // Col 1+: Dates
            // Calculate which date this column represents
            LocalDate colDate = startDate.plusDays(columnIndex - 1);

            // Find a booking for this Room and this Date
            for (Booking b : bookings) {
                // Must match Room Number
                if (b.getRoomNo() == room.getRoomNo()) {

                    // Must fall within Date Range
                    // CheckIn <= Date < CheckOut (CheckOut day is usually free for new guest)
                    boolean isAfterOrOnCheckIn = !colDate.isBefore(b.getCheckInDate());
                    boolean isBeforeCheckOut = colDate.isBefore(b.getCheckOutDate());

                    if (isAfterOrOnCheckIn && isBeforeCheckOut) {
                        return b; // Return the actual Booking object
                    }
                }
            }
            return null; // Empty slot
        }
    }
    // CUSTOM RENDERER

    class BookingRenderer extends DefaultTableCellRenderer {

        // Soft pastel colors
        private final Color[] colors = {
            new Color(152, 251, 152), // Pale Green
            new Color(135, 206, 250), // Light Sky Blue
            new Color(255, 182, 193), // Light Pink
            new Color(255, 218, 185) // Peach
        };

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Reset base styles
            c.setBorder(BorderFactory.createEmptyBorder());
            c.setHorizontalAlignment(SwingConstants.LEFT);
            c.setText("");

            // --- HEADER COLUMN (Room Number) ---
            if (column == 0) {
                c.setBackground(new Color(240, 240, 240));
                c.setForeground(Color.BLACK);
                c.setFont(c.getFont().deriveFont(Font.BOLD));
                c.setText((String) value);
                return c;
            }

            // --- DATA COLUMNS ---
            if (value instanceof Booking b) {

                // Color based on BookingID to keep it consistent across the bar
                int colorIndex = Math.abs(b.getBookingID()) % colors.length;
                c.setBackground(colors[colorIndex]);
                c.setForeground(Color.DARK_GRAY);

                // Determine if this cell is the "Start Date" cell
                // We check if the column's date matches the booking's check-in date
                LocalDate colDate = startDateDisplay.plusDays(column - 1);

                if (colDate.isEqual(b.getCheckInDate())) {
                    // It is the start date: Show Guest Name
                    String guestName = customerNameMap.getOrDefault(b.getCustomerID(), "ID: " + b.getCustomerID());
                    c.setText("  " + guestName);
                    c.setFont(c.getFont().deriveFont(Font.BOLD, 10f));
                } else if (column == 1 && colDate.isAfter(b.getCheckInDate())) {
                    // Edge case: If the booking started BEFORE the visible table (scroll start), 
                    // show the name in the very first visible column (index 1) so we know who it is.
                    String guestName = customerNameMap.getOrDefault(b.getCustomerID(), "ID: " + b.getCustomerID());
                    c.setText("  " + guestName + " (cont.)");
                    c.setFont(c.getFont().deriveFont(Font.PLAIN, 10f));
                }

            } else {
                // Empty Room
                c.setBackground(Color.WHITE);
                c.setText("");
            }

            return c;
        }
    }
}
