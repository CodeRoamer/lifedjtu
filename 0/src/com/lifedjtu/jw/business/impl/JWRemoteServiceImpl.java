
package com.lifedjtu.jw.business.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;


import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.pojos.Course;
import com.lifedjtu.jw.pojos.CourseTakenItem;
import com.lifedjtu.jw.pojos.Exam;
import com.lifedjtu.jw.pojos.dto.BuildingDto;
import com.lifedjtu.jw.pojos.dto.RoomDayInfo;
import com.lifedjtu.jw.pojos.dto.RoomDto;
import com.lifedjtu.jw.pojos.dto.RoomInfoDto;
import com.lifedjtu.jw.pojos.dto.RoomWeekInfo;
import com.lifedjtu.jw.pojos.Score;
import com.lifedjtu.jw.pojos.dto.StudentRegistry;

import com.lifedjtu.jw.util.URLFetcher;
import com.lifedjtu.jw.util.MapMaker;
import com.lifedjtu.jw.util.FetchResponse;
import com.lifedjtu.jw.util.extractor.DomElement;
import com.lifedjtu.jw.util.extractor.Extractor;

@Component("jwRemoteService")
public class JWRemoteServiceImpl implements JWRemoteService {

	private final String loginURL = "jw.djtu.edu.cn/academic/j_acegi_security_check";
	private final String studentMessageURL = "http://jw.djtu.edu.cn/academic/showHeader.do";
	private final String studentRegistryURL = "http://jw.djtu.edu.cn/academic/student/studentinfo/studentInfoModifyIndex.do?frombase=0&wantTag=0";
	private final String modifyPasswordURL = "http://jw.djtu.edu.cn/academic/sysmgr/modifypasswd_user.jsdo";
	private final String queryBuildingOnDateURL = "http://jw.djtu.edu.cn/academic/teacher/teachresource/roomschedule_week.jsdo";
	private final String queryRoomURL = "http://jw.djtu.edu.cn/academic/teacher/teachresource/roomschedule.jsdo";
	private final String queryRemoteCourseTableURL = "http://jw.djtu.edu.cn/academic/student/currcourse/currcourse.jsdo";
	private final String queryRemoteExamsURL = "http://jw.djtu.edu.cn/academic/student/exam/index.jsdo";
	private final String queryRemoteScoresURL = "http://jw.djtu.edu.cn/academic/manager/score/studentOwnScore.do";
	
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
		if(!Extractor.$("table[class=error_top]",fetchResponse.getResponseBody()).isEmpty()){
			return null;
		}
		List<DomElement> title = Extractor.$("table[class=form] th",fetchResponse.getResponseBody());
		List<DomElement> value = Extractor.$("table[class=form] td",fetchResponse.getResponseBody());
		List<String> titles = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		for(int i=0;i<title.size();i++){
			titles.add(title.get(i).getText().trim());
		}
		for(int i=0;i<value.size();i++){
			values.add(value.get(i).getText().trim());
		}
		values.remove(2);
		StudentRegistry studentRegistry = new StudentRegistry(titles, values);
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
		FetchResponse fetchResponse = URLFetcher.fetchURLByPost(queryBuildingOnDateURL, sessionId, MapMaker.instance("aid", new Integer(aid).toString())
				.param("buildingid", new Integer(buildingId).toString())
				.param("whichweek", new Integer(week).toString())
				.param("week", new Integer(weekday).toString())
				.toMap());
		if(!Extractor.$("table[class=error_top]",fetchResponse.getResponseBody()).isEmpty()){
			return null;
		}
		//System.out.println(fetchResponse.getResponseBody());
		List<DomElement> list = Extractor.$("tr[class=infolist_common]",fetchResponse.getResponseBody());
		//System.out.println(list.size());
		List<RoomInfoDto> roomInfoDtos = new ArrayList<RoomInfoDto>();
		for(int i=0;i<list.size();i++){
			List<DomElement> info = list.get(i).children("td");
			List<DomElement> occupyInfo = info.get(6).find("tr").get(1).children("td");
			List<String> occupyInfos = new ArrayList<String>();
			for(int j=0;j<occupyInfo.size();j++){
				//System.out.println(occupyInfo.get(j).getText().trim());
				occupyInfos.add(occupyInfo.get(j).getText().trim());
			}
			RoomInfoDto roomInfoDto = new RoomInfoDto(info.get(0).getText().trim()
					, info.get(1).getText().trim()
					, info.get(2).getText().trim()
					, info.get(3).getText().trim()
					, info.get(4).getText().trim()
					, info.get(5).getText().trim()
					, occupyInfos);
			roomInfoDtos.add(roomInfoDto);
		}
		BuildingDto buildingDto = new BuildingDto(roomInfoDtos);
		return buildingDto;
	}

	@Override
	public RoomDto queryRoom(String sessionId, int aid, int buildingId,
			int roomId) {
		// TODO Auto-generated method stub
		FetchResponse fetchResponse = URLFetcher.fetchURLByPost(queryRoomURL, sessionId, MapMaker.instance("aid", new Integer(aid).toString())
																					.param("buildingid", new Integer(buildingId).toString())
																					.param("room", new Integer(roomId).toString())
																					.toMap());
		if(!Extractor.$("table[class=error_top]",fetchResponse.getResponseBody()).isEmpty()){
			return null;
		}
		List<DomElement> table = Extractor.$("table[class=infolists_tab]",fetchResponse.getResponseBody());
		//System.out.println(fetchResponse.getResponseBody());
		List<RoomWeekInfo> weekInfos = new ArrayList<RoomWeekInfo>();
		for(int i=0;i<table.size();i++){
			List<RoomDayInfo> dayInfos = new ArrayList<RoomDayInfo>();
			List<List<String>> week = new ArrayList<List<String>>();
			List<DomElement> list = table.get(i).children("tr");
			for(int j=1;j<list.size();j++){
				List<String> classes = new ArrayList<String>();
				List<DomElement> node = list.get(j).children("td");
				for(int k=1;k<node.size();k++){
					classes.add(node.get(k).getText().trim());
				}
				week.add(classes);
			}
			for(int p=0;p<7;p++){
				List<String> infos = new ArrayList<String>();
				for(int q=0;q<10;q++){
					
					infos.add(week.get(q).get(p));
				}
				RoomDayInfo dayInfo = new RoomDayInfo(infos);
				dayInfos.add(dayInfo);
			}
			RoomWeekInfo weekInfo = new RoomWeekInfo(dayInfos);
			weekInfos.add(weekInfo);
		}
		RoomDto roomDto = new RoomDto(weekInfos); 
		
		return roomDto;
	}

	@Override
	public List<Course> queryRemoteCourseTable(String sessionId) {
		// TODO Auto-generated method stub
		FetchResponse fetchResponse = URLFetcher.fetchURLByGet(queryRemoteCourseTableURL, sessionId);
		if(!Extractor.$("table[class=error_top]",fetchResponse.getResponseBody()).isEmpty()){
			return null;
		}
		//System.out.println(fetchResponse.getResponseBody());
		List<DomElement> list = Extractor.$("table[class=infolist_tab]:first > tr",fetchResponse.getResponseBody());
		List<Course> courses = new ArrayList<Course>();
		for(int i=1;i<list.size()-3;i++){
			List<DomElement> courseTemp = list.get(i).children("td");
			List<DomElement> courseTakenItemTemp = courseTemp.get(9).find("tr");
			List<CourseTakenItem> courseTakenItems = new ArrayList<CourseTakenItem>();
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
		if(!Extractor.$("table[class=error_top]",fetchResponse.getResponseBody()).isEmpty()){
			return null;
		}
		List<DomElement> list = Extractor.$("table[class=infolist_tab] > tr",fetchResponse.getResponseBody());
		List<Exam> exams = new ArrayList<Exam>();
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
		if(!Extractor.$("table[class=error_top]",fetchResponse.getResponseBody()).isEmpty()){
			return null;
		}
		List<DomElement> list = Extractor.$("table[class=datalist] > tr",fetchResponse.getResponseBody());
		List<Score> scores = new ArrayList<Score>();
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




