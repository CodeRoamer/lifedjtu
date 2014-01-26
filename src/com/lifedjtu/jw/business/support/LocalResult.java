package com.lifedjtu.jw.business.support;


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
}
