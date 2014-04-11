package com.lifedjtu.jw.ui.struts2.core.support;


import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.lifedjtu.jw.util.LifeDjtuEnum.ResultState;
import com.opensymphony.xwork2.ActionSupport;

public class LifeDjtuAction extends ActionSupport implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6638851538209732942L;
	
	protected int flag;
	protected Map<String, Object> session;
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public int makeFlag(boolean bool){
		if(bool)
			return ResultState.SUCCESS.ordinal();
		else
			return ResultState.FAIL.ordinal();
	}

	public int makeFlag(Object obj){
		if(obj!=null)
			return ResultState.SUCCESS.ordinal();
		else
			return ResultState.FAIL.ordinal();
	}

	
}
