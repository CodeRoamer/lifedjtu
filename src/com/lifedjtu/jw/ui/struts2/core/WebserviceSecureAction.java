package com.lifedjtu.jw.ui.struts2.core;

import java.util.Arrays;
import java.util.List;

import com.lifedjtu.jw.business.JWLocalService;
import com.lifedjtu.jw.business.support.LocalResult;
import com.lifedjtu.jw.pojos.CourseInstance;
import com.lifedjtu.jw.pojos.FriendPending;
import com.lifedjtu.jw.pojos.User;
import com.lifedjtu.jw.pojos.dto.ExamDto;
import com.lifedjtu.jw.pojos.dto.ScoreDto;
import com.lifedjtu.jw.ui.struts2.core.support.LifeDjtuAction;
import com.lifedjtu.jw.util.LifeDjtuEnum.FriendRequestStatus;
import com.lifedjtu.jw.util.LifeDjtuEnum.ResultState;
/**
 * 如果获取的集合为空，不能够说明是获取失败，有可能是无东西可以获取！！
 * @author apple
 *
 */
public class WebserviceSecureAction extends LifeDjtuAction {

	private JWLocalService jwLocalService;
	

	public List<User> getFriendList() {
		return friendList;
	}

	public void setFriendList(List<User> friendList) {
		this.friendList = friendList;
	}

	public List<FriendPending> getPendingList() {
		return pendingList;
	}

	public void setPendingList(List<FriendPending> pendingList) {
		this.pendingList = pendingList;
	}

	public boolean isRemoveBoth() {
		return removeBoth;
	}

	public void setRemoveBoth(boolean removeBoth) {
		this.removeBoth = removeBoth;
	}

	public String getFriendStudentId() {
		return friendStudentId;
	}

	public void setFriendStudentId(String friendStudentId) {
		this.friendStudentId = friendStudentId;
	}

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	public String getRequestSourceStudentId() {
		return requestSourceStudentId;
	}

	public void setRequestSourceStudentId(String requestSourceStudentId) {
		this.requestSourceStudentId = requestSourceStudentId;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getCourseAlias() {
		return courseAlias;
	}

	public void setCourseAlias(String courseAlias) {
		this.courseAlias = courseAlias;
	}

	public String getCourseRemoteId() {
		return courseRemoteId;
	}

	public void setCourseRemoteId(String courseRemoteId) {
		this.courseRemoteId = courseRemoteId;
	}	

	public String getCourseGroupId() {
		return courseGroupId;
	}

	public void setCourseGroupId(String courseGroupId) {
		this.courseGroupId = courseGroupId;
	}

	public String getCourseInstanceGroupId() {
		return courseInstanceGroupId;
	}

	public void setCourseInstanceGroupId(String courseInstanceGroupId) {
		this.courseInstanceGroupId = courseInstanceGroupId;
	}

	public String getCourseInstanceId() {
		return courseInstanceId;
	}

	public void setCourseInstanceId(String courseInstanceId) {
		this.courseInstanceId = courseInstanceId;
	}

	public int getCourseMemberNum() {
		return courseMemberNum;
	}

	public void setCourseMemberNum(int courseMemberNum) {
		this.courseMemberNum = courseMemberNum;
	}


	public List<User> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<User> memberList) {
		this.memberList = memberList;
	}

	public List<String> getClasses() {
		return classes;
	}

	public void setClasses(List<String> classes) {
		this.classes = classes;
	}

	public int getGoodEval() {
		return goodEval;
	}

	public void setGoodEval(int goodEval) {
		this.goodEval = goodEval;
	}

	public int getBadEval() {
		return badEval;
	}

	public void setBadEval(int badEval) {
		this.badEval = badEval;
	}

	
	
	public int getSameCourseMemberNum() {
		return sameCourseMemberNum;
	}

	public void setSameCourseMemberNum(int sameCourseMemberNum) {
		this.sameCourseMemberNum = sameCourseMemberNum;
	}

	public int getSameClassMemberNum() {
		return sameClassMemberNum;
	}

