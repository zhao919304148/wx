package com.wap.kfcenter.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.sys.exception.EpiccException;
import com.wap.kfcenter.entity.CzmsRegistEntity;
import com.wap.kfcenter.entity.carslist.CarsListReturnDataEntity;
import com.wap.kfcenter.entity.carslist.CarsListReturnEntity;
import com.wap.kfcenter.service.KfSelfService;
import com.wap.trans.entity.tr_1047.ReqTrans1047Entity;
import com.wap.trans.entity.tr_1049.ReqTrans1049CarDataEntity;
import com.wap.trans.entity.tr_1049.ReqTrans1049Entity;
import com.wap.trans.entity.tr_1049.ResTrans1049ServiceDataEntity;
import com.wap.trans.entity.tr_1050.ReqTrans1050Entity;
import com.wap.util.CommonUtils;
import com.wx.util.WeixinUtil;

@Controller
@RequestMapping(value = { "/kfcenter/czms" })
public class CzmsController {

	private static final Log logger = LogFactory.getLog(CzmsController.class);
	
	@Autowired(required = false)
	private KfSelfService kfSelfService = null;

	/**
	 * 描述： 车主秘书登陆校验
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @author 董培恒 2017年10月27日 下午9:06:52
	 */
	@RequestMapping(value = { "/czmsPrepare.do" })
	public ModelAndView czmsPrepare(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ReqTrans1049Entity reqTrans1049Entity = null;
		ReqTrans1049CarDataEntity reqTrans1049CarDataEntity = null;
		List<ReqTrans1049CarDataEntity> reqTrans1049CarDataList = null;
		com.alibaba.fastjson.JSONObject resJson = null;
		String czmsRetcode = "";
		List<ResTrans1049ServiceDataEntity> resTrans1049ServiceDataEntities = null;
		
		List<ResTrans1049ServiceDataEntity> registList = null;
		
		List<String> wzcList = null;
		// 车主秘书页面
		ModelAndView czmsView = new ModelAndView("/kf/selfservice/czms/main");
		String openid = (String)request.getAttribute("openId");
		logger.info("车主秘书czmsPrepare request getattribute openid=" + request.getAttribute("openId"));
		
		Map<String, String> returnmap = null;
		Map<String, String> phonemap = null;
		String phonenumber = "";
		String customername = "";
		String retcode = "";
		String username = "";
		String identifyno = "";
		String identifytype = "";
		CarsListReturnEntity usercarlist = null;
		String licenseNo = "";
		String checkOpenCode = "";
		try {
			returnmap = WeixinUtil.getUserNameFormPICC(openid);
			if (returnmap != null) {
				retcode = returnmap.get("retcode");
				if ("0".equals(retcode)) {
					//验证是否在微信公众号中打开页面   开始
					JSONObject openJson = CommonUtils.checkWxOpen(request, openid);
					System.out.println("是否在微信浏览器中打开：" + openJson.get("resCode"));
					checkOpenCode = (String)openJson.get("resCode");
					if(!"0".equals(checkOpenCode)){
						try {
							request.getRequestDispatcher("browser_err.jsp").forward(request, response);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					//验证是否在微信公众号中打开页面   结束
					username = returnmap.get("username");
					identifyno = returnmap.get("identifyno");
					identifytype = returnmap.get("identifytype");
					//个人中心的身份证号,其他证件号码不带入,非身份证为空客户自助填写
					if(StringUtils.isNotEmpty(identifytype) && "01".equals(identifytype)){
						czmsView.addObject("identifyno", identifyno);
					}
					//获取个人中心手机号
					phonemap = WeixinUtil.getUserPhoneFromPICC(openid);
					if(phonemap != null){
						if("0".equals(phonemap.get("retcode"))){
							phonenumber = (String)phonemap.get("phoneno");
							customername = (String)phonemap.get("realname");
							czmsView.addObject("phonenumber", phonenumber);
							logger.info("车主秘书-customername=" + customername);
							czmsView.addObject("customername", customername);
						}
					}
					
					// 获取个人中心的车牌号
					usercarlist = WeixinUtil.getCarsListFromPICC(username);
					if ("0".equals(usercarlist.getHead().getRetcode())) {
						// 根据车牌号和底色判断客户是否注册了车主秘书
						registList = new ArrayList<ResTrans1049ServiceDataEntity>();
						wzcList = new ArrayList<String>();
						for(CarsListReturnDataEntity carData : usercarlist.getBody().getDataList()){
							licenseNo = carData.getLicenseno();
							//取到一个车牌号，查询一下车主秘书注册信息，查到放到已注册list里面，查不到的放到未注册list里面
							reqTrans1049Entity = new ReqTrans1049Entity();
							reqTrans1049CarDataEntity = new ReqTrans1049CarDataEntity();
							reqTrans1049CarDataList = new ArrayList<ReqTrans1049CarDataEntity>();
							reqTrans1049Entity.setQuerytype("1");
							reqTrans1049CarDataEntity.setLicenseno(licenseNo);
							reqTrans1049CarDataList.add(reqTrans1049CarDataEntity);
							reqTrans1049Entity.setReqTrans1049CarDataList(reqTrans1049CarDataList);
							//调用车主秘书查询接口
							resJson = kfSelfService.czmsRegisterQuery(reqTrans1049Entity);
							if(resJson != null){
								czmsRetcode = (String)resJson.get("retcode");
								if("1".equals(czmsRetcode)){
									resTrans1049ServiceDataEntities = (List<ResTrans1049ServiceDataEntity>)resJson.get("registerInfo");
									if(resTrans1049ServiceDataEntities != null && resTrans1049ServiceDataEntities.size() > 0){
										//重新封装一个list返回给页面
										for(ResTrans1049ServiceDataEntity data : resTrans1049ServiceDataEntities){
											registList.add(data);
										}
									}else{
										//未注册
										wzcList.add(licenseNo);
									}
								}else if("0".equals(czmsRetcode)){
									//如果是0，则认为是没有注册
									wzcList.add(licenseNo);
								}
							}else{
								//没有注册
								wzcList.add(licenseNo);
							}
						}
						if(registList != null && registList.size() > 0){
							czmsView.addObject("res", "1");
							czmsView.addObject("registList", registList);
						}else{
							czmsView.addObject("res", "0");
							czmsView.addObject("msg", "没有已注册违章短信提醒信息");
						}
						if(wzcList != null && wzcList.size() > 0){
							czmsView.addObject("res1", "1");
							czmsView.addObject("wzcList", wzcList);
						}else{
							czmsView.addObject("res1", "0");
							czmsView.addObject("msg1", "没有未注册违章短信提醒车辆信息");
						}
					} else {
						System.out.println("获取用户车辆信息失败！");
						czmsView.addObject("res", "0");
						czmsView.addObject("msg", usercarlist.getHead().getRetmsg());
					}
				} else {
					request.getRequestDispatcher("/kf/toCentralLogin.jsp").forward(request, response);
				}
			} else {
				request.getRequestDispatcher("/kf/toCentralLogin.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				request.getRequestDispatcher("/500.jsp").forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return czmsView;
	}
	
	/**
	 * 
	 * 描述:受邀有礼根据车牌号获取被保人信息 1047
	 * 
	 * @param licenseNo
	 * @param request
	 * @return
	 * @author zhangjian2017年10月19日 上午11:15:59
	 */
	@RequestMapping(value = "getCarInfo.do")
	@ResponseBody
	public com.alibaba.fastjson.JSONObject getCarInfo(String licenseNo,String licenseColorCode,HttpServletRequest request,HttpServletResponse response){
		com.alibaba.fastjson.JSONObject res = null;
		com.alibaba.fastjson.JSONObject resEntity = null;
		ReqTrans1047Entity req1047Entity = null;
		
		String retcode = "";
		String username = "";
		boolean validCarFlag = false;
		
		String openid = (String)request.getAttribute("openId");
		logger.info("车主秘书获取车辆信息opendid：" + openid);

		Map<String, String> returnmap = new HashMap<String, String>();
		CarsListReturnEntity usercarlist = null;
		try {
			if(StringUtils.isNotEmpty(licenseNo) && StringUtils.isNotEmpty(licenseColorCode)){
				//验证提交过来的车牌号是否是个人中心取过来的车牌号
				//先验证是否登录
				returnmap = WeixinUtil.getUserNameFormPICC(openid);
				if (returnmap != null) {
					retcode = returnmap.get("retcode");
					username = returnmap.get("username");
					if ("0".equals(retcode)) {
						// 获取个人中心的车牌号
						usercarlist = WeixinUtil.getCarsListFromPICC(username);
						if ("0".equals(usercarlist.getHead().getRetcode())) {
							// 根据车牌号和底色判断客户是否注册了车主秘书
							for(CarsListReturnDataEntity carData : usercarlist.getBody().getDataList()){
								if(licenseNo.equals(carData.getLicenseno())){
									//true 是个人中心绑定车辆
									validCarFlag = true;
									break;
								}
							}
						}
					}
				}
				if(validCarFlag){
					req1047Entity = new ReqTrans1047Entity();
					req1047Entity.setLicenseno(licenseNo);
					req1047Entity.setLicensecolorcode(licenseColorCode);// 默认蓝色
					req1047Entity.setInteruse("02");//微信端客户自助注册车主秘书，只返回车架号、发动机号、车型
					req1047Entity.setCheckcode("");
					//获取承保系统中的车辆信息及被保险人信息
					resEntity = kfSelfService.getSYYLInfo(req1047Entity);
					if(resEntity != null){
						res = new com.alibaba.fastjson.JSONObject();
						retcode = (String)resEntity.get("retcode");
						if("1".equals(retcode)){
							res.put("res", retcode);
							res.put("info",resEntity.get("info"));
						}else{
							res.put("res", retcode);
							res.put("msg",resEntity.get("retmsg"));
						}
					}else{
						res = new com.alibaba.fastjson.JSONObject();
						res.put("res", "0");
						res.put("msg","获取车辆信息失败");
					}
				}else{
					res = new com.alibaba.fastjson.JSONObject();
					res.put("res", "0");
					res.put("msg","您选择的车牌号不是个人中心绑定的车牌号");
				}
			}else{
				res = new com.alibaba.fastjson.JSONObject();
				res.put("res", "0");
				res.put("msg", "车牌号和号牌底色不能为空");
			}
		} catch (Exception e) {
			res = new com.alibaba.fastjson.JSONObject();
			res.put("res", "0");
			if (e instanceof EpiccException) {
				EpiccException ep = (EpiccException) e;
				res.put("msg", ep.getErrMess());
			} else {
				res.put("msg", e.getMessage());
			}
		}
		return res;
	}

	/**
	 * 描述:注册车主秘书和修改车主秘书
	 * @param czmsRegistEntity
	 * @param request
     * @param response
	 * @return json object
     * @author 吕亮 2017年11月29日 上午11:09:16
	 */
	@RequestMapping(value = "czmsSubmit.do")
	@ResponseBody
	public com.alibaba.fastjson.JSONObject czmsRegisterOrModify(CzmsRegistEntity czmsRegistEntity, HttpServletRequest request,HttpServletResponse response) {
		com.alibaba.fastjson.JSONObject res = new com.alibaba.fastjson.JSONObject();
		ReqTrans1050Entity reqTrans1050Entity = null;
		Map<String, String> returnmap = null;
		CarsListReturnEntity usercarlist = null;
		boolean validCarFlag = false;
		
		String openid = (String)request.getAttribute("openId");;
		logger.info("车主秘书提交注册opendid：" + openid);
		
		String licenseNo = czmsRegistEntity.getLicenseNo();
		String licenseColorCode = czmsRegistEntity.getLicenseColorCode();
		String engineNo = czmsRegistEntity.getEngineNo();
		String identityNo = czmsRegistEntity.getIdentityNo();
		String phoneNumber = czmsRegistEntity.getPhoneNumber();
		String customerName = czmsRegistEntity.getCustomerName();
		String flag1 = czmsRegistEntity.getFlag1();
		String flag2 = czmsRegistEntity.getFlag2();
		String flag3 = czmsRegistEntity.getFlag3();
		String flag4 = czmsRegistEntity.getFlag4();
		String operatetype = czmsRegistEntity.getOperateType();
		String comcode = czmsRegistEntity.getComcode();
		String retcode = "";
		String retmsg = "";
		String loginRetCode = "";
		String loginUserName = "";
		try {
			//验证是否登录
			returnmap = WeixinUtil.getUserNameFormPICC(openid);
			if (returnmap != null) {
				loginRetCode = returnmap.get("retcode");
				loginUserName = returnmap.get("username");
				if ("0".equals(loginRetCode)) {
					// 获取个人中心的车牌号
					usercarlist = WeixinUtil.getCarsListFromPICC(loginUserName);
					if ("0".equals(usercarlist.getHead().getRetcode())) {
						// 根据车牌号和底色判断客户是否注册了车主秘书
						for(CarsListReturnDataEntity carData : usercarlist.getBody().getDataList()){
							if(licenseNo.equals(carData.getLicenseno())){
								//true 是个人中心绑定车辆
								validCarFlag = true;
								break;
							}
						}
					}else{
						res.put("res", "0");
						res.put("msg", "请到个人中心进行绑定车辆");
						return res;
					}
				}else{
					res.put("res", "0");
					res.put("msg", "请登录");
					return res;
				}
			}else{
				res.put("res", "0");
				res.put("msg", "请登录");
				return res;
			}
			
			if(StringUtils.isNotEmpty(operatetype) && "1".equals(operatetype)){
				//注册
				if(StringUtils.isNotEmpty(licenseNo) && StringUtils.isNotEmpty(licenseColorCode) && StringUtils.isNotEmpty(engineNo) && 
						StringUtils.isNotEmpty(identityNo) && StringUtils.isNotEmpty(phoneNumber) && StringUtils.isNotEmpty(customerName) 
						&& (StringUtils.isNotEmpty(flag1) && "1".equals(flag1) 
								|| StringUtils.isNotEmpty(flag2) && "1".equals(flag2)
								|| StringUtils.isNotEmpty(flag3) && "1".equals(flag3)
								|| StringUtils.isNotEmpty(flag4) && "1".equals(flag4))){
					logger.info("车主秘书注册服务flag1=" + flag1 + "flag2=" + flag2 + "flag3==" + flag3 + "flag4=" + flag4);
					//验证提交车辆是否是个人中心绑定车辆
					if(validCarFlag){
						reqTrans1050Entity = new ReqTrans1050Entity();
						reqTrans1050Entity.setOperatetype("1");
						reqTrans1050Entity.setLicenseno(licenseNo);
						reqTrans1050Entity.setLicensecolorcode(licenseColorCode);
						reqTrans1050Entity.setEngineno(engineNo);
						reqTrans1050Entity.setCustomername(customerName);
						reqTrans1050Entity.setIdentityno(identityNo);
						reqTrans1050Entity.setPhonenumber(phoneNumber);
						reqTrans1050Entity.setOperatecode("wx_auto");
						reqTrans1050Entity.setFlag(flag1 + flag2 + flag3 + flag4 + "00000");
						reqTrans1050Entity.setComcode(comcode);
						res = kfSelfService.czmsRegisterOrModify(reqTrans1050Entity);
						retcode = (String)res.get("retcode");
						retmsg = (String)res.get("retmsg");
						if("1".equals(retcode)){
							res.put("res", "1");
							res.put("msg", "注册成功");
						}else{
							res.put("res", "0");
							res.put("msg", retmsg);
						}
					}else{
						res.put("res", "0");
						res.put("msg", "注册车牌号非个人中心绑定车牌号，无法注册");
					}
				}else{
					res.put("res", "0");
					res.put("msg", "注册信息有误");
				}
			}else if(StringUtils.isNotEmpty(operatetype) && "2".equals(operatetype)){
				//修改
				if(StringUtils.isNotEmpty(licenseNo) && StringUtils.isNotEmpty(licenseColorCode) 
						&& StringUtils.isNotEmpty(engineNo)){
					if(validCarFlag){
						reqTrans1050Entity = new ReqTrans1050Entity();
						reqTrans1050Entity.setOperatetype("2");
						reqTrans1050Entity.setLicenseno(licenseNo);
						reqTrans1050Entity.setLicensecolorcode(licenseColorCode);
						reqTrans1050Entity.setEngineno(engineNo);
						res = kfSelfService.czmsRegisterOrModify(reqTrans1050Entity);
						retcode = (String)res.get("retcode");
						retmsg = (String)res.get("retmsg");
						if("1".equals(retcode)){
							res.put("res", "1");
							res.put("msg", "修改成功");
						}else{
							res.put("res", "0");
							res.put("msg", retmsg);
						}
					}else{
						res.put("res", "0");
						res.put("msg", "车牌号非个人中心绑定车牌号，无法修改");
					}
				}else{
					res.put("res", "0");
					res.put("msg", "修改信息有误");
				}
			}else{
				res.put("res", "0");
				res.put("msg", "操作类型错误");
			}
		} catch (Exception e) {
			res.put("resCode", "0");
			if (e instanceof EpiccException) {
				EpiccException ep = (EpiccException) e;
				res.put("errMsg", ep.getErrMess());
			} else {
				res.put("errMsg", e.getMessage());
			}
		}
		return res;
	}

}
