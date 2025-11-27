package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

//import SmartShip_CustomerClient.controller.CustomerCommandController;
import javax.swing.table.TableColumn;

import controller.ClerkCommandController;

import model.User;

public class ClerkFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static User currentUser;
	private ClerkCommandController controller;
	private JTabbedPane tabbedPane;

	// Profile fields
	private JTextField emailField;
	private JTextField phoneField;
	private JTextField addressField;

	public ClerkFrame(User user) {
		this.currentUser = user;
		this.controller = new ClerkCommandController(); // check

		setTitle("SmartShip - Clerk Portal");
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
		tabbedPane.addTab("Process Shipment Requests", processShipReqPanel());
		tabbedPane.addTab("Handle Payments", HandlePaymentsPanel());
		tabbedPane.addTab("Assign Packages", assignPackagesPanel());
		tabbedPane.addTab("Track Packages", createTrackPackagePanel());
		tabbedPane.addTab("Profile", createProfilePanel());

		add(tabbedPane, BorderLayout.CENTER);

		// Create bottom panel with logout
		JPanel bottomPanel = createBottomPanel();
		add(bottomPanel, BorderLayout.SOUTH);

		setVisible(true);
	}

	// Creativity
	private JPanel createTopPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(new Color(52, 73, 94));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		JLabel titleLabel = new JLabel("SmartShip Clerk Portal");
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
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));
		buttonPanel.setBackground(new Color(249, 249, 249));
		buttonPanel.setMaximumSize(new Dimension(400, 200));

		JButton processShipReqBtn = new JButton("Process Shipment Requests");
		processShipReqBtn.setBackground(new Color(163, 67, 53));
		processShipReqBtn.setForeground(Color.WHITE);
		processShipReqBtn.setFont(new Font("Arial", Font.BOLD, 11));
		processShipReqBtn.addActionListener(e -> tabbedPane.setSelectedIndex(1));

		JButton handlePaymentsBtn = new JButton("Handle Payments");
		handlePaymentsBtn.setBackground(new Color(163, 67, 53));
		handlePaymentsBtn.setForeground(Color.WHITE);
		handlePaymentsBtn.setFont(new Font("Arial", Font.BOLD, 11));
		handlePaymentsBtn.addActionListener(e -> tabbedPane.setSelectedIndex(2));

		JButton assignPackageBtn = new JButton("Assign Packages");
		assignPackageBtn.setBackground(new Color(163, 67, 53));
		assignPackageBtn.setForeground(Color.WHITE);
		assignPackageBtn.setFont(new Font("Arial", Font.BOLD, 11));
		assignPackageBtn.addActionListener(e -> tabbedPane.setSelectedIndex(3));

		JButton trackPackageBtn = new JButton("Track Package");
		trackPackageBtn.setBackground(new Color(163, 67, 53));
		trackPackageBtn.setForeground(Color.WHITE);
		trackPackageBtn.setFont(new Font("Arial", Font.BOLD, 11));
		trackPackageBtn.addActionListener(e -> tabbedPane.setSelectedIndex(4));

		JButton updateProfileBtn = new JButton("Update Profile");
		updateProfileBtn.setBackground(new Color(163, 67, 53));
		updateProfileBtn.setForeground(Color.WHITE);
		updateProfileBtn.setFont(new Font("Arial", Font.BOLD, 11));
		updateProfileBtn.addActionListener(e -> tabbedPane.setSelectedIndex(5));

		buttonPanel.add(processShipReqBtn);
		buttonPanel.add(handlePaymentsBtn);
		buttonPanel.add(assignPackageBtn);
		buttonPanel.add(trackPackageBtn);
		buttonPanel.add(updateProfileBtn);

		mainPanel.add(buttonPanel);
		mainPanel.add(Box.createVerticalGlue());

		return mainPanel;
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

	// Process Shipment Request panel, displays all the shipments of the customer
	// and allows the clerk to change delivery status and payment status
	private JPanel processShipReqPanel() {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBackground(new Color(249, 249, 249));
	    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	    JLabel titleLabel = new JLabel("Customer Shipments");
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
	    titleLabel.setForeground(new Color(52, 73, 94));
	    panel.add(titleLabel, BorderLayout.NORTH);

	    String[] columns = {"Tracking #", "Recipient", "Weight (kg)", "Type", "Zone", "Status", "Cost", "Payment"};
	    DefaultTableModel model = new DefaultTableModel(columns, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            // Check if payment status is "Paid"
	            String paymentStatus = (String) getValueAt(row, 7);
	            
	            // Status (column 5) is always editable
	            // Payment (column 7) is NOT editable if already "Paid"
	            if (column == 5) {
	                return true;
	            } else if (column == 7) {
	                return !paymentStatus.equals("Paid");
	            }
	            return false;
	        }
	    };
	    
	    JTable shipmentsTable = new JTable(model);
	    shipmentsTable.setRowHeight(25);
	    shipmentsTable.setFont(new Font("Arial", Font.PLAIN, 11));

	    // Combo box for Status
	    String[] statuses = {"Pending", "Shipped", "Delivered", "Cancelled"};
	    JComboBox<String> statusCombo = new JComboBox<>(statuses);
	    TableColumn statusColumn = shipmentsTable.getColumnModel().getColumn(5);
	    statusColumn.setCellEditor(new DefaultCellEditor(statusCombo));

	    // Combo box for Payment
	    String[] payments = {"Unpaid", "Paid"};
	    JComboBox<String> paymentCombo = new JComboBox<>(payments);
	    TableColumn paymentColumn = shipmentsTable.getColumnModel().getColumn(7);
	    paymentColumn.setCellEditor(new DefaultCellEditor(paymentCombo));

	    // Load data from controller
	    List<Map<String, String>> shipments = controller.getAllOrders();
	    if (shipments != null) {
	        for (Map<String, String> shipment : shipments) {
	            model.addRow(new Object[]{
	                shipment.get("trackingNumber"),
	                shipment.get("recipientInfo"),
	                shipment.get("weight"),
	                shipment.get("packageType"),
	                shipment.get("zone"),
	                shipment.get("status"),
	                "$" + shipment.get("cost"),
	                shipment.get("paymentStatus")
	            });
	        }
	    }

	    // Listen for edits in Status or Payment
	    shipmentsTable.getModel().addTableModelListener(e -> {
	        int row = e.getFirstRow();
	        int col = e.getColumn();

	        if (col == 5 || col == 7) {
	            String tracking = shipmentsTable.getValueAt(row, 0).toString();
	            String newStatus = shipmentsTable.getValueAt(row, 5).toString();
	            String newPayment = shipmentsTable.getValueAt(row, 7).toString();

	            boolean updated = controller.updateShipmentStatus(tracking, newStatus, newPayment);
	            if (!updated) {
	                JOptionPane.showMessageDialog(null, "Failed to update shipment: " + tracking);
	            } else {
	                // Refresh the table to reflect changes
	                model.fireTableDataChanged();
	            }
	        }
	    });

	    JScrollPane scrollPane = new JScrollPane(shipmentsTable);
	    panel.add(scrollPane, BorderLayout.CENTER);

	    // Refresh button
	    JButton refreshBtn = new JButton("Refresh");
	    refreshBtn.addActionListener(e -> {
	        model.setRowCount(0);
	        List<Map<String, String>> updatedShipments = controller.getAllOrders();
	        if (updatedShipments != null) {
	            for (Map<String, String> shipment : updatedShipments) {
	                model.addRow(new Object[]{
	                    shipment.get("trackingNumber"),
	                    shipment.get("recipientInfo"),
	                    shipment.get("weight"),
	                    shipment.get("packageType"),
	                    shipment.get("zone"),
	                    shipment.get("status"),
	                    "$" + shipment.get("cost"),
	                    shipment.get("paymentStatus")
	                });
	            }
	        }
	    });

	    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    bottomPanel.setBackground(new Color(249, 249, 249));
	    bottomPanel.add(refreshBtn);
	    panel.add(bottomPanel, BorderLayout.SOUTH);

	    return panel;
	}

	// Handles Payment information and allows the clerk to view customer invoices
	private JPanel HandlePaymentsPanel() {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBackground(new Color(249, 249, 249));
	    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	    JLabel titleLabel = new JLabel("Handle Customer Payments");
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
	    titleLabel.setForeground(new Color(52, 73, 94));
	    panel.add(titleLabel, BorderLayout.NORTH);

	    String[] columns = {"Tracking #", "Customer", "Recipient", "Status", "Cost", "Payment", "Payment Method"};
	    DefaultTableModel model = new DefaultTableModel(columns, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            // No columns are editable in this view - it's read-only
	            return false;
	        }
	    };
	    
	    JTable paymentsTable = new JTable(model);
	    paymentsTable.setRowHeight(25);
	    paymentsTable.setFont(new Font("Arial", Font.PLAIN, 11));

	    // Load data from controller with customer names
	    List<Map<String, String>> shipments = controller.getAllOrdersWithCustomerNames();
	    if (shipments != null && !shipments.isEmpty()) {
	        for (Map<String, String> shipment : shipments) {
	            model.addRow(new Object[]{
	                shipment.get("trackingNumber"),
	                shipment.get("customerName"),
	                shipment.get("recipientInfo"),
	                shipment.get("status"),
	                "$" + shipment.get("cost"),
	                shipment.get("paymentStatus"),
	                shipment.get("paymentMethod")
	            });
	        }
	    } else {
	        System.out.println("No shipments retrieved or shipments list is null");
	    }

	    JScrollPane scrollPane = new JScrollPane(paymentsTable);
	    panel.add(scrollPane, BorderLayout.CENTER);

	    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    bottomPanel.setBackground(new Color(249, 249, 249));
	    
	    // View Customer Invoice button
	    JButton viewInvoiceBtn = new JButton("View Customer Invoice");
	    viewInvoiceBtn.setBackground(new Color(163, 67, 53));
	    viewInvoiceBtn.setForeground(Color.WHITE);
	    viewInvoiceBtn.setFont(new Font("Arial", Font.BOLD, 12));
	    viewInvoiceBtn.addActionListener(e -> {
	        int selectedRow = paymentsTable.getSelectedRow();
	        if (selectedRow == -1) {
	            JOptionPane.showMessageDialog(null, "Please select a shipment to view its invoice", 
	                "Selection Error", JOptionPane.ERROR_MESSAGE);
	        } else {
	            String trackingNumber = model.getValueAt(selectedRow, 0).toString();
	            viewInvoiceForShipment(trackingNumber);
	        }
	    });
	    
	    // Refresh button
	    JButton refreshBtn = new JButton("Refresh");
	    refreshBtn.addActionListener(e -> {
	        model.setRowCount(0);
	        List<Map<String, String>> updatedShipments = controller.getAllOrdersWithCustomerNames();
	        if (updatedShipments != null) {
	            for (Map<String, String> shipment : updatedShipments) {
	                model.addRow(new Object[]{
	                    shipment.get("trackingNumber"),
	                    shipment.get("customerName"),
	                    shipment.get("recipientInfo"),
	                    shipment.get("status"),
	                    "$" + shipment.get("cost"),
	                    shipment.get("paymentStatus"),
	                    shipment.get("paymentMethod")
	                });
	            }
	        }
	    });
	    
	    bottomPanel.add(viewInvoiceBtn);
	    bottomPanel.add(refreshBtn);
	    panel.add(bottomPanel, BorderLayout.SOUTH);

	    return panel;
	}

	private void viewInvoiceForShipment(String trackingNumber) {
	    try {
	        // Get the invoice for this tracking number
	        List<Map<String, String>> invoices = controller.getCustomerInvoices(currentUser.getUserId());
	        
	        if (invoices == null || invoices.isEmpty()) {
	            // Try to get invoice by tracking number using a different approach
	            Map<String, String> shipmentInfo = controller.trackPackage(trackingNumber);
	            if (shipmentInfo != null) {
	                // Create a simple invoice display
	                StringBuilder invoiceDetails = new StringBuilder();
	                invoiceDetails.append("SHIPMENT INVOICE\n");
	                invoiceDetails.append("===================\n\n");
	                invoiceDetails.append("Tracking Number: ").append(trackingNumber).append("\n");
	                invoiceDetails.append("Recipient: ").append(shipmentInfo.get("recipientInfo")).append("\n");
	                invoiceDetails.append("Weight: ").append(shipmentInfo.get("weight")).append(" kg\n");
	                invoiceDetails.append("Package Type: ").append(shipmentInfo.get("packageType")).append("\n");
	                invoiceDetails.append("Zone: ").append(shipmentInfo.get("zone")).append("\n");
	                invoiceDetails.append("Status: ").append(shipmentInfo.get("status")).append("\n");
	                invoiceDetails.append("\n");
	                invoiceDetails.append("Subtotal: $").append(shipmentInfo.get("cost")).append("\n");
	                invoiceDetails.append("Tax (10%): $").append(String.format("%.2f", Double.parseDouble(shipmentInfo.get("cost")) * 0.10)).append("\n");
	                invoiceDetails.append("TOTAL: $").append(String.format("%.2f", Double.parseDouble(shipmentInfo.get("cost")) * 1.10)).append("\n");
	                invoiceDetails.append("\n");
	                invoiceDetails.append("Payment Status: ").append(shipmentInfo.get("paymentStatus")).append("\n");
	                
	                JTextArea textArea = new JTextArea(invoiceDetails.toString());
	                textArea.setEditable(false);
	                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
	                
	                JScrollPane scrollPane = new JScrollPane(textArea);
	                scrollPane.setPreferredSize(new Dimension(400, 350));
	                
	                JOptionPane.showMessageDialog(this, scrollPane, "Invoice Details", JOptionPane.INFORMATION_MESSAGE);
	            } else {
	                JOptionPane.showMessageDialog(null, "No invoice found for this shipment", 
	                    "Invoice Not Found", JOptionPane.WARNING_MESSAGE);
	            }
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving invoice: " + ex.getMessage(), 
	            "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	// Track Every Customer Package in the Database
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
				JOptionPane.showMessageDialog(null, "Please enter a tracking number", "Input Error",
						JOptionPane.ERROR_MESSAGE);
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

	private void updateProfile() {
		String email = emailField.getText().trim();
		String phone = phoneField.getText().trim();
		String address = addressField.getText().trim();

		if (email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
			JOptionPane.showMessageDialog(null, "All fields are required", "Validation Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean success = controller.updateCustomerProfile(currentUser.getUserId(), email, phone, address);
		if (success) {
			JOptionPane.showMessageDialog(null, "Profile updated successfully", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Failed to update profile", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void loadProfileInfo() {
		Map<String, String> info = controller.getCustomerProfile(currentUser.getUserId());
		if (info != null) {
			emailField.setText(info.getOrDefault("email", ""));
			phoneField.setText(info.getOrDefault("phone", ""));
			addressField.setText(info.getOrDefault("address", ""));
		}
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
		
	
	private JPanel assignPackagesPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(249, 249, 249));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel titleLabel = new JLabel("Assign Customer Shipments");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		titleLabel.setForeground(new Color(52, 73, 94));
		panel.add(titleLabel, BorderLayout.NORTH);

		String[] columns = { "Tracking #", "Recipient", "Weight (kg)", "Type", "Zone", "Status", "Cost", "Payment",
				"Assign Driver", "Assign Vehicle", "Assign Route" };

		DefaultTableModel model = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Check if payment status is "Paid"
				String paymentStatus = (String) getValueAt(row, 7);

				// Status (5) is always editable
				// Payment (7) is NOT editable if already "Paid"
				// Assignment columns (8, 9, 10) are always editable
				if (column == 5 || column == 8 || column == 9 || column == 10) {
					return true;
				} else if (column == 7) {
					return !paymentStatus.equals("Paid");
				}
				return false;
			}
		};

		JTable shipmentsTable = new JTable(model);
		shipmentsTable.setRowHeight(25);
		shipmentsTable.setFont(new Font("Arial", Font.PLAIN, 11));

		// Combo box for Status
		String[] statuses = { "Pending", "Shipped", "Delivered", "Cancelled" };
		JComboBox<String> statusCombo = new JComboBox<>(statuses);
		TableColumn statusColumn = shipmentsTable.getColumnModel().getColumn(5);
		statusColumn.setCellEditor(new DefaultCellEditor(statusCombo));

		// Combo box for Payment
		String[] payments = { "Unpaid", "Paid" };
		JComboBox<String> paymentCombo = new JComboBox<>(payments);
		TableColumn paymentColumn = shipmentsTable.getColumnModel().getColumn(7);
		paymentColumn.setCellEditor(new DefaultCellEditor(paymentCombo));

		// Fetch available drivers from database
		List<Map<String, String>> driversList = controller.getAvailableDrivers();
		JComboBox<String> driverCombo = new JComboBox<>();
		driverCombo.addItem("-- Select Driver --");
		Map<String, Integer> driverMap = new HashMap<>();

		if (driversList != null) {
			for (Map<String, String> driver : driversList) {
				String displayName = driver.get("username") + " (ID: " + driver.get("driverId") + ", Deliveries: "
						+ driver.get("totalDeliveries") + ")";
				driverCombo.addItem(displayName);
				driverMap.put(displayName, Integer.parseInt(driver.get("driverId")));
			}
		}
		TableColumn driverColumn = shipmentsTable.getColumnModel().getColumn(8);
		driverColumn.setCellEditor(new DefaultCellEditor(driverCombo));

		// Fetch available vehicles from database
		List<Map<String, String>> vehiclesList = controller.getAvailableVehicles();
		JComboBox<String> vehicleCombo = new JComboBox<>();
		vehicleCombo.addItem("-- Select Vehicle --");
		Map<String, Integer> vehicleMap = new HashMap<>();

		if (vehiclesList != null) {
			for (Map<String, String> vehicle : vehiclesList) {
				String displayName = vehicle.get("licensePlate") + " - " + vehicle.get("vehicleType") + " (Capacity: "
						+ vehicle.get("capacity") + " kg, Items: " + vehicle.get("currentItemCount") + ")";
				vehicleCombo.addItem(displayName);
				vehicleMap.put(displayName, Integer.parseInt(vehicle.get("vehicleId")));
			}
		}
		TableColumn vehicleColumn = shipmentsTable.getColumnModel().getColumn(9);
		vehicleColumn.setCellEditor(new DefaultCellEditor(vehicleCombo));

		// we dont really have any routes at the moment so i left them hard coded like this
		String[] routes = { " Select Route ", "Route A - North", "Route B - South", "Route C - East", "Route D - West",
				"Route E - Central" };
		JComboBox<String> routeCombo = new JComboBox<>(routes);
		TableColumn routeColumn = shipmentsTable.getColumnModel().getColumn(10);
		routeColumn.setCellEditor(new DefaultCellEditor(routeCombo));

		// Load shipment data and assignments
		loadShipmentData(model);

		// Listen for edits in Status, Payment, or Assignments
		shipmentsTable.getModel().addTableModelListener(e -> {
			int row = e.getFirstRow();
			int col = e.getColumn();

			if (row < 0 || col < 0)
				return;

			String tracking = shipmentsTable.getValueAt(row, 0).toString();

			// Handle Status or Payment updates
			if (col == 5 || col == 7) {
				String newStatus = shipmentsTable.getValueAt(row, 5).toString();
				String newPayment = shipmentsTable.getValueAt(row, 7).toString();

				boolean updated = controller.updateShipmentStatus(tracking, newStatus, newPayment);
				if (!updated) {
					JOptionPane.showMessageDialog(null, "Failed to update shipment: " + tracking);
				}
			}

			// Handle Driver assignment
			if (col == 8) {
				String selectedDriver = shipmentsTable.getValueAt(row, 8).toString();
				Integer driverId = driverMap.get(selectedDriver);

				if (driverId != null) {
					boolean success = controller.assignShipment(tracking, driverId, null, null);
					if (success) {
						JOptionPane.showMessageDialog(null, "Driver assigned successfully to tracking #" + tracking,
								"Success", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Failed to assign driver to tracking #" + tracking, "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			// Handle Vehicle assignment
			if (col == 9) {
				String selectedVehicle = shipmentsTable.getValueAt(row, 9).toString();
				Integer vehicleId = vehicleMap.get(selectedVehicle);

				if (vehicleId != null) {
					boolean success = controller.assignShipment(tracking, null, vehicleId, null);
					if (success) {
						JOptionPane.showMessageDialog(null, "Vehicle assigned successfully to tracking #" + tracking,
								"Success", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Failed to assign vehicle to tracking #" + tracking,
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			// Handle Route assignment
			if (col == 10) {
				String selectedRoute = shipmentsTable.getValueAt(row, 10).toString();

				if (!selectedRoute.equals("-- Select Route --")) {
					boolean success = controller.assignShipment(tracking, null, null, selectedRoute);
					if (success) {
						JOptionPane.showMessageDialog(null, "Route assigned successfully to tracking #" + tracking,
								"Success", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Failed to assign route to tracking #" + tracking, "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(shipmentsTable);
		panel.add(scrollPane, BorderLayout.CENTER);

		// Refresh button
		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.setBackground(new Color(163, 67, 53));
		refreshBtn.setForeground(Color.WHITE);
		refreshBtn.setFont(new Font("Arial", Font.BOLD, 12));
		refreshBtn.addActionListener(e -> {
			model.setRowCount(0);
			loadShipmentData(model);
		});

		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bottomPanel.setBackground(new Color(249, 249, 249));
		bottomPanel.add(refreshBtn);
		panel.add(bottomPanel, BorderLayout.SOUTH);

		return panel;
	}

	private void loadShipmentData(DefaultTableModel model) {
		List<Map<String, String>> shipments = controller.getAllOrders();
		Map<String, Map<String, String>> assignments = controller.getShipmentAssignments();

		if (shipments != null) {
			for (Map<String, String> shipment : shipments) {
				String trackingNumber = shipment.get("trackingNumber");
				Map<String, String> assignment = assignments != null ? assignments.get(trackingNumber) : null;

				String driverDisplay = "Select Driver";
				String vehicleDisplay = "Select Vehicle";
				String routeDisplay = "Select Route";

				if (assignment != null) {
					if (assignment.containsKey("driverName")) {
						driverDisplay = assignment.get("driverName") + " (ID: " + assignment.get("driverId") + ")";
					}
					if (assignment.containsKey("licensePlate")) {
						vehicleDisplay = assignment.get("licensePlate") + " (ID: " + assignment.get("vehicleId") + ")";
					}
					if (assignment.containsKey("route")) {
						routeDisplay = assignment.get("route");
					}
				}

				model.addRow(new Object[] { trackingNumber, shipment.get("recipientInfo"), shipment.get("weight"),
						shipment.get("packageType"), shipment.get("zone"), shipment.get("status"),
						"$" + shipment.get("cost"), shipment.get("paymentStatus"), driverDisplay, vehicleDisplay,
						routeDisplay });
			}
		}
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new LoginFrame(); // User logs in first
		});

	}

}
