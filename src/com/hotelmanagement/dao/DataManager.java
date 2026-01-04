package com.hotelmanagement.dao;

import com.hotelmanagement.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
            e.printStackTrace();
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
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getDate("DOB").toLocalDate(),
                        rs.getString("Email"),
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
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
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
                        rs.getDate("DateMade") != null
                        ? rs.getDate("DateMade").toLocalDate() : null
                );
                bookings.add(b);
            }
        }
    }

    public static boolean createBooking(int customerId, int roomId, LocalDate checkIn,
            LocalDate checkOut, double totalPrice) {

        String insertSql = "INSERT INTO Booking (CustomerID, RoomNo, CheckInDate, CheckOutDate, DateMade) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

            pstmt.setInt(1, customerId);
            pstmt.setInt(2, roomId);
            pstmt.setDate(3, java.sql.Date.valueOf(checkIn));
            pstmt.setDate(4, java.sql.Date.valueOf(checkOut));
            pstmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                loadDataFromDatabase();

                // Logic to send email regardless of who is logged in
                new Thread(() -> {
                    String targetEmail = "";
                    String targetName = "Valued Guest";

                    // If a customer is logged in, we already have their info
                    if (deltapms.session.UserSession.isCustomer()) {
                        targetEmail = deltapms.session.UserSession.getUserEmail();
                        targetName = deltapms.session.UserSession.getUserName();
                    } else {
                        // STAFF IS LOGGED IN: Look up the customer's email via the customerId
                        targetEmail = getCustomerEmailById(customerId);
                    }

                    if (targetEmail != null && !targetEmail.isEmpty()) {
                        String htmlContent = "<h1>Booking Confirmation</h1>"
                                + "<p>Dear " + targetName + ",</p>"
                                + "<p>A new booking has been created for you at Delta Hotels.</p>"
                                + "<ul>"
                                + "<li><b>Room:</b> " + roomId + "</li>"
                                + "<li><b>Check-in:</b> " + checkIn + "</li>"
                                + "<li><b>Check-out:</b> " + checkOut + "</li>"
                                + "<li><b>Total Price:</b> £" + String.format("%.2f", totalPrice) + "</li>"
                                + "</ul>";

                        com.deltapms.utils.EmailService.sendEmail(targetEmail, "Booking Confirmation - Delta Hotels", htmlContent);
                    }
                }).start();

                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("Error creating booking: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteBooking(int bookingID) {
        String sql = "DELETE FROM Booking WHERE BookingID = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookingID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Getters
    public static List<Customer> getCustomers() {
        return customers;
    }

    private static String getCustomerEmailById(int id) {
        String email = "";
        String sql = "SELECT Email FROM Customer WHERE CustomerID = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                email = rs.getString("Email");
            }
        } catch (SQLException e) {
            System.err.println("Could not find customer email: " + e.getMessage());
        }
        return email;
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
                            .anyMatch(existingBooking
                                    -> existingBooking.getRoomNo() == room.getRoomNo()
                            && // Filter by room first
                            // Existing Check-In is BEFORE Proposed Check-Out
                            existingBooking.getCheckInDate().isBefore(proposedCheckOutDate)
                            && // Existing Check-Out is AFTER Proposed Check-In
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
