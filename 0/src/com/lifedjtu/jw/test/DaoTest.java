package com.lifedjtu.jw.test;

import java.io.File;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.lifedjtu.jw.dao.impl.UserDao;
import com.lifedjtu.jw.pojos.User;

public class DaoTest {
	@Test
	public void testRemoteService(){
		char sep = File.separatorChar;
		ApplicationContext ctx=new FileSystemXmlApplicationContext("WebRoot"+sep+"WEB-INF"+sep+"applicationContext.xml");

		UserDao userDao = (UserDao)ctx.getBean("userDao");
		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setStudentId("1018110323");
		user.setUsername("李赫");
		user.setPassword("911119");
		long start = System.currentTimeMillis();
		userDao.add(user);
		long end = System.currentTimeMillis();
		System.err.println((end-start)/(double)1000+"s");
		
	}
}
