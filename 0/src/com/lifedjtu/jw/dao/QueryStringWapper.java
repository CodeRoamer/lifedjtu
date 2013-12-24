package com.lifedjtu.jw.dao;

public class QueryStringWapper {
	private String queryString;
	
	private String preString;
	
	private String sufString;

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getPreString() {
		return preString;
	}

	public void setPreString(String preString) {
		this.preString = preString;
	}

	public String getSufString() {
		return sufString;
	}

	public void setSufString(String sufString) {
		this.sufString = sufString;
	}
	
	
	public QueryStringWapper() {
		super();
		preString = new String();
		sufString = new String();
	}

	public static QueryStringWapper instance(){
		return new QueryStringWapper();
	}
	
	public QueryStringWapper select(String field){
		if(preString.isEmpty()){
			preString += " select " + field;
		}
		else{
			preString += " , " + field;
		}
		return this;
	}
	
	public QueryStringWapper equal(String field, int num){
		if(sufString.isEmpty()){
			sufString += " where " + field + " = " + num;
		}
		else{
			sufString += " and " + field + " = " + num;
		}
		return this;
	}
	
	public QueryStringWapper equal(String field, double num){
		if(sufString.isEmpty()){
			sufString += " where " + field + " = " + num;
		}
		else{
			sufString += " and " + field + " = " + num;
		}
		return this;
	}
	
	public QueryStringWapper equal(String field, String value){
		if(sufString.isEmpty()){
			sufString += " where " + field + " = " + value;
		}
		else{
			sufString += " and " + field + " = " + value;
		}
		return this;
	}
	
	public QueryStringWapper lt(String field, int num){
		if(sufString.isEmpty()){
			sufString += " where " + field + " > " + num;
		}
		else{
			sufString += "and " + field + " > " + num;
		}
		return this;
	}
	
	public QueryStringWapper lt(String field, double num){
		if(sufString.isEmpty()){
			sufString += " where " + field + " > " + num;
		}
		else{
			sufString += " and " + field + " > " + num;
		}
		return this;
	}
	
	public QueryStringWapper st(String field, int num){
		if(sufString.isEmpty()){
			sufString += " where " + field + " < " + num;
		}
		else{
			sufString += " and " + field + " < " + num;
		}
		return this;
	}
	
	public QueryStringWapper st(String field, double num){
		if(sufString.isEmpty()){
			sufString += " where " + field + " < " + num;
		}
		else{
			sufString += " and " + field + " < " + num;
		}
		return this;
	}
	
	public QueryStringWapper build(){
		queryString = preString + sufString;
		return this;
	}
}
