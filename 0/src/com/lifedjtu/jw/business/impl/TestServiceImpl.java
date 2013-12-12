package com.lifedjtu.jw.business.impl;

import org.springframework.stereotype.Component;

import com.lifedjtu.jw.business.TestService;

@Component("testService")
public class TestServiceImpl implements TestService{
	public String greeting(){
		return "From Spring";
	}
}
