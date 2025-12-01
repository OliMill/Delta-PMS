package com.hotelmanagement.models;

public class Room {
    private int roomNo;
    private int roomTypeID;
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
    //override for formating
    @Override
    public String toString() {
    return "Room{" +
           "roomNo=" + roomNo +
           ", roomTypeID=" + roomTypeID +
           '}';
    }
}