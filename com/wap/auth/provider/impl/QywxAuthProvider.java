package com.wap.auth.provider.impl;

import java.io.IOException;
import java.util.List;

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

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.wap.auth.exception.AuthenticationException;
import com.wap.auth.provider.QywxAbstractAuthProvider;
import com.wap.util.ConstantUtils;

public class QywxAuthProvider extends QywxAbstractAuthProvider {

	public static final Log logger = LogFactory.getLog(QywxAuthProvider.class);
	@Autowired
	private JedisPool jedisPool;
	
	private List<String> excludeMappings;
	
	public List<String> getExcludeMappings() {
		return excludeMappings;
	}
	public void setExcludeMappings(List<String> excludeMappings) {
		this.excludeMappings = excludeMappings;
	}
	
	@Override
	public boolean doAuthenticate(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		/*
		 * 判断票据的合法性：是否为有效的_access,_sign
		 * _access：用户获取redis的ssesion
		 * _sign:防止_access被恶意篡改
		 */
		String ticket = request.getParameter(ConstantUtils.QYWX_ACCESS_TICKET);
		String ticketSign = request.getParameter(ConstantUtils.QYWX_ACCESS_SIGN);
		String ticket_id = request.getParameter(ConstantUtils.QYWX_TICKET_ID);
		
		Jedis jedis = null;
		String tmpTicketSign = "";
		
		logger.info("[企业微信授权认证拦截]-["+ConstantUtils._ACCESS_TICKET+":"+ticket+"]-["+ConstantUtils._TICKET_SIGN+":"+ticketSign+"]");
		if(StringUtils.isBlank(ticket) && StringUtils.isBlank(ticketSign)){
			//正常从菜单上点击事件过来，都没有这两个参数
			//跳转至我们的微信授权页面/auth/go?redirectUrl=[当前页面]，只处理get请求
			ServletContext servletContext = request.getSession().getServletContext();
			String httpBasePath = (String) servletContext.getAttribute("httpBasePath");
			String redirectUrl = httpBasePath.substring(0,httpBasePath.length()-1)+request.getServletPath();
			String queryStr = request.getQueryString();
			if(StringUtils.isNotBlank(queryStr)){
				redirectUrl = redirectUrl +"?"+queryStr;
			}
			redirectUrl = httpBasePath+"qywxauth/qywxAuth?redirectUrl="+redirectUrl;
			logger.info("企业微信授权认证，无认证票据与签名，需进行微信认证授权获取userId-[redirectUrl:"+redirectUrl+"]");
			this.redirectRquest(request, response, redirectUrl);
			return false;
		}else if(StringUtils.isBlank(ticket)||StringUtils.isBlank(ticketSign)){//参数错误
			throw new AuthenticationException("-04", "参数错误：票据或签名不存在");
		}else{
			//票据不是空
			Assert.isTrue(StringEscapeUtils.escapeHtml(ticket).equals(ticket));
			Assert.isTrue(StringEscapeUtils.escapeHtml(ticketSign).equals(ticketSign));
			//验证票据有效性，如果票据有效，放行
			try{
				jedis = jedisPool.getResource();
				tmpTicketSign = jedis.get(ticket);
				if(StringUtils.isNotBlank(tmpTicketSign)){
					if(ticketSign.equals(tmpTicketSign)){
						//验证通过,需要从redis中把本次的访问票据删除
						jedis.del(ticket);
						request.setAttribute(ConstantUtils.QYWX_ACCESS_TICKET, ticket);
						request.setAttribute(ConstantUtils.QYWX_ACCESS_SIGN, ticketSign);
						request.setAttribute(ConstantUtils.QYWX_TICKET_ID, ticket_id);
					}else{
						//验证失败
						logger.info("企业微信签名验证异常");
						throw new AuthenticationException("-701", "非法操作");
					}
				}else{
					logger.info("企业微信本次访问票据已过期");
					throw new AuthenticationException("-702", "票据过期，请重新访问");
				}
			}catch(Exception e){
				e.printStackTrace();
				throw new AuthenticationException("-703", e.getMessage());
			}finally{
				if(jedis != null){
					jedis.close();
				}
			}
		}
		return true;
	}
	
	/**
	 * 描述:重定向
	 * @param req
	 * @param res
	 * @param url
	 * @author 吕亮2018年4月23日 下午5:07:24
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
