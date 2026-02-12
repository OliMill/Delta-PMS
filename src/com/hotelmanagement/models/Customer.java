package com.hotelmanagement.models;

import java.time.LocalDate;

public class Customer {
    private int customerID;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String email;
    private String passwordHash;

    //customer model
    public Customer(int customerID, String firstName, String lastName, LocalDate dob,
    String email,String passwordHash) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // getters
    public int getCustomerID() {
        return customerID;
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

    public String getPasswordHash() {
        return passwordHash;
    }
    
    // setters
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}