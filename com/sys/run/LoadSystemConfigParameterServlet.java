package com.sys.run;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.digester.Digester;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.commman.transmanager.service.TransManager;
import com.sys.dic.SysDicHelper;

//import core.apps.dic.SysDicHelper;


public class LoadSystemConfigParameterServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5130241834911695773L;
		
	
	/**
	 * 初始化过程
	 */
	public void init(ServletConfig config) throws  ServletException{
		String filePathName = config.getInitParameter("filepathname");
		String homePath = (config.getServletContext()).getRealPath("");
        //log4j配置  
		PropertyConfigurator.configure(homePath + "/WEB-INF/classes/log4j.properties");//加载.properties文件
		SystemParameter systemParameter = SystemParameter.getInstance();		
		systemParameter.setSystemHomePath(homePath);
		
		try{
			Digester digester = new Digester();
			digester.push( systemParameter );
            //通讯子系统参数配置 			
			digester.addBeanPropertySetter("system/commmanager/serverno", "serverNo");    //本地服务序号
			digester.addBeanPropertySetter("system/commmanager/queueSize", "queueSize");  		//交易队列长度限制
			digester.addBeanPropertySetter("system/commmanager/tradefile", "tradeFile");        //交易文件配置文件
			digester.addBeanPropertySetter("system/commmanager/commfile", "commFile");        //交易通讯区配置
			digester.parse(new File(homePath + filePathName));
			systemParameter.setTradeFile(homePath + systemParameter.getTradeFile());
			systemParameter.setCommFile(homePath + systemParameter.getCommFile());
			//初始化系统字典的content
			config.getServletContext() ;
	        //做一下字典中WebApplicationContext 加载
			ServletContext servletContext = config.getServletContext() ;// request.getSession().getServletContext();   
			WebApplicationContext wac = WebApplicationContextUtils.   
			            getRequiredWebApplicationContext(servletContext);  
			SysDicHelper.getInstance().setApplicationContext(wac); 
			servletContext.setAttribute("httpBasePath", SysDicHelper.getInstance().getDicContentByDicTypeAndDicId("HTTPBASEPATH", "picc01").getIdValue());
//			//本地
			servletContext.setAttribute("httpBasePath", "http://localhost:7001/bjwxplat/");
		}catch( Exception e){
			throw new ServletException(e);
		}
	}
}
