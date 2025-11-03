package com.hotelmanagement.models;

import java.time.LocalDate;

public class Staff {
    private int staffID;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String email;
    private String username;
    private String passwordHash;
    private String job;
    
    public Staff(int staffID, String firstName, String lastName, LocalDate dob, 
                 String email, String username, String passwordHash, String job) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.job = job;
    }

    public int getStaffID() {
        return staffID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getJob() {
        return job;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setJob(String job) {
        this.job = job;
    }
}