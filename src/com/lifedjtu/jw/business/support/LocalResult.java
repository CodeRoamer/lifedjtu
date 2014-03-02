package com.lifedjtu.jw.business.support;

import com.lifedjtu.jw.util.LifeDjtuEnum.ResultState;


public class LocalResult<T> {
	private T result;
	private int resultState;
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	public int getResultState() {
		return resultState;
	}
	public void setResultState(int resultState) {
		this.resultState = resultState;
	}
	
	public void autoFill(T obj){
		if(obj==null){
			result = null;
			resultState = ResultState.FAIL.ordinal();
		}else {
			if(obj instanceof Boolean && !(Boolean)obj){
				result = null;
				resultState = ResultState.FAIL.ordinal();
			}else{
				result = obj;
				resultState = ResultState.SUCCESS.ordinal();
			}
		}
	}
}
