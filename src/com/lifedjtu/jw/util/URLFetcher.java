package com.lifedjtu.jw.util;

import static com.lifedjtu.jw.util.extractor.Extractor.$;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lifedjtu.jw.config.LifeDjtuConfig;


//请使用IP地址，节约DNS查询时间
public class URLFetcher {	
	public static FetchResponse fetchURLByGet(String url, String sessionId){
		//System.err.println("GET in session:"+sessionId);
		
		
		FetchResponse fetchResponse = new FetchResponse();
		
		
		url = repairURL(url);
		
		//创建sdk对象
		
		LifeDjtuFetchUrl fetch= new LifeDjtuFetchUrl();
		//打开允许重定向开关,默认
		fetch.setAllowRedirect(LifeDjtuConfig.getBooleanProperty("allowRedirect"));
		//设置最大重定向次数
		fetch.setRedirectNum(LifeDjtuConfig.getIntegerProperty("redirectNum"));
		//设置cookie
		fetch.setCookie(LifeDjtuConfig.getProperty("sessionIdKey"), sessionId);
		//发起一次get请求
		fetchResponse.setResponseBody(fetch.get(url));
		//获取http code
		fetchResponse.setStatusCode(fetch.getStatusCode());
		//获取返回的头部信息
		fetchResponse.setResponseHeader(fetch.getResponseHeaders());
				
		//如果非登陆，响应报文不会给Cookie的，这时候直接用方法传进来的cookie赋值给FetchResponse，如果为登陆，手动获取
		if(fetch.getResponseCookies()!=null){
			Pattern pattern = Pattern.compile(".*?JSESSIONID=(.*?);.*");
			Matcher matcher = pattern.matcher(fetch.getResponseCookies());
			if(matcher.find()){
				fetchResponse.setSessionId(matcher.group(1));
			}
		}
		
		if(fetchResponse.getSessionId()==null||fetchResponse.getSessionId().equals("")){
			fetchResponse.setSessionId(sessionId);
		}
		
		//System.err.println("GET OUT session:"+fetchResponse.getSessionId());
		//System.err.println(fetchResponse.getResponseHeader().toString());
		
		return fetchResponse;
	}
	
	//推荐使用MapMaker一行搞定初始化一个map，示例  MapMaker.instance("j_username", "1018110323").param("j_password", "******").toMap()
	public static FetchResponse fetchURLByPost(String url, String sessionId, Map<String, String> params){
		//System.err.println("POST in session:"+sessionId);

		
		FetchResponse fetchResponse = new FetchResponse();
		
		url = repairURL(url);
		
		//创建sdk对象
		LifeDjtuFetchUrl fetch= new LifeDjtuFetchUrl();
		//打开允许重定向开关,默认
		fetch.setAllowRedirect(LifeDjtuConfig.getBooleanProperty("allowRedirect"));
		//设置最大重定向次数
		fetch.setRedirectNum(LifeDjtuConfig.getIntegerProperty("redirectNum"));
		//设置cookie
		fetch.setCookie(LifeDjtuConfig.getProperty("sessionIdKey"), sessionId);		
		//设置post参数，实际post的内容
		fetch.setPostData(params);		
		//发起一次post请求
		fetchResponse.setResponseBody(fetch.post(url));
		//获取头部信息
		fetchResponse.setResponseHeader(fetch.getResponseHeaders());
		//获取http code
		fetchResponse.setStatusCode(fetch.getStatusCode());
		
		//如果非登陆，响应报文不会给Cookie的，这时候直接用方法传进来的cookie赋值给FetchResponse，如果为登陆，手动获取
		if(fetch.getResponseCookies()!=null){
			Pattern pattern = Pattern.compile(".*?JSESSIONID=(.*?);.*");
			Matcher matcher = pattern.matcher(fetch.getResponseCookies());
			if(matcher.find()){
				fetchResponse.setSessionId(matcher.group(1));
			}
		}
		
		if(fetchResponse.getSessionId()==null||fetchResponse.getSessionId().equals("")){
			fetchResponse.setSessionId(sessionId);
		}
		
		
		//System.err.println("POST OUT session:"+fetchResponse.getSessionId());
		//System.err.println(fetchResponse.getResponseHeader().toString());
		
		return fetchResponse;
	}
	
	private static String repairURL(String url){
		if(!url.startsWith("http://"))
			return "http://"+url;
		return url;
	}
	
	public static void main(String[] args){
		long currentTime = System.currentTimeMillis();
		FetchResponse response = fetchURLByPost("202.199.128.21/academic/j_acegi_security_check", null, MapMaker.instance("j_username", "1018110323").param("j_password", "lh911119").toMap());
		System.err.println((System.currentTimeMillis()-currentTime)/(double)1000+"s");
		currentTime = System.currentTimeMillis();
		FetchResponse response2 = fetchURLByGet("202.199.128.21/academic/showHeader.do", response.getSessionId());
		System.err.println((System.currentTimeMillis()-currentTime)/(double)1000+"s");

		System.out.println(response.getSessionId());
		
		if(response2.getStatusCode()!=200){
			System.out.println("登陆失败或已经超时，重新登陆！");
		}else {
			
			String infoString = $("#greeting", response2.getResponseBody()).get(0).getText().trim();
			
			System.out.println(infoString);
		}
	}
}
