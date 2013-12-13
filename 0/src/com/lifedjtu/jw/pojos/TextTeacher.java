package com.lifedjtu.jw.pojos;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;



public class TextTeacher {
	@Test
	public void textTeacher(){
		Teacher t = new Teacher();
		t.setId(1);
		t.setName("123");
		
		Configuration cfg = new Configuration();
		SessionFactory sf = cfg.configure().buildSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(t);
		session.getTransaction().commit();
		session.close();
		sf.close();
	}
}
