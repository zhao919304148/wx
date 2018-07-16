package com.wap.dzsw.controller;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sys.exception.EpiccException;
import com.wap.dzsw.entity.BrandKeFu;
import com.wap.dzsw.entity.BrandNetwork;
import com.wap.dzsw.entity.CancellationOrderEntity;
import com.wap.dzsw.entity.MoveTheCar;
import com.wap.dzsw.entity.ServiceReservationOrderEntity;
import com.wap.dzsw.entity.Verification;
import com.wap.dzsw.service.FuWuService;
import com.wap.util.RSAUtil;
import com.wx.util.WeixinUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = { "/fuwu" })
public class FuWuController {

	private static final Log logger = LogFactory.getLog(FuWuController.class);

	private static final String SEREORRETURN = "/dzsw/SeReList";

	@Autowired
	private FuWuService fuWuService = null;

	public void setFuWuService(FuWuService fuWuService) {
		this.fuWuService = fuWuService;
	}

	/**
	 * 
	 * 描述:服务预约订单提交
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @author 李朝晖 2016年11月22日 上午11:18:11
	 */
	@ResponseBody
	@RequestMapping(value = { "/serviceReOrder.do" })
	public JSONObject SeReOrder(HttpServletRequest request, ServiceReservationOrderEntity serviceReOrderEntity) {
		System.out.println("openId:"+ serviceReOrderEntity.getOpenId());
		JSONObject jsonObject = new JSONObject();
		String resCode = "";
		String msg = "";
		String openId = serviceReOrderEntity.getOpenId();
		logger.info("服务预约订单开始，用户opinId==" + openId);
		Map map = null;
		try {
			//拦截
			String userAgent = request.getHeader("User-Agent");
			if(!StringUtils.isNotEmpty(userAgent)){
				jsonObject.put("resCode","1");
				jsonObject.put("msg","请在微信客户端打开链接！");
				return jsonObject;
			}
			if(userAgent.indexOf("MicroMessenger") == -1){
				System.out.println("userAgent not have MicroMessenger!");
				jsonObject.put("resCode","1");
				jsonObject.put("msg","请在微信客户端打开链接！");
				return jsonObject;
			}
			if("".equals(openId)){
				jsonObject.put("resCode","1");
				jsonObject.put("msg", "请在微信客户端打开链接！");
				return jsonObject;
			}
			Map<String, String>  returnmap = WeixinUtil.getUserNameFormPICC(openId);
			if(returnmap == null){
				jsonObject.put("resCode","2");
				jsonObject.put("msg","请绑定身份信息并登录");
				return jsonObject;
			}
			String retcode = returnmap.get("retcode");
			String retmsg = returnmap.get("retmsg");
			String identifytype = returnmap.get("identifytype");
			String identifyno = returnmap.get("identifyno");
			if(!"0".equals(retcode)){
				jsonObject.put("resCode","2");//未登录
				jsonObject.put("msg",StringUtils.isEmpty(retmsg) ? "请关注[北京人保财险]公众号，绑定身份信息并登录" : retmsg);
				return jsonObject;
			}
			if(StringUtils.isEmpty(identifytype) || StringUtils.isEmpty(identifyno)){
				jsonObject.put("resCode","3");
				jsonObject.put("msg","请到个人中心完善个人资料！");
				return jsonObject;
			}
			serviceReOrderEntity.setIdentifyNo(identifyno);
			serviceReOrderEntity.setIdentifyType(identifytype);
			//拦截end
			if("1004".equals(serviceReOrderEntity.getCardType())){
				serviceReOrderEntity.setSendType("1");
			}
			serviceReOrderEntity.setOpenId("123");
			serviceReOrderEntity.setIdentifyNo("111111");
			serviceReOrderEntity.setIdentifyType("01");
			serviceReOrderEntity.setOpenId(openId);
			System.out.println(serviceReOrderEntity.toString());
			map = fuWuService.getServiceReOrder(serviceReOrderEntity);
			System.out.println(map);
			resCode = (String) map.get("res");
			msg = (String) map.get("msg");
			if ("1".equals(resCode)) {
				jsonObject.put("resCode", "1");
				jsonObject.put("msg", msg);
			} else {
				logger.info(msg);
				System.out.println(msg);
				jsonObject.put("resCode", resCode);
				jsonObject.put("msg", "预约失败，请重试！");
			}
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.info("异常信息："+e.getMessage());
			jsonObject.put("resCode", 1);
			jsonObject.put("msg", "服务异常，请重试！");
			return jsonObject;
		}
		return jsonObject;
	}

