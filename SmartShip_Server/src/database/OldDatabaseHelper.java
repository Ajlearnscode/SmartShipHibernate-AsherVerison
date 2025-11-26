package database;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.*;

/**
 * Handles all database operations for the SmartShip system.
 * Now inserts into role-specific tables (customers, managers, drivers, clerks)
 * in addition to the main users table.
 */
public class OldDatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/sspms";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    /**
     * Creates a connection to the database.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    /**
     * REGISTER NEW USER - UPDATED
     * Inserts into users table AND the appropriate role-specific table.
     * 
     * @return true if registration succeeded, false if it failed
     */
    public static boolean registerUser(String username, String password, String email, 
                                      String phone, String address, String role) {
        // First, insert into users table
        String userSql = "INSERT INTO users (username, password, email, phone, address, role) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Insert into users table
            userStmt.setString(1, username);
            userStmt.setString(2, password);
            userStmt.setString(3, email);
            userStmt.setString(4, phone);
            userStmt.setString(5, address);
            userStmt.setString(6, role);
            
            int rowsAffected = userStmt.executeUpdate();
            if (rowsAffected > 0) {
                // Get the generated user_id
                ResultSet generatedKeys = userStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    
                    // Now insert into role-specific table
                    boolean roleInserted = insertIntoRoleTable(conn, userId, role);
                    
                    if (roleInserted) {
                        return true;
                    } else {
                        // Rollback: delete from users table if role insert fails
                        deleteUserById(userId);
                        return false;
                    }
                }
            }
            return false;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Helper method to insert into role-specific tables
     * Inserts the new user into the appropriate table based on their role
     */
    private static boolean insertIntoRoleTable(Connection conn, int userId, String role) {
        try {
            switch (role) {
                case "Customer":
                    return insertCustomer(conn, userId);
                case "Manager":
                    return insertManager(conn, userId);
                case "Driver":
                    return insertDriver(conn, userId);
                case "Clerk":
                    return insertClerk(conn, userId);
                default:
                    return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Insert into customers table
     */
    private static boolean insertCustomer(Connection conn, int userId) throws SQLException {
        String sql = "INSERT INTO customers (user_id, membership_tier) VALUES (?, 'Standard')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Insert into managers table
     */
    private static boolean insertManager(Connection conn, int userId) throws SQLException {
        String sql = "INSERT INTO managers (user_id, department) VALUES (?, 'Operations')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Insert into drivers table
     */
    private static boolean insertDriver(Connection conn, int userId) throws SQLException {
        String sql = "INSERT INTO drivers (user_id, status) VALUES (?, 'Active')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Insert into clerks table
     */
    private static boolean insertClerk(Connection conn, int userId) throws SQLException {
        String sql = "INSERT INTO clerks (user_id, department) VALUES (?, 'Operations')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Helper method to delete user (used if role insertion fails)
     */
    private static void deleteUserById(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * LOGIN - UNCHANGED
     * Checks if username and password match a user in the database.
     * 
     * @return Map with user info if login succeeds, null if it fails
     */
    public static Map<String, String> login(String username, String password) {
        String sql = "SELECT user_id, username, email, phone, address, role FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Map<String, String> userInfo = new HashMap<>();
                userInfo.put("userId", String.valueOf(rs.getInt("user_id")));
                userInfo.put("username", rs.getString("username"));
                userInfo.put("email", rs.getString("email"));
                userInfo.put("phone", rs.getString("phone"));
                userInfo.put("address", rs.getString("address"));
                userInfo.put("role", rs.getString("role"));
                return userInfo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * GET CUSTOMER INFO - UPDATED
     * Now retrieves from both users and customers tables
     */
    public static Map<String, String> getCustomerInfo(int userId) {
        String sql = "SELECT u.*, c.company_name, c.account_balance, c.membership_tier, c.total_shipments " +
                    "FROM users u LEFT JOIN customers c ON u.user_id = c.user_id WHERE u.user_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Map<String, String> info = new HashMap<>();
                info.put("username", rs.getString("username"));
                info.put("email", rs.getString("email"));
                info.put("phone", rs.getString("phone"));
                info.put("address", rs.getString("address"));
                info.put("role", rs.getString("role"));
                // Customer-specific fields
                if (rs.getString("company_name") != null) {
                    info.put("companyName", rs.getString("company_name"));
                    info.put("accountBalance", String.valueOf(rs.getDouble("account_balance")));
                    info.put("membershipTier", rs.getString("membership_tier"));
                    info.put("totalShipments", String.valueOf(rs.getInt("total_shipments")));
                }
                return info;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * GET EMPLOYEE INFO - NEW METHOD
     * Retrieves employee-specific information based on role
     */
    public static Map<String, String> getEmployeeInfo(int userId, String role) {
        Map<String, String> info = new HashMap<>();
        
        try (Connection conn = getConnection()) {
            // Get basic user info
            String userSql = "SELECT * FROM users WHERE user_id = ?";
            try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
                userStmt.setInt(1, userId);
                ResultSet rs = userStmt.executeQuery();
                
                if (rs.next()) {
                    info.put("username", rs.getString("username"));
                    info.put("email", rs.getString("email"));
                    info.put("phone", rs.getString("phone"));
                    info.put("address", rs.getString("address"));
                    info.put("role", rs.getString("role"));
                }
            }
            
            // Get role-specific info
            if ("Manager".equals(role)) {
                String managerSql = "SELECT * FROM managers WHERE user_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(managerSql)) {
                    stmt.setInt(1, userId);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        info.put("department", rs.getString("department"));
                        info.put("employeeId", rs.getString("employee_id"));
                        if (rs.getDate("hire_date") != null) {
                            info.put("hireDate", rs.getDate("hire_date").toString());
                        }
                    }
                }
            } else if ("Driver".equals(role)) {
                String driverSql = "SELECT * FROM drivers WHERE user_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(driverSql)) {
                    stmt.setInt(1, userId);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        info.put("licenseNumber", rs.getString("license_number"));
                        if (rs.getDate("license_expiry") != null) {
                            info.put("licenseExpiry", rs.getDate("license_expiry").toString());
                        }
                        info.put("totalDeliveries", String.valueOf(rs.getInt("total_deliveries")));
                        info.put("rating", String.valueOf(rs.getDouble("rating")));
                        info.put("status", rs.getString("status"));
                    }
                }
            } else if ("Clerk".equals(role)) {
                String clerkSql = "SELECT * FROM clerks WHERE user_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(clerkSql)) {
                    stmt.setInt(1, userId);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        info.put("department", rs.getString("department"));
                        info.put("employeeId", rs.getString("employee_id"));
                        info.put("shiftTime", rs.getString("shift_time"));
                        if (rs.getDate("hire_date") != null) {
                            info.put("hireDate", rs.getDate("hire_date").toString());
                        }
                    }
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return info;
    }
    
    /**
     * UPDATE CUSTOMER INFO - UNCHANGED
     * Lets users edit their email, phone, and address.
     */
    public static boolean updateCustomerInfo(int userId, String email, String phone, String address) {
        String sql = "UPDATE users SET email = ?, phone = ?, address = ? WHERE user_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setString(2, phone);
            stmt.setString(3, address);
            stmt.setInt(4, userId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * CREATE SHIPMENT - UNCHANGED
     * Customer creates a new delivery order.
     */
    public static String createShipment(int userId, String senderInfo, String recipientInfo, 
                                       double weight, String dimensions, String type, int zone) {
        String sql = "INSERT INTO shipments (user_id, sender_info, recipient_info, weight, " +
                    "dimensions, package_type, zone, status, tracking_number, cost) VALUES (?, ?, ?, ?, ?, ?, ?, 'Pending', ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String trackingNumber = "SHIP" + System.currentTimeMillis();
            double cost = calculateShippingCost(weight, zone, type);
            
            stmt.setInt(1, userId);
            stmt.setString(2, senderInfo);
            stmt.setString(3, recipientInfo);
            stmt.setDouble(4, weight);
            stmt.setString(5, dimensions);
            stmt.setString(6, type);
            stmt.setInt(7, zone);
            stmt.setString(8, trackingNumber);
            stmt.setDouble(9, cost);
            
            stmt.executeUpdate();
            return trackingNumber;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Helper method to calculate shipping cost
     */
    private static double calculateShippingCost(double weight, int zone, String type) {
        double baseCost = weight * 5.0;
        double zoneCost = zone * 10.0;
        double typeCost = type.equals("Express") ? 20.0 : (type.equals("Fragile") ? 15.0 : 0.0);
        return baseCost + zoneCost + typeCost;
    }
    
    /**
     * GET CUSTOMER ORDERS - UNCHANGED
     */
    public static List<Map<String, String>> getCustomerOrders(int userId) {
        String sql = "SELECT shipment_id, tracking_number, recipient_info, weight, package_type, zone, status, cost, payment_status " +
                    "FROM shipments WHERE user_id = ? ORDER BY created_at DESC";
        List<Map<String, String>> orders = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, String> order = new HashMap<>();
                order.put("shipmentId", String.valueOf(rs.getInt("shipment_id")));
                order.put("trackingNumber", rs.getString("tracking_number"));
                order.put("recipientInfo", rs.getString("recipient_info"));
                order.put("weight", String.valueOf(rs.getDouble("weight")));
                order.put("packageType", rs.getString("package_type"));
                order.put("zone", String.valueOf(rs.getInt("zone")));
                order.put("status", rs.getString("status"));
                order.put("cost", String.format("%.2f", rs.getDouble("cost")));
                order.put("paymentStatus", rs.getString("payment_status"));
                orders.add(order);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    /**
     * MAKE PAYMENT - UNCHANGED
     */
    public static boolean makePayment(int shipmentId, String paymentMethod) {
        String sql = "UPDATE shipments SET payment_status = 'Paid', payment_method = ? WHERE shipment_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, paymentMethod);
            stmt.setInt(2, shipmentId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * GET PENDING SHIPMENTS - UNCHANGED
     */
    public static List<Map<String, String>> getPendingShipments() {
        String sql = "SELECT s.shipment_id, s.tracking_number, s.weight, s.zone, s.package_type, u.username " +
                    "FROM shipments s JOIN users u ON s.user_id = u.user_id WHERE s.status = 'Pending'";
        List<Map<String, String>> shipments = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Map<String, String> shipment = new HashMap<>();
                shipment.put("shipmentId", String.valueOf(rs.getInt("shipment_id")));
                shipment.put("trackingNumber", rs.getString("tracking_number"));
                shipment.put("weight", String.valueOf(rs.getDouble("weight")));
                shipment.put("zone", String.valueOf(rs.getInt("zone")));
                shipment.put("packageType", rs.getString("package_type"));
                shipment.put("customer", rs.getString("username"));
                shipments.add(shipment);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shipments;
    }
    
    /**
     * ASSIGN SHIPMENT TO VEHICLE - UNCHANGED
     */
    public static boolean assignShipmentToVehicle(int shipmentId, int vehicleId) {
        String sql = "UPDATE shipments SET vehicle_id = ?, status = 'Assigned' WHERE shipment_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, vehicleId);
            stmt.setInt(2, shipmentId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * GET DRIVER DELIVERIES - UNCHANGED
     */
    public static List<Map<String, String>> getDriverDeliveries(int userId) {
        String sql = "SELECT s.tracking_number, s.recipient_info, s.status, s.address " +
                    "FROM shipments s JOIN vehicles v ON s.vehicle_id = v.vehicle_id " +
                    "WHERE v.driver_id = ? AND s.status IN ('Assigned', 'In Transit')";
        List<Map<String, String>> deliveries = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, String> delivery = new HashMap<>();
                delivery.put("trackingNumber", rs.getString("tracking_number"));
                delivery.put("recipientInfo", rs.getString("recipient_info"));
                delivery.put("status", rs.getString("status"));
                delivery.put("address", rs.getString("address"));
                deliveries.add(delivery);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deliveries;
    }
    
    /**
     * UPDATE DELIVERY STATUS - UNCHANGED
     */
    public static boolean updateDeliveryStatus(String trackingNumber, String status) {
        String sql = "UPDATE shipments SET status = ? WHERE tracking_number = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setString(2, trackingNumber);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * GENERATE DAILY REPORT - UNCHANGED
     */
    public static String generateDailyReport() {
        String sql = "SELECT COUNT(*) as total, SUM(weight) as total_weight, SUM(cost) as total_revenue " +
                    "FROM shipments WHERE DATE(created_at) = CURDATE()";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return "DAILY REPORT:\n" +
                       "Total Shipments: " + rs.getInt("total") + "\n" +
                       "Total Weight: " + rs.getDouble("total_weight") + " kg\n" +
                       "Total Revenue: $" + String.format("%.2f", rs.getDouble("total_revenue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Error generating report";
    }
 // ADD THESE METHODS TO YOUR DatabaseHelper.java FILE

    /**
     * GET SHIPMENT BY TRACKING NUMBER
     * Returns shipment details for tracking
     */
    public static Map<String, String> getShipmentByTrackingNumber(String trackingNumber) {
        String sql = "SELECT shipment_id, tracking_number, recipient_info, weight, package_type, zone, status, cost, payment_status, address " +
                    "FROM shipments WHERE tracking_number = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, trackingNumber);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Map<String, String> shipment = new HashMap<>();
                shipment.put("shipmentId", String.valueOf(rs.getInt("shipment_id")));
                shipment.put("trackingNumber", rs.getString("tracking_number"));
                shipment.put("recipientInfo", rs.getString("recipient_info"));
                shipment.put("weight", String.valueOf(rs.getDouble("weight")));
                shipment.put("packageType", rs.getString("package_type"));
                shipment.put("zone", String.valueOf(rs.getInt("zone")));
                shipment.put("status", rs.getString("status"));
                shipment.put("cost", String.format("%.2f", rs.getDouble("cost")));
                shipment.put("paymentStatus", rs.getString("payment_status"));
                shipment.put("address", rs.getString("address"));
                return shipment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET CUSTOMER INVOICES
     * Returns all invoices for a specific customer
     */
    public static List<Map<String, String>> getCustomerInvoices(int userId) {
        String sql = "SELECT i.invoice_id, i.shipment_id, i.subtotal, i.tax, i.discount, i.surcharge, i.total, i.status, i.invoice_date " +
                    "FROM invoices i " +
                    "JOIN shipments s ON i.shipment_id = s.shipment_id " +
                    "WHERE s.user_id = ? " +
                    "ORDER BY i.invoice_date DESC";
        
        List<Map<String, String>> invoices = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, String> invoice = new HashMap<>();
                invoice.put("invoiceId", String.valueOf(rs.getInt("invoice_id")));
                invoice.put("shipmentId", String.valueOf(rs.getInt("shipment_id")));
                invoice.put("subtotal", String.format("%.2f", rs.getDouble("subtotal")));
                invoice.put("tax", String.format("%.2f", rs.getDouble("tax")));
                invoice.put("discount", String.format("%.2f", rs.getDouble("discount")));
                invoice.put("surcharge", String.format("%.2f", rs.getDouble("surcharge")));
                invoice.put("total", String.format("%.2f", rs.getDouble("total")));
                invoice.put("status", rs.getString("status"));
                
                java.sql.Timestamp timestamp = rs.getTimestamp("invoice_date");
                if (timestamp != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    invoice.put("invoiceDate", sdf.format(new java.util.Date(timestamp.getTime())));
                }
                
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    /**
     * MAKE INVOICE PAYMENT
     * Process payment for an invoice
     */
    public static boolean makeInvoicePayment(int invoiceId, double amount, String paymentMethod) {
        String updateInvoiceSql = "UPDATE invoices SET status = 'Paid' WHERE invoice_id = ?";
        String insertPaymentSql = "INSERT INTO payments (invoice_id, amount, payment_method, payment_date, status) " +
                                 "VALUES (?, ?, ?, NOW(), 'Completed')";
        
        try (Connection conn = getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateInvoiceSql);
             PreparedStatement paymentStmt = conn.prepareStatement(insertPaymentSql)) {
            
            // Update invoice status
            updateStmt.setInt(1, invoiceId);
            updateStmt.executeUpdate();
            
            // Insert payment record
            paymentStmt.setInt(1, invoiceId);
            paymentStmt.setDouble(2, amount);
            paymentStmt.setString(3, paymentMethod);
            paymentStmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * CREATE SHIPMENT - UPDATED VERSION
     * Now stores recipient address in shipments table
     */
    public static String createShipment(int userId, String senderInfo, String recipientInfo, 
                                       double weight, String dimensions, String type, int zone, String recipientAddress) {
        String sql = "INSERT INTO shipments (user_id, sender_info, recipient_info, weight, " +
                    "dimensions, package_type, zone, status, tracking_number, cost, address) VALUES (?, ?, ?, ?, ?, ?, ?, 'Pending', ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String trackingNumber = "SHIP" + System.currentTimeMillis();
            double cost = calculateShippingCost(weight, zone, type);
            
            stmt.setInt(1, userId);
            stmt.setString(2, senderInfo);
            stmt.setString(3, recipientInfo);
            stmt.setDouble(4, weight);
            stmt.setString(5, dimensions);
            stmt.setString(6, type);
            stmt.setInt(7, zone);
            stmt.setString(8, trackingNumber);
            stmt.setDouble(9, cost);
            stmt.setString(10, recipientAddress);
            
            stmt.executeUpdate();
            
            // Create invoice for this shipment
            createInvoiceForShipment(userId, trackingNumber, cost);
            
            return trackingNumber;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Helper method to create invoice when shipment is created
     */
    private static void createInvoiceForShipment(int userId, String trackingNumber, double subtotal) {
        String getShipmentSql = "SELECT shipment_id FROM shipments WHERE tracking_number = ?";
        String getCustomerSql = "SELECT customer_id FROM customers WHERE user_id = ?";
        String createInvoiceSql = "INSERT INTO invoices (customer_id, shipment_id, subtotal, tax, discount, surcharge, total, status, invoice_date) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, 'Unpaid', NOW())";
        
        try (Connection conn = getConnection();
             PreparedStatement getShipmentStmt = conn.prepareStatement(getShipmentSql);
             PreparedStatement getCustomerStmt = conn.prepareStatement(getCustomerSql);
             PreparedStatement createInvoiceStmt = conn.prepareStatement(createInvoiceSql)) {
            
            // Get shipment ID
            getShipmentStmt.setString(1, trackingNumber);
            ResultSet shipmentRs = getShipmentStmt.executeQuery();
            int shipmentId = 0;
            if (shipmentRs.next()) {
                shipmentId = shipmentRs.getInt("shipment_id");
            }
            
            // Get customer ID
            getCustomerStmt.setInt(1, userId);
            ResultSet customerRs = getCustomerStmt.executeQuery();
            int customerId = 0;
            if (customerRs.next()) {
                customerId = customerRs.getInt("customer_id");
            }
            
            // Create invoice with tax (assume 10% tax)
            double tax = subtotal * 0.10;
            double total = subtotal + tax;
            
            createInvoiceStmt.setInt(1, customerId);
            createInvoiceStmt.setInt(2, shipmentId);
            createInvoiceStmt.setDouble(3, subtotal);
            createInvoiceStmt.setDouble(4, tax);
            createInvoiceStmt.setDouble(5, 0.0); // discount
            createInvoiceStmt.setDouble(6, 0.0); // surcharge
            createInvoiceStmt.setDouble(7, total);
            createInvoiceStmt.setString(8, "Unpaid");
            
            createInvoiceStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    
}