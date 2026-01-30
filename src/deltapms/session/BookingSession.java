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
        totalPrice = 0;
    }
    
    public static void clear() {
        selectedRoom = null;
        checkInDate = null;
        checkOutDate = null;
        totalPrice = 0.0;
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