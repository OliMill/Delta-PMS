package com.hotelmanagement.models;

import java.time.LocalDate;

public class Booking {
    private int bookingID;
    private int customerID;
    private int roomNo;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate dateMade;
    
    // booking model 
    public Booking(int bookingID, int customerID, int roomNo, LocalDate checkInDate, LocalDate checkOutDate, LocalDate dateMade) {
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.roomNo = roomNo;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.dateMade = dateMade;
    }
    //getters
    public int getBookingID() {
        return bookingID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public LocalDate getDateMade() {
        return dateMade;
    }
    
    // setters
    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    
    public void setDateMade(LocalDate dateMade) {
        this.dateMade = dateMade;
    }

 

    // Helper methods
    public int getNumberOfNights() {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
}