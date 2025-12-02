package deltapms.session;

import com.hotelmanagement.models.Room;
import java.time.LocalDate;

public class BookingSession {
    private static Room selectedRoom;
    private static LocalDate checkInDate;
    private static LocalDate checkOutDate;
    private static double totalPrice;
    
    public static void startNewBooking(Room room, LocalDate checkIn, LocalDate checkOut) {
        selectedRoom = room;
        checkInDate = checkIn;
        checkOutDate = checkOut;
        // Calculate price based on room type and duration
        totalPrice = calculatePrice(room, checkIn, checkOut);
    }
    
    public static void clear() {
        selectedRoom = null;
        checkInDate = null;
        checkOutDate = null;
        totalPrice = 0.0;
    }
    
    private static double calculatePrice(Room room, LocalDate checkIn, LocalDate checkOut) {
        long nights = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
        
        // Simple pricing based on room type ID
        double nightlyRate;
        nightlyRate = switch (room.getRoomTypeID()) {
            // TEMPORARY CONSTANT PRICE WILL BE ALTERABLE
            case 1 -> 100.0;
            case 2 -> 150.0;
            case 3 -> 200.0;
            case 4 -> 180.0;
            case 5 -> 250.0;
            case 6 -> 80.0;
            case 7 -> 500.0;
            default -> 100.0;
        }; 
        // Standard
        // Deluxe
        // Family Suite
        // Business Suite
        // Executive Suite
        // Economy
        // Penthouse
        
        return nightlyRate * nights;
    }
    
    // Getters
    public static Room getSelectedRoom() {
        return selectedRoom;
    }
    
    public static LocalDate getCheckInDate() {
        return checkInDate;
    }
    
    public static LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    
    public static double getTotalPrice() {
        return totalPrice;
    }
    
    public static long getNumberOfNights() {
        if (checkInDate == null || checkOutDate == null) return 0;
        return java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
    
    public static boolean hasActiveBooking() {
        return selectedRoom != null && checkInDate != null && checkOutDate != null;
    }
}