package com.hotelmanagement.models;

import java.time.LocalDate;

public class Staff extends User {
    private String job;

    public Staff(int staffID, String firstName, String lastName, LocalDate dob, 
                 String email, String passwordHash, String job) {
        // Pass attributes to the User constructor
        super(staffID, firstName, lastName, dob, email, passwordHash);
        
        // staff job
        this.job = job;
    }

    // Getters
    public String getRole() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    // Wrapper for backwards compatability
    public int getStaffID() {
        return super.getId();
    }

    public void setStaffID(int staffID) {
        super.setId(staffID);
    }
}
