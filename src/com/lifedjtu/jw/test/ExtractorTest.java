package com.lifedjtu.jw.test;

import static com.lifedjtu.jw.util.extractor.Extractor.$;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.lifedjtu.jw.pojos.dto.EvaEntry;
import com.lifedjtu.jw.pojos.dto.EvaList;
import com.lifedjtu.jw.util.extractor.DomElement;

public class ExtractorTest {
	public static void main(String[] args) throws IOException{
		
		String userDir = System.getProperty("user.dir");
		char sep = File.separatorChar;
		
		String htmlsRoot = userDir+sep+"WebRoot"+sep+"static"+sep+"html"+sep+"partialEvaList.html";
		
		String content = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(htmlsRoot)));
		String line;
		while((line=reader.readLine())!=null){
			content+=line;
		}		
		
		//System.out.println(content);
//		long current = System.currentTimeMillis();
//		List<DomElement> list1 = $("table[class=none] > td",content);
//		//List<DomElement> list2 = $("table[class=form] > td",content);
//		System.out.println((System.currentTimeMillis()-current)/(double)1000+"s");
//		DomElement domElement = list1.get(0);
//		domElement.toString();
//		System.out.println(domElement.getText());
		//domElement = list2.get(0);
		//System.out.println(domElement.getText());
//		for(DomElement domElement : list){
//			System.out.println(domElement.getText());
//		}
//		if(list.isEmpty()) System.out.println("true");
		
		List<DomElement> list = $("tr[class=infolist_common] > td:odd",content);

		EvaList evaList = new EvaList();
		
		Iterator<DomElement> iterator = list.iterator();
		System.out.println(list.size());
		while(iterator.hasNext()){
			DomElement one = iterator.next();
			if(!iterator.hasNext()){
				break;
			}
			DomElement two = iterator.next();
			List<DomElement> aElements = two.find("a");
			if(aElements==null||aElements.size()==0){
				continue;
			}
			
			EvaEntry evaEntry = new EvaEntry();
			evaEntry.setCourseName(one.getText().trim());
			evaEntry.setCourseHref(aElements.get(0).attr("href").substring(2));
			evaEntry.setCourseStatus(0);
			
			evaList.addEntry(evaEntry);
		}
		System.out.println(evaList.size());
		System.out.println(evaList.toJSON());
		
		reader.close();
	}
}
