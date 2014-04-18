package com.lifedjtu.jw.test;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.business.task.JWUpdateCacheAsyncer;
import com.lifedjtu.jw.dao.impl.UserDao;
import com.lifedjtu.jw.pojos.User;
import com.lifedjtu.jw.pojos.dto.CourseDto;
import com.lifedjtu.jw.pojos.dto.DjtuDate;
import com.lifedjtu.jw.pojos.dto.ExamDto;

public class AsyncerServiceTest {
	@Test
	public void testRemoteService(){
		char sep = File.separatorChar;
		ApplicationContext ctx=new FileSystemXmlApplicationContext("WebRoot"+sep+"WEB-INF"+sep+"applicationContext.xml");

		JWUpdateCacheAsyncer jwUpdateCacheAsyncer = (JWUpdateCacheAsyncer)ctx.getBean("jwUpdateCacheAsyncer");
		UserDao userDao = (UserDao)ctx.getBean("userDao");
		User user = userDao.findOneById("6929647b-f6c1-4889-bfed-e953ad19ff2b");
		JWRemoteService remoteService = (JWRemoteService)ctx.getBean("jwRemoteService");
		String sessionId = remoteService.signinRemote(user.getStudentId(), user.getPassword());
		DjtuDate djtuDate = remoteService.queryDjtuDate();
		List<CourseDto> courseDtos = remoteService.queryRemoteCourseTable(sessionId);
		List<ExamDto> examDtos = remoteService.queryRemoteExams(sessionId);
//		User user = new User();
//		String userId = UUIDGenerator.randomUUID();
//		user.setId(userId);
//		user.setStudentId("1018110323");
//		user.setPassword("lh911119");
//		user.setUsername("李赫");
//		user.setAcademy("外国语学院");
//		user.setCurSessionId(sessionId);
//		userDao.add(user);
		
		long start = System.currentTimeMillis();
		jwUpdateCacheAsyncer.updateCourseInfo(user.getId(), courseDtos, djtuDate);
		jwUpdateCacheAsyncer.updateExamInfo(user.getId(), examDtos, djtuDate);
		long end = System.currentTimeMillis();
		System.err.println((end-start)/(double)1000+"s");
//		for(CourseDto course : courses){
//			System.out.println(course.toJSON());
//		}
		//System.out.println(roomDto);
	}
}
