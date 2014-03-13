package com.lifedjtu.jw.business.task.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.business.task.JWUpdateCacheAsyncer;
import com.lifedjtu.jw.dao.CriteriaWrapper;
import com.lifedjtu.jw.dao.impl.CourseDao;
import com.lifedjtu.jw.dao.impl.CourseInstanceDao;
import com.lifedjtu.jw.dao.impl.ExamDao;
import com.lifedjtu.jw.dao.impl.ExamInstanceDao;
import com.lifedjtu.jw.dao.impl.IMGroupDao;
import com.lifedjtu.jw.dao.impl.IMGroupUserDao;
import com.lifedjtu.jw.dao.impl.SystemNoticeDao;
import com.lifedjtu.jw.dao.impl.UserCourseDao;
import com.lifedjtu.jw.dao.impl.UserDao;
import com.lifedjtu.jw.dao.support.UUIDGenerator;
import com.lifedjtu.jw.pojos.Course;
import com.lifedjtu.jw.pojos.CourseInstance;
import com.lifedjtu.jw.pojos.Exam;
import com.lifedjtu.jw.pojos.ExamInstance;
import com.lifedjtu.jw.pojos.IMGroup;
import com.lifedjtu.jw.pojos.IMGroupUser;
import com.lifedjtu.jw.pojos.SystemNotice;
import com.lifedjtu.jw.pojos.User;
import com.lifedjtu.jw.pojos.UserCourse;
import com.lifedjtu.jw.pojos.dto.CourseDto;
import com.lifedjtu.jw.pojos.dto.CourseRecordDto;
import com.lifedjtu.jw.pojos.dto.CourseTakenItem;
import com.lifedjtu.jw.pojos.dto.DjtuDate;
import com.lifedjtu.jw.pojos.dto.ExamDto;
import com.lifedjtu.jw.pojos.dto.ExamTimeEntry;
import com.lifedjtu.jw.pojos.dto.ScoreDto;
import com.lifedjtu.jw.util.MapMaker;
import com.lifedjtu.jw.util.LifeDjtuEnum.GroupFlag;
import com.lifedjtu.jw.util.pattern.InfoProcessHub;

@Component("jwUpdateCacheAsyncer")
@Transactional
public class JWUpdateCacheAsyncerImpl implements JWUpdateCacheAsyncer{
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private CourseInstanceDao courseInstanceDao;
	@Autowired
	private ExamDao examDao;
	@Autowired
	private ExamInstanceDao examInstanceDao;
	@Autowired
	private UserCourseDao userCourseDao;
	@Autowired
	private JWRemoteService jwRemoteService;
	@Autowired
	private SystemNoticeDao systemNoticeDao;
	@Autowired
	private IMGroupDao imGroupDao;
	@Autowired
	private IMGroupUserDao imGroupUserDao;
	@Autowired
	private UserDao userDao;
	/**
	 * 此方法用来更新course表
	 */
	@Override
	@Async
	public void updateCourseInfo(String userId, List<CourseDto> courseDtos, DjtuDate djtuDate) {
		List<Course> courses = new ArrayList<Course>();
		List<CourseInstance> courseInstances = new ArrayList<CourseInstance>();
		List<UserCourse> userCourses = new ArrayList<UserCourse>();
		List<IMGroup> imGroups = new ArrayList<IMGroup>();
		List<IMGroupUser> imGroupUsers = new ArrayList<IMGroupUser>();
		
		//there is a need for me to get the user!!! sorry for the efficiency
		//如果我不手动去User，那么在添加imGroupUser时 stduetnId字段仍为空。。。
		//而且我需要修改user的ready字段
		User user = userDao.findOneById(userId);
		
		for(CourseDto courseDto : courseDtos){
			//System.out.println("Course Info:\n"+courseDto.toJSON());
			Course course = courseDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseAlias", courseDto.getAliasName()),Restrictions.eq("courseName", courseDto.getCourseName())));
			if(course==null){
				course = new Course();
				course.setCourseAlias(courseDto.getAliasName());
				course.setCourseName(courseDto.getCourseName());
				course.setId(UUIDGenerator.randomUUID());
				course.setCourseCredits(Double.parseDouble(courseDto.getCourseMarks().trim()));
				course.setCourseProperty(courseDto.getCourseAttr());
				courses.add(course);
			}
			//添加Course group
			IMGroup imGroup_course = imGroupDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("course.id", course.getId())));
			if(imGroup_course==null){
				imGroup_course = new IMGroup();
				imGroup_course.setGroupFlag(GroupFlag.SAME_COURSE.ordinal());
				imGroup_course.setCourse(course);
				imGroup_course.setId(UUIDGenerator.randomUUID());
				imGroups.add(imGroup_course);
			}
			
