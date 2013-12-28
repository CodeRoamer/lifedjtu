package com.lifedjtu.jw.business;

import java.util.List;

import com.lifedjtu.jw.pojos.CourseInstance;
import com.lifedjtu.jw.pojos.ExamInstance;
import com.lifedjtu.jw.pojos.Room;
import com.lifedjtu.jw.pojos.User;
import com.lifedjtu.jw.pojos.dto.BuildingDto;
import com.lifedjtu.jw.pojos.dto.RoomDto;
import com.lifedjtu.jw.pojos.dto.ScoreDto;
import com.lifedjtu.jw.pojos.dto.StudentRegistry;

public interface JWLocalService {
	/**
	 * 核心业务方法，与数据库交互
	 */
	public boolean isUserExist(String studentId);
	public User addUser(User user);
	public User signinLocal(String studentId, String password);
	public boolean checkPrivateKey(String studentId, String dynamicPass);
	public boolean changeLocalPassword(String studentId, String dynamicPass, String originPass, String newPass, String newPassAgain);
	public List<Room> queryFreeRooms(String studentId, String dynamicPass, double longitude, double latitude);
	public List<CourseInstance> queryLocalCourseTabel(String studentId,String dynamicPass);
	public List<ExamInstance> queryLocalExams(String studentId, String dynamicPass);
	
	/**
	 * 以下方法的返回结果，或从remoteService获取的中间变量不存数据库
	 */
	public StudentRegistry fetchStudentRegistry(String studentId, String dynamicPass);
	public BuildingDto queryBuildingOnDate(String studentId,String dynamicPass, int aid, int buildingId, int week, int weekday);
	public RoomDto queryRoom(String studentId,String dynamicPass, int aid, int buildingId, int roomId);
	public List<ScoreDto> queryLocalScores(String studentId, String dynamicPass);
}
