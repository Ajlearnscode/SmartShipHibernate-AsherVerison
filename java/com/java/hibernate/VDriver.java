package com.java.hibernate;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

public class VDriver extends User implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String driverId;
		//private Route route;
		private Vehicle assignedVehicle;
		private DriverLicense license;
		private Delivery assignedDeliveries;
		
		
		//default constructor
		public VDriver() {
			super();
			this.driverId = "D????";
			this.assignedVehicle = new Vehicle();
			this.license = new DriverLicense();
		}
		
		//primary constructor
		public VDriver(String firstName, String lastName, Date dob, int age, String nationalId, Address address, String telNum, String username, String password, String email, String driverId, Vehicle assignedVehicle, DriverLicense license) {
			super( firstName,  lastName,  dob,  age,  nationalId,  address,  telNum,  username,  password,  email);
			this.driverId = driverId;
			this.assignedVehicle = assignedVehicle;
			this.license = license;
		}

		//COPY constructor
		public VDriver(VDriver v) {
			super(v);
			this.driverId = v.driverId;
			this.assignedVehicle = v.assignedVehicle;
			this.license = v.license;
		}

		//Setter and getters
		public String getDriverId() {
			return this.driverId;
		}

		public void setDriverId(String driverId) {
			this.driverId = driverId;
		}

		public Vehicle getAssignedVehicle() {
			return this.assignedVehicle;
		}

		public void setAssignedVehicle(Vehicle assignedVehicle) {
			this.assignedVehicle = assignedVehicle;
		}

		public DriverLicense getLicense() {
			return this.license;
		}

		public void setLicense(DriverLicense license) {
			this.license = license;
		}
		
		
		@Override
		public String toString() {
			return "Driver ID: " + driverId +
					"\nFirst Name: " + this.firstName + 
					"\nLast Name: " + this.lastName + 
					"\nDob: " + this.dob + 
					"\nAge:" + this.age + 
					"\nNational Id: " + this.nationalId + 
					"\nAddress: " + this.address + 
					"\nTel Number: " + this.telNum + 
					"\nAssigned Vehicle: " + assignedVehicle.toString() +
					"\nDriver's License: " + license.toString() + "\n";
		}
}
