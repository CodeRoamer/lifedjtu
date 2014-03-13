package com.lifedjtu.jw.business.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lifedjtu.jw.business.JWLocalService;
import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.business.support.LocalResult;
import com.lifedjtu.jw.business.task.JWUpdateCacheAsyncer;
import com.lifedjtu.jw.business.task.JWUpdateCacheScheduler;
import com.lifedjtu.jw.config.LifeDjtuConfig;
import com.lifedjtu.jw.dao.CriteriaWrapper;
import com.lifedjtu.jw.dao.Pageable;
import com.lifedjtu.jw.dao.ProjectionWrapper;
import com.lifedjtu.jw.dao.Sortable;
import com.lifedjtu.jw.dao.Tuple;
import com.lifedjtu.jw.dao.UpdateWrapper;
import com.lifedjtu.jw.dao.impl.AreaDao;
import com.lifedjtu.jw.dao.impl.BuildingDao;
import com.lifedjtu.jw.dao.impl.CourseDao;
import com.lifedjtu.jw.dao.impl.CourseInstanceDao;
import com.lifedjtu.jw.dao.impl.FriendDao;
import com.lifedjtu.jw.dao.impl.FriendPendingDao;
import com.lifedjtu.jw.dao.impl.IMGroupDao;
import com.lifedjtu.jw.dao.impl.IMGroupUserDao;
import com.lifedjtu.jw.dao.impl.RoomTakenItemDao;
import com.lifedjtu.jw.dao.impl.UserCourseDao;
import com.lifedjtu.jw.dao.impl.UserDao;
import com.lifedjtu.jw.dao.support.UUIDGenerator;
import com.lifedjtu.jw.pojos.Area;
import com.lifedjtu.jw.pojos.Building;
import com.lifedjtu.jw.pojos.CourseInstance;
import com.lifedjtu.jw.pojos.Friend;
import com.lifedjtu.jw.pojos.FriendPending;
import com.lifedjtu.jw.pojos.IMGroupUser;
import com.lifedjtu.jw.pojos.RoomTakenItem;
import com.lifedjtu.jw.pojos.User;
import com.lifedjtu.jw.pojos.dto.BuildingDto;
import com.lifedjtu.jw.pojos.dto.CourseDto;
import com.lifedjtu.jw.pojos.dto.DjtuDate;
import com.lifedjtu.jw.pojos.dto.ExamDto;
import com.lifedjtu.jw.pojos.dto.RoomDto;
import com.lifedjtu.jw.pojos.dto.ScoreDto;
import com.lifedjtu.jw.pojos.dto.StudentRegistry;
import com.lifedjtu.jw.util.Crypto;
import com.lifedjtu.jw.util.LifeDjtuEnum.ExamStatus;
import com.lifedjtu.jw.util.LifeDjtuEnum.FriendRequestStatus;
import com.lifedjtu.jw.util.LifeDjtuEnum.ResultState;
import com.lifedjtu.jw.util.LifeDjtuUtil;
import com.lifedjtu.jw.util.MapMaker;

@Component("jwLocalService")
@Transactional
public class JWLocalServiceImpl implements JWLocalService{
	@Autowired
	private JWRemoteService jwRemoteService;
	@Autowired
	private JWUpdateCacheAsyncer jwUpdateCacheAsyncer;
	@Autowired
	private UserDao userDao;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private RoomTakenItemDao roomTakenItemDao;
	@Autowired
	private UserCourseDao userCourseDao;
	@Autowired
	private JWUpdateCacheScheduler jwUpdateCacheScheduler;
	@Autowired
	private CourseInstanceDao courseInstanceDao;
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private IMGroupUserDao imGroupUserDao;
	@Autowired
	private IMGroupDao imGroupDao;
	@Autowired
	private FriendDao friendDao;
	@Autowired
	private FriendPendingDao friendPendingDao;
	
	public FriendDao getFriendDao() {
		return friendDao;
	}

	public void setFriendDao(FriendDao friendDao) {
		this.friendDao = friendDao;
	}

	public FriendPendingDao getFriendPendingDao() {
		return friendPendingDao;
	}

