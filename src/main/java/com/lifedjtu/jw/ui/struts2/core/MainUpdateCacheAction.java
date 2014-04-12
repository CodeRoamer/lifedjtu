package com.lifedjtu.jw.ui.struts2.core;

import com.lifedjtu.jw.business.task.JWUpdateCacheScheduler;
import com.lifedjtu.jw.util.security.Crypto;
import com.opensymphony.xwork2.Action;

public class MainUpdateCacheAction {
	private String remoteKey;
	private boolean status;
	
	private JWUpdateCacheScheduler jwUpdateCacheScheduler;
	
	public String updateRoomInfo(){
		if(Crypto.validateCronKey(remoteKey)){
			status = true;
			jwUpdateCacheScheduler.updateRoomInfo("");
		}else{
			status = false;
		}
		
		return Action.SUCCESS;
	}

	
	
	
	
	
	public String getRemoteKey() {
		return remoteKey;
	}

	public void setRemoteKey(String remoteKey) {
		this.remoteKey = remoteKey;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public JWUpdateCacheScheduler getJwUpdateCacheScheduler() {
		return jwUpdateCacheScheduler;
	}

	public void setJwUpdateCacheScheduler(
			JWUpdateCacheScheduler jwUpdateCacheScheduler) {
		this.jwUpdateCacheScheduler = jwUpdateCacheScheduler;
	}

}