	/**
	 * 
	 * 描述:服务预约类订单接口查询
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @author 李朝晖 2016年12月6日 上午10:29:09
	 */
	/*@ResponseBody
	@RequestMapping(value = { "/seReOrInquiry.do" })
	public JSONObject seReOrInquiry(HttpServletRequest request,	ServiceReOrInquiryEntity serviceReOrInquiryEntity) {
		JSONObject jsonObject = new JSONObject();
		String res = "";
		String msg = "";
		Map map = null;
		String openId = request.getParameter("openId");
		try {
			//拦截
			String userAgent = request.getHeader("User-Agent");
			if(!StringUtils.isNotEmpty(userAgent)){
				jsonObject.put("resCode","1");
				jsonObject.put("msg","请在微信客户端打开链接！");
				return jsonObject;
			}
			if(userAgent.indexOf("MicroMessenger") == -1){
				System.out.println("userAgent not have MicroMessenger!");
				jsonObject.put("resCode","1");
				jsonObject.put("msg","请在微信客户端打开链接！");
				return jsonObject;
			}
			if("".equals(openId)){
				jsonObject.put("resCode","1");
				jsonObject.put("msg", "请在微信客户端打开链接！");
				return jsonObject;
			}
			Map<String, String>  returnmap = WeixinUtil.getUserNameFormPICC(openId);
			if(returnmap == null){
				jsonObject.put("resCode","2");
				jsonObject.put("msg","请绑定身份信息并登录");
				return jsonObject;
			}
			String retcode = returnmap.get("retcode");
			String retmsg = returnmap.get("retmsg");
			String identifytype = returnmap.get("identifytype");
			String identifyno = returnmap.get("identifyno");
			if(!"0".equals(retcode)){
				jsonObject.put("resCode","2");//未登录
				jsonObject.put("msg",StringUtils.isEmpty(retmsg) ? "请关注[北京人保财险]公众号，绑定身份信息并登录" : retmsg);
				return jsonObject;
			}
			if(StringUtils.isNotEmpty(identifytype) && StringUtils.isNotEmpty(identifyno)){
				jsonObject.put("resCode","3");
				jsonObject.put("msg","请到个人中心完善个人资料！");
				return jsonObject;
			}
			//拦截end  

			map = fuWuService.getSeReOrInquiry(serviceReOrInquiryEntity);
			if (map == null) {
				jsonObject.put("res", "1");
				jsonObject.put("msg", "未查询预约订单");
				return jsonObject;
			}
			if ("1".equals(res)) {
				ResTrans1024BasePartEntity resTrans1024 = (ResTrans1024BasePartEntity)map.get("resTrans1024BasePartEntity");
				jsonObject.put("resCode", "0");
				jsonObject.put("msg", msg);
				jsonObject.put("resTrans1024", resTrans1024);
				return jsonObject;
			}
		} catch (EpiccException e) {
			e.printStackTrace();
			jsonObject.put("resCode", "0");
			jsonObject.put("msg", e.getErrMess());

			return jsonObject;
		}
		return jsonObject;
	}*/

	/**
	 * 
	 * 描述: 订单取消接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author 李朝晖 2016年12月6日 上午10:50:05
	 */
	@ResponseBody
	@RequestMapping(value = { "/CanCeation.do" })
	public JSONObject getCanCeation(HttpServletRequest request, HttpServletResponse response, CancellationOrderEntity cancell) {
		JSONObject json = new JSONObject();
		String res = "";
		String msg = "";
		ModelAndView model = new ModelAndView();
		Map<String, String> returnmap = new HashMap<String, String>();
		String openid = request.getParameter("openId");
		logger.info("服务预约订单开始，用户opinId==" + openid);
		Map map = null;
		try {
			returnmap = WeixinUtil.getUserNameFormPICC(openid);
			if (null == returnmap) {
				json.put("resCode", "2");
				json.put("msg", "请绑定身份信息并登陆");
				return json;
			} 
			cancell.setCardNo("1");
			map = fuWuService.getCancellation(cancell);
			if (map != null) {
				res = (String) map.get("resCode");
				msg = (String) map.get("msg");
				if ("1".equals(res)) {
					json.put("resCode", res);
					json.put("msg", msg);
				} else {
					json.put("res", res);
					json.put("msg", msg);
				}
			} else {
				json.put("resCode", "0");
				json.put("msg", msg);
			}

		} catch (EpiccException e) {
			e.printStackTrace();
			json.put("resCode", "0");
			json.put("msg", e.getErrMess());
		}
		return json;
	}

	/**
	 * 
	 * 描述:挪车服务接口
	 * 
	 * @param request
	 * @return
	 * @author 李朝晖 2016年12月9日 下午2:50:01
	 */
	@ResponseBody
	@RequestMapping(value = { "/MoveTheCar.do" })
	public JSONObject getMoveTheCar(HttpServletRequest request, MoveTheCar moveTheCar) {
		JSONObject json = new JSONObject();
		String resCode = "";
		String msg = "";
		System.out.println(moveTheCar.getName());
		Map map = null;
		ModelAndView model = new ModelAndView();
		Map<String, String> returnmap = new HashMap<String, String>();
		String openid = request.getParameter("openId");
		logger.info("服务预约订单开始，用户openId" + openid);
		try {
			returnmap = WeixinUtil.getUserNameFormPICC(openid);
			if (null != returnmap) {
				/*moveTheCar.setLicenseNo("1");
				moveTheCar.setName("1");
				moveTheCar.setPhoneNumber("1");*/
				map = fuWuService.getMoveTheCar(moveTheCar);
				if (map != null) {
					resCode = (String) map.get("res");
					msg = (String) map.get("msg");
					if ("0".equals(resCode)) {
						json.put("resCode", "0");
						json.put("msg", "成功");
					} else {
						json.put("resCode", "1");
						json.put("msg", "开通挪车服务失败");
					}
				} else {
					json.put("resCode", "1");
					json.put("msg", "失败");
				}
			} else {
				json.put("resCode", "2");
				json.put("msg", "请绑定身份信息并登陆");
			}
		} catch (EpiccException e) {
			e.printStackTrace();
			json.put("resCode", "1");
			json.put("msg", e.getErrMess());
			return json;
		}
		return json;
	}

