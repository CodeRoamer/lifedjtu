package com.lifedjtu.jw.business.impl;

import static com.lifedjtu.jw.util.extractor.Extractor.$;

import java.util.ArrayList;
import java.util.List;


import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.pojos.Course;
import com.lifedjtu.jw.pojos.CourseTakenItem;
import com.lifedjtu.jw.pojos.Exam;
import com.lifedjtu.jw.pojos.dto.BuildingDto;
import com.lifedjtu.jw.pojos.dto.RoomDto;
import com.lifedjtu.jw.pojos.Score;
import com.lifedjtu.jw.pojos.dto.StudentRegistry;

import com.lifedjtu.jw.util.URLFetcher;
import com.lifedjtu.jw.util.MapMaker;
import com.lifedjtu.jw.util.FetchResponse;
import com.lifedjtu.jw.util.extractor.DomElement;
import com.lifedjtu.jw.util.extractor.Extractor;

public class JWRemoteServiceImpl implements JWRemoteService {

	private String loginURL = "jw.djtu.edu.cn/academic/j_acegi_security_check";
	private String studentMessageURL = "http://jw.djtu.edu.cn/academic/showHeader.do";
	private String studentRegistryURL = "http://jw.djtu.edu.cn/academic/student/studentinfo/studentInfoModifyIndex.do?frombase=0&wantTag=0";
	private String modifyPasswordURL = "http://jw.djtu.edu.cn/academic/sysmgr/modifypasswd_user.jsdo";
	private String queryRoomURL = "http://jw.djtu.edu.cn/academic/teacher/teachresource/roomschedule_week.jsdo";
	private String queryRemoteCourseTableURL = "http://jw.djtu.edu.cn/academic/student/currcourse/currcourse.jsdo";
	private String queryRemoteExamsURL = "http://jw.djtu.edu.cn/academic/student/exam/index.jsdo";
	private String queryRemoteScoresURL = "http://jw.djtu.edu.cn/academic/manager/score/studentOwnScore.do";
	
	@Override
	public String signinRemote(String studentId, String password) {
		// TODO Auto-generated method stub
			FetchResponse fetchResponse = URLFetcher.fetchURLByPost(loginURL, null, MapMaker.instance("j_username", studentId).param("j_password", password).toMap());
			FetchResponse response2 = URLFetcher.fetchURLByGet(studentMessageURL, fetchResponse.getSessionId());
			if(response2.getStatusCode()!=200){
				System.out.println("登陆失败或已经超时，重新登陆！");
				return null;
			}else {
				return fetchResponse.getSessionId();
			}
	}

	@Override
	public StudentRegistry fetchStudentRegistry(String sessionId) {
		// TODO Auto-generated method stub
		FetchResponse fetchResponse = URLFetcher.fetchURLByGet(studentRegistryURL, sessionId);
		System.out.println(fetchResponse.getResponseBody());
		List<DomElement> list1 = Extractor.$("table[class=form] th",fetchResponse.getResponseBody());
		List<DomElement> list2 = Extractor.$("table[class=form] td",fetchResponse.getResponseBody());
		StudentRegistry studentRegistry = new StudentRegistry(list1, list2);
		return studentRegistry;
	}

	@Override
	public boolean changeRemotePassword(String sessionId, String originPass,
			String newPass, String newPassAgain) {
		// TODO Auto-generated method stub
		FetchResponse fetchResponse = URLFetcher.fetchURLByPost(modifyPasswordURL, sessionId, MapMaker.instance("oldpasswd", originPass)
																						.param("newpasswd", newPass)
																						.param("confirmedpasswd", newPassAgain)
																						.toMap());
		List<DomElement> list = Extractor.$("td[id=content_margin]",fetchResponse.getResponseBody());
		if(list.isEmpty()){
			return true;
		}
		return false;
	}

	@Override
	public BuildingDto queryBuildingOnDate(String sessionId, int aid,
			int buildingId, int week, int weekday) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoomDto queryRoom(String sessionId, int aid, int buildingId,
			int roomId) {
		// TODO Auto-generated method stub
		FetchResponse fetchResponse = URLFetcher.fetchURLByPost(queryRoomURL, sessionId, MapMaker.instance("aid", new Integer(aid).toString())
																					.param("buildingid", new Integer(buildingId).toString())
																					.param("room", new Integer(roomId).toString())
																					.toMap());
		System.out.println(fetchResponse.getResponseBody());
		
		return null;
	}

