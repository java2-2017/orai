package hu.mik.java2.util;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {
	private SessionFactory sessionFactory;
	
	@Autowired
	public HibernateUtil(EntityManagerFactory factory) {
		if(factory.unwrap(SessionFactory.class) == null) {
			throw new RuntimeException("Not Hibernate SessionFactory");
		}
		
		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
	
	
}
