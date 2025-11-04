package com.hotelmanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://185.156.138.148:3306/4-omillard"; 
    private static final String USER = "4-omillard";
    private static final String PASSWORD = "Bark2-Ball-Society";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
