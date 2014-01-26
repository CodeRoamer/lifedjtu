package com.lifedjtu.jw.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

public class LifeDjtuFetchUrl {
	private static DefaultHttpClient httpClient = new DefaultHttpClient();
	//request part below
	
	//get and post
	private boolean allowRedirect = false;
	private int redirectNum = 0;
	private List<Cookie> setCookies = new ArrayList<Cookie>();
	private Map<String, String> postData = new HashMap<String, String>();
	
	//get
	private HttpGet httpGet;
	
	//post
	private HttpPost httpPost;
	
	//response part below
	private String responseBody;
	private String responseCookies;
	private int statusCode;
	private Map<String, String> responseHeaders;
	
	
	private String url;
	
	

	public HttpGet getHttpGet() {
		return httpGet;
	}

	public void setHttpGet(HttpGet httpGet) {
		this.httpGet = httpGet;
	}

	public HttpPost getHttpPost() {
		return httpPost;
	}

	public void setHttpPost(HttpPost httpPost) {
		this.httpPost = httpPost;
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
		return setCookies;
	}

	public void setCookie(Cookie cookie) {
		this.setCookies.add(cookie);
	}

	public void setCookie(String key, String value){
		this.setCookies.add(new Cookie(key,value));
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

	public String get(){
		return get(url);
	}
	
	public String get(String url){
		this.url = url;
		httpGet = new HttpGet();
		httpGet.setURI(composeURI());
		httpGet.setHeader("Cookie",composeCookieHeader());
		
		return connect(httpGet);
		
	}
	
	public String post(){
		return post(url);
	}
	
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

		return connect(httpPost);
	}
	
	
	private URI composeURI(){
		StringBuilder builder = new StringBuilder(url);
		if(postData!=null&&postData.size()!=0){
			builder.append("?");
			for(Map.Entry<String, String> entry:postData.entrySet()){
				builder.append(entry.getKey()+"="+entry.getValue()+"&");
			}
		}
		try {
			URI uri = new URI(builder.toString());
			return uri;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private HttpEntity composeEntity(){
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
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
	
	public String composeCookieHeader(){
		if(setCookies!=null&&setCookies.size()!=0){
			StringBuilder builder = new StringBuilder();
			for(Cookie cookie : setCookies){
				builder.append(cookie.getName()+"="+cookie.getValue()+";");
			}
			return builder.toString();
		}
		return null;
	}
	
	public String connect(HttpUriRequest httpRequest){
		BufferedReader reader = null;
		try {
			HttpResponse response = httpClient.execute(httpRequest);
			Header cookieHeader = response.getFirstHeader("Set-Cookie");
			responseCookies = (cookieHeader==null)?"":cookieHeader.getValue();
			statusCode = response.getStatusLine().getStatusCode();
			HeaderIterator iter = response.headerIterator();
			responseHeaders = new HashMap<String,String>();
			while(iter.hasNext()){
				Header header = iter.nextHeader();
				responseHeaders.put(header.getName(), header.getValue());
			}
			
			//处理编码
			InputStream inputStream = response.getEntity().getContent();
			Header typeHeader = response.getEntity().getContentType();
			Header encodingHeader = response.getEntity().getContentEncoding();
			String encoding = "";
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
			System.out.println(encoding);
			reader = new BufferedReader(new InputStreamReader(inputStream,encoding));
			String line = "";
			StringBuilder builder = new StringBuilder();
			while((line=reader.readLine())!=null){
				builder.append(line);
			}
			reader.close();
			responseBody = builder.toString();
			reset();
			
			return responseBody;
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
		setCookies.clear();
		postData.clear();
		url = "";
	}
	
	private boolean found = false;
    private String result;
	
	public String detectChineseCharset(InputStream in) throws IOException
    {
        String[] prob;
        // Initalize the nsDetector() ;
        nsDetector det = new nsDetector(nsPSMDetector.CHINESE);
        // Set an observer...
        // The Notify() will be called when a matching charset is found.

        det.Init(new nsICharsetDetectionObserver()
        {

            public void Notify(String charset)
            {
                found = true;
                result = charset;
            }
        });
        BufferedInputStream imp = new BufferedInputStream(in);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        boolean isAscii = true;
        boolean isDone = false;
        while ((len = imp.read(buf, 0, buf.length)) != -1)
        {
        	byteArrayOutputStream.write(buf,0,buf.length);
        	if(!isDone){
        		// Check if the stream is only ascii.
                if (isAscii)
                    isAscii = det.isAscii(buf, len);
                // DoIt if non-ascii and not done yet.
                if (!isAscii)
                {
                    if (det.DoIt(buf, len, false))
                        isDone = true;
                }
        	}
        }
        byteArrayOutputStream.flush();
        in.close();
        imp.close();
        det.DataEnd();
        if (isAscii)
        {
            found = true;
            prob = new String[]
                    {
                        "ASCII"
                    };
        } else if (found)
        {
            prob = new String[]
                    {
                        result
                    };
        } else
        {
            prob = det.getProbableCharsets();
        }
        
        
        String str = byteArrayOutputStream.toString(prob[0]);
        byteArrayOutputStream.close();
        return str;
        
    }

	
	
	public static void main(String[] args){
		LifeDjtuFetchUrl fetch = new LifeDjtuFetchUrl();
		System.err.println(fetch.get("http://www.taobao.com"));
		System.err.println(fetch.getResponseCookies()+"\n"+fetch.getResponseHeaders()+"\n"+fetch.getStatusCode());
	}
}
