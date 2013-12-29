package com.lifedjtu.jw.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ProjectCOLAnalyser {
	public static void main(String[] args) throws Exception{
		String root = System.getProperty("user.dir");
		System.out.println(root);
		
		int lines = 0;
		
		String[] sourceFolders = {"src","WebRoot/WEB-INF","WebRoot/static/js/custom"};
		for(int i = 0; i < sourceFolders.length; i++){
			File sr = new File(root+'/'+sourceFolders[i]);
			lines += folderLines(sr);
		}
		
		System.out.println(lines);
	}
	
	public static int folderLines(File file) throws Exception{
		int sum = 0;
		if(file.isDirectory()){
			File[] files = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if(pathname.isDirectory()&&!pathname.getName().equals("lib")){
						return true;
					}else if(pathname.isFile()){
						String pathName = pathname.getName();
						String extName = pathName.substring(pathName.lastIndexOf(".")+1);
						if(extName.equals("java")||extName.equals("xml")||extName.equals("html")||extName.equals("jsp")||extName.equals("css")||extName.equals("js")){
							return true;
						}
					}
					return false;
				}
			});
			for(int i = 0; i < files.length; i++){
				sum += folderLines(files[i]);
			}
		}else if(file.isFile()){
			//System.out.println(file.getName());
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			while(reader.readLine()!=null){
				sum++;
			}
			reader.close();
		}
		return sum;
	}
	
}
