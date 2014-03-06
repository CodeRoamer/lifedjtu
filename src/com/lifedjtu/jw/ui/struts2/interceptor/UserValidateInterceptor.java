package com.lifedjtu.jw.ui.struts2.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.lifedjtu.jw.business.JWLocalService;
import com.lifedjtu.jw.business.support.LocalResult;
import com.lifedjtu.jw.util.LifeDjtuEnum.ResultState;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class UserValidateInterceptor extends AbstractInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4864635152625782030L;
	
	private JWLocalService jwLocalService;
	
	public JWLocalService getJwLocalService() {
		return jwLocalService;
	}


	public void setJwLocalService(JWLocalService jwLocalService) {
		this.jwLocalService = jwLocalService;
	}


	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		boolean isWebservice = invocation.getProxy().getNamespace().contains("webservice");
		boolean isSecure = invocation.getProxy().getNamespace().contains("secure");
		
		if(isWebservice&&isSecure){
			String studentId = request.getParameter("studentId");
			String dynamicPass = request.getParameter("dynamicPass");
			
			if((studentId==null||studentId.equals(""))||(dynamicPass==null||dynamicPass.equals(""))){
				return "needLogin";
			}
			
			LocalResult<String> result = jwLocalService.prepareUser(studentId, dynamicPass);
			
			
			if(result.getResultState()==ResultState.SUCCESS.ordinal()){
				invocation.getStack().setValue("sessionId", result.getResult());
				return invocation.invoke();
			}else if(result.getResultState()==ResultState.NEEDLOGIN.ordinal()){
				return "needLogin";
			}else {
				return "fail";
			}
		}else{
			return invocation.invoke();
		}
		
		
		
	}

}
