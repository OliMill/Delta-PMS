package com.hotelmanagement.models;

import java.time.LocalDate;
import java.math.BigDecimal;

public class Booking {
    private int bookingID;
    private int customerID;
    private int roomNo;
    private LocalDate dateMade;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal totalPrice;
    private boolean depositPaid;
    
    public Booking(int bookingID, int customerID, int roomNo, LocalDate dateMade, 
                   LocalDate checkInDate, LocalDate checkOutDate, BigDecimal totalPrice, boolean depositPaid) {
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.roomNo = roomNo;
        this.dateMade = dateMade;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.depositPaid = depositPaid;
    }

    public int getBookingID() {
        return bookingID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public LocalDate getDateMade() {
        return dateMade;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public boolean isDepositPaid() {
        return depositPaid;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public void setDateMade(LocalDate dateMade) {
        this.dateMade = dateMade;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDepositPaid(boolean depositPaid) {
        this.depositPaid = depositPaid;
    }
    

    // Helper methods
    public int getNumberOfNights() {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
}