package com.lifedjtu.jw.util.pattern;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lifedjtu.jw.pojos.dto.CourseTakenItem;
import com.lifedjtu.jw.pojos.dto.DjtuDate;
import com.lifedjtu.jw.pojos.dto.ExamTimeEntry;

public class InfoProcessHub {
	//将一个rawString转换为一个DjtuDate对象
	public static DjtuDate analyseDjtuDate(String rawString){
		String[] parts = rawString.split("\\s+");
		//System.out.println(Arrays.toString(parts));
		
		Date date = new Date();
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int term;
		String termStr = parts[2].charAt(parts[2].length()-1)+"";
		if(termStr.equals("秋")){
			term = 2;
		}else if(termStr.equals("春")){
			term = 1;
		}else {
			term = 0;
		}
		int week = 0;
		String weekStr = parts[3].substring(1);
		week = Integer.parseInt(weekStr.substring(0, weekStr.length()-1));
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		if(weekDay==1){
			weekDay=7;
		}else{
			--weekDay;
		}
		
		DjtuDate djtuDate = new DjtuDate(year, month, day, term, week, weekDay);
		
		return djtuDate;
	}
	
	//将一个表示日期的String转换为一个ExamTimeEntry对象
	public static ExamTimeEntry transferExamDate(String examDate) throws ParseException{
		Pattern pattern = Pattern.compile("([0-9]+-[0-9]+-[0-9]+)[^0-9]+([0-9]+:[0-9]+)[^0-9]+([0-9]+:[0-9]+)");
		Matcher matcher = pattern.matcher(examDate);
		if(matcher.find()){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			ExamTimeEntry entry = new ExamTimeEntry();
			String date = matcher.group(1);
			//System.err.println(date);
			String startTime = matcher.group(2);
			//System.err.println(startTime);
			String endTime = matcher.group(3);
			//System.err.println(endTime);
			
			Date startDate = dateFormat.parse(date+" "+startTime);
			Date endDate = dateFormat.parse(date+" "+endTime);
			
			entry.setDate(startDate);
			entry.setLastedMinutes((int)((endDate.getTime()-startDate.getTime())/1000/60));
			return entry;
		}
		return null;
	}
	
	//转换多个CourseTakenItem转换为一个字符串
	public static String transferCourseTakenItem(CourseTakenItem courseTakenItem){
		StringBuilder builder = new StringBuilder();
		
		String week = courseTakenItem.getWeek();
		
		Pattern pattern = Pattern.compile("[^0-9]*([0-9]+)(([-|、])([0-9]+))?");
		Matcher matcher = pattern.matcher(week);
		String startWeekStr = "startWeek=";
		String endWeekStr = "endWeek=";
		int index = 0;
		while(matcher.find(index)){
			if(index!=0){
				startWeekStr+="|";
				endWeekStr+="|";
			}
			startWeekStr+=matcher.group(1);
			if(matcher.group(2)==null){
				endWeekStr+=matcher.group(1);
			}else if(matcher.group(3).equals("-")){
				endWeekStr+=matcher.group(4);
			}
			
			index = matcher.end(0);
		}		
		
		builder.append(startWeekStr+"&"+endWeekStr+"&");
		
		String weekDayStr = courseTakenItem.getDay().substring(1);
		int weekDay = 0;
		if(weekDayStr.equals("一")){
			weekDay = 1;
		}else if(weekDayStr.equals("二")){
			weekDay = 2;
		}else if(weekDayStr.equals("三")){
			weekDay = 3;
		}else if(weekDayStr.equals("四")){
			weekDay = 4;
		}else if(weekDayStr.equals("五")){
			weekDay = 5;
		}else if(weekDayStr.equals("六")){
			weekDay = 6;
		}else if(weekDayStr.equals("日")){
			weekDay = 7;
		}
		builder.append("weekDay="+weekDay+"&");
		
		builder.append("roomName="+courseTakenItem.getRoomName()+"&");
		
		String segments = courseTakenItem.getTime();
		pattern = Pattern.compile("[^0-9]*([0-9]+)([^0-9]+)([0-9]+)[^0-9]*");
		matcher = pattern.matcher(segments);
		if(matcher.find()){
			int first = Integer.parseInt(matcher.group(1));
			int second = Integer.parseInt(matcher.group(3));
			String action = matcher.group(2);
			
			if(action.equals("、")){
				builder.append("segments="+first+"|"+second);
			}else if(action.equals("-")){
				builder.append("segments="+first++);
				for(; first <= second; ++first){
					builder.append("|"+first);
				}
			}
		}
		return builder.toString();
	}
	
}
