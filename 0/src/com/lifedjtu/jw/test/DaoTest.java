package com.lifedjtu.jw.test;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.lifedjtu.jw.dao.CriteriaWrapper;
import com.lifedjtu.jw.dao.Pageable;
import com.lifedjtu.jw.dao.Sortable;
import com.lifedjtu.jw.dao.impl.UserDao;
import com.lifedjtu.jw.pojos.User;

public class DaoTest {
	@Test
	public void testRemoteService(){
		char sep = File.separatorChar;
		ApplicationContext ctx=new FileSystemXmlApplicationContext("WebRoot"+sep+"WEB-INF"+sep+"applicationContext.xml");

//		CriteriaWrapper wrapper = CriteriaWrapper.instance(Restrictions.eq("id", "\"hello\""), Restrictions.between("bba", "1", "5")).or(Restrictions.isEmpty("jiji"),Restrictions.eq("lla", "hage"));
//		UpdateWrapper updateWrapper = UpdateWrapper.instance().inc("value", -1).set("good", "haha");
//		
//		System.out.println(updateWrapper.getUpdate(User.class, wrapper));
//		
		UserDao userDao = (UserDao)ctx.getBean("userDao");
//		User user = new User();
//		user.setId(UUID.randomUUID().toString());
//		user.setStudentId("1018110207");
//		user.setUsername("李辛洋");
//		user.setPassword("12345");
		long start = System.currentTimeMillis();
		List<User> users = userDao.findByParams(CriteriaWrapper.instance(Restrictions.eq("username", "李辛洋"),Restrictions.isNotNull("academy")));
		long end = System.currentTimeMillis();
		System.err.println((end-start)/(double)1000+"s");
		for(User user : users){
			System.out.println(user.toJSON());
		}
		//System.out.println(user.toJSON());
	}
}
