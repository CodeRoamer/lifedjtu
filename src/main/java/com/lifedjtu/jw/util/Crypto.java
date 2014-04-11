package com.lifedjtu.jw.util;

import java.util.UUID;


public class Crypto {
	//用于加密密码
	public static String encodeAES(String password){
		try {
			return EncryptAES.encode(password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//用于解密密码
	public static String decodeAES(String encodedPass){
		try {
			return EncryptAES.decode(encodedPass);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//用于生成用户密钥
	public static String randomPrivateKey(){
		return EncryptMD5.MD5Encode(UUID.randomUUID().toString());
	}
	//用于生产一次性动态口令，算法未想好，目前直接返回密钥，一定要使用此方法假装生产了一次性口令，方便今后的算法修改
	public static String cypherDynamicPassword(String privateKey,long time){
		
		return privateKey;
	}
	//验证调度密码，目前可以忽略
	public static boolean validateCronKey(String remoteKey){
		if(remoteKey!=null&&remoteKey.equals("1234")){
			return true;
		}else{
			return false;
		}
	}
}
