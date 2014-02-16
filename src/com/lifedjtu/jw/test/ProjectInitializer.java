package com.lifedjtu.jw.test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.business.task.JWUpdateCacheAsyncer;
import com.lifedjtu.jw.business.task.JWUpdateCacheScheduler;
import com.lifedjtu.jw.dao.impl.UserDao;
import com.lifedjtu.jw.dao.support.UUIDGenerator;
import com.lifedjtu.jw.pojos.User;
import com.lifedjtu.jw.pojos.dto.CourseDto;
import com.lifedjtu.jw.pojos.dto.DjtuDate;
import com.lifedjtu.jw.pojos.dto.ExamDto;

public class ProjectInitializer {
	public static void main(String[] args){
		char sep = File.separatorChar;
		ApplicationContext ctx=new FileSystemXmlApplicationContext("WebContent"+sep+"WEB-INF"+sep+"applicationContext.xml");

		JWUpdateCacheScheduler scheduler = (JWUpdateCacheScheduler)ctx.getBean("jwUpdateCacheScheduler");
		JWRemoteService remoteService = (JWRemoteService)ctx.getBean("jwRemoteService");
		JWUpdateCacheAsyncer jwUpdateCacheAsyncer = (JWUpdateCacheAsyncer)ctx.getBean("jwUpdateCacheAsyncer");
		UserDao userDao = (UserDao)ctx.getBean("userDao");

		String sessionId = remoteService.randomSessionId();
		long start = System.currentTimeMillis();
		scheduler.updateAreaInfo(sessionId);
		scheduler.updateBuildingInfo(sessionId);
		scheduler.updateRoomInfo(sessionId);
		scheduler.updateRoomTakenItem(sessionId);
		long end = System.currentTimeMillis();
		System.err.println((end-start)/(double)1000+"s");
		
		
		System.err.println("开始添加用户，并测试课程与考试添加功能...");
		List<User> users = getUsers();
		userDao.addMulti(users);
		DjtuDate djtuDate = remoteService.queryDjtuDate(sessionId);
		start = System.currentTimeMillis();
		for(User user : users){
			System.out.println("开始添加用户 "+user.getUsername()+" 的课程与考试状况...");
			String innerSession = remoteService.signinRemote(user.getStudentId(), user.getPassword());
			List<CourseDto> courseDtos = remoteService.queryRemoteCourseTable(innerSession);
			List<ExamDto> examDtos = remoteService.queryRemoteExams(innerSession);
			jwUpdateCacheAsyncer.updateCourseInfo(user.getId(), courseDtos, djtuDate);
			jwUpdateCacheAsyncer.updateExamInfo(user.getId(), examDtos);
		}
		end = System.currentTimeMillis();
		System.err.println((end-start)/(double)1000+"s");

	}
	
	public static List<User> getUsers(){
		User user = new User();
		user.setId(UUIDGenerator.randomUUID());
		user.setStudentId("1018110323");
		user.setPassword("lh911119");
		user.setUsername("李赫");
		user.setAcademy("外国语学院");
		
		User user2 = new User();
		user2.setId(UUIDGenerator.randomUUID());
		user2.setStudentId("1018110207");
		user2.setPassword("1991080213");
		user2.setUsername("李辛洋");
		user2.setAcademy("外国语学院");
		
//		User user3 = new User();
//		user3.setId(UUIDGenerator.randomUUID());
//		user3.setStudentId("1018110328");
//		user3.setPassword("1018110328");
//		user3.setUsername("杨东");
//		user3.setAcademy("外国语学院");	
		
		return Arrays.asList(user,user2/*, user3*/);
	}
}
