package com.wap.dzsw.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sys.dic.SysDicHelper;
import com.sys.exception.EpiccException;
import com.wap.dzsw.entity.ActivityGiftCardQueryEntity;
import com.wap.dzsw.entity.Czj2017GiftItemVo;
import com.wap.dzsw.entity.Czj2017GiftVo;
import com.wap.dzsw.entity.PanicBuyingRequest;
import com.wap.dzsw.service.ActivityService;
import com.wap.util.ConstantUtils;
import com.wx.util.WeixinUtil;

/**
 * 
	* 描述:
	*     活动相关controller
	* @author 许宝众
	* @version 1.0
* @since 2017年9月19日 下午1:13:40
 */
@Controller
@RequestMapping("/activities")
public class ActivityController {
	private static final Log logger = LogFactory.getLog(ActivityController.class);
	private AtomicInteger czj2017ConcurrentCount = new AtomicInteger(0);
	@Autowired
	private ActivityService activityService;
	/**
	 * 
			* 描述:
			* 	2017车主节限时抢券活动：抢券处理
			* @param cardtype
			* @return
			* @author 许宝众 2017年9月19日 下午1:17:33
	 */
	@RequestMapping(value="/201710/getLimitTimeLift.do")
	@ResponseBody
	public JSONObject getLimitTimeLift(String cardtype,HttpServletRequest request){
		JSONObject resJson = null;
		Integer maxLimit = (Integer) SysDicHelper.getInstance().getRedisConstants(ConstantUtils.SERVER_REQUEST_MAX_LIMIT);
		String openid = (String) request.getAttribute(ConstantUtils.OPEN_ID);
		String username = (String) request.getAttribute(ConstantUtils.USER_NAME);
		if(StringUtils.isBlank(openid)||(StringUtils.isBlank(username)||(StringUtils.isBlank(cardtype)))){
			resJson = new JSONObject();
			resJson.put("resCode", "-1");
			resJson.put("errMsg", "参数错误");
			return resJson;
		}
		/*--------------处理抢券 BEGIN-----------------------------------*/
		//并发最大处理量
		int current = czj2017ConcurrentCount.incrementAndGet();
		resJson = new JSONObject();
		String uuid = UUID.randomUUID().toString();
		logger.info("编号："+uuid+",[请求参数："+"openid="+openid+",username="+username+",cardtype="+"cardtype"+"]");
		try{
			if(current>maxLimit){
				resJson.put("resCode", "-2");//
				resJson.put("errMsg", "当前人数过多，请稍后重试");
			}else{
				//组织请求内容
				PanicBuyingRequest panicBuyingRequest = new PanicBuyingRequest();
				panicBuyingRequest.setCardtype(cardtype);
				panicBuyingRequest.setOpenid(openid);
				panicBuyingRequest.setUsername(username);
				//请求
				resJson = activityService.getLimitTimeRobGift(panicBuyingRequest);
				logger.info("编号："+uuid+",处理结果："+resJson.toJSONString());
			}
		}catch(Exception e){
			logger.error("编号："+uuid+",处理异常：",e);
			resJson.put("resCode", "-99");
			resJson.put("errMsg", "服务异常，请稍后重试");
		}finally{
			czj2017ConcurrentCount.decrementAndGet();
		}
		/*--------------处理抢券 END-----------------------------------*/
		return resJson;
	}
	
	/**
	 * 
			* 描述:
			* 	2017车主节活动期间每日领取
			* @param cardtype
			* @return
			* @author 许宝众 2017年9月19日 下午1:17:33
	 */
	@RequestMapping(value="/201710/getPerdayGift.do")
	@ResponseBody
	public JSONObject getPerdayGift(String cardtype,HttpServletRequest request){
		JSONObject resJson = null;
		Integer maxLimit = (Integer) SysDicHelper.getInstance().getRedisConstants(ConstantUtils.SERVER_REQUEST_MAX_LIMIT);
		String openid = (String) request.getAttribute(ConstantUtils.OPEN_ID);
		String username = (String) request.getAttribute(ConstantUtils.USER_NAME);
		if(StringUtils.isBlank(openid)||(StringUtils.isBlank(username)||(StringUtils.isBlank(cardtype)))){
			resJson = new JSONObject();
			resJson.put("resCode", "-1");
			resJson.put("errMsg", "参数错误");
			return resJson;
		}
		/*--------------处理抢券 BEGIN-----------------------------------*/
		//并发最大处理量
		int current = czj2017ConcurrentCount.incrementAndGet();
		resJson = new JSONObject();
		String uuid = UUID.randomUUID().toString();
		logger.info("编号："+uuid+",[请求参数："+"openid="+openid+",username="+username+",cardtype="+"cardtype"+"]");
		try{
			if(current>maxLimit){
				resJson.put("resCode", "-2");//
				resJson.put("errMsg", "当前人数过多，请稍后重试");
			}else{
				//组织请求内容
				PanicBuyingRequest panicBuyingRequest = new PanicBuyingRequest();
				panicBuyingRequest.setCardtype(cardtype);
				panicBuyingRequest.setOpenid(openid);
				panicBuyingRequest.setUsername(username);
				//请求
				resJson = activityService.getPerdayGift(panicBuyingRequest);
				logger.info("编号："+uuid+",处理结果："+resJson.toJSONString());
			}
		}catch(Exception e){
			logger.error("编号："+uuid+",处理异常：",e);
			resJson.put("resCode", "-99");
			resJson.put("errMsg", "服务异常，请稍后重试");
		}finally{
			czj2017ConcurrentCount.decrementAndGet();
		}
		/*--------------处理抢券 END-----------------------------------*/
		return resJson;
	}
	
