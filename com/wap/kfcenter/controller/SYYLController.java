package com.wap.kfcenter.controller;

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
import com.wap.kfcenter.service.KfSelfService;
import com.wap.trans.entity.tr_1047.ReqTrans1047Entity;
import com.wap.trans.entity.tr_1048.ReqTrans1048Entity;
import com.wap.util.CommonUtils;

@Controller
@RequestMapping(value = { "/syyl" })
public class SYYLController {

	private static final Log logger = LogFactory.getLog(SYYLController.class);
	
	@Autowired(required = false)
	private KfSelfService kfSelfService = null;

	/**
	 * 
	 * 描述:受邀有礼页面判断是否登录  受邀暂时不判断
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @author zhangjian2017年10月19日 下午3:43:33
	 */
	@RequestMapping(value = { "/syylPrepare.do" })
	public ModelAndView syylPrepare(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		// 受邀有礼页面
		ModelAndView syylView = null;
		String openid = request.getParameter("openId");
		logger.info("受邀有礼prepare openid=" + openid);
		JSONObject openJson = CommonUtils.checkWxOpen(request, openid);
		String checkOpenCode = (String)openJson.get("resCode");
		if(!"0".equals(checkOpenCode)){
//			syylView = new ModelAndView("/browser_err");
//			return syylView;
		}
		syylView = new ModelAndView("/kf/selfservice/syyl/syylActive");
		return syylView;
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
	@RequestMapping(value = "getSYYLInfo.do")
	@ResponseBody
	public com.alibaba.fastjson.JSONObject getSYYLInfo(String licenseNo,String yqcode,HttpServletRequest request,HttpServletResponse response){
		com.alibaba.fastjson.JSONObject res = null;
		com.alibaba.fastjson.JSONObject resEntity = null;
		ReqTrans1047Entity req1047Entity = new ReqTrans1047Entity();
		req1047Entity.setLicenseno(licenseNo);
		req1047Entity.setLicensecolorcode("01");// 默认蓝色
		req1047Entity.setInteruse("01");//受邀有礼
		req1047Entity.setCheckcode(yqcode);
		String retcode = "";
		try {
			if(StringUtils.isNotEmpty(licenseNo) && StringUtils.isNotEmpty(yqcode)){
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
				res.put("msg", "车牌号和邀请码不能为空");
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
	 * 
	 * 描述:受邀注册提交
	 * 
	 * @param licenseno
	 * @param engineno
	 * @param customername
	 * @param identityno
	 * @param phonenumber
	 * @param yqcode
	 * @param request
	 * @return
	 * @author zhangjian2017年10月20日 上午8:44:32
	 */
	@RequestMapping(value = "syylSubmit.do")
	@ResponseBody
	public com.alibaba.fastjson.JSONObject syylSubmit(String licenseno,
			String engineno, String customername, String identityno,
			String phonenumber, String yqcode, String comcode,HttpServletRequest request) {
		
		com.alibaba.fastjson.JSONObject res = new com.alibaba.fastjson.JSONObject();
		com.alibaba.fastjson.JSONObject resEntity = null;
		ReqTrans1048Entity req1048Entity = null;
		String openid = request.getParameter("openId");
		logger.info("受邀有礼submit openid=" + openid);
		JSONObject openJson = CommonUtils.checkWxOpen(request, openid);
		String checkOpenCode = (String)openJson.get("resCode");
		if(!"0".equals(checkOpenCode)){
			res.put("res", "0");
			res.put("msg", "请在微信公众号中进行访问");
			return res;
		}
		//注册车主秘书前字段校验
		if(StringUtils.isNotEmpty(licenseno) && StringUtils.isNotEmpty(engineno) &&
				StringUtils.isNotEmpty(customername) && StringUtils.isNotEmpty(identityno) &&
				StringUtils.isNotEmpty(phonenumber) && StringUtils.isNotEmpty(yqcode)){
			req1048Entity = new ReqTrans1048Entity();
			req1048Entity.setLicenseno(licenseno);
			req1048Entity.setLicensecolorcode("01");// 默认蓝色
			req1048Entity.setEngineno(engineno);
			req1048Entity.setCustomername(customername);
			req1048Entity.setIdentityno(identityno);
			req1048Entity.setPhonenumber(phonenumber);
			req1048Entity.setFlag("1110000000");// 默认开通四项服务
			req1048Entity.setYqcode(yqcode);
			req1048Entity.setComcode(comcode);
			try {
				resEntity = kfSelfService.syylSubmit(req1048Entity);
				if(resEntity != null){
					res.put("res", resEntity.get("retcode"));
					res.put("msg",resEntity.get("retmsg"));
				}else{
					res.put("res", "0");
					res.put("msg","注册失败");
				}
			} catch (Exception e) {
				res.put("resCode", "0");
				if (e instanceof EpiccException) {
					EpiccException ep = (EpiccException) e;
					res.put("msg", ep.getErrMess());
				} else {
					res.put("msg", e.getMessage());
				}
			}
		}else{
			res.put("res", "0");
			res.put("msg", "提交信息有误");
		}
		return res;
	}

}
