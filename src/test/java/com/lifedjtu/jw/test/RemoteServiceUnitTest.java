package com.lifedjtu.jw.test;

import java.io.File;

import com.lifedjtu.jw.pojos.SystemNotice;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.business.impl.JWRemoteServiceImpl;
import com.lifedjtu.jw.pojos.dto.CourseRecordDto;

public class RemoteServiceUnitTest {
	/*
	@Test
	public void testRemoteService(){
		char sep = File.separatorChar;
		ApplicationContext ctx=new FileSystemXmlApplicationContext("WebContent"+sep+"WEB-INF"+sep+"applicationContext.xml");

		JWRemoteService remoteService = (JWRemoteServiceImpl)ctx.getBean("jwRemoteService");
		String sessionId = remoteService.signinRemote("1018110323", "lh911119");
		long start = System.currentTimeMillis();
		//List<CourseDto> courses = remoteService.queryRemoteCourseTable(sessionId);//OK!!!
		//StudentRegistry studentRegistry = remoteService.fetchStudentRegistry(sessionId);//OK!!!
		//boolean result = remoteService.changeRemotePassword(sessionId, "lh911119", "1234", "1234");//OK!!!
		//BuildingDto buildingDto = remoteService.queryBuildingOnDate(sessionId, 78, 1061, 17, 6);//OK!!!
		//RoomDto roomDto = remoteService.queryRoom(sessionId, 78, 1061, 1065);//OK!!!
		List<ExamDto> exams = remoteService.queryRemoteExams(sessionId); //OK!!!
		//List<Score> scores = remoteService.queryRemoteScores(sessionId);//OK!!!
		long end = System.currentTimeMillis();
		System.err.println((end-start)/(double)1000+"s");
		//System.err.println(result);
		
		for(ExamDto examDto : exams){
			System.err.println(examDto.toJSON());
		}
		
//		for(CourseDto course : courses){
//			System.out.println(course.toJSON());
//			for(CourseTakenItem courseTakenItem : course.getCourseTakenItems()){
//				System.err.println(InfoProcessHub.transferCourseTakenItem(courseTakenItem));
//			}
//		}
		//System.out.println(roomDto);
	}
	*/
	
	@Test
	public void testRemoteService(){
		char sep = File.separatorChar;
        String path = new StringBuilder().append("src").append(sep).append("main").append(sep).append("webapp").append(sep).append("WEB-INF").append(sep).append("applicationContext.xml").toString();
		ApplicationContext ctx=new FileSystemXmlApplicationContext(path);

		final JWRemoteService remoteService = (JWRemoteServiceImpl)ctx.getBean("jwRemoteService");

        new Thread(){
            @Override
            public void run(){
                long current = System.currentTimeMillis();
                String sessionId = remoteService.signinRemote("1018110323", "lh911119");
                System.out.println((System.currentTimeMillis()-current)/1000.0+"s");

                if(sessionId!=null){
                    System.out.println(remoteService.showHeader(sessionId));
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run(){
                long current = System.currentTimeMillis();
                String sessionId = remoteService.signinRemote("1018110207", "1991080213");
                System.out.println((System.currentTimeMillis()-current)/1000.0+"s");

                if(sessionId!=null){
                    System.out.println(remoteService.showHeader(sessionId));
                }
            }
        }.start();

        try{
            Thread.sleep(100000);
        }catch (Exception exception){
            exception.printStackTrace();
        }

	}
	
	
	/*
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
	*/
}
