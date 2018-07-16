package com.wap.auth.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.Base64;
import com.sys.redis.RedisSessionService;
import com.wap.util.AESCode;
import com.wap.util.ConstantUtils;
import com.wap.util.RandomUtil;
import com.wap.util.WxSDKUtil;
import com.wx.util.WeixinUtil;

@Controller
@RequestMapping("auth")
public class WeiXinAuthController {
	public static final Log logger = LogFactory.getLog(WeiXinAuthController.class);
	@Autowired
	private RedisSessionService sessionService;
	@ResponseBody
	@RequestMapping(value="openid",produces={"text/html;charset=utf-8"})
	public String wxAuthTest(HttpServletRequest request,HttpServletResponse response,String code,String state){
		String queryString = request.getQueryString();
		String para=queryString.substring(queryString.indexOf("para=")+5,queryString.indexOf("&code"));
		logger.info("quertString " +queryString );
		if(StringUtils.isBlank(para)){
			throw new RuntimeException("para is must not empty");
		}
		if(StringUtils.isBlank(code)){
			throw new RuntimeException("code is must not empty");
		}
		try {
	//		//根据code获取openid
			Map<String, String> resMap=WeixinUtil.getWxUserOpenId(code);
			String openid = resMap.get("openid");
			JSONObject paraJson = JSONObject.parseObject(URLDecoder.decode(para, "utf-8"));
			String redirectUrl = paraJson.getString("redirectUrl");
			request.setAttribute("redirectUrl", redirectUrl);
			if(StringUtils.isNotBlank(openid)){
				String _ticket_sign = null;
				String sessionId = ConstantUtils._ACCESS_TICKET_PREFFIX+openid;
				String _access_ticket = sessionId;
				boolean sessionExists = sessionService.exists(sessionId);
				//当前session存在
				if(sessionExists){
					_ticket_sign = sessionService.getAtrribute(sessionId, ConstantUtils._TICKET_SIGN);
					//签名不存在，生成签名
					if(StringUtils.isBlank(_ticket_sign)){
						_ticket_sign=generateSessionSign(openid);
					}
				}else{
					//session不存在，创建session，生成签名
					sessionService.createSession(sessionId);
					_ticket_sign = generateSessionSign(openid);
				}
				//保存session
				sessionService.setAttribute(sessionId, ConstantUtils._TICKET_SIGN, _ticket_sign);
				sessionService.setAttribute(sessionId, ConstantUtils.OPEN_ID, openid);
				//重定向链接
				request.setAttribute(ConstantUtils._ACCESS_TICKET, _access_ticket);
				request.setAttribute(ConstantUtils._TICKET_SIGN, _ticket_sign);
				logger.info("腾讯返回openid--->ticket:"+_access_ticket+",sign:"+_ticket_sign+",openId:"+openid);
				redirectUrl = this.handleRedirectUrl(redirectUrl,request);
				logger.info("redirectUrl : " + redirectUrl);
				request.setAttribute("redirectUrl", redirectUrl);
				request.getRequestDispatcher("/dzsw/redirectUrl.jsp").forward(request, response);
				return null;
			}else{
				String errmsg = resMap.get("errmsg");
				//响应错误信息
				if(StringUtils.isBlank(errmsg)){
					errmsg = "openId获取异常";
				}
				return errmsg;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
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
				if(queryStr.contains(ConstantUtils._ACCESS_TICKET)){
					queryStr=queryStr.replaceAll("&?"+ConstantUtils._ACCESS_TICKET+"=[^&]*&?", "&");
				}
				if(queryStr.contains(ConstantUtils._TICKET_SIGN)){
					queryStr=queryStr.replaceAll("&?"+ConstantUtils._TICKET_SIGN+"=[^&]*&?", "&");
				}
			}
			queryStr=queryStr.replaceAll("(^&+|&+$)", "");
			//添加新的票据和签名
			String preffix = ConstantUtils._ACCESS_TICKET+"="+(String) request.getAttribute(ConstantUtils._ACCESS_TICKET)
					+"&"+ConstantUtils._TICKET_SIGN+"="+(String) request.getAttribute(ConstantUtils._TICKET_SIGN);
			if(StringUtils.isNotBlank(queryStr)){
				queryStr=preffix+"&"+queryStr;
			}else{
				queryStr=preffix;
			}
			
			return redirectUri+"?"+queryStr;
		}
		return null;
	}
	@RequestMapping("/go")
	public String toWxAuth(HttpServletRequest request,HttpServletResponse response){
		String httpBasePath = (String) request.getSession().getServletContext().getAttribute("httpBasePath");
		String queryString = request.getQueryString();
		Assert.isTrue(StringUtils.isNotBlank(queryString), "参数不能为空");
		String redirectUrl=queryString.substring(queryString.indexOf("redirectUrl=")+12);
		try {
			JSONObject para=new JSONObject();
			para.put("redirectUrl", redirectUrl);
			redirectUrl =  httpBasePath+"auth/openid?para="+para.toJSONString();
			//拼接微信授权跳转链接
			String srcUrl = WeixinUtil.getWxAuthRequestUrl(redirectUrl);
			logger.info("即将进行微信授权跳转至："+srcUrl);
			request.setAttribute("redirectUrl", srcUrl);
			return "dzsw/redirectUrl";
		} catch (Exception e) {
			throw new RuntimeException("授权链接转换失败");
		}
	}
	
