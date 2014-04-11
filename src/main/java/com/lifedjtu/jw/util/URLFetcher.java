package com.lifedjtu.jw.util;

import static com.lifedjtu.jw.util.extractor.Extractor.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.lifedjtu.jw.config.LifeDjtuConfig;
import com.lifedjtu.jw.pojos.dto.Pair;


//请使用IP地址，节约DNS查询时间
public class URLFetcher {	
	
	public static FetchResponse fetchStreamByGet(String url, String sessionId){
		FetchResponse fetchResponse = new FetchResponse();
		
		url = repairURL(url);
				
		LifeDjtuFetchUrl fetch= new LifeDjtuFetchUrl();
		//打开允许重定向开关,默认
		fetch.setAllowRedirect(LifeDjtuConfig.getBooleanProperty("allowRedirect"));
		//设置最大重定向次数
		fetch.setRedirectNum(LifeDjtuConfig.getIntegerProperty("redirectNum"));
		//设置cookie
		fetch.setCookie(LifeDjtuConfig.getProperty("sessionIdKey"), sessionId);
		//发起一次get请求
		fetchResponse.setInputStream(fetch.getAsStream(url));
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
	
	public static FetchResponse fetchURLByGet(String url, String sessionId){
		//System.err.println("GET in session:"+sessionId);
		
		
		FetchResponse fetchResponse = new FetchResponse();
		
		
		url = repairURL(url);
		
		//创建sdk对象
//		FetchUrlService fetchUrlService = FetchUrlServiceFactory.getFetchUrlService();
//		String content = fetchUrlService.get(url);
//		System.err.println(content);
		
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
		endlessSigninRemote("1018110323", "lh911119");
		System.err.println((System.currentTimeMillis()-currentTime)/(double)1000+"s");
		
			    
	}
	
	public static String endlessSigninRemote(String studentId, String password){
		Pair<String, String> pair = queryRemoteDamnCode(null);
		FetchResponse response = fetchURLByPost("202.199.128.21/academic/j_acegi_security_check", pair.getKey(), MapMaker.instance("j_username", studentId).param("j_password", password).param("j_captcha", pair.getValue()).toMap());
		FetchResponse response2 = fetchURLByGet("202.199.128.21/academic/showHeader.do", response.getSessionId());
		
		for(int i = 0; i < 10; i++){
			pair = queryRemoteDamnCode(pair.getKey());
			response = fetchURLByPost("202.199.128.21/academic/j_acegi_security_check", pair.getKey(), MapMaker.instance("j_username", studentId).param("j_password", password).param("j_captcha", pair.getValue()).toMap());
			response2 = fetchURLByGet("202.199.128.21/academic/showHeader.do", response.getSessionId());
			if(response2.getStatusCode()==200){
				System.err.println("tried "+(i+1)+" times");
				break;
			}
		}
		
		if(response2.getStatusCode()!=200){
			System.out.println("登陆失败或已经超时，重新登陆！");
			return null;
		}else {
			String infoString = $("#greeting", response2.getResponseBody()).get(0).getText().trim();
			System.out.println(infoString);
			return pair.getKey();
		}
	}
	
	public static Pair<String, String> queryRemoteDamnCode(String sessionId){
		
		try{
			FetchResponse fetchResponse = fetchStreamByGet("202.199.128.21/academic/getCaptcha.do?999999999999999999999999999999999999", sessionId);
			
			BufferedImage image = null;
			image = ImageIO.read(fetchResponse.getInputStream());

		    int averageGrey = 0;
		    //设置灰度
		    for(int i = 0; i < image.getHeight(); i++){
		    	for(int j = 0; j < image.getWidth(); j++){
		    		int rgb = image.getRGB(j, i);
		    		
		    		Color color = new Color(rgb); // 根据rgb的int值分别取得r,g,b颜色。  
		            int gray = (int) (0.3 * color.getRed() + 0.59  
		                * color.getGreen() + 0.11 * color.getBlue());
		            
		            averageGrey+=gray;
		            
		            Color newColor = new Color(gray, gray, gray);  
		            image.setRGB(j, i, newColor.getRGB());
		    	}
		    }
		    
		    averageGrey/=image.getHeight()*image.getWidth();
		    //二值化
		    for(int i = 0; i < image.getHeight(); i++){
		    	for(int j = 0; j < image.getWidth(); j++){
		    		int rgb = image.getRGB(j, i);
		    		
		            Color color = new Color(rgb); // 根据rgb的int值分别取得r,g,b颜色。
		            int value = 255 - color.getBlue();
		            if (value > averageGrey) {
		                Color newColor = new Color(0, 0, 0);
		                image.setRGB(j, i, newColor.getRGB());
		            } else {
		                Color newColor = new Color(255, 255, 255);
		                image.setRGB(j, i, newColor.getRGB());
		            }
		        }
		    }
		    
		    if(imageToText(image, "test", "test")==0){
		    	File file = new File("./test.txt");
		    	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		    	String result = bufferedReader.readLine();
		    	bufferedReader.close();
		    	
		    	char[] charArr = result.toCharArray();
		    	System.err.println("\nBefore Fix: \""+new String(charArr)+"\"");
		    	String str = LifeDjtuUtil.fixDamnCode(charArr);
		    	System.err.println("After Fix: \""+str+"\"");
		    	
		    	return new Pair<String, String>(fetchResponse.getSessionId(),str);
		    }
		}catch(Exception exception){
			exception.printStackTrace();
		}
		
		return null;

	}
	
	public static int imageToText(BufferedImage image, String imageName, String textName) throws Exception{
		File file = new File("./"+imageName+".jpeg");
		ImageIO.write(image, "jpeg", file);

	    
	    List<String> cmd = new ArrayList<String>(); // 存放命令行参数的数组
	    cmd.add("D:\\Program Files (x86)\\Tesseract-OCR\\tesseract.exe");
	    cmd.add("");
	    cmd.add("./"+textName); // 输出文件位置
	    cmd.add("eng"); // 英文，找到tessdata里对应的字典文件。
	    ProcessBuilder pb = new ProcessBuilder();

	    cmd.set(1, file.getAbsolutePath()); // 把图片文件位置放在第一个位置

	    pb.command(cmd); // 执行命令行
	    pb.redirectErrorStream(true); // 通知进程生成器是否合并标准错误和标准输出,把进程错误保存起来。
	    Process process;
	    process = pb.start();
	    return process.waitFor(); // 当前进程停止,直到process停止执行，返回执行结果.
	}
	
	
}
