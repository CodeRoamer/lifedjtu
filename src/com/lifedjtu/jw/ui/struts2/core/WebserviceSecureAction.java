package com.lifedjtu.jw.ui.struts2.core;

import java.util.Arrays;
import java.util.List;

import com.lifedjtu.jw.business.JWLocalService;
import com.lifedjtu.jw.business.support.LocalResult;
import com.lifedjtu.jw.pojos.CourseInstance;
import com.lifedjtu.jw.pojos.User;
import com.lifedjtu.jw.pojos.dto.ExamDto;
import com.lifedjtu.jw.pojos.dto.ScoreDto;
import com.lifedjtu.jw.ui.struts2.core.support.LifeDjtuAction;
import com.lifedjtu.jw.util.LifeDjtuEnum.ResultState;
/**
 * 如果获取的集合为空，不能够说明是获取失败，有可能是无东西可以获取！！
 * @author apple
 *
 */
public class WebserviceSecureAction extends LifeDjtuAction {

	private JWLocalService jwLocalService;

	

	public String getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(String remoteId) {
		this.remoteId = remoteId;
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

	public int getSameGradeMemberNum() {
		return sameGradeMemberNum;
	}

	public void setSameGradeMemberNum(int sameGradeMemberNum) {
		this.sameGradeMemberNum = sameGradeMemberNum;
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
	private String remoteId;
	//out
	private int courseMemberNum;
	private List<String> classes;
	private int goodEval;
	private int badEval;
	private int sameCourseMemberNum;
	private int sameClassMemberNum;
	private int sameGradeMemberNum;
	
	//private List<Memo> memos;
	public String getCourseInstance(){
		LocalResult<Integer> localResult1 = jwLocalService.getSameCourseUserNum(remoteId);
		LocalResult<Integer> localResult2 = jwLocalService.getSameClassUserNum(studentId, remoteId);
		LocalResult<Integer> localResult3 = jwLocalService.getSameGradeUserNum(studentId, remoteId);
		
		LocalResult<CourseInstance> localResult4 = jwLocalService.getCourseInstance(sessionId,remoteId);
		
		sameCourseMemberNum = localResult1.getResult();
		sameClassMemberNum = localResult2.getResult();
		sameGradeMemberNum = localResult3.getResult();
		
		goodEval = localResult4.getResult().getGoodEval();
		badEval = localResult4.getResult().getBadEval();
		
		
		
		classes = Arrays.asList(localResult4.getResult().getClasses().split("\\|"));
		courseMemberNum = localResult4.getResult().getCourseMemberNum();
		
		if(localResult1.getResultState()!=ResultState.SUCCESS.ordinal()||localResult2.getResultState()!=ResultState.SUCCESS.ordinal()||localResult3.getResultState()!=ResultState.SUCCESS.ordinal()||localResult4.getResultState()!=ResultState.SUCCESS.ordinal()){
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
	//private String remoteId;
	private int pageNum = 0;
	//out
	//private List<User> memberList;
	
	public String getSameClassMembers(){
		LocalResult<List<User>> localResult = jwLocalService.getSameClassUsers(studentId, remoteId, pageNum, 10);
		
		memberList = localResult.getResult();
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/getSameCourseMembers.action
	 */
	//in
	//private String remoteId;
	//private int pageNum = 0;
	//out
	//private List<User> memberList;
	
	public String getSameCourseMembers(){
		LocalResult<List<User>> localResult = jwLocalService.getSameCourseUsers(remoteId, pageNum, 10);
		
		memberList = localResult.getResult();
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/getSameGradeMembers.action
	 */
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
	
	/**
	 * -----/webservice/secure/giveGoodEval.action
	 */
	//in
	//private String remoteId;
	//out
	
	public String giveGoodEval(){
		LocalResult<Boolean> localResult = jwLocalService.giveGoodEvalToCourse(studentId, remoteId);
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
	
	/**
	 * -----/webservice/secure/giveBadEval.action
	 */
	//in
	//private String remoteId;
	//out
	
	public String giveBadEval(){
		LocalResult<Boolean> localResult = jwLocalService.giveBadEvalToCourse(studentId, remoteId);
		
		flag = localResult.getResultState();
		
		return SUCCESS;
	}
}
