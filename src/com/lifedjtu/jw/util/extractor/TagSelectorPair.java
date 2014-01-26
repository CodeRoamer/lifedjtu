package com.lifedjtu.jw.util.extractor;

import com.lifedjtu.jw.pojos.EntityObject;

public class TagSelectorPair extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4321405513574601254L;
	private String key;
	private String value;
	private String extra;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public TagSelectorPair(String key, String value, String extra) {
		super();
		this.key = key;
		this.value = value;
		this.extra = extra;
	}
	public TagSelectorPair() {}
	
}
