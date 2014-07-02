package com.lifedjtu.jw.business.impl;

import static com.lifedjtu.jw.util.extractor.Extractor.$;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lifedjtu.jw.business.JWRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lifedjtu.jw.business.JWRemotePatchService;
import com.lifedjtu.jw.pojos.dto.EvaEntry;
import com.lifedjtu.jw.pojos.dto.EvaList;
import com.lifedjtu.jw.util.fetcher.support.FetchResponse;
import com.lifedjtu.jw.util.fetcher.URLFetcher;
import com.lifedjtu.jw.util.extractor.DomElement;

@Component("jwRemotePatchService")
public class JWRemotePatchServiceImpl implements JWRemotePatchService{

    @Autowired
    private JWRemoteService jwRemoteService;

	public String tempSignin(String studentId, String password){

        return jwRemoteService.signinRemote(studentId,password);

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
			
			URLFetcher.fetchURLByPost(baseURL+postAffix, sessionId, params);
		}
		
		return true;
	}

	@Override
	public EvaList getEvaList(String sessionId) {
		String baseURL = "http://202.199.128.21/academic/eva/index/";
		String listAffix = "resultlist.jsdo";
		
		FetchResponse listResponse = URLFetcher.fetchURLByGet(baseURL+listAffix, sessionId);
		
		List<DomElement> list;
		if(listResponse.getStatusCode()==200){			
			list = $("tr[class=infolist_common] > td:odd",listResponse.getResponseBody());
		}else{
			list = new ArrayList<DomElement>();
		}

		//List<DomElement> list = $("tr[class=infolist_common] > td:odd",LifeDJTUUtil.fakeEvaList());
		
		EvaList evaList = new EvaList();
		
		Iterator<DomElement> iterator = list.iterator();
		while(iterator.hasNext()){
			DomElement one = iterator.next();
			if(!iterator.hasNext()){
				break;
			}
			DomElement two = iterator.next();
			List<DomElement> aElements = two.find("a");
			if(aElements==null||aElements.size()==0){
				continue;
			}
			
			EvaEntry evaEntry = new EvaEntry();
			evaEntry.setCourseName(one.getText().trim());
			evaEntry.setCourseHref(baseURL+aElements.get(0).attr("href").substring(2));
			evaEntry.setCourseStatus(0);
			
			evaList.addEntry(evaEntry);
		}
		
		return evaList;
	}

	@Override
	public boolean evaluateCourse(String session, String courseHref) {
		String baseURL = "http://202.199.128.21/academic/eva/index/";
		String postAffix = "putresult.jsdo";
		
		
		FetchResponse itemResponse = URLFetcher.fetchURLByGet(courseHref, session);
		
		List<DomElement> innerList = $("form[name=form1] input",itemResponse.getResponseBody());
		//List<DomElement> innerList = $("form[name=form1] input",LifeDJTUUtil.fakeEvalItem());

		
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
		//System.out.println(params.toString());
		
		
		FetchResponse postResponse = URLFetcher.fetchURLByPost(baseURL+postAffix, session, params);

        //System.out.println(postResponse.getStatusCode()+":\n"+postResponse.getResponseBody());

        return postResponse.getStatusCode() == 200 || postResponse.getStatusCode() == 302;
	}

}
