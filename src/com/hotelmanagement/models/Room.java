package com.hotelmanagement.models;

import java.math.BigDecimal;

public class Room {
    private int roomNo;
    private int roomTypeID;
    private BigDecimal pricePerNight;
    private boolean isAvailable;
    private Integer currentBookingID;
    
    private RoomType roomType;
    
    public Room(int roomNo, int roomTypeID) {
        this.roomNo = roomNo;
        this.roomTypeID = roomTypeID;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setCurrentBookingID(Integer currentBookingID) {
        this.currentBookingID = currentBookingID;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    
}