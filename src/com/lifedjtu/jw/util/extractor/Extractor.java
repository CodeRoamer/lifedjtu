package com.lifedjtu.jw.util.extractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Extractor {
	public static final String CLASS_SELECTOR = ".";
	public static final String ID_SELECTOR = "#";
	
	//这个pattern是用来匹配一个标签的各种属性与值的对
	private static Pattern propertyPattern = Pattern.compile("(\\w+-?)+=(\\\"(.*?)\\\"|(/?\\w+-?/?\\.?)+)",Pattern.MULTILINE);
	//这个pattern是用来匹配全部的html标签和转义字符（&开头的），以便将他们删去
	private static Pattern removeTagPattern = Pattern.compile("(<[^<>]*?>|&\\w+?;)",Pattern.MULTILINE);
	
	private static Pattern allTagFindPattern = Pattern.compile("<(/)?(\\b\\w+)[^<>]*?>",Pattern.MULTILINE);
	public static List<DomElement> $(String selector, String content){
		
		//System.out.println(content);
		
		String[] selectors = selector.split("\\s");
		if(selectors.length==1){//单一的选择器模式
			return singleSelector(selectors[0], content);
		}else if(selectors.length==2){//子孙选择器模式，儿子，孙子，全有
			List<DomElement> domElements = singleSelector(selectors[0], content);
			List<DomElement> domElements2 = new ArrayList<DomElement>();
			for(DomElement domElement : domElements){
				domElements2.addAll(singleSelector(selectors[1], domElement.getHtml()));
			}
			return domElements2;
		}else if(selectors.length==3){//中间符号的选择器
			if(selectors[1].equals(">")){ //亲孩子
				
				//System.out.println(content.length());
				
				List<DomElement> domElements = singleSelector(selectors[0], content);
				//System.err.println(domElements.size());
				List<DomElement> domElements2 = new ArrayList<DomElement>();
				for(DomElement domElement : domElements){
					domElements2.addAll(findAllDirectChildrenAndSelect(domElement.getHtml(), selectors[2]));
				}
				return domElements2;
			}else if(selectors[1].equals("+")){//相邻同级tag，限定
				
			}else if(selectors[1].equals("~")){ //之后同级的tag，限定
				
			}
			
		}
		return null;
	}
	
	private static List<DomElement> singleSelector(String selector, String content){
		List<DomElement> domElements = null;
		String filterString = null;
		int filterStart = selector.lastIndexOf(":");
		if(filterStart!=-1){
			filterString = selector.substring(filterStart+1);
			selector = selector.substring(0, filterStart);
			//System.out.println(filterString);
		}
		
		
		Filter filter = createFilter(filterString);
		
		
		
		//class选择器
		if(selector.startsWith(CLASS_SELECTOR)){
			//class选择器
			String className = selector.substring(1);
			//<([a-z|A-Z]+)[^>]*class=(\"(.*?\s+)?title(\s+.*?)?\"|title)[^>]*>
			String regexPattern = "<([a-z|A-Z]+)[^>]*class=(\\\"((.*?\\s+)?"+className+"(\\s+.*?)?)\\\"|"+className+")[^>]*?>";
			Pattern tagPattern  = Pattern.compile(regexPattern, Pattern.MULTILINE);
			
			
			Matcher matcher = tagPattern.matcher(content);
//			if(matcher.find(0)){
//				for(int j = 0; j <= matcher.groupCount(); j++){
//			        System.out.print("[" + matcher.group(j) + "]\tindex:"+matcher.end(j));
//			        System.out.println();
//			    }
//			}
						
			domElements = packDomElement(matcher, content);
			
		}else if(selector.startsWith(ID_SELECTOR)){
			//ID选择器
			String id = selector.substring(1);
			String regexPattern = "<([a-z|A-Z]+)[^>]*id=(\\\"("+id+")\\\"|"+id+")[^>]*?>";
			Pattern tagPattern = Pattern.compile(regexPattern,Pattern.MULTILINE);
			Matcher matcher = tagPattern.matcher(content);

			domElements = packDomElement(matcher, content);

		}else {
			//标签选择器
			
			//将选择器字符串拆分
			String[] parts = selector.split("\\[|\\]");
			String selectorTagName = parts[0];
			List<TagSelectorPair> listPairs = new ArrayList<TagSelectorPair>();
			
			if(parts.length>1){
				//这个pattern用来分析每一个选择器的键值对
				Pattern selectorPairPattern = Pattern.compile("((\\w+-?)+)([$^*]?)=[\"|']?([^\"']*)[\"|']?"); 
				for(int i = 1; i < parts.length; i++){
					if(parts[i].trim().equals("")){
						continue;
					}
					Matcher matcher = selectorPairPattern.matcher(parts[i]);
					TagSelectorPair pair = new TagSelectorPair();
					if(matcher.find()){
						pair.setKey(matcher.group(1));
						pair.setValue(matcher.group(4));
						pair.setExtra(matcher.group(3));
					}
					listPairs.add(pair);
				}
			}
			
			
//			for(TagSelectorPair pair : listPairs){
//				System.out.println(pair.toJSON());
//			}
			
			domElements = new ArrayList<DomElement>();
			
			//这个pattern用来获取全部的此标签元素，全部的<tr ..>，全部的<table ..>之类的
			Pattern tagPattern = Pattern.compile("<"+selectorTagName+"[^>]*>",Pattern.MULTILINE);
			Matcher matcher = tagPattern.matcher(content);
			int sequenceIndex = 0;
			while(matcher.find(sequenceIndex)){
				String selectedTag = matcher.group(0);
				//System.out.println(selectedTag);
				//不满足选择器列出的属性键值对的话，扫描下一个此标签元素
				if(!isFullfilPropertyPattern(listPairs, selectedTag, selectorTagName)){
					//勿忘更新扫描起点
					sequenceIndex = matcher.end(0);
					continue;
				}
				
				//下面将要获取全部的此标签属性键值对
				Map<String, String> properties = packTagProperties(selectedTag);
				
				
				DomElement domElement = new DomElement();
				domElement.setTag(selectedTag);
				domElement.setTagName(selectorTagName);
				domElement.setClasses(packTagClasses(properties.get("class")));
				domElement.setAttributes(properties);

				
				sequenceIndex = matcher.end(0);
				
				//为了获取结束元素，执行下面的操作
				//获取元素的html内容
				domElement.setHtml(packTagHtml(domElement.getTagName(), content, sequenceIndex));
				
				//获取元素的纯text
				domElement.setText(packTagText(domElement.getHtml()));

				
				domElements.add(domElement);				
			}
		}
		if(filter!=null){
			return filter.filterList(domElements);
		}else{
			return domElements;
		}
	}
	
	//打包，适合第一个class查询与第二个id查询，注意！不完全适合第三种查询
	private static List<DomElement> packDomElement(Matcher matcher, String content){
		List<DomElement> domElements = new ArrayList<DomElement>();

		
		int sequenceIndex = 0;
		while(matcher.find(sequenceIndex)){
			//group 0  all tag
			//group 1  tag name
			String tag = matcher.group(0);
			String tagName = matcher.group(1);
			
			//提取全部的标签property
			Map<String, String> properties = packTagProperties(tag);
			
			//开始构建DomElement
			DomElement domElement = new DomElement();
			domElement.setTag(tag);
			domElement.setTagName(tagName);
			domElement.setClasses(packTagClasses(properties.get("class")));
			domElement.setAttributes(properties);

			
			sequenceIndex = matcher.end(0);
						
			//获取元素的html内容
			domElement.setHtml(packTagHtml(domElement.getTagName(), content, sequenceIndex));
			
			//获取元素的纯text
			domElement.setText(packTagText(domElement.getHtml()));
			
			
			domElements.add(domElement);

		}
		return domElements;
	}
	
	
	//根据标签和属性选择标签时，构建三种不同情况下的子选择器:^是前缀满足，*是包含即可，$后尾满足
	private static String createPropertyPattern(TagSelectorPair pair, String selectorTagName){
		String extra = pair.getExtra();
		String front = "";
		String back = "";
		if(extra.equals("")){
			
		}else if(extra.equals("^")){
			back = "[^\"']*";
		}else if(extra.equals("$")){
			front = "[^\"']*";
		}else if(extra.equals("*")){
			front = "[^\"']*";
			back = "[^\"']*";
		}
		return "<"+selectorTagName+"[^.>]*"+pair.getKey()+"=[\"|']"+front+pair.getValue()+back+"[\"|'][^>]*>";
	}
	
	//判断此tag是否同时满足全部的TagSelectorPair
	private static boolean isFullfilPropertyPattern(List<TagSelectorPair> listPairs, String tag, String selectorTagName){
		for(TagSelectorPair pair : listPairs){
			if(!tag.matches(createPropertyPattern(pair,selectorTagName))){
				return false;
			}
		}
		return true;
	}
	
	//打包单个元素标签的全部属性
	private static Map<String, String> packTagProperties(String wholeTag){
		Map<String, String> properties = new HashMap<String, String>();
		Matcher subMatcher = propertyPattern.matcher(wholeTag);
		int subSequenceIndex = 0;
		while(subMatcher.find(subSequenceIndex)){
			properties.put(subMatcher.group(1), subMatcher.group(3));
			subSequenceIndex = subMatcher.end(0);
		}
		return properties;
	}
	
	private static List<String> packTagClasses(String classes){
		if(classes!=null&&!classes.equals("")){
			return Arrays.asList(classes.split("\\s"));
		}else{
			return null;
		}
	}
	
	//打包元素的html内容
	private static String packTagHtml(String tagName, String content, int startIndex){
		Stack<Byte> stack = new Stack<Byte>();
		Pattern sameTagPattern = Pattern.compile("<(/)?"+tagName+"[^>]*?>");
		Matcher subMatcher1 = sameTagPattern.matcher(content.substring(startIndex));
		int i = 0;
		while(subMatcher1.find(i)){
			if(subMatcher1.group(1)!=null){
				try{
					stack.pop();
				}catch(EmptyStackException exception){
					i = subMatcher1.start(0);
					break;
				}
			}else{
				stack.push((byte)0);
			}
			i = subMatcher1.end(0);
		}
		return content.substring(startIndex, i+startIndex);
	}
	
	//打包去掉html标签的text
	private static String packTagText(String html){
		Matcher subMatcher2 = removeTagPattern.matcher(html);
		return subMatcher2.replaceAll("");
	}
	
	//找到直接的孩子，然后返回
	public static List<DomElement> findAllDirectChildrenAndSelect(String html, String selector){
		Matcher matcher = allTagFindPattern.matcher(html);
		int sequenceIndex = 0;
		Vector<Tag> tags = new Vector<Tag>();
		while(matcher.find(sequenceIndex)){
			Tag tag = new Tag();
			tag.setTag(matcher.group(0));
			tag.setTagName(matcher.group(2));
			tag.setEnd((matcher.group(1)!=null&&!matcher.group(1).equals("")));
			tag.setTagEndIndex(matcher.end(0));
			tags.add(tag);
			sequenceIndex = matcher.end(0);
		}
		List<Tag> children = new ArrayList<Tag>();
		Stack<Tag> stack = new Stack<Tag>();
		for(int i = tags.size()-1; i >= 0; i--){
			Tag tag = tags.get(i);
			//System.out.println(tag.getTagName()+"\t"+i);
			if(tag.isEnd()){
				stack.push(tag);
			}else{
				if(stack.isEmpty()){
					children.add(0, tag);
				}else{
					if(stack.peek().getTagName().equals(tag.getTagName())&&stack.peek().isEnd()){
						stack.pop();
						if(stack.size()==0){
							children.add(0, tag);
						}
					}
					
				}
			}
		}
		
		return singleTagSelector(selector, children, html);
	}
	
	//单一标签的正则判断，而非全文搜索，需要htmlContent来获得标签的html内容以及text
	private static List<DomElement> singleTagSelector(String selector, List<Tag> tags, String htmlContent){
		String filterString = null;
		int filterStart = selector.lastIndexOf(":");
		if(filterStart!=-1){
			filterString = selector.substring(filterStart+1);
			selector = selector.substring(0, filterStart);
			//System.out.println(filterString);
		}
		
		
		Filter filter = createFilter(filterString);
		
		
		
		String regexPattern = null;
		List<DomElement> domElements = new ArrayList<DomElement>();
		if(selector.startsWith(CLASS_SELECTOR)){
			String className = selector.substring(1);
			regexPattern = "<([a-z|A-Z]+)[^>]*class=(\\\"((.*?\\s+)?"+className+"(\\s+.*?)?)\\\"|"+className+")[^>]*?>";;
			for(Tag tag : tags){
				if(tag.getTag().matches(regexPattern)){
					DomElement domElement = new DomElement();
					domElement.setTag(tag.getTag());
					domElement.setTagName(tag.getTagName());
					domElement.setAttributes(packTagProperties(tag.getTag()));
					domElement.setClasses(packTagClasses(domElement.getAttributes().get("class")));
					domElement.setHtml(packTagHtml(tag.getTagName(), htmlContent, tag.getTagEndIndex()));
					domElement.setText(packTagText(domElement.getHtml()));
					domElements.add(domElement);
				}
			}
			
		}else if(selector.startsWith(ID_SELECTOR)){
			String id = selector.substring(1);
			regexPattern = "<([a-z|A-Z]+)[^>]*id=(\\\"("+id+")\\\"|"+id+")[^>]*?>";
			for(Tag tag : tags){
				if(tag.getTag().matches(regexPattern)){
					DomElement domElement = new DomElement();
					domElement.setTag(tag.getTag());
					domElement.setTagName(tag.getTagName());
					domElement.setAttributes(packTagProperties(tag.getTag()));
					domElement.setClasses(packTagClasses(domElement.getAttributes().get("class")));
					domElement.setHtml(packTagHtml(tag.getTagName(), htmlContent, tag.getTagEndIndex()));
					domElement.setText(packTagText(domElement.getHtml()));
					domElements.add(domElement);
				}
			}
		}else {
			String[] parts = selector.split("\\[|\\]");
			String selectorTagName = parts[0];
			List<TagSelectorPair> listPairs = new ArrayList<TagSelectorPair>();
			
			if(parts.length>1){
				//这个pattern用来分析每一个选择器的键值对
				Pattern selectorPairPattern = Pattern.compile("((\\w+-?)+)([$^*]?)=[\"|']?([^\"']*)[\"|']?"); 
				for(int i = 1; i < parts.length; i++){
					if(parts[i].trim().equals("")){
						continue;
					}
					Matcher matcher = selectorPairPattern.matcher(parts[i]);
					TagSelectorPair pair = new TagSelectorPair();
					if(matcher.find()){
						pair.setKey(matcher.group(1));
						pair.setValue(matcher.group(4));
						pair.setExtra(matcher.group(3));
					}
					listPairs.add(pair);
				}
			}
			
			for(Tag tag : tags){
				if(isFullfilPropertyPattern(listPairs, tag.getTag(), selectorTagName)){
					DomElement domElement = new DomElement();
					domElement.setTag(tag.getTag());
					domElement.setTagName(tag.getTagName());
					domElement.setAttributes(packTagProperties(tag.getTag()));
					domElement.setClasses(packTagClasses(domElement.getAttributes().get("class")));
					domElement.setHtml(packTagHtml(tag.getTagName(), htmlContent, tag.getTagEndIndex()));
					domElement.setText(packTagText(domElement.getHtml()));
					domElements.add(domElement);
				}
			}
			
			
		}

		if(filter!=null){
			return filter.filterList(domElements);
		}else{
			return domElements;
		}
	}
	
	private static Filter createFilter(String filterString){
		if(filterString==null||filterString.equals("")){
			return null;
		}
		//列出支持的filter
		//eq(index)  只返回下标为....index的
		//lt(index)  只返回下标小于...index的
		//gt(index)  只返回下标大于...index的
		//contains(text) 只返回text包含...text的
		//empty  只返回html为空的
		//even   只返回下标为偶数的,0,2,4,6,8
		//first  只返回第一个
		//last   只返回第二个
		Pattern filterPattern = Pattern.compile("([a-z]+)(\\(([^\\)]+)\\))?");
		Matcher filterMatcher = filterPattern.matcher(filterString);
				
		if(filterMatcher.find()){
			return new Filter(filterMatcher.group(1), filterMatcher.group(3));
		}else {
			return null;
		}
	}
	
}
