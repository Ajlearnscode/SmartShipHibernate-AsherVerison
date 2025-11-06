package com.java.hibernate;

import java.io.Serializable;

public class DriverLicense implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String classLetter;
	private String trn;
	private Date dateIssued;
	private Date expiryDate;
	private String collectorate;
	private Date dob;
	private char sex;
	private String firstName;
	private String middleName;
	private String lastName;
	private Address address;
	private String licensedToDrive;
	private String controlNum;
	private String nationality;
	
	//Default constructor
	public DriverLicense() 
	{
		this.classLetter = "";
		this.trn = "";
		this.dateIssued = new Date();
		this.expiryDate = new Date();
		this.collectorate = "";
		this.dob = new Date();
		this.sex = '-';
		this.firstName = "";
		this.middleName = "";
		this.lastName = "";
		this.address = new Address();
		this.licensedToDrive = "";
		this.controlNum = "";
		this.nationality = "";
	}
	

	
	//Primary Constructor
	public DriverLicense(String classLetter, String trn, Date dateIssued, Date expiryDate, String collectorate,
			Date dob, char sex, String firstName, String middleName, String lastName, Address address,
			String licensedToDrive, String controlNum, String nationality) 
	{
		this.classLetter = classLetter;
		this.trn = trn;
		this.dateIssued = dateIssued;
		this.expiryDate = expiryDate;
		this.collectorate = collectorate;
		this.dob = dob;
		this.sex = sex;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.address = address;
		this.licensedToDrive = licensedToDrive;
		this.controlNum = controlNum;
		this.nationality = nationality;
	}
	
	//COpy Constructor
	public DriverLicense(DriverLicense dl) {
		this.classLetter = dl.classLetter;
		this.trn = dl.trn;
		this.dateIssued = dl.dateIssued;
		this.expiryDate = dl.expiryDate;
		this.collectorate = dl.collectorate;
		this.dob = dl.dob;
		this.sex = dl.sex;
		this.firstName = dl.firstName;
		this.middleName = dl.middleName;
		this.lastName = dl.lastName;
		this.address = dl.address;
		this.licensedToDrive = dl.licensedToDrive;
		this.controlNum = dl.controlNum;
		this.nationality = dl.nationality;
	}
	
	
	
	public String getClassLetter() {
		return this.classLetter;
	}



	public void setClassLetter(String classLetter) {
		this.classLetter = classLetter;
	}



	public String getTrn() {
		return this.trn;
	}



	public void setTrn(String trn) {
		this.trn = trn;
	}



	public Date getDateIssued() {
		return this.dateIssued;
	}



	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}



	public Date getExpiryDate() {
		return this.expiryDate;
	}



	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}



	public String getCollectorate() {
		return this.collectorate;
	}



	public void setCollectorate(String collectorate) {
		this.collectorate = collectorate;
	}



	public Date getDob() {
		return this.dob;
	}



	public void setDob(Date dob) {
		this.dob = dob;
	}



	public char getSex() {
		return this.sex;
	}



	public void setSex(char sex) {
		this.sex = sex;
	}



	public String getFirstName() {
		return this.firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getMiddleName() {
		return this.middleName;
	}



	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}



	public String getLastName() {
		return this.lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public Address getAddress() {
		return this.address;
	}



	public void setAddress(Address address) {
		this.address = address;
	}



	public String getLicensedToDrive() {
		return this.licensedToDrive;
	}



	public void setLicensedToDrive(String licensedToDrive) {
		this.licensedToDrive = licensedToDrive;
	}



	public String getControlNum() {
		return this.controlNum;
	}



	public void setControlNum(String controlNum) {
		this.controlNum = controlNum;
	}



	public String getNationality() {
		return this.nationality;
	}



	public void setNationality(String nationality) {
		this.nationality = nationality;
	}



	@Override
	public String toString() {
		return "Class: " + classLetter +
				"\nTRN: " + trn +
				"\nDate-Issued: " + dateIssued + 
				"\nExpiry-Date: " + expiryDate +
				"\nCollectorate: " + collectorate +
				"\nDate-of-Birth: " + dob + 
				"\nSex: " + sex +
				"\nFirst Name: " + firstName + 
				"\nMiddle Name: " + middleName +
				"\nLast Name: " + lastName +
				"\nAddress: " + address.toString() +
				"\nLicensed to Drive: " + licensedToDrive +
				"\nControl Number: " + controlNum +
				"\nNationality: " + nationality + "\n";
	}
	

}
