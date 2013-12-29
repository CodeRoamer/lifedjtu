package com.lifedjtu.jw.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lifedjtu.jw.business.JWLocalService;
import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.dao.impl.UserDao;
import com.lifedjtu.jw.pojos.RoomTakenItem;
import com.lifedjtu.jw.pojos.User;
import com.lifedjtu.jw.pojos.dto.BuildingDto;
import com.lifedjtu.jw.pojos.dto.CourseDto;
import com.lifedjtu.jw.pojos.dto.ExamDto;
import com.lifedjtu.jw.pojos.dto.RoomDto;
import com.lifedjtu.jw.pojos.dto.ScoreDto;
import com.lifedjtu.jw.pojos.dto.StudentRegistry;

@Component("jwLocalService")
@Transactional
public class JWLocalServiceImpl implements JWLocalService{
	@Autowired
	private JWRemoteService jwRemoteService;
	@Autowired
	private UserDao userDao;
	
	

	@Override
	public boolean isUserExist(String studentId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User signinLocal(String studentId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeLocalPassword(String studentId, String dynamicPass,
			String originPass, String newPass, String newPassAgain) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RoomTakenItem> queryFreeRooms(String studentId,
			String dynamicPass, double longitude, double latitude) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CourseDto> queryLocalCourseTabel(String studentId,
			String dynamicPass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExamDto> queryLocalExams(String studentId, String dynamicPass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User fetchUserDetailInfo(String studentId, String dynamicPass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean fetchUserCourseInfo(String studentId, String dynamicPass) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fetchUserExamInfo(String studentId, String dynamicPass) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StudentRegistry fetchStudentRegistry(String studentId,
			String dynamicPass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildingDto queryBuildingOnDate(String studentId,
			String dynamicPass, int aid, int buildingId, int week, int weekday) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoomDto queryRoom(String studentId, String dynamicPass, int aid,
			int buildingId, int roomId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScoreDto> queryLocalScores(String studentId, String dynamicPass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkPrivateKey(String studentId, String dynamicPass) {
		// TODO Auto-generated method stub
		return false;
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

	
	
}
