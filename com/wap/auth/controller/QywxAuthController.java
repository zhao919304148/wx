package com.wap.auth.controller;

import java.net.URLDecoder;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSONObject;
import com.sys.redis.RedisSessionService;
import com.wap.qywx.service.ReportService;
import com.wap.trans.entity.tr_1071.ReqTrans1071Entity;
import com.wap.util.ConstantUtils;
import com.wap.util.SignUtil;
import com.wx.util.WeixinUtil;

@Controller
@RequestMapping("qywxauth")
public class QywxAuthController {
	
	public static final Log logger = LogFactory.getLog(QywxAuthController.class);
	@Autowired
	private RedisSessionService sessionService;
	@Autowired
	private JedisPool jedisPool;
	
	@Autowired
	private ReportService reportService;
	
	@ResponseBody
	@RequestMapping(value="qywxAuthUserId",produces={"text/html;charset=utf-8"})
	public String qywxUserAuth(HttpServletRequest request,HttpServletResponse response,String para,String code,String state){
		
		Jedis jedis = null;
		String access_ticket = "";
		String ticket_sign = "";
		
		String ticket_id = "";
		String errmsg = "";
		
		String[] uri = null;
		String method = "";
		String[] pathmethod = null;
		
		ReqTrans1071Entity reqTrans1071Entity = null;
		com.alibaba.fastjson.JSONObject res = null;
		String retcode = "";
		
		String queryString = request.getQueryString();
		para = "";
		para=queryString.substring(queryString.indexOf("para=")+5,queryString.indexOf("&code"));
		if(StringUtils.isBlank(para)){
			throw new RuntimeException("para is must not empty");
		}
		if(StringUtils.isBlank(code)){
			throw new RuntimeException("code is must not empty");
		}
		try {
            //根据code获取userid,根据userid判断是否有访问报表的权限
			Map<String, String> resMap = WeixinUtil.getQywxUserId(code);
			String userid = resMap.get("UserId");
			if(StringUtils.isNotBlank(userid)){
				//验证通过
				JSONObject paraJson = JSONObject.parseObject(URLDecoder.decode(para, "utf-8"));
				String redirectUrl = paraJson.getString("redirectUrl");
				request.setAttribute("redirectUrl", redirectUrl);
				
				//用户权限验证
				if(StringUtils.isNotBlank(redirectUrl) && redirectUrl.indexOf("bjwxplat") != -1){
					uri = redirectUrl.split("bjwxplat");
					method = uri[1];
					if(StringUtils.isNotBlank(method) && method.indexOf("?") != -1){
						pathmethod = method.split("\\?");
						method = pathmethod[0];
					}
					if(StringUtils.isNotBlank(method)){
						reqTrans1071Entity = new ReqTrans1071Entity();
						reqTrans1071Entity.setUserid(userid);
						reqTrans1071Entity.setMethod(method);
						
						res = reportService.qywxUserAuth(reqTrans1071Entity);
						if(res != null){
							retcode = (String)res.get("retcode");
							if("1".equals(retcode)){
								//有权限
								jedis = jedisPool.getResource();
								//生成票据及签名
								access_ticket = UUID.randomUUID().toString().toUpperCase();
								access_ticket = access_ticket.replaceAll("-", "");
								ticket_sign = SignUtil.generateSign(access_ticket);
								jedis.set(access_ticket, ticket_sign);
								jedis.expire(access_ticket, 20);
								//将userid也存入redis
								ticket_id = SignUtil.generateSign(userid);
								jedis.set(ticket_id, userid);
								jedis.expire(ticket_id, 20);
								
								logger.info("企业微信生成的访问票据ticket:" + access_ticket + ",sign:" + ticket_sign);
								//重定向链接
								request.setAttribute(ConstantUtils.QYWX_ACCESS_TICKET, access_ticket);
								request.setAttribute(ConstantUtils.QYWX_ACCESS_SIGN, ticket_sign);
								request.setAttribute(ConstantUtils.QYWX_TICKET_ID, ticket_id);
								redirectUrl = this.handleRedirectUrl(redirectUrl,request);
								logger.info("redirectUrl : " + redirectUrl);
								request.setAttribute("redirectUrl", redirectUrl);
								request.getRequestDispatcher("/dzsw/redirectUrl.jsp").forward(request, response);
								return null;
							}else{
								//没有权限
								errmsg = "您没有访问该功能的权限！";
								return errmsg;
							}
						}else{
							errmsg = "您没有访问该功能的权限！";
							return errmsg;
						}
					}else{
						errmsg = "非法访问！";
						return errmsg;
					}
				}else{
					errmsg = "非法访问！";
					return errmsg;
				}
			}else{
				errmsg = "您没有访问该功能的权限！";
				return errmsg;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}finally{
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	/**
	 * 
	 * 描述:
	 * 	处理将重定向的链接地址
	 * @param url
	 * @return
	 * @author xbz2017年6月19日 下午1:42:52
	 */
	private String handleRedirectUrl(String url,HttpServletRequest request) throws Exception{
		//截取重定向链接地址，不包含请求参数
		int idxof = url.indexOf('?');
		String redirectUri = url;
		if(idxof!=-1){
			redirectUri = url.substring(0,idxof);
		}
		if(StringUtils.isNotBlank(redirectUri)){
			String queryStr = "";
			//判断路径参数是否存在票据和签名
			int indexOf = url.indexOf("?");
			//如果存在参数则去掉参数中包含的票据和签名
			if(indexOf!=-1){
				queryStr=url.substring(indexOf+1);
				if(queryStr.contains(ConstantUtils.QYWX_ACCESS_TICKET)){
					queryStr=queryStr.replaceAll("&?"+ConstantUtils.QYWX_ACCESS_TICKET+"=[^&]*&?", "&");
				}
				if(queryStr.contains(ConstantUtils.QYWX_ACCESS_SIGN)){
					queryStr=queryStr.replaceAll("&?"+ConstantUtils.QYWX_ACCESS_SIGN+"=[^&]*&?", "&");
				}
			}
			queryStr=queryStr.replaceAll("(^&+|&+$)", "");
			//添加新的票据和签名
			String preffix = ConstantUtils.QYWX_ACCESS_TICKET+"="+(String) request.getAttribute(ConstantUtils.QYWX_ACCESS_TICKET)
					+"&"+ConstantUtils.QYWX_ACCESS_SIGN+"="+(String) request.getAttribute(ConstantUtils.QYWX_ACCESS_SIGN)
					+"&"+ConstantUtils.QYWX_TICKET_ID+"="+(String) request.getAttribute(ConstantUtils.QYWX_TICKET_ID);
			if(StringUtils.isNotBlank(queryStr)){
				queryStr=preffix+"&"+queryStr;
			}else{
				queryStr=preffix;
			}
			
			return redirectUri+"?"+queryStr;
		}
		return null;
	}
	
	
	@RequestMapping("/qywxAuth")
	public String toQywxAuth(HttpServletRequest request,HttpServletResponse response){
		String httpBasePath = (String) request.getSession().getServletContext().getAttribute("httpBasePath");
		String queryString = request.getQueryString();
		Assert.isTrue(StringUtils.isNotBlank(queryString), "参数不能为空");
		String redirectUrl=queryString.substring(queryString.indexOf("redirectUrl=")+12);
		//从这个redirectUrl中取到具体的报表的名称，如报表1(report1),做授权的时候，作为state参数传过去
		try {
			JSONObject para=new JSONObject();
			para.put("redirectUrl", redirectUrl);
			redirectUrl =  httpBasePath+"qywxauth/qywxAuthUserId?para="+para.toJSONString();
			//拼接微信授权跳转链接
			String srcUrl = WeixinUtil.getQywxAuthRequestUrl(redirectUrl,"test");
			logger.info("即将进行企业微信授权跳转至："+srcUrl);
			request.setAttribute("redirectUrl", srcUrl);
			return "dzsw/redirectUrl";
		} catch (Exception e) {
			throw new RuntimeException("授权链接转换失败");
		}
	}

}
