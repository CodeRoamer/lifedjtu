package com.lifedjtu.jw.business;

import java.util.List;

import com.lifedjtu.jw.business.support.LocalResult;
import com.lifedjtu.jw.pojos.Area;
import com.lifedjtu.jw.pojos.Building;
import com.lifedjtu.jw.pojos.CourseInstance;
import com.lifedjtu.jw.pojos.FriendPending;
import com.lifedjtu.jw.pojos.RoomTakenItem;
import com.lifedjtu.jw.pojos.User;
import com.lifedjtu.jw.pojos.dto.BuildingDto;
import com.lifedjtu.jw.pojos.dto.ExamDto;
import com.lifedjtu.jw.pojos.dto.GroupDto;
import com.lifedjtu.jw.pojos.dto.RoomDto;
import com.lifedjtu.jw.pojos.dto.ScoreDto;
import com.lifedjtu.jw.pojos.dto.StudentRegistry;
import com.lifedjtu.jw.util.LifeDjtuEnum.FriendRequestStatus;

public interface JWLocalService {
	/**
	 * 用户身份验证接口，提供给Interceptor
	 */
	public LocalResult<String> prepareUser(String studentId, String dynamicPass);
	public LocalResult<Boolean> prepareUserLocal(String studentId, String dynamicPass);
	
	/**
	 * 初始化方法，主要与数据库交互（也有可能与远程交互）
	 */
	public boolean isUserExistAndReady(String studentId);
	public User signupLocal(String studentId, String passowrd);
	public User signinLocal(String studentId, String password);
	/**
	 * 核心业务方法，主要与数据库交互（也有可能与远程交互）
	 */
	public LocalResult<Boolean> changeLocalPassword(String studentId, String sessionId, String originPass, String newPass, String newPassAgain);
	public LocalResult<List<RoomTakenItem>> queryFreeRooms(double longitude, double latitude); //无需走远程，直接与数据库交互即可
	public LocalResult<List<CourseInstance>> queryLocalCourseTabel(String studentId, String sessionId); //忽略注释.....仅在用户注册时，登录时，和请求刷新时调用，这些数据需存储在app中
	public LocalResult<List<ExamDto>> queryLocalExams(String studentId, String sessionId); //忽略注释.....仅在用户注册时，登录时，和请求刷新时调用，这些数据需存储在app中。推送利器！同种课程，一旦有一人查询到考试存在，全部选修此课程的同学都会被推送考试通知
	
	public LocalResult<List<Area>> queryLocalAreas();
	public LocalResult<List<Building>> queryLocalBuildings(String areaId);
	public LocalResult<List<RoomTakenItem>> queryFreeRooms(String buildingId);
	
	public LocalResult<Boolean> safeUpdateRoomTakenInfo();
	
	public LocalResult<List<User>> getSameCourseUsers(String courseId, int pageNum, int pageSize); //指的是抓取此课中，全部上此门课程的人
	public LocalResult<List<User>> getSameClassUsers(String courseInstanceId, int pageNum, int pageSize); //指的是抓取此课中，同班上此门课程的人
	//deprecated!!!!  no use for list same grades
	//public LocalResult<List<User>> getSameGradeUsers(String studentId,String remoteId, int pageNum, int pageSize); //指的是抓取此门课中，同年级的上此门课的人
	public LocalResult<Integer> getSameCourseUserNum(String courseId);//指的是抓取此课中，全部上此门课程的人
	public LocalResult<Integer> getSameClassUserNum(String courseInstanceId); //指的是抓取此课中，同班上此门课程的人
	//deprecated!!!!  no use for list same grades
	//public LocalResult<Integer> getSameGradeUserNum(String studentId,String remoteId); //指的是抓取此门课中，同年级的上此门课的人

	public LocalResult<CourseInstance> getCourseInstance(String courseRemoteId);//根据remoteId，获取此门课程的全部信息
	
	public LocalResult<Boolean> giveGoodEvalToCourse(String studentId, String courseInstanceId);
	public LocalResult<Boolean> giveBadEvalToCourse(String studentId, String courseInstanceId);
	
	//全部为studentId，这样方便操作，好友功能
	public LocalResult<Boolean> addFriend(String studentId, String friendStudentId, String content);
	public LocalResult<List<FriendPending>> getFriendPendingList(String studentId);
	public LocalResult<Boolean> answerFriendRequest(String studentId, String requestSourceStudentId, FriendRequestStatus status);
	public LocalResult<List<FriendPending>> viewFriendRequestResultList(String studentId);
	
	public LocalResult<List<User>> getFriendList(String studentId);
	public LocalResult<Boolean> removeFriend(String studentId, String friendId, boolean removeBoth);
	
	//后续的群组功能，获取用户全部群组及成员简易信息
	public LocalResult<List<GroupDto>> getGroupsForUser(String studentId);
	/**
	 * 这些方法负责在用户注册时，初始化用户数据，搜取用户信息
	 * 对于一个用户来说，我们需要在其注册时，一步一步引导用户，并从其身上获取必要的数据，更好地为他和为其他用户服务
	 * 这些数据包括：
	 * 1. 原生的用户个人信息，需要从获取学籍处获得
	 * 2. 原生的用户课程信息，需要从个人课表处获得，数据会被加载，加载后分别刷新Course表和CourseInstance表
	 * 3. 原生的用户考试信息，需要从个人考试处获得，数据会被加载，加载后分别刷新Exam表和ExamInstance表
	 */
	public LocalResult<User> fetchUserDetailInfo(String studentId, String sessionId); //加载用户详细信息，更新数据库，返回User实体
	
	/**
	 * 只为了抓取用户信息，不访问远程
	 */
	public LocalResult<User> getUserDetailInfo(String studentId);
	
	/**
	 * 以下方法的返回结果，或从remoteService获取的中间变量不存数据库
	 */
	public LocalResult<StudentRegistry> fetchStudentRegistry(String sessionId);
	public LocalResult<BuildingDto> queryBuildingOnDate(String sessionId, int aid, int buildingId, int week, int weekday);
	public LocalResult<RoomDto> queryRoom(String sessionId, int aid, int buildingId, int roomId);
	public LocalResult<List<ScoreDto>> queryLocalScores(String studentId, String sessionId, int schoolYear, int term); //需要与数据库交互，获取关联用户，忽略注释....推送利器！同种课程实例！注意是实例，一旦有一人查询到分数已出，全部在读于这一课程实例（同一老师授课）的同学都会收到出分推送通知！
	
	public LocalResult<Double> queryAverageMarks(String sessionId);
	public LocalResult<Double> queryAverageMarksByTerm(String sessionId, int schoolYear, int term);
	
	public LocalResult<Double> queryGPAMarks(String sessionId);
	public LocalResult<Double> queryGPAMarksByTerm(String sessionId, int schoolYear, int term);
	
	
	/**
	 * 以下为一些工具类服务方法,仅限内部调用
	 */
	public String getCourseIdByAlias(String courseAlias);
	public String getCourseInstanceIdByRemoteId(String remoteId);
	
	public String getGroupIdByCourseAlias(String courseAlias);
	public String getGroupIdByCourseRemoteId(String remoteId);
	
	public LocalResult<List<User>> getGroupUserList(String groupId, int pageNum, int pageSize);
	public LocalResult<Integer> getGroupUserNum(String groupId);
}
