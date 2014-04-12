package com.lifedjtu.jw.util.fetcher;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LifeDjtuFetchUrl {
	private CloseableHttpClient httpClient;

	//request part below
	//get and post
	private boolean allowRedirect = false; //允许跳转
	private int redirectNum = 0; //跳转次数
	private List<Cookie> cookies = new ArrayList<>(); //Cookie For Request
	private Map<String, String> postData = new HashMap<>(); //post data for response
	
	//get
	private HttpGet httpGet;
	
	//post
	private HttpPost httpPost;
	
	//response part below
	private String responseBody;
	private String responseCookies;
	private int statusCode;
	private Map<String, String> responseHeaders;
	private String contentType;
	
	private String url;
	

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isAllowRedirect() {
		return allowRedirect;
	}

	public void setAllowRedirect(boolean allowRedirect) {
		this.allowRedirect = allowRedirect;
	}

	public int getRedirectNum() {
		return redirectNum;
	}

	public void setRedirectNum(int redirectNum) {
		this.redirectNum = redirectNum;
	}

	public List<Cookie> getCookies() {
		return cookies;
	}

	public void addCookie(Cookie cookie) {
		this.cookies.add(cookie);
	}

	public void addCookie(String key, String value){
		this.cookies.add(new Cookie(key,value));
	}
	
	public Map<String, String> getPostData() {
		return postData;
	}

	public void setPostData(Map<String, String> postData) {
		this.postData = postData;
	}

	public void addPostData(String key, String value){
		this.postData.put(key, value);
	}
	
	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public String getResponseCookies() {
		return responseCookies;
	}

	public void setResponseCookies(String responseCookies) {
		this.responseCookies = responseCookies;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Map<String, String> getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(Map<String, String> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    /**
     * HTTP Get Method
     * @return response body
     */
	public String get(){
		return get(url);
	}

    /**
     * HTTP Get Method: Return Stream Instead, Use for download file, such as images
     * @param url url of forwarding request
     * @return input stream object
     */
	public InputStream getAsStream(String url){
		this.url = url;
		httpGet = new HttpGet();
		httpGet.setURI(composeURI());
		httpGet.setHeader("Cookie",composeCookieHeader());
		
		return connect(httpGet);
	}

    /**
     * HTTP GET Method
     * @param url url of forwarding request
     * @return response body
     */
	public String get(String url){
		this.url = url;
		httpGet = new HttpGet();
		httpGet.setURI(composeURI());
		httpGet.setHeader("Cookie",composeCookieHeader());
		
		connect(httpGet);
		
		return responseBody;
		
	}

    /**
     * HTTP Post Method
     * @return response body
     */
	public String post(){
		return post(url);
	}

    /**
     * HTTP Post Method
     * @param url url of forwarding request
     * @return response body
     */
	public String post(String url){
		this.url = url;
		httpPost = new HttpPost();
		try {
			httpPost.setURI(new URI(url));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		httpPost.setEntity(composeEntity());
		httpPost.setHeader("Cookie",composeCookieHeader());

		connect(httpPost);
		
		return responseBody;
	}

    /**
     * compose url, add request params
     * @return composed uri object
     */
	private URI composeURI(){
		StringBuilder builder = new StringBuilder(url);
		if(postData!=null&&postData.size()!=0){
			builder.append("?");
			for(Map.Entry<String, String> entry:postData.entrySet()){
				builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		try {
			return new URI(builder.toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * compose entity
     * @return HttpEntity
     */
	private HttpEntity composeEntity(){
		List<NameValuePair> formparams = new ArrayList<>();
		if(postData!=null&&postData.size()!=0){
			for(Map.Entry<String, String> entry:postData.entrySet()){
				formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			return entity;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		
	}

    /**
     * compose cookie for request
     * @return string contains cookie info
     */
	public String composeCookieHeader(){
		if(cookies!=null&&cookies.size()!=0){
			StringBuilder builder = new StringBuilder();
			for(Cookie cookie : cookies){
				if(cookie.getValue()==null||"".equals(cookie.getValue())){
					continue;
				}
				builder.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
			}
			return builder.toString();
		}
		return null;
	}
	
	public InputStream connect(HttpUriRequest httpRequest){
		BufferedReader reader = null;
		try {
            httpClient = HttpClients.createMinimal();
            //设置client请求
//			HttpParams params = httpClient.getParams();
//			params.setParameter(ClientPNames.HANDLE_REDIRECTS, allowRedirect);
//			params.setParameter(ClientPNames.MAX_REDIRECTS, redirectNum);
//			httpClient.setParams(params);
			//执行connect
			CloseableHttpResponse response = httpClient.execute(httpRequest);

			reset(); //reset

			//抓取cookie，尤其是session cookie
			Header cookieHeader = response.getFirstHeader("Set-Cookie");
			responseCookies = (cookieHeader==null)?"":cookieHeader.getValue();
			//获取状态码
			statusCode = response.getStatusLine().getStatusCode();
			//获取全部的头部
			HeaderIterator iter = response.headerIterator();
			responseHeaders = new HashMap<>();
			while(iter.hasNext()){
				Header header = iter.nextHeader();
				responseHeaders.put(header.getName(), header.getValue());
			}
			
			//处理编码
			InputStream inputStream = response.getEntity().getContent();
			Header typeHeader = response.getEntity().getContentType();
			Header encodingHeader = response.getEntity().getContentEncoding();
			//默认处理文字
			boolean plainText = true;
			//response type的判断
			if(typeHeader != null){
				String value = typeHeader.getValue();
				int index;
				if((index=value.indexOf(";"))!=-1){
					value = value.substring(0,index);
				}
				//value now is the type of response
				//System.err.println("in fetcher: "+value);
				contentType = value;
				
				if(!value.contains("text")){
					plainText = false;
					//System.err.println("not text");
				}
			}
			
			if(plainText){
				//编码判断
				String encoding;
				if(encodingHeader!=null){
					encoding = encodingHeader.getValue().trim();
				}else if(typeHeader != null){
					Pattern pattern = Pattern.compile(".*[cC]harset=([a-zA-z0-9-]+).*");
					Matcher matcher = pattern.matcher(typeHeader.getValue());
					if(matcher.find()){
						encoding = matcher.group(1);
					}else{
						encoding = "UTF-8";
					}
				}else{
					encoding = "UTF-8";
				}
				//System.out.println(encoding);
				reader = new BufferedReader(new InputStreamReader(inputStream,encoding));
				String line;
				StringBuilder builder = new StringBuilder();
				while((line=reader.readLine())!=null){
					builder.append(line);
				}
				reader.close();
                response.close();
                httpClient.close();
				responseBody = builder.toString();
				
				return null;
			}else{
				//处理其他格式，返回流
				return inputStream;
			}
			
			//System.err.println(statusCode);
			//System.out.println(responseBody);
			
		} catch (IOException e) {
			e.printStackTrace();
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			return null;
		} 
	}
	
	public void reset() {
		allowRedirect = false;
		redirectNum = 0;
		cookies.clear();
		postData.clear();
		url = "";
	}
	

	
	public static void main(String[] args){
		LifeDjtuFetchUrl fetch = new LifeDjtuFetchUrl();
		System.err.println(fetch.get("http://jw.djtu.edu.cn/academic/showHeader.do"));
		System.err.println(fetch.getResponseCookies()+"\n"+fetch.getResponseHeaders()+"\n"+fetch.getStatusCode());
	}
}
