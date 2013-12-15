package com.lifedjtu.jw.util;

import static com.lifedjtu.jw.util.extractor.Extractor.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baidu.bae.api.factory.BaeFactory;
import com.baidu.bae.api.fetchurl.BaeFetchurl;
import com.baidu.bae.api.fetchurl.BasicNameValuePair;
import com.baidu.bae.api.fetchurl.NameValuePair;
import com.lifedjtu.jw.config.LifeDjtuConfig;

public class URLFetcher {	
	public static FetchResponse fetchURLByGet(String url, String sessionId){
		FetchResponse fetchResponse = new FetchResponse();
		
		url = repairURL(url);
		
		//创建sdk对象
		BaeFetchurl fetch= BaeFactory.getBaeFetchurl();
		//打开允许重定向开关,默认
		fetch.setAllowRedirect(LifeDjtuConfig.getBooleanProperty("allowRedirect"));
		//设置最大重定向次数
		fetch.setRedirectNum(LifeDjtuConfig.getIntegerProperty("redirectNum"));
		//设置cookie
		fetch.setCookie(LifeDjtuConfig.getProperty("sessionIdKey"), sessionId);
		//发起一次get请求
		fetchResponse.setResponseBody(fetch.get(url));
		//获取http code
		fetchResponse.setStatusCode(fetch.getHttpCode());
		//获取返回的头部信息
		fetchResponse.setResponseHeader(fetch.getResponseHeader());
				
		//如果非登陆，响应报文不会给Cookie的，这时候直接用方法传进来的cookie赋值给FetchResponse，如果为登陆，手动获取
		if(fetch.getResponseCookies()!=null){
			Pattern pattern = Pattern.compile(".*?JSESSIONID=(.*?);.*");
			Matcher matcher = pattern.matcher(fetch.getResponseCookies());
			if(matcher.find()){
				fetchResponse.setSessionId(matcher.group(1));
			}
		}else{
			fetchResponse.setSessionId(sessionId);
		}
		
		return fetchResponse;
	}
	
	//推荐使用MapMaker一行搞定初始化一个map，示例  MapMaker.instance("j_username", "1018110323").param("j_password", "******").toMap()
	public static FetchResponse fetchURLByPost(String url, String sessionId, Map<String, String> params){
		FetchResponse fetchResponse = new FetchResponse();
		
		url = repairURL(url);
		
		//创建sdk对象
		BaeFetchurl fetch= BaeFactory.getBaeFetchurl();
		//打开允许重定向开关,默认
		fetch.setAllowRedirect(LifeDjtuConfig.getBooleanProperty("allowRedirect"));
		//设置最大重定向次数
		fetch.setRedirectNum(LifeDjtuConfig.getIntegerProperty("redirectNum"));
		//设置cookie
		fetch.setCookie(LifeDjtuConfig.getProperty("sessionIdKey"), sessionId);
		//构造post请求
		List<NameValuePair> postData = new ArrayList<NameValuePair>();
		for(Map.Entry<String, String> entry : params.entrySet()){
			NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue());
			postData.add(nvp);
		}
		//设置post参数，实际post的内容
		fetch.setPostData(postData);
		//发起一次post请求
		fetchResponse.setResponseBody(fetch.post(url));
		//获取头部信息
		fetchResponse.setResponseHeader(fetch.getResponseHeader());
		//获取http code
		fetchResponse.setStatusCode(fetch.getHttpCode());
		
		//如果非登陆，响应报文不会给Cookie的，这时候直接用方法传进来的cookie赋值给FetchResponse，如果为登陆，手动获取
		if(fetch.getResponseCookies()!=null){
			Pattern pattern = Pattern.compile(".*?JSESSIONID=(.*?);.*");
			Matcher matcher = pattern.matcher(fetch.getResponseCookies());
			if(matcher.find()){
				fetchResponse.setSessionId(matcher.group(1));
			}
		}else{
			fetchResponse.setSessionId(sessionId);
		}
		
		return fetchResponse;
	}
	
	private static String repairURL(String url){
		if(!url.startsWith("http://"))
			return "http://"+url;
		return url;
	}
	
	public static void main(String[] args){
		FetchResponse response = fetchURLByPost("jw.djtu.edu.cn/academic/j_acegi_security_check", null, MapMaker.instance("j_username", "1018110323").param("j_password", "xxxxxx").toMap());
		
		FetchResponse header = fetchURLByGet("jw.djtu.edu.cn/academic/showHeader.do", response.getSessionId());
		
		String infoString = $("#greeting", header.getResponseBody()).get(0).getText();
		
		System.out.println(infoString);
	}
}
