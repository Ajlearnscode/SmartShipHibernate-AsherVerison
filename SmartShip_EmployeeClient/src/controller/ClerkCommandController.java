package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class ClerkCommandController {
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
    public List<Map<String, String>> getAllOrders() {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("GET_ALL_ORDERS");
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
    
    
    /**
     * Update the status and payment status of a shipment
     */
    public boolean updateShipmentStatus(String trackingNumber, String newStatus, String newPaymentStatus) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Tell server what command we want
            out.writeObject("UPDATE_SHIPMENT");
            out.flush();

            // Send the shipment values to update
            out.writeObject(trackingNumber);
            out.flush();

            out.writeObject(newStatus);
            out.flush();

            out.writeObject(newPaymentStatus);
            out.flush();

            // Read result from server
            Object response = in.readObject();
            if (response instanceof Boolean) {
                return (Boolean) response;
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                null,
                "Error updating shipment: " + e.getMessage(),
                "Update Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

        return false;
    }
    
    
    //WE WANT SPECIFIC CUSTOMER SHIPMENTS
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
   

   
    

//#########################################################################################################################################################################################################################################################
  //NEW ADDITIONS - November 26, 2025 (Asher Maxwell)
    
    public boolean updateShipmentAssignment(String trackingNumber, String driver, String vehicle, String route) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("UPDATE_SHIPMENT_ASSIGNMENT"); // Needs to be registered
            out.flush();
            out.writeObject(trackingNumber);
            out.writeObject(driver);
            out.writeObject(vehicle);
            out.writeObject(route);
            out.flush();
            
            Object response = in.readObject();
            return response instanceof Boolean && (Boolean) response;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get available drivers from database
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> getAvailableDrivers() {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("GET_AVAILABLE_DRIVERS"); //command in the Commands and Command Registry
            out.flush();
            
            Object response = in.readObject();
            if (response instanceof List) {
                return (List<Map<String, String>>) response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving drivers: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return new ArrayList<>(); // Return empty list instead of null
    }

    /**
     * Get available vehicles from database
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> getAvailableVehicles() {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("GET_AVAILABLE_VEHICLES"); //command in the Commands and Command Registry
            out.flush();
            
            Object response = in.readObject();
            if (response instanceof List) {
                return (List<Map<String, String>>) response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving vehicles: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return new ArrayList<>(); // Return empty list instead of null
    }

    /**
     * Assign shipment to driver, vehicle, and route
     */
    public boolean assignShipment(String trackingNumber, Integer driverId, 
                                  Integer vehicleId, String route) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("ASSIGN_SHIPMENT_FULL"); //command in the Commands and Command Registry
            out.flush();
            out.writeObject(trackingNumber);
            out.flush();
            out.writeObject(driverId);
            out.flush();
            out.writeObject(vehicleId);
            out.flush();
            out.writeObject(route);
            out.flush();
            
            Object response = in.readObject();
            if (response instanceof Boolean) {
                return (Boolean) response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error assigning shipment: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    /**
     * Get shipment assignments
     */
    @SuppressWarnings("unchecked")
    public Map<String, Map<String, String>> getShipmentAssignments() {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("GET_SHIPMENT_ASSIGNMENTS"); //command in the Commands and Command Registry
            out.flush();
            
            Object response = in.readObject();
            if (response instanceof Map) {
                return (Map<String, Map<String, String>>) response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving assignments: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return new HashMap<>(); // Return empty map instead of null
    }

    /**
     * Get all orders with customer names included
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> getAllOrdersWithCustomerNames() {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("GET_ALL_ORDERS_WITH_CUSTOMER_NAMES");
            out.flush();
            
            Object response = in.readObject();
            if (response instanceof List) {
                List<Map<String, String>> result = (List<Map<String, String>>) response;
                System.out.println("Retrieved " + result.size() + " shipments with customer names");
                return result;
            } else {
                System.out.println("Unexpected response type: " + (response != null ? response.getClass() : "null"));
            }
        } catch (Exception e) {
            System.err.println("Error in getAllOrdersWithCustomerNames: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving shipments: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return new ArrayList<>(); // Return empty list instead of null
    }
    //END OF NEW ADDITIONS
  //#########################################################################################################################################################################################################################################################


}
