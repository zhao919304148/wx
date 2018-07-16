package com.wap.kfcenter.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sys.dic.SysDicHelper;
import com.sys.dic.entity.CrsBrandEntity;
import com.sys.dic.entity.CrsModelEntity;
import com.sys.exception.EpiccException;
import com.wap.kfcenter.entity.DbServicelistEntity;
import com.wap.kfcenter.entity.carslist.CarsListReturnEntity;
import com.wap.kfcenter.service.KfSelfService;
import com.wap.trans.entity.tr_1052.ReqTrans1052Entity;
import com.wap.trans.entity.tr_1053.ReqTrans1053Entity;
import com.wap.trans.entity.tr_1053.ReqTrans1053YuyueDataEntity;
import com.wx.util.WeixinUtil;

@Controller
@RequestMapping(value={"/dbfw"})
public class DBFWController {
	
	private static final Log logger = LogFactory.getLog(KfCenterController.class);
	
	@Autowired(required = false)
	private KfSelfService kfSelfService = null;
	@Autowired(required = false)
	private DbServicelistEntity service = null;
	

	/**
	 * 
	* 描述:代办服务预约，根据openid获取用户证件类型及证件号，并验证是否登录微信，
	* 然后根据证件号获取用户姓名、联系电话和车牌号；
	* 根据手机号调用客户关系管理平台查询客户可享受的服务，返回服务列表展示
	* @param request
	* @param response
	* @param session
	* @return
	* @author fenghairui
	* @date 2017年10月27日
	 */
	@RequestMapping(value={"/dbfwPrepare.do"})
	public ModelAndView dbfwPrepare(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		//代办服务预约列表展示界面
		ModelAndView modelView = new ModelAndView("kf/selfservice/dbfw/dbfwyy");
		String openid = request.getParameter("openId");
		//正式上线把此处注释掉
//		openid = "osX-mwAC_JYB5Jpl9m0OXGPWdDn8";           
		modelView.addObject("openid", openid);
		
		Map<String, String> returnmap = new HashMap<String, String>();
		//先判断是否在微信浏览器中打开
		com.alibaba.fastjson.JSONObject explore = com.wap.util.CommonUtils.checkWxOpen(request, openid);
		if("1".equals(explore.get("resCode"))){
			modelView = new ModelAndView("browser_err");
			return modelView;
		}
		List<DbServicelistEntity> servicelist = new ArrayList<DbServicelistEntity>();
		com.alibaba.fastjson.JSONObject resServicelist = new com.alibaba.fastjson.JSONObject();
		String retcode = "";
		String retmsg = "";
		String identifytype = "";
		String identifyno = "";
		String username = "";
		String phoneno = "";
		try{
			returnmap = WeixinUtil.getUserNameFormPICC(openid);
			if(returnmap != null){
				retcode = returnmap.get("retcode");
				retmsg = returnmap.get("retmsg");
				identifytype = returnmap.get("identifytype");
				identifyno = returnmap.get("identifyno");
				
				if("0".equals(retcode)){
					logger.info("代办预约页面跳转开始,用户成功登陆openId==" + openid);
					//获取用户手机号
					Map<String, String> phoRetMap = null;
					phoRetMap = WeixinUtil.getUserPhoneFromPICC(openid);
					if("0".equals(phoRetMap.get("retcode"))){
						username = phoRetMap.get("realname");
						modelView.addObject("username", username);  //微信个人中心用户名，用作服务预约的联系人姓名
						//根据客户手机号，调用客户关系管理平台接口，查询客户可享受的服务列表
						ReqTrans1052Entity reqTrans1052Entity = new ReqTrans1052Entity();
						phoneno = phoRetMap.get("phoneno");
						reqTrans1052Entity.setPhonenumber(phoneno);
						modelView.addObject("phoneno", phoneno);
						resServicelist = kfSelfService.fwyyInfoQuery(reqTrans1052Entity);
						if("1".equals(resServicelist.get("retcode"))){
							//请求成功
							modelView.addObject("res","1");
							modelView.addObject("servicelist",resServicelist.get("servicelist"));	
						}else{
							//请求失败
							modelView.addObject("res","0");
							modelView.addObject("msg","没有查询到您可以享受的服务");
						}
						
					}else{
						logger.info("代办预约页面跳转,获取用户手机号失败");
						modelView.addObject("res",phoRetMap.get("retcode"));
						modelView.addObject("msg",phoRetMap.get("retmsg"));
					}
					
					
				}else{
					logger.info("代办预约页面跳转,请关注[北京人保财险]公众号，绑定身份信息并登录");
					modelView.addObject("res","0");
					modelView.addObject("msg", "请关注[北京人保财险]公众号，绑定身份信息并登录");
				}
			}else{
				logger.info("代办预约页面跳转,请绑定身份信息并登录");
				modelView.addObject("res","0");
				modelView.addObject("msg","请绑定身份信息并登录");
			}
		}catch(Exception e){
			logger.info("代办预约页面跳转,系统繁忙");
			e.printStackTrace();
			modelView.addObject("res","0");
			modelView.addObject("msg","系统繁忙");
		}
		
		return modelView;
	}
	
