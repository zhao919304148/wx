package com.wap.auth.provider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wap.auth.exception.AuthenticationException;

public abstract class AbstractAuthProvider {
	/**
	 * 
			* 描述:
			* 	用户权限校验
			* @param request
			* @param response
			* @return
			* 		返回true,继续校验；返回false,校验失败
			* @throws AuthenticationException
			* @author xbz2017年6月20日 上午9:29:40
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