	public void setSameClassMemberNum(int sameClassMemberNum) {
		this.sameClassMemberNum = sameClassMemberNum;
	}

	

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	
	public int getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(int schoolYear) {
		this.schoolYear = schoolYear;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public double getAvgMark() {
		return avgMark;
	}

	public void setAvgMark(double avgMark) {
		this.avgMark = avgMark;
	}

	public String getOriginPass() {
		return originPass;
	}

	public void setOriginPass(String originPass) {
		this.originPass = originPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getNewPassAgain() {
		return newPassAgain;
	}

	public void setNewPassAgain(String newPassAgain) {
		this.newPassAgain = newPassAgain;
	}

	public JWLocalService getJwLocalService() {
		return jwLocalService;
	}

	public void setJwLocalService(JWLocalService jwLocalService) {
		this.jwLocalService = jwLocalService;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public List<CourseInstance> getCourseDtos() {
		return courseDtos;
	}

	public void setCourseDtos(List<CourseInstance> courseDtos) {
		this.courseDtos = courseDtos;
	}

	public List<ExamDto> getExamDtos() {
		return examDtos;
	}

	public void setExamDtos(List<ExamDto> examDtos) {
		this.examDtos = examDtos;
	}

	public List<ScoreDto> getScoreDtos() {
		return scoreDtos;
	}

	public void setScoreDtos(List<ScoreDto> scoreDtos) {
		this.scoreDtos = scoreDtos;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -1144628074252245556L;

	private String sessionId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
	/**
	 * -----/webservice/secure/getUserInfo.action
	 */
	//in
	private String studentId;
	//out
	private User user;
	
	public String getUserInfo(){
		LocalResult<User> localResult = jwLocalService.fetchUserDetailInfo(studentId, sessionId);
		
		user = localResult.getResult();
		user.setUserCourses(null);
		user.setPrivateKey(null);
		user.setPassword(null);
		user.setId(null);
		user.setCurSessionDate(null);
		user.setCurSessionId(null);
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/getCourseInfo.action
	 */
	//in
	//private String studentId;
	//out
	private List<CourseInstance> courseDtos;
	
	public String getCourseInfo(){
		LocalResult<List<CourseInstance>> localResult = jwLocalService.queryLocalCourseTabel(studentId, sessionId);
		
		courseDtos = localResult.getResult();
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/getExamInfo.action
	 */
	//in
	//private String studentId;
	//out
	private List<ExamDto> examDtos;
	
	public String getExamInfo(){
		LocalResult<List<ExamDto>> localResult = jwLocalService.queryLocalExams(studentId, sessionId);
		
		examDtos = localResult.getResult();
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/getScoreInfo.action
	 */
	//in
	//private String studentId;
//	private int schoolYear;
//	private int term;
	//out
	private List<ScoreDto> scoreDtos;
	
	public String getScoreInfo(){
		LocalResult<List<ScoreDto>> localResult = jwLocalService.queryLocalScores(studentId, sessionId, schoolYear, term);
		
		scoreDtos = localResult.getResult();
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/changePassword.action
	 */
	//in
	//private String studentId;
	private String originPass;
	private String newPass;
	private String newPassAgain;
	
	//out
	
	public String changePassword(){
		//System.err.println(originPass+" "+newPass+" "+newPassAgain);
		
		LocalResult<Boolean> localResult = jwLocalService.changeLocalPassword(studentId, sessionId, originPass, newPass, newPassAgain);
				
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	
	/**
	 * -----/webservice/secure/getAverageMark.action
	 */
	//in
	//private String studentId;
	private int schoolYear = 0;
	private int term = 0;
	//out
	private double avgMark;
	
	public String getAverageMark(){
		
		LocalResult<Double> localResult = jwLocalService.queryAverageMarksByTerm(sessionId, schoolYear, term);
		
		flag = localResult.getResultState();
		
		avgMark = localResult.getResult();
		
		return SUCCESS;
		
	}
	
	
	
	/**
	 * -----/webservice/secure/getCourseInstance.action
	 */
	//in
	private String courseAlias;
	private String courseRemoteId;
	//out
	private int courseMemberNum;
	private List<String> classes;
	private int goodEval;
	private int badEval;
	private int sameCourseMemberNum;
	private int sameClassMemberNum;
	private String courseGroupId;//courseId;
	private String courseInstanceGroupId;//courseInstanceId;
	//private int sameGradeMemberNum;
	
	//private List<Memo> memos;
	public String getCourseInstance(){
		
		courseGroupId = jwLocalService.getGroupIdByCourseAlias(courseAlias);
		courseInstanceGroupId = jwLocalService.getGroupIdByCourseRemoteId(courseRemoteId);
		
		LocalResult<Integer> localResult1 = jwLocalService.getGroupUserNum(courseGroupId);
		LocalResult<Integer> localResult2 = jwLocalService.getGroupUserNum(courseInstanceGroupId);
		//LocalResult<Integer> localResult3 = jwLocalService.getSameGradeUserNum(studentId, remoteId);
		
		LocalResult<CourseInstance> localResult4 = jwLocalService.getCourseInstance(sessionId,courseRemoteId);
		
		sameCourseMemberNum = localResult1.getResult();
		sameClassMemberNum = localResult2.getResult();
		//sameGradeMemberNum = localResult3.getResult();
		
		goodEval = localResult4.getResult().getGoodEval();
		badEval = localResult4.getResult().getBadEval();
		
		
		
		classes = Arrays.asList(localResult4.getResult().getClasses().split("\\|"));
		courseMemberNum = localResult4.getResult().getCourseMemberNum();
		
		if(localResult1.getResultState()!=ResultState.SUCCESS.ordinal()||localResult2.getResultState()!=ResultState.SUCCESS.ordinal()||localResult4.getResultState()!=ResultState.SUCCESS.ordinal()){
			flag = ResultState.FAIL.ordinal();
		}else{
			flag = ResultState.SUCCESS.ordinal();
		}
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/getSameClassMembers.action
	 */
	//in
	private String bindId; //应该为courseInstanceId
	private int pageNum = 0;
	//out
	private List<User> memberList;
	
	public String getSameClassMembers(){
		LocalResult<List<User>> localResult = jwLocalService.getSameClassUsers(bindId, pageNum, 10);
		
		memberList = localResult.getResult();
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/getSameCourseMembers.action
	 */
	//in
	//private String bindId; //应该为courseId
	//private int pageNum = 0;
	//out
	//private List<User> memberList;
	
	public String getSameCourseMembers(){
		LocalResult<List<User>> localResult = jwLocalService.getSameCourseUsers(bindId, pageNum, 10);
		
		memberList = localResult.getResult();
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/getSameGradeMembers.action
	 */
	/*
	//in
	//private String remoteId;
	//private int pageNum = 0;
	//out
	private List<User> memberList;
	
	public String getSameGradeMembers(){
		LocalResult<List<User>> localResult = jwLocalService.getSameGradeUsers(studentId,remoteId, pageNum, 10);
		
		memberList = localResult.getResult();
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	*/
	/**
	 * -----/webservice/secure/giveGoodEval.action
	 */
	//in
	private String courseInstanceId;
	//out
	
	public String giveGoodEval(){
		LocalResult<Boolean> localResult = jwLocalService.giveGoodEvalToCourse(studentId, courseInstanceId);
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/giveBadEval.action
	 */
	//in
	//private String courseInstanceId;
	//out
	
	public String giveBadEval(){
		LocalResult<Boolean> localResult = jwLocalService.giveBadEvalToCourse(studentId, courseInstanceId);
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	
	/**
	 * -----/webservice/secure/addFriend.action
	 */
	//in
	private String friendStudentId;
	private String requestContent;
	//out
	
	public String addFriend(){
		LocalResult<Boolean> localResult = jwLocalService.addFriend(studentId, friendStudentId, requestContent);
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	

	/**
	 * -----/webservice/secure/answerFriendRequest.action
	 */
	//in
	private String requestSourceStudentId;
	private int answer;
	//out
	
	public String answerFriendRequest(){
		LocalResult<Boolean> localResult = jwLocalService.answerFriendRequest(studentId, requestSourceStudentId, FriendRequestStatus.valueOf(answer));
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/getAllFriends.action
	 */
	//in
	//out
	private List<User> friendList;
	
	public String getAllFriends(){
		LocalResult<List<User>> localResult = jwLocalService.getFriendList(studentId);
		
		friendList = localResult.getResult();
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/getAllFriendPendingRequests.action
	 */
	//in
	//out
	private List<FriendPending> pendingList;
	
	public String getAllFriendPendingRequests(){
		LocalResult<List<FriendPending>> localResult = jwLocalService.getFriendPendingList(studentId);
		
		pendingList = localResult.getResult();
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	
	/**
	 * -----/webservice/secure/viewAllFriendPendingRequests.action
	 */
	//in
	//out
	//private List<FriendPending> pendingList;
	
	public String viewAllFriendPendingRequests(){
		LocalResult<List<FriendPending>> localResult = jwLocalService.viewFriendRequestResultList(studentId);
		
		pendingList = localResult.getResult();
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/removeFriend.action
	 */
	//in
	//private String friendStudentId;
	private boolean removeBoth;
	//out
	
	public String removeFriend(){
		LocalResult<Boolean> localResult = jwLocalService.removeFriend(studentId, friendStudentId, removeBoth);
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/getGroupMembers.action
	 */
	//in
	//private String bindId; //应该为groupId
	//private int pageNum = 0;
	//out
	//private List<User> memberList;
	
	public String getGroupMembers(){
		LocalResult<List<User>> localResult = jwLocalService.getGroupUserList(bindId, pageNum, 10);
		
		memberList = localResult.getResult();
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
}
