package view;

import java.awt.*;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import controller.CustomerCommandController;
import model.User;

public class CustomerFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static User currentUser;
    private CustomerCommandController controller;
    private JTabbedPane tabbedPane;
    
    // Profile fields
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    
    // Shipment creation fields
    private JTextField senderNameField;
    private JTextField senderPhoneField;
    private JTextField recipientNameField;
    private JTextField recipientPhoneField;
    private JTextField recipientAddressField;
    private JSpinner weightSpinner;
    private JSpinner dimensionsSpinner;
    private JComboBox<String> packageTypeCombo;
    private JComboBox<Integer> zoneCombo;

    public CustomerFrame(User user) {
        this.currentUser = user;
        this.controller = new CustomerCommandController();
        
        setTitle("SmartShip - Customer Portal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Set icon
        try {
            ImageIcon icon = new ImageIcon("./assets/iutLogo.png");
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.out.println("Icon not found");
        }
        
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(249, 249, 249));
        
        // Create top panel with user info
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);
        
        // Create tabbed pane for different sections
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(249, 249, 249));
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Add tabs
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Profile", createProfilePanel());
        tabbedPane.addTab("Create Shipment", createCreateShipmentPanel());
        tabbedPane.addTab("My Shipments", createMyShipmentsPanel());
        tabbedPane.addTab("Track Package", createTrackPackagePanel());
        tabbedPane.addTab("Invoices", createInvoicesPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Create bottom panel with logout
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("SmartShip Customer Portal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.WEST);
        
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        userInfoPanel.setBackground(new Color(52, 73, 94));
        
        JLabel userLabel = new JLabel("ID: " + currentUser.getUserId() + " | Username: " + currentUser.getUsername());
        userLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userLabel.setForeground(Color.WHITE);
        userInfoPanel.add(userLabel);
        
        panel.add(userInfoPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createDashboardPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(249, 249, 249));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(52, 73, 94));
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JLabel actionsLabel = new JLabel("Quick Actions:");
        actionsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(actionsLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBackground(new Color(249, 249, 249));
        buttonPanel.setMaximumSize(new Dimension(400, 200));
        
        JButton createShipmentBtn = new JButton("Create New Shipment");
        createShipmentBtn.setBackground(new Color(163, 67, 53));
        createShipmentBtn.setForeground(Color.WHITE);
        createShipmentBtn.setFont(new Font("Arial", Font.BOLD, 11));
        createShipmentBtn.addActionListener(e -> tabbedPane.setSelectedIndex(2));
        
        JButton viewShipmentsBtn = new JButton("View My Shipments");
        viewShipmentsBtn.setBackground(new Color(163, 67, 53));
        viewShipmentsBtn.setForeground(Color.WHITE);
        viewShipmentsBtn.setFont(new Font("Arial", Font.BOLD, 11));
        viewShipmentsBtn.addActionListener(e -> tabbedPane.setSelectedIndex(3));
        
        JButton trackPackageBtn = new JButton("Track Package");
        trackPackageBtn.setBackground(new Color(163, 67, 53));
        trackPackageBtn.setForeground(Color.WHITE);
        trackPackageBtn.setFont(new Font("Arial", Font.BOLD, 11));
        trackPackageBtn.addActionListener(e -> tabbedPane.setSelectedIndex(4));
        
        JButton viewInvoicesBtn = new JButton("View Invoices");
        viewInvoicesBtn.setBackground(new Color(163, 67, 53));
        viewInvoicesBtn.setForeground(Color.WHITE);
        viewInvoicesBtn.setFont(new Font("Arial", Font.BOLD, 11));
        viewInvoicesBtn.addActionListener(e -> tabbedPane.setSelectedIndex(5));
        
        JButton updateProfileBtn = new JButton("Update Profile");
        updateProfileBtn.setBackground(new Color(163, 67, 53));
        updateProfileBtn.setForeground(Color.WHITE);
        updateProfileBtn.setFont(new Font("Arial", Font.BOLD, 11));
        updateProfileBtn.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        
        buttonPanel.add(createShipmentBtn);
        buttonPanel.add(viewShipmentsBtn);
        buttonPanel.add(trackPackageBtn);
        buttonPanel.add(viewInvoicesBtn);
        buttonPanel.add(updateProfileBtn);
        
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());
        
        return mainPanel;
    }
    
    private JPanel createProfilePanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(249, 249, 249));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Update Your Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 73, 94));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
       
        
        mainPanel.add(createLabelField("Email:", emailField = new JTextField(30)));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        mainPanel.add(createLabelField("Phone:", phoneField = new JTextField(30)));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        mainPanel.add(createLabelField("Address:", addressField = new JTextField(30)));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JButton updateBtn = new JButton("Update Profile");
        updateBtn.setBackground(new Color(163, 67, 53));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setFont(new Font("Arial", Font.BOLD, 12));
        updateBtn.setPreferredSize(new Dimension(150, 40));
        updateBtn.addActionListener(e -> updateProfile());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(249, 249, 249));
        buttonPanel.add(updateBtn);
        mainPanel.add(buttonPanel);
        
        mainPanel.add(Box.createVerticalGlue());
        loadProfileInfo();
        return mainPanel;
    }
    
    private JPanel createCreateShipmentPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(249, 249, 249));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Create New Shipment");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 73, 94));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JPanel senderPanel = createCollapsibleSection("Sender Information");
        senderNameField = new JTextField(30);
        senderPhoneField = new JTextField(30);
        senderPanel.add(createLabelField("Sender Name:", senderNameField));
        senderPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        senderPanel.add(createLabelField("Sender Phone:", senderPhoneField));
        mainPanel.add(senderPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JPanel recipientPanel = createCollapsibleSection("Recipient Information");
        recipientNameField = new JTextField(30);
        recipientPhoneField = new JTextField(30);
        recipientAddressField = new JTextField(30);
        recipientPanel.add(createLabelField("Recipient Name:", recipientNameField));
        recipientPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        recipientPanel.add(createLabelField("Recipient Phone:", recipientPhoneField));
        recipientPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        recipientPanel.add(createLabelField("Recipient Address:", recipientAddressField));
        mainPanel.add(recipientPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JPanel packagePanel = createCollapsibleSection("Package Information");
        weightSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 1000.0, 0.5));
        dimensionsSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 1000.0, 0.5));
        packageTypeCombo = new JComboBox<>(new String[]{"Standard", "Express", "Fragile"});
        zoneCombo = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        
        packagePanel.add(createLabelField("Weight (kg):", weightSpinner));
        packagePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        packagePanel.add(createLabelField("Dimensions (cm):", dimensionsSpinner));
        packagePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        packagePanel.add(createLabelField("Package Type:", packageTypeCombo));
        packagePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        packagePanel.add(createLabelField("Destination Zone:", zoneCombo));
        mainPanel.add(packagePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JButton createBtn = new JButton("Create Shipment");
        createBtn.setBackground(new Color(163, 67, 53));
        createBtn.setForeground(Color.WHITE);
        createBtn.setFont(new Font("Arial", Font.BOLD, 12));
        createBtn.setPreferredSize(new Dimension(150, 40));
        createBtn.addActionListener(e -> createShipment());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(249, 249, 249));
        buttonPanel.add(createBtn);
        mainPanel.add(buttonPanel);
        
        mainPanel.add(Box.createVerticalGlue());
        
        return mainPanel;
    }
    
    private JPanel createMyShipmentsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(249, 249, 249));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("My Shipments");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 73, 94));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        String[] columns = {"Tracking #", "Recipient", "Weight (kg)", "Type", "Zone", "Status", "Cost", "Payment"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable shipmentsTable = new JTable(model);
        shipmentsTable.setRowHeight(25);
        shipmentsTable.setFont(new Font("Arial", Font.PLAIN, 11));
        
        List<Map<String, String>> shipments = controller.getCustomerShipments(currentUser.getUserId());
        if (shipments != null) {
            for (Map<String, String> shipment : shipments) {
                Object[] row = {
                    shipment.get("trackingNumber"),
                    shipment.get("recipientInfo"),
                    shipment.get("weight"),
                    shipment.get("packageType"),
                    shipment.get("zone"),
                    shipment.get("status"),
                    "$" + shipment.get("cost"),
                    shipment.get("paymentStatus")
                };
                model.addRow(row);
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(shipmentsTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);
            List<Map<String, String>> updatedShipments = controller.getCustomerShipments(currentUser.getUserId());
            if (updatedShipments != null) {
                for (Map<String, String> shipment : updatedShipments) {
                    Object[] row = {
                        shipment.get("trackingNumber"),
                        shipment.get("recipientInfo"),
                        shipment.get("weight"),
                        shipment.get("packageType"),
                        shipment.get("zone"),
                        shipment.get("status"),
                        "$" + shipment.get("cost"),
                        shipment.get("paymentStatus")
                    };
                    model.addRow(row);
                }
            }
        });
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(new Color(249, 249, 249));
        bottomPanel.add(refreshBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createTrackPackagePanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(249, 249, 249));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Track Your Package");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 73, 94));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JTextField trackingField = new JTextField(30);
        mainPanel.add(createLabelField("Enter Tracking Number:", trackingField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JTextArea resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Courier", Font.PLAIN, 11));
        resultArea.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        
        JButton trackBtn = new JButton("Track Package");
        trackBtn.setBackground(new Color(163, 67, 53));
        trackBtn.setForeground(Color.WHITE);
        trackBtn.setFont(new Font("Arial", Font.BOLD, 12));
        trackBtn.setPreferredSize(new Dimension(150, 40));
        trackBtn.addActionListener(e -> {
            String trackingNumber = trackingField.getText().trim();
            if (trackingNumber.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a tracking number", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Map<String, String> trackingInfo = controller.trackPackage(trackingNumber);
                if (trackingInfo != null) {
                    StringBuilder info = new StringBuilder();
                    info.append("Tracking Number: ").append(trackingInfo.get("trackingNumber")).append("\n");
                    info.append("Status: ").append(trackingInfo.get("status")).append("\n");
                    info.append("Recipient: ").append(trackingInfo.get("recipientInfo")).append("\n");
                    info.append("Weight: ").append(trackingInfo.get("weight")).append(" kg\n");
                    info.append("Package Type: ").append(trackingInfo.get("packageType")).append("\n");
                    info.append("Zone: ").append(trackingInfo.get("zone")).append("\n");
                    info.append("Cost: $").append(trackingInfo.get("cost")).append("\n");
                    info.append("Payment Status: ").append(trackingInfo.get("paymentStatus"));
                    resultArea.setText(info.toString());
                } else {
                    resultArea.setText("Package not found!");
                }
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(249, 249, 249));
        buttonPanel.add(trackBtn);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(new JLabel("Tracking Information:"));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createVerticalGlue());
        
        return mainPanel;
    }
    
    private JPanel createInvoicesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(249, 249, 249));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("My Invoices");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 73, 94));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        String[] columns = {"Invoice #", "Invoice/shipment ID#", "Amount with Tax", "Status", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable invoicesTable = new JTable(model);
        invoicesTable.setRowHeight(30);
        invoicesTable.setFont(new Font("Arial", Font.PLAIN, 11));
        
        List<Map<String, String>> invoices = controller.getCustomerInvoices(currentUser.getUserId());
        if (invoices != null) {
            for (Map<String, String> invoice : invoices) {
                Object[] row = {
                    invoice.get("invoiceId"),
                    invoice.get("shipmentId"),
                    "$" + invoice.get("total"),
                    invoice.get("status"),
                    invoice.get("invoiceDate")
                };
                model.addRow(row);
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(invoicesTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(new Color(249, 249, 249));
        
        JButton payBtn = new JButton("Pay Selected Invoice");
        payBtn.setBackground(new Color(163, 67, 53));
        payBtn.setForeground(Color.WHITE);
        payBtn.setFont(new Font("Arial", Font.BOLD, 12));
        payBtn.addActionListener(e -> {
            int selectedRow = invoicesTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select an invoice", "Selection Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String invoiceId = model.getValueAt(selectedRow, 0).toString();
                String amount = model.getValueAt(selectedRow, 2).toString().replace("$", "");
                makePayment(invoiceId, amount, model, selectedRow);
            }
        });
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);
            List<Map<String, String>> updatedInvoices = controller.getCustomerInvoices(currentUser.getUserId());
            if (updatedInvoices != null) {
                for (Map<String, String> invoice : updatedInvoices) {
                    Object[] row = {
                        invoice.get("invoiceId"),
                        invoice.get("shipmentId"),
                        "$" + invoice.get("total"),
                        invoice.get("status"),
                        invoice.get("invoiceDate")
                    };
                    model.addRow(row);
                }
            }
        });
        
        bottomPanel.add(payBtn);
        bottomPanel.add(refreshBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createLabelField(String label, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(new Color(249, 249, 249));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 11));
        lbl.setPreferredSize(new Dimension(150, 20));
        panel.add(lbl);
        panel.add(field);
        return panel;
    }
    
    private JPanel createCollapsibleSection(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(title),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        return panel;
    }
    
    private void loadProfileInfo() {
        Map<String, String> info = controller.getCustomerProfile(currentUser.getUserId());
        if (info != null) {
            emailField.setText(info.getOrDefault("email", ""));
            phoneField.setText(info.getOrDefault("phone", ""));
            addressField.setText(info.getOrDefault("address", ""));
        }
    }
    
    private void updateProfile() {
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        
        if (email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean success = controller.updateCustomerProfile(currentUser.getUserId(), email, phone, address);
        if (success) {
            JOptionPane.showMessageDialog(null, "Profile updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to update profile", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void createShipment() {
        String senderName = senderNameField.getText().trim();
        String senderPhone = senderPhoneField.getText().trim();
        String recipientName = recipientNameField.getText().trim();
        String recipientPhone = recipientPhoneField.getText().trim();
        String recipientAddress = recipientAddressField.getText().trim();
        double weight = (double) weightSpinner.getValue();
        double dimensions = (double) dimensionsSpinner.getValue();
        String packageType = (String) packageTypeCombo.getSelectedItem();
        int zone = (int) zoneCombo.getSelectedItem();
        
        if (senderName.isEmpty() || senderPhone.isEmpty() || recipientName.isEmpty() || 
            recipientPhone.isEmpty() || recipientAddress.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String senderInfo = senderName + " | " + senderPhone;
        String recipientInfo = recipientName + " | " + recipientPhone;
        
        String trackingNumber = controller.createShipment(currentUser.getUserId(), senderInfo, 
                                                         recipientInfo, weight, String.valueOf(dimensions), 
                                                         packageType, zone, recipientAddress);
        
        if (trackingNumber != null) {
            JOptionPane.showMessageDialog(null, "Shipment created successfully!\nTracking Number: " + trackingNumber, 
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
            senderNameField.setText("");
            senderPhoneField.setText("");
            recipientNameField.setText("");
            recipientPhoneField.setText("");
            recipientAddressField.setText("");
            weightSpinner.setValue(1.0);
            dimensionsSpinner.setValue(1.0);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to create shipment", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void makePayment(String invoiceId, String amount, DefaultTableModel model, int row) {
        String[] methods = {"Cash", "Card"};
        String paymentMethod = (String) JOptionPane.showInputDialog(
                null, "Select Payment Method:", "Payment",
                JOptionPane.INFORMATION_MESSAGE, null, methods, methods[0]
        );

        if (paymentMethod != null) {
            try {
                // Show loading message
                JOptionPane.showMessageDialog(null, "Processing payment...", "Please Wait", JOptionPane.INFORMATION_MESSAGE);
                
                // Call controller to make payment and get receipt
                Map<String, String> receipt = controller.makePaymentAndReturnReceipt(
                        Integer.parseInt(invoiceId),
                        Double.parseDouble(amount),
                        paymentMethod
                );

                if (receipt != null) {
                    // Display receipt dialog
                    new ReceiptDialog(this, receipt).setVisible(true);
                    
                    // Update the table to show paid status
                    model.setValueAt("Paid", row, 3);
                    
                    JOptionPane.showMessageDialog(null, "Payment completed successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Payment failed. Please try again.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid invoice ID or amount.", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    
    
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(163, 67, 53));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 12));
        logoutBtn.setPreferredSize(new Dimension(100, 35));
        logoutBtn.addActionListener(e -> {
            setVisible(false);
            dispose();
            new LoginFrame();
        });
        
        panel.add(logoutBtn);
        return panel;
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame(); // User logs in first
        });
    }


}