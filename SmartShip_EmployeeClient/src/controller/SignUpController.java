package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

public class SignUpController {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5000;
    
    /**
     * Register new employee (Clerk, Manager, or Driver)
     * Employees must select their role during registration
     */
    public boolean register(String username, String password, String email, 
                           String phone, String address, String role) {
        // Validation
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username cannot be empty!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password cannot be empty!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (email == null || email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email cannot be empty!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (phone == null || phone.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phone cannot be empty!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (address == null || address.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Address cannot be empty!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate role - only allow employee roles
        if (!isValidEmployeeRole(role)) {
            JOptionPane.showMessageDialog(null, "Invalid employee role selected!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Connect to server and register
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            
            // Send REGISTER command
            out.writeObject("REGISTER");
            out.flush();
            
            // Send employee data
            out.writeObject(username);
            out.flush();
            
            out.writeObject(password);
            out.flush();
            
            out.writeObject(email);
            out.flush();
            
            out.writeObject(phone);
            out.flush();
            
            out.writeObject(address);
            out.flush();
            
            out.writeObject(role);
            out.flush();
            
            // Read response from server
            Object response = in.readObject();
            
            if (response instanceof Boolean) {
                boolean success = (Boolean) response;
                if (success) {
                    JOptionPane.showMessageDialog(null, 
                        "Registration successful! You are now registered as " + role + ". Please login.", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Registration failed. Username may already exist.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Unexpected response from server.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error connecting to server: " + e.getMessage(), 
                "Connection Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
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