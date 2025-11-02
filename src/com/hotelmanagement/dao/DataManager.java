package com.hotelmanagement.dao;

import com.hotelmanagement.models.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

public class DataManager {
    // Your ArrayLists as specified
    private static List<Customer> customers = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();
    private static List<RoomType> roomTypes = new ArrayList<>(); // NEW
    private static List<Booking> bookings = new ArrayList<>();
    private static List<Staff> staff = new ArrayList<>();
    private static List<Staff> managers = new ArrayList<>();
    
    private static void initialiseSampleData(){
        RoomType singleRoom = new RoomType(1, 1, 1, 0, true, false, true, true, true, true);
        RoomType doubleRoom = new RoomType(2, 1, 0, 1, true, true, true, true, true, true);
        RoomType twinRoom = new RoomType(3, 1, 2, 0, true, false, true, true, true, true);
        RoomType suite = new RoomType(4, 2, 0, 1, true, true, true, true, true, true);
        
        roomTypes.add(singleRoom);
        roomTypes.add(doubleRoom);
        roomTypes.add(twinRoom);
        roomTypes.add(suite);
    }
}