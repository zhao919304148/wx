package com.wap.kfcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.sys.dic.SysDicHelper;
import com.sys.dic.entity.BrandEntity;
import com.sys.exception.EpiccException;
import com.wap.kfcenter.entity.carslist.CarsListReturnDataEntity;
import com.wap.kfcenter.entity.carslist.CarsListReturnEntity;
import com.wap.kfcenter.service.KfSelfService;
import com.wap.trans.entity.tr_1051.ReqTrans1051Entity;
import com.wx.util.WeixinUtil;

@Controller
@RequestMapping(value={"/sgjy"})
public class SGJYController {
	
	private static final Log logger = LogFactory.getLog(KfCenterController.class);
	
	@Autowired(required = false)
	private KfSelfService kfSelfService = null;
	

	/**
	 * 
	* 描述:事故救援预约，根据openid获取用户证件类型及证件号，并验证是否登录微信，
	* 然后根据证件号获取用户姓名、联系电话和车牌号；
	* @param request
	* @param response
	* @param session
	* @return
	* @author 戴元守 2017年9月15日 下午4:21:26
	 */
	@RequestMapping(value={"/sgjyPrepare.do"})
	public ModelAndView sgjyPrepare(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		//事故救援预约界面
		ModelAndView modelView = new ModelAndView("kf/selfservice/sgjy/sgjyyuyue");
		String openid = request.getParameter("openId");
		//正式上线把此处注释掉
//		openid = "osX-mwAC_JYB5Jpl9m0OXGPWdDn8";  
		logger.info("事故救援预约,用户openId==" + openid);
		modelView.addObject("openId", openid);
		
		//先判断是否在微信浏览器中打开
		com.alibaba.fastjson.JSONObject explore = com.wap.util.CommonUtils.checkWxOpen(request, openid);
		if("1".equals(explore.get("resCode"))){
			modelView = new ModelAndView("browser_err");
			return modelView;
		}
		
		Map<String, String> returnmap = new HashMap<String, String>();
		List<BrandEntity> brandList = new ArrayList<BrandEntity>();
		String retcode = "";
		String retmsg = "";
		String identifytype = "";
		String identifyno = "";
		String username = "";
		CarsListReturnEntity usercarlist = null;
		try{
			returnmap = WeixinUtil.getUserNameFormPICC(openid);
			if(returnmap != null){
				retcode = returnmap.get("retcode");
				retmsg = returnmap.get("retmsg");
				identifytype = returnmap.get("identifytype");
				identifyno = returnmap.get("identifyno");
				username = returnmap.get("username");
				if("0".equals(retcode)){
					logger.info("事故救援预约,用户成功登陆!");
					
					//获取品牌
					//是否需要与车牌对应
					brandList = SysDicHelper.getInstance().getBrandList();
					modelView.addObject("brandList",brandList);	
					
					//获取用户手机号
					Map<String, String> phoRetMap = null;
					phoRetMap = WeixinUtil.getUserPhoneFromPICC(openid);
					if("0".equals(phoRetMap.get("retcode"))){
						modelView.addObject("phoneno",phoRetMap.get("phoneno"));	
						modelView.addObject("username",phoRetMap.get("realname"));
					}else{
						logger.info("事故救援预约,获取用户手机号失败!");
						modelView.addObject("res",phoRetMap.get("retcode"));
						modelView.addObject("msg",phoRetMap.get("retmsg"));
					}
					
					//获取用户地理位置信息
					Map<String, String> addrRetMap = null;
					addrRetMap = WeixinUtil.getUserLocationFromPICC(openid);
					if("0".equals(addrRetMap.get("retcode")) && !"".equals(addrRetMap.get("address"))){
						modelView.addObject("caraddress",addrRetMap.get("address"));	
					}else{
						logger.info("事故救援预约,获取用户地理位置信息失败!");
						modelView.addObject("caraddress","北京市");	
					}
					
					//获取车牌号
					usercarlist = WeixinUtil.getCarsListFromPICC(username);
					if("0".equals(usercarlist.getHead().getRetcode())){
						modelView.addObject("carList",usercarlist.getBody().getDataList());	
					}else{
						logger.info("事故救援预约,获取用户车辆信息失败！");
						modelView.addObject("msg",usercarlist.getHead().getRetmsg());
					}
					modelView.addObject("res","1");					
				}else{
					logger.info("事故救援预约,请关注[北京人保财险]公众号，绑定身份信息并登录！");
					modelView.addObject("res","0");
					modelView.addObject("msg","请关注[北京人保财险]公众号，绑定身份信息并登录");
				}
			}else{
				logger.info("事故救援预约,请绑定身份信息并登录！");
				modelView.addObject("res","0");
				modelView.addObject("msg","请绑定身份信息并登录");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("事故救援预约,系统繁忙！"+e.getMessage());
			modelView.addObject("res","0");
			modelView.addObject("msg","系统繁忙");
		}
		
		return modelView;
	}
	/**
	 * 
			* 描述:事故救援预约-信息保存
			* @param customername
			* @param mobile
			* @param licenseno
			* @param brandmodel
			* @param carsite
			* @param cardetail
			* @param remark
			* @param request
			* @return
			* @author 戴元守 2017年10月24日 上午1:11:18
	 */
	@RequestMapping(value="sgjySubmit.do")
	@ResponseBody
	public com.alibaba.fastjson.JSONObject sgjySubmit(String customername,String mobile,String licenseno,String brandmodel,String carsite,String cardetail,String remark,HttpServletRequest request){
		com.alibaba.fastjson.JSONObject res = new com.alibaba.fastjson.JSONObject();
		ReqTrans1051Entity reqTrans1051Entity = new ReqTrans1051Entity();
		
		reqTrans1051Entity.setCustomername(customername);
		reqTrans1051Entity.setMobile(mobile);
		reqTrans1051Entity.setLicenseno(licenseno);
		reqTrans1051Entity.setBrandmodel(brandmodel);
		reqTrans1051Entity.setCarsite(carsite);
		reqTrans1051Entity.setCardetail(cardetail);
		reqTrans1051Entity.setRemark(remark);
		
		try {
			res = kfSelfService.sgjyInfoSave(reqTrans1051Entity);
		} catch (Exception e) {
			res.put("retcode", "0");
			logger.info("事故救援预约提交失败！" + e.getMessage());
			if(e instanceof EpiccException){
				EpiccException ep = (EpiccException)e;
				res.put("retmsg", ep.getErrMess());
			}else{
				res.put("retmsg", e.getMessage());
			}
		}
		return res;	
	}


}
