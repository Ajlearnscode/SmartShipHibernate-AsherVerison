package com.java.hibernate;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

		private String country;
		private String street;
		private String city;
		private String state;
		private String zipCode;
		
		//default constructor
		public Address() {
			this.country = " ";
			this.street = " ";
			this.city = " ";
			this.state = " ";
			this.zipCode = " ";
		}
		
		//primary constructor
		public Address(String country, String street, String city, String state, String zipCode) {
			this.country = country;
			this.street = street;
			this.city = city;
			this.state = state;
			this.zipCode = zipCode;
		}
		
		//copy constructor
		public Address(Address add) {
			this.country = add.country;
			this.street = add.street;
			this.city = add.city;
			this.state = add.state;
			this.zipCode = add.zipCode;
		}

		public String getCountry() {
			return this.country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getStreet() {
			return this.street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getCity() {
			return this.city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return this.state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getZipCode() {
			return this.zipCode;
		}

		public void setZipCode(String zipCode) {
			this.zipCode = zipCode;
		}
}