	@Override
	public List<Course> queryRemoteCourseTable(String sessionId) {
		// TODO Auto-generated method stub
		FetchResponse fetchResponse = URLFetcher.fetchURLByGet(queryRemoteCourseTableURL, sessionId);
		//System.out.println(fetchResponse.getResponseBody());
		List<DomElement> list = Extractor.$("table[class=infolist_tab]:first > tr",fetchResponse.getResponseBody());
		List<Course> courses = new ArrayList();
		for(int i=1;i<list.size()-3;i++){
			List<DomElement> courseTemp = list.get(i).children("td");
			List<DomElement> courseTakenItemTemp = courseTemp.get(9).find("tr");
			List<CourseTakenItem> courseTakenItems = new ArrayList();
			for(int j=0;j<courseTakenItemTemp.size();j++){
				List<DomElement> taken = courseTakenItemTemp.get(j).children("td");
				CourseTakenItem item = new CourseTakenItem(taken.get(0).getText().trim(),
						taken.get(1).getText().trim(),
						taken.get(2).getText().trim(),
						taken.get(3).getText().trim());
				courseTakenItems.add(item);
			}
			Course course = new Course(courseTemp.get(0).getText().trim(),
					courseTemp.get(1).getText().trim(),
					courseTemp.get(2).getText().trim(),
					courseTemp.get(3).getText().trim(),
					courseTemp.get(4).getText().trim(),
					courseTemp.get(5).getText().trim(),
					courseTemp.get(6).getText().trim(),
					courseTemp.get(7).getText().trim(),
					courseTemp.get(8).getText().trim(),
					courseTakenItems);
			courses.add(course);
		}
		return courses;
	}

	@Override
	public List<Exam> queryRemoteExams(String sessionId) {
		// TODO Auto-generated method stub
		FetchResponse fetchResponse = URLFetcher.fetchURLByGet(queryRemoteExamsURL, sessionId);
		List<DomElement> list = Extractor.$("table[class=infolist_tab] > tr",fetchResponse.getResponseBody());
		List<Exam> exams = new ArrayList();
		for(int i=1;i<list.size();i++){
			List<DomElement> examTemp = list.get(i).children("td");
			Exam exam = new Exam(examTemp.get(0).getText().trim(),
					examTemp.get(1).getText().trim(),
					examTemp.get(2).getText().trim(),
					examTemp.get(3).getText().trim(),
					examTemp.get(5).getText().trim());
			exams.add(exam);
		}
		return exams;
	}

	@Override
	public List<Score> queryRemoteScores(String sessionId) {
		// TODO Auto-generated method stub
		FetchResponse fetchResponse = URLFetcher.fetchURLByGet(queryRemoteScoresURL, sessionId);
		List<DomElement> list = Extractor.$("table[class=datalist] > tr",fetchResponse.getResponseBody());
		List<Score> scores = new ArrayList();
		for(int i=1;i<list.size();i++){
			List<DomElement> scoreTemp = list.get(i).children("td");
			Score score = new Score(scoreTemp.get(0).getText().trim(),
					scoreTemp.get(1).getText().trim(),
					scoreTemp.get(2).getText().trim(),
					scoreTemp.get(3).getText().trim(),
					scoreTemp.get(4).getText().trim(),
					scoreTemp.get(5).getText().trim(),
					scoreTemp.get(6).getText().trim(),
					scoreTemp.get(7).getText().trim(),
					scoreTemp.get(8).getText().trim(),
					scoreTemp.get(9).getText().trim(),
					scoreTemp.get(10).getText().trim(),
					scoreTemp.get(11).getText().trim(),
					scoreTemp.get(12).getText().trim(),
					scoreTemp.get(13).getText().trim(),
					scoreTemp.get(14).getText().trim(),
					scoreTemp.get(15).getText().trim());
			scores.add(score);
		}
		return scores;
	}

}
