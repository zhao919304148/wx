package com.wap.auth.provider.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wap.auth.exception.AuthenticationException;
import com.wap.auth.provider.QywxAbstractAuthProvider;

public class QywxTicketAuthProvider extends QywxAbstractAuthProvider {

	@Override
	public boolean doAuthenticate(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		// TODO Auto-generated method stub
		return false;
	}

}
