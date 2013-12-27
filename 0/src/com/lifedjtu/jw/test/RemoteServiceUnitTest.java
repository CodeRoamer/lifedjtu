package com.lifedjtu.jw.test;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.business.impl.JWRemoteServiceImpl;
import com.lifedjtu.jw.pojos.Area;
import com.lifedjtu.jw.pojos.Building;
import com.lifedjtu.jw.pojos.Room;
import com.lifedjtu.jw.pojos.dto.CourseDto;

public class RemoteServiceUnitTest {
	@Test
	public void testRemoteService(){
		char sep = File.separatorChar;
		ApplicationContext ctx=new FileSystemXmlApplicationContext("WebRoot"+sep+"WEB-INF"+sep+"applicationContext.xml");

		JWRemoteService remoteService = (JWRemoteServiceImpl)ctx.getBean("jwRemoteService");
		String sessionId = remoteService.signinRemote("1018110323", "lh911119");
		long start = System.currentTimeMillis();
		List<CourseDto> courses = remoteService.queryRemoteCourseTable(sessionId);//OK!!!
		//StudentRegistry studentRegistry = remoteService.fetchStudentRegistry(sessionId);//OK!!!
		//boolean result = remoteService.changeRemotePassword(sessionId, "1234", "lh911119", "lh911119");//OK!!!
		//BuildingDto buildingDto = remoteService.queryBuildingOnDate(sessionId, 78, 1061, 8, 4);//OK!!!
		//RoomDto roomDto = remoteService.queryRoom(sessionId, 78, 1061, 1065);//OK!!!
		//List<Exam> exams = remoteService.queryRemoteExams(sessionId); //OK!!!
		//List<Score> scores = remoteService.queryRemoteScores(sessionId);//OK!!!
		long end = System.currentTimeMillis();
		System.err.println((end-start)/(double)1000+"s");
		for(CourseDto course : courses){
			System.out.println(course.toJSON());
		}
		//System.out.println(roomDto);
	}
	
	@Test
	public void testQueryRemoteAreas(){
		JWRemoteService jwRemoteService = new JWRemoteServiceImpl();
		String sessionId = jwRemoteService.signinRemote("1018110201", "357159");
		List<Area> list = jwRemoteService.queryRemoteAreas(sessionId);
		for(Area area : list){
			System.out.println(area.toJSON());
		}
	}
	
	@Test
	public void testQueryRemoteBuildings(){
		JWRemoteService jwRemoteService = new JWRemoteServiceImpl();
		String sessionId = jwRemoteService.signinRemote("1018110201", "357159");
		List<Building> list = jwRemoteService.queryRemoteBuildings(sessionId, "1");
		for(Building building : list){
			System.out.println(building.toJSON());
		}
	}
	
	@Test
	public void testQueryRemoteRooms(){
		JWRemoteService jwRemoteService = new JWRemoteServiceImpl();
		String sessionId = jwRemoteService.signinRemote("1018110201", "357159");
		List<Room> list = jwRemoteService.queryRemoteRooms(sessionId, "80");
		for(Room room : list){
			System.out.println(room.toJSON());
		}
	}
}
