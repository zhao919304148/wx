package com.wap.util;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SessionContext {
	private static final Log logger = LogFactory.getLog(SessionContext.class);
	private static SessionContext instance;
	private static HashMap<String, HttpSession> sessionMap;

	private SessionContext() {
		sessionMap = new HashMap<String, HttpSession>();
	}

	public static SessionContext getInstance() {
		if (instance == null) {
			instance = new SessionContext();
		}
		return instance;
	}

	public synchronized void addSession(HttpSession session) {
		if (session != null) {
			sessionMap.put(session.getId().substring(0,32), session);
			logger.error("addSession:"+session.getId());
		}
	}

	public synchronized void removeSession(HttpSession session) {
		if (session != null) {
			sessionMap.remove(session.getId().substring(0,32));
			logger.error("removeSession:"+session.getId());
		}
	}

	public synchronized HttpSession getSession(String session_id) {
		if (session_id == null)
			return null;
		return (HttpSession) sessionMap.get(session_id);
	}

	public synchronized HttpSession getSessionFromRequest(HttpServletRequest request) {
		if (request == null)
			return null;
		if (request.getParameter("JSESSIONID") == null){
			request.setAttribute("JSESSIONID", request.getSession().getId().substring(0,32));
			return request.getSession();
		}
		HttpSession session = (HttpSession) sessionMap.get(request.getParameter("JSESSIONID"));
		if(session==null){
			session = request.getSession();
			request.setAttribute("JSESSIONID", session.getId().substring(0,32));
		}else{
			session.setMaxInactiveInterval(1800);
			//如果SessionContext中存在有效session,则销毁本次请求时,服务器自动创建的session对象.
			request.getSession().setMaxInactiveInterval(10);
		}
		return session;
	}
}
