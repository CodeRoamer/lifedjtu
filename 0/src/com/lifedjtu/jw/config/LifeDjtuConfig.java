package com.lifedjtu.jw.config;

import java.io.IOException;
import java.util.Properties;

public class LifeDjtuConfig {
	private static Properties props;
	static{
		props = new Properties();
		try {
			props.load(LifeDjtuConfig.class.getResourceAsStream("/lifedjtu.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String name){
		return (String)props.get(name);
	}
	
	public static boolean getBooleanProperty(String name){
		return Boolean.valueOf((String)props.get(name));
	}
	
	public static int getIntegerProperty(String name){
		return Integer.valueOf((String)props.get(name));
	}
	
	public static double getDoubleProperty(String name){
		return Double.valueOf((String)props.get(name));
	}
	
	public static void main(String[] args){
		System.out.println(getProperty("sessionIdKey"));
	}
}
