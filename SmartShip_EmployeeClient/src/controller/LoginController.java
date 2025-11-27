package controller;

import model.User;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import javax.swing.JOptionPane;

public class LoginController {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5000;
    
    /**
     * Authenticate employee (Clerk, Manager, or Driver)
     * Only allows login for employee roles
     */
    public User authenticate(String username, String password) {
        // Validation
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Username cannot be empty!", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        if (password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Password cannot be empty!", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        // Connect to server and authenticate
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            
            // Send LOGIN command
            out.writeObject("LOGIN");
            out.flush();
            
            out.writeObject(username);
            out.flush();
            
            out.writeObject(password);
            out.flush();
            
            // Read response from server
            Object response = in.readObject();
            
            if (response instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, String> userInfo = (Map<String, String>) response;
                
                String role = userInfo.get("role");
                
                // Check if user is an employee (not a customer)
                if ("Customer".equals(role)) {
                    JOptionPane.showMessageDialog(null, 
                        "This is the Employee Portal. Customers must use the Customer Client.", 
                        "Access Denied", 
                        JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                
                // Check if role is valid employee role
                if (!isValidEmployeeRole(role)) {
                    JOptionPane.showMessageDialog(null, 
                        "Invalid employee role. Contact administrator.", 
                        "Access Denied", 
                        JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                
                // Create User object from response
                User user = new User();
                user.setUserId(Integer.parseInt(userInfo.get("userId")));
                user.setUsername(userInfo.get("username"));
                user.setEmail(userInfo.get("email"));
                user.setPhone(userInfo.get("phone"));
                user.setAddress(userInfo.get("address"));
                user.setRole(role);
                
                return user;
                
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Invalid username or password!", 
                    "Login Failed", 
                    JOptionPane.ERROR_MESSAGE);
                return null;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error connecting to server: " + e.getMessage(), 
                "Connection Error", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Helper method to validate employee roles
     */
    private boolean isValidEmployeeRole(String role) {
        return "Clerk".equals(role) || "Driver".equals(role) || "Manager".equals(role);
    }
}