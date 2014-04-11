package com.lifedjtu.jw.util.extractor;

import java.util.List;
import java.util.Map;

import com.lifedjtu.jw.pojos.EntityObject;


public class DomElement extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1306929705282307030L;
	//当前Dom对象的html内容
	private String html;
	//当前Dom对象的去掉html标签内容，纯text
	private String text;
	//当前Dom对象的class集合
	private List<String> classes;
	//当前Dom对象的属性键值对，多个属性值也存到了一个string字符串中
	private Map<String,String> attributes;
	//标签的名字
	private String tagName;
	//标签的开始标签全部内容
	private String tag;
	
	

	//获取某个属性值
	public String attr(String name){
		return attributes.get(name);
	}
	//是否拥有这个class
	public boolean hasClass(String name){
		return classes.contains(name);
	}
	//同attr
	public String prop(String name){
		return attributes.get(name);
	}
	//获取当前dom对象的value属性的值，适合全部的input标签以及select，textarea标签等
	public String val(){
		return attributes.get("value");
	}
	//在当前dom体中寻找符合传入选择器的全部标签
	public List<DomElement> find(String selector){
		return Extractor.$(selector, html);
	}
	//在当前dom体中寻找符合传入选择器的直接子标签
	public List<DomElement> children(String selector){
		return Extractor.findAllDirectChildrenAndSelect(html, selector);
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<String> getClasses() {
		return classes;
	}
	public void setClasses(List<String> classes) {
		this.classes = classes;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	
}
