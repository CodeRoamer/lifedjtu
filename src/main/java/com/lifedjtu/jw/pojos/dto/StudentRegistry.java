package com.lifedjtu.jw.pojos.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.lifedjtu.jw.pojos.EntityObject;
import com.lifedjtu.jw.pojos.User;


public class StudentRegistry extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 806815179830239704L;
	private List<String> titles;
	private List<String> values;
	public StudentRegistry(List<String> titles, List<String> values) {
		super();
		this.titles = titles;
		this.values = values;
	}
	public List<String> getTitles() {
		return titles;
	}
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public String toString(){
		String s = "";
		for(int i=0;i<titles.size();i++){
			s += titles.get(i) + ":" + values.get(i) + "\n";
		}
		return s;
	}
	
	public User autofillUserInfo(User user){
//		private String username;
//		private int gender;
//		private Date birthDate;
//		private String province;
//		
//		private int grade;
//		private String academy;
//		private String major;
//		private String cls;
//		private String area;
//		
//		private String nickname;
//		private boolean userReady;
		
		//姓名 性别 出生日期 考区 院系 专业 年级 班级 校区 
		
		Iterator<String> iteratorKey = titles.iterator();
		Iterator<String> iteratorValue = values.iterator();
		
		while(iteratorKey.hasNext()){
			String key = iteratorKey.next();
			String value = iteratorValue.next();
			
			if(key.equals("姓名")){
				user.setUsername(value);
			}else if(key.equals("性别")){
				user.setGender(value);
			}else if(key.equals("出生日期")){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					user.setBirthDate(format.parse(value));
				} catch (ParseException e) {
					e.printStackTrace();
					user.setBirthDate(new Date());
				}
			}else if(key.equals("考区")){
				user.setProvince(value);
			}else if(key.equals("院系")){
				user.setAcademy(value);
			}else if(key.equals("专业")){
				user.setMajor(value);
			}else if(key.equals("年级")){
				user.setGrade(Integer.parseInt(value.substring(0, value.length()-1)));
			}else if(key.equals("班级")){
				user.setCls(value);
			}else if(key.equals("校区")){
				user.setArea(value);
			}
			
		}
		
		//user is not ready here!! setUserReady in async method see that in Asyncer
		//user.setUserReady(true);
		
		user.setNickname("");
		
		return user;
	}
	
}
