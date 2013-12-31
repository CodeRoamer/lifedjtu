package com.lifedjtu.jw.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lifedjtu.jw.business.JWLocalService;
import com.lifedjtu.jw.business.JWRemoteService;
import com.lifedjtu.jw.business.support.LocalResult;
import com.lifedjtu.jw.dao.impl.UserDao;
import com.lifedjtu.jw.pojos.RoomTakenItem;
import com.lifedjtu.jw.pojos.User;
import com.lifedjtu.jw.pojos.dto.BuildingDto;
import com.lifedjtu.jw.pojos.dto.CourseDto;
import com.lifedjtu.jw.pojos.dto.ExamDto;
import com.lifedjtu.jw.pojos.dto.RoomDto;
import com.lifedjtu.jw.pojos.dto.ScoreDto;
import com.lifedjtu.jw.pojos.dto.StudentRegistry;
import com.lifedjtu.jw.util.Crypto;

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
		if(userDao.findOneById(studentId) != null) return true;
		return false;
	}

	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		String sessionId = jwRemoteService.signinRemote(user.getUsername(), user.getPassword());
		if(sessionId != null){
			user.setCurSessionId(sessionId);
			try {
				user.setPassword(Crypto.encodeAES(user.getPassword()));
				user.setPrivateKey(Crypto.randomPrivateKey());
				userDao.add(user);
				return user;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
		
	}

	@Override
	public User signinLocal(String studentId, String password) {
		// TODO Auto-generated method stub	
		try {
			User user = userDao.findOneById(studentId);
			if(user.getPassword().equals(Crypto.encodeAES(password)));
			String sessionId = jwRemoteService.signinRemote(user.getUsername(), user.getPassword());
			user.setCurSessionId(sessionId);
			user.setPrivateKey(Crypto.randomPrivateKey());
			return user;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int changeLocalPassword(String studentId, String dynamicPass,
			String originPass, String newPass, String newPassAgain) {
		// TODO Auto-generated method stub
		try{
			User user = userDao.findOneById(studentId);
			if(dynamicPass.equals(Crypto.cypherDynamicPassword(user.getPrivateKey(), 123))){
				if(jwRemoteService.changeRemotePassword(user.getCurSessionId(), originPass, newPass, newPassAgain)){
					user.setPassword(Crypto.encodeAES(newPass));
					userDao.update(user);
					return 2;
				}
				return 1;
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
;
	@Override
	public LocalResult<List<RoomTakenItem>> queryFreeRooms(String studentId,
			String dynamicPass, double longitude, double latitude) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalResult<List<CourseDto>> queryLocalCourseTabel(String studentId,
			String dynamicPass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalResult<List<ExamDto>> queryLocalExams(String studentId, String dynamicPass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalResult<User> fetchUserDetailInfo(String studentId, String dynamicPass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int fetchUserCourseInfo(String studentId, String dynamicPass) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int fetchUserExamInfo(String studentId, String dynamicPass) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LocalResult<StudentRegistry> fetchStudentRegistry(String studentId,
			String dynamicPass) {
		// TODO Auto-generated method stub
		LocalResult<StudentRegistry> localResult = new LocalResult<StudentRegistry>();
		User user = userDao.findOneById(studentId);
		if(dynamicPass.equals(Crypto.cypherDynamicPassword(user.getPrivateKey(), 123))){
			StudentRegistry studentRegistry = jwRemoteService.fetchStudentRegistry(user.getCurSessionId());
			if(studentRegistry != null){
				localResult.setResult(studentRegistry);
				localResult.setResultState(2);
				return localResult;
			}
			else{
				localResult.setResultState(1);
				return localResult;
			}
		}
		else{
			
			localResult.setResultState(0);
			return localResult;
		}
	}

	@Override
	public LocalResult<BuildingDto> queryBuildingOnDate(String studentId,
			String dynamicPass, int aid, int buildingId, int week, int weekday) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalResult<RoomDto> queryRoom(String studentId, String dynamicPass, int aid,
			int buildingId, int roomId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalResult<List<ScoreDto>> queryLocalScores(String studentId, String dynamicPass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkPrivateKey(String studentId, String dynamicPass) {
		// TODO Auto-generated method stub
		User user = userDao.findOneById(studentId);
		if(dynamicPass.equals(Crypto.cypherDynamicPassword(user.getPrivateKey(), 123))) return true;
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
