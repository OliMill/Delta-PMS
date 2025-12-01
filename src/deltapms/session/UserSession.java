/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deltapms.session;
//stores details about current user session
public class UserSession {
    private static String userName;
    private static String userRole;
    private static String userEmail;
    private static int userId;
    private static boolean isLoggedIn = false;
    
    public static void login(String name, String role, String email, int id) {
        userName = name;
        userRole = role;
        userEmail = email;
        userId = id;
        isLoggedIn = true;
    }
    
    public static void logout() {
        userName = null;
        userRole = null;
        userEmail = null;
        userId = -1;
        isLoggedIn = false;
    }
    
    public static boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    public static String getUserName() {
        return userName;
    }
    
    public static String getUserRole() {
        return userRole;
    }
    
    public static String getUserEmail() {
        return userEmail;
    }
    
    public static int getUserId() {
        return userId;
    }
    
    public static boolean isCustomer() {
        return "Customer".equals(userRole);
    }
    
    public static boolean isStaff() {
        return "Receptionist".equals(userRole) || "Housekeeping".equals(userRole) || 
               "Maintenance".equals(userRole) || "Staff".equals(userRole);
    }
    
    public static boolean isManager() {
        return "Manager".equals(userRole);
    }
}