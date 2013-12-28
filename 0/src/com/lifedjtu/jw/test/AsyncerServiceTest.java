package com.lifedjtu.jw.test;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.business.task.JWUpdateCacheAsyncer;
import com.lifedjtu.jw.pojos.dto.CourseDto;
import com.lifedjtu.jw.pojos.dto.DjtuDate;

public class AsyncerServiceTest {
	@Test
	public void testRemoteService(){
		char sep = File.separatorChar;
		ApplicationContext ctx=new FileSystemXmlApplicationContext("WebRoot"+sep+"WEB-INF"+sep+"applicationContext.xml");

		JWUpdateCacheAsyncer jwUpdateCacheAsyncer = (JWUpdateCacheAsyncer)ctx.getBean("jwUpdateCacheAsyncer");
		JWRemoteService remoteService = (JWRemoteService)ctx.getBean("jwRemoteService");
		String sessionId = remoteService.signinRemote("1018110323", "lh911119");
		DjtuDate djtuDate = remoteService.queryDjtuDate(sessionId);
		List<CourseDto> courseDtos = remoteService.queryRemoteCourseTable(sessionId);
		long start = System.currentTimeMillis();
		jwUpdateCacheAsyncer.updateCourseInfo(courseDtos, djtuDate);
		long end = System.currentTimeMillis();
		System.err.println((end-start)/(double)1000+"s");
//		for(CourseDto course : courses){
//			System.out.println(course.toJSON());
//		}
		//System.out.println(roomDto);
	}
}
