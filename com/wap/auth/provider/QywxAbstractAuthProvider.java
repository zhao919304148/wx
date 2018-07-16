package com.wap.auth.provider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wap.auth.exception.AuthenticationException;

public abstract class QywxAbstractAuthProvider {
	
	/**
	 * 描述:企业微信授权认证
	 * @param request
	 * @param response
	 * @return
	 * @throws AuthenticationException
	 * @author 吕亮2018年4月23日 下午4:27:25
	 */
	public abstract boolean doAuthenticate(HttpServletRequest request,HttpServletResponse response) throws AuthenticationException;
	private int order;
	
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}

}
