package com.lifedjtu.jw.dao.impl;

import com.lifedjtu.jw.dao.TestDao;

public class TestDaoImpl implements TestDao{

	@Override
	public String greeting() {
		return "Hello from dao";
	}
	
}
