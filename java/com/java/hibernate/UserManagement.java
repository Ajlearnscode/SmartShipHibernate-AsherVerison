package com.java.hibernate;

public interface UserManagement {
	public abstract void exit();
	public abstract void signUp(Customer customer);
	public abstract void login(Customer customer);
	public abstract void view(String cusId);
	public abstract void update(Customer customer);
	public abstract void delete(String cusId);
}
