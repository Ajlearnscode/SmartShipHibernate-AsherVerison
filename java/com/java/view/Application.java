package com.java.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import com.java.hibernate.*;


public class Application extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//Attributes
	private JLabel firstNameLabel;
	private JTextField firstNameTextField;
	private JLabel lastNameLabel;
	private JTextField lastNameTextField;
	//Date of birth
	private JLabel dobLabel;
	private JLabel dayLabel;
	private JTextField dayTextField;
	private JLabel monthLabel;
	private JTextField monthTextField;
	private JLabel yearLabel;
	private JTextField yearTextField;
	//end of section
	private JLabel ageLabel;
	private JTextField ageTextField;
	private JLabel nationalIdLabel;
	private JTextField nationalIdTextField;
	//Address Section
	private JLabel countryLabel;
	private JTextField countryTextField;
	private JLabel streetLabel;
	private JTextField streetTextField;
	private JLabel cityLabel;
	private JTextField cityTextField;
	private JLabel stateLabel;
	private JTextField stateTextField;
	private JLabel zipCodeLabel;
	private JTextField zipCodeTextField;
	private JLabel idLabel;
	private JTextField idTextField;
	//Confidential
	private JLabel usernameLabel;
	private JTextField usernameTextField;
	private JLabel passwordLabel;
	private JTextField passwordTextField;
	private JLabel emailLabel;
	private JTextField emailTextField;
	private JLabel telNumLabel;
	private JTextField telNumTextField;
	
	//End of Address Section
	private JLabel newLine;
	
	//Buttons
	private JButton signUpBtn;
	private JButton loginBtn;
	
	//Desktop Pane is required for internal frames
	private JDesktopPane desktop;
	
	//Internal Frames
	JInternalFrame signUpFrame;
	JInternalFrame loginFrame;
	
	
	private static Connection dbConn =null;
	private Statement stmt = null;
	private ResultSet result = null;
	
	//application default constructor
	public Application() {
		Application.getDatabase();
		//Frame Properties
		setTitle("Smart Ship Ltd.");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1980,1080);
		setVisible(true);
		
		//creation of the desk tp pan to house the internal framw
		desktop = new JDesktopPane();
		setContentPane(desktop);
		
		//Add the internal frame
		signUpFrame("Sign Up", 600, 700); //title, width, height of the internal signup frame
		
		
		
		
	}
	
	
	//This method is used to create a sign up internal frame to be used in the constructor
	public void signUpFrame(String title,  int width, int height) {
		
		signUpFrame = new JInternalFrame(title, true, true, true, true);
		
		//Internal frame attributes
		signUpFrame.setSize(width, height);
		
		signUpFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		//Layouts and constraints		
		signUpFrame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		//Labels
		//First Name Label
		firstNameLabel = new JLabel("First Name: ");
		gbc.gridx = 0; //column 0
		gbc.gridy = 0; //row 0
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(firstNameLabel, gbc);
		
		//First Name Text Field
		firstNameTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 0; //row 0
		gbc.anchor = GridBagConstraints.EAST;
		signUpFrame.add(firstNameTextField, gbc);
		
		
		//Last Name Label
		lastNameLabel = new JLabel("Last Name: ");
		gbc.gridx = 0; //column 0
		gbc.gridy = 1; //row 1
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(lastNameLabel, gbc);
		
		//Last Name Text Field
		lastNameTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 1; //row 1
		gbc.anchor = GridBagConstraints.EAST;
		signUpFrame.add(lastNameTextField, gbc);
		
		
		
		//new line (row)
		newLine = new JLabel("--------------------------------------------------------");
		gbc.gridx = 0; //column 0
		gbc.gridy = 2; //row 3
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(newLine, gbc);
		
		//Date of Birth Labels
		dobLabel = new JLabel("Date of Birth");
		gbc.gridx = 0; //column 0
		gbc.gridy = 3; //row 4
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.CENTER;
		signUpFrame.add(dobLabel, gbc);
		
		//Day Label
		dayLabel = new JLabel("Day: ");
		gbc.gridx = 0; //column 0
		gbc.gridy = 4; //row 5
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(dayLabel, gbc);
		
		//Day Text Field
		dayTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 4; //row 5
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(dayTextField, gbc);
		
		//Month Label
		monthLabel = new JLabel("Month: ");
		gbc.gridx = 0; //column 0
		gbc.gridy = 5; //row 6
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(monthLabel, gbc);
		
		//Month Text Field
		monthTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 5; //row 6
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(monthTextField, gbc);
		
		//year Label
		yearLabel = new JLabel("Year: ");
		gbc.gridx = 0; //column 0
		gbc.gridy = 6; //row 7
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(yearLabel, gbc);
		
		//Year Text Field
		yearTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 6; //row 7
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(yearTextField, gbc);
		
		//Age 
		//Age Label
		ageLabel = new JLabel("Age: ");
		gbc.gridx = 0; //column 0
		gbc.gridy = 7; //row 8
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(ageLabel, gbc);
		
		//Age Text Field
		ageTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 7; //row 8
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(ageTextField, gbc);
		ageTextField.setEditable(false);
		
		//new line (row)
		newLine = new JLabel("--------------------------------------------------------");
		gbc.gridx = 0; //column 0
		gbc.gridy = 8; //row 9
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(newLine, gbc);
		
		
		
		//new line (row)
		newLine = new JLabel("Address");
		gbc.gridx = 0; //column 0
		gbc.gridy = 9; //row 10
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.CENTER;
		signUpFrame.add(newLine, gbc);
		
		
		//National Id Label
		nationalIdLabel = new JLabel("National ID: ");
		gbc.gridx = 0; //column 0
		gbc.gridy = 10; //row 11
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(nationalIdLabel, gbc);
		
		
		//National ID Text Field
		nationalIdTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 10; //row 11
		//gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(nationalIdTextField, gbc);
		
	
		//Address Label
		countryLabel = new JLabel("Country: ");
		gbc.gridx = 0; //column 1
		gbc.gridy = 11; //row 12
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(countryLabel, gbc);
		
		//Address Label
		countryTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 11; //row 12
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(countryTextField, gbc);
		
		
		//Street Id Label
		streetLabel = new JLabel("Street: ");
		gbc.gridx = 0; //column 1
		gbc.gridy = 12; //row 13
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(streetLabel, gbc);
		
		//Street Text Field
		streetTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 12; //row 13
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(streetTextField, gbc);
		
		//City  Label
		cityLabel = new JLabel("City: ");
		gbc.gridx = 0; //column 1
		gbc.gridy = 13; //row 14
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(cityLabel, gbc);
		
		//City Text Field
		cityTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 13; //row 14
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(cityTextField, gbc);

		
		//State Label
		stateLabel = new JLabel("State: ");
		gbc.gridx = 0; //column 0
		gbc.gridy = 14; //row 15
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(stateLabel, gbc);
		
		//State Text Field
		stateTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 14; //row 15
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(stateTextField, gbc);
		
		
		//ZipCode Label
		zipCodeLabel = new JLabel("Zip Code: ");
		gbc.gridx = 0; //column 0
		gbc.gridy = 15; //row 16
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(zipCodeLabel, gbc);
		
		//ZipCode Text Field
		zipCodeTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 15; //row 16
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(zipCodeTextField, gbc);
		
		//new line (row)
		newLine = new JLabel("--------------------------------------------------------");
		gbc.gridx = 0; //column 0
		gbc.gridy = 16; //row 17
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(newLine, gbc);
		
		//ID Label
		idLabel = new JLabel("User ID: ");
		gbc.gridx = 0; //column 0
		gbc.gridy = 17; //row 18
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(idLabel, gbc);
		
		//ID Text Field
		idTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 17; //row 18
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(idTextField, gbc);
		idTextField.setEditable(false);
		
		
		//Username Label
		usernameLabel = new JLabel("Username: ");
		gbc.gridx = 0; //column 0
		gbc.gridy = 18; //row 19
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(usernameLabel, gbc);
		
		//Username Text Field
		usernameTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 18; //row 19
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(usernameTextField, gbc);
		
		
		//Passowrd Label
		passwordLabel = new JLabel("Password: ");
		gbc.gridx = 0; //column 0
		gbc.gridy = 19; //row 20
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(passwordLabel, gbc);
		
		//Passowrd Text Field
		passwordTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 19; //row 20
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(passwordTextField, gbc);
		
		//email Label
		emailLabel = new JLabel("Email: ");
		gbc.gridx = 0; //column 0
		gbc.gridy = 20; //row 21
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(emailLabel, gbc);
		
		//Email Text Field
		emailTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 20; //row 21
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(emailTextField, gbc);

		//Telephone Number Label
		telNumLabel = new JLabel("Telephone: ");
		gbc.gridx = 0; //column 0
		gbc.gridy = 21; //row 22
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(telNumLabel, gbc);
		
		//Telephone Number Text Field
		telNumTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 21; //row 22
		gbc.gridwidth = 3; //span 2 cols
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(telNumTextField, gbc);
		
		//Buttons
		signUpBtn = new JButton("Sign Up");
		signUpBtn.setSize(30, 30);
		gbc.gridx = 0;
		gbc.gridy = 22;
		gbc.anchor = GridBagConstraints.WEST;
		signUpFrame.add(signUpBtn, gbc);
		
		//Buttons
		loginBtn = new JButton("Login");
		loginBtn.setSize(30, 30);
		gbc.gridx = 1;
		gbc.gridy = 22;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 150, 10, 10);
		signUpFrame.add(loginBtn, gbc);

		
		//DO NOT TOUCH!!!
		//Left last to ensure visibility
		signUpFrame.setVisible(true);
		
        // Add to desktop pane
        desktop.add(signUpFrame);
        
        signUpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String cusId = idTextField.getText().trim();
				String firstName = firstNameTextField.getText().trim();
				String lastName = lastNameTextField.getText().trim();
				
				
				String dayS = dayTextField.getText().trim();
				String monthS = monthTextField.getText().trim();
				String yearS = yearTextField.getText().trim();
				String ageS = ageTextField.getText().trim();
				

				
				String nationalId = nationalIdTextField.getText().trim();
				String country = countryTextField.getText().trim();
				String street = streetTextField.getText().trim();
				String city = cityTextField.getText().trim();
				String state = stateTextField.getText().trim();
				String zipCode = zipCodeTextField.getText().trim();
				String username = usernameTextField.getText().trim();
				String password = passwordTextField.getText().trim();
				String email = emailTextField.getText().trim();
				String telNum = telNumTextField.getText().trim();
				String paymentMethod = "?";
				
				if (firstName.isEmpty() || lastName.isEmpty() || nationalId.isEmpty() || country.isEmpty() || street.isEmpty()
						|| city.isEmpty() || state.isEmpty() || zipCode.isEmpty() || username.isEmpty()|| email.isEmpty() || password.isEmpty() 
						|| telNum.isEmpty() || dayS.isEmpty() || monthS.isEmpty() || yearS.isEmpty()){
					JOptionPane.showMessageDialog(
							signUpFrame, 
							"Data missing. Fill in all fields", 
							"Create Status", 
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			
				//parse the data once we know that the data field is not void
				int day = Integer.parseInt(dayS);
				int month = Integer.parseInt(monthS);
				int year = Integer.parseInt(yearS);
				
				//calulation for the age of the user
				int currentYear = java.time.LocalDate.now().getYear();
				int age = currentYear - year;
				
				String ageConvertString = Integer.toString(age);
				
				ageTextField.setText(ageConvertString);
				
				retrievePreviousId();
				
				boolean isAdded = create(new Customer( firstName,  lastName, new Date(day, month, year) , age,  nationalId, new Address(country, street, city, state, zipCode),  telNum,  username,  password,  email,  paymentMethod));
				if (isAdded) {
					JOptionPane.showMessageDialog(
							signUpFrame, 
							"Record added", 
							"Create Status", 
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
        
      //add an action listener
      	loginBtn.addActionListener(new ActionListener(){
      			
      	//when the login button is pressed, the system moves from the sign up form to the login form for existing user
      		@Override
      		public void actionPerformed(ActionEvent e) {
      			try {
      				signUpFrame.setClosed(true);//this closes the sign up internal frame
      			} catch (PropertyVetoException e1) {
      				e1.printStackTrace();
      			}
      			loginFrame("Login", 500, 500);
      		}
      	}); //end of login btn action listener
      	
      	

	}//end of signUpFrame method
	
	public void loginFrame(String title,  int width, int height) {
		
		//Create the new frame
		loginFrame = new JInternalFrame("Login", true, true, true, true);
		loginFrame.setSize(500, 500);
		loginFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		loginFrame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
	
		//Labels
		//User Name
		usernameLabel = new JLabel("Username: ");
		gbc.gridx = 0; //column 1
		gbc.gridy = 0; //row 1
		gbc.anchor = GridBagConstraints.WEST;
		loginFrame.add(usernameLabel, gbc);
		
		usernameTextField = new JTextField(30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 0; //row 1
		gbc.anchor = GridBagConstraints.WEST;
		loginFrame.add(usernameTextField, gbc);
		
		//Password 
		passwordLabel = new JLabel("Password: ");
		gbc.gridx = 0; //column 1
		gbc.gridy = 1; //row 2
		gbc.anchor = GridBagConstraints.WEST;
		loginFrame.add(passwordLabel, gbc);
		
		passwordTextField = new JTextField(30);
		gbc.gridx = 1; //column 
		gbc.gridy = 1; //row 2
		gbc.anchor = GridBagConstraints.WEST;
		loginFrame.add(passwordTextField, gbc);
		
		//Buttons
		signUpBtn = new JButton("Sign Up");
		signUpBtn.setSize(30, 30);
		gbc.gridx = 0; //column 0
		gbc.gridy = 2; //row 3
		gbc.anchor = GridBagConstraints.WEST;
		loginFrame.add(signUpBtn, gbc);
		
		//Buttons
		loginBtn = new JButton("Login");
		loginBtn.setSize(30, 30);
		gbc.gridx = 1; //column 2
		gbc.gridy = 2; //row 3
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 150, 10, 10);
		loginFrame.add(loginBtn, gbc);
		
		loginFrame.setVisible(true);
		
        // Add to desktop pane
        desktop.add(loginFrame);
		
      
        
      //add an action listener
      signUpBtn.addActionListener(new ActionListener(){
      //when the sign up button is pressed, the system moves from the login form to the sign up form for current user
      @Override
      public void actionPerformed(ActionEvent e) {
      		try {
      			loginFrame.setClosed(true);//this closes the login internal frame
      					
      			} catch (PropertyVetoException e1) {
      				e1.printStackTrace();
      			}
      				signUpFrame("Sign Up", 600, 700);
      			}
      	});//End of sign-up button action listener 
	}//End of loginFram 
	
	
	
	private static Connection getDatabase() {
		if (dbConn==null) {
			final String url = "jdbc:mysql://localhost:3306/apProjectv1";
			try {
				dbConn = DriverManager.getConnection(url, "root", "");
				if (dbConn != null) {
					//System.out.println("Connection Success");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dbConn;
	}
	
	private boolean create(Customer cus) {
		String sql = "INSERT INTO apProjectv1.Customer (cusId, firstName, lastName, day, month, year, age, nationalId, country, street, city, state, zipCode, username, password, email, telNum, paymentMethod) "
				   + "VALUES ('"+cus.getCusId()+"','"+cus.getFirstName()+"','"+cus.getLastName()+"','"+cus.getDob().getDay()+"','"+cus.getDob().getMonth()+"', '"+cus.getDob().getYear()+"', '"+cus.getAge()+"', '"+cus.getNationalId()+"', '"+cus.getAddress().getCountry()+"', '"+cus.getAddress().getStreet()+"', '"+cus.getAddress().getCity()+"', '"+cus.getAddress().getState()+"', '"+cus.getAddress().getZipCode()+"', '"+cus.getUsername()+"', '"+cus.getPassword()+"', '"+cus.getEmail()+"', '"+cus.getTelNum()+"',  '"+cus.getPaymentMethod()+"');";
		try {
			//System.out.println(sql);
			stmt = dbConn.createStatement();
			int affectedRows = stmt.executeUpdate(sql);
			return affectedRows == 1;
		} catch (SQLSyntaxErrorException e) {
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	protected void retrievePreviousId() {
		String sql = "SELECT MAX(cusId) AS lastId FROM Customer;";
		try {
			//System.out.println(sql);
			stmt = dbConn.createStatement();
			result = stmt.executeQuery(sql);
			if (result.next()) {
				int lastId = result.getInt("lastId"); // get the highest cusId
	            int nextId = lastId + 1;              // next ID value
	            idTextField.setText(String.valueOf(nextId));
			}else {
				idTextField.setText("1");
			}
		
		} catch (SQLSyntaxErrorException e) {
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
