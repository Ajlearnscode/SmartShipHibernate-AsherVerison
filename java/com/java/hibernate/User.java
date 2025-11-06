package com.java.hibernate;
import java.io.Serializable;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class User implements Serializable {

		private static final long serialVersionUID = 1L;
		//credentials
		protected String firstName;
		protected String lastName;
		@Embedded
		protected Date dob; //composition relationship
		protected int age;
		//important legal details
		protected String nationalId;
		
		@Embedded
		protected Address address; //composition relationship
		//Misc. attributes
		protected String telNum;
		//Confidential
		protected String username;
		protected String password;
		protected String email;
		
		//default constructor
		public User() {
			super();
			this.firstName = "?";
			this.lastName = "?";
			this.dob = new Date();
			this.age = 0;
			this.nationalId = "0";
			this.address = new Address();
			this.telNum = "(000) - 000 - 0000";
			this.username = "?";
			this.password = "?";
			this.email = "?";
		}

		//primary constructor
		public User(String firstName, String lastName, Date dob, int age, String nationalId, Address address, String telNum, String username, String password, String email) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.dob = dob;
			this.age = age;
			this.nationalId = nationalId;
			this.address = address;
			this.telNum = telNum;
			this.username = username;
			this.password = password;
			this.email = email;
			
		}
		
		//Copy constructor
		public User(User usr) {
			super();
			this.firstName = usr.firstName;
			this.lastName = usr.lastName;
			this.dob = usr.dob;
			this.age = usr.age;
			this.nationalId = usr.nationalId;
			this.address = usr.address;
			this.telNum = usr.telNum;
			this.username = usr.username;
			this.password = usr.password;
			this.email = usr.email;
		}
		
		
		//setters and getters
		
		public String getFirstName() {
			return this.firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return this.lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public Date getDob() {
			return this.dob;
		}

		public void setDob(Date dob) {
			this.dob = dob;
		}

		public int getAge() {
			return this.age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getNationalId() {
			return this.nationalId;
		}

		public void setNationalId(String nationalId) {
			this.nationalId = nationalId;
		}

		public Address getAddress() {
			return this.address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}

		public String getTelNum() {
			return this.telNum;
		}

		public void setTelNum(String telNum) {
			this.telNum = telNum;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String toString() {
			return "First Name: " + firstName + 
					"\nLast Name: " + lastName + 
					"\nDob: " + dob + 
					"\nAge:" + age + 
					"\nNational Id: " + nationalId + 
					"\nAddress: " + address + 
					"\nTel Number: " + telNum + 
					"\nUsername: " + username +
					"\nEmail: " + email + "\n";
		}
}
