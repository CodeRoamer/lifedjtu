package com.lifedjtu.jw.pojos;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

public class EntityObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8495226126373394030L;
	public static final String GETTER_PREFIX = "get";
	public static final String IS_PREFIX = "is";

	
	
	public JSONObject toJSON(){
		try{
			Method[] methods = getClass().getDeclaredMethods();
			ArrayList<Method> methodList = new ArrayList<Method>();
			ArrayList<String> fieldNameList = new ArrayList<String>();
			for(Method method : methods){
				if(method.getName().startsWith(GETTER_PREFIX)){
					//System.out.println(method.getName());
					methodList.add(method);
					String fieldName = Character.toLowerCase(method.getName().charAt(GETTER_PREFIX.length()))+method.getName().substring(GETTER_PREFIX.length()+1);
					fieldNameList.add(fieldName);
				}else if(method.getName().startsWith(IS_PREFIX)){
					methodList.add(method);
					String fieldName = Character.toLowerCase(method.getName().charAt(IS_PREFIX.length()))+method.getName().substring(IS_PREFIX.length()+1);
					fieldNameList.add(fieldName);
				}
			}
			
			JSONObject json = new JSONObject();
			
			for(int i = 0; i < methodList.size(); i++){
				Object object = methodList.get(i).invoke(this, (Object[])null);
				if(object instanceof EntityObject){
					object = ((EntityObject)object).toJSON();
				}else if(object instanceof Date){
					object = ((Date)object).getTime();
				}
				
				json.put(fieldNameList.get(i), object);
			}
			
			return json;
			
		}catch(Exception exception){
			//System.err.println("Error Occurs!");
			exception.printStackTrace();
			return null;
		}
	}
}
