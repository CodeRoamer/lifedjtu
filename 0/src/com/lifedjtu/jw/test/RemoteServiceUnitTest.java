package com.lifedjtu.jw.test;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.business.impl.JWRemoteServiceImpl;
import com.lifedjtu.jw.pojos.Course;

public class RemoteServiceUnitTest {
	@Test
	public void testRemoteService(){
		char sep = File.separatorChar;
		ApplicationContext ctx=new FileSystemXmlApplicationContext("WebRoot"+sep+"WEB-INF"+sep+"applicationContext.xml");

		JWRemoteService remoteService = (JWRemoteServiceImpl)ctx.getBean("jwRemoteService");
		String sessionId = remoteService.signinRemote("1018110323", "lh911119");
		List<Course> courses = remoteService.queryRemoteCourseTable(sessionId);
		for(Course course : courses){
			System.out.println(course.toJSON());
		}
	}
}
