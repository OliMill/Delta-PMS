    package com.hotelmanagement.dao;

    import com.hotelmanagement.models.*;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Date;
    import java.time.LocalDate;
    import java.util.stream.Collectors;

    public class DataManager {

        private static List<Customer> customers = new ArrayList<>();
        private static List<Room> rooms = new ArrayList<>();
        private static List<RoomType> roomTypes = new ArrayList<>();
        private static List<Booking> bookings = new ArrayList<>();
        private static List<Staff> staff = new ArrayList<>();
        private static List<Staff> managers = new ArrayList<>();

        // Initialising all the data
        public static void loadDataFromDatabase() {
            // Clear all lists before loading to prevent duplicate data on reload
            customers.clear();
            rooms.clear();
            roomTypes.clear();
            bookings.clear();
            staff.clear();
            managers.clear();

            try (Connection conn = DatabaseConnection.getConnection()) {
                loadStaff(conn);
                loadCustomers(conn);
                loadRoomTypes(conn);
                loadRooms(conn);
                loadBookings(conn);
            } catch (SQLException e) {
                // Print a clean error message if loading fails
                System.err.println("Failed to load data from database:");
            }
        }

        // Functions to set database into Lists.
        private static void loadStaff(Connection conn) throws SQLException {
            String sql = "SELECT * FROM Staff";
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String role = rs.getString("Role"); 

                    Staff s = new Staff(
                            rs.getInt("StaffID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getDate("DOB").toLocalDate(),
                            rs.getString("Email"),
                            rs.getString("Username"),
                            rs.getString("PasswordHash"),
                            role
                    );
                    staff.add(s);

                    // if staff is manager also add to manager list
                    if (role != null && role.equalsIgnoreCase("Manager")) {
                        managers.add(s);
                    }
                }
            }
        }

        private static void loadCustomers(Connection conn) throws SQLException {
            String sql = "SELECT * FROM Customer";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Customer c = new Customer(
                            rs.getInt("CustomerID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getDate("DOB").toLocalDate(),
                            rs.getString("Email"),
                            rs.getString("Username"),
                            rs.getString("PasswordHash")
                    );
                    customers.add(c);
                }
            }
        }

        private static void loadRoomTypes(Connection conn) throws SQLException {
            String sql = "SELECT * FROM RoomType";
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    RoomType rt = new RoomType(
                            rs.getInt("RoomTypeID"),
                            rs.getInt("Bathrooms"),
                            rs.getInt("SingleBeds"),
                            rs.getInt("DoubleBeds"),
                            rs.getBoolean("Desk"),
                            rs.getBoolean("Bath"),
                            rs.getBoolean("Shower"),
                            rs.getBoolean("TV"),
                            rs.getBoolean("CoffeeMachine"),
                            rs.getBoolean("DepositBOX")
                    );
                    roomTypes.add(rt);
                }
            }
        }

        private static void loadRooms(Connection conn) throws SQLException {
            String sql = "SELECT * FROM Rooms";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Room r = new Room(
                            rs.getInt("RoomNo"),
                            rs.getInt("RoomTypeID")
                    );
                    rooms.add(r);
                }
            }
        }

        private static void loadBookings(Connection conn) throws SQLException {
            String sql = "SELECT * FROM Booking";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking(
                            rs.getInt("BookingID"),
                            rs.getInt("CustomerID"),
                            rs.getInt("RoomNo"),
                            rs.getDate("CheckInDate").toLocalDate(),
                            rs.getDate("CheckOutDate").toLocalDate(),
                            rs.getBoolean("DepositePaid") 
                    );
                    bookings.add(b);
                }
            }
        }

        // Getters
        public static List<Customer> getCustomers() {
            return customers;
        }

        public static List<Room> getRooms() {
            return rooms;
        }

        public static List<RoomType> getRoomTypes() {
            return roomTypes;
        }

        public static List<Booking> getBookings() {
            return bookings;
        }

        public static List<Room> getAvailableRooms(LocalDate proposedCheckInDate, LocalDate proposedCheckOutDate) {

            // Date Validation
            if (proposedCheckInDate == null || proposedCheckOutDate == null || proposedCheckOutDate.isBefore(proposedCheckInDate)) {
                System.err.println("Invalid date range provided. Check-in date must be before check-out date.");
                return new ArrayList<>();
            }

            // Iterate through all rooms and filter them based on availability
            return rooms.stream()
                .filter(room -> {

                    // Check if ANY booking in the ENTIRE hotel list (DataManager.bookings) 
                    // conflicts with the proposed dates AND belongs to the current room.
                    boolean hasConflict = bookings.stream()
                        .anyMatch(existingBooking -> 
                            existingBooking.getRoomNo() == room.getRoomNo() && // Filter by room first

                            // Existing Check-In is BEFORE Proposed Check-Out
                            existingBooking.getCheckInDate().isBefore(proposedCheckOutDate) &&

                            // Existing Check-Out is AFTER Proposed Check-In
                            existingBooking.getCheckOutDate().isAfter(proposedCheckInDate)
                        );

                    // A room is AVAILABLE if it DOES NOT have a conflict.
                    return !hasConflict;
                })
                .collect(Collectors.toList());
        }

        public static List<Staff> getStaff() {
            return staff;
        }

        public static List<Staff> getManagers() {
            return managers;
        }
    }