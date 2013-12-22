package com.lifedjtu.jw.business.impl;


import org.junit.Test;

public class TestServiceImpl {
	
	
	@Test
	public void testFetchStudentRegistry(){
		JWRemoteServiceImpl jwRemoteServiceImpl = new JWRemoteServiceImpl();
		String sessionId = jwRemoteServiceImpl.signinRemote("1018110201", "357159");
		System.out.println(jwRemoteServiceImpl.fetchStudentRegistry(sessionId));
	}
	
	@Test
	public void testQueryRoom(){
		JWRemoteServiceImpl jwRemoteServiceImpl = new JWRemoteServiceImpl();
		String sessionId = jwRemoteServiceImpl.signinRemote("1018110201", "357159");
		System.out.println(jwRemoteServiceImpl.queryRoom(sessionId, 1, 80, 89));
	}
	
	@Test
	public void testQueryRemoteCourseTable(){
		JWRemoteServiceImpl jwRemoteServiceImpl = new JWRemoteServiceImpl();
		String sessionId = jwRemoteServiceImpl.signinRemote("1018110201", "357159");
		System.out.println(jwRemoteServiceImpl.queryRemoteCourseTable(sessionId));
	}
	
	@Test
	public void testQueryRemoteExams(){
		JWRemoteServiceImpl jwRemoteServiceImpl = new JWRemoteServiceImpl();
		String sessionId = jwRemoteServiceImpl.signinRemote("1018110201", "357159");
		System.out.println(jwRemoteServiceImpl.queryRemoteExams(sessionId));
	}
	
	@Test
	public void testQueryRemoteScores(){
		JWRemoteServiceImpl jwRemoteServiceImpl = new JWRemoteServiceImpl();
		String sessionId = jwRemoteServiceImpl.signinRemote("1018110201", "357159");
		System.out.println(jwRemoteServiceImpl.queryRemoteScores(sessionId));
	}
	
	@Test
	public void testQueryBuildingOnDate(){
		JWRemoteServiceImpl jwRemoteServiceImpl = new JWRemoteServiceImpl();
		String sessionId = jwRemoteServiceImpl.signinRemote("1018110201", "357159");
		System.out.println(jwRemoteServiceImpl.queryBuildingOnDate(sessionId, 1, 80, 3, 1));
	}
}
