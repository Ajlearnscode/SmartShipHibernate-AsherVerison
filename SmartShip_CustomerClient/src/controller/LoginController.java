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
            // Establish connection
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            
            // Send LOGIN command
            out.writeObject("LOGIN");
            out.flush();
            
            // Send credentials
            out.writeObject(username);
            out.flush();
            
            out.writeObject(password);
            out.flush();
            
            // Read response from server
            Object response = in.readObject();
            
            if (response instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, String> userInfo = (Map<String, String>) response;
                
                // Create User object from response
                User user = new User();
                user.setUserId(Integer.parseInt(userInfo.get("userId")));
                user.setUsername(userInfo.get("username"));
                user.setEmail(userInfo.get("email"));
                user.setPhone(userInfo.get("phone"));
                user.setAddress(userInfo.get("address"));
                user.setRole(userInfo.get("role"));
                
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