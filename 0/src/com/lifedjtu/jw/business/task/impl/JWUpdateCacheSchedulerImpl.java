package com.lifedjtu.jw.business.task.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lifedjtu.jw.business.task.JWUpdateCacheScheduler;
import com.lifedjtu.jw.dao.impl.UserDao;
import com.lifedjtu.jw.dao.support.UUIDGenerator;
import com.lifedjtu.jw.pojos.User;

@Component("jwUpdateCacheScheduler")
public class JWUpdateCacheSchedulerImpl implements JWUpdateCacheScheduler{
	@Autowired
	private UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void updateRoomInfo() {
		User user = new User();
		user.setId(UUIDGenerator.randomUUID());
		user.setUsername("李赫");
		userDao.add(user);
		System.err.println("in updateRoomInfo!!!");
	}

}
