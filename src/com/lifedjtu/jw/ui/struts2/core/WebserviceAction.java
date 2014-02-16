package com.lifedjtu.jw.ui.struts2.core;

import com.lifedjtu.jw.business.JWLocalService;
import com.lifedjtu.jw.pojos.User;
import com.lifedjtu.jw.ui.struts2.core.support.LifeDjtuAction;
import com.lifedjtu.jw.util.LifeDjtuEnum.ResultState;

public class WebserviceAction extends LifeDjtuAction{

	private JWLocalService jwLocalService;
	
	
	public JWLocalService getJwLocalService() {
		return jwLocalService;
	}

	public void setJwLocalService(JWLocalService jwLocalService) {
		this.jwLocalService = jwLocalService;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7133236231253538495L;

	/**
	 * ------/webservice/needLogin.action
	 */
	//out
	//private int flag;
	public String needLogin(){
		flag = ResultState.NEEDLOGIN.ordinal();
		return SUCCESS;
	}
	
	/**
	 * ------/webservice/fail.action
	 */
	//out
	//private int flag;
	public String fail(){
		flag = ResultState.FAIL.ordinal();
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/checkUser.action
	 */
	//in
	private String studentId;
	//out
	private boolean exist;
	
	public String checkUser(){
		exist = jwLocalService.isUserExist(studentId);
		
		flag = makeFlag(true);
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/signin.action
	 */
	//in
	//private String studentId;
	private String password;
	//out
	private String privateKey = "";
	
	public String signin(){
		User user = jwLocalService.signinLocal(studentId, password);
		
		flag = makeFlag(user);
		
		if(flag==ResultState.SUCCESS.ordinal())
			privateKey = user.getPrivateKey();
		
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/signup.action
	 */
	//in
	//private String studentId;
	//private String password;
	//out
	//private String privateKey;

	public String signup(){
		User user = jwLocalService.signupLocal(studentId, password);
		
		flag = makeFlag(user);
		
		if(flag==ResultState.SUCCESS.ordinal())
			privateKey = user.getPrivateKey();
		
		
		return SUCCESS;
	}
	
	
}
