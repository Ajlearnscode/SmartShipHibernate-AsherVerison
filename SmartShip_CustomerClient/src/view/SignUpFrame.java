package view;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import controller.SignUpController;


public class SignUpFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public SignUpFrame() {
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 700);
        setLocationRelativeTo(null);
        ImageIcon ssLogo = new ImageIcon("./assets/iutLogo.png");
        
        if (ssLogo.getIconWidth() > 0) {
            setIconImage(ssLogo.getImage());
        }
        
        getContentPane().setBackground(new Color(249, 249, 249));
        
        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(249, 249, 249));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Left panel for signup form
        JPanel leftPanel = createSignUpFormPanel();
        
        // Right panel for image
        JPanel rightPanel = createImagePanel();

        // Add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
        
        setVisible(true);
    }
    
    private JPanel createSignUpFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(249, 249, 249));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 20));
        panel.setPreferredSize(new Dimension(400, getHeight()));

        // Welcome text
        JLabel welcomeLabel1 = new JLabel("Create Account");
        welcomeLabel1.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel1.setForeground(new Color(52, 73, 94));
        welcomeLabel1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel welcomeLabel2 = new JLabel("Join SmartShip as a Customer");
        welcomeLabel2.setFont(new Font("Arial", Font.PLAIN, 14));
        welcomeLabel2.setForeground(new Color(52, 73, 94));
        welcomeLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(welcomeLabel1);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(welcomeLabel2);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Username field
        JTextField usernameField = new JTextField();
        addLabelAndField(panel, "Username:", usernameField);
        
        // Email field
        JTextField emailField = new JTextField();
        addLabelAndField(panel, "Email:", emailField);
        
        // Phone field
        JTextField phoneField = new JTextField();
        addLabelAndField(panel, "Phone:", phoneField);
        
        // Address field
        JTextField addressField = new JTextField();
        addLabelAndField(panel, "Address:", addressField);
        
        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 12));
        passwordLabel.setForeground(new Color(52, 73, 94));
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(passwordLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setMaximumSize(new Dimension(300, 40));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(236, 240, 241)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        passwordField.setBackground(Color.WHITE);
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // REMOVED: Role selection section - Customers are always registered as "Customer"
        // No need for a role dropdown

        // Sign Up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(new Color(163, 67, 53));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        signUpButton.setFocusPainted(false);
        signUpButton.setPreferredSize(new Dimension(300, 40));
        signUpButton.setMaximumSize(new Dimension(300, 40));
        signUpButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        signUpButton.addActionListener(e -> {
            // Get all field values
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
            String role = "Customer"; // HARDCODED - always register as Customer
            
            // Create controller and register
            SignUpController controller = new SignUpController();
            boolean success = controller.register(username, password, email, phone, address, role);
            
            if (success) {
                // Registration successful, go to login
                new LoginFrame();
                setVisible(false);
                dispose();
            }
            // If failed, user stays on signup page (error already shown by controller)
        });
        panel.add(signUpButton);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        return panel;
    }
    
    private void addLabelAndField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(new Color(52, 73, 94));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        field.setPreferredSize(new Dimension(300, 40));
        field.setMaximumSize(new Dimension(300, 40));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(236, 240, 241)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        field.setBackground(Color.WHITE);
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }
    
    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(249, 249, 249));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        try {
            ImageIcon signupImage = new ImageIcon("./assets/smartshipImage.png");
            JLabel imageLabel = new JLabel(signupImage);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(imageLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            // Fallback if image not found
            JLabel placeholder = new JLabel("Sign Up Image", SwingConstants.CENTER);
            placeholder.setFont(new Font("Arial", Font.ITALIC, 18));
            placeholder.setForeground(new Color(52, 73, 94));
            panel.add(placeholder, BorderLayout.CENTER);
        }

        // Add login link at the bottom
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.X_AXIS));
        loginPanel.setBackground(new Color(249, 249, 249));
        loginPanel.add(Box.createHorizontalGlue());
        
        JLabel loginLabel = new JLabel("Already have an account? ");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        loginLabel.setForeground(new Color(52, 73, 94));
        
        JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(new Color(163, 67, 53));
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> {
            new LoginFrame();
            setVisible(false);
        });
        
        loginPanel.add(loginLabel);
        loginPanel.add(loginButton);
        loginPanel.add(Box.createHorizontalGlue());
        
        panel.add(loginPanel, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        new SignUpFrame();
    }
}