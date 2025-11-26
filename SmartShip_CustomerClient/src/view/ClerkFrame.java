package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.User;



public class ClerkFrame extends JFrame {
	

	    private static final long serialVersionUID = 1L;

		public ClerkFrame(User user) {
	        setTitle("Clerk Frame");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(950, 700);
	        setLocationRelativeTo(null);
	        ImageIcon ssLogo = new ImageIcon("./assets/iutLogo.png");
	        setIconImage(ssLogo.getImage());

	        if (ssLogo.getIconWidth() > 0) {
	            setIconImage(ssLogo.getImage());
	            System.out.println("Icon set successfully!");
	        }
	        
	        getContentPane().setBackground(new Color(249, 249, 249));
	        
	        // Create main panel with BorderLayout
	        JPanel mainPanel = new JPanel(new BorderLayout());
	        mainPanel.setBackground(new Color(249, 249, 249));
	        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	       

	        add(mainPanel);
	        
	        setVisible(true);
	    }
		
		//test to open frame
		public static void main(String [] args) {
			new ClerkFrame(null);
			
		}
	}
