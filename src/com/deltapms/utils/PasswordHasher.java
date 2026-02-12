/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deltapms.utils;

import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Oli
 */
public class PasswordHasher {

    // A "Pepper" to ensure secure passwords even if hash is guessed
    private static final String PEPPER = "z8$Kj#29L!mP5vQ7xR0@nW3eY6tU1"; 

    public static String hashPassword(String plainTextPassword) {
        // Concatenate the password and the pepper before hashing
        String passwordWithPepper = plainTextPassword + PEPPER;
        return BCrypt.hashpw(passwordWithPepper, BCrypt.gensalt(12));
    }

    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        // add the same pepper during verification
        String passwordWithPepper = plainTextPassword + PEPPER;
        return BCrypt.checkpw(passwordWithPepper, hashedPassword);
    }
    
    public static String securePassword(String password) {
        List<String> errors = new ArrayList<>();

        // Check Length
        if (password.length() < 8) {
            errors.add("- At least 8 characters long");
        }

        // Check for Uppercase
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            errors.add("- At least one uppercase letter (A-Z)");
        }

        // Check for Lowercase
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            errors.add("- At least one lowercase letter (a-z)");
        }

        // Check for Numbers
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            errors.add("- At least one number (0-9)");
        }

        // Check for Special Characters
        if (!Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find()) {
            errors.add("- At least one special character (!@#$)");
        }

        // If the list is empty
        if (errors.isEmpty()) {
            return "";
        }

        // Join all errors into a single message with newlines
        return "Password must meet the following requirements:\n" + String.join("\n", errors);
    }
}