package com.lifedjtu.jw.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
	public static void main(String[] args){
		StringBuilder builder = new StringBuilder();
		
		String segments = "下午1、4节";
		Pattern pattern = Pattern.compile("[^0-9]*([0-9]+)([^0-9]+)([0-9]+)[^0-9]*");
		Matcher matcher = pattern.matcher(segments);
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
		
		System.out.println(builder.toString());
	}
}
