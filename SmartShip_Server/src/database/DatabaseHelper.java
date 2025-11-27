package database;

import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Handles all database operations for the SmartShip system.
 * Cleaned and corrected:
 *  - Single createShipment method (with recipientAddress)
 *  - Transactional registerUser (users + role table)
 *  - Transactional invoice creation and payment
 *  - Defensive checks (customer record existence, parameter counts)
 *  - Added wrapper methods expected by GUI/controller
 */
public class DatabaseHelper {
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
     * REGISTER NEW USER (transactional)
     * Inserts into users table AND the appropriate role-specific table.
     *
     * @return true if registration succeeded, false if it failed
     */
    public static boolean registerUser(String username, String password, String email,
                                      String phone, String address, String role) {
        String userSql = "INSERT INTO users (username, password, email, phone, address, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {

            conn.setAutoCommit(false); // begin transaction

            userStmt.setString(1, username);
            userStmt.setString(2, password);
            userStmt.setString(3, email);
            userStmt.setString(4, phone);
            userStmt.setString(5, address);
            userStmt.setString(6, role);

            int rowsAffected = userStmt.executeUpdate();
            if (rowsAffected == 0) {
                conn.rollback();
                return false;
            }

            ResultSet generatedKeys = userStmt.getGeneratedKeys();
            if (!generatedKeys.next()) {
                conn.rollback();
                return false;
            }

            int userId = generatedKeys.getInt(1);

            // Insert into role-specific table using same connection (transactional)
            boolean roleInserted = insertIntoRoleTable(conn, userId, role);
            if (!roleInserted) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Helper method to insert into role-specific tables
     * Uses the provided connection (transactional scope)
     */
    private static boolean insertIntoRoleTable(Connection conn, int userId, String role) throws SQLException {
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
    }

    private static boolean insertCustomer(Connection conn, int userId) throws SQLException {
        String sql = "INSERT INTO customers (user_id, membership_tier) VALUES (?, 'Standard')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    private static boolean insertManager(Connection conn, int userId) throws SQLException {
        String sql = "INSERT INTO managers (user_id, department) VALUES (?, 'Operations')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    private static boolean insertDriver(Connection conn, int userId) throws SQLException {
        String sql = "INSERT INTO drivers (user_id, status) VALUES (?, 'Active')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    private static boolean insertClerk(Connection conn, int userId) throws SQLException {
        String sql = "INSERT INTO clerks (user_id, department) VALUES (?, 'Operations')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * LOGIN
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
     * GET CUSTOMER INFO
     */
    public static Map<String, String> getCustomerInfo(int userId) {
        String sql = "SELECT u.*, c.customer_id, c.company_name, c.account_balance, c.membership_tier, c.total_shipments " +
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

                // Customer-specific fields (may be null)
                int customerId = rs.getInt("customer_id");
                if (!rs.wasNull() && customerId != 0) {
                    info.put("customerId", String.valueOf(customerId));
                    info.put("companyName", rs.getString("company_name"));
                    info.put("accountBalance", String.format("%.2f", rs.getDouble("account_balance")));
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
     * Wrapper expected by GUI/controller (keeps naming consistent)
     */
    public static Map<String, String> getCustomerProfile(int userId) {
        return getCustomerInfo(userId);
    }

    /**
     * GET EMPLOYEE INFO
     */
    public static Map<String, String> getEmployeeInfo(int userId, String role) {
        Map<String, String> info = new HashMap<>();

        try (Connection conn = getConnection()) {
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
     * UPDATE CUSTOMER INFO
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
     * CREATE SHIPMENT (single definitive version)
     * Stores recipient address and creates invoice (transactionally)
     *
     * @return trackingNumber on success, null on failure
     */
		    public static String createShipment(int userId, String senderInfo, String recipientInfo,
		            double weight, String dimensions, String type, int zone, String recipientAddress) {
		    		String insertShipmentSql = "INSERT INTO shipments (user_id, sender_info, recipient_info, weight, dimensions, package_type, zone, status, tracking_number, cost, address, created_at) " +
		    									"VALUES (?, ?, ?, ?, ?, ?, ?, 'Pending', ?, ?, ?, NOW())";
		
		    		try (Connection conn = getConnection();
		    				PreparedStatement insertStmt = conn.prepareStatement(insertShipmentSql)) {
		
		    					conn.setAutoCommit(false);
		
								String trackingNumber = "SHIP" + System.currentTimeMillis();
								double cost = calculateShippingCost(weight, zone, type);
								
								insertStmt.setInt(1, userId);
								insertStmt.setString(2, senderInfo);
								insertStmt.setString(3, recipientInfo);
								insertStmt.setDouble(4, weight);
								insertStmt.setString(5, dimensions);
								insertStmt.setString(6, type);
								insertStmt.setInt(7, zone);
								insertStmt.setString(8, trackingNumber);
								insertStmt.setDouble(9, cost);
								insertStmt.setString(10, recipientAddress);
								
								int inserted = insertStmt.executeUpdate();
								if (inserted == 0) {
								conn.rollback();
								return null;
								}
		
				// Create invoice for this shipment (transactional; uses same connection)
					boolean invoiceCreated = createInvoiceForShipment(conn, userId, trackingNumber, cost);
						if (!invoiceCreated) {
							conn.rollback();
							return null;
						}
				
						conn.commit();
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
        double typeCost = "Express".equals(type) ? 20.0 : ("Fragile".equals(type) ? 15.0 : 0.0);
        return baseCost + zoneCost + typeCost;
    }

    /**
     * GET CUSTOMER ORDERS
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
     * Wrapper expected by GUI/controller
     */
    public static List<Map<String, String>> getCustomerShipments(int userId) {
        return getCustomerOrders(userId);
    }

    /**
     * MAKE PAYMENT ON SHIPMENT (legacy)
     * Updates shipments table payment_status and payment_method.
     */
    public static boolean makeShipmentPayment(int shipmentId, String paymentMethod) {
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
     * GET PENDING SHIPMENTS
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
     * ASSIGN SHIPMENT TO VEHICLE (simple)
     * Note: For a strict many-to-many relationship and vehicle capacity checks, a proper assignment table and checks should be implemented.
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
     * GET DRIVER DELIVERIES
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
     * UPDATE DELIVERY STATUS
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
     * GENERATE DAILY REPORT
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

    /**
     * GET SHIPMENT BY TRACKING NUMBER
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

                Timestamp timestamp = rs.getTimestamp("invoice_date");
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
     * MAKE INVOICE PAYMENT (transactional)
     */
    public static boolean makeInvoicePayment(int invoiceId, double amount, String paymentMethod) {
        String selectInvoiceSql = "SELECT shipment_id FROM invoices WHERE invoice_id = ?";
        String updateInvoiceSql = "UPDATE invoices SET status = 'Paid' WHERE invoice_id = ?";
        String updateShipmentSql = "UPDATE shipments SET payment_status = 'Paid', payment_method = ? WHERE shipment_id = ?";
        String insertPaymentSql = "INSERT INTO payments (invoice_id, amount, payment_method, payment_date, status) VALUES (?, ?, ?, NOW(), 'Completed')";

        try (Connection conn = getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectInvoiceSql);
             PreparedStatement updateInvoiceStmt = conn.prepareStatement(updateInvoiceSql);
             PreparedStatement updateShipmentStmt = conn.prepareStatement(updateShipmentSql);
             PreparedStatement paymentStmt = conn.prepareStatement(insertPaymentSql)) {

            conn.setAutoCommit(false);

            // 1) find shipment_id related to the invoice
            int shipmentId = 0;
            selectStmt.setInt(1, invoiceId);
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    shipmentId = rs.getInt("shipment_id");
                } else {
                    System.err.println("makeInvoicePayment: invoice not found: " + invoiceId);
                    conn.rollback();
                    return false;
                }
            }

            // 2) update invoice status
            updateInvoiceStmt.setInt(1, invoiceId);
            updateInvoiceStmt.executeUpdate();

            // 3) update shipment payment status & method
            updateShipmentStmt.setString(1, paymentMethod);
            updateShipmentStmt.setInt(2, shipmentId);
            updateShipmentStmt.executeUpdate();

            // 4) insert payment record
            paymentStmt.setInt(1, invoiceId);
            paymentStmt.setDouble(2, amount);
            paymentStmt.setString(3, paymentMethod);
            paymentStmt.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                Connection conn = getConnection();
                conn.rollback();
                conn.close();
            } catch (Exception ex) {
                // ignore
            }
            return false;
        }
    }


    /**
     * Helper to create invoice for a shipment (uses provided connection; transactional)
     * Returns true if created successfully.
     */
    private static boolean createInvoiceForShipment(Connection conn, int userId, String trackingNumber, double subtotal) throws SQLException {
        String getShipmentSql = "SELECT shipment_id FROM shipments WHERE tracking_number = ?";
        String getCustomerSql = "SELECT customer_id FROM customers WHERE user_id = ?";
        String createInvoiceSql = "INSERT INTO invoices (customer_id, shipment_id, tracking_number, subtotal, tax, discount, surcharge, total, status, invoice_date) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        // Get shipment ID
        int shipmentId = 0;
        try (PreparedStatement getShipmentStmt = conn.prepareStatement(getShipmentSql)) {
            getShipmentStmt.setString(1, trackingNumber);
            ResultSet shipmentRs = getShipmentStmt.executeQuery();
            if (shipmentRs.next()) {
                shipmentId = shipmentRs.getInt("shipment_id");
            } else {
                System.err.println("createInvoiceForShipment: shipment not found for tracking " + trackingNumber);
                return false;
            }
        }

        // Get customer ID
        int customerId = 0;
        try (PreparedStatement getCustomerStmt = conn.prepareStatement(getCustomerSql)) {
            getCustomerStmt.setInt(1, userId);
            ResultSet customerRs = getCustomerStmt.executeQuery();
            if (customerRs.next()) {
                customerId = customerRs.getInt("customer_id");
            } else {
                System.err.println("createInvoiceForShipment: customer record not found for user " + userId);
                return false;
            }
        }

        // Prepare invoice numbers
        double tax = subtotal * 0.10; // 10% assumed
        double total = subtotal + tax;

        try (PreparedStatement createInvoiceStmt = conn.prepareStatement(createInvoiceSql)) {
            createInvoiceStmt.setInt(1, customerId);
            createInvoiceStmt.setInt(2, shipmentId);
            createInvoiceStmt.setString(3, trackingNumber);   // <-- store same tracking number
            createInvoiceStmt.setDouble(4, subtotal);
            createInvoiceStmt.setDouble(5, tax);
            createInvoiceStmt.setDouble(6, 0.0); // discount
            createInvoiceStmt.setDouble(7, 0.0); // surcharge
            createInvoiceStmt.setDouble(8, total);
            createInvoiceStmt.setString(9, "Unpaid");
            int rows = createInvoiceStmt.executeUpdate();
            return rows > 0;
        }
    }
    
    /**
     * Get complete invoice and shipment details by invoice ID
     * Used for building receipt information
     */
    public static Map<String, String> getInvoiceAndShipmentDetails(int invoiceId) {
        String sql = "SELECT i.invoice_id, i.shipment_id, i.tracking_number, i.total, i.status AS invoice_status, " +
                     "s.tracking_number AS ship_tracking, s.status AS ship_status, s.address, " +
                     "u.username, u.email, u.address AS user_address, u.phone " +
                     "FROM invoices i " +
                     "JOIN shipments s ON i.shipment_id = s.shipment_id " +
                     "JOIN users u ON s.user_id = u.user_id " +
                     "WHERE i.invoice_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Map<String, String> details = new HashMap<>();
                details.put("invoiceId", String.valueOf(rs.getInt("invoice_id")));
                details.put("shipmentId", String.valueOf(rs.getInt("shipment_id")));
                details.put("trackingNumber", rs.getString("ship_tracking"));
                details.put("customerName", rs.getString("username"));
                details.put("email", rs.getString("email"));
                details.put("address", rs.getString("user_address"));
                details.put("phone", rs.getString("phone"));
                details.put("status", rs.getString("ship_status"));
                details.put("total", String.format("%.2f", rs.getDouble("total")));
                
                return details;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching invoice and shipment details: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    
    
    public static boolean updateShipment(String trackingNumber, String newStatus, String newPaymentStatus) {
        String sql = "UPDATE shipments SET status = ?, payment_status = ? WHERE tracking_number = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setString(2, newPaymentStatus);  
            stmt.setString(3, trackingNumber);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


 //##########################################################################################################################################################################################################################################################
//NEW ADDITIONS -- November 27, 2025 (12:12 AM)
    
    /*
     * Method used to get every single shipment request that exists within 'shipments' database (used by the clerk)
     * Connected to:
     * Used by:
     * */
    public static List<Map<String, String>> getAllOrders() {
         String sql = "SELECT shipment_id, tracking_number, recipient_info, weight, " +
                      "package_type, zone, status, cost, payment_status " +
                      "FROM shipments ORDER BY created_at DESC";

         List<Map<String, String>> orders = new ArrayList<>();

         try (Connection conn = getConnection();
              PreparedStatement stmt = conn.prepareStatement(sql);
              ResultSet rs = stmt.executeQuery()) {

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
     * This method gets all the available drivers that exists within the database
     * Returns drivers with status 'Active'
     */
    public static List<Map<String, String>> getAvailableDrivers() {
        String sql = "SELECT d.driver_id, d.user_id, d.license_number, d.license_expiry, " +
                     "d.vehicle_id, d.total_deliveries, d.rating, d.status, " +
                     "u.username " +
                     "FROM drivers d " +
                     "JOIN users u ON d.user_id = u.user_id " +
                     "WHERE d.status = 'Active' " +
                     "ORDER BY u.username";
        
        List<Map<String, String>> drivers = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Map<String, String> driver = new HashMap<>();
                driver.put("driverId", String.valueOf(rs.getInt("driver_id")));
                driver.put("userId", String.valueOf(rs.getInt("user_id")));
                driver.put("username", rs.getString("username"));
                driver.put("licenseNumber", rs.getString("license_number"));
                driver.put("totalDeliveries", String.valueOf(rs.getInt("total_deliveries")));
                driver.put("rating", String.format("%.1f", rs.getDouble("rating")));
                driver.put("status", rs.getString("status"));
                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    /**
     * Method that gets all the available vehicles within the database so the clerk can assign shipment to a particular readily available vehicle
     * Returns vehicles with status indicating availability
     */
    public static List<Map<String, String>> getAvailableVehicles() {
        String sql = "SELECT vehicle_id, driver_id, vehicle_type, license_plate, " +
                     "capacity, status, current_weight, current_item_count, " +
                     "last_maintenance_date, next_maintenance_date " +
                     "FROM vehicles " +
                     "WHERE status IN ('Available', 'Active') " +
                     "ORDER BY vehicle_type, license_plate";
        
        List<Map<String, String>> vehicles = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Map<String, String> vehicle = new HashMap<>();
                vehicle.put("vehicleId", String.valueOf(rs.getInt("vehicle_id")));
                vehicle.put("vehicleType", rs.getString("vehicle_type"));
                vehicle.put("licensePlate", rs.getString("license_plate"));
                vehicle.put("capacity", String.format("%.2f", rs.getDouble("capacity")));
                vehicle.put("currentWeight", String.format("%.2f", rs.getDouble("current_weight")));
                vehicle.put("currentItemCount", String.valueOf(rs.getInt("current_item_count")));
                vehicle.put("status", rs.getString("status"));
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    /**
     * This method is responsible for assigning a shipment of a customer to an available driver, vehicle, and route
     * Updates shipment_assignments table and increments counters
     */
    public static boolean assignShipment(String trackingNumber, Integer driverId, 
                                         Integer vehicleId, String route) {
        // First check if tracking number exists in shipments table
        String checkShipmentSql = "SELECT tracking_number FROM shipments WHERE tracking_number = ?";
        
        // Insert or update assignment
        String upsertAssignmentSql = 
            "INSERT INTO shipment_assignments (tracking_number, driver_id, vehicle_id, route) " +
            "VALUES (?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE " +
            "driver_id = COALESCE(VALUES(driver_id), driver_id), " +
            "vehicle_id = COALESCE(VALUES(vehicle_id), vehicle_id), " +
            "route = COALESCE(VALUES(route), route), " +
            "updated_at = CURRENT_TIMESTAMP";
        
        String updateDriverSql = "UPDATE drivers SET total_deliveries = total_deliveries + 1 " +
                                 "WHERE driver_id = ? AND driver_id NOT IN " +
                                 "(SELECT driver_id FROM shipment_assignments WHERE tracking_number = ? AND driver_id IS NOT NULL)";
        
        String updateVehicleSql = "UPDATE vehicles SET current_item_count = current_item_count + 1 " +
                                  "WHERE vehicle_id = ? AND vehicle_id NOT IN " +
                                  "(SELECT vehicle_id FROM shipment_assignments WHERE tracking_number = ? AND vehicle_id IS NOT NULL)";
        
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            
            // Check if shipment exists
            try (PreparedStatement checkStmt = conn.prepareStatement(checkShipmentSql)) {
                checkStmt.setString(1, trackingNumber);
                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next()) {
                    System.err.println("Shipment not found: " + trackingNumber);
                    conn.rollback();
                    return false;
                }
            }
            
            // Insert or update assignment
            try (PreparedStatement stmt = conn.prepareStatement(upsertAssignmentSql)) {
                stmt.setString(1, trackingNumber);
                
                if (driverId != null) {
                    stmt.setInt(2, driverId);
                } else {
                    stmt.setNull(2, Types.INTEGER);
                }
                
                if (vehicleId != null) {
                    stmt.setInt(3, vehicleId);
                } else {
                    stmt.setNull(3, Types.INTEGER);
                }
                
                if (route != null && !route.isEmpty() && !route.equals("-- Select Route --")) {
                    stmt.setString(4, route);
                } else {
                    stmt.setNull(4, Types.VARCHAR);
                }
                
                stmt.executeUpdate();
            }
            
            // Update driver delivery count if driver assigned (only if new assignment)
            if (driverId != null) {
                try (PreparedStatement stmt = conn.prepareStatement(updateDriverSql)) {
                    stmt.setInt(1, driverId);
                    stmt.setString(2, trackingNumber);
                    stmt.executeUpdate();
                }
            }
            
            // Update vehicle item count if vehicle assigned (only if new assignment)
            if (vehicleId != null) {
                try (PreparedStatement stmt = conn.prepareStatement(updateVehicleSql)) {
                    stmt.setInt(1, vehicleId);
                    stmt.setString(2, trackingNumber);
                    stmt.executeUpdate();
                }
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns current driver, vehicle, and route assignments for shipments
     */
    public static Map<String, Map<String, String>> getShipmentAssignments() {
        String sql = "SELECT sa.tracking_number, " +
                     "sa.driver_id, u.username as driver_name, " +
                     "sa.vehicle_id, v.license_plate, v.vehicle_type, " +
                     "sa.route, " +
                     "sa.assigned_at, sa.updated_at " +
                     "FROM shipment_assignments sa " +
                     "LEFT JOIN drivers d ON sa.driver_id = d.driver_id " +
                     "LEFT JOIN users u ON d.user_id = u.user_id " +
                     "LEFT JOIN vehicles v ON sa.vehicle_id = v.vehicle_id";
        
        Map<String, Map<String, String>> assignments = new HashMap<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String trackingNumber = rs.getString("tracking_number");
                Map<String, String> assignment = new HashMap<>();
                
                int driverId = rs.getInt("driver_id");
                if (!rs.wasNull()) {
                    assignment.put("driverId", String.valueOf(driverId));
                    assignment.put("driverName", rs.getString("driver_name"));
                }
                
                int vehicleId = rs.getInt("vehicle_id");
                if (!rs.wasNull()) {
                    assignment.put("vehicleId", String.valueOf(vehicleId));
                    assignment.put("licensePlate", rs.getString("license_plate"));
                    assignment.put("vehicleType", rs.getString("vehicle_type"));
                }
                
                String route = rs.getString("route");
                if (route != null) {
                    assignment.put("route", route);
                }
                
                Timestamp assignedAt = rs.getTimestamp("assigned_at");
                if (assignedAt != null) {
                    assignment.put("assignedAt", assignedAt.toString());
                }
                
                Timestamp updatedAt = rs.getTimestamp("updated_at");
                if (updatedAt != null) {
                    assignment.put("updatedAt", updatedAt.toString());
                }
                
                assignments.put(trackingNumber, assignment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignments;
    }

    /**
     * Removes a specific assignment (driver, vehicle, or route) - this allows us to still retain automatic updates
     */
    public static boolean removeAssignment(String trackingNumber, String assignmentType) {
        String sql = "";
        
        switch (assignmentType.toLowerCase()) {
            case "driver":
                sql = "UPDATE shipment_assignments SET driver_id = NULL, updated_at = CURRENT_TIMESTAMP " +
                      "WHERE tracking_number = ?";
                break;
            case "vehicle":
                sql = "UPDATE shipment_assignments SET vehicle_id = NULL, updated_at = CURRENT_TIMESTAMP " +
                      "WHERE tracking_number = ?";
                break;
            case "route":
                sql = "UPDATE shipment_assignments SET route = NULL, updated_at = CURRENT_TIMESTAMP " +
                      "WHERE tracking_number = ?";
                break;
            default:
                return false;
        }
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trackingNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns all shipments assigned to a specific driver
     */
    public static List<Map<String, String>> getAssignmentsByDriver(int driverId) {
        String sql = "SELECT sa.tracking_number, sa.route, s.recipient_info, s.address, s.status " +
                     "FROM shipment_assignments sa " +
                     "JOIN shipments s ON sa.tracking_number = s.tracking_number " +
                     "WHERE sa.driver_id = ? " +
                     "ORDER BY sa.assigned_at DESC";
        
        List<Map<String, String>> assignments = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, String> assignment = new HashMap<>();
                assignment.put("trackingNumber", rs.getString("tracking_number"));
                assignment.put("route", rs.getString("route"));
                assignment.put("recipientInfo", rs.getString("recipient_info"));
                assignment.put("address", rs.getString("address"));
                assignment.put("status", rs.getString("status"));
                assignments.add(assignment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignments;
    }

    /**
     * Returns all shipments assigned to a specific vehicle
     */
    public static List<Map<String, String>> getAssignmentsByVehicle(int vehicleId) {
        String sql = "SELECT sa.tracking_number, sa.route, s.recipient_info, s.weight, s.status " +
                     "FROM shipment_assignments sa " +
                     "JOIN shipments s ON sa.tracking_number = s.tracking_number " +
                     "WHERE sa.vehicle_id = ? " +
                     "ORDER BY sa.assigned_at DESC";
        
        List<Map<String, String>> assignments = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vehicleId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, String> assignment = new HashMap<>();
                assignment.put("trackingNumber", rs.getString("tracking_number"));
                assignment.put("route", rs.getString("route"));
                assignment.put("recipientInfo", rs.getString("recipient_info"));
                assignment.put("weight", String.valueOf(rs.getDouble("weight")));
                assignment.put("status", rs.getString("status"));
                assignments.add(assignment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignments;
    }
    
    

    /**
     * Returns shipments with customer username included
     */
    public static List<Map<String, String>> getAllOrdersWithCustomerNames() {
        String sql = "SELECT s.shipment_id, s.tracking_number, s.recipient_info, s.weight, " +
                     "s.package_type, s.zone, s.status, s.cost, s.payment_status, s.payment_method, " +
                     "s.sender_info, s.created_at, u.username as customer_name " +
                     "FROM shipments s " +
                     "JOIN users u ON s.user_id = u.user_id " +
                     "ORDER BY s.created_at DESC";

        List<Map<String, String>> orders = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
                
                String paymentMethod = rs.getString("payment_method");
                order.put("paymentMethod", paymentMethod != null ? paymentMethod : "N/A");
                
                order.put("senderInfo", rs.getString("sender_info"));
                order.put("customerName", rs.getString("customer_name"));
                
                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    order.put("date", sdf.format(new java.util.Date(timestamp.getTime())));
                }
                
                orders.add(order);
            }

        } catch (SQLException e) {
            System.err.println("Error in getAllOrdersWithCustomerNames: " + e.getMessage());
            e.printStackTrace();
        }

        return orders;
    }
    

 //END OF NEW ADDITION - Asher Maxwell
    //#######################################################################################################################################################################################################################################################
    // --- End of class ---
}
