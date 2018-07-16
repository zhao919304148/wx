package com.wap.kfcenter.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.sys.dic.SysDicHelper;
import com.sys.dic.entity.DicContentEntity;
import com.sys.exception.EpiccException;
import com.wap.kfcenter.entity.carslist.CarsListReturnEntity;
import com.wap.kfcenter.service.KfSelfService;
import com.wap.trans.entity.tr_1054.ReqTrans1054BodyEntity;
import com.wap.trans.entity.tr_1055.ReqTrans1055BodyEntity;
import com.wap.trans.entity.tr_1055.ResTrans1055MovecarMainDataEntity;
import com.wap.trans.entity.tr_1056.ReqTrans1056BodyEntity;
import com.wap.util.CommonUtils;
import com.wx.util.WeixinUtil;

@Controller
@RequestMapping(value = { "/ncfw" })
public class NCFWController {
	
	private static final Log logger = LogFactory.getLog(NCFWController.class);
	@Autowired
	private JedisPool jedisPool;
	@Autowired(required = false)
	private KfSelfService kfSelfService = null;
	
	/**
	 * 
			* 描述:挪车服务功能跳转--查询已注册车辆信息	未绑定车辆--注册页面	绑定--挪车服务
			* @param request
			* @param response
			* @param session
			* @return
			* @author zhangjian2017年12月1日 下午1:14:50
	 */
	@RequestMapping(value = { "/queryCarServiceList.do" })
	public ModelAndView queryCarServiceList(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		
		ModelAndView ncfwView = new ModelAndView("/kf/selfservice/ncfw/carService");//挪车服务
		ModelAndView ncfwzcView = new ModelAndView("/kf/selfservice/ncfw/registerCarService");//挪车服务注册
		String openid = request.getParameter("openId");
		openid = "osX-mwAGOCY6hbqeE_8zU_1SnP64";
		logger.info("挪车服务prepare openid===" + openid);
		
		//判断是否微信浏览器打开
		/**
		com.alibaba.fastjson.JSONObject explore = CommonUtils.checkWxOpen(request, openid);
		if("1".equals(explore.get("resCode"))){
			ncfwView = new ModelAndView("browser_err");
			return ncfwView;
		}
		*/
		
		try{
			Map<String, String> returnmap = new HashMap<String, String>();
			returnmap = WeixinUtil.getUserNameFormPICC(openid);
			if(returnmap != null){
				if("0".equals(returnmap.get("retcode"))){//验证是否登录
					logger.info("挪车服务注册页面跳转开始,用户成功登陆openId===" + openid);
					Map<String, String> phoRetMap = null;
					phoRetMap = WeixinUtil.getUserPhoneFromPICC(openid);//获取个人中心手机号
					if("0".equals(phoRetMap.get("retcode"))){
						//查询已注册车辆信息	未绑定车辆--注册页面	绑定--挪车服务
						ReqTrans1055BodyEntity reqBody = new ReqTrans1055BodyEntity();
						reqBody.setTelno(phoRetMap.get("phoneno"));
						reqBody.setChanneltype("1");
						com.alibaba.fastjson.JSONObject returnjson = kfSelfService.queryCarServiceList(reqBody);
						if ("1".equals(returnjson.get("retcode"))) {//查询成功
							//warning -- Type mismatch?
							List<ResTrans1055MovecarMainDataEntity> returnCarList =	(List<ResTrans1055MovecarMainDataEntity>)returnjson.get("carlist");
							if (returnCarList.size()>0) {//挪车服务页面
								ncfwView.addObject("res","1");
								ncfwView.addObject("registeredList",returnCarList);
							}else {//新注册页面
								CarsListReturnEntity usercarlist = WeixinUtil.getCarsListFromPICC(phoRetMap.get("username"));//获取个人中心绑定车辆列表
								if ("0".equals(usercarlist.getHead().getRetcode())) {
									ncfwzcView.addObject("res", "1");
									ncfwzcView.addObject("carlist", usercarlist.getBody().getDataList());//车辆列表
									ncfwzcView.addObject("realname", phoRetMap.get("realname"));//客户姓名
									ncfwzcView.addObject("phoneno", phoRetMap.get("phoneno"));//联系电话
								}else {
									logger.info("挪车服务页面跳转,查询已注册车辆失败");
									ncfwzcView.addObject("res", "0");
									ncfwzcView.addObject("msg", usercarlist.getHead().getRetmsg());
								}
								return ncfwzcView;
							}
						} else {//查询失败
							logger.info("挪车服务注册页面跳转,查询未注册车辆失败");
							ncfwView.addObject("res",phoRetMap.get("retcode"));
							ncfwView.addObject("msg",phoRetMap.get("retmsg"));
						}
					}else{
						logger.info("挪车服务注册页面跳转,获取用户手机号失败");
						ncfwView.addObject("res",phoRetMap.get("retcode"));
						ncfwView.addObject("msg",phoRetMap.get("retmsg"));
					}
				}else{
					logger.info("挪车服务注册页面跳转,请关注[北京人保财险]公众号，绑定身份信息并登录");
					ncfwView.addObject("res","0");
					ncfwView.addObject("msg", "请关注[北京人保财险]公众号，绑定身份信息并登录");
				}
			}else{
				logger.info("挪车服务注册页面跳转,请绑定身份信息并登录");
				ncfwView.addObject("res","0");
				ncfwView.addObject("msg","请绑定身份信息并登录");
			}
		}catch(Exception e){
			logger.info("挪车服务注册页面跳转,系统繁忙");
			e.printStackTrace();
			ncfwView.addObject("res","0");
			ncfwView.addObject("msg","系统繁忙，请稍后重试");
		}
		return ncfwView;
	}
	
