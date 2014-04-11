package com.lifedjtu.jw.util.extractor;

import com.lifedjtu.jw.pojos.EntityObject;

public class Tag extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7849019969167509431L;
	private String tag;
	private String tagName;
	private boolean isEnd;
	private int tagEndIndex;
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public boolean isEnd() {
		return isEnd;
	}
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	public Tag(String tag, String tagName, boolean isEnd, int tagEndIndex) {
		super();
		this.tag = tag;
		this.tagName = tagName;
		this.isEnd = isEnd;
		this.tagEndIndex = tagEndIndex;
	}
	
	public int getTagEndIndex() {
		return tagEndIndex;
	}
	public void setTagEndIndex(int tagEndIndex) {
		this.tagEndIndex = tagEndIndex;
	}
	public Tag() {}
}
