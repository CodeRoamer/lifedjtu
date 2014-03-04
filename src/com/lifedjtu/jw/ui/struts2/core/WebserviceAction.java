package com.lifedjtu.jw.ui.struts2.core;

import java.util.List;

import com.lifedjtu.jw.business.JWLocalService;
import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.pojos.User;
import com.lifedjtu.jw.pojos.dto.ArticleDto;
import com.lifedjtu.jw.pojos.dto.DjtuDate;
import com.lifedjtu.jw.ui.struts2.core.support.LifeDjtuAction;
import com.lifedjtu.jw.util.LifeDjtuEnum.ResultState;

public class WebserviceAction extends LifeDjtuAction{

	private JWLocalService jwLocalService;
	private JWRemoteService jwRemoteService;
	
	
	
	public DjtuDate getDate() {
		return date;
	}

	public void setDate(DjtuDate date) {
		this.date = date;
	}

	public JWLocalService getJwLocalService() {
		return jwLocalService;
	}

	public void setJwLocalService(JWLocalService jwLocalService) {
		this.jwLocalService = jwLocalService;
	}

	public JWRemoteService getJwRemoteService() {
		return jwRemoteService;
	}

	public void setJwRemoteService(JWRemoteService jwRemoteService) {
		this.jwRemoteService = jwRemoteService;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public List<ArticleDto> getNotes() {
		return notes;
	}

	public void setNotes(List<ArticleDto> notes) {
		this.notes = notes;
	}

	public String getNoteHref() {
		return noteHref;
	}

	public void setNoteHref(String noteHref) {
		this.noteHref = noteHref;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
	
	/**
	 * -----/webservice/getDjtuNotes.action
	 */
	//in
	private int pageNum;
	//out
	private List<ArticleDto> notes;
	
	public String getDjtuNotes(){
		notes = jwRemoteService.queryRemoteNotes(pageNum);
		
		flag = makeFlag(notes);
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/getDjtuNote.action
	 */
	//in
	private String noteHref;
	//out
	private String note;
	
	public String getDjtuNote(){
		ArticleDto articleDto = new ArticleDto();
		articleDto.setHref(noteHref);
		
		note = jwRemoteService.queryRemoteNote(articleDto).getContent();
		
		flag = makeFlag(note);
		
		return SUCCESS;
	}
	
	
	/**
	 * -----/webservice/getDjtuDate.action
	 */
	private DjtuDate date;
	public String getDjtuDate(){
		
		date = jwRemoteService.queryDjtuDate(jwRemoteService.randomSessionId());
		
		flag = makeFlag(date);
		
		return SUCCESS;
	}
}
