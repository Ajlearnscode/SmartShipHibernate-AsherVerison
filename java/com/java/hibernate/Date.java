package com.java.hibernate;

import jakarta.persistence.Embeddable;

@Embeddable
public class Date {
	
	private int day;
	private int month;
	private int year;
	
	public Date() {
		this.day = 0;
		this.month = 0;
		this.year = 0;
	}
	
	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public Date(Date d) {
		this.day = d.day;
		this.month = d.month;
		this.year = d.year;
	}
	
	//getters
	public int getDay() {
		return this.day;
	}
	
	public int getMonth() {
		return this.month;
	}
	
	public int getYear() {
		return this.year;
	}
	
	//setters
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public void display() {
		System.out.println(toString());
	}
	
	public String toString() {
		return "Day: " + day +
				"\nMonth: " + month +
				"\nYear: " + year + "\n";
	}
	
}
