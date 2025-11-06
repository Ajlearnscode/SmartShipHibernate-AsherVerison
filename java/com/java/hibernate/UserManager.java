package com.java.hibernate;
import com.java.view.*;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class UserManager implements UserManagement {
	
	private static SessionFactory sessionFactory = null;
	
	public UserManager() {
		sessionFactory = buildSessionFactory();
	}

	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure().buildSessionFactory();
		}catch(Exception e) {
			System.err.println("SessionFactory creation failed: " + e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
		
		//return sessionFactory;
	}
	
	@Override
	public void exit() {

	}

	@Override
	public void signUp(Customer customer) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.persist(customer);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void login(Customer customer) {

	}

	@Override
	public void view(String cusId) {

	}

	@Override
	public void update(Customer customer) {

	}

	@Override
	public void delete(String cusId) {

	}
	
	public static void main(String[] args) {
		UserManager manager = new UserManager();
		//Customer cus1 = new Customer("Asher", "Maxwell", new Date(7,8,2002), 23, "12345678", new Address("Jamaica", "Fairview", "Montego Bay", "St.James", "JMCJS13"), "876-123-4567", "ashmax", "12345", "ashermaxwell@gmail.com", "Card");
		
		new Application();
		
	//	manager.signUp(cus1);
		manager.exit();
	}

}
