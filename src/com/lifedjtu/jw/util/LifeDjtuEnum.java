package com.lifedjtu.jw.util;

public class LifeDjtuEnum {
	
	public static enum PrivateInfoPolicy {
		CLOSE_TO_ALL,OPEN_TO_FRIENDS,  OPEN_TO_ALL
	}
	
	public static enum GroupFlag {
		SAME_CLASS,SAME_COURSE;
	}
		
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
	
	public static enum ExamStatus{
		CHONG_XIU("重修考试"), BU_HUAN("补缓考试"), ZHENG_CHANG("正常考试"), ER_CI("二次考试");
		
		private String description;
		
		private ExamStatus(String description){
			this.description = description;
		}
		
		
		public static ExamStatus fromString(String description){
			if(description.equals(CHONG_XIU.toString())){
				return CHONG_XIU;
			}else if(description.equals(BU_HUAN.toString())){
				return BU_HUAN;
			}else if(description.equals(ZHENG_CHANG.toString())){
				return ZHENG_CHANG;
			}else if(description.equals(ER_CI.description)){
				return ER_CI;
			}else {
				System.err.println("exam status cannot find right mappings for the givin string!");
				return ZHENG_CHANG;
			}
		}
		
		@Override
		public String toString(){
			return description;
		}
		
	}
	
	
	
	
}
