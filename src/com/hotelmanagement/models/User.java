package com.hotelmanagement.models;

import java.time.LocalDate;

// abstract base class for Staff and Customer
public abstract class User {
    private int id; 
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String email;
    private String passwordHash;

    public User(int id, String firstName, String lastName, LocalDate dob, 
                String email, String passwordHash) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Shared Getters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDob() { return dob; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }

    // Shared Setters 
    public void setId(int id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}
