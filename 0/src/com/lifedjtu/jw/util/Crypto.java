package com.lifedjtu.jw.util;

import java.util.UUID;


public class Crypto {
	public static String encodeAES(String password) throws Exception{
		return EncryptAES.encode(password);
	}
	
	public static String decodeAES(String encodedPass) throws Exception{
		return EncryptAES.decode(encodedPass);
	}
	
	public static String randomPrivateKey(){
		return EncryptMD5.MD5Encode(UUID.randomUUID().toString());
	}
	
	public static String cypherDynamicPassword(String privateKey,long time){
		
		return privateKey;
	}
	
	public static boolean validateCronKey(String remoteKey){
		if(remoteKey!=null&&remoteKey.equals("1234")){
			return true;
		}else{
			return false;
		}
	}
}
