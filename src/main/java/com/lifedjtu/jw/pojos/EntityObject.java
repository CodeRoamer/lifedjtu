package com.lifedjtu.jw.pojos;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lifedjtu.jw.util.LifeDjtuUtil;



/**
 * 可以作为实体的公共父类，附加了很多便捷方法
 * @author Li He
 * 
 */
public class EntityObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8495226126373394030L;
	public static final String GETTER_PREFIX = "get";
	public static final String SETTER_PREFIX = "set";
	public static final String IS_PREFIX = "is";

	/**
	 * 根据字段名设置一个字段值， String to EntityObject，只支持Object
	 * @param fieldName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean setField(String fieldName, String value){
		try{
			MethodPair pair = getMethodPairByField(fieldName);
			if(pair==null){
				List<Field> fields = getEntityObjectFields();
				if(fields==null||fields.size()==0){
					return false;
				}else{
					for(Field field : fields){
						Class fieldCls = field.getType();
						MethodPair innerPair = getMethodPairByField(field.getName());
						Object fieldValue = innerPair.getGetterMethod().invoke(this, (Object[])null);
						if(fieldValue == null){
							fieldValue = fieldCls.newInstance();
						}
						boolean result = ((EntityObject)fieldValue).setField(fieldName, value);
						if(result){
							innerPair.getSetterMethod().invoke(this, fieldValue);	
							return true;
						}else{
							continue;
						}
					}
				}
				return false;
			}
			Method setter = pair.getSetterMethod();
			
			Field field = getClass().getDeclaredField(fieldName);
			Class fieldCls = field.getType();
			String typeName = fieldCls.getSimpleName();
			
			//鍩烘湰绫诲瀷, int(Integer) double(Double) long(Long) boolean(Boolean) short(Short)  byte(Byte)
			if(typeName.equals("int")){   
				setter.invoke(this, Integer.valueOf(value));
			}else if(typeName.equals("double")){
				setter.invoke(this, Double.valueOf(value));
			}else if(typeName.equals("boolean")){
				setter.invoke(this, Boolean.valueOf(value));
			}else if(typeName.equals("List")){
				setter.invoke(this, value);
			}else{
				Object temp = fieldCls.newInstance();

				if(temp instanceof Date){
					setter.invoke(this, LifeDjtuUtil.parseDate(value));
				}else if(temp instanceof String){
					setter.invoke(this, value);
				}else{
					return false;
				}
			}
			
			return true;
		}catch(Exception exception){
			exception.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取EntityObject子类的字段，其他字段忽略
	 * @return
	 */
	private List<Field> getEntityObjectFields(){
		Field[] fields = getClass().getDeclaredFields();
		List<Field> list = new ArrayList<Field>();
		for(Field field : fields){
			@SuppressWarnings("rawtypes")
			Class cls = field.getType();
			try{
				Object object = cls.newInstance();
				if(object instanceof EntityObject){
					list.add(field);
				}
			}catch(Exception exception){
				//exception.printStackTrace();
				continue;
			}
		}
		return list;
		
	}
	
	/**
	 * 根据field名称获取一个MethodPair对象
	 * @param fieldName
	 * @return
	 */
	private MethodPair getMethodPairByField(String fieldName){
		try{
			Field field = getClass().getDeclaredField(fieldName);
			Method[] methods = getClass().getDeclaredMethods();
			String methodAffix = field.getName().toUpperCase().charAt(0)+field.getName().substring(1); //将字段名转换为方法名的后缀
			Method setter = null;
			Method getter = null;
			boolean hasSetter = false;
			boolean hasGetter = false;
			for(Method method : methods){
				Pattern pattern = Pattern.compile("(get|set)"+methodAffix);
				Matcher matcher = pattern.matcher(method.getName());
				if(matcher.find()){
					if(matcher.group(1).equals(GETTER_PREFIX)){
						hasGetter = true;
						getter = method;
						if(hasSetter){
							break;
						}
					}else if(matcher.group(1).equals(SETTER_PREFIX)){
						hasSetter = true;
						setter = method;
						if(hasGetter){
							break;
						}
					}
				}
			}
			
			if(setter == null || getter == null){
				return null;
			}else{
				MethodPair pair = new MethodPair();
				pair.setSetterMethod(setter);
				pair.setGetterMethod(getter);
				return pair;
			}
		}catch(Exception exception){
			return null;
		}
	}
	
	
	
	
	/**
	 * 这个inner class保存一对getter与setter method
	 * @author Li He
	 *
	 */
	private class MethodPair {
		private Method setterMethod;
		private Method getterMethod;
		public Method getSetterMethod() {
			return setterMethod;
		}
		public void setSetterMethod(Method setterMethod) {
			this.setterMethod = setterMethod;
		}
		public Method getGetterMethod() {
			return getterMethod;
		}
		public void setGetterMethod(Method getterMethod) {
			this.getterMethod = getterMethod;
		}
		
		
	}
	
	/**
	 * 生成JSONObject，可以序列化为字符串
	 * @return JSONObject
	 */
	public JSONObject toJSON(){
		try{
			Method[] methods = getClass().getDeclaredMethods(); //获取声明的全部methods
						
			JSONObject json = new JSONObject();
			
			for(Method method : methods){
				String fieldName = null;
				
				/*
				 * 判断是否为getter method，如果为getter method，赋值fieldName
				 */
				if(method.getName().startsWith(GETTER_PREFIX)){
					fieldName = Character.toLowerCase(method.getName().charAt(GETTER_PREFIX.length()))+method.getName().substring(GETTER_PREFIX.length()+1);					
				}else if(method.getName().startsWith(IS_PREFIX)){
					fieldName = Character.toLowerCase(method.getName().charAt(IS_PREFIX.length()))+method.getName().substring(IS_PREFIX.length()+1);					
				}
				
				/*
				 * fieldName不为空，此方法为getter method
				 */
				if(fieldName!=null){
					Field field = getClass().getDeclaredField(fieldName);  //获取Field对象
					boolean ormField = false; //是否为orm框架关联注释字段
					//必须排除@ManyToOne @ManyToMany @OneToMany的情况
					Annotation[] annotations = field.getDeclaredAnnotations();
					for(Annotation annotation : annotations){
						String annotationName = annotation.annotationType().getSimpleName();
						if(annotationName.equals("ManyToOne")||annotationName.equals("OneToMany")||annotationName.equals("ManyToMany")){
							ormField = true;
							break;
						}
					}
					
					if(!ormField){
						Object object = method.invoke(this, (Object[])null);
						
						json.put(fieldName, getJsonObject(object));
					}
					
				}				
			}
			
			return json;
			
		}catch(Exception exception){
			//System.err.println("Error Occurs!");
			exception.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 递归获取可以put到JSONObject中的对象
	 * @param object
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Object getJsonObject(Object object){
		
		if(object==null){
			return null;
		}
		
		if(object instanceof EntityObject){ //字段对象为EntityObject
			object = ((EntityObject)object).toJSON();
		}else if(object instanceof Date){  //字段对象为Date
			object = ((Date)object).getTime();
		}else if(object instanceof Map){
			JSONObject jsonObject = new JSONObject();
			for(Object obj : ((Map)object).entrySet()){
				Entry entry = (Entry)obj;
				//如果为map，必须保证key为String
				jsonObject.put(entry.getKey().toString(), getJsonObject(entry.getValue()));
			}
			
			object = jsonObject;
			
		}else if(object instanceof Collection){
			JSONArray jsonArray = new JSONArray();
			
			for(Object obj : (Collection)object){
				jsonArray.put(getJsonObject(obj));
			}
			
			object = jsonArray;
		}
		
		return object;
	}
	
}
