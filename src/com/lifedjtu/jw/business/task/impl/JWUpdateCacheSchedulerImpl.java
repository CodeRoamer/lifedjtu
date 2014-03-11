package com.lifedjtu.jw.business.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lifedjtu.jw.business.JWLocalService;
import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.business.task.JWUpdateCacheScheduler;
import com.lifedjtu.jw.dao.CriteriaWrapper;
import com.lifedjtu.jw.dao.impl.AreaDao;
import com.lifedjtu.jw.dao.impl.BuildingDao;
import com.lifedjtu.jw.dao.impl.CourseDao;
import com.lifedjtu.jw.dao.impl.CourseInstanceDao;
import com.lifedjtu.jw.dao.impl.IMGroupDao;
import com.lifedjtu.jw.dao.impl.IMGroupUserDao;
import com.lifedjtu.jw.dao.impl.InstantMessageDao;
import com.lifedjtu.jw.dao.impl.RoomDao;
import com.lifedjtu.jw.dao.impl.RoomTakenItemDao;
import com.lifedjtu.jw.dao.impl.UserCourseDao;
import com.lifedjtu.jw.dao.support.UUIDGenerator;
import com.lifedjtu.jw.pojos.Area;
import com.lifedjtu.jw.pojos.Building;
import com.lifedjtu.jw.pojos.Course;
import com.lifedjtu.jw.pojos.CourseInstance;
import com.lifedjtu.jw.pojos.IMGroup;
import com.lifedjtu.jw.pojos.IMGroupUser;
import com.lifedjtu.jw.pojos.Room;
import com.lifedjtu.jw.pojos.RoomTakenItem;
import com.lifedjtu.jw.pojos.User;
import com.lifedjtu.jw.pojos.UserCourse;
import com.lifedjtu.jw.pojos.dto.BuildingDto;
import com.lifedjtu.jw.pojos.dto.DjtuDate;
import com.lifedjtu.jw.pojos.dto.RoomInfoDto;
import com.lifedjtu.jw.util.LifeDjtuEnum.GroupFlag;
import com.lifedjtu.jw.util.MapMaker;

@Component("jwUpdateCacheScheduler")
@Transactional
public class JWUpdateCacheSchedulerImpl implements JWUpdateCacheScheduler{
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private RoomDao roomDao;
	@Autowired
	private RoomTakenItemDao roomTakenItemDao;
	@Autowired
	private JWRemoteService jwRemoteService;
	@Autowired
	private JWLocalService jwLocalService;
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private CourseInstanceDao courseInstanceDao;
	@Autowired
	private IMGroupDao imGroupDao;
	@Autowired
	private IMGroupUserDao imGroupUserDao;
	@Autowired
	private InstantMessageDao instantMessageDao;
	@Autowired
	private UserCourseDao userCourseDao;
	
	@Override
	public boolean updateBuildingEntryInfo(String sessionId, String areaId) {
		Area area = areaDao.findOneById(areaId);
		List<Building> buildings = jwRemoteService.queryRemoteBuildings(sessionId, area.getAreaRemoteId()+"");
		for(Building building : buildings){
			Building temp = buildingDao.findOneByParams(CriteriaWrapper.instance().or(Restrictions.eq("buildingRemoteId", building.getBuildingRemoteId()),Restrictions.eq("buildingName", building.getBuildingName())));
			if(temp==null){
				building.setId(UUIDGenerator.randomUUID());
				building.setArea(area);
				buildingDao.add(building);
			}else{
				temp.setBuildingName(building.getBuildingName());
				temp.setBuildingRemoteId(building.getBuildingRemoteId());
				buildingDao.update(temp);
			}			
		}
		
		return true;
	}
	@Override
	public boolean updateRoomEntryInfo(String sessionId, String buildingId) {
		Building building = buildingDao.findOneById(buildingId);
		System.out.println(building.getBuildingName()+" 开始处理...");
		List<Room> rooms = jwRemoteService.queryRemoteRooms(sessionId, building.getBuildingRemoteId()+"");
		for(Room room : rooms){
			Room temp = roomDao.findOneByParams(CriteriaWrapper.instance().or(Restrictions.eq("roomRemoteId", room.getRoomRemoteId()),Restrictions.eq("roomName", room.getRoomName())));
			if(temp==null){
				room.setId(UUIDGenerator.randomUUID());
				room.setBuilding(building);
				roomDao.add(room);
			}else{
				temp.setRoomName(room.getRoomName());
				temp.setCourseCapacity(room.getCourseCapacity());
				temp.setExamCapacity(room.getExamCapacity());
				temp.setRoomRemoteId(room.getRoomRemoteId());
				temp.setRoomSeatNum(room.getRoomSeatNum());
				temp.setRoomSeatType(room.getRoomSeatType());
				temp.setRoomType(room.getRoomType());
				roomDao.update(temp);
			}	
		}
		System.out.println(building.getBuildingName()+" 已经处理完毕！");
		return true;
	}


