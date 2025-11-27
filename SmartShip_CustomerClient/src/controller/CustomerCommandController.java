package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * Controller for customer operations
 * Communicates with server for all customer-related commands
 */
public class CustomerCommandController {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5000;
    
    /**
     * Get customer profile information
     */
    public Map<String, String> getCustomerProfile(int userId) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("GET_CUSTOMER_INFO");
            out.flush();
            
            out.writeObject(userId);
            out.flush();
            
            Object response = in.readObject();
            if (response instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, String> profileInfo = (Map<String, String>) response;
                return profileInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving profile: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    /**
     * Update customer profile
     */
    public boolean updateCustomerProfile(int userId, String email, String phone, String address) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("UPDATE_CUSTOMER_INFO");
            out.flush();
            
            out.writeObject(userId);
            out.flush();
            
            out.writeObject(email);
            out.flush();
            
            out.writeObject(phone);
            out.flush();
            
            out.writeObject(address);
            out.flush();
            
            Object response = in.readObject();
            if (response instanceof Boolean) {
                return (Boolean) response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating profile: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    /**
     * Create a new shipment
     */
    public String createShipment(int userId, String senderInfo, String recipientInfo, 
                                double weight, String dimensions, String type, int zone, String recipientAddress) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("CREATE_SHIPMENT");
            out.flush();
            
            out.writeObject(userId);
            out.flush();
            
            out.writeObject(senderInfo);
            out.flush();
            
            out.writeObject(recipientInfo);
            out.flush();
            
            out.writeObject(weight);
            out.flush();
            
            out.writeObject(dimensions);
            out.flush();
            
            out.writeObject(type);
            out.flush();
            
            out.writeObject(zone);
            out.flush();
            
            out.writeObject(recipientAddress);
            out.flush();
            
            Object response = in.readObject();
            if (response instanceof String) {
                return (String) response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error creating shipment: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    /**
     * Get all customer shipments
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> getCustomerShipments(int userId) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("GET_CUSTOMER_ORDERS");
            out.flush();
            
            out.writeObject(userId);
            out.flush();
            
            Object response = in.readObject();
            if (response instanceof List) {
                return (List<Map<String, String>>) response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving shipments: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    /**
     * Track a package by tracking number
     */
    public Map<String, String> trackPackage(String trackingNumber) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("TRACK_PACKAGE");
            out.flush();
            
            out.writeObject(trackingNumber);
            out.flush();
            
            Object response = in.readObject();
            if (response instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, String> trackingInfo = (Map<String, String>) response;
                return trackingInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Get customer invoices
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> getCustomerInvoices(int userId) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("GET_CUSTOMER_INVOICES");
            out.flush();
            
            out.writeObject(userId);
            out.flush();
            
            Object response = in.readObject();
            if (response instanceof List) {
                return (List<Map<String, String>>) response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving invoices: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    /**
     * Make payment for an invoice
     */
    public boolean makePayment(int invoiceId, double amount, String paymentMethod) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("MAKE_PAYMENT");
            out.flush();
            
            out.writeObject(invoiceId);
            out.flush();
            
            out.writeObject(amount);
            out.flush();
            
            out.writeObject(paymentMethod);
            out.flush();
            
            Object response = in.readObject();
            if (response instanceof Boolean) {
                return (Boolean) response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error processing payment: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    public Map<String, String> makePaymentAndReturnReceipt(int invoiceId, double amount, String paymentMethod) {
        try {
            // Connect to server
            Socket socket = new Socket("localhost", 5000);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            // Send the action name
            out.writeObject("MAKE_PAYMENT_AND_GET_RECEIPT");
            out.flush();
            
            // Send the payment details
            out.writeObject(invoiceId);
            out.writeObject(amount);
            out.writeObject(paymentMethod);
            out.flush();
            
            // Receive the receipt map from server
            @SuppressWarnings("unchecked")
            Map<String, String> receipt = (Map<String, String>) in.readObject();
            
            // Clean up
            in.close();
            out.close();
            socket.close();
            
            return receipt;
            
        } catch (Exception e) {
            System.err.println("Error processing payment: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    
}