	/**
	 * 
			* 描述:
			* 根据openId与当前时间生成一个签名
			* @param sessionId
			* @return
			* @author 2017年7月21日 下午1:38:02
	 */
	public static String generateSessionSign(String sessionId){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String now = sdf.format(new Date());
		String src=String.format("sessionid=%s&timestamp=%s", sessionId,now);
		int random = 5+RandomUtil.getRandomNumber(64);
		return encrpyMd5AndHexString(src, random);
	}
	/**
	 * 
	 * 描述: 将字符串进行N次加密
	 * 
	 * @param src
	 *            源字符串
	 * @param cycleIndex
	 *            加密次数
	 * @return
	 * @author 许宝众2017年7月11日 上午10:39:28
	 */
	public static String encrpyMd5AndHexString(String src, int cycleIndex) {
		String res = src;
		for (int i = 0; i < cycleIndex; i++) {
			try {
				res = Hex.encodeHexString(DigestUtils.md5(res));
				res = res.substring(1) + res.substring(0, 1);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return res;
	}
	@RequestMapping(method=RequestMethod.POST,value="sdkSign")
	@ResponseBody
	public JSONObject weChatSdkSign(String url){
		JSONObject resJson = null;
		if(StringUtils.isNotBlank(url)){
			Map<String, String> resMap = WxSDKUtil.getInstance().sign(url);
			resJson = new JSONObject();
			resJson.put("resCode","1");
			resJson.put("data", resMap);
		}else{
			resJson = new JSONObject();
			resJson.put("resCode", "0");
			resJson.put("errMsg", "url参数不能为空");
		}
		return resJson;
	}
	
	public static void main(String[] args) throws Exception {
		 String openId="osX-mwHc1xa7SZEjYG4fIPyFUSCo";
//		 for(int i=1;i<100;i++){
//			 String encrypt = AESCode.customEncrypt(openId, "WXTEMPLATEMESSAGE");
//			 System.out.println(encrypt);
//		 }
		 
		 byte[] decodeFast = Base64.decodeFast("966f8b1e43bc4d44a8ffD60xR6RLgIviDUjbfVcNx8nOaM+0sRC5PWiqryFAGU=%0D%0A");
		 System.out.println(Hex.encodeHexString(decodeFast));
		 String res="966f8b1e43bc4d44a8ffD60xR6RLgIviDUjbfVcNx8nOaM+0sRC5PWiqryFAGU=%0D%0A";
		 System.out.println(AESCode.customDecypt(res, "WXTEMPLATEMESSAGE"));
		StringEscapeUtils.escapeHtml(res).equals(res);
	}
}