	/**
	 * 
			* 描述:代办服务预约-跳转到各服务的预约界面
			* @param request
			* @param response
			* @param session
			* @return
			* @author 戴元守 2017年9月29日 上午8:41:36
	 */
	@RequestMapping(value={"/dbfwPrepareRegist.do"})
	public ModelAndView dbfwyy(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String openid = request.getParameter("openid");
		String username = request.getParameter("username");
		String phoneno = request.getParameter("phoneno");
		String servicecode = request.getParameter("servicecode");
		String servicetype = request.getParameter("servicetype");
		String cusid = request.getParameter("cusid");
		String customercname = request.getParameter("customercname");
		String cserid = request.getParameter("cserid");
		
		ModelAndView modelView = null;

		//先判断是否在微信浏览器中打开
		com.alibaba.fastjson.JSONObject explore = com.wap.util.CommonUtils.checkWxOpen(request, openid);
		if("1".equals(explore.get("resCode"))){
			modelView = new ModelAndView("browser_err");
			return modelView;
		}
		
		List<CrsBrandEntity> brandList = new ArrayList<CrsBrandEntity>();
		//判断不同服务类型跳转到不同页面
		if("CS00000002".equals(servicecode)){         //代驾
			modelView= new ModelAndView("/kf/selfservice/dbfw/djfwyuyue");
		}else if ("CS00000005".equals(servicecode)){  //代办验车
			modelView= new ModelAndView("/kf/selfservice/dbfw/dbycyuyue");
		}else if ("CS00000017".equals(servicecode)){  //代办保养
			modelView= new ModelAndView("/kf/selfservice/dbfw/dbbyyuyue");
			
			//代办保养页面，需要获取品牌
			brandList = SysDicHelper.getInstance().getCrsBrandList();
			modelView.addObject("brandList",brandList);	
		}else if ("CS00000012".equals(servicecode)){   //代办理赔
			modelView= new ModelAndView("/kf/selfservice/dbfw/dblpyuyue");
		}
		
		modelView.addObject("openid",openid);
		modelView.addObject("phoneno",phoneno);
		modelView.addObject("linkerphoneno",phoneno);
		modelView.addObject("username",username);
		modelView.addObject("servicecode",servicecode);
		modelView.addObject("servicetype",servicetype);
		modelView.addObject("cusid",cusid);
		modelView.addObject("customercname",customercname);
		modelView.addObject("cserid",cserid);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date()); 
		calendar.add(Calendar.MINUTE, 10);
		String tenMinLater = df.format(calendar.getTime());
		
