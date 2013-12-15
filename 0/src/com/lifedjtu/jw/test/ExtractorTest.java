package com.lifedjtu.jw.test;

import static com.lifedjtu.jw.util.extractor.Extractor.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.lifedjtu.jw.util.extractor.DomElement;

public class ExtractorTest {
	public static void main(String[] args) throws IOException{
		
		String userDir = System.getProperty("user.dir");
		char sep = File.separatorChar;
		
		String htmlsRoot = userDir+sep+"WebRoot"+sep+"static"+sep+"html"+sep+"currcourse.html";
		
		String content = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(htmlsRoot)));
		String line;
		while((line=reader.readLine())!=null){
			content+=line;
		}		
		
		
		long current = System.currentTimeMillis();
		List<DomElement> list = $("table[class=infolist_tab] > tr",content);
		System.out.println((System.currentTimeMillis()-current)/(double)1000+"s");
		for(DomElement domElement : list){
			System.out.println(domElement.getText());
		}
	}
}
