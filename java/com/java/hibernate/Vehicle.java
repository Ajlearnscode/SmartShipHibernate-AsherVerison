package com.java.hibernate;

import java.io.Serializable;

public class Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String vin;
	private String licensePlateNum;
	private String vehicleMake;
	private String vehicleType;
	private String model;
	private String modelNumber;
	private Date year;
	private String colour;
	
	//Default Constructor
	public Vehicle() {
		this.vin = "";
		this.licensePlateNum = "";
		this.vehicleMake = "";
		this.vehicleType = "";
		this.model = "";
		this.modelNumber = "";
		this.year = new Date();
		this.colour = "";
	}
	
	//Primary Constructor
	public Vehicle(String vin, String licensePlateNum, String vehicleMake, String vehicleType, String model, String modelNumber, Date year, String colour) {
		this.vin = vin;
		this.licensePlateNum = licensePlateNum;
		this.vehicleMake = vehicleMake;
		this.vehicleType = vehicleType;
		this.model = model;
		this.modelNumber = modelNumber;
		this.year = year;
		this.colour = colour;
	}

	//Copy Constructor
	public Vehicle(Vehicle v) {
		this.vin = v.vin;
		this.licensePlateNum = v.licensePlateNum;
		this.vehicleMake = v.vehicleMake;
		this.vehicleType = v.vehicleType;
		this.model = v.model;
		this.modelNumber = v.modelNumber;
		this.year = v.year;
		this.colour = v.colour;
	}

	
	//setters and getters
	public String getVin() {
		return this.vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getLicensePlateNum() {
		return this.licensePlateNum;
	}

	public void setLicensePlateNum(String licensePlateNum) {
		this.licensePlateNum = licensePlateNum;
	}

	public String getVehicleMake() {
		return this.vehicleMake;
	}

	public void setVehicleMake(String vehicleMake) {
		this.vehicleMake = vehicleMake;
	}

	public String getVehicleType() {
		return this.vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModelNumber() {
		return this.modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public Date getYear() {
		return this.year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public String getColour() {
		return this.colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}
	
	//End of setters and getters


	@Override
	public String toString() {
		return "Vin: " + vin + 
				"\nLicense Plate Num: " + licensePlateNum + 
				"\nVehicle Make:" + vehicleMake + 
				"\nVehicle Type: " + vehicleType + 
				"\nModel: " + model + 
				"\nModel Number: " + modelNumber + 
				"\nYear: " + year + 
				"\nColour: " + colour + "\n";
	}



	
	
	

}//end of class
