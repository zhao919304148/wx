package com.wap.kfcenter.controller;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sys.dic.SysDicHelper;
import com.wap.kfcenter.entity.ActiveReceiveNumEntity;
import com.wap.kfcenter.entity.ActiveReceiveNumQueryEntity;
import com.wap.kfcenter.entity.CzReqListEntity;
import com.wap.kfcenter.entity.CzResListEntity;
import com.wap.kfcenter.entity.ExchangeQueryReqEntity;
import com.wap.kfcenter.entity.ExchangeQueryResEntity;
import com.wap.kfcenter.entity.ExchangeReqEntity;
import com.wap.kfcenter.entity.ExchangeReqListEntity;
import com.wap.kfcenter.entity.ExchangeResEntity;
import com.wap.kfcenter.entity.PolicyEntity;
import com.wap.kfcenter.entity.PolicyQueryEntity;
import com.wap.kfcenter.service.KfCenterService;
import com.wap.util.DateUtils;
import com.wap.util.RemoteIpUtil;
import com.wx.util.WeixinUtil;

import core.db.dao.IBaseService;

@Controller
@RequestMapping(value={"/kfcenter"})
public class KfCenterController {
	
	private static final Log logger = LogFactory.getLog(KfCenterController.class);
	
	@Autowired(required = false)
	private IBaseService baseService = null;
	
	@Autowired(required = false)
	private KfCenterService kfCenterService = null;

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public void setKfCenterService(KfCenterService kfCenterService) {
		this.kfCenterService = kfCenterService;
	}
	