	public void setFriendPendingDao(FriendPendingDao friendPendingDao) {
		this.friendPendingDao = friendPendingDao;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public CourseInstanceDao getCourseInstanceDao() {
		return courseInstanceDao;
	}

	public void setCourseInstanceDao(CourseInstanceDao courseInstanceDao) {
		this.courseInstanceDao = courseInstanceDao;
	}

	public JWUpdateCacheScheduler getJwUpdateCacheScheduler() {
		return jwUpdateCacheScheduler;
	}

	public void setJwUpdateCacheScheduler(
			JWUpdateCacheScheduler jwUpdateCacheScheduler) {
		this.jwUpdateCacheScheduler = jwUpdateCacheScheduler;
	}
	
	public JWUpdateCacheAsyncer getJwUpdateCacheAsyncer() {
		return jwUpdateCacheAsyncer;
	}

	public void setJwUpdateCacheAsyncer(JWUpdateCacheAsyncer jwUpdateCacheAsyncer) {
		this.jwUpdateCacheAsyncer = jwUpdateCacheAsyncer;
	}

	public UserCourseDao getUserCourseDao() {
		return userCourseDao;
	}

	public void setUserCourseDao(UserCourseDao userCourseDao) {
		this.userCourseDao = userCourseDao;
	}

	public IMGroupUserDao getImGroupUserDao() {
		return imGroupUserDao;
	}

	public void setImGroupUserDao(IMGroupUserDao imGroupUserDao) {
		this.imGroupUserDao = imGroupUserDao;
	}

	public IMGroupDao getImGroupDao() {
		return imGroupDao;
	}

	public void setImGroupDao(IMGroupDao imGroupDao) {
		this.imGroupDao = imGroupDao;
	}

	public AreaDao getAreaDao() {
		return areaDao;
	}

	public void setAreaDao(AreaDao areaDao) {
		this.areaDao = areaDao;
	}

	public BuildingDao getBuildingDao() {
		return buildingDao;
	}

	public void setBuildingDao(BuildingDao buildingDao) {
		this.buildingDao = buildingDao;
	}

	public RoomTakenItemDao getRoomTakenItemDao() {
		return roomTakenItemDao;
	}

	public void setRoomTakenItemDao(RoomTakenItemDao roomTakenItemDao) {
		this.roomTakenItemDao = roomTakenItemDao;
	}

	public JWRemoteService getJwRemoteService() {
		return jwRemoteService;
	}

	public void setJwRemoteService(JWRemoteService jwRemoteService) {
		this.jwRemoteService = jwRemoteService;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	

	@Override
	public boolean isUserExist(String studentId) {
		if(userDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("studentId", studentId))) != null) return true;
		return false;
	}

	@Override
	public User signupLocal(String studentId, String password) {
		
		if(isUserExist(studentId)){
			return null;
		}
		
		String sessionId = jwRemoteService.signinRemote(studentId, password);
		if(sessionId != null){
			User user = new User();
			
			user.setCurSessionId(sessionId);
			user.setCurSessionDate(new Date());
			
			user.setStudentId(studentId);
			user.setPassword(Crypto.encodeAES(password));
			
			user.setPrivateKey(Crypto.randomPrivateKey());
			user.setId(UUIDGenerator.randomUUID());
			
			userDao.add(user);
			
			return user;
		}
		return null;
	}

	@Override
	public User signinLocal(String studentId, String password) {
		
		if(!isUserExist(studentId)){
			return null;
		}
		
		String sessionId = jwRemoteService.signinRemote(studentId, password);
		if(sessionId != null){
			User user = userDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("studentId", studentId)));
			
			user.setCurSessionId(sessionId);
			user.setCurSessionDate(new Date());
			
			user.setPassword(Crypto.encodeAES(password));
			
			user.setPrivateKey(Crypto.randomPrivateKey());
			
			userDao.update(user);
			
