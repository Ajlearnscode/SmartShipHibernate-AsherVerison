package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

public class SignUpController {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5000; //  server runs on port 5000
    
    public boolean register(String username, String password, String email, String phone, String address, String role) {
        // Validation
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
       
        
        if (email == null || email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (phone == null || phone.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phone cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (address == null || address.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Address cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Connect to server and register
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        
        try {
            // Establish connection
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            
            // Send REGISTER command to backend which the network_server that is the SmartShip_Server
            out.writeObject("REGISTER");
            out.flush();
            
            // Send user data in the exact order expected by RegisterCommand
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
                        "Registration successful! Please login.", 
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
            // Close resources
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}