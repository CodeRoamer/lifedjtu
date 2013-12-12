package com.lifedjtu.jw.ui.struts2.core;

import com.lifedjtu.jw.business.TestService;
import com.opensymphony.xwork2.Action;

public class MainAction {
	private String msg;
	private TestService testService;
	
	public TestService getTestService() {
		return testService;
	}

	public void setTestService(TestService testService) {
		this.testService = testService;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String execute(){
		msg = "Hello From Struts2"+testService.greeting()+"; Life In DJTU";
		return Action.SUCCESS;
	}
}
