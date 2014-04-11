package com.lifedjtu.jw.ui.struts2.result;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import com.lifedjtu.jw.pojos.EntityObject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * Struts2的标准Result自定义类，用于提供JSON形式的result type
 * @author apple
 *
 */
public class JsonResult implements Result {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3508855947023735656L;

	public static final String DEFAULT_PARAM = "included";

	// Define the param of the result
	String included; 

	public String getIncluded() {
		return included;
	}

	public void setIncluded(String included) {
		this.included = included;
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
	
	public void execute(ActionInvocation invocation) throws Exception {
		//为了jsonp，扩展当前的jsonResult
		String callBack = ServletActionContext.getRequest().getParameter("callback");
		boolean jsonpFlag = false;
		if(callBack!=null&&!"".equals(callBack)){
			ServletActionContext.getResponse().setContentType("text/javascript");
			jsonpFlag = true;
		}else{
			ServletActionContext.getResponse().setContentType("application/json");
		}
		
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");

		PrintWriter responseStream = ServletActionContext.getResponse()
				.getWriter();

		ValueStack valueStack = invocation.getStack();

		JSONObject json = new JSONObject();

		String[] included = this.included.split(",");

		if (included != null && included.length != 0) {
			// 指定了included参数
			for (String name : included) {
				
				Object value = valueStack.findValue(name);
				
				json.put(name, getJsonObject(value));
			}			
		} 

		if(jsonpFlag){
			responseStream.println(callBack+"("+json.toString()+");");
		}else{
			responseStream.println(json.toString());
		}
		
	}
}