			return user;
		}
		return null;		
		
	}

	

	@Override
	public LocalResult<String> prepareUser(String studentId, String dynamicPass) {
		User user = userDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("studentId", studentId)));
		
		String serverDynamicPass = Crypto.cypherDynamicPassword(user.getPrivateKey(), System.currentTimeMillis());
		
		LocalResult<String> sessionResult = new LocalResult<String>();
		//System.err.println(dynamicPass+"\n"+serverDynamicPass);
		if(dynamicPass.equals(serverDynamicPass)){
			//第二步，确认SessionId是否过期
			if(LifeDjtuConfig.getIntegerProperty("djtu.sessionAge")<(System.currentTimeMillis()-user.getCurSessionDate().getTime())){
				//相差时间大于Session的age，所以Session过时，重新获取
				String sessionId = jwRemoteService.signinRemote(user.getStudentId(), Crypto.decodeAES(user.getPassword()));
				if(sessionId==null||sessionId.equals("")){
					sessionResult.setResult(null);
					sessionResult.setResultState(ResultState.NEEDLOGIN.ordinal());
				}else{
					user.setCurSessionDate(new Date());
					user.setCurSessionId(sessionId);
					userDao.update(user);
					sessionResult.setResult(sessionId);
					sessionResult.setResultState(ResultState.SUCCESS.ordinal());
				}
			}else{
				//仍旧值得更新Session的日期，因为使用一次，Session就会重新倒计时
				user.setCurSessionDate(new Date());
				userDao.update(user);
				
				sessionResult.setResult(user.getCurSessionId());
				sessionResult.setResultState(ResultState.SUCCESS.ordinal());
			}
		}else{
			sessionResult.setResult(null);
			sessionResult.setResultState(ResultState.NEEDLOGIN.ordinal());
		}
		
		return sessionResult;
	}

	@Override
	public LocalResult<Boolean> changeLocalPassword(String studentId, String sessionId,
			String originPass, String newPass, String newPassAgain) {
		boolean result = jwRemoteService.changeRemotePassword(sessionId, originPass, newPass, newPassAgain);
		//System.err.println(result);
		if(result){
			User user = userDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("studentId", studentId)));
			user.setPassword(Crypto.encodeAES(newPass));
			userDao.update(user);
		}
		
		LocalResult<Boolean> localResult = new LocalResult<Boolean>();
		localResult.autoFill(result);
		
		return localResult;
	}

	/**
	 * 计划根据经纬度来定位学生周边的空教室情况
	 */
	@Override
	public LocalResult<List<RoomTakenItem>> queryFreeRooms(double longitude,
			double latitude) {
		// TODO Auto-generated method stub
		
		
		return null;
	}

	@Override
	public LocalResult<List<CourseInstance>> queryLocalCourseTabel(String studentId, String sessionId) {
		List<CourseDto> courseDtos = jwRemoteService.queryRemoteCourseTable(sessionId);
		
		List<CourseInstance> courseInstances = new ArrayList<CourseInstance>();
		
		for(CourseDto courseDto : courseDtos){
			courseInstances.add(LifeDjtuUtil.transferCourseDto(courseDto, -1, -1));
		}
		
		LocalResult<List<CourseInstance>> localResult = new LocalResult<List<CourseInstance>>();
		
		localResult.autoFill(courseInstances);
		
		Tuple tuple = userDao.findOneProjectedByParams(CriteriaWrapper.instance().and(Restrictions.eq("studentId", studentId)), ProjectionWrapper.instance().fields("id","studentId"));
		
		
		jwUpdateCacheAsyncer.updateCourseInfo((String)tuple.get(0), courseDtos, jwRemoteService.queryDjtuDate(sessionId));
		
		return localResult;
	}

	@Override
	public LocalResult<List<ExamDto>> queryLocalExams(String studentId, String sessionId) {
		
		List<ExamDto> examDtos = jwRemoteService.queryRemoteExams(sessionId);
		
		LocalResult<List<ExamDto>> localResult = new LocalResult<List<ExamDto>>();
		
		localResult.autoFill(examDtos);
		
		Tuple tuple = userDao.findOneProjectedByParams(CriteriaWrapper.instance().and(Restrictions.eq("studentId", studentId)), ProjectionWrapper.instance().fields("id","studentId"));
		
		jwUpdateCacheAsyncer.updateExamInfo((String)tuple.get(0), examDtos,jwRemoteService.queryDjtuDate(sessionId));
		
		return localResult;
	}

	@Override
	public LocalResult<User> fetchUserDetailInfo(String studentId,
			String sessionId) {
		StudentRegistry registry = jwRemoteService.fetchStudentRegistry(sessionId);
		
		User user = userDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("studentId", studentId)));
		
		user = registry.autofillUserInfo(user);
		
		userDao.update(user);
		
		LocalResult<User> localResult = new LocalResult<User>();
		localResult.autoFill(user);
		
		return localResult;
	}

	
	@Override
	public LocalResult<StudentRegistry> fetchStudentRegistry(String sessionId) {
		StudentRegistry registry = jwRemoteService.fetchStudentRegistry(sessionId);

		LocalResult<StudentRegistry> localResult = new LocalResult<StudentRegistry>();
		localResult.autoFill(registry);
		
		return localResult;
	}

	@Override
	public LocalResult<BuildingDto> queryBuildingOnDate(String sessionId,
			int aid, int buildingId, int week, int weekday) {
		
		BuildingDto buildingDto = jwRemoteService.queryBuildingOnDate(sessionId, aid, buildingId, week, weekday);
		
		LocalResult<BuildingDto> localResult = new LocalResult<BuildingDto>();
		
		localResult.autoFill(buildingDto);
		
		return localResult;
		
	}

	@Override
	public LocalResult<RoomDto> queryRoom(String sessionId, int aid,
			int buildingId, int roomId) {
		RoomDto roomDto = jwRemoteService.queryRoom(sessionId, aid, buildingId, roomId);
		
		LocalResult<RoomDto> localResult = new LocalResult<RoomDto>();
		localResult.autoFill(roomDto);
		
		return localResult;
	}

	@Override
	public LocalResult<List<ScoreDto>> queryLocalScores(String studentId, String sessionId, int schoolYear, int term) {
		List<ScoreDto> scoreDtos;
		
		if(schoolYear==0||term==0){
			scoreDtos = jwRemoteService.queryRemoteScores(sessionId);
			DjtuDate djtuDate = jwRemoteService.queryDjtuDate(sessionId);
			jwUpdateCacheAsyncer.updateScoreOutInfo(studentId, scoreDtos, djtuDate);
		}else{
			scoreDtos = jwRemoteService.queryRemoteScores(sessionId, schoolYear, term, false);
		}
		
		
		LocalResult<List<ScoreDto>> localResult = new LocalResult<List<ScoreDto>>();
		
		
		localResult.autoFill(scoreDtos);
		return localResult;		
	}

	/**
	 * 计划此方法运算学生的全部学期的平均分
	 */
	@Override
	public LocalResult<Double> queryAverageMarks(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalResult<Double> queryAverageMarksByTerm(String sessionId,
			int schoolYear, int term) {
		
		if(schoolYear==0||term==0){
			DjtuDate djtuDate = jwRemoteService.queryDjtuDate(sessionId);
			schoolYear = djtuDate.getSchoolYear();
			term = djtuDate.getTerm();
		}
		
		List<ScoreDto> scoreDtos = jwRemoteService.queryRemoteScores(sessionId, schoolYear, term,true);
		
		if(scoreDtos==null||scoreDtos.size()==0){
			LocalResult<Double> localResult = new LocalResult<Double>();
			localResult.autoFill(Double.valueOf(0));
			return localResult;
					
		}
		
		double scoreSum = 0;
		double markSum = 0;
		
		for(ScoreDto scoreDto : scoreDtos){
			
			if(scoreDto.getCourseProperty().equals(ExamStatus.CHONG_XIU.toString())){
				continue;
			}
			
			if(scoreDto.getTotalScore()==null||scoreDto.getTotalScore().equals("")){
				continue;
			}
			
			//System.err.println(scoreDto.toJSON());
			
			double mark = Double.parseDouble(scoreDto.getCourseMarks());
			double score;
			try{
				score =	Double.parseDouble(scoreDto.getTotalScore());
			}catch(Exception exception){
				String totalScore = scoreDto.getTotalScore();
				
				if(totalScore.equals("优")){
					score = 90;
				}else if(totalScore.equals("良")){
					score = 80;
				}else if(totalScore.equals("中")){
					score = 70;
				}else if(totalScore.equals("及格")){
					score = 60;
				}else if(totalScore.equals("不及格")){
					score = 50;
				}else if(totalScore.equals("合格")){
					score = 100;
				}else if(totalScore.equals("不合格")){
					score = 0;
				}else{
					throw new RuntimeException("cannot translate score!!!:"+totalScore);
				}
				
				//System.err.println("give it score:"+score);
				
			}
			scoreSum += mark*score;
			markSum += mark;
			
		}
		
		LocalResult<Double> localResult = new LocalResult<Double>();
		localResult.autoFill(scoreSum/markSum);
		
		//System.err.println(localResult.getResult());
		
		return localResult;
	}

	@Override
	public LocalResult<Double> queryGPAMarks(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalResult<Double> queryGPAMarksByTerm(String sessionId,
			int schoolYear, int term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalResult<List<Area>> queryLocalAreas() {
		LocalResult<List<Area>> localResult = new LocalResult<List<Area>>();
		localResult.autoFill(areaDao.findAll());
		return localResult;
	}

	@Override
	public LocalResult<List<Building>> queryLocalBuildings(String areaId) {
		LocalResult<List<Building>> localResult = new LocalResult<List<Building>>();
		localResult.autoFill(buildingDao.findByParams(CriteriaWrapper.instance().and(Restrictions.eq("area.id", areaId))));
		// TODO Auto-generated method stub
		return localResult;
	}

	@Override
	public LocalResult<List<RoomTakenItem>> queryFreeRooms(String buildingId) {
		LocalResult<List<RoomTakenItem>> localResult = new LocalResult<List<RoomTakenItem>>();
		localResult.autoFill(roomTakenItemDao.findByParams(CriteriaWrapper.instance().and(Restrictions.eq("building.id", buildingId))));
		return localResult;
	}

	@Override
	public LocalResult<Boolean> safeUpdateRoomTakenInfo() {
		
		List<RoomTakenItem> temp = roomTakenItemDao.findByParamsInPageInOrder(CriteriaWrapper.instance().and(Restrictions.isNotNull("dataDate")), Pageable.inPage(0, 1), Sortable.instance("dataDate", Sortable.DESCEND));
		
		boolean executeFlag = false;
		
		if(temp==null||temp.size()==0){
			executeFlag = true;
		}else{
			RoomTakenItem roomTakenItem = temp.get(0);
			
			Date date = new Date();
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			
			if(roomTakenItem.getDataDate().before(calendar.getTime())){
				//System.err.println("did update room taken info");
				executeFlag = true;
			}else{
				//System.err.println("did not update room taken info");
			}
		}
		
		
		if(executeFlag){
			jwUpdateCacheScheduler.updateRoomTakenInfo(jwRemoteService.randomSessionId());
		}
		
		LocalResult<Boolean> localResult = new LocalResult<Boolean>();
		localResult.autoFill(true);
		
		return localResult;
	}

	/**
	 * 不分年级，修读这门课的人，全部给出，主要用来应对：重修人士，培养计划改革，以及旧的未删数据
	 */
	@Override
	public LocalResult<List<User>> getSameCourseUsers(String courseId, int pageNum, int pageSize) {
		//Tuple courseInstance = courseInstanceDao.findOneProjectedByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseRemoteId", remoteId)), ProjectionWrapper.instance().fields("courseAlias","id"));
				
		//List<UserCourse> userCourses = userCourseDao.findByJoinedParams(MapMaker.instance("courseInstance", "courseInstance").toMap(), CriteriaWrapper.instance().and(Restrictions.eq("courseInstance.courseAlias", courseInstance.get(0))));
		//List<UserCourse> userCourses = userCourseDao.findByJoinedParamsInPageInOrder(MapMaker.instance("courseInstance", "courseInstance").toMap(), CriteriaWrapper.instance().and(Restrictions.eq("courseInstance.courseAlias", courseInstance.get(0))), Pageable.inPage(pageNum, pageSize), Sortable.instance("timestamp", Sortable.ASCEND));
		
		List<User> users = new ArrayList<User>();
		
		List<IMGroupUser> imGroupUsers = imGroupUserDao.findByJoinedParamsInPageInOrder(MapMaker.instance("imGroup", "imGroup").toMap(), CriteriaWrapper.instance().and(Restrictions.eq("imGroup.course.id", courseId)), Pageable.inPage(pageNum, pageSize), Sortable.instance("timestamp", Sortable.ASCEND));
		
		for(IMGroupUser imGroupUser:imGroupUsers){
			User user = new User();
			User temp = imGroupUser.getUser();
			user.setAcademy(temp.getAcademy());
			user.setArea(temp.getArea());
			user.setBirthDate(temp.getBirthDate());
			user.setCls(temp.getCls());
			user.setGender(temp.getGender());
			user.setGrade(temp.getGrade());
			user.setId(temp.getId());
			user.setMajor(temp.getMajor());
			user.setNickname(temp.getNickname());
			user.setProvince(temp.getProvince());
			user.setUsername(temp.getUsername());
			
			users.add(user);
		}
		
		LocalResult<List<User>> localResult = new LocalResult<List<User>>();
		localResult.autoFill(users);
		
		return localResult;
	}

	/**
	 * 什么叫sameClass，就是说，在同一个班修读这一门课~
	 */
	@Override
	public LocalResult<List<User>> getSameClassUsers(String courseInstanceId, int pageNum, int pageSize) {
		
		//List<UserCourse> userCourses = userCourseDao.findByJoinedParams(MapMaker.instance("courseInstance", "courseInstance").toMap(), CriteriaWrapper.instance().and(Restrictions.eq("courseInstance.courseRemoteId", remoteId)));
		//List<UserCourse> userCourses = userCourseDao.findByJoinedParamsInPageInOrder(MapMaker.instance("courseInstance", "courseInstance").toMap(), CriteriaWrapper.instance().and(Restrictions.eq("courseInstance.courseRemoteId", remoteId)),Pageable.inPage(pageNum, pageSize),Sortable.instance("timestamp", Sortable.ASCEND));
		
		List<IMGroupUser> imGroupUsers = imGroupUserDao.findByJoinedParamsInPageInOrder(MapMaker.instance("imGroup", "imGroup").toMap(), CriteriaWrapper.instance().and(Restrictions.eq("imGroup.courseInstance.id", courseInstanceId)), Pageable.inPage(pageNum, pageSize), Sortable.instance("timestamp", Sortable.ASCEND));
		
		List<User> users = new ArrayList<User>();
		for(IMGroupUser imGroupUser:imGroupUsers){
			User user = new User();
			User temp = imGroupUser.getUser();
			user.setAcademy(temp.getAcademy());
			user.setArea(temp.getArea());
			user.setBirthDate(temp.getBirthDate());
			user.setCls(temp.getCls());
			user.setGender(temp.getGender());
			user.setGrade(temp.getGrade());
			user.setId(temp.getId());
			user.setMajor(temp.getMajor());
			user.setNickname(temp.getNickname());
			user.setProvince(temp.getProvince());
			user.setUsername(temp.getUsername());
			
			users.add(user);
		}
		
		LocalResult<List<User>> localResult = new LocalResult<List<User>>();
		localResult.autoFill(users);
		
		return localResult;
	}

	/**
	 * 同年级的~~修读同一门课，在不同的教学班
	 */
	/*
	@Override
	public LocalResult<List<User>> getSameGradeUsers(String studentId,String remoteId, int pageNum, int pageSize) {
		Tuple courseInstance = courseInstanceDao.findOneProjectedByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseRemoteId", remoteId)), ProjectionWrapper.instance().fields("courseAlias","id"));
		Tuple curUser = userDao.findOneProjectedByParams(CriteriaWrapper.instance().and(Restrictions.eq("studentId", studentId)),ProjectionWrapper.instance().fields("grade","id"));
		
		//List<UserCourse> userCourses = userCourseDao.findByJoinedParams(MapMaker.instance("courseInstance", "courseInstance").param("user", "user").toMap(), CriteriaWrapper.instance().and(Restrictions.eq("user.grade", curUser.get(0)),Restrictions.eq("courseInstance.courseAlias", courseInstance.get(0))));
		List<UserCourse> userCourses = userCourseDao.findByJoinedParamsInPageInOrder(MapMaker.instance("courseInstance", "courseInstance").param("user", "user").toMap(), CriteriaWrapper.instance().and(Restrictions.eq("user.grade", curUser.get(0)),Restrictions.eq("courseInstance.courseAlias", courseInstance.get(0))),Pageable.inPage(pageNum, pageSize),Sortable.instance("timestamp", Sortable.ASCEND));

		List<User> users = new ArrayList<User>();
		for(UserCourse userCourse:userCourses){
			User user = new User();
			User temp = userCourse.getUser();
			user.setAcademy(temp.getAcademy());
			user.setArea(temp.getArea());
			user.setBirthDate(temp.getBirthDate());
			user.setCls(temp.getCls());
			user.setGender(temp.getGender());
			user.setGrade(temp.getGrade());
			user.setId(temp.getId());
			user.setMajor(temp.getMajor());
			user.setNickname(temp.getNickname());
			user.setProvince(temp.getProvince());
			user.setUsername(temp.getUsername());
			
			users.add(user);
		}
		
		LocalResult<List<User>> localResult = new LocalResult<List<User>>();
		localResult.autoFill(users);
		
		return localResult;
	}
	 */
	@Override
	public LocalResult<CourseInstance> getCourseInstance(String sessionId, String courseInstanceId) {
		if(courseInstanceId==null||courseInstanceId.equals("")){
			return null;
		}
		
		CourseInstance courseInstance = courseInstanceDao.findOneById(courseInstanceId);
		
		LocalResult<CourseInstance> localResult = new LocalResult<CourseInstance>();
		localResult.autoFill(courseInstance);
		
		return localResult;
	}

	@Override
	public LocalResult<Boolean> giveGoodEvalToCourse(String studentId,
			String courseInstanceId) {
		int result = courseInstanceDao.updateFirstById(courseInstanceId, UpdateWrapper.instance().inc("'goodEval'", 1));
					
		LocalResult<Boolean> localResult = new LocalResult<Boolean>();
		
		if(result>0){
			localResult.autoFill(true);
		}else{
			localResult.autoFill(false);
		}
		
		return localResult;
	}

	@Override
	public LocalResult<Boolean> giveBadEvalToCourse(String studentId,
			String courseInstanceId) {
		int result = courseInstanceDao.updateFirstById(courseInstanceId, UpdateWrapper.instance().inc("'badEval'", 1));
		
		LocalResult<Boolean> localResult = new LocalResult<Boolean>();
		
		if(result>0){
			localResult.autoFill(true);
		}else{
			localResult.autoFill(false);
		}
		
		return localResult;
	}

	@Override
	public LocalResult<Integer> getSameCourseUserNum(String courseId) {
		List<IMGroupUser> imGroupUsers = imGroupUserDao.findByJoinedParams(MapMaker.instance("imGroup", "imGroup").toMap(), CriteriaWrapper.instance().and(Restrictions.eq("imGroup.course.id", courseId)));

		LocalResult<Integer> localResult = new LocalResult<Integer>();
		localResult.autoFill(imGroupUsers.size());
		return localResult;
	}

	@Override
	public LocalResult<Integer> getSameClassUserNum(String courseInstanceId) {
		List<IMGroupUser> imGroupUsers = imGroupUserDao.findByJoinedParams(MapMaker.instance("imGroup", "imGroup").toMap(), CriteriaWrapper.instance().and(Restrictions.eq("imGroup.courseInstance.id", courseInstanceId)));
		
		LocalResult<Integer> localResult = new LocalResult<Integer>();
		localResult.autoFill(imGroupUsers.size());
		return localResult;
	}

	@Override
	public String getCourseIdByAlias(String courseAlias) {
		Tuple tuple = courseDao.findOneProjectedByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseAlias", courseAlias)), ProjectionWrapper.instance().fields("id","courseAlias"));
	
		return (String)tuple.get(0);
	}

	@Override
	public String getCourseInstanceIdByRemoteId(String remoteId) {
		Tuple tuple = courseInstanceDao.findOneProjectedByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseRemoteId", remoteId)), ProjectionWrapper.instance().fields("id","courseRemoteId"));
		
		return (String)tuple.get(0);
	}

	@Override
	public LocalResult<Boolean> addFriend(String studentId, String friendStudentId,
			String content) {
		User mySelf = userDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("studentId", studentId)));
		User pendingFriend = userDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("studentId", friendStudentId)));
		
		FriendPending friendPending = new FriendPending();
		friendPending.setId(UUIDGenerator.randomUUID());
		friendPending.setRequestContent(content);
		friendPending.setRequestDes(pendingFriend);
		friendPending.setRequestDesName(pendingFriend.getUsername());
		friendPending.setRequestDesStudentId(friendStudentId);
		
		friendPending.setRequestSource(mySelf);
		friendPending.setRequestSourceName(mySelf.getUsername());
		friendPending.setRequestSourceStudentId(studentId);
		friendPending.setRequestStatus(FriendRequestStatus.PENDING.ordinal());
		
		friendPendingDao.add(friendPending);
		
		LocalResult<Boolean> localResult = new LocalResult<Boolean>();
		localResult.autoFill(true);
		
		return localResult;
	}

	@Override
	public LocalResult<List<FriendPending>> getFriendPendingList(
			String studentId) {
		List<FriendPending> pendings = friendPendingDao.findByParams(CriteriaWrapper.instance().and(Restrictions.eq("requestDesStudentId", studentId)));
	
		LocalResult<List<FriendPending>> localResult = new LocalResult<List<FriendPending>>();
		localResult.autoFill(pendings);
		
		return localResult;
	}

	@Override
	public LocalResult<Boolean> answerFriendRequest(String studentId,
			String requestSourceStudentId, FriendRequestStatus status) {
		
		LocalResult<Boolean> localResult = new LocalResult<Boolean>();
		if(FriendRequestStatus.PENDING==status){//非法！！，不能将任何一个请求重新改为pending
			localResult.autoFill(false);
			return localResult;
		}
		
		List<FriendPending> friendPendings = friendPendingDao.findByParams(CriteriaWrapper.instance().and(Restrictions.eq("requestSourceStudentId", requestSourceStudentId),Restrictions.eq("requestDesStudentId", studentId),Restrictions.eq("requestStatus", FriendRequestStatus.PENDING.ordinal())));
	
		if(friendPendings.size()==0){//并没有类似请求，用户操作非法
			localResult.autoFill(false);
			return localResult;
		}
		
		for(FriendPending friendPending : friendPendings){
			friendPending.setRequestStatus(status.ordinal());
		}
		
		if(status==FriendRequestStatus.REFUSED){ //用户拒绝了，不做任何事情
			localResult.autoFill(true);
			return localResult;
		}
		
		User mySelf = friendPendings.get(0).getRequestDes();
		User wantToAddMe = friendPendings.get(0).getRequestSource();
		
		if(status==FriendRequestStatus.ALLOWED){ //单纯的同意，单方添加
			Friend friend = new Friend();
			friend.setFriend(mySelf);
			friend.setToUser(wantToAddMe);
			friend.setFriendStudentId(studentId);
			friend.setId(UUIDGenerator.randomUUID());
			friend.setToUserStudentId(requestSourceStudentId);
			friendDao.add(friend);
		}else if(status==FriendRequestStatus.ALLOWED_BOTH){ //双方互加！！好友
			Friend friend = new Friend();
			friend.setFriend(mySelf);
			friend.setToUser(wantToAddMe);
			friend.setFriendStudentId(studentId);
			friend.setId(UUIDGenerator.randomUUID());
			friend.setToUserStudentId(requestSourceStudentId);
			friendDao.add(friend);
			
			Friend friend2 = new Friend();
			friend2.setFriend(wantToAddMe);
			friend2.setToUser(mySelf);
			friend2.setFriendStudentId(requestSourceStudentId);
			friend2.setId(UUIDGenerator.randomUUID());
			friend2.setToUserStudentId(studentId);
			friendDao.add(friend2);
		}
		
		localResult.autoFill(true);
		return localResult;
	}

	@Override
	public LocalResult<List<FriendPending>> viewFriendRequestResultList(
			String studentId) {
		
		List<FriendPending> list = friendPendingDao.findByParams(CriteriaWrapper.instance().and(Restrictions.eq("requestSourceStudentId", studentId),Restrictions.ne("requestStatus", FriendRequestStatus.PENDING.ordinal()),Restrictions.eq("requestSourceReadFlag", 0)));

		for(FriendPending friendPending : list){
			friendPending.setRequestSourceReadFlag(1);
		}
		
		LocalResult<List<FriendPending>> localResult = new LocalResult<List<FriendPending>>();
		
		localResult.autoFill(list);
		
		return localResult;
	}

	@Override
	public LocalResult<List<User>> getFriendList(String studentId) {
		List<Friend> friends = friendDao.findByParams(CriteriaWrapper.instance().and(Restrictions.eq("toUserStudentId", studentId)));
	
		List<User> users = new ArrayList<User>();
		
		for(Friend friend : friends){
			User user = new User();
			User temp = friend.getFriend();
			user.setAcademy(temp.getAcademy());
			user.setArea(temp.getArea());
			user.setBirthDate(temp.getBirthDate());
			user.setCls(temp.getCls());
			user.setGender(temp.getGender());
			user.setGrade(temp.getGrade());
			user.setId(temp.getId());
			user.setMajor(temp.getMajor());
			user.setNickname(temp.getNickname());
			user.setProvince(temp.getProvince());
			user.setUsername(temp.getUsername());
			
			users.add(user);
		}
		
		LocalResult<List<User>> localResult = new LocalResult<List<User>>();
		localResult.autoFill(users);
		
		return localResult;
	}

	@Override
	public LocalResult<Boolean> removeFriend(String studentId, String friendStudentId,
			boolean removeBoth) {
		friendDao.deleteByParams(CriteriaWrapper.instance().and(Restrictions.eq("'toUserStudentId'", studentId),Restrictions.eq("'friendStudentId'", friendStudentId)));

		if(removeBoth){
			friendDao.deleteByParams(CriteriaWrapper.instance().and(Restrictions.eq("'toUserStudentId'", friendStudentId),Restrictions.eq("'friendStudentId'", studentId)));
		}
		
		LocalResult<Boolean> localResult = new LocalResult<Boolean>();
		localResult.autoFill(true);
		return localResult;
	}

	/*
	@Override
	public LocalResult<Integer> getSameGradeUserNum(String studentId,
			String remoteId) {
		Tuple courseInstance = courseInstanceDao.findOneProjectedByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseRemoteId", remoteId)), ProjectionWrapper.instance().fields("courseAlias","id"));
		Tuple curUser = userDao.findOneProjectedByParams(CriteriaWrapper.instance().and(Restrictions.eq("studentId", studentId)),ProjectionWrapper.instance().fields("grade","id"));
		
		List<UserCourse> userCourses = userCourseDao.findByJoinedParams(MapMaker.instance("courseInstance", "courseInstance").param("user", "user").toMap(), CriteriaWrapper.instance().and(Restrictions.eq("user.grade", curUser.get(0)),Restrictions.eq("courseInstance.courseAlias", courseInstance.get(0))));
		
		LocalResult<Integer> localResult = new LocalResult<Integer>();
		localResult.autoFill(userCourses.size());
		return localResult;
	}
	*/
	
	
}
