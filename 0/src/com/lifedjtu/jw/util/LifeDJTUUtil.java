package com.lifedjtu.jw.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.lifedjtu.jw.pojos.dto.CourseDto;
import com.lifedjtu.jw.pojos.dto.CourseRow;
import com.lifedjtu.jw.pojos.dto.CourseTakenItem;

public class LifeDJTUUtil {
	public static String fakePartialEvaList() {
		String userDir = System.getProperty("user.dir");
		char sep = File.separatorChar;

		String htmlsRoot = userDir + sep + "WebRoot" + sep + "static" + sep
				+ "html" + sep + "partialEvaList.html";

		String content = "";

		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(htmlsRoot)));
			String line;
			while ((line = reader.readLine()) != null) {
				content += line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return content;
	}
	
	public static String fakeEvaList() {
		String userDir = System.getProperty("user.dir");
		char sep = File.separatorChar;

		String htmlsRoot = userDir + sep + "WebRoot" + sep + "static" + sep
				+ "html" + sep + "evaList.html";

		String content = "";

		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(htmlsRoot)));
			String line;
			while ((line = reader.readLine()) != null) {
				content += line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return content;
		
	}

	public static String fakeEvalItem(){
		String userDir = System.getProperty("user.dir");
		char sep = File.separatorChar;

		String htmlsRoot = userDir + sep + "WebRoot" + sep + "static" + sep
				+ "html" + sep + "evaItem.html";

		String content = "";

		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(htmlsRoot)));
			String line;
			while ((line = reader.readLine()) != null) {
				content += line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return content;
	}
	
	/*
	 * 转变Course集合为CourseRow的可表现形式
	 */
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