	/**
	 * 
			* 描述:挪车服务页面跳转挪车注册页面--查询个人中心绑定车辆后去除已注册车辆
			* @param request
			* @param response
			* @param session
			* @return
			* @author zhangjian2017年12月1日 下午1:15:43
	 */
	@RequestMapping(value = { "/registerCarService.do" })
	public ModelAndView registerCarService(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		ModelAndView ncfwzcView = new ModelAndView("/kf/selfservice/ncfw/registerCarService");//挪车服务注册
		String openid = request.getParameter("openId");
		openid = "osX-mwAGOCY6hbqeE_8zU_1SnP64";
		logger.info("挪车服务注册prepare openid===" + openid);
		
		/**
		//判断是否微信浏览器打开
		com.alibaba.fastjson.JSONObject explore = CommonUtils.checkWxOpen(request, openid);
		if("1".equals(explore.get("resCode"))){
			ncfwzcView = new ModelAndView("browser_err");
			return ncfwzcView;
		}
		*/
		
		try{
			Map<String, String> returnmap = new HashMap<String, String>();
			returnmap = WeixinUtil.getUserNameFormPICC(openid);
			if(returnmap != null){
				if("0".equals(returnmap.get("retcode"))){//验证是否登录
					logger.info("挪车服务页面跳转开始,用户成功登陆openId===" + openid);
					Map<String, String> phoRetMap = null;
					phoRetMap = WeixinUtil.getUserPhoneFromPICC(openid);//获取个人中心手机号
					if("0".equals(phoRetMap.get("retcode"))){
						//查询已注册车辆信息	未绑定车辆--注册页面	绑定--挪车服务
						ReqTrans1055BodyEntity reqTrans1055Entity = new ReqTrans1055BodyEntity();
						reqTrans1055Entity.setTelno(phoRetMap.get("phoneno"));
						reqTrans1055Entity.setChanneltype("1");
						com.alibaba.fastjson.JSONObject returnjson = kfSelfService.queryCarServiceList(reqTrans1055Entity);
						if ("1".equals(returnjson.get("retcode"))) {//查询成功
							List<ResTrans1055MovecarMainDataEntity> returnCarList =	(List<ResTrans1055MovecarMainDataEntity>)returnjson.get("carlist");
							CarsListReturnEntity userCarList = WeixinUtil.getCarsListFromPICC(phoRetMap.get("username"));//获取个人中心绑定车辆列表
							if ("0".equals(userCarList.getHead().getRetcode())) {
								//把个人中心绑定车辆去除已注册车辆
								for (int i = 0; i < userCarList.getBody().getDataList().size(); i++) {
									if (returnCarList.get(i).getLicenseno().equals(userCarList.getBody().getDataList().get(i).getLicenseno())) {
										userCarList.getBody().getDataList().remove(i);
									}
								}
								ncfwzcView.addObject("res", "1");
								ncfwzcView.addObject("carlist", userCarList.getBody().getDataList());//车辆列表
								ncfwzcView.addObject("realname", phoRetMap.get("realname"));//客户姓名
								ncfwzcView.addObject("phoneno", phoRetMap.get("phoneno"));//联系电话
							}else {
								logger.info("挪车服务注册页面跳转,查询未注册车辆失败");
								ncfwzcView.addObject("res", "0");
								ncfwzcView.addObject("msg", userCarList.getHead().getRetmsg());
							}
						} else {//查询失败
							logger.info("挪车服务页面跳转,查询已注册车辆失败");
							ncfwzcView.addObject("res",phoRetMap.get("retcode"));
							ncfwzcView.addObject("msg",phoRetMap.get("retmsg"));
						}
					}else{
						logger.info("挪车服务页面跳转,获取用户手机号失败");
						ncfwzcView.addObject("res",phoRetMap.get("retcode"));
						ncfwzcView.addObject("msg",phoRetMap.get("retmsg"));
					}
				}else{
					logger.info("挪车服务页面跳转,请关注[北京人保财险]公众号，绑定身份信息并登录");
					ncfwzcView.addObject("res","0");
					ncfwzcView.addObject("msg", "请关注[北京人保财险]公众号，绑定身份信息并登录");
				}
			}else{
				logger.info("挪车服务页面跳转,请绑定身份信息并登录");
				ncfwzcView.addObject("res","0");
				ncfwzcView.addObject("msg","请绑定身份信息并登录");
			}
		}catch(Exception e){
			logger.info("挪车服务页面跳转,系统繁忙");
			e.printStackTrace();
			ncfwzcView.addObject("res","0");
			ncfwzcView.addObject("msg","系统繁忙，请稍后重试");
		}
		return ncfwzcView;
	}
	