	@Override
	public boolean updateAreaInfo(String sessionId) {
		List<Area> areas = jwRemoteService.queryRemoteAreas(sessionId);
		for(Area area : areas){
			System.out.println(area.getAreaName()+"开始寻找...");
			Area temp = areaDao.findOneByParams(CriteriaWrapper.instance().or(Restrictions.eq("areaRemoteId", area.getAreaRemoteId()),Restrictions.eq("areaName", area.getAreaName())));
			if(temp==null){
				System.out.println(area.getAreaName()+"不存在，设置新的主键ID...");
				area.setId(UUIDGenerator.randomUUID());
				areaDao.add(area);
			}else{
				System.out.println(area.getAreaName()+"已存在，更新实体...");
				temp.setAreaName(area.getAreaName());
				temp.setAreaRemoteId(area.getAreaRemoteId());
				areaDao.update(temp);
			}
		}
		return true;
	}
	@Override
	public boolean updateBuildingInfo(String sessionId) {
		List<Area> areas = areaDao.findAll();
		for(Area area : areas){
			updateBuildingEntryInfo(sessionId, area.getId());
		}
		
		return true;
	}
	@Override
	public boolean updateRoomInfo(String sessionId) {
		List<Building> buildings = buildingDao.findAll();
		for(Building building : buildings){
			updateRoomEntryInfo(sessionId, building.getId());
		}
		
		return true;
	}
	@Override
	public boolean updateRoomTakenItem(String sessionId) {
		DjtuDate djtuDate = jwRemoteService.queryDjtuDate(sessionId);
		//System.err.println(djtuDate.toJSON());
		List<Building> buildings = buildingDao.findAll();
		for(Building building : buildings){
			System.out.println(building.getBuildingName()+"开始处理...");
			boolean result = updateRoomTakenItemByBuilding(sessionId, building.getId(), djtuDate.getWeek(), djtuDate.getWeekDay());
			System.out.println(building.getBuildingName()+"处理结束~ 结果："+result);
		}
		return true;
	}
	@Override
	public boolean updateRoomTakenItemByBuilding(String session,
			String buildingId, int week, int weekday) {
		Building building = buildingDao.findOneById(buildingId);
		BuildingDto buildingDtoTod = jwRemoteService.queryBuildingOnDate(session, -1, building.getBuildingRemoteId(), week, weekday);
		if(++weekday==8){
			++week;
			weekday=1;
		}
		BuildingDto buildingDtoTom = jwRemoteService.queryBuildingOnDate(session, -1, building.getBuildingRemoteId(), week, weekday);
		List<Room> rooms = building.getRooms();
		if(rooms==null||rooms.size()==0){
			return false;
		}
		List<RoomInfoDto> roomInfoDtosTod = buildingDtoTod.getRoomInfoDtos();
		List<RoomInfoDto> roomInfoDtosTom = buildingDtoTom.getRoomInfoDtos();
		List<RoomTakenItem> roomTakenItems = new ArrayList<RoomTakenItem>();
		
		Iterator<RoomInfoDto> iteratorTod = roomInfoDtosTod.iterator();
		Iterator<RoomInfoDto> iteratorTom = roomInfoDtosTom.iterator();
		
		while(iteratorTod.hasNext()){
			RoomInfoDto roomInfoDtoTod = iteratorTod.next();
			RoomInfoDto roomInfoDtoTom = iteratorTom.next();
			
			RoomTakenItem roomTakenItem = roomTakenItemDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("roomName", roomInfoDtoTod.getRoomName()),Restrictions.eq("building.id", building.getId())));
			if(roomTakenItem==null){
				roomTakenItem = new RoomTakenItem();
				roomTakenItem.setBuilding(building);
				roomTakenItem.setId(UUIDGenerator.randomUUID());
				roomTakenItem.setRoomName(roomInfoDtoTod.getRoomName());
				roomTakenItem.setRoom(queryRoom(rooms, roomInfoDtoTod.getRoomName()));
			}
			
