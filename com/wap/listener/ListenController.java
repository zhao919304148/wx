package com.wap.listener;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.HttpSessionMutexListener;

import com.wap.main.entity.LoginReportEntity;

import core.db.dao.IBaseService;

public class ListenController extends HttpSessionMutexListener implements ServletContextListener{
	private ApplicationContext appContext;
	private ServletContext servletContext;
	
	ApplicationContext getContext()
    {
        if(appContext == null)
            appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        return appContext;
    }
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		super.sessionCreated(event);
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		super.sessionDestroyed(event);
		LoginReportEntity loginReportEntity  = (LoginReportEntity) event.getSession().getAttribute("loginReportEntity");
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String strCurrentDateTime= formatter.format(new Date());//获取当前系统时间  年月日时分秒
	    if(loginReportEntity != null){
	    	
	    	try {
	    		loginReportEntity.setLogouttime(formatter.parse(strCurrentDateTime));//登出时间
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	IBaseService baseService = (IBaseService)appContext.getBean("baseService");
	    	baseService.update(loginReportEntity);
	    	
	    }
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		appContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        if(appContext == null)
        {
            servletContext = event.getServletContext();
        }
	}
}
