package com.hotelmanagement.models;

import java.time.LocalDate;

public class Customer extends User {

    public Customer(int customerID, String firstName, String lastName, LocalDate dob,
                    String email, String passwordHash) {
        // Pass everything up to the User constructor
        super(customerID, firstName, lastName, dob, email, passwordHash);
    }

    // Wrappers to maintain backward compatibility
    public int getCustomerID() {
        return super.getId();
    }

    public void setCustomerID(int customerID) {
        super.setId(customerID);
    }
}
