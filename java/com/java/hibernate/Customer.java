package com.java.hibernate;
import java.io.Serializable;
import javax.swing.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Customer") //name of the table in the database
public class Customer extends User implements Serializable{
	
		private static final long serialVersionUID = 1L;
		@Id
		@Column(name= "cusId")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long cusId;
		private String paymentMethod;
		
		
		//default constructor
		public Customer() {
			super();
			this.cusId = 0L;
			this.paymentMethod = "";
		}
		
		//primary constructor - 1
		public Customer(String firstName, String lastName, Date dob, int age, String nationalId, Address address, String telNum, String username, String password, String email, long cusId, String paymentMethods) {
			super(firstName,lastName, dob, age, nationalId, address, telNum,  username,  password,  email);
			this.cusId = cusId;
			this.paymentMethod = paymentMethods;
		}
		
		//primary constructor - 2
		public Customer(String firstName, String lastName, Date dob, int age, String nationalId, Address address, String telNum, String username, String password, String email, String paymentMethods) {
			super(firstName,lastName, dob, age, nationalId, address, telNum,  username,  password,  email);
			this.paymentMethod = paymentMethods;
		}
		
		//Copy constructor
		public Customer(Customer cus) {
			super(cus);
			this.cusId = cus.cusId;
			this.paymentMethod = cus.paymentMethod;
		}
		
		
		//setters and getters
		public long getCusId() {
			return this.cusId;
		}

		public void setCusId(long cusId) {
			this.cusId = cusId;
		}

		public String getPaymentMethod() {
			return this.paymentMethod;
		}

		public void setPaymentMethod(String paymentMethod) {
			this.paymentMethod = paymentMethod;
		}
		
		/* Customers can create accounts
		 * Login 
		 * Create Shipments
		 * */
		


		/*@Override
		public String toString() {
			return "Customer ID: " + cusId +
					"\nFirst Name: " + firstName + 
					"\nLast Name: " + lastName + 
					"\nDob: " + dob + 
					"\nAge:" + age + 
					"\nNational Id: " + nationalId + 
					"\nAddress: " + address + 
					"\nTel Number: " + telNum + "\n";
		}*/
		
		@Override
		public String toString() {
			return "Customer ID: " + cusId + super.toString() + "\n";
		}
		
}
