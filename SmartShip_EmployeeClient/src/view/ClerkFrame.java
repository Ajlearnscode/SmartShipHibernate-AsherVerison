package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
//import SmartShip_CustomerClient.controller.CustomerCommandController;
import javax.swing.table.TableColumn;

import controller.ClerkCommandController;

import controller.LoginController;
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
    



	public ClerkFrame(User user) {
		 this.currentUser = user;
	     this.controller = new ClerkCommandController(); //check
	     


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
	        //tabbedPane.addTab("Process Shipment Requests", createTrackPackagePanel());
	        //tabbedPane.addTab("Handle Payments", createCreateShipmentPanel());
	      //tabbedPane.addTab("Assign Packages", createMyShipmentsPanel());
	        
	        tabbedPane.addTab("Process Shipment Requests", processShipReqPanel());
	        tabbedPane.addTab("Handle Payments", null);
	        tabbedPane.addTab("Assign Packages", null);
	        
	        
	        
	        add(tabbedPane, BorderLayout.CENTER);
	        
	        // Create bottom panel with logout
	        JPanel bottomPanel = createBottomPanel();
	        add(bottomPanel, BorderLayout.SOUTH);
	        
	        setVisible(true);
	}
	
	
	//Creativity
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
        
        
        buttonPanel.add(processShipReqBtn);
        buttonPanel.add(handlePaymentsBtn);
        buttonPanel.add(assignPackageBtn);
   
        
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
    
    
    //Functionality
    /*private JPanel processShipReqPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(249, 249, 249));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Customer Shipments");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 73, 94));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        String[] columns = {"Tracking #", "Recipient", "Weight (kg)", "Type", "Zone", "Status", "Cost", "Payment"};
    
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable shipmentsTable = new JTable(model);
        shipmentsTable.setRowHeight(25);
        shipmentsTable.setFont(new Font("Arial", Font.PLAIN, 11));
        
        List<Map<String, String>> shipments = controller.getAllOrders();
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
           List<Map<String, String>> updatedShipments = controller.getAllOrders();
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
    }*/
    
    
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
                // Only Status (5) and Payment (7) are editable
                return column == 5 || column == 7;
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
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(shipmentsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Refresh button
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);  // Clear table
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

   	
	  /** Main method for testing
	 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame(); // User logs in first
        });

    }
    
}