		modelView.addObject("restime",tenMinLater);
		try{
			
			//获取用户地理位置信息
			Map<String, String> addrRetMap = null;
			addrRetMap = WeixinUtil.getUserLocationFromPICC(openid);
			if("0".equals(addrRetMap.get("retcode")) && !"".equals(addrRetMap.get("address"))){
				modelView.addObject("startaddress",addrRetMap.get("address"));	
			}else{
				modelView.addObject("startaddress","北京市");
				logger.info("代办预约页面跳转,获取用户地理位置信息失败");
			}
			//跳转代办验车或代办理赔页面，查询车牌号
			if("CS00000005".equals(servicecode)||"CS00000012".equals(servicecode)){
				//获取客户车辆信息
				Map<String, String> returnmap = new HashMap<String, String>();
				returnmap = WeixinUtil.getUserNameFormPICC(openid);
				//获取车牌号
				CarsListReturnEntity usercarlist = WeixinUtil.getCarsListFromPICC(returnmap.get("username"));
				if("0".equals(usercarlist.getHead().getRetcode())){
					modelView.addObject("carList",usercarlist.getBody().getDataList());	
				}else{
					logger.info("事故救援预约,获取用户车辆信息失败！");
					modelView.addObject("msg",usercarlist.getHead().getRetmsg());
				}
			}
				
			modelView.addObject("res","1");
		}catch(Exception e){
			logger.info("代办预约页面跳转,系统繁忙!");
			e.printStackTrace();
			modelView.addObject("res","0");
			modelView.addObject("msg","系统繁忙");
		}
		return modelView;
	}
	
	/**
	 * 
			* 描述:代办保养页面，根据选择的品牌查询车型列表，返回原页面
			* @param request
			* @param response
			* @param session
			* @return
			* @author fenghairui
			* @date 2017年11月7日 14:41:36
	 */
	@RequestMapping(value={"/dbfwQueryModel.do"})
	@ResponseBody
	public com.alibaba.fastjson.JSONObject dbfwQueryModel(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		com.alibaba.fastjson.JSONObject res = new com.alibaba.fastjson.JSONObject();
		String brandname = request.getParameter("brandname"); 
		
		List<CrsModelEntity> modelList = new ArrayList<CrsModelEntity>();
		modelList = SysDicHelper.getInstance().getCrsModelList(brandname);
		if(modelList.size()>0){
			String jsonModel =JSONArray.fromObject(modelList).toString();
			res.put("modelList", jsonModel);
			res.put("retcode", "1");
			res.put("retmsg", "查询车型成功");
		}else{
			res.put("retcode", "0");
			res.put("retmsg", "查询车型失败");
		}

		return res;
	}
	
	
	/**
	* 描述:代办服务预约-调用客户关系管理平台接口保存预约请求
	* @param request
	* @param response
	* @param session
	* @return
	* @author fenghairui
	* @date  2017-10-30
	 */
	@RequestMapping(value={"/dbfwSubmit.do"})
	@ResponseBody
	public com.alibaba.fastjson.JSONObject djfwyuyue(HttpServletRequest request){
		com.alibaba.fastjson.JSONObject res = new com.alibaba.fastjson.JSONObject();
		String openid = request.getParameter("openid");
		String username = request.getParameter("username");
		String phoneno = request.getParameter("phoneno");
		String linkerphoneno = request.getParameter("linkerphoneno");
		String servicecode = request.getParameter("servicecode");
		String servicetype = request.getParameter("servicetype");
		String cusid = request.getParameter("cusid");
		String customercname = request.getParameter("customercname");
		String cserid = request.getParameter("cserid"); 
		String licenseno = request.getParameter("licenseno"); 
		String remarks = request.getParameter("remarks"); 
		String appointment = request.getParameter("appointment"); 
		String startaddress = request.getParameter("startaddress"); 
		String endaddress = request.getParameter("endaddress"); 
		String purchasedate = request.getParameter("purchasedate"); 
		String runMails = request.getParameter("runMails")!=null&&!"".equals(request.getParameter("runMails"))?request.getParameter("runMails"):"0.0";
		String brandName = request.getParameter("brandName"); 
		String modelCode = request.getParameter("modelCode"); 
		String engineno = request.getParameter("engineno"); 
		String flag = request.getParameter("flag"); 
		
		
		//先判断是否在微信浏览器中打开
		com.alibaba.fastjson.JSONObject explore = com.wap.util.CommonUtils.checkWxOpen(request, openid);
		if("1".equals(explore.get("resCode"))){
			res.put("retcode", "0");
			res.put("retmsg", "请在微信浏览器中打开");
			return res;
		}
		
		ReqTrans1053Entity reqTrans1053Entity = new ReqTrans1053Entity();
		ReqTrans1053YuyueDataEntity reqTrans1053YuyueDataEntity = new ReqTrans1053YuyueDataEntity();
		//公共部分赋值
		reqTrans1053YuyueDataEntity.setLicenseno(licenseno);
		reqTrans1053YuyueDataEntity.setServiceCode(servicecode);
		reqTrans1053YuyueDataEntity.setServiceType(servicetype);
		reqTrans1053YuyueDataEntity.setCustomerID(cusid);
		reqTrans1053YuyueDataEntity.setCustserviceID(cserid);
		reqTrans1053YuyueDataEntity.setCustomerName(customercname);
		reqTrans1053YuyueDataEntity.setCustomerMobile(phoneno);
		reqTrans1053YuyueDataEntity.setRegistCode("微信");   //默认为微信端
		reqTrans1053YuyueDataEntity.setLinkerName(username);
		reqTrans1053YuyueDataEntity.setLinkerMobile(linkerphoneno);
		reqTrans1053YuyueDataEntity.setRemark(remarks);
		reqTrans1053YuyueDataEntity.setAppointment(appointment);
		
		try {
			//根据不同预约服务类型设置请求值
			if("djfw".equals(flag)){
				//代驾服务
				reqTrans1053YuyueDataEntity.setServiceName("特殊代驾");
				reqTrans1053YuyueDataEntity.setStartAddress(startaddress);
				reqTrans1053YuyueDataEntity.setEndAddress(endaddress);
				//预约服务类型   01：代驾预约    02：代办验车    03：代办保养    04：代办理赔
				reqTrans1053Entity.setOperatetype("01");
			}else if("dbby".equals(flag)){
				//代办保养
				reqTrans1053YuyueDataEntity.setServiceName("上门保养服务");
				reqTrans1053YuyueDataEntity.setPurchasedate(purchasedate);
				reqTrans1053YuyueDataEntity.setRunMails(runMails);
				reqTrans1053YuyueDataEntity.setBrandName(brandName);
				reqTrans1053YuyueDataEntity.setModelCode(modelCode);
				reqTrans1053YuyueDataEntity.setStartAddress(startaddress);
				//预约服务类型   01：代驾预约    02：代办验车    03：代办保养    04：代办理赔
				reqTrans1053Entity.setOperatetype("03");
			}else if("dblp".equals(flag)){
				//代办理赔
				reqTrans1053YuyueDataEntity.setServiceName("代办理赔服务");
				reqTrans1053YuyueDataEntity.setEngineno(engineno);
				reqTrans1053YuyueDataEntity.setStartAddress(startaddress);
				//预约服务类型   01：代驾预约    02：代办验车    03：代办保养    04：代办理赔
				reqTrans1053Entity.setOperatetype("04");
			}else if("dbyc".equals(flag)){
				//代办验车
				reqTrans1053YuyueDataEntity.setServiceName("代办验车服务");
				reqTrans1053YuyueDataEntity.setEngineno(engineno);
				reqTrans1053YuyueDataEntity.setStartAddress(startaddress);
				//预约服务类型   01：代驾预约    02：代办验车    03：代办保养    04：代办理赔
				reqTrans1053Entity.setOperatetype("02");
			}
			reqTrans1053Entity.setReqTrans1053YuyueDataEntity(reqTrans1053YuyueDataEntity);
			res = kfSelfService.fwyyInfoRegister(reqTrans1053Entity);
			
		} catch (EpiccException e) {
			res.put("retcode", "0");
			res.put("retmsg", "代办预约提交失败，请联系人工客服");
			logger.info("代办预约提交报错：");
			if(e instanceof EpiccException){
				EpiccException ep = (EpiccException)e;
				logger.info("代办预约提交报错："+ ep.getErrMess());
			}else{
				res.put("retmsg", e.getMessage());
				logger.info("代办预约提交报错："+ e.getMessage());
			}
		}
		return res;
	}

}
