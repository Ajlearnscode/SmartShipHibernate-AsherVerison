package com.java.hibernate;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

public class Clerk extends User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String clerkId;
	
	//default constructor
	public Clerk() {
		super();
		this.clerkId = "CL????";
	}
			
	//primary constructor
	public Clerk(String firstName, String lastName, Date dob, int age, String nationalId, Address address, String telNum, String username, String password, String email, String clerkId) {
		super(firstName,lastName, dob, age, nationalId, address, telNum, username, password, email);
		this.clerkId = clerkId;
	}
			
	//Copy constructor
	public Clerk(Clerk clk) {
		super(clk);
		this.clerkId = clk.clerkId;
	}
	
	//setters and getters
			
	public String getClerkId() {
		return this.clerkId;
	}

	public void setClerkId(String clerkId) {
		this.clerkId = clerkId;
	}

	@Override
	public String toString() {
		return "Clerk ID: " + clerkId + super.toString() + "\n";
	}
	
}//end of class 
