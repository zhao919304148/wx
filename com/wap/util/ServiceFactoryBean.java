package com.wap.util;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ServiceFactoryBean {
	private static WebApplicationContext context;
    private static ServletContext sc;

    public static void initServiceFactory(ServletContext servletContext) {
		context = WebApplicationContextUtils
			.getRequiredWebApplicationContext(servletContext);
		sc = servletContext;
    }

    public static Object getService(ServletContext servletContext,
	    String serviceName) {
    	return context.getBean(serviceName);
    }

    public static Object getService(String serviceName) {
    	return context.getBean(serviceName);
    }
    
    public static WebApplicationContext getContext(){
    	return context;
    }
    
    public static ServletContext getServletContext(){
    	return sc;
    }
	
}
