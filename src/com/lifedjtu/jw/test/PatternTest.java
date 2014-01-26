package com.lifedjtu.jw.test;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
	public static void main(String[] args) throws ParseException{
//		StringBuilder builder = new StringBuilder();
//		
//		String segments = "下午1、4节";
//		Pattern pattern = Pattern.compile("[^0-9]*([0-9]+)([^0-9]+)([0-9]+)[^0-9]*");
//		Matcher matcher = pattern.matcher(segments);
//		if(matcher.find()){
//			int first = Integer.parseInt(matcher.group(1));
//			int second = Integer.parseInt(matcher.group(3));
//			String action = matcher.group(2);
//			
//			if(action.equals("、")){
//				builder.append("segments="+first+"|"+second);
//			}else if(action.equals("-")){
//				builder.append("segments="+first++);
//				for(; first <= second; ++first){
//					builder.append("|"+first);
//				}
//			}
//		}
//		
//		System.out.println(builder.toString());
//		
//		Pattern pattern = Pattern.compile("([0-9]+-[0-9]+-[0-9]+)[^0-9]+([0-9]+:[0-9]+)[^0-9]+([0-9]+:[0-9]+)");
//		Matcher matcher = pattern.matcher("2014-01-02 13:50--15:20");
//		if(matcher.find()){
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//			ExamTimeEntry entry = new ExamTimeEntry();
//			String date = matcher.group(1);
//			System.err.println(date);
//			String startTime = matcher.group(2);
//			System.err.println(startTime);
//			String endTime = matcher.group(3);
//			System.err.println(endTime);
//			
//			Date startDate = dateFormat.parse(date+" "+startTime);
//			Date endDate = dateFormat.parse(date+" "+endTime);
//			
//			entry.setDate(startDate);
//			entry.setLastedMinutes((int)((endDate.getTime()-startDate.getTime())/1000/60));
//			System.out.println(entry.toJSON());
//		}
		
		String str = "1-8,10-16周";
		//String str = "1周";
		Pattern pattern = Pattern.compile("[^0-9]*([0-9]+)(([-|、])([0-9]+))?");
		Matcher matcher = pattern.matcher(str);
		int index = 0;
		while(matcher.find(index)){
			for(int i = 0; i <= matcher.groupCount(); i++){
				System.out.println(matcher.group(i));
			}
			index = matcher.end(0);
		}
		
	}
}
