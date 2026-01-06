package com.hotelmanagement.models;

public class Room {
    private int roomNo;
    private int roomTypeID;
    private RoomType roomType;
    
    public Room(int roomNo, int roomTypeID) {
        this.roomNo = roomNo;
    this.roomTypeID = roomTypeID;
    
    //Search for the matching type in your data manager
    for (com.hotelmanagement.models.RoomType rt : com.hotelmanagement.dao.DataManager.getRoomTypes()) {
        if (rt.getRoomTypeID() == roomTypeID) {
            this.roomType = rt;
            break;
        }
    }
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
        return "Room{"
                + "roomNo=" + roomNo
                + ", roomTypeID=" + roomTypeID
                + '}';
    }

    public String getRoomTypeNameById() {
        // Check if the roomType object is linked and return its name
        if (this.roomType != null) {
            return this.roomType.getRoomName();
        }
        return "Unknown Type";
    }
    
    public double getRoomPrice(){
        if (this.roomType!=null){
            return this.roomType.getPricePerNight();
        }
        return 0.0;
    }
}
 