	/**
	 * 
			* 描述:获取分机号
			* @param request
			* @param response
			* @param session
			* @return
			* @author zhangjian2017年12月1日 下午3:15:44
	 */
	@RequestMapping(value = { "/getExtensionNo.do" })
	@ResponseBody
	public com.alibaba.fastjson.JSONObject getExtensionNo(HttpServletRequest request) {
		com.alibaba.fastjson.JSONObject res = new com.alibaba.fastjson.JSONObject(); 
		//用openId+次数(唯一)记录每天获取分机号次数
		String openid = request.getParameter("openId");
		openid = "osX-mwAGOCY6hbqeE_8zU_1SnP64";
//		String incrKey = "XXXX-"+openid;
//		Jedis jedis = jedisPool.getResource();
//		Long resIncr = jedis.incr(incrKey);
//		if(resIncr<=5){
//			if(resIncr==1){
//				//设置key过期时间：明天凌晨
//				Calendar tomorrowCal = Calendar.getInstance();
//				tomorrowCal.add(Calendar.DATE, 1);
//				tomorrowCal.set(Calendar.HOUR_OF_DAY, 0);
//				tomorrowCal.set(Calendar.MINUTE, 0);
//				tomorrowCal.set(Calendar.SECOND, 0);
//				tomorrowCal.set(Calendar.MILLISECOND, 0);
//				long unixTime = tomorrowCal.getTimeInMillis()/1000;
//				jedis.expireAt(incrKey, unixTime);
//			}
//			//获取分机号
//			try {
//				ReqTrans1056BodyEntity reqTrans1056Entity = new ReqTrans1056BodyEntity();
//				reqTrans1056Entity.setChanneltype("1");
//				res = kfSelfService.getExtensionNumber(reqTrans1056Entity);
//				if("1".equals(res.get("retcode"))){
//					System.out.println(res.get("extensionno"));
//				}else {
//					
//				}
//			} catch (Exception e) {
//				res.put("retcode", "0");
//				res.put("retmsg", "获取分机号失败，请联系人工客服");
//				logger.info("获取分机号报错：");
//				if(e instanceof EpiccException){
//					EpiccException ep = (EpiccException)e;
//					logger.info("获取分机号报错："+ ep.getErrMess());
//				}else{
//					res.put("retmsg", e.getMessage());
//					logger.info("获取分机号报错："+ e.getMessage());
//				}
//			}
//		}else{
//			//不允许获取
//			//jedis.decr(incrKey);
//			res.put("retcode", "0");
//			res.put("retmsg", "今日已到获取上限，请明日再试");
//		}
		//获取分机号
		try {
			ReqTrans1056BodyEntity reqTrans1056Entity = new ReqTrans1056BodyEntity();
			reqTrans1056Entity.setChanneltype("1");
			res = kfSelfService.getExtensionNumber(reqTrans1056Entity);
			if("1".equals(res.get("retcode"))){
				System.out.println(res.get("extensionno"));
			}else {
				
			}
		} catch (Exception e) {
			res.put("retcode", "0");
			res.put("retmsg", "获取分机号失败，请联系人工客服");
			logger.info("获取分机号报错：");
			if(e instanceof EpiccException){
				EpiccException ep = (EpiccException)e;
				logger.info("获取分机号报错："+ ep.getErrMess());
			}else{
				res.put("retmsg", e.getMessage());
				logger.info("获取分机号报错："+ e.getMessage());
			}
		}
		return res;
	}
	