	/**
	 * 
	 * 描述:根据openid获取用户证件类型及证件号，并验证是否登录微信，
	 * 然后根据证件号获取保单列表信息
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @author 吕亮 2016年8月24日 下午1:38:05
	 */
	@RequestMapping(value={"/jcxActivePrepare.do"})
	public ModelAndView jcxActivePrepare(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		
		String userAgent = request.getHeader("User-Agent");
		if(StringUtils.isNotEmpty(userAgent)){
			if(userAgent.indexOf("MicroMessenger") == -1){
				System.out.println("userAgent not have MicroMessenger!");
				return new ModelAndView("/browser_err");
			}
		}
		ModelAndView modelView = new ModelAndView("/kf/active/jcxActive");
		Map map = null;
		List<PolicyEntity> policyList = null;
		PolicyQueryEntity policyQueryEntity = new PolicyQueryEntity();
		String res = "";
		String msg = "";
		
		String openid = request.getParameter("openId");
		logger.info("领取礼品页面跳转开始,用户openId==" + openid);
		//正式上线把此处注释掉
		//openid = "oXHv4jmszxDgkRolbiTzcA0lA8iA";
		modelView.addObject("openId", openid);
		Map<String, String> returnmap = new HashMap<String, String>();
		String retcode = "";
		String retmsg = "";
		String identifytype = "";
		String identifyno = "";
		try{
			//1.验证是否已经绑定并登录微信（调用总公司getusername接口）
			returnmap = WeixinUtil.getUserNameFormPICC(openid);
			if(returnmap != null){
				retcode = returnmap.get("retcode");
				retmsg = returnmap.get("retmsg");
				identifytype = returnmap.get("identifytype");
				identifyno = returnmap.get("identifyno");
				if("0".equals(retcode)){
					//2.取保单列表信息
					policyQueryEntity.setOpenid(openid);
                    //内部测试用
                    //policyQueryEntity.setIdentifyType("01");
                    //policyQueryEntity.setIdentifyNo("230223198301203034");
					policyQueryEntity.setIdentifyType(identifytype);
					policyQueryEntity.setIdentifyNo(identifyno);
					map = kfCenterService.getPolicyList(policyQueryEntity);
					if(map != null){
						res = (String)map.get("res");
						msg = (String)map.get("msg");
						if("1".equals(res)){
							policyList = (List<PolicyEntity>) map.get("policylist");
							if(policyList != null && policyList.size() > 0){
								modelView.addObject("policyList", policyList);
								modelView.addObject("res","1");
								modelView.addObject("msg","成功");
								//modelView.addObject("identifyType", "01");
								//modelView.addObject("identifyNumber", "230223198301203034");
					            modelView.addObject("identifyType", identifytype);
					            modelView.addObject("identifyNumber",identifyno);
								
							}else{
								modelView.addObject("res","0");
								modelView.addObject("msg","未查询到符合规则的保单信息");
							}
						}else{
							modelView.addObject("res","0");
							modelView.addObject("msg",msg);
						}
					}else{
						modelView.addObject("res","0");
						modelView.addObject("msg","未查询到符合规则的保单信息");
					}
				}else{
					modelView.addObject("res","2");
					modelView.addObject("msg",StringUtils.isEmpty(retmsg) ? "请关注[北京人保财险]公众号，绑定身份信息并登录" : retmsg);
				}
			}else{
				modelView.addObject("res","2");
				modelView.addObject("msg","请绑定身份信息并登录");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			modelView.addObject("res","0");
			modelView.addObject("msg","系统繁忙");
		}
		return modelView;
	}
	
	/**
	 * 描述:根据保单号及可兑换金额获取兑换券码及充值链接
	 * @param request
	 * @param response
	 * @author 吕亮 2016年8月24日 下午1:39:10
	 */
	@RequestMapping(value={"/jcxActive.do"})
	public void jcxActive(HttpServletRequest request,HttpServletResponse response){

		String openid = request.getParameter("openId");
		String policyno = request.getParameter("policyNo");
		String identifyType = request.getParameter("identifyType");
		String identifyNumber = request.getParameter("identifyNumber");
		int exchangeAmount = Integer.parseInt(request.getParameter("exchangeAmount"));
		int total = 0;
		
		logger.info("openId=" + openid + ";policyno=" + policyno + ";identifyType=" + identifyType + ";identifyNumber=" + identifyNumber + ";exchangeAmount=" + exchangeAmount);
		
		String exchangeNum1 = request.getParameter("exchangeNum1");
		String exchangeNum2 = request.getParameter("exchangeNum2");
		String exchangeNum5 = request.getParameter("exchangeNum5");
		String exchangeNum8 = request.getParameter("exchangeNum8");
		String exchangeNum10 = request.getParameter("exchangeNum10");
		
		String czNum1 = request.getParameter("czNum1");
		String czNum2 = request.getParameter("czNum2");
		String czNum3 = request.getParameter("czNum3");
		String czNum5 = request.getParameter("czNum5");
		String czNum10 = request.getParameter("czNum10");
		String czNum20 = request.getParameter("czNum20");
		
		logger.info("exchangeNum1=" + exchangeNum1 + ";exchangeNum2=" + exchangeNum2 + ";exchangeNum5=" + exchangeNum5 + ";exchangeNum8=" + exchangeNum8 + ";exchangeNum10=" + exchangeNum10);
		logger.info("czNum1=" + czNum1 + ";czNum2=" + czNum2 + ";czNum3=" + czNum3 + ";czNum5=" + czNum5 + ";czNum10=" + czNum10 + ";czNum20=" + czNum20);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		
		ExchangeResEntity exchangeResEntity = null;
		
		ExchangeReqEntity exchangeReqEntity = new ExchangeReqEntity();
		
		List<ExchangeReqListEntity> exchangeReqList = new ArrayList<ExchangeReqListEntity>();
		ExchangeReqListEntity exchangeReqListEntity = null;
		List<CzReqListEntity> czReqList = new ArrayList<CzReqListEntity>();
		CzReqListEntity czReqListEntity = null;
		Map map = null;
		String res = "";
		String msg = "";
		try {
			if(StringUtils.isNotEmpty(policyno)){
				out = response.getWriter();
				exchangeReqEntity.setOpenid(openid);
				exchangeReqEntity.setPolicyNo(policyno);
				exchangeReqEntity.setIdentifyType(identifyType);
				exchangeReqEntity.setIdentifyNumber(identifyNumber);
				if(StringUtils.isNotEmpty(exchangeNum1)){
					exchangeReqListEntity = new ExchangeReqListEntity();
					exchangeReqListEntity.setExchangePrice("100");
					exchangeReqListEntity.setExchangeNum(exchangeNum1);
					exchangeReqList.add(exchangeReqListEntity);
					total = total + Integer.parseInt(exchangeNum1)*100;
				}
				if(StringUtils.isNotEmpty(exchangeNum2)){
					exchangeReqListEntity = new ExchangeReqListEntity();
					exchangeReqListEntity.setExchangePrice("200");
					exchangeReqListEntity.setExchangeNum(exchangeNum2);
					exchangeReqList.add(exchangeReqListEntity);
					total = total + Integer.parseInt(exchangeNum2)*200;
				}
				if(StringUtils.isNotEmpty(exchangeNum5)){
					exchangeReqListEntity = new ExchangeReqListEntity();
					exchangeReqListEntity.setExchangePrice("500");
					exchangeReqListEntity.setExchangeNum(exchangeNum5);
					exchangeReqList.add(exchangeReqListEntity);
					total = total + Integer.parseInt(exchangeNum5)*500;
				}
				if(StringUtils.isNotEmpty(exchangeNum8)){
					exchangeReqListEntity = new ExchangeReqListEntity();
					exchangeReqListEntity.setExchangePrice("800");
					exchangeReqListEntity.setExchangeNum(exchangeNum8);
					exchangeReqList.add(exchangeReqListEntity);
					total = total + Integer.parseInt(exchangeNum8)*800;
				}
				if(StringUtils.isNotEmpty(exchangeNum10)){
					exchangeReqListEntity = new ExchangeReqListEntity();
					exchangeReqListEntity.setExchangePrice("1000");
					exchangeReqListEntity.setExchangeNum(exchangeNum10);
					exchangeReqList.add(exchangeReqListEntity);
					total = total + Integer.parseInt(exchangeNum10)*1000;
				}
				if(StringUtils.isNotEmpty(czNum1)){
					czReqListEntity = new CzReqListEntity();
					czReqListEntity.setCzPrice("10");
					czReqListEntity.setCzNum(czNum1);
					czReqList.add(czReqListEntity);
					total = total + Integer.parseInt(czNum1)*10;
				}
				if(StringUtils.isNotEmpty(czNum2)){
					czReqListEntity = new CzReqListEntity();
					czReqListEntity.setCzPrice("20");
					czReqListEntity.setCzNum(czNum2);
					czReqList.add(czReqListEntity);
					total = total + Integer.parseInt(czNum2)*20;
				}
				if(StringUtils.isNotEmpty(czNum3)){
					czReqListEntity = new CzReqListEntity();
					czReqListEntity.setCzPrice("30");
					czReqListEntity.setCzNum(czNum3);
					czReqList.add(czReqListEntity);
					total = total + Integer.parseInt(czNum3)*30;
				}
				if(StringUtils.isNotEmpty(czNum5)){
					czReqListEntity = new CzReqListEntity();
					czReqListEntity.setCzPrice("50");
					czReqListEntity.setCzNum(czNum5);
					czReqList.add(czReqListEntity);
					total = total + Integer.parseInt(czNum5)*50;
				}
				if(StringUtils.isNotEmpty(czNum10)){
					czReqListEntity = new CzReqListEntity();
					czReqListEntity.setCzPrice("100");
					czReqListEntity.setCzNum(czNum10);
					czReqList.add(czReqListEntity);
					total = total + Integer.parseInt(czNum10)*100;
				}
				if(StringUtils.isNotEmpty(czNum20)){
					czReqListEntity = new CzReqListEntity();
					czReqListEntity.setCzPrice("200");
					czReqListEntity.setCzNum(czNum20);
					czReqList.add(czReqListEntity);
					total = total + Integer.parseInt(czNum20)*200;
				}
				logger.info("kfcentercontroller可兑换金额：" + exchangeAmount + ",页面提交到后台总的兑换金额：" + total);
				if(total != exchangeAmount){
					json.element("res", "0");
					json.element("msg", "提交的金额与可兑换金额不符！");
				}else{
					exchangeReqEntity.setExchangeReqList(exchangeReqList);
					exchangeReqEntity.setCzReqList(czReqList);
					map = kfCenterService.getExchangeList(exchangeReqEntity);
					if(map != null){
						res = (String)map.get("res");
						msg = (String)map.get("msg");
						if("1".equals(res)){
							//exchangeResEntity = (ExchangeResEntity)map.get("exchangeinfo");
							if(exchangeResEntity != null){
								json = JSONObject.fromObject(exchangeResEntity);
								json.element("res", "1");
								json.element("msg", "领取成功");
							}else{
								json.element("res", "0");
								json.element("msg", "获取兑换券失败");
							}
						}else{
							json.element("res", "0");
							json.element("msg", msg);
						}
					}else{
						json.element("res", "0");
						json.element("msg", "礼品领取失败");
					}
				}
			}else{
				json.element("res", "0");
				json.element("msg", "保单参数错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.element("res", "0");
			json.element("msg", "系统繁忙");
		} finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 描述:根据报案号查询已领取的兑换券及充值链接
	 * @param request
	 * @param response
	 * @author 吕亮 2016年8月24日 下午1:41:13
	 */
	@RequestMapping(value={"/getExchangeList.do"})
	public void getExchangeList(HttpServletRequest request,HttpServletResponse response){
		
		String policyno = request.getParameter("policyNo");
		logger.info("policyNo=" + policyno);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		ExchangeQueryResEntity exchangeQueryResEntity = null;
		ExchangeQueryReqEntity exchangeQueryReqEntity = null;
		Map map = null;
		String res = "";
		String msg = "";
		try {
			out = response.getWriter();
			if(StringUtils.isEmpty(policyno)){
				json.element("res", "0");
				json.element("msg", "保单号不能为空！");
			}else{
				exchangeQueryReqEntity = new ExchangeQueryReqEntity();
				exchangeQueryReqEntity.setPolicyNo(policyno);
				map = kfCenterService.getExchangeQryList(exchangeQueryReqEntity);
				if(map != null){
					res = (String)map.get("res");
					msg = (String)map.get("msg");
					if("1".equals(res)){
						//exchangeQueryResEntity = (ExchangeQueryResEntity)map.get("exchangelistinfo");
						if(exchangeQueryResEntity != null){
							json = JSONObject.fromObject(exchangeQueryResEntity);
							json.element("res", "1");
							json.element("msg", "成功");
						}else{
							json.element("res", "0");
							json.element("msg", "无记录");
						}
					}else{
						json.element("res", "0");
						json.element("msg", msg);
					}
				}else{
					json.element("res", "0");
					json.element("msg", "兑换券查询失败！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.element("res", "0");
			json.element("msg", "系统繁忙");
		} finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 
			* 描述:活动首页
			* @param request
			* @param response
			* @param session
			* @return
			* @author 朱久满 2016年10月31日 下午5:44:42
	 */
	@RequestMapping(value={"/activePrepare.do"})
	public ModelAndView activePrepare(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		
		ModelAndView modelView = new ModelAndView("/kf/active/activeIndex");
		ModelAndView modelView1 = new ModelAndView("/browser_err");
		String openid = request.getParameter("openId");
		logger.info("------绑定有礼开始,用户openId==" + openid);
		modelView.addObject("openId", openid);
		String userAgent = request.getHeader("User-Agent");
		if(StringUtils.isNotEmpty(userAgent)){
			if(userAgent.indexOf("MicroMessenger") == -1){
				System.out.println("userAgent not have MicroMessenger!");
				return modelView1;
			}
		}
		return modelView;
	}
	
	/**
	 * 
	 * 描述:根据openid获取用户证件类型及证件号，并验证是否登录微信，
	 * 然后根据证件号获取保单列表信息
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @author 吕亮 2016年8月24日 下午1:38:05
	 */
	@RequestMapping(value={"/boundGetGiftPrepare.do"})
	public ModelAndView boundGetGiftPrepare(HttpServletRequest request,HttpServletResponse response, HttpSession session){

		System.out.println("remote client ip========" + RemoteIpUtil.getClientIp(request) + "-----------");
		ModelAndView modelView = new ModelAndView("/kf/active/boundGetGiftActive");
		ModelAndView modelView1 = new ModelAndView("/browser_err");
		String userAgent = request.getHeader("User-Agent");
		if(StringUtils.isNotEmpty(userAgent)){
			if(userAgent.indexOf("MicroMessenger") == -1){
				System.out.println("userAgent not have MicroMessenger!");
				return modelView1;
			}
		}
		Map map = null;
		Map mapNum = null;
		String res = "";
		String msg = "";
		
		String openid = request.getParameter("openId");
		logger.info("绑定有礼开始,用户openId==" + openid);
		//正式上线把此处注释掉
		//openid = "oXHv4jmszxDgkRolbiTzcA0lA8iA";
		modelView.addObject("openId", openid);
		
		Map<String, String> returnmap = new HashMap<String, String>();
		String retcode = "";
		String retmsg = "";
		String identifytype = "";
		String identifyno = "";
		
		ActiveReceiveNumQueryEntity receiveNumQueryEntity = null;
		ActiveReceiveNumEntity receiveNumEntity = null;
		String receiveNumStr = "";
		int receiveNum = 0;
		String resReceive = "";
		
		ExchangeReqEntity exchangeReqEntity = null;
		List<CzReqListEntity> czReqList = null;
		CzReqListEntity czReqListEntity = null;
		ExchangeResEntity exchangeResEntity = null;
		List<CzResListEntity> czResList = null;
		CzResListEntity czResListEntity = null;
		String czhref = "";
		
		String activeNumStr =SysDicHelper.getInstance().getValueByDicTypeAndDicId("KF_ACTIVE_END_NUM", "01").trim();
		int activeNum = Integer.parseInt(activeNumStr);
		try{
			if(StringUtils.isNotEmpty(openid)){
				//获取当前已领取人数
				receiveNumQueryEntity = new ActiveReceiveNumQueryEntity();
				receiveNumQueryEntity.setOpenId(openid);
				mapNum = kfCenterService.getActiveReceiveNum(receiveNumQueryEntity);
				if(mapNum != null){
					resReceive = (String)mapNum.get("res");
					if("1".equals(resReceive)){
						receiveNumEntity = (ActiveReceiveNumEntity)mapNum.get("receiveNumEntity");
						if(receiveNumEntity != null){
							System.out.println("controller receiveNumEntity not null");
							receiveNumStr = receiveNumEntity.getReceivenum();
							if(StringUtils.isNotEmpty(receiveNumStr)){
								receiveNum = Integer.parseInt(receiveNumStr);
							}
						}else{
							System.out.println("controller receiveNumEntity is null");
						}
					}
				}
				//1.验证是否已经绑定并登录微信（调用总公司getusername接口）
				returnmap = WeixinUtil.getUserNameFormPICC(openid);
				if(returnmap != null){
					retcode = returnmap.get("retcode");
					retmsg = returnmap.get("retmsg");
					identifytype = returnmap.get("identifytype");
					identifyno = returnmap.get("identifyno");
					if("0".equals(retcode)){
						if(StringUtils.isNotEmpty(identifytype) && StringUtils.isNotEmpty(identifyno)){
							modelView.addObject("getflag", "c");
							//2.获取充值卡链接，并返回到页面
							exchangeReqEntity = new ExchangeReqEntity();
							exchangeReqEntity.setOpenid(openid);
							exchangeReqEntity.setIdentifyType(identifytype);
							exchangeReqEntity.setIdentifyNumber(identifyno);
							exchangeReqEntity.setOperateType("qry");
							czReqList = new ArrayList<CzReqListEntity>();
							czReqListEntity = new CzReqListEntity();
							czReqListEntity.setCzPrice("5");
							czReqListEntity.setCzNum("1");
							czReqList.add(czReqListEntity);
							exchangeReqEntity.setCzReqList(czReqList);
							//取码接口
							map = kfCenterService.getBoundCzcodeList(exchangeReqEntity);
							if(map != null){
								res = (String)map.get("res");
								msg = (String)map.get("msg");
								if("1".equals(res)){
									//获取兑换码
									exchangeResEntity = (ExchangeResEntity)map.get("exchangeinfo");
									if(exchangeResEntity != null){
										czResList = exchangeResEntity.getCzResList();
										if(czResList != null && czResList.size() > 0){
											czResListEntity = czResList.get(0);
											if(czResListEntity != null){
												czhref = czResListEntity.getCzhref();
												if(StringUtils.isNotEmpty(czhref)){
													modelView.addObject("res","1");
													modelView.addObject("msg","成功");
													modelView.addObject("czhref",czhref);
												}else{
													modelView.addObject("res","4");
													modelView.addObject("msg","未获取到充值卡信息");
												}
											}else{
												modelView.addObject("res","4");
												modelView.addObject("msg","未获取到充值卡信息");
											}
										}else{
											modelView.addObject("res","4");
											modelView.addObject("msg","未获取到充值卡信息");
										}
									}else{
										modelView.addObject("res","4");
										modelView.addObject("msg","未获取到充值卡信息");
									}
								}else{
									if(StringUtils.isNotEmpty(msg)){
										if(("请单击我要领取进行礼品领取").equals(msg)){
											modelView.addObject("res","4");
											modelView.addObject("msg",msg);
										}else if(("本次活动已结束").equals(msg)){
											modelView.addObject("res","5");
											modelView.addObject("msg",msg);
										}else{
											modelView.addObject("res","0");
											modelView.addObject("msg",msg);
										}
									}else{
										modelView.addObject("res","0");
										modelView.addObject("msg",msg);
									}
								}
							}else{
								modelView.addObject("res","0");
								modelView.addObject("msg","查询已领取充值卡信息失败");
							}
						}else{
							modelView.addObject("res","3");
							modelView.addObject("msg","请到个人中心完善个人资料");
						}
					}else{
						modelView.addObject("res","2");
						modelView.addObject("msg",StringUtils.isEmpty(retmsg) ? "请关注[北京人保财险]公众号，绑定身份信息并登录" : retmsg);
					}
				}else{
					modelView.addObject("res","2");
					modelView.addObject("msg","请绑定证件信息并登录");
				}
			}else{
				return modelView1;
			}
		}catch(Exception e){
			e.printStackTrace();
			modelView.addObject("res","0");
			modelView.addObject("msg","系统繁忙");
		}
		if(activeNum > receiveNum){
			//当前剩余礼品数量
			//modelView.addObject("receiveNum",(activeNum-receiveNum));
			if(!"2016-12-27".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) && !"2016-12-28".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) && !"2016-12-29".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) && !"2016-12-30".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) && !"2016-12-31".equals(DateUtils.format(new Date(), "yyyy-MM-dd"))){
				modelView.addObject("active_end","本次活动已结束！");
			}
		}else{
			//modelView.addObject("receiveNum",0);
			modelView.addObject("active_end","本次活动已结束！");
		}
		return modelView;
	}
	
	/**
	 * 
	 * 描述:根据openid获取用户证件类型及证件号，并验证是否登录微信，
	 * 然后根据证件号获取保单列表信息
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @author 吕亮 2016年8月24日 下午1:38:05
	 */
	@RequestMapping(value={"/boundGetGift_20161226old.do"})
	public void boundGetGift_20161226old(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		
		JSONObject json = new JSONObject();

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		PrintWriter out = null;
		
		String userAgent = request.getHeader("User-Agent");
		String openid = request.getParameter("openId");
		System.out.println("绑定有礼开始openid=" + openid);
		json.element("openId", openid);
		
		Map<String, String> returnmap = new HashMap<String, String>();
		String retcode = "";
		String retmsg = "";
		String identifytype = "";
		String identifyno = "";
		
		Map map = null;
		Map mapNum = null;
		String res = "";
		String msg = "";
		
		ActiveReceiveNumQueryEntity receiveNumQueryEntity = null;
		ActiveReceiveNumEntity receiveNumEntity = null;
		String receiveNumStr = "";
		int receiveNum = 0;
		String resReceive = "";
		
		ExchangeReqEntity exchangeReqEntity = null;
		List<CzReqListEntity> czReqList = null;
		CzReqListEntity czReqListEntity = null;
		ExchangeResEntity exchangeResEntity = null;
		List<CzResListEntity> czResList = null;
		CzResListEntity czResListEntity = null;
		
		String activeNumStr =SysDicHelper.getInstance().getValueByDicTypeAndDicId("KF_ACTIVE_END_NUM", "01").trim();
		int activeNum = Integer.parseInt(activeNumStr);
		try{
			out = response.getWriter();
			if(StringUtils.isNotEmpty(userAgent)){
				if(userAgent.indexOf("MicroMessenger") == -1){
					System.out.println("userAgent not have MicroMessenger!");
					json.element("code","0");
					json.element("info","请在微信客户端打开链接！");
				}else{
					//if(StringUtils.isNotEmpty(openid) && StringUtils.isNotEmpty(phoneNumber)){
					if(StringUtils.isNotEmpty(openid)){
						//查询已领取数量请求报文参数
						receiveNumQueryEntity = new ActiveReceiveNumQueryEntity();
						receiveNumQueryEntity.setOpenId(openid);
						
						returnmap = WeixinUtil.getUserNameFormPICC(openid);
						if(returnmap != null){
							retcode = returnmap.get("retcode");
							retmsg = returnmap.get("retmsg");
							identifytype = returnmap.get("identifytype");
							identifyno = returnmap.get("identifyno");
							if("0".equals(retcode)){
								if(StringUtils.isNotEmpty(identifytype) && StringUtils.isNotEmpty(identifyno)){
									//1.验证为已绑定并登录用户，获取当前已领取人数
									mapNum = kfCenterService.getActiveReceiveNum(receiveNumQueryEntity);
									if(mapNum != null){
										resReceive = (String)mapNum.get("res");
										if("1".equals(resReceive)){
											receiveNumEntity = (ActiveReceiveNumEntity)mapNum.get("receiveNumEntity");
											if(receiveNumEntity != null){
												receiveNumStr = receiveNumEntity.getReceivenum();
												if(StringUtils.isNotEmpty(receiveNumStr)){
													receiveNum = Integer.parseInt(receiveNumStr);
												}
											}else{
												System.out.println("controller receiveNumEntity is null");
											}
										}
									}
									if(activeNum > receiveNum){
										if("2016-12-23".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) || "2016-12-24".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) || "2016-12-25".equals(DateUtils.format(new Date(), "yyyy-MM-dd"))){
											//2.获取充值卡链接，并返回到页面
											exchangeReqEntity = new ExchangeReqEntity();
											exchangeReqEntity.setOpenid(openid);
											exchangeReqEntity.setIdentifyType(identifytype);
											exchangeReqEntity.setIdentifyNumber(identifyno);
											exchangeReqEntity.setPhoneNumber("");
											exchangeReqEntity.setOperateType("get");
											czReqList = new ArrayList<CzReqListEntity>();
											czReqListEntity = new CzReqListEntity();
											czReqListEntity.setCzPrice("5");
											czReqListEntity.setCzNum("1");
											czReqList.add(czReqListEntity);
											exchangeReqEntity.setCzReqList(czReqList);
											//取码接口
											map = kfCenterService.getBoundCzcodeList(exchangeReqEntity);
											if(map != null){
												res = (String)map.get("res");
												msg = (String)map.get("msg");
												if("1".equals(res)){
													//获取兑换码
													exchangeResEntity = (ExchangeResEntity)map.get("exchangeinfo");
													if(exchangeResEntity != null){
														czResList = exchangeResEntity.getCzResList();
														if(czResList != null && czResList.size() > 0){
															czResListEntity = czResList.get(0);
															if(czResListEntity != null){
																json.element("code","1");
																json.element("info","礼品领取成功！");
															}else{
																json.element("code","0");
																json.element("info", "未获取到充值卡信息！");
															}
														}else{
															json.element("code","0");
															json.element("info", "未获取到充值卡信息！");
														}
													}else{
														json.element("code","0");
														json.element("info", "未获取到充值卡信息！");
													}
												}else{
													json.element("code","0");
													json.element("info",msg);
												}
											}else{
												json.element("code","0");
												json.element("info","领取礼品失败！");
											}
										}else{
											json.element("code","0");
											json.element("info","本次活动已结束");
										}
									}else{
										json.element("code","0");
										json.element("info","本次活动已结束");
									}
									
								}else{
									json.element("code","0");
									json.element("info","请到个人中心完善个人资料！");
								}
							}else{
								json.element("code","0");
								json.element("info", StringUtils.isEmpty(retmsg) ? "请关注[北京人保财险]公众号，绑定身份信息并登录！" : retmsg);
							}
						}else{
							json.element("code","0");
							json.element("info","请绑定证件信息并登录！");
						}
						//获取当前最新的礼品剩余数量
						mapNum = kfCenterService.getActiveReceiveNum(receiveNumQueryEntity);
						if(mapNum != null){
							resReceive = (String)mapNum.get("res");
							if("1".equals(resReceive)){
								receiveNumEntity = (ActiveReceiveNumEntity)mapNum.get("receiveNumEntity");
								if(receiveNumEntity != null){
									receiveNumStr = receiveNumEntity.getReceivenum();
									if(StringUtils.isNotEmpty(receiveNumStr)){
										receiveNum = Integer.parseInt(receiveNumStr);
									}
								}else{
									System.out.println("controller receiveNumEntity is null");
								}
							}
						}
						if(activeNum > receiveNum){
							//当前已领取礼品人数
							if(!"2016-12-23".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) && !"2016-12-24".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) && !"2016-12-25".equals(DateUtils.format(new Date(), "yyyy-MM-dd"))){
								json.element("active_end","本次活动已结束！");
							}
							//json.element("receiveNum",(activeNum-receiveNum));
						}else{
							//json.element("receiveNum",0);
							json.element("active_end","本次活动已结束！");
						}
					}else{
						json.element("code","0");
						json.element("info", "请求信息错误！");
					}
				}
			}else{
				json.element("code","0");
				json.element("info", "请在微信客户端打开链接！");
			}
		}catch(Exception e){
			e.printStackTrace();
			json.element("code","0");
			json.element("info", "系统繁忙！");
		}finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 
	 * 描述:根据openid获取用户证件类型及证件号，并验证是否登录微信，
	 * 然后根据证件号获取保单列表信息
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @author 吕亮 2016年8月24日 下午1:38:05
	 */
	@RequestMapping(value={"/boundGetGift.do"})
	public void boundGetGift(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar ca = Calendar.getInstance();
		String sid1 = session.getId();
		System.out.println("sid1=" + sid1);
		
		JSONObject json = new JSONObject();

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		PrintWriter out = null;
		
		String userAgent = request.getHeader("User-Agent");
		String openid = request.getParameter("openId");
		String phoneNumber = request.getParameter("phoneNumber");
		String rq1 = (String)session.getAttribute(phoneNumber);
		System.out.println("手机号=" + phoneNumber + "第一次访问时间=" + rq1);
		
		if(StringUtils.isNotEmpty(rq1)){
			try {
				ca.setTime(sdf.parse(rq1));
				String d1 = sdf.format(ca.getTime());
				System.out.println(d1);
				
				ca.add(Calendar.SECOND, 10);
				String d2 = sdf.format(ca.getTime());
				System.out.println("手机号" + phoneNumber + "第二次访问时间=" + d2);
				
				int r = DateUtils.compareDate(d2,sdf.format(new Date()),"yyyy-MM-dd HH:mm:ss");
				//同一手机号第一次访问时间+10s，如果大于当前时间，说明访问的频率太高了，直接返回
				if(r == -1){
					System.out.println("非法访问，直接返回！");
				}else{
					session.setAttribute(phoneNumber, sdf.format(new Date()));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("手机号=" + phoneNumber + "第一次访问时间set=" + sdf.format(new Date()));
			session.setAttribute(phoneNumber, sdf.format(new Date()));
		}
		
		
		logger.info("绑定有礼开始,用户openId==" + openid + "提交的手机号=" + phoneNumber);
		json.element("openId", openid);
		
		Map<String, String> returnmap = new HashMap<String, String>();
		String retcode = "";
		String retmsg = "";
		String identifytype = "";
		String identifyno = "";
		
		Map map = null;
		Map mapNum = null;
		String res = "";
		String msg = "";
		
		ActiveReceiveNumQueryEntity receiveNumQueryEntity = null;
		ActiveReceiveNumEntity receiveNumEntity = null;
		String receiveNumStr = "";
		int receiveNum = 0;
		String resReceive = "";
		
		ExchangeReqEntity exchangeReqEntity = null;
		List<CzReqListEntity> czReqList = null;
		CzReqListEntity czReqListEntity = null;
		ExchangeResEntity exchangeResEntity = null;
		List<CzResListEntity> czResList = null;
		CzResListEntity czResListEntity = null;
		
		String activeNumStr =SysDicHelper.getInstance().getValueByDicTypeAndDicId("KF_ACTIVE_END_NUM", "01").trim();
		int activeNum = Integer.parseInt(activeNumStr);
		try{
			out = response.getWriter();
			if(StringUtils.isNotEmpty(userAgent)){
				if(userAgent.indexOf("MicroMessenger") == -1){
					System.out.println("userAgent not have MicroMessenger!");
					json.element("code","0");
					json.element("info","请在微信客户端打开链接！");
				}else{
					if(StringUtils.isNotEmpty(openid) && StringUtils.isNotEmpty(phoneNumber)){
						//查询已领取数量请求报文参数
						receiveNumQueryEntity = new ActiveReceiveNumQueryEntity();
						receiveNumQueryEntity.setOpenId(openid);
						
						returnmap = WeixinUtil.getUserNameFormPICC(openid);
						if(returnmap != null){
							retcode = returnmap.get("retcode");
							retmsg = returnmap.get("retmsg");
							identifytype = returnmap.get("identifytype");
							identifyno = returnmap.get("identifyno");
							if("0".equals(retcode)){
								if(StringUtils.isNotEmpty(identifytype) && StringUtils.isNotEmpty(identifyno)){
									//1.验证为已绑定并登录用户，获取当前已领取人数
									mapNum = kfCenterService.getActiveReceiveNum(receiveNumQueryEntity);
									if(mapNum != null){
										resReceive = (String)mapNum.get("res");
										if("1".equals(resReceive)){
											receiveNumEntity = (ActiveReceiveNumEntity)mapNum.get("receiveNumEntity");
											if(receiveNumEntity != null){
												receiveNumStr = receiveNumEntity.getReceivenum();
												if(StringUtils.isNotEmpty(receiveNumStr)){
													receiveNum = Integer.parseInt(receiveNumStr);
												}
											}else{
												System.out.println("controller receiveNumEntity is null");
											}
										}
									}
									if(activeNum > receiveNum){
										if("2016-12-27".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) || "2016-12-28".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) || "2016-12-29".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) || "2016-12-30".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) || "2016-12-31".equals(DateUtils.format(new Date(), "yyyy-MM-dd"))){
											//2.获取充值卡链接，并返回到页面
											exchangeReqEntity = new ExchangeReqEntity();
											exchangeReqEntity.setOpenid(openid);
											exchangeReqEntity.setIdentifyType(identifytype);
											exchangeReqEntity.setIdentifyNumber(identifyno);
											exchangeReqEntity.setPhoneNumber(phoneNumber);
											exchangeReqEntity.setOperateType("get");
											czReqList = new ArrayList<CzReqListEntity>();
											czReqListEntity = new CzReqListEntity();
											czReqListEntity.setCzPrice("5");
											czReqListEntity.setCzNum("1");
											czReqList.add(czReqListEntity);
											exchangeReqEntity.setCzReqList(czReqList);
											//取码接口
											map = kfCenterService.getBoundCzcodeList(exchangeReqEntity);
											if(map != null){
												res = (String)map.get("res");
												msg = (String)map.get("msg");
												if("1".equals(res)){
													//获取兑换码
													exchangeResEntity = (ExchangeResEntity)map.get("exchangeinfo");
													if(exchangeResEntity != null){
														czResList = exchangeResEntity.getCzResList();
														if(czResList != null && czResList.size() > 0){
															czResListEntity = czResList.get(0);
															if(czResListEntity != null){
																json.element("code","1");
																json.element("info","礼品领取成功！");
															}else{
																json.element("code","0");
																json.element("info", "未获取到充值卡信息！");
															}
														}else{
															json.element("code","0");
															json.element("info", "未获取到充值卡信息！");
														}
													}else{
														json.element("code","0");
														json.element("info", "未获取到充值卡信息！");
													}
												}else{
													json.element("code","0");
													json.element("info",msg);
												}
											}else{
												json.element("code","0");
												json.element("info","领取礼品失败！");
											}
										}else{
											json.element("code","0");
											json.element("info","本次活动已结束");
										}
									}else{
										json.element("code","0");
										json.element("info","本次活动已结束");
									}
									
								}else{
									json.element("code","0");
									json.element("info","请到个人中心完善个人资料！");
								}
							}else{
								json.element("code","0");
								json.element("info", StringUtils.isEmpty(retmsg) ? "请关注[北京人保财险]公众号，绑定身份信息并登录！" : retmsg);
							}
						}else{
							json.element("code","0");
							json.element("info","请绑定证件信息并登录！");
						}
						//获取当前最新的礼品剩余数量
						mapNum = kfCenterService.getActiveReceiveNum(receiveNumQueryEntity);
						if(mapNum != null){
							resReceive = (String)mapNum.get("res");
							if("1".equals(resReceive)){
								receiveNumEntity = (ActiveReceiveNumEntity)mapNum.get("receiveNumEntity");
								if(receiveNumEntity != null){
									receiveNumStr = receiveNumEntity.getReceivenum();
									if(StringUtils.isNotEmpty(receiveNumStr)){
										receiveNum = Integer.parseInt(receiveNumStr);
									}
								}else{
									System.out.println("controller receiveNumEntity is null");
								}
							}
						}
						if(activeNum > receiveNum){
							//当前已领取礼品人数
							if(!"2016-12-27".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) && !"2016-12-28".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) && !"2016-12-29".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) && !"2016-12-30".equals(DateUtils.format(new Date(), "yyyy-MM-dd")) && !"2016-12-31".equals(DateUtils.format(new Date(), "yyyy-MM-dd"))){
								json.element("active_end","本次活动已结束！");
							}
							//json.element("receiveNum",(activeNum-receiveNum));
						}else{
							//json.element("receiveNum",0);
							json.element("active_end","本次活动已结束！");
						}
					}else{
						json.element("code","0");
						json.element("info", "请求信息错误！");
					}
				}
			}else{
				json.element("code","0");
				json.element("info", "请在微信客户端打开链接！");
			}
		}catch(Exception e){
			e.printStackTrace();
			json.element("code","0");
			json.element("info", "系统繁忙！");
		}finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 
	 * 描述:根据openid获取用户证件类型及证件号，并验证是否登录微信，
	 * 然后根据证件号获取保单列表信息
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @author 吕亮 2016年8月24日 下午1:38:05
	 */
	@RequestMapping(value={"/boundGetGiftCz.do"})
	public void boundGetGiftCz(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		JSONObject json = new JSONObject();
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		PrintWriter out = null;
		
		String userAgent = request.getHeader("User-Agent");
		String openid = request.getParameter("openId");
		logger.info("获取充值链接,用户openId==" + openid);
		json.element("openId", openid);
		
		
		Map map = null;
		String res = "";
		String msg = "";
		
		Map<String, String> returnmap = new HashMap<String, String>();
		String retcode = "";
		String retmsg = "";
		String identifytype = "";
		String identifyno = "";
		
		ExchangeReqEntity exchangeReqEntity = null;
		List<CzReqListEntity> czReqList = null;
		CzReqListEntity czReqListEntity = null;
		ExchangeResEntity exchangeResEntity = null;
		List<CzResListEntity> czResList = null;
		CzResListEntity czResListEntity = null;
		String czhref = "";
		
		try{
			out = response.getWriter();
			if(StringUtils.isNotEmpty(userAgent)){
				if(userAgent.indexOf("MicroMessenger") == -1){
					System.out.println("userAgent not have MicroMessenger!");
					json.element("code","0");
					json.element("info","请在微信客户端打开链接！");
				}else{
					if(StringUtils.isNotEmpty(openid)){
						//1.验证是否已经绑定并登录微信（调用总公司getusername接口）
						returnmap = WeixinUtil.getUserNameFormPICC(openid);
						if(returnmap != null){
							retcode = returnmap.get("retcode");
							retmsg = returnmap.get("retmsg");
							identifytype = returnmap.get("identifytype");
							identifyno = returnmap.get("identifyno");
							if("0".equals(retcode)){
								if(StringUtils.isNotEmpty(identifytype) && StringUtils.isNotEmpty(identifyno)){
									//2.获取充值卡链接，并返回到页面
									exchangeReqEntity = new ExchangeReqEntity();
									exchangeReqEntity.setOpenid(openid);
									exchangeReqEntity.setIdentifyType(identifytype);
									exchangeReqEntity.setIdentifyNumber(identifyno);
									exchangeReqEntity.setOperateType("get");
									
									czReqList = new ArrayList<CzReqListEntity>();
									czReqListEntity = new CzReqListEntity();
									
									czReqListEntity.setCzPrice("5");
									czReqListEntity.setCzNum("1");
									czReqList.add(czReqListEntity);
									
									exchangeReqEntity.setCzReqList(czReqList);
									//取码接口
									map = kfCenterService.getBoundCzcodeList(exchangeReqEntity);
									if(map != null){
										res = (String)map.get("res");
										msg = (String)map.get("msg");
										if("1".equals(res)){
											//获取兑换码
											exchangeResEntity = (ExchangeResEntity)map.get("exchangeinfo");
											if(exchangeResEntity != null){
												czResList = exchangeResEntity.getCzResList();
												if(czResList != null && czResList.size() > 0){
													czResListEntity = czResList.get(0);
													if(czResListEntity != null){
														czhref = czResListEntity.getCzhref();
														if(StringUtils.isNotEmpty(czhref)){
															json.element("qrycode","1");
															json.element("qryinfo","成功");
															json.element("qryczhref",czhref);
														}else{
															json.element("qrycode","0");
															json.element("qryinfo","未获取到充值卡信息");
														}
													}else{
														json.element("qrycode","0");
														json.element("qryinfo","未获取到充值卡信息");
													}
												}else{
													json.element("qrycode","0");
													json.element("qryinfo","未获取到充值卡信息");
												}
											}else{
												json.element("qrycode","0");
												json.element("qryinfo","未获取到充值卡信息");
											}
										}else{
											json.element("qrycode","0");
											json.element("qryinfo",msg);
										}
									}else{
										json.element("qrycode","0");
										json.element("qryinfo","领取礼品失败！");
									}
								}else{
									json.element("qrycode","0");
									json.element("qryinfo","请到个人中心完善个人资料！");
								}
							}else{
								json.element("qrycode","0");
								json.element("qryinfo", StringUtils.isEmpty(retmsg) ? "请关注[北京人保财险]公众号，绑定身份信息并登录！" : retmsg);
							}
						}else{
							json.element("qrycode","0");
							json.element("qryinfo","请绑定证件信息并登录");
						}
					}else{
						json.element("qrycode","0");
						json.element("qryinfo", "请在微信客户端打开链接！");
					}
				}
			}else{
				json.element("qrycode","0");
				json.element("qryinfo", "请在微信客户端打开链接！");
			}
		}catch(Exception e){
			e.printStackTrace();
			json.element("qrycode","0");
			json.element("qryinfo", "系统繁忙！");
		}finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}
	
	@RequestMapping(value={"/testWx_old20161205.do"})
	public ModelAndView testWx_old20161205(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		
		ModelAndView modelView = new ModelAndView("/kf/active/testWx");
		
        String userAgent = request.getHeader("User-Agent");
        System.out.println("useragent==" + userAgent + "==================");
		if(StringUtils.isNotEmpty(userAgent)){
			if(userAgent.indexOf("MicroMessenger") != -1){
				modelView.addObject("1","微信浏览器");
				System.out.println("userAgent with MicroMessenger!");
			}else{
				modelView.addObject("0", "只能在微信浏览器中打开领取");
				System.out.println("userAgent not with MicroMessenger!");
				return new ModelAndView("/browser_err");
			}
		}else{
			modelView.addObject("0", "只能在微信浏览器中打开领取");
			
			System.out.println("userAgent is null!");
		}
		return modelView;
		
	}
}