			CourseInstance courseInstance = updateCourseInstanceInfo(course, courseDto, djtuDate.getSchoolYear(), djtuDate.getTerm());
			courseInstances.add(courseInstance);
			
			//添加class group
			IMGroup imGroup_class = imGroupDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseInstance.id", courseInstance.getId())));
			if(imGroup_class==null){
				imGroup_class = new IMGroup();
				imGroup_class.setGroupFlag(GroupFlag.SAME_CLASS.ordinal());
				imGroup_class.setCourseInstance(courseInstance);
				imGroup_class.setId(UUIDGenerator.randomUUID());
				imGroups.add(imGroup_class);
			}
			
			
			UserCourse userCourse = userCourseDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("user.id", userId),Restrictions.eq("courseInstance.id", courseInstance.getId())));
			
			
			
			if(userCourse==null){
				userCourse = new UserCourse();
				userCourse.setId(UUIDGenerator.randomUUID());
				userCourse.setUser(user);
				userCourse.setCourseInstance(courseInstance);
				userCourses.add(userCourse);
			}
			
			IMGroupUser imGroupUser_Course = imGroupUserDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("imGroup.id", imGroup_course.getId()),Restrictions.eq("user.id", userCourse.getUser().getId())));
			if(imGroupUser_Course==null){
				imGroupUser_Course = new IMGroupUser();
				imGroupUser_Course.setId(UUIDGenerator.randomUUID());
				imGroupUser_Course.setImGroup(imGroup_course);
				imGroupUser_Course.setStudentId(userCourse.getUser().getStudentId());
				imGroupUser_Course.setUser(userCourse.getUser());
				imGroupUsers.add(imGroupUser_Course);
			}
			
			IMGroupUser imGroupUser_class = imGroupUserDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("imGroup.id", imGroup_class.getId()),Restrictions.eq("user.id", userCourse.getUser().getId())));
			if(imGroupUser_class==null){
				imGroupUser_class = new IMGroupUser();
				imGroupUser_class.setId(UUIDGenerator.randomUUID());
				imGroupUser_class.setImGroup(imGroup_class);
				imGroupUser_class.setStudentId(userCourse.getUser().getStudentId());
				imGroupUser_class.setUser(userCourse.getUser());
				imGroupUsers.add(imGroupUser_class);
			}
			
			//System.out.println("Course Instance:\n"+courseInstance.toJSON());
			//System.out.println("User Course:\n"+userCourse.toJSON());
		}
		courseDao.addMulti(courses);
		courseInstanceDao.addMulti(courseInstances);
		userCourseDao.addMulti(userCourses);	
		
		imGroupDao.addMulti(imGroups);
		imGroupUserDao.addMulti(imGroupUsers);
		
		//所有的Group终将添加完毕，这是user 已经ready了
		user.setUserReady(true);
	}

	@Override
	public CourseInstance updateCourseInstanceInfo(Course course, CourseDto courseDto, int year, int term) {
		CourseInstance courseInstance = courseInstanceDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseAlias", courseDto.getAliasName()),Restrictions.eq("courseRemoteId", courseDto.getCourseRemoteId()),Restrictions.eq("courseName", courseDto.getCourseName())));
		if(courseInstance==null){
			courseInstance = new CourseInstance();
			courseInstance.setId(UUIDGenerator.randomUUID());
			courseInstance.setCourseAlias(courseDto.getAliasName());
			courseInstance.setCourseName(courseDto.getCourseName());
			courseInstance.setCourseRemoteId(courseDto.getCourseRemoteId());
			courseInstance.setBadEval(0);
			courseInstance.setGoodEval(0);
			courseInstance.setExamStatus(courseDto.getExamAttr());
			courseInstance.setCourseSequence(courseDto.getCourseNumber());
			courseInstance.setPostponed(courseDto.getIsDelayed());
			courseInstance.setCourse(course);
		}
		
		//如果课程还没有合班信息，远程抓取下.
		if(courseInstance.getClasses()==null||courseInstance.getClasses().equals("")){
			CourseRecordDto temp = jwRemoteService.queryRemoteCourseRecord(jwRemoteService.randomSessionId(), courseInstance.getCourseRemoteId());
			if(temp!=null){
				StringBuilder builder = new StringBuilder();
				for(String str : temp.getClasses()){
					builder.append(str+"|");
				}
				String classes = builder.toString();
				courseInstance.setClasses(classes.substring(0,classes.length()-1));
				courseInstance.setCourseMemberNum(temp.getRealCapacity());
			}
		}
		
		StringBuilder takenBuilder = new StringBuilder();
		List<CourseTakenItem> courseTakenItems = courseDto.getCourseTakenItems();
		if(courseTakenItems==null||courseTakenItems.size()==0){
			takenBuilder.append("时间地点均不占");
		}else{
			for(CourseTakenItem courseTakenItem : courseTakenItems){
				takenBuilder.append(InfoProcessHub.transferCourseTakenItem(courseTakenItem)+";");
			}
		}
		
		courseInstance.setCourseTakenInfo(takenBuilder.toString());
		courseInstance.setTeacherName(courseDto.getTeacherName());
		courseInstance.setYear(year);
		courseInstance.setTerm(term);
		
		return courseInstance;
	}

	@Override
	@Async
	public void updateExamInfo(String userId, List<ExamDto> examDtos, DjtuDate djtuDate){
		try{
			List<SystemNotice> notices = new ArrayList<SystemNotice>();
			
			for(ExamDto examDto : examDtos){
				Course course = courseDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseName", examDto.getCourseName()),Restrictions.eq("courseAlias", examDto.getCourseAliasName())));
				Exam exam = examDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("course.id", course.getId())));
				if(exam==null){
					exam = new Exam();
					exam.setCourse(course);
					exam.setCourseAlias(examDto.getCourseAliasName());
					exam.setCourseName(examDto.getCourseName());
					exam.setId(UUIDGenerator.randomUUID());
				}
				
				examDao.add(exam);
				
				
				
				
				//examDate日期，roomName考试地点，courseProperty考试性质，course的全部信息，根据这些确定一个唯一的courseInstance
				//courseAlias，examStatus，year，term
				UserCourse userCourse = userCourseDao.findOneByJoinedParams(MapMaker.instance("courseInstance", "courseInstance").toMap(),CriteriaWrapper.instance().and(Restrictions.eq("user.id", userId),Restrictions.eq("courseInstance.year", djtuDate.getSchoolYear()), Restrictions.eq("courseInstance.term", djtuDate.getTerm()),Restrictions.eq("courseInstance.examStatus", examDto.getCourseProperty()), Restrictions.eq("courseInstance.courseAlias", course.getCourseAlias())));
				userCourse.setExamNoted(true);
				CourseInstance courseInstance = userCourse.getCourseInstance();				
				ExamInstance examInstance = updateExamInstanceInfo(exam, examDto, courseInstance);
				//勿忘set上新更新的ExamInstance
				userCourse.setExamInstance(examInstance);
				userCourseDao.update(userCourse);
				
				//开始两种关联通知！第一种更新和第二种更新均需要跟此用户与此考试的科目关联的CourseInstance
				//1. 通知直接用户
				List<UserCourse> userCourses = userCourseDao.findByParams(CriteriaWrapper.instance().and(Restrictions.eq("courseInstance.id", courseInstance.getId())));
				for(UserCourse uc : userCourses){
					//uc.setExamInstance(examInstance);
					if(!uc.isExamNoted()){
						SystemNotice notice = new SystemNotice();
						notice.setId(UUIDGenerator.randomUUID());
						notice.setDate(new Date());
						notice.setToUser(uc.getUser());
						notice.setTitle("考试安排通知");
						notice.setContent(examDto.getCourseName()+"("+examDto.getCourseAliasName()+")的考试安排已经新鲜出炉,快来登陆人在交大查看考场教室,我们祝福你考一个好成绩哦~");
						notices.add(notice);
						uc.setExamNoted(true);
					}
				}
				userCourseDao.addMulti(userCourses);
				
				//2. 通知间接用户
				List<UserCourse> userCourses2 = userCourseDao.findByJoinedParams(MapMaker.instance("courseInstance", "courseInstance").toMap(),CriteriaWrapper.instance().and(Restrictions.eq("courseInstance.courseAlias", courseInstance.getCourseAlias()),Restrictions.eq("courseInstance.year", djtuDate.getSchoolYear()), Restrictions.eq("courseInstance.term", djtuDate.getTerm()),Restrictions.eq("courseInstance.examStatus", examDto.getCourseProperty()),Restrictions.ne("courseInstance.id", courseInstance.getId())));
				//List<ExamInstance> examInstances = new ArrayList<ExamInstance>();
				for(UserCourse uc : userCourses2){
					
					if(!uc.isExamNoted()){
						SystemNotice notice = new SystemNotice();
						notice.setId(UUIDGenerator.randomUUID());
						notice.setDate(new Date());
						notice.setToUser(uc.getUser());
						notice.setTitle("考试安排通知");
						notice.setContent(examDto.getCourseName()+"("+examDto.getCourseAliasName()+")的考试安排已经新鲜出炉,快来登陆人在交大查看考场教室,我们祝福你考一个好成绩哦~");
						notices.add(notice);
						uc.setExamNoted(true);
					}
					
//					ExamInstance tempInstance = uc.getExamInstance();
//					if(tempInstance==null){
//						tempInstance = new ExamInstance();
//						tempInstance.setCourseInstance(uc.getCourseInstance());
//						tempInstance.setCourseName(uc.getCourseInstance().getCourseName());
//						tempInstance.setExam(exam);
//						tempInstance.setId(UUIDGenerator.randomUUID());
//						tempInstance.setScoreOut(false);
//						tempInstance.setExamDate(exam.getExamDate());
//						tempInstance.setLastedMinutes(exam.getLastedMinutes());
//						tempInstance.setRoomName(null);
//						examInstances.add(tempInstance);
//					}else if(tempInstance.getRoomName()==null||tempInstance.getRoomName().equals("")){
//						tempInstance.setRoomName(null);
//						tempInstance.setExamDate(exam.getExamDate());
//						tempInstance.setLastedMinutes(exam.getLastedMinutes());
//						examInstances.add(tempInstance);
//					}else{
//						continue;
//					}
//					uc.setExamInstance(tempInstance);
				}
				//examInstanceDao.addMulti(examInstances);
				userCourseDao.addMulti(userCourses2);
			}
		
			systemNoticeDao.addMulti(notices);
			
		}catch(Exception exception){
			exception.printStackTrace();
			
		}
	}

	@Override
	public ExamInstance updateExamInstanceInfo(Exam exam, ExamDto examDto, CourseInstance courseInstance){
		//timeEntry是一个考试实例的时间
		ExamTimeEntry timeEntry = null;
		try {
			timeEntry = InfoProcessHub.transferExamDate(examDto.getExamDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		ExamInstance examInstance = examInstanceDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("roomName", examDto.getRoomName()),Restrictions.eq("examStatus", examDto.getCourseProperty()),Restrictions.eq("courseInstance.id", courseInstance.getId())));
		
		if(examInstance==null){
			examInstance = new ExamInstance();
			examInstance.setCourseInstance(courseInstance);
			examInstance.setCourseName(courseInstance.getCourseName());
			examInstance.setExam(exam);
			examInstance.setId(UUIDGenerator.randomUUID());
			examInstance.setScoreOut(false);
		}
		examInstance.setExamDate(timeEntry.getDate());
		examInstance.setExamStatus(examDto.getCourseProperty());
		examInstance.setLastedMinutes(timeEntry.getLastedMinutes());
		examInstance.setRoomName(examDto.getRoomName());
		examInstanceDao.add(examInstance);
		return examInstance;
	}

	
	@Override
	@Async
	public void updateScoreOutInfo(String studentId, List<ScoreDto> scoreDtos,
			DjtuDate djtuDate) {
		
		try{
			List<SystemNotice> notices = new ArrayList<SystemNotice>();
			
			for(ScoreDto scoreDto : scoreDtos){
				UserCourse userCourse = userCourseDao.findOneByJoinedParams(MapMaker.instance("courseInstance", "courseInstance").param("user", "user").toMap(),CriteriaWrapper.instance().and(Restrictions.eq("user.studentId", studentId),Restrictions.eq("courseInstance.year", djtuDate.getSchoolYear()), Restrictions.eq("courseInstance.term", djtuDate.getTerm()),Restrictions.eq("courseInstance.examStatus", scoreDto.getCourseProperty()), Restrictions.eq("courseInstance.courseAlias", scoreDto.getCourseAliasName())));
				userCourse.setScoreNoted(true);
				CourseInstance courseInstance = userCourse.getCourseInstance();
				List<UserCourse> userCourses = courseInstance.getUserCourses();
				for(UserCourse uc : userCourses){
					if(!uc.isScoreNoted()){
						SystemNotice notice = new SystemNotice();
						notice.setId(UUIDGenerator.randomUUID());
						notice.setDate(new Date());
						notice.setToUser(uc.getUser());
						notice.setTitle("考试出分通知");
						notice.setContent(scoreDto.getCourseName()+"("+scoreDto.getCourseAliasName()+")的考试分数已经新鲜出炉,快来登陆人在交大查看成绩吧~");
						notices.add(notice);
						uc.setScoreNoted(true);
					}
				}
				
				userCourseDao.addMulti(userCourses);
			}
			
			systemNoticeDao.addMulti(notices);
		}catch(Exception exception){
			exception.printStackTrace();
			
		}
		
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

	public ExamDao getExamDao() {
		return examDao;
	}

	public void setExamDao(ExamDao examDao) {
		this.examDao = examDao;
	}

	public ExamInstanceDao getExamInstanceDao() {
		return examInstanceDao;
	}

	public void setExamInstanceDao(ExamInstanceDao examInstanceDao) {
		this.examInstanceDao = examInstanceDao;
	}
	
	public JWRemoteService getJwRemoteService() {
		return jwRemoteService;
	}

	public void setJwRemoteService(JWRemoteService jwRemoteService) {
		this.jwRemoteService = jwRemoteService;
	}

	public UserCourseDao getUserCourseDao() {
		return userCourseDao;
	}

	public void setUserCourseDao(UserCourseDao userCourseDao) {
		this.userCourseDao = userCourseDao;
	}

	public SystemNoticeDao getSystemNoticeDao() {
		return systemNoticeDao;
	}

	public void setSystemNoticeDao(SystemNoticeDao systemNoticeDao) {
		this.systemNoticeDao = systemNoticeDao;
	}

	
	
	
}
