package com.lifedjtu.jw.business.impl;

import static com.lifedjtu.jw.util.extractor.Extractor.$;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lifedjtu.jw.business.JWRemotePatchService;
import com.lifedjtu.jw.util.FetchResponse;
import com.lifedjtu.jw.util.MapMaker;
import com.lifedjtu.jw.util.URLFetcher;
import com.lifedjtu.jw.util.extractor.DomElement;

@Component("jwRemotePatchService")
public class JWRemotePatchServiceImpl implements JWRemotePatchService{

	public String tempSignin(String studentId, String password){
		String signinURL = "http://202.199.128.21/academic/j_acegi_security_check";
		String headerURL = "http://202.199.128.21/academic/showHeader.do";
		FetchResponse response = URLFetcher.fetchURLByPost(signinURL, null, MapMaker.instance("j_username", studentId).param("j_password", password).toMap());
		String sessionId = response.getSessionId();		
		FetchResponse headerResponse = URLFetcher.fetchURLByGet(headerURL, sessionId);
		if(headerResponse.getStatusCode()!=200){
			return null;
		}else{
			return sessionId;
		}
	}
	
	@Override
	public boolean evaluateAllCourses(String sessionId) {
		String baseURL = "http://202.199.128.21/academic/eva/index/";
		String listAffix = "resultlist.jsdo";
		String postAffix = "putresult.jsdo";
		String itemAffix = "";
		
		FetchResponse listResponse = URLFetcher.fetchURLByGet(baseURL+listAffix, sessionId);
		List<DomElement> list = $("a[class=infolist]:contains(评估)",listResponse.getResponseBody());

		for(DomElement domElement : list){
			System.err.println(domElement.getTag());
			itemAffix = domElement.attr("href").substring(2);
			FetchResponse itemResponse = URLFetcher.fetchURLByGet(baseURL+itemAffix, sessionId);
			
			List<DomElement> innerList = $("form[name=form1] input",itemResponse.getResponseBody());

			
			Map<String, String> params = new HashMap<String, String>();
			for(DomElement innerDom : innerList){
				String name = innerDom.attr("name");
				String value = innerDom.val();
				if(!name.equals("itemid")&&name.startsWith("itemid"))
				{
					if(value.startsWith("100")){
						params.put(name, value);
					}
				}else{
					params.put(name, value);
				}
			}
			System.out.println(params.toString());
			
			//URLFetcher.fetchURLByPost(baseURL+postAffix, sessionId, params);
		}
		
		return true;
	}

}
