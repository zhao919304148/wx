package com.wap.auth.provider.impl;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.sys.redis.RedisSessionService;
import com.wap.auth.exception.AuthenticationException;
import com.wap.auth.provider.AbstractAuthProvider;
import com.wap.util.ConstantUtils;
public class OpenIdAuthProvider extends AbstractAuthProvider{
	public static final Log logger = LogFactory.getLog(OpenIdAuthProvider.class);
	@Autowired
	private RedisSessionService sessionService;

	@Override
	public boolean doAuthenticate(HttpServletRequest request,HttpServletResponse response) throws AuthenticationException{
		/*
		 * 判断票据的合法性：是否为有效的_access,_sign
		 * _access：用户获取redis的ssesion
		 * _sign:防止_access被恶意篡改
		 */
		String ticket=request.getParameter(ConstantUtils._ACCESS_TICKET);
		String ticketSign=request.getParameter(ConstantUtils._TICKET_SIGN);
		logger.info("[openId认证拦截]-["+ConstantUtils._ACCESS_TICKET+":"+ticket+"]-["+ConstantUtils._TICKET_SIGN+":"+ticketSign+"]");
		if(StringUtils.isBlank(ticket)&&StringUtils.isBlank(ticketSign)){//首次登录，需要微信授权
			//跳转至我们的微信授权页面/auth/go?redirectUrl=[当前页面]，只处理get请求
			ServletContext servletContext = request.getSession().getServletContext();
			String httpBasePath = (String) servletContext.getAttribute("httpBasePath");
			String redirectUrl = httpBasePath.substring(0,httpBasePath.length()-1)+request.getServletPath();
			String queryStr = request.getQueryString();
			if(StringUtils.isNotBlank(queryStr)){
				redirectUrl = redirectUrl +"?"+queryStr;
			}
			redirectUrl = httpBasePath+"auth/go?redirectUrl="+redirectUrl;
			logger.info("无认证票据与签名，需进行微信认证授权获取openId-[redirectUrl:"+redirectUrl+"]");
			this.redirectRquest(request, response, redirectUrl);
			return false;
		}else if(StringUtils.isBlank(ticket)||StringUtils.isBlank(ticketSign)){//参数错误
			throw new AuthenticationException("-04", "参数错误：票据或签名不存在");
		}else{
			//tiket tiketSign 参数校验，防止xss
			Assert.isTrue(StringEscapeUtils.escapeHtml(ticket).equals(ticket));
			Assert.isTrue(StringEscapeUtils.escapeHtml(ticketSign).equals(ticketSign));
			//根据_ACCESS_TICKET取_TICKET_SIGN,校验票据的合法性
			String dbSign = sessionService.getString(ticket, ConstantUtils._TICKET_SIGN);
			if(dbSign==null){
				throw new AuthenticationException("-07", "本次会话已过期，请重新登录");
			}else if(!ticketSign.equals(dbSign)){
				throw new AuthenticationException("-05", "签名异常");
			}else{
				String openId = sessionService.getAtrribute(ticket, ConstantUtils.OPEN_ID);
				if(StringUtils.isNotBlank(openId)){
					//保存一些常用信息到本次线程上下文，以满足后续处理使用
					request.setAttribute(ConstantUtils._ACCESS_TICKET, ticket);
					request.setAttribute(ConstantUtils._TICKET_SIGN, ticketSign);
					request.setAttribute(ConstantUtils.OPEN_ID, openId);
					logger.info("当前线程变量：[ticket:"+ticket+",openid:"+openId+",sign:"+ticketSign+"]");
				}else{
					throw new AuthenticationException("-06","openId未得到");
				}
			}
		}
		//票据合法，放行
		return true;
	}

	/**
	 * 
	* 描述:
	* 		重定向请求
	* @param req
	* @author 许宝众2017年6月19日 上午11:06:20
	 */
	private void redirectRquest(HttpServletRequest req,HttpServletResponse res,String url){
		try {
			req.setAttribute("redirectUrl", url);
			req.getRequestDispatcher("/dzsw/redirectUrl.jsp").forward(req, res);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			try {
				res.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
		
	}
}
