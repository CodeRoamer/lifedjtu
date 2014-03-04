package com.lifedjtu.jw.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.lifedjtu.jw.dao.support.UUIDGenerator;
import com.lifedjtu.jw.pojos.CourseInstance;
import com.lifedjtu.jw.pojos.dto.CourseDto;
import com.lifedjtu.jw.pojos.dto.CourseRow;
import com.lifedjtu.jw.pojos.dto.CourseTakenItem;
import com.lifedjtu.jw.util.pattern.InfoProcessHub;

public class LifeDjtuUtil {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Date parseDate(String dateStr){
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
	
	public static Date getStartDateOfTerm(int schoolYear, int term){
		GregorianCalendar calendar = new GregorianCalendar();
//		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		if(term==1){
			calendar.set(schoolYear, 2, 1);
			int i = 2;
			while(calendar.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY){
				calendar.set(schoolYear, 2, i);
				i++;
			}
		}else{
			calendar.set(schoolYear, 8, 1);
			int i = 2;
			while(calendar.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY){
				calendar.set(schoolYear, 8, i);
				i++;
			}
		}
		
		return calendar.getTime();
	}
	
	public static String fakePartialEvaList() {
		String userDir = System.getProperty("user.dir");
		char sep = File.separatorChar;

		String htmlsRoot = userDir + sep + "WebRoot" + sep + "static" + sep
				+ "html" + sep + "partialEvaList.html";

		String content = "";
		BufferedReader reader = null;
		try {

			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(htmlsRoot)));
			String line;
			while ((line = reader.readLine()) != null) {
				content += line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(reader!=null)
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		return content;
	}
	
	public static String fakeEvaList() {
		String userDir = System.getProperty("user.dir");
		char sep = File.separatorChar;

		String htmlsRoot = userDir + sep + "WebRoot" + sep + "static" + sep
				+ "html" + sep + "evaList.html";

		String content = "";
		BufferedReader reader = null;
		try {

			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(htmlsRoot)));
			String line;
			while ((line = reader.readLine()) != null) {
				content += line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(reader!=null)
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		return content;
		
	}

	public static String fakeEvalItem(){
		String userDir = System.getProperty("user.dir");
		char sep = File.separatorChar;

		String htmlsRoot = userDir + sep + "WebRoot" + sep + "static" + sep
				+ "html" + sep + "evaItem.html";

		String content = "";

		BufferedReader reader = null;
		try {

			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(htmlsRoot)));
			String line;
			while ((line = reader.readLine()) != null) {
				content += line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(reader!=null)
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		return content;
	}
	
	public static String fakeCurrentCourse(){
		String userDir = System.getProperty("user.dir");
		char sep = File.separatorChar;

		String htmlsRoot = userDir + sep + "WebRoot" + sep + "static" + sep
				+ "html" + sep + "currcourse.html";

		String content = "";
		BufferedReader reader = null;
		try {

			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(htmlsRoot)));
			String line;
			while ((line = reader.readLine()) != null) {
				content += line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(reader!=null)
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		return content;
	}
	
	public static CourseInstance transferCourseDto(CourseDto courseDto, int year, int term) {

		CourseInstance courseInstance = new CourseInstance();
		courseInstance.setId(UUIDGenerator.randomUUID());
		courseInstance.setCourseAlias(courseDto.getAliasName());
		courseInstance.setCourseName(courseDto.getCourseName());
		courseInstance.setCourseRemoteId(courseDto.getCourseRemoteId());
		courseInstance.setBadEval(0);
		courseInstance.setGoodEval(0);
		courseInstance.setExamStatus(courseDto.getExamAttr());
		courseInstance.setCourseSequence(courseDto.getCourseNumber());
		courseInstance.setPostponed(courseDto.isDelayed());

		
		StringBuilder takenBuilder = new StringBuilder();
		List<CourseTakenItem> courseTakenItems = courseDto.getCourseTakenItems();
		if(courseTakenItems==null||courseTakenItems.size()==0){
			takenBuilder.append("时间地点均不占");
		}else{
			for(CourseTakenItem courseTakenItem : courseTakenItems){
				takenBuilder.append(InfoProcessHub.transferCourseTakenItem(courseTakenItem)+";");
			}
		}
		
		courseInstance.setCourseTakenInfo(takenBuilder.toString());
		courseInstance.setTeacherName(courseDto.getTeacherName());
		courseInstance.setYear(year);
		courseInstance.setTerm(term);
		
		return courseInstance;
	}

	
	/*
	 * 转变Course集合为CourseRow的可表现形式
	 */
	@SuppressWarnings("unused")
	public List<CourseRow> transformCourseList(List<CourseDto> courses) {
		List<CourseRow> courseRows = new ArrayList<CourseRow>();
		for (int i = 0; i < 10; ++i) {
			CourseRow courseRow = new CourseRow();
			courseRow.setIndex(i + 1);
			courseRows.add(courseRow);
		}

		for (CourseDto course : courses) {
			String name = course.getCourseName();
			List<CourseTakenItem> items = course.getCourseTakenItems();
			for (CourseTakenItem item : items) {
			}
		}

		return null;
	}
}
