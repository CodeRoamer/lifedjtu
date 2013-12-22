package com.lifedjtu.jw.business.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.stereotype.Component;

import com.lifedjtu.jw.business.TestService;
import com.lifedjtu.jw.util.extractor.DomElement;


public class TestServiceImpl {
	public static void main(String args[]){
		JWRemoteServiceImpl jwRemoteServiceImpl = new JWRemoteServiceImpl();
		
		//System.out.println(jwRemoteServiceImpl.signinRemote("1018110201", "357159"));
		
		//System.out.println(jwRemoteServiceImpl.fetchStudentRegistry("883F27E687B490A034A8E51A00C5D4B5"));
		//System.out.println(jwRemoteServiceImpl.changeRemotePassword("883F27E687B490A034A8E51A00C5D4B5", "357159", "357159", "357159"));
	}
	
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
		System.out.println(jwRemoteServiceImpl.queryRemoteCourseTable(sessionId).toString());
	}
	
	@Test
	public void testQueryRemoteExams(){
		JWRemoteServiceImpl jwRemoteServiceImpl = new JWRemoteServiceImpl();
		String sessionId = jwRemoteServiceImpl.signinRemote("1018110201", "357159");
		System.out.println(jwRemoteServiceImpl.queryRemoteExams(sessionId).toString());
	}
	
	@Test
	public void testQueryRemoteScores(){
		JWRemoteServiceImpl jwRemoteServiceImpl = new JWRemoteServiceImpl();
		String sessionId = jwRemoteServiceImpl.signinRemote("1018110201", "357159");
		System.out.println(jwRemoteServiceImpl.queryRemoteScores(sessionId).toString());
	}
	
	@Test
	public void testQueryBuildingOnDate(){
		JWRemoteServiceImpl jwRemoteServiceImpl = new JWRemoteServiceImpl();
		String sessionId = jwRemoteServiceImpl.signinRemote("1018110201", "357159");
		System.out.println(jwRemoteServiceImpl.queryBuildingOnDate(sessionId, 1, 80, 3, 1).toString());
	}
}
