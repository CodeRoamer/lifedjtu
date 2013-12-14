package com.lifedjtu.jw.util.extractor;

import java.util.List;
import java.util.Map;

import com.lifedjtu.jw.pojos.EntityObject;


public class DomElement extends EntityObject{
	private String html;
	private String text;
	private List<String> classes;
	private Map<String,String> attributes;
	private String tagName;
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
	
	public String attr(String name){
		return attributes.get(name);
	}
	
	public boolean hasClass(String name){
		return classes.contains(name);
	}
	
	public String prop(String name){
		return attributes.get(name);
	}
	
	public String val(){
		return attributes.get("value");
	}
	public List<DomElement> find(String selector){
		return Extractor.$(selector, html);
	}
}
