package com.lifedjtu.jw.util;

public class LifeDjtuEnum {
	public static enum ResultState {
		FAIL, NEEDLOGIN, SUCCESS;
		public static ResultState valueOf(int value){
			switch (value) {
			case 0:
				return FAIL;
			case 1:
				return NEEDLOGIN;
			case 2:
				return SUCCESS;
			default:
				return FAIL;
			}
		}
	}
	
	public static enum UserGender {
		MALE, FEMALE,NOT_CLEAR;
		public static UserGender valueOf(int value){
			switch (value) {
			case 0:
				return MALE;
			case 1:
				return FEMALE;
			default:
				return NOT_CLEAR;
			}
		}
	}
}
