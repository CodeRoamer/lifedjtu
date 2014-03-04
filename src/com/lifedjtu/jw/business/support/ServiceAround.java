package com.lifedjtu.jw.business.support;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component("serviceAround")
public class ServiceAround {
			
	public boolean booleanAroundMethod(ProceedingJoinPoint point){
		try{
			//logger.entry(point.getArgs());
			System.err.println("In "+point.getTarget().getClass().getName()+" "+point.getSignature().getName());
			return (Boolean)point.proceed(point.getArgs());
		}catch(Throwable exception){
			exception.printStackTrace();
			return false;
		}
	}
	
	public Object objectAroundMethod(ProceedingJoinPoint point){
		try{
			//logger.entry(point.getArgs());
			System.err.println("In "+point.getTarget().getClass().getName()+" "+point.getSignature().getName());
			return point.proceed(point.getArgs());
		}catch(Throwable exception){
			//logger.catching(exception);
			exception.printStackTrace();
			return null;
		}
	}
}
