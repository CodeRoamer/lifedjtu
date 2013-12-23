package com.lifedjtu.jw.util;

import java.util.UUID;


public class Crypto {
	public String encodeAES(String password) throws Exception{
		return EncryptAES.encode(password);
	}
	
	public String decodeAES(String encodedPass) throws Exception{
		return EncryptAES.decode(encodedPass);
	}
	
	public String randomPrivateKey(){
		return EncryptMD5.MD5Encode(UUID.randomUUID().toString());
	}
	
	public String cypherDynamicPassword(String privateKey,long time){
		
		return privateKey;
	}
}