	/**
	 * 
			* 描述:
			* 跳转2017车主节活动主页面
			* @return
			* @author zs 2017年9月26日 下午9:00:00
	 */
	
	@RequestMapping(value="/201710/activityMain.do")
	public String activityMain(HttpServletResponse response,HttpServletRequest request){
		try {
			Date date = new Date();
			String openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
			//判断用户是否关注了公众号
			boolean notifyPublicAccount = WeixinUtil.isNotifyPublicAccount(openId);
			if(!notifyPublicAccount){
				String guanzhuUrl = (String)request.getSession().getServletContext().getAttribute("httpBasePath")+"dzsw/activity/notifyMe.html";
				request.setAttribute("redirectUrl", guanzhuUrl);
				request.getRequestDispatcher("/dzsw/redirectUrl.jsp").forward(request, response);
				return null;
			}
			JSONObject checkwx = checkwx(request,openId);
			if(StringUtils.isBlank(openId)){
				return "errMsg:参数错误";
			}
			String cord = (String) checkwx.get("resCode");
			if(!"0".equals(cord)){
				return checkwx.toString();
			}
//			Czj2017GiftVo czj2017giftvo	= activityService.getSpecailDateCzj2017ActivityGiftList(date, openId);
//			List<Czj2017GiftItemVo> robGifts = czj2017giftvo.getRobGifts();//每日抢礼品，第一个是当前正在进行的活动，或者将要进行的活动、第二个是下一轮活动礼品
//			List<Czj2017GiftItemVo> perdayGifts = czj2017giftvo.getPerdayGifts();//所有每日领取礼品信息
//			request.setAttribute("robGifts", robGifts);
//			request.setAttribute("perdayGifts", perdayGifts);
//			request.getRequestDispatcher("/dzsw/activity/activity.jsp").forward(request, response);
			
			String guanzhuUrl = (String)request.getSession().getServletContext().getAttribute("httpBasePath")+"dzsw/dszh/main.jsp";
			request.setAttribute("redirectUrl", guanzhuUrl);
			request.setAttribute("activity", "activity");
			return "dzsw/redirectUrl";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} 
	}
	
	/**
	 * 
			* 描述:微信验证、登陆、绑定
			* @param request
			* @param openId
			* @author qex 2017年2月27日 上午10:30:36
	 * @return 
	 */
	private JSONObject checkwx(HttpServletRequest request, String openId ){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resCode","0");//默认成功返回，错误时覆盖此值
		String userAgent = request.getHeader("User-Agent");
		if(userAgent.indexOf("MicroMessenger") == -1){
			logger.info("userAgent not have MicroMessenger!");
			jsonObject.put("resCode","1");
			jsonObject.put("msg","请在微信客户端打开链接！");
			return jsonObject;
		}
		if("".equals(openId)){
			jsonObject.put("resCode","1");
			jsonObject.put("msg", "请重新在微信公众号菜单进入电商福袋！");
			return jsonObject;
		}

		return jsonObject;
	}
	

	/**
	 * 
	* 描述:
	* 	查询车主抢到礼品
	* @param cardtype
	* @return
	* @author zs 2017年9月28日 下午10:44:33
	 */
	@RequestMapping(value="/201710/queryOwnerGift.do")
	@ResponseBody
	public JSONObject queryOwnerGift(String pageNo,HttpServletRequest request){
		JSONObject resJson = null;
		ActivityGiftCardQueryEntity   activitygiftcardqueryentity = new ActivityGiftCardQueryEntity();
		Integer maxLimit = (Integer) SysDicHelper.getInstance().getRedisConstants(ConstantUtils.SERVER_REQUEST_MAX_LIMIT);
		String openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
		if(StringUtils.isBlank(openId) || StringUtils.isBlank(pageNo)){
			resJson = new JSONObject();
			resJson.put("resCode", "-1");
			resJson.put("errMsg", "参数错误");
		}else{
			int pageNos = Integer.parseInt(pageNo);
			activitygiftcardqueryentity.setOpenid(openId);
			activitygiftcardqueryentity.setPageno(pageNos);
			try {
				resJson = activityService.queryOwnerGiftList(activitygiftcardqueryentity);
			} catch (EpiccException e) {
				// TODO Auto-generated catch block
				resJson = new JSONObject();
				e.printStackTrace();
				resJson.put("resCode", "-99");
				resJson.put("errMsg", "服务异常，请稍后重试");
			}	
		}
		return resJson;
	}
}
