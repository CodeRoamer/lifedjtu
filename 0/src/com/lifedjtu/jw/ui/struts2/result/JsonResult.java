package com.lifedjtu.jw.ui.struts2.result;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import com.lifedjtu.jw.pojos.EntityObject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;

public class JsonResult implements Result {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3508855947023735656L;

	public static final String DEFAULT_PARAM = "included";

	// Define the param of the result
	String included; // 如果你指定了included，excluded会被忽略

	public String getIncluded() {
		return included;
	}

	public void setIncluded(String included) {
		this.included = included;
	}

	@SuppressWarnings("rawtypes")
	public void execute(ActionInvocation invocation) throws Exception {
		ServletActionContext.getResponse().setContentType("application/json");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");

		PrintWriter responseStream = ServletActionContext.getResponse()
				.getWriter();

		ValueStack valueStack = invocation.getStack();

		JSONObject json = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();

		String[] included = this.included.split(",");

		if (included == null || included.length == 0) {

		} else {
			// 指定了included参数
			for (String name : included) {
				map.put(name.trim(), valueStack.findValue(name));
			}
		}

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() instanceof EntityObject) {
				json.put(entry.getKey(),
						((EntityObject) entry.getValue()).toJSON());
			} else if (entry.getValue() instanceof Date) {
				json.put(entry.getKey(), ((Date) entry.getValue()).getTime());
			} else if (entry.getValue() instanceof List) {
				Object elem = ((List) entry.getValue()).iterator().next();
				if (elem instanceof EntityObject) {
					JSONArray array = new JSONArray();

					for (Object object : ((List) entry.getValue())) {
						array.put(((EntityObject) object).toJSON());
					}

					json.put(entry.getKey(), array);
				} else if (elem instanceof Date) {

					JSONArray array = new JSONArray();

					for (Object object : ((List) entry.getValue())) {
						array.put(((Date) object).getTime());
					}

					json.put(entry.getKey(), array);
				} else {
					json.put(entry.getKey(), entry.getValue());
				}
			} else if (entry.getValue() instanceof Set) {
				Object elem = ((Set) entry.getValue()).iterator().next();
				if (elem instanceof EntityObject) {
					JSONArray array = new JSONArray();

					for (Object object : ((Set) entry.getValue())) {
						array.put(((EntityObject) object).toJSON());
					}

					json.put(entry.getKey(), array);
				} else if (elem instanceof Date) {

					JSONArray array = new JSONArray();

					for (Object object : ((Set) entry.getValue())) {
						array.put(((Date) object).getTime());
					}

					json.put(entry.getKey(), array);
				} else {
					json.put(entry.getKey(), entry.getValue());
				}
			} else if (entry.getValue() instanceof Map) {
				Map.Entry elem = (Map.Entry) ((Map) entry.getValue())
						.entrySet().iterator().next();
				if (elem.getValue() instanceof EntityObject) {
					JSONObject jsonObject = new JSONObject();

					for (Object object : ((Map) entry.getValue()).entrySet()) {
						Map.Entry innerEntry = (Map.Entry) object;
						jsonObject
								.put(innerEntry.getKey().toString(),
										((EntityObject) innerEntry.getValue())
												.toJSON());
					}

					json.put(entry.getKey(), jsonObject);
				} else if (elem instanceof Date) {
					JSONObject jsonObject = new JSONObject();

					for (Object object : ((Map) entry.getValue()).entrySet()) {
						Map.Entry innerEntry = (Map.Entry) object;
						jsonObject
								.put(innerEntry.getKey().toString(),
										((Date) innerEntry.getValue())
												.getTime());
					}

					json.put(entry.getKey(), jsonObject);
				} else {
					json.put(entry.getKey(), entry.getValue());
				}
			} else {
				json.put(entry.getKey(), entry.getValue());
			}
		}

		responseStream.println(json.toString());
	}
}
