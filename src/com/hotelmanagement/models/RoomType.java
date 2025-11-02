package com.hotelmanagement.models;

public class RoomType {
    private int roomTypeID;
    private int bathrooms;
    private int singleBeds;
    private int doubleBeds;
    private boolean hasDesk;
    private boolean hasBath;
    private boolean hasShower;
    private boolean hasTV;
    private boolean hasCoffee;
    private boolean hasDepositBox;

    
    public RoomType(int roomTypeID, int bathrooms, int singleBeds, int doubleBeds, 
                   boolean hasDesk, boolean hasBath, boolean hasShower, boolean hasTV, 
                   boolean hasCoffee, boolean hasDepositBox) {
        this.roomTypeID = roomTypeID;
        this.bathrooms = bathrooms;
        this.singleBeds = singleBeds;
        this.doubleBeds = doubleBeds;
        this.hasDesk = hasDesk;
        this.hasBath = hasBath;
        this.hasShower = hasShower;
        this.hasTV = hasTV;
        this.hasCoffee = hasCoffee;
        this.hasDepositBox = hasDepositBox;

    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public int getSingleBeds() {
        return singleBeds;
    }

    public int getDoubleBeds() {
        return doubleBeds;
    }

    public boolean isHasDesk() {
        return hasDesk;
    }

    public boolean isHasBath() {
        return hasBath;
    }

    public boolean isHasShower() {
        return hasShower;
    }

    public boolean isHasTV() {
        return hasTV;
    }

    public boolean isHasCoffee() {
        return hasCoffee;
    }

    public boolean isHasDepositBox() {
        return hasDepositBox;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public void setSingleBeds(int singleBeds) {
        this.singleBeds = singleBeds;
    }

    public void setDoubleBeds(int doubleBeds) {
        this.doubleBeds = doubleBeds;
    }

    public void setHasDesk(boolean hasDesk) {
        this.hasDesk = hasDesk;
    }

    public void setHasBath(boolean hasBath) {
        this.hasBath = hasBath;
    }

    public void setHasShower(boolean hasShower) {
        this.hasShower = hasShower;
    }

    public void setHasTV(boolean hasTV) {
        this.hasTV = hasTV;
    }

    public void setHasCoffee(boolean hasCoffee) {
        this.hasCoffee = hasCoffee;
    }

    public void setHasDepositBox(boolean hasDepositBox) {
        this.hasDepositBox = hasDepositBox;
    }

    // Helper methods
    public int getTotalBeds() {
        return singleBeds + doubleBeds;
    }
    
    public int getSleepingCapacity() {
        return singleBeds + (doubleBeds * 2);
    }
    
    public String getAmenitiesSummary() {
        StringBuilder amenities = new StringBuilder();
        if (hasDesk) amenities.append("Desk, ");
        if (hasBath) amenities.append("Bath, ");
        if (hasShower) amenities.append("Shower, ");
        if (hasTV) amenities.append("TV, ");
        if (hasCoffee) amenities.append("Coffee, ");
        if (hasDepositBox) amenities.append("Safe, ");
        
        // Remove trailing comma and space
        if (amenities.length() > 0) {
            amenities.setLength(amenities.length() - 2);
        }
        return amenities.toString();
    }
}