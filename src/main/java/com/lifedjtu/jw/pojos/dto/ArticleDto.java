package com.lifedjtu.jw.pojos.dto;

import com.lifedjtu.jw.pojos.EntityObject;

public class ArticleDto extends EntityObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -627966521603383180L;
	
	private String title;
	private String content;
	private String releaseDate;
	private String href;
	private String readCount;
	private boolean important;
	
	
	
	public boolean isImportant() {
		return important;
	}
	public void setImportant(boolean important) {
		this.important = important;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getReadCount() {
		return readCount;
	}
	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
	public ArticleDto(String title, String content, String releaseDate,
			String href, String readCount) {
		super();
		this.title = title;
		this.content = content;
		this.releaseDate = releaseDate;
		this.href = href;
		this.readCount = readCount;
	}
	public ArticleDto() {}
}
