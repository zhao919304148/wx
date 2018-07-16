package com.wap.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sys.exception.EpiccException;
import com.wap.main.entity.LoginReportEntity;
import com.wap.main.entity.UserEntity;
import com.wap.main.service.MainService;


public class LoginFilter implements Filter {
	private static final Log logger = LogFactory.getLog(LoginFilter.class);
	private MainService mainService = null;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		logger.error("------->loginFilter:request>JSESSIONID:"+request.getParameter("JSESSIONID"));
		HttpSession session = request.getSession();
		String openId = request.getParameter("openId");
		Object openId2 = session.getAttribute("openId");
		logger.error("------->loginFilter:"+request.getRequestURI());
		if(request.getRequestURI().contains("login.jsp")||request.getRequestURI().contains("index.jsp")){
			chain.doFilter(req, res);
			return;
		}
		if(StringUtils.isNotBlank(openId) && openId2 != null && openId.equals(openId2.toString())){
			response.sendRedirect(request.getContextPath()+"/login.jsp?openId="+openId);
			return;
		}
		//从session中读取用户信息,判断是否已登录
		logger.error("----------session:"+session);
		logger.error("----------JSESSIONID:"+session.getId());
		UserEntity user = (UserEntity) session.getAttribute("loginUser");
		logger.error("----------user:"+user);
		if(user == null || user.getUsercode()== null){
			if(StringUtils.isBlank(openId)){
				response.sendRedirect(request.getContextPath()+"/error.jsp");
				return;
			}else{
				LoginReportEntity lr = mainService.getHisLoginUserByOpenId(openId);
				if(lr != null){
					if(!"0".equals(lr.getValidflag())){
						logger.error("登录拦截:有登录记录,且Validflag为"+lr.getValidflag()+"!");
						UserEntity userEntity = new UserEntity();
						userEntity.setUsercode(lr.getUsercode());
						userEntity.setPassword(lr.getPassword());
						//调用登录
						try {
							userEntity = mainService.login(userEntity);
							if(userEntity != null && userEntity.getUsercode() != null){//登录成功
								session.setAttribute("loginUser",userEntity);
								session.setAttribute("openId", openId);
								mainService.loginReport(userEntity, session);
								chain.doFilter(req, res);
							}else{//登录失败,重定向至登录页面
								response.sendRedirect(request.getContextPath()+"/login.jsp?openId="+openId);
								return;
							}
						} catch (EpiccException e) {
							e.printStackTrace();
							logger.error("loginFilter异常信息:"+e.getErrMess());
							response.sendRedirect(request.getContextPath()+"/login.jsp?openId="+openId);
							return;
						}
					}else{
						response.sendRedirect(request.getContextPath()+"/login.jsp?openId="+openId);
						return;
					}
				}else{
					response.sendRedirect(request.getContextPath()+"/login.jsp?openId="+openId);
					return;
				}
			}
		}else{
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		this.mainService = (MainService) wac.getBean("mainService");
	}

}
