package com.lifedjtu.jw.business;


import java.util.List;

import com.lifedjtu.jw.pojos.*;
import com.lifedjtu.jw.pojos.dto.BuildingDto;
import com.lifedjtu.jw.pojos.dto.RoomDto;
import com.lifedjtu.jw.pojos.Score;
import com.lifedjtu.jw.pojos.dto.StudentRegistry;

public interface JWRemoteService {
	public String signinRemote(String studentId, String password);
	
	public StudentRegistry fetchStudentRegistry(String sessionId);
	
	public boolean changeRemotePassword(String sessionId, String originPass, String newPass, String newPassAgain);
	
	public BuildingDto queryBuildingOnDate(String sessionId, int aid, int buildingId, int week, int weekday);
	
	public RoomDto queryRoom(String sessionId, int aid, int buildingId, int roomId);
	
	public List<Course> queryRemoteCourseTable(String sessionId);
	
	public List<Exam> queryRemoteExams(String sessionId);
	
	public List<Score> queryRemoteScores(String sessionId);
}