	/**
	 * 
	 * 描述:  验证码
	 * @param verCation
	 * @return
	 * @author  李朝晖 2017年1月6日 上午10:17:26
	 * 		  @throws EpiccException 
	 */
	@ResponseBody
	@RequestMapping(value = { "/verification.do" })
	public JSONObject verificationCode(HttpServletRequest request, Verification  verCation) {
		JSONObject json = new JSONObject();
		String resCode = "";
		String msg = "";
		Map map = null;
		Map<String, String> returnmap = new HashMap<String, String>();
		String openid = request.getParameter("openId");
		logger.info("服务验证码，用户"+openid);
		returnmap = WeixinUtil.getUserNameFormPICC(openid);
		if( null != returnmap){
			try {
				map = fuWuService.verification(verCation);
				if(map != null){
					resCode = (String) map.get("resCode");
					msg = (String) map.get("msg");
					if("0".equals(resCode)){
						json.put("resCode", "0");
						json.put("msg", "成功");
						json.put("ValadateCode", map.get("ValadateCode"));
						return json;
					}else{
						json.put("resCode", "1");
						json.put("msg", "发送失败");
						return json;
					}
				}else{
					json.put("resCode", "2");
					json.put("msg", "请绑定身份信息并登陆");
					return json;
				}
			} catch (EpiccException e) {
				e.printStackTrace();
				json.put("resCode", "1");
				json.put("msg", e.getErrMess());
				return json;
			}
		}
		return json;
	}


	/**
	 * 
	 * 描述:客服-网点列表
	 * @param networkType
	 * @return
	 * @author qex2017年1月13日 下午2:03:59
	 */
	@ResponseBody
	@RequestMapping(value = { "/getNetworkService.do" })
	public JSONObject getNetworkService(String networkType){
		System.out.println("请求：networktType="+networkType);
		JSONObject jsonObject = new JSONObject();
		List<BrandNetwork> list = null;
		try {
			if(StringUtils.isNotBlank(networkType)){//玻璃
				list = fuWuService.getNetworkServiceBoLi(networkType);
				jsonObject.put("resCode", "0");
				jsonObject.put("data", list);
			}else {
				jsonObject.put("resCode", "1");
				jsonObject.put("msg", "参数异常");
			}
			
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			e.printStackTrace();
			jsonObject.put("resCode", "1");
			jsonObject.put("msg", "系统异常！请与系统管理员联系。");
		}
		return jsonObject;
	}

	/**
	 * 
	 * 描述:	客服-品牌-网点列表
	 * @param networktType
	 * @return
	 * @author qex 2017年1月13日 下午2:03:25
	 */
	@ResponseBody
	@RequestMapping(value = { "/getNetworkBrand.do" })
	public JSONObject getNetworkBrand(String networkType){
		Assert.isTrue(networkType!=null&&NumberUtils.isDigits(networkType));
		System.out.println("请求：networktType="+networkType);
		JSONObject json = new JSONObject();
		List<BrandKeFu> list=null;
		try {
			//直赔和喷漆维修保养
			if(StringUtils.isNotBlank(networkType)){
				list = fuWuService.getNetworkBrandPenQi(networkType);
				json.put("resCode", "0");
				json.put("data", list);
			}else{
				json.put("resCode", "1");
				json.put("msg", "参数错误");
			}
			
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			e.printStackTrace();
			json.put("resCode", "1");
			json.put("msg", "系统异常！请与系统管理员联系。");
		}finally{
			logger.info("方法getNetworkBrand,请求参数：[networkType = "+networkType+"],返回报文：["+json.toString()+"]");
		}
		
		return json;
	}

	/**
	 * 
			* 描述:宽图加密
			* @param openId
			* @param userId
			* @return
			* @author qex 2017年3月6日 下午1:38:37
	 */
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping(value={"toKuanTu.do"})
	public JSONObject toKuanTu(String cardNo,String cardPass){
		JSONObject jsonObject = new JSONObject();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("cardNumber=").append(cardNo)
					.append("&randomCode=").append(cardPass)
					.append("&createTime=").append(new Date().getTime());
		logger.info("stringBuffer:"+stringBuffer.toString());
		try {
			String encryptByPublicKey = RSAUtil.encryptByPublicKey(stringBuffer.toString(),Base64.decodeBase64(RSAUtil.RSA_PUBLIC_KEY));
			logger.info("jiami:"+encryptByPublicKey);
			String encode = URLEncoder.encode(encryptByPublicKey);
			jsonObject.put("jiami", encode);
			logger.info("jiamiencode:"+encode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

}