	/**
	 * 
			* 描述:挪车服务注册提交
			* @param request
			* @return
			* @author zhangjian2017年12月1日 下午3:39:28
	 */
	@RequestMapping(value = { "/submitInfo.do" })
	@ResponseBody
	public com.alibaba.fastjson.JSONObject submitInfo(HttpServletRequest request){
		com.alibaba.fastjson.JSONObject res = new com.alibaba.fastjson.JSONObject();
		ReqTrans1054BodyEntity reqTrans1054Entity = new ReqTrans1054BodyEntity();
		reqTrans1054Entity.setLicenseno(request.getParameter("licenseno"));
		reqTrans1054Entity.setTelno(request.getParameter("phoneno"));
		reqTrans1054Entity.setOwenername(request.getParameter("realname"));
		reqTrans1054Entity.setExtensionno(request.getParameter("extensionno"));
		reqTrans1054Entity.setChanneltype("1");
		try {
			res = kfSelfService.carServiceRegister(reqTrans1054Entity);
			System.out.println(res);
			if("1".equals(res.get("retcode"))){
				res.put("retcode", "1");
			}else {
				res.put("retcode", "0");
				res.put("msg", res.get("retmsg"));
			}
		} catch (Exception e) {
			res.put("retcode", "0");
			res.put("retmsg", "注册挪车服务失败，请联系人工客服");
			logger.info("注册挪车服务报错：");
			if(e instanceof EpiccException){
				EpiccException ep = (EpiccException)e;
				logger.info("注册挪车服务报错："+ ep.getErrMess());
			}else{
				res.put("retmsg", e.getMessage());
				logger.info("注册挪车服务报错："+ e.getMessage());
			}
		}
		return res;
	}
}
