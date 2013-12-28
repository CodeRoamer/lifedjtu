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
	 * 核心业务方法，主要与数据库交互（也有可能与远程交互）
	 */
	public boolean isUserExist(String studentId);
	public User addUser(User user);
	public User signinLocal(String studentId, String password);
	public boolean changeLocalPassword(String studentId, String dynamicPass, String originPass, String newPass, String newPassAgain);
	public List<Room> queryFreeRooms(String studentId, String dynamicPass, double longitude, double latitude);
	public List<CourseInstance> queryLocalCourseTabel(String studentId,String dynamicPass);
	public List<ExamInstance> queryLocalExams(String studentId, String dynamicPass);
	
	/**
	 * 这些方法负责在用户注册时，初始化用户数据，搜取用户信息
	 * 对于一个用户来说，我们需要在其注册时，一步一步引导用户，并从其身上获取必要的数据，更好地为他和为其他用户服务
	 * 这些数据包括：
	 * 1. 原生的用户个人信息，需要从获取学籍处获得
	 * 2. 原生的用户课程信息，需要从个人课表处获得，数据会被加载，加载后分别刷新Course表和CourseInstance表
	 * 3. 原生的用户考试信息，需要从个人考试处获得，数据会被加载，加载后分别刷新Exam表和ExamInstance表
	 */
	public User fetchUserDetailInfo(String studentId, String dynamicPass); //加载用户详细信息，更新数据库，返回User实体
	public boolean fetchUserCourseInfo(String studentId, String dynamicPass); //获取用户课程信息，刷新Course表格，填充属于此用户的CourseInstance
	public boolean fetchUserExamInfo(String studentId, String dynamicPass); //获取用户考试信息，刷新Exam表格，填充属于此用户的ExamInstance
	
	
	/**
	 * 以下方法的返回结果，或从remoteService获取的中间变量不存数据库
	 */
	public StudentRegistry fetchStudentRegistry(String studentId, String dynamicPass);
	public BuildingDto queryBuildingOnDate(String studentId,String dynamicPass, int aid, int buildingId, int week, int weekday);
	public RoomDto queryRoom(String studentId,String dynamicPass, int aid, int buildingId, int roomId);
	public List<ScoreDto> queryLocalScores(String studentId, String dynamicPass);
	
	
	/**
	 * 以下为一些工具类服务方法,仅限内部调用
	 */
	public boolean checkPrivateKey(String studentId, String dynamicPass);
	
}
