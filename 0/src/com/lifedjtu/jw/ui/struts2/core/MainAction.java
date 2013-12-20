package com.lifedjtu.jw.ui.struts2.core;

import com.opensymphony.xwork2.Action;

public class MainAction {
	private String msg;
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String execute(){
		msg = "Hello From Struts2 Life In DJTU";
		return Action.SUCCESS;
	}
}