			roomTakenItem.setTodayTakenCondition(transferRoomTakenCondition(roomInfoDtoTod.getOccupyInfos()));
			roomTakenItem.setTomorrowTakenCondition(transferRoomTakenCondition(roomInfoDtoTom.getOccupyInfos()));
			roomTakenItem.setDataDate(new Date());	
			
			roomTakenItems.add(roomTakenItem);
		}
		
		
		roomTakenItemDao.addMulti(roomTakenItems);
		
		return true;
	}
	

	
	
	public UserCourseDao getUserCourseDao() {
		return userCourseDao;
	}
	public void setUserCourseDao(UserCourseDao userCourseDao) {
		this.userCourseDao = userCourseDao;
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
	public IMGroupDao getImGroupDao() {
		return imGroupDao;
	}
	public void setImGroupDao(IMGroupDao imGroupDao) {
		this.imGroupDao = imGroupDao;
	}
	public IMGroupUserDao getImGroupUserDao() {
		return imGroupUserDao;
	}
	public void setImGroupUserDao(IMGroupUserDao imGroupUserDao) {
		this.imGroupUserDao = imGroupUserDao;
	}
	public InstantMessageDao getInstantMessageDao() {
		return instantMessageDao;
	}
	public void setInstantMessageDao(InstantMessageDao instantMessageDao) {
		this.instantMessageDao = instantMessageDao;
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
	public RoomDao getRoomDao() {
		return roomDao;
	}
	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}
	public JWRemoteService getJwRemoteService() {
		return jwRemoteService;
	}
	public void setJwRemoteService(JWRemoteService jwRemoteService) {
		this.jwRemoteService = jwRemoteService;
	}
	public JWLocalService getJwLocalService() {
		return jwLocalService;
	}

	public void setJwLocalService(JWLocalService jwLocalService) {
		this.jwLocalService = jwLocalService;
	}

	public RoomTakenItemDao getRoomTakenItemDao() {
		return roomTakenItemDao;
	}
	public void setRoomTakenItemDao(RoomTakenItemDao roomTakenItemDao) {
		this.roomTakenItemDao = roomTakenItemDao;
	}
	private Room queryRoom(List<Room> rooms, String roomName){
		for(Room room : rooms){
			if(room.getRoomName().equals(roomName)){
				return room;
			}
		}
		return null;
	}

	private String transferRoomTakenCondition(List<String> roomTakenList){
		StringBuilder builder = new StringBuilder();
		for(String taken : roomTakenList){
			if(taken.trim().equals("")){
				builder.append('0');
			}else{
				builder.append('1');
			}
		}
		//System.out.println(roomTakenList.toString());
		//System.out.println(builder.toString());
		return builder.toString();
	}
	
	@Override
	@Async
	public void updateRoomTakenInfo(String sessionId){
		updateRoomTakenItem(sessionId);
	}
	
	@Override
	@Scheduled(cron="0 0 1 * * ?")
	//@Scheduled(fixedDelay=300000)
	public void updateSchoolInfo() {
		System.err.println("Daily updated info start!");
		String sessionId = jwRemoteService.randomSessionId();
		updateAreaInfo(sessionId);
		updateBuildingInfo(sessionId);
		updateRoomInfo(sessionId);
		updateRoomTakenItem(sessionId);
		System.err.println("Daily updated info complete!!");
	}
	
	
	
	
	@Override
	public boolean updateSameCourseGroupInfo() {
		List<Course> courses = courseDao.findAll();
		for(Course course: courses){
			//System.err.println("add Course:"+course.getCourseAlias());
			IMGroup imGroup = imGroupDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("course.id", course.getId())));
			if(imGroup==null){
				imGroup = new IMGroup();
				imGroup.setGroupFlag(GroupFlag.SAME_COURSE.ordinal());
				imGroup.setCourse(course);
				imGroup.setId(UUIDGenerator.randomUUID());
				imGroupDao.add(imGroup);
			}
			
			List<UserCourse> userCourses = userCourseDao.findByJoinedParams(MapMaker.instance("courseInstance", "courseInstance").toMap(), CriteriaWrapper.instance().and(Restrictions.eq("courseInstance.course.id", course.getId())));
			//System.err.println("find student learn this course:"+userCourses.size());
			for(UserCourse userCourse : userCourses){
				User temp = userCourse.getUser();
				
				IMGroupUser imGroupUser = imGroupUserDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("imGroup.id", imGroup.getId()),Restrictions.eq("user.id", temp.getId())));
				if(imGroupUser==null){
					imGroupUser = new IMGroupUser();
					imGroupUser.setId(UUIDGenerator.randomUUID());
					imGroupUser.setImGroup(imGroup);
					imGroupUser.setStudentId(temp.getStudentId());
					imGroupUser.setUser(temp);
					imGroupUserDao.add(imGroupUser);
				}
			}
			
			String courseId = course.getId();
			
			updateSameClassGroupInfoByCourse(courseId);
		}
		
		return true;
	}
	@Override
	public boolean updateSameClassGroupInfoByCourse(String courseId) {
		List<CourseInstance> courseInstances = courseInstanceDao.findByParams(CriteriaWrapper.instance().and(Restrictions.eq("course.id", courseId)));
		for(CourseInstance courseInstance: courseInstances){
			//System.err.println("\tadd CourseInstance:"+courseInstance.getCourseRemoteId());

			IMGroup imGroup = imGroupDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseInstance.id", courseInstance.getId())));
			if(imGroup==null){
				imGroup = new IMGroup();
				imGroup.setGroupFlag(GroupFlag.SAME_CLASS.ordinal());
				imGroup.setCourseInstance(courseInstance);
				imGroup.setId(UUIDGenerator.randomUUID());
				imGroupDao.add(imGroup);
			}
			
			List<UserCourse> userCourses = userCourseDao.findByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseInstance.id", courseInstance.getId())));
			//System.err.println("find student learn this courseInstance:"+userCourses.size());
			for(UserCourse userCourse : userCourses){
				User temp = userCourse.getUser();
				
				IMGroupUser imGroupUser = imGroupUserDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("imGroup.id", imGroup.getId()),Restrictions.eq("user.id", temp.getId())));
				if(imGroupUser==null){
					imGroupUser = new IMGroupUser();
					imGroupUser.setId(UUIDGenerator.randomUUID());
					imGroupUser.setImGroup(imGroup);
					imGroupUser.setStudentId(temp.getStudentId());
					imGroupUser.setUser(temp);
					imGroupUserDao.add(imGroupUser);
				}
			}
		}
		
		return true;
	}
	
	
	@Override
	public boolean cleanupInstantMessage() {
		instantMessageDao.deleteByParams(CriteriaWrapper.instance().and(Restrictions.eq("'readFlag'", 1)));
		return true;
	}
	
	//@Scheduled(fixedDelay=120000)
	@Scheduled(cron="0 0 2 * * ?")
	@Override
	public void updateIMSystem() {
		try{
			updateSameCourseGroupInfo();
			cleanupInstantMessage();
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
}
