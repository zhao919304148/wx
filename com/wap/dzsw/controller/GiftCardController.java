package com.wap.dzsw.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.hql.ast.tree.IsNotNullLogicOperatorNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sys.dic.SysDicHelper;
import com.sys.dic.entity.BrandEntity;
import com.sys.exception.EpiccException;
import com.sys.redis.RedisSessionService;
import com.sys.service.GetBussinessNoService;
import com.wap.auth.provider.impl.OpenIdAuthProvider;
import com.wap.dzsw.entity.ActivityCzj2017Gift;
import com.wap.dzsw.entity.BaoYangCouponConsume;
import com.wap.dzsw.entity.BaoYangCouponRecord;
import com.wap.dzsw.entity.CancelOrder;
import com.wap.dzsw.entity.CarDataEntity;
import com.wap.dzsw.entity.CarPhotoEntity;
import com.wap.dzsw.entity.DrivingInfoVo;
import com.wap.dzsw.entity.FriendGetGiftVo;
import com.wap.dzsw.entity.FriendRecommendVo;
import com.wap.dzsw.entity.GiftCardEntity;
import com.wap.dzsw.entity.GiftCardQueryEntity;
import com.wap.dzsw.entity.GiftConsignVo;
import com.wap.dzsw.entity.IdentifyDateEntity;
import com.wap.dzsw.entity.NetworkVo;
import com.wap.dzsw.entity.Page;
import com.wap.dzsw.entity.PhoneModifyEntity;
import com.wap.dzsw.entity.RbOrder;
import com.wap.dzsw.entity.SaveYuYueData;
import com.wap.dzsw.entity.ServiceReOrInquiryEntity;
import com.wap.dzsw.entity.Verification;
import com.wap.dzsw.service.FuWuService;
import com.wap.dzsw.service.GiftCardService;
import com.wap.trans.entity.tr_1021.ResTrans1021Score;
import com.wap.trans.entity.tr_1024.ResTrans1024BasePartEntity;
import com.wap.trans.entity.tr_1061.ResTrans1061CardDataEntity;
import com.wap.util.AESCode;
import com.wap.util.CommonUtils;
import com.wap.util.ConstantUtils;
import com.wap.util.DateUtils;
import com.wap.util.HttpUtil;
import com.wap.util.RSACode;
import com.wap.util.RandomUtil;
import com.wap.util.TransUtil;
import com.wap.wx_interface.controller.WxController;
import com.wx.util.ConfigUtil;
import com.wx.util.Md5Util;
import com.wx.util.WeixinUtil;

@Controller
@RequestMapping(value={"/giftCard"})
public class GiftCardController {
	private static final Log logger = LogFactory.getLog(GiftCardController.class);
	@Autowired
	private GiftCardService giftCardService;
	@Autowired
	private RedisSessionService sessionService;
	@Autowired
	private FuWuService fuWuService;
	@Autowired(required = true)
	private CacheManager cacheManager;
	/**
	 * 我的电商福袋礼品列表
	 * 描述:
	 * @param req
	 * @param GiftCardQueryEntity
	 * @param model
	 * @return
	 * @author QEX2016年11月22日 上午10:00:54
	 */
	@ResponseBody
	@RequestMapping(value = {"giftCardList.do"})
	public JSONObject giftCardList(HttpServletRequest request, GiftCardQueryEntity cardQueryEntity){
		logger.info("giftCcardList请求参数："+cardQueryEntity.toString());
		JSONObject jsonObject = new JSONObject();
		try {
			String openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
			cardQueryEntity.setOpenId(openId);
			logger.info("openId:"+openId);
			jsonObject.put("openId", openId);
			//拦截
			JSONObject checkwx = checkwx(request,openId);
			System.out.println("checkwx:"+checkwx);
			String cord = (String) checkwx.get("resCode");
			if(!"0".equals(cord)){
				return checkwx;
			}
			String identifytype = (String) checkwx.get("identifytype");
			String identifyno = (String) checkwx.get("identifyno");
			cardQueryEntity.setIdentifyType(identifytype);
			cardQueryEntity.setIdentifyNo(identifyno);
			String licenseNoList = "";
			StringBuffer licenseNoListSb = new StringBuffer();
			String allLicenseNoList = "";
			List<CarDataEntity> userCarList = (List<CarDataEntity>) request.getAttribute(ConstantUtils.USER_CAR_LIST);
			logger.info("userCarList:"+userCarList);
			//拼接全部车辆
			if (userCarList != null && !userCarList.isEmpty()) {//实际，进入此方案，逻辑正常的情况下，这里一定存在车辆信息
				for(int i=0;i<userCarList.size();i++){
					CarDataEntity car = userCarList.get(i);
					if(i!=0){
						licenseNoListSb.append(","+car.getLicenseNo());
					}else{
						licenseNoListSb.append(car.getLicenseNo());
					}
					
				}
			}
			allLicenseNoList = licenseNoListSb.toString();
			String licenseNoList2= cardQueryEntity.getLicenseNoList();
			if(licenseNoList2==null||"ALL".equals(licenseNoList2)){
				licenseNoList = allLicenseNoList;
			}else{//单车辆查询
				licenseNoList = licenseNoList2;
				//单辆车需校验此车辆是否为绑定车辆 add xbz 2017-06-21
				if(!allLicenseNoList.contains(licenseNoList)){//非法查询
					throw new RuntimeException("查询车辆（"+licenseNoList+"）不在可查询车辆列表（"+allLicenseNoList+"）中。");
				}
			}
			cardQueryEntity.setLicenseNoList(licenseNoList);
			logger.info(cardQueryEntity.toString());
			Map<String, Object> map = giftCardService.getGiftCardList(cardQueryEntity);
			String res = (String)map.get("res");
			String msg = (String)map.get("msg");
			logger.info("res:"+res+",msg:"+msg);
			if("1".equals(res)){
				List<GiftCardEntity> giftCardList = (List<GiftCardEntity>) map.get("giftCardList");
				ResTrans1021Score score = (ResTrans1021Score) map.get("score");
				Page page = (Page) map.get("page");
				if(giftCardList!=null){
					for (GiftCardEntity giftCardEntity : giftCardList) {
						giftCardEntity.getCardType();
						giftCardEntity.setValidDate(DateUtils.format(DateUtils.parse(giftCardEntity.getValidDate(),DateUtils.FORMAT_SHORT ), DateUtils.FORMAT_SHORT_CN));
					}
				}
				String enopenId = "";
				//创建加密openId 提供积分商城用户Id
				if(score!=null){
					enopenId= AESCode.Encrypt(openId, AESCode.AESKEY);
					String userId= AESCode.Encrypt(score.getUserId(), AESCode.AESKEY);
					score.setUserId(userId);
					logger.info("userId:"+score.getUserId());
					//加密userId 提供商城使用
					score.setValidDate(DateUtils.format(DateUtils.parse(score.getValidDate(),DateUtils.FORMAT_SHORT ), DateUtils.FORMAT_SHORT_CN));
				}
				jsonObject.put("enOpenId", enopenId);
				jsonObject.put("data", giftCardList);
				jsonObject.put("score", score);
				jsonObject.put("page", page);
				jsonObject.put("resCode","0");
				jsonObject.put("msg","成功");
				jsonObject.put("userCarList", userCarList); //车辆信息
			}else{
				jsonObject.put("resCode","4");
				jsonObject.put("msg",msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonObject.put("resCode","1"); 
			jsonObject.put("msg","系统繁忙");
		}
		return jsonObject;
	}


	
	/**
	 * 查询订单详情
	 * 描述:
	 * @param rb
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = {"/getOrderDetail.do"})
	public JSONObject getOrderDetail(String openId,HttpServletRequest request, RbOrder rb){
		openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("openId", openId);
		logger.info(rb.toString());
		logger.info("openId:"+openId);
		//拦截
		JSONObject checkwx = checkwx(request,openId);
		System.out.println("checkwx:"+checkwx);
		String cord = (String) checkwx.get("resCode");
		if(!"0".equals(cord)){
			return checkwx;
		}
		/**
		 * 此处做成配置
		 * 1：庞大订单
		 * 2：卡拉丁订单
		 * 3：落地系统
		 */
		//庞大
		String cardType = rb.getFwType();
		rb.setOpenId(openId);
		logger.info("实体类"+cardType);
		if("1003".equals(cardType)){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			String timestamp = df.format(new Date());
			logger.info(timestamp);
			//卡号，合作者账号，时间戳，sikey
			String param = "card_code="+rb.getCardNo()+"&partner="
					+ConfigUtil.getString("accoun")+"&timestamp="+timestamp+ConfigUtil.getString("frompangdakey");
			logger.info(param);
			try {
				String sign = Md5Util.MD5(param);
				logger.info("sign:"+sign);
				JSONObject jsons = new JSONObject();
				jsons.put("card_code",  rb.getCardNo());
				jsons.put("partner", ConfigUtil.getString("accoun"));
				jsons.put("timestamp",timestamp);
				jsons.put("sign", sign);
				logger.info(jsonObject);
				String url = ConfigUtil.getString("pangdaurl")+"?card_code="+rb.getCardNo()+"&partner="+ConfigUtil.getString("accoun")+"&timestamp="+timestamp+"&sign="+sign;
				logger.info("请求url："+url);
				String resStr = HttpUtil.httpGetProxy(url);
				logger.info("庞大返回resStr："+resStr);
				JSONObject jsStr = JSONObject.fromObject(resStr);
				if("200".equals(jsStr.getString("code"))){
					JSONObject orderDetail = (JSONObject) jsStr.get("rdate");
					logger.info(orderDetail);
					logger.info(orderDetail);
					jsonObject.put("msg", "成功");
					jsonObject.put("resCode", "0");
					jsonObject.put("name", orderDetail.get("user_name"));  //姓名
					jsonObject.put("fwDate", orderDetail.get("service_time"));   //
					jsonObject.put("phoneNumber", orderDetail.get("user_phone")); //客户手机号
					jsonObject.put("networkAddress", orderDetail.get("address"));  //网点地址
					jsonObject.put("type", cardType); 
					return jsonObject;
				}else if("100".equals(jsStr.getString("code"))){
					logger.info("返回的信息为:"+jsStr.getString("code")+jsStr.getString("msg"));
					logger.info("返回的信息为:"+jsStr.getString("code")+jsStr.getString("msg"));
					jsonObject.put("resCode", "1");
					jsonObject.put("msg", "参数有误");
				}else{
					logger.info("返回的信息为:"+jsStr.getString("code")+jsStr.getString("msg"));
					logger.info("返回的信息为:"+jsStr.getString("code")+jsStr.getString("msg"));
					jsonObject.put("resCode", "1");
					jsonObject.put("msg", "系统繁忙");
				}
			} catch (Exception e) {
				logger.info("错误信息："+e.getMessage());
				e.printStackTrace();
			}
			return jsonObject;
		}

		//		//卡拉丁
		if("1008".equals(cardType) || "1020".equals(cardType)|| "1037".equals(cardType)){

			String card_number = rb.getCardNo();
			String user_type_id =ConfigUtil.getString("user_type_id");// "55a5c2aefee73429fe000739";//卡拉丁提共的id
			String param = card_number+"&"+user_type_id;
			logger.info("param:"+param);
			String url = ConfigUtil.getString("kaladingurl")+"?card_number="+card_number+"&user_type_id="+user_type_id;
			String jsStr = HttpUtil.httpGetProxy(url);
			logger.info("Http-jsStr=="+jsStr);
			JSONObject kalad = JSONObject.fromObject(jsStr);
			if((Integer)kalad.get("success")!=1){
				jsonObject.put("resCode", "1");
				jsonObject.put("msg", "卡拉丁订单请求失败");
				return jsonObject;
			}
			JSONArray parseArray = JSONArray.parseArray(kalad.get("data").toString());
			if(parseArray.size()==0){
				jsonObject.put("resCode", "1");
				jsonObject.put("msg", "卡拉丁订单请求失败");
				return jsonObject;
			}
			com.alibaba.fastjson.JSONObject orderDetail =  (com.alibaba.fastjson.JSONObject) parseArray.get(0);
			String phoneNumber = (String) orderDetail.get("phone_num");
			jsonObject.put("name", orderDetail.get("name"));  //客户姓名
			jsonObject.put("orderNo", orderDetail.get("seq"));  //卡拉丁编号
			jsonObject.put("phoneNumber", phoneNumber);   //客户手机号
			jsonObject.put("fwDate", orderDetail.get("serve_date")); //服务时间
			jsonObject.put("networkAddress", orderDetail.get("address"));  //地址
			jsonObject.put("resCode", "0");
			jsonObject.put("msg", "成功");
			jsonObject.put("type", cardType); 
			return jsonObject;
		}
		//接落地1001 1004 1007 1009 1010 1011 1234(喷漆修复)
		if("1001".equals(cardType) || "1004".equals(cardType) || "1011".equals(cardType) ||
				"1007".equals(cardType) || "1009".equals(cardType) || "1010".equals(cardType)||"1234".equals(cardType)){
			String type = cardType;
			if("1004".equals(cardType)){
				rb.setFwType("1");
			}else if("1010".equals(cardType)){
				rb.setFwType("2");
			}else if("1007".equals(cardType)){
				rb.setFwType("3");
			}else if("1009".equals(cardType)){
				rb.setFwType("4");
			}else if("1234".equals(cardType)){
				rb.setFwType("5");
			}
			ServiceReOrInquiryEntity serviceReOrInquiry= new ServiceReOrInquiryEntity();
			TransUtil.copyObject(rb, serviceReOrInquiry);   //配值
			logger.info("请求参数："+serviceReOrInquiry.toString());
			try {
				Map<String, Object> seReOrInquiry = fuWuService.getSeReOrInquiry(serviceReOrInquiry);
				if(seReOrInquiry==null){
					jsonObject.put("resCode", "1"); 
					jsonObject.put("msg", "未查询到预约订单"); 
					return jsonObject;
				}
				if("1".equals(seReOrInquiry.get("res"))){
					ResTrans1024BasePartEntity resTrans1024 = (ResTrans1024BasePartEntity)seReOrInquiry.get("resTrans1024BasePartEntity");
					logger.info(resTrans1024.toString());
					NetworkVo netWorkById = giftCardService.getNetWorkById(resTrans1024.getNetworkJobId(),cardType);
					jsonObject.put("msg","成功");
					jsonObject.put("resCode","0");
					jsonObject.put("name", resTrans1024.getName());  
					jsonObject.put("fwType", resTrans1024.getFwType());  
					jsonObject.put("orderNo", resTrans1024.getOrderNo()); 
					jsonObject.put("licenseNo", resTrans1024.getLicenseNo());  
					jsonObject.put("fwAddress", resTrans1024.getFwAddress());  
					jsonObject.put("networkName", resTrans1024.getNetworkName());  
					jsonObject.put("phoneNumber", resTrans1024.getPhoneNumber());   
					jsonObject.put("networkJobId", resTrans1024.getNetworkJobId());  
					jsonObject.put("networkPhone", resTrans1024.getNetworkPhone());  
					jsonObject.put("networkAddress", resTrans1024.getNetworkAddress()); 
					jsonObject.put("latitude", netWorkById.getLatitude()); 
					jsonObject.put("longitude", netWorkById.getLongitude()); 
					jsonObject.put("type", type);//订单详情页，logo名称
					logger.info(resTrans1024.getFwDate());
					jsonObject.put("fwDate", DateUtils.format(DateUtils.parse(resTrans1024.getFwDate(), DateUtils.FORMAT_SHORT), DateUtils.FORMAT_SHORT_CN)); 
					logger.info("订单详情："+jsonObject.toString());
					return jsonObject;
				}else{
					jsonObject.put("resCode", "1");
					jsonObject.put("msg", "未查询到预约订单");
					return jsonObject;
				}
				//订单信息
			} catch (EpiccException e) {
				logger.info("订单查询："+e.getMessage());
				jsonObject.put("resCode","1");
				jsonObject.put("msg","服务异常");
			}
		}
		return jsonObject;
	}

	/**
	 * 品牌列表
	 * 描述:
	 * @param request
	 * @param response
	 * @return
	 * @author qex 2016年12月8日 下午4:20:03
	 */
	@ResponseBody
	@RequestMapping(value = {"getBrand.do"})
	public JSONObject getBrand(String networkType){
		logger.info("品牌查询 networkType:"+networkType);
		JSONObject jsonObject = new JSONObject();
		List<BrandEntity> brandList = giftCardService.queryBrand(networkType);
		jsonObject.put("data", brandList);
		jsonObject.put("resCode", "0");
		return jsonObject;
	}

	/**
	 * 品牌网点
	 * 描述:
	 * @param brandId
	 * @param latitude
	 * @param longitude
	 * @param page
	 * @return
	 * @author qex 2016年12月8日 下午3:10:29
	 */
	@ResponseBody
	@RequestMapping(value = {"getNetworkByBrandId.do"})
	public JSONObject getNetworkByBrandId(String brandName, String cardType,String brandId,String latitude, String longitude,Page page,String networkType){
		logger.info("请求参数：cardType="+cardType+",brandId="+brandId+",latitude="+latitude+",longitude="+longitude+",page="+page+",networkTpe="+networkType);
		logger.info("getNetworkByBrandId="+brandId);
		JSONObject jsonObject = new JSONObject();
		/*if(null==brandId||"".equals(brandId)){
			jsonObject.put("res","1");
			jsonObject.put("msg", "请求参数缺失，请刷新页面重试！");
			return jsonObject;
		}*/
		jsonObject.put("resCode", "1");
		try {
			Page page1 = giftCardService.getNetworkByBrandId(brandId,latitude, longitude,page,networkType,cardType);
			jsonObject.put("resCode", "0");
			jsonObject.put("cardType", cardType);
			jsonObject.put("brandName", brandName);
			jsonObject.put("brandId", brandId);
			jsonObject.put("data", page1.getList());
			jsonObject.put("pageCount", page1.getPageCount());
			jsonObject.put("pageNo", page.getPageNo());
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("resMsg", e.getMessage());
		}
		return jsonObject;
	}

	/**
	 * 获取预约数据
	 * 描述:
	 * @param networkJobId
	 * @return
	 * @author qex 2016年12月12日 下午3:00:04
	 */
	@ResponseBody
	@RequestMapping(value = {"getYuYueData.do"})
	public JSONObject getYuYueData(HttpServletRequest request, String networkJobId, String openId){
		openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
		logger.info("openId:"+openId);
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("openId", openId);
		//拦截
		JSONObject checkwx = checkwx(request,openId);
		System.out.println("checkwx:"+checkwx);
		String cord = (String) checkwx.get("resCode");
		if(!"0".equals(cord)){
			return checkwx;
		}
		//拦截end
		jsonObject = giftCardService.getYuYueData(networkJobId);
		jsonObject.put("resCode", "0");
		logger.info(jsonObject.toString());
		return jsonObject;
	}

	/**
	 * 喷漆修复保存预约数据、预约服务订单保存
	 * 描述:
	 * @param pojo
	 * @return
	 * @author qex2016年12月14日 下午1:52:30
	 */
	@ResponseBody
	@RequestMapping(value = {"saveYuYueData.do"})
	public JSONObject saveYuYueData(SaveYuYueData pojo, HttpServletRequest request){
		logger.info("请求："+pojo.toString());
		JSONObject jsonObject = new JSONObject();
		String openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
		pojo.setOpenId(openId);
		logger.info("openId:"+ openId);
		jsonObject.put("openId", openId);
		//拦截
		JSONObject checkwx = checkwx(request,openId);
		System.out.println("checkwx:"+checkwx);
		String cord = (String) checkwx.get("resCode");
		if(!"0".equals(cord)){
			return checkwx;
		}
		String identifytype = (String) checkwx.get("identifytype");
		String identifyno = (String) checkwx.get("identifyno");
		//拦截end
		pojo.setIdentifyNo(identifyno);
		pojo.setIdentifyType(identifytype);
		//拦截end
		pojo.setSendTpe("0");
		try {
			jsonObject = giftCardService.saveYuYueDate(pojo);
		} catch (EpiccException e) {
			e.printStackTrace();
			jsonObject.put("resCode", "4");
			jsonObject.put("msg", "系统异常，请联络客服！");
		}
		return jsonObject;
	}



	/**
	 * 
	 * 描述:玻璃修复网点
	 * @param pageNo
	 * @param latitude
	 * @param longitude
	 * @return
	 * @author qex 2017年1月4日 下午5:46:01
	 */
	@ResponseBody
	@RequestMapping(value = {"getNetworkBoli.do"})
	public JSONObject getNetworkBoli(String openId, String pageNo, String latitude, String longitude, String cardType, HttpServletRequest request){
		openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
		logger.info("openId="+openId+"pageNO"+pageNo+"===latitude"+ latitude+"longitude="+ longitude);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("openId", openId);
		//拦截
		JSONObject checkwx = checkwx(request,openId);
		System.out.println("checkwx:"+checkwx);
		String cord = (String) checkwx.get("resCode");
		if(!"0".equals(cord)){
			return checkwx;
		}
		//拦截end
		Page  page = giftCardService.getNetworkBoli(cardType, pageNo, longitude, latitude);
		logger.info("page="+page);
		jsonObject.put("data",page.getList());
		jsonObject.put("pageNo", pageNo);
		jsonObject.put("pageCount", page.getPageCount());
		jsonObject.put("resCode", "0");
		return jsonObject;
	}

	/**
	 * 喷漆玻璃取消订单
	 * 描述:
	 * @param cancel
	 * @return
	 * @author qex 2017年1月5日 下午2:47:23
	 */
	@ResponseBody
	@RequestMapping(value={"cancelOrder.do"})
	public JSONObject cancelOrder(String openId, CancelOrder cancel, HttpServletRequest request){
		openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
		logger.info("openId:"+ openId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("openId", openId);
		try {
			//拦截
			JSONObject checkwx = checkwx(request,openId);
			System.out.println("checkwx:"+checkwx);
			String cord = (String) checkwx.get("resCode");
			if(!"0".equals(cord)){
				return checkwx;
			}
			//拦截end
			logger.info("cancelOrder-请求信息:"+cancel.toString());
			if("1004".equals(cancel.getFwType())){
				cancel.setFwType("1");//上门代办验车
			}else if("1010".equals(cancel.getFwType())){
				cancel.setFwType("2");//上门代办理赔服务
			}else if("1007".equals(cancel.getFwType())){
				cancel.setFwType("3");//喷漆
			}else if("1009".equals(cancel.getFwType())){
				cancel.setFwType("4");//玻璃修复
			}/*else if("1234".equals(cancel.getFwType())){
				cancel.setFwType("5");//维护保养
			}*/
			cancel.setYuYueDate(DateUtils.format(DateUtils.parse(cancel.getYuYueDate(),DateUtils.FORMAT_SHORT_CN), DateUtils.FORMAT_INT));
			jsonObject = giftCardService.cancelOrder(cancel);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("errorMsg:"+e.getMessage());
			logger.info("errorMsg:"+e.getMessage());
			jsonObject.put("resCode","1");
			jsonObject.put("msg","请求异常，请重试！");
		}
		return jsonObject;
	}


	/**
	 * 
			* 描述:积分消费查询
			* @param openId
			* @return
			* @author qex 2017年2月5日 上午10:58:33
	 */
	@ResponseBody
	@RequestMapping(value={"getScoreDeail.do"})
	public Map<String,Object> getScoreDeail(String openId,String pageNo,HttpServletRequest request){
		openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
//		openId = openId.replace(" ", "+");
		Map<String,Object> resMap=new HashMap<String,Object>();
		resMap.put("resCode", "1");
		logger.info("openId:"+openId);
		int pageno=1;
		if(StringUtils.isNumeric(pageNo)){
			pageno=Integer.valueOf(pageNo);
		}
		try {
			Map<String, String>  returnmap = WeixinUtil.getUserNameFormPICC(openId);
			String identifytype = returnmap.get("identifytype");
			String identifyno = returnmap.get("identifyno");
			Page page = giftCardService.getScoreDeail(identifyno,identifytype,pageno);
			if(page!=null){
				resMap.put("resCode", "0");
				resMap.put("page", page);
				return resMap;
			}else{
				
			}
		} catch (Exception e) {
			logger.info("error:"+e.getMessage());
			e.printStackTrace();
		}
		return resMap;
	}
	
	/**
	 * 
			* 描述:爱车保养详情页网点列表
			* @param networkType
			* @param longitude
			* @param latitude
			* @param pageNo
			* @return
			* @author qex 2017年2月21日 上午9:59:44
	 */
	@ResponseBody
	@RequestMapping(value={"getNetworkByType.do"})
	public JSONObject getNetworkByType(String networkType,String longitude, String latitude, String pageNo){
		Page page = giftCardService.getNetworkByType(networkType,longitude,latitude,pageNo);
		JSONObject jsonObject = new JSONObject();
		jsonObject .put("data",page.getList());
		jsonObject.put("pageNo", pageNo);
		jsonObject.put("pageCount", page.getPageCount());
		jsonObject.put("resCode", "0");
		return jsonObject;
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
//		if(userAgent.indexOf("MicroMessenger") == -1){
//			logger.info("userAgent not have MicroMessenger!");
//			jsonObject.put("resCode","1");
//			jsonObject.put("msg","请在微信客户端打开链接！");
//			return jsonObject;
//		}
//		if("".equals(openId)){
//			jsonObject.put("resCode","1");
//			jsonObject.put("msg", "请重新在微信公众号菜单进入电商福袋！");
//			return jsonObject;
//		}
		//modify by xbz 2017-06-21 登录方式变更后，进入此方法时，已在PiccWxContext中保存了用户的信息，不需要再进行接口调用获取 BEGIN
//		Map<String, String>  returnmap = WeixinUtil.getUserNameFormPICC(openId);
//		logger.info("returnmap:"+returnmap);
//		if(returnmap == null){
//			jsonObject.put("resCode","2");
//			jsonObject.put("msg","请绑定身份信息并登录！");
//			return jsonObject;
//		}
//		String retcode = returnmap.get("retcode");
//		String retmsg = returnmap.get("retmsg");
//		jsonObject.put("identifytype", returnmap.get("identifytype"));
//		jsonObject.put("identifyno", returnmap.get("identifyno"));
//		jsonObject.put("resCode", "0");
//		if(!"0".equals(retcode)){
//			jsonObject.put("resCode","2");//未登录
//			jsonObject.put("msg",StringUtils.isEmpty(retmsg) ? "请关注[北京人保财险]公众号，绑定身份信息并登录" : retmsg);
//			return jsonObject;
//		}
//		if(StringUtils.isEmpty(returnmap.get("identifytype")) || StringUtils.isEmpty(returnmap.get("identifyno"))){
//			jsonObject.put("resCode","3");
//			jsonObject.put("msg","请到个人中心完善个人资料！");
//			return jsonObject;
//		}
		
		
		
		String username = (String) request.getAttribute(ConstantUtils.USER_NAME);
		String identifttype = (String) request.getAttribute(ConstantUtils.CENTRAL_IDENTIFY_TYPE);
		String identiftno = (String) request.getAttribute(ConstantUtils.CENTRAL_IDENTIFY_NO);
//		if((StringUtils.isBlank(identifttype)||StringUtils.isBlank(identiftno))){
//			jsonObject.put("resCode","3");
//			jsonObject.put("msg","请到个人中心完善个人资料！");
//			return jsonObject;
//		}else{
//			jsonObject.put("username", username);
//			jsonObject.put("identifytype", identifttype);
//			jsonObject.put("identifyno", identiftno);
//		}
		jsonObject.put("username", username);
		jsonObject.put("identifytype", identifttype);
		jsonObject.put("identifyno", identiftno);
		return jsonObject;
	}
	
	/**
	 * 
			* 描述:积分加密
			* @param openId
			* 			不加密
			* @param userId
			* 			AES加密后的userId
			* @return
			* @author qex 2017年3月6日 下午1:38:37
	 */
	@ResponseBody
	@RequestMapping(value={"toJiFen.do"})
	public JSONObject toJiFen(String openId,String userId,HttpServletRequest request){
		openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
		logger.info("openId:"+openId+",userId:"+userId);
		JSONObject jsonObject = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("userId", userId);
		data.put("openId", openId);
		data.put("dateTime",DateUtils.getYMDHMS());
		try {
			String key = RandomUtil.getRandomString(6);
			logger.info("data:"+data.toString()+",key:"+key);
			String dataStr = AESCode.Encrypt(data.toString(), key);
			String keyStr =Base64.encode(RSACode.encrypt(key));
			jsonObject.put("key", keyStr);
			jsonObject.put("data", dataStr);
			logger.info("加密--》data:"+dataStr+",key:"+keyStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 
	* 描述:
	* 	请求发送油卡充值卡短信验证码
	* @param openId
	* 				微信id
	* @param verification 
	* 				验证码VO
	* @return
	* @author 许宝众 2017年4月24日 下午1:58:05
	 */
	@ResponseBody
	@RequestMapping(value={"ykSMSCode.do"})
	public JSONObject ykSMSCode(Verification verification,HttpServletRequest request){
		//判空
		if(!org.springframework.util.StringUtils.hasText(verification.getGoodsSeqNumber())){
			JSONObject resJson = new JSONObject();
			resJson.put("resCode", "-1");
			resJson.put("errMsg", "商品序列号不能为空");
			return resJson;
		}
		verification.setOperateType("01");//操作类型01：油卡充值卡 短信验证码
		return giftCardService.sendPhoneValidateCode(verification);
	}
	
	
	
	/***
	 * 
			* 描述:
			* 确认手机号后，准备接收卡密短信通知
			* @param openId
			* 			微信ID
			* 			手机号
			* @param goodsSeqNumber
			* 			礼品序列号
			* @return
			* @author 许宝众 2017年4月21日 下午1:17:35
	 */
	@ResponseBody
	@RequestMapping(value={"ykConfirmSubmit.do"})
	public JSONObject confirmPrepareReceiveMsgNotify(String goodsSeqNumber,String validateCode,HttpServletRequest request){
		//校验
		if(!org.springframework.util.StringUtils.hasText(goodsSeqNumber)){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("resCode", "0");
			jsonObject.put("errMsg","礼品序列号不能为空");
		}else if(!org.springframework.util.StringUtils.hasText(validateCode)){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("resCode", "0");
			jsonObject.put("errMsg","礼品序列号不能为空");
		}
		
		try {
			return giftCardService.requestSendGiftMsgNotify(goodsSeqNumber,validateCode);
		} catch (EpiccException e) {
			logger.error("请求发送卡密短信失败", e);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("resCode", "0");
			jsonObject.put("errMsg",e.getErrMess());
			return jsonObject;
		}
	}
	
	/**
	 * 
			* 描述:关联投保人车辆，获取投保人手机号
			* @param request
			* @param identifyType 证件类型
			* @param identifyNo 证件号码
			* @param licenseNo 车牌号
			* @return JSONObject
			* @author 骆利锋 2017年6月2日 下午6:37:49
	 */
	@RequestMapping(value={"getPhoneNumByLicenseNo.do"})
	@ResponseBody
	public JSONObject getPhoneNumByLicenseNo(HttpServletRequest request,String identifyType,String identifyNo,String licenseNo){
		try {
			return giftCardService.getPhoneNumByLicenseNo(identifyType, identifyNo, licenseNo);
		} catch (EpiccException e) {
			logger.error("关联投保人车辆，获取投保人手机号", e);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("resCode", "0");
			jsonObject.put("errMsg",e.getErrMess());
			return jsonObject;
		}
	}
	
	/**
	 * 
			* 描述:获取手机校验码(关联投保车辆)
			* @param request
			* @param operateType 操作类型
			* @param identifyType 证件类型
			* @param identifyNo 证件号码
			* @param licenseNo 车牌号
			* @return JSONObject
			* @author 骆利锋 2017年6月2日 下午6:35:36
	 */
	@RequestMapping(value={"getPhoneVerifyCodeForBindCar.do"})
	@ResponseBody
	public JSONObject getVerifyCodeForBindCar(HttpServletRequest request,String identifyType,String identifyNo,String licenseNo){
		String operateType = "bindCar";
		//将请求参数组成JSON串
		JSONObject reqData = new JSONObject();
		reqData.put("identifytype", identifyType);
		reqData.put("identifyno", identifyNo);
		reqData.put("licenseno", licenseNo);
		try {
			return giftCardService.getPhoneVerifyCode(operateType, reqData.toString());
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.error("获取手机校验码", e);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("resCode", "0");
			jsonObject.put("errMsg",e.getErrMess());
			return jsonObject;
		}
	}
	
	/**
	 * 
			* 描述:获取手机校验码(变更手机号)
			* @param request
			* @param operateType 操作类型
			* @param identifyType 证件类型
			* @param identifyNo 证件号码
			* @param licenseNo 车牌号
			* @return JSONObject
			* @author 骆利锋 2017年6月2日 下午6:35:36
	 */
	@RequestMapping(value={"getPhoneVerifyCodeForChangePhone.do"})
	@ResponseBody
	public JSONObject getVerifyCodeForChangePhone(HttpServletRequest request,String identifyType,String identifyNo,String licenseNo){
		String operateType = "changePhone";
		//将请求参数组成JSON串
		JSONObject reqData = new JSONObject();
		reqData.put("identifytype", identifyType);
		reqData.put("identifyno", identifyNo);
		reqData.put("licenseno", licenseNo);
		try {
			return giftCardService.getPhoneVerifyCode(operateType, reqData.toString());
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.error("获取手机校验码", e);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("resCode", "0");
			jsonObject.put("errMsg",e.getErrMess());
			return jsonObject;
		}
	}
	
	/**
	 * 
			* 描述:关联投保车辆，绑定（提交）用户信息
			* @param identifyType
			* @param identifyNo
			* @param licenseNo
			* @param userName
			* @param valiDateCode
			* @return JSONObject
			* @author 骆利锋 2017年6月6日 上午11:14:19
	 */
	@RequestMapping(value={"bindCarUserInfo.do"})
	@ResponseBody
	public JSONObject bindCarUserInfo(HttpServletRequest request,String identifyType,String identifyNo,String licenseNo,String valiDateCode){
		try {
			String userName = (String) request.getAttribute(ConstantUtils.USER_NAME);
			return giftCardService.bindCarUserInfo(identifyType, identifyNo, licenseNo, userName, valiDateCode);
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.error("关联投保车辆，绑定（提交）用户信息", e);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("resCode", "0");
			jsonObject.put("errMsg",e.getErrMess());
			return jsonObject;
		}
	}
	
	/**
	 * 
			* 描述:投保手机号变更（原手机号可用）
			* @param identifyType
			* @param identifyNo
			* @param licenseNo
			* @param userName
			* @param valiDateCode
			* @param modifyPhoneNumber 变更后的手机号
			* @return JSONObject
			* @author 骆利锋 2017年6月6日 下午1:27:35
	 */
	@RequestMapping(value={"modifyPhoneNum.do"})
	@ResponseBody
	public JSONObject modifyPhoneNum(HttpServletRequest request,String identifyType,String identifyNo,String licenseNo,String valiDateCode,String modifyPhoneNumber){
		try {
			String userName = (String) request.getAttribute(ConstantUtils.USER_NAME);
			String openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
			return giftCardService.changePhone(openId,identifyType, identifyNo, licenseNo, userName, valiDateCode, modifyPhoneNumber);
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.error("投保手机号变更（原手机号可用）", e);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("resCode", "0");
			jsonObject.put("errMsg",e.getErrMess());
			return jsonObject;
		}
	}
	
	/**
	 * 
			* 描述:投保手机号变更（提交资料审核）
			* @param files 上传的车辆照片信息
			* @param phoneModifyEntity 手机号变更实体
			* @return JSONObject
			* @author 骆利锋 2017年6月6日 下午2:01:21
	 */
	@RequestMapping(value={"changePhoneAudit.do"})
	@ResponseBody
	public JSONObject changePhoneAudit(HttpSession session,HttpServletRequest request,HttpServletResponse response/*,PhoneModifyEntity phoneModifyEntity*/){
		try {
			String serverIds = request.getParameter("serverIds");
			logger.info("上传到微信端图片serverIds="+serverIds);
			String identifyType = request.getParameter("identifyType");
			String identifyNo = request.getParameter("identifyNo");
			String licenseNo = request.getParameter("licenseNo");
			String modifyPhoneNumber = request.getParameter("modifyPhoneNumber");
			String userName = (String) request.getAttribute(ConstantUtils.USER_NAME);
			List<CarPhotoEntity> files = new ArrayList<CarPhotoEntity>();
			//从微信提取新增的照片
			if(serverIds!=null && !"".equals(serverIds.trim())){
				/*String[] sids = serverIds.split(",");
				if(sids.length>0){
					for (String sid : sids) {
						if(sid != null && !"".equals(sid.trim())){
							CarPhotoEntity carPhotoEntity = new CarPhotoEntity();
							carPhotoEntity.setBase64code(WeixinUtil.downloadImage(sid));
							carPhotoEntity.setFilename(GetBussinessNoService.GetBussinessNo("CARPHOTO")+".jpg");
							files.add(carPhotoEntity);
						}
					}
				}*/
				CarPhotoEntity carPhotoEntity = new CarPhotoEntity();
				carPhotoEntity.setBase64Code(WeixinUtil.downloadImage(serverIds));
				carPhotoEntity.setFileName(GetBussinessNoService.GetBussinessNo("CARPHOTO")+".jpg");
				files.add(carPhotoEntity);
			}
			PhoneModifyEntity phoneModifyEntity = new PhoneModifyEntity();
			phoneModifyEntity.setIdentifyType(identifyType);
			phoneModifyEntity.setIdentifyNo(identifyNo);
			phoneModifyEntity.setLicenseNo(licenseNo);
			phoneModifyEntity.setUserName(userName);
			phoneModifyEntity.setModifyPhoneNumber(modifyPhoneNumber);
		
			return giftCardService.changePhoneAudit(files, phoneModifyEntity);
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.error("投保手机号变更（提交资料审核）", e);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("resCode", "0");
			jsonObject.put("errMsg",e.getErrMess());
			return jsonObject;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("投保手机号变更（提交资料审核）", e);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("resCode", "0");
			jsonObject.put("errMsg","系统异常");
			return jsonObject;
		}
	}
	
	/**
	 * 
			* 描述:根据车牌号获取车辆信息
			* @param openId
			* @param licenseNo
			* @return
			* @author 骆利锋 2017年6月15日 上午9:12:30
	 */
	@RequestMapping(value="getCarList.do")
	@ResponseBody
	public JSONObject getCarList(String licenseNo,HttpServletRequest request){
		JSONObject json = new JSONObject();
		String openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
		try {
			List<CarDataEntity> list = giftCardService.loginGetCarInfo(openId);
			for(CarDataEntity car : list){
				if (car.getLicenseNo().equals(licenseNo)) {
					CarDataEntity CarData = new CarDataEntity();
					CarData.setLicenseNo(licenseNo);
					List<IdentifyDateEntity> identifyList= new ArrayList<IdentifyDateEntity>();
					for(IdentifyDateEntity identify : car.getIdentifyDateEntityList()){
						identify.setPhoneNumber(CommonUtils.shieldPhoneNumber(identify.getPhoneNumber().trim()));
						identifyList.add(identify);
					}
					CarData.setIdentifyDateEntityList(identifyList);
					json = JSONObject.fromObject(CarData);
					json.put("resCode", "1");
					break;
				}
			}
		} catch (EpiccException e) {
			e.printStackTrace();
			json.put("resCode", "0");
		}
		return json;
	}
	
	/**
	 * 
	* 描述:
	* 	请求发送京东E卡充值卡短信验证码
	* @param openId
	* 				微信id
	* @param verification 
	* 				验证码VO
	* @return
	* @author 骆利锋 2017年6月27日 下午1:58:05
	 */
	@ResponseBody
	@RequestMapping(value={"jdSMSCode.do"})
	public JSONObject jdSMSCode(Verification verification,String openId,HttpServletRequest request){
		//判空
		if(!org.springframework.util.StringUtils.hasText(verification.getGoodsSeqNumber())){
			JSONObject resJson = new JSONObject();
			resJson.put("resCode", "-1");
			resJson.put("errMsg", "商品序列号不能为空");
			return resJson;
		}
		verification.setOperateType("01");//操作类型01：油卡充值卡 短信验证码
		return giftCardService.sendPhoneValidateCode(verification);
	}
	
	/***
	 * 
			* 描述:
			* 确认手机号后，准备接收卡密短信通知
			* @param openId
			* 			微信ID
			* 			手机号
			* @param goodsSeqNumber
			* 			礼品序列号
			* @return
			* @author 骆利锋 2017年6月27日 下午1:58:05
	 */
	@ResponseBody
	@RequestMapping(value={"jdConfirmSubmit.do"})
	public JSONObject jdConfirmPrepareReceiveMsgNotify(String openId,String goodsSeqNumber,String validateCode,HttpServletRequest request){
		//校验
		if(!org.springframework.util.StringUtils.hasText(goodsSeqNumber)){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("resCode", "0");
			jsonObject.put("errMsg","礼品序列号不能为空");
		}else if(!org.springframework.util.StringUtils.hasText(validateCode)){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("resCode", "0");
			jsonObject.put("errMsg","礼品序列号不能为空");
		}
		
		try {
			return giftCardService.requestSendGiftMsgNotify(goodsSeqNumber,validateCode);
		} catch (EpiccException e) {
			logger.error("请求发送卡密短信失败", e);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("resCode", "0");
			jsonObject.put("errMsg",e.getErrMess());
			return jsonObject;
		}
	}
	
	/**
	 * 
			* 描述:
			* 	解绑车辆
			* @param licenseNo
			* @return
			* @author 许宝众 2017年6月27日 下午2:46:56
	 */
	@RequestMapping(value="unbindCar.do")
	@ResponseBody
	public com.alibaba.fastjson.JSONObject unbindCar(String licenseNo,HttpServletRequest request){
		com.alibaba.fastjson.JSONObject res= new com.alibaba.fastjson.JSONObject();
		String openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
		String userName = (String) request.getAttribute(ConstantUtils.USER_NAME);
		if(StringUtils.isBlank(openId)){
			res.put("resCode", "0");
			res.put("errMsg", "openId不能为空");
		}else{
			try{
				res=giftCardService.unbindCar(openId , userName,licenseNo);
			}catch(Exception e){
				res.put("resCode", "0");
				if(e instanceof EpiccException){
					EpiccException ep = (EpiccException)e;
					res.put("errMsg", ep.getErrMess());
				}else{
					res.put("errMsg", e.getMessage());
				}
			}
		}
		return res;
	}
	
	/**
	 * 洗车网点查询
			* 描述:
			* @return
			* @author 骆利锋 2017年7月10日 下午3:00:48
	 */
	@RequestMapping(value="getWashCarNetwork.do")
	@ResponseBody
	public JSONObject getWashCarNetwork(String queryType,String queryContent,String longitude,String latitude,String networkType){
		JSONObject json = new JSONObject();
		try {
			json = giftCardService.queryWashCarNetWork(queryType, queryContent, longitude, latitude,networkType);
		} catch (EpiccException e) {
			e.printStackTrace();
			json.put("resCode",0);
			json.put("errMsg", e.getErrMess());
		}
		return json;
	}
	
	/**
	 * 生成洗车随机码
			* 描述:
			* @return
			* @author 骆利锋 2017年7月10日 下午3:00:48
	 */
	@RequestMapping(value="getWashCarCode.do")
	@ResponseBody
	public JSONObject getWashCarCode(String goodsSeqNumber,HttpServletRequest request){
		JSONObject json = new JSONObject();
		String openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
		try {
			json = giftCardService.generateWashCarCode(goodsSeqNumber,openId);
		} catch (EpiccException e) {
			e.printStackTrace();
			json.put("resCode",0);
			json.put("errMsg", e.getErrMess());
		}
		return json;
	}
	
	/**
	 * 洗车记录查询
			* 描述:洗车记录查询
			* @return
			* @author 骆利锋 2017年7月10日 下午3:00:48
	 */
	@RequestMapping(value="getWashCarRecord.do")
	@ResponseBody
	public JSONObject getWashCarRecord(String goodsSeqNumber){
		JSONObject json = new JSONObject();
		try {
			json = giftCardService.queryWashCarRecord(goodsSeqNumber);
		} catch (EpiccException e) {
			e.printStackTrace();
			json.put("resCode",0);
			json.put("errMsg", e.getErrMess());
		}
		return json;
	}
	/**
	 * 
	 * 描述:微信模板消息url地址跳转校验
	 * 
	 * @param request
	 * @param response
	 * @author zs 2017年9月18日 上午14:23:01
	 */
	@ResponseBody
	@RequestMapping(value = { "/authWxUrl.do" })
	public String authWxUrl(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 解密key
			String keyStr = "WXTEMPLATEMESSAGE";
			String OPEN_ID = (String) request
					.getAttribute(ConstantUtils.OPEN_ID);
			String cardNo = request.getParameter("cardNo");
			String fwType = request.getParameter("fwType");
			String openId = request.getParameter("openId");
			logger.info("接收到openid："+openId);
			String cardPass = request.getParameter("cardPass");
			String validDate = request.getParameter("validDate");
			String type = request.getParameter("type");
			String cardTypeName = request.getParameter("cardTypeName");
			openId = AESCode.hexStringToString(openId);
			logger.info("16进制转码后openid："+openId);
			// 解密报文
			openId = AESCode.customDecypt(openId,keyStr);
			logger.info("解密后openid："+openId);
			logger.info("服务器openid："+OPEN_ID);
			if (StringUtils.isNotBlank(OPEN_ID) && OPEN_ID.equals(openId)) {
				String url = "/dzsw/redirectNotService.jsp?cardNo=" + cardNo
						+ "&fwType=" + fwType + "&cardPass=" + cardPass
						+ "&validDate=" + validDate + "&type=" + type
						+ "&cardTypeName=" + cardTypeName + "&openId=" + openId;
				request.getRequestDispatcher(url).forward(request, response);
				return null;

			} else {
				logger.info("openId异常");
				return "openId异常";
			}
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e.getMessage());
			return e.getMessage();
		}
	}
	
	/**
	 * 
			* 描述:查询全年洗车记录
			* @param goodsSeqNumber
			* @return
			* @author lxp 2017年12月12日 上午10:37:33
	 */
	@RequestMapping(value="getAllYearWashConsumeRecord.do")
	@ResponseBody
	public JSONObject getAllYearWashConsumeRecord(String month, String goodsSeqNumber){
		logger.info("month:"+month+", goodsSeqNumber:"+goodsSeqNumber);
		JSONObject json = new JSONObject();
		try {
			json = giftCardService.getAllYearWashConsumeRecord(month, goodsSeqNumber);
		} catch (EpiccException e) {
			e.printStackTrace();
			json.put("resCode",0);
			json.put("errMsg", e.getErrMess());
		}
		logger.info("json:"+json);
		return json;
	}
	
	/**
	 * 
			* 描述:查询保养代金劵网点
			* @param queryType
			* @param queryContent
			* @param longitude
			* @param latitude
			* @param networkType
			* @param brandId
			* @return
			* @author 赵硕  2017年12月21日 下午3:39:17
	 */
	@RequestMapping(value="getBaoYangCouponNetwork.do")
	@ResponseBody
	public JSONObject getBaoYangCouponNetwork(String queryType,String queryContent,String longitude,String latitude,String networkType,String brandId){
		JSONObject json = new JSONObject();
		try {
			if(StringUtils.isNotBlank(longitude) && StringUtils.isNotBlank(latitude) && StringUtils.isNotBlank(queryType)
					&& StringUtils.isNotBlank(networkType)  && StringUtils.isNotBlank(brandId)){
				json = giftCardService.queryBaoYangCouponNetWork(queryType, queryContent, longitude, latitude, networkType , brandId);
			}else{
				logger.info("查询保养代金劵网点,参数错误");
				json.put("resCode",0);
				json.put("errMsg", "参数错误");
			}	
		} catch (EpiccException e) {
			e.printStackTrace();
			json.put("resCode",0);
			json.put("errMsg", e.getErrMess());
		}
		return json;
	}
	/**
	 * 
			* 描述:跳转维修保养劵订单支付页
			* @param response
			* @param request
			* @param cardNo
			* @param cardPass
			* @param remainAmount
			* @param networkName
			* @param networkJobId
			* @param networkPhone
			* @param networkAddress
			* @return String
			* @author 赵硕  2017年12月13日 下午3:02:08
	 */
	@RequestMapping(value="prepareBaoYangCouponPay.do")
	public String prepareBaoYangCouponPay(HttpServletResponse response,HttpServletRequest request,String licenseNo
			,String remainAmount,String networkName,String networkJobId,String networkPhone,String networkAddress){
			String page = "error";
			String errMsg = null;
			Date now = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				if(StringUtils.isNotBlank(licenseNo)  
						&& StringUtils.isNotBlank(remainAmount)){
					request.setAttribute("licenseNo", licenseNo);
					request.setAttribute("remainAmount", remainAmount);
					request.setAttribute("networkName", networkName);
					request.setAttribute("networkJobId", networkJobId);
					request.setAttribute("networkPhone", networkPhone);
					request.setAttribute("networkAddress", networkAddress);
					page = "dzsw/baoYangCouponPay";
				}else{
					errMsg="跳转维修保养劵订单支付页,参数错误";
					page = "error";
				}
			} catch (Exception e) {
				e.printStackTrace();
				errMsg = e.getMessage();
			}finally{
				logger.info("跳转维修保养劵订单支付页接口调用时间：{" + sdf.format(now)+"} ,参数：{ licenseNo="+licenseNo+",remainAmount="+remainAmount+",networkName="+networkName+",networkJobId="+networkJobId+",networkPhone="+networkPhone+",networkAddress="+networkAddress+"}"+",errMsg：{"+errMsg+"}");
			}
			return page;
	}
	
	/**
	 * 
		* 描述:维修保养代金券多次消费
		* @param cardId
		* @param passWord
		* @param consumeAmount
		* @param networkName
		* @param networkJobid
		* @param networkPhone
		* @param networkAddress
		* @param fwkCode
		* @return jsonObject
		* @author 赵硕  2017年12月8日 上午10:49:38
	 */
	@ResponseBody
	@RequestMapping(value = { "/consumeBaoYangCoupon.do" })
	public JSONObject consumeBaoYangCoupon(HttpServletRequest request,String licenseNo,String consumeAmount,String networkName
			,String networkJobId ,String fwkCode){
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		JSONObject json = new  JSONObject();
		try {
			if(StringUtils.isNotBlank(licenseNo) && 
					StringUtils.isNotBlank(consumeAmount) && 
					     StringUtils.isNotBlank(networkJobId) && StringUtils.isNotBlank(fwkCode)){
				String openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				String consumeTime = df.format(new Date());
				BaoYangCouponConsume baoYangCouponConsume = new BaoYangCouponConsume();
				baoYangCouponConsume.setLicenseNo(licenseNo);
				baoYangCouponConsume.setConsumeAmount(consumeAmount);
				baoYangCouponConsume.setConsumeTime(consumeTime);
				baoYangCouponConsume.setNetworkName(networkName);
				baoYangCouponConsume.setNetworkJobid(networkJobId);
				baoYangCouponConsume.setFwkCode(fwkCode);
				baoYangCouponConsume.setOpenId(openId);
				json  = giftCardService.consumeBaoYangCoupon(baoYangCouponConsume);
			}else{
				json.put("resCode",0);
				json.put("errMsg", "参数错误");
			}
		} catch (EpiccException e) {
			e.printStackTrace();
			json.put("resCode",0);
			json.put("errMsg", e.getErrMess());
		}finally{
			logger.info("调用维修保养代金券多次消费接口时间：{"+sdf.format(now)+"},请求参数：{licenseNo ="+licenseNo+",consumeAmount="+consumeAmount+",networkName="+networkName
					+",networkJobId="+networkJobId+",fwkCode="+fwkCode+"},返回报文：{"+json.toString()+"}");
		}	
		return json;
	}
	
	
	/**
	 * 
	 * 描述:维修保养代金卷使用记录查询
	 * @return
	 * @author 赵硕 2017年12月7日 下午9:51:48
	 */
	@RequestMapping(value="getBaoYangCouponRecord.do")
	public String getBaoYangCouponRecord(HttpServletRequest request,
			HttpServletResponse response,String licenseNo,String totalAmount){
		logger.info("进入维修保养代金卷使用记录查询：licenseNo"+licenseNo);
        String page = "";		
		if(StringUtils.isBlank(licenseNo)){
			page = "error";
		}else{
			try {
				String openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
				Map<String, Object> map = giftCardService.queryBaoYangCouponRecord(licenseNo , openId);
				String resCode = map.get("resCode").toString();
				if(!"1".equals(resCode)){
					page = "error";
					return page;
				}
				request.setAttribute("list", (List<BaoYangCouponRecord>)map.get("list"));
				Double dou = Double.parseDouble(map.get("remainAmount").toString());
				request.setAttribute("remainAmount",dou.intValue());
				request.setAttribute("totalAmount",totalAmount);
				request.setAttribute("licenseNo",licenseNo);
				logger.info("进入维修保养代金卷使用记录查询页面跳转：/dzsw/baoYangCouponConsumeRecord.jsp");
			    page = "dzsw/baoYangCouponConsumeRecord";
				} catch (Exception e) {
					e.printStackTrace();
					page = "error";
				}
		}
		
		return page;
	}
	
	/**
	 * 
			* 描述:全年保养劵使用记录查询
			* @param request
			* @param response
			* @param cardNo
			* @return
			* @author 赵硕  2017年12月18日 下午1:39:40
	 */
	@RequestMapping(value="getAllYearBaoYangCouponRecord.do")
	public String getAllYearBaoYangCouponRecord(HttpServletRequest request,
			HttpServletResponse response,String cardNo){
		logger.info("进入全年保养劵使用记录查询：cardNo"+cardNo);
		JSONObject json  = new JSONObject();
        String page = "";		
		if(StringUtils.isBlank(cardNo)){
			page = "error";
		}else{
			try {
				json = giftCardService.queryAllYearBaoYangCouponRecord(cardNo);
				String  resCode = json.getString("resCode");
				if("1".equals(resCode)){
					request.setAttribute("list", (List<ResTrans1061CardDataEntity>)json.get("list"));
					logger.info("进入全年保养劵使用记录查询页面跳转：/dzsw/allYearBaoYangConsumeRecord.jsp");
					page = "dzsw/allYearBaoYangConsumeRecord";
				}else {
					page = "error";
				}
				} catch (Exception e) {
					e.printStackTrace();
					page = "error";
				}	
		}	
		return page;
	}
	
	/**
	 * 
			* 描述:爱车保养订单查询
			* @param cardNo
			* @return
			* @throws EpiccException
			* @author 赵硕  2017年12月22日 上午10:37:54
	 */
	@ResponseBody
	@RequestMapping(value="getAiCarOrderDetail.do")
	public JSONObject getAiCarOrderDetail(String cardNo) throws EpiccException{
		JSONObject json = new JSONObject();
		logger.info("爱车保养订单查询：cardNo"+cardNo);
		if(StringUtils.isBlank(cardNo)){
			json.put("resCode",0);
			json.put("errMsg", "参数错误");
		}else{
			try {
				json = giftCardService.queryAllYearBaoYangCouponRecord(cardNo);
				} catch (Exception e) {
					e.printStackTrace();
					json.put("resCode",0);
					json.put("errMsg", e.getMessage());
				}	
		}	
		return json;
	}
	/**
	 * 
			* 描述:取消全年保养劵订单
			* @param request
			* @param response
			* @param orderNo
			* @return
			* @author 朱久满 2017年12月20日 下午4:04:17
	 */
	@ResponseBody
	@RequestMapping(value="cancelAllYearBaoYangOrder.do")
	public JSONObject cancelAllYearBaoYangOrder(HttpServletRequest request,
			HttpServletResponse response,String orderNo){
		JSONObject json = new JSONObject(); 
		try {
			String openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
			if(StringUtils.isNotBlank(orderNo) && StringUtils.isNotBlank(openId)){
				json =  giftCardService.allYearBaoYangOrderCancel(orderNo, openId);
			}else{
				json.put("resCode", 0);
				json.put("errMsg", "参数错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("resCode", 0);
			json.put("errMsg", e.getMessage());
		}
		
		return json;
	}
	
	 /**
     * 
	 * 描述:交通事故快速处理协议书
	 * @param emailAddress
	 * @return
	 * @author 赵硕  2018年2月8日 上午11:14:55
     */
	@ResponseBody
	@RequestMapping(value = { "/fastProcessing.do" })
	public JSONObject fastProcessing(HttpServletRequest request,String emailAddress){
		JSONObject resJson = new JSONObject();
		String openId = (String) request.getAttribute(ConstantUtils.OPEN_ID);
		try {
			resJson.put("resCode", "0");//默认失败
			if(StringUtils.isNotBlank(emailAddress) && StringUtils.isNotBlank(openId)){
				fuWuService.sendFastProcessingEmail(openId,emailAddress, resJson);
			}else{
			resJson.put("errMsg", "参数错误");
			}
		} catch (Exception e) {
			resJson.put("errMsg", "程序异常");
			e.printStackTrace();
		}
		logger.info("交通事故快速处理协议书：emailAddress="+emailAddress+" resJson="+resJson.toString());
		return resJson;
	}
	/**
	 * 
	* 描述:查询2018版洗车卡当日洗车次数是否超过限制
	* @param request
	* @param licenseNo
	* @return
	* @author 赵硕 2018年2月13日 下午4:30:14
	 */
	@ResponseBody
	@RequestMapping(value = { "/qryWashCardPaymentNum.do" })
	public JSONObject qryWashCardPaymentNum(HttpServletRequest request,String licenseNo){
		JSONObject resJson = new JSONObject();
		resJson.put("resCode", "0");//默认失败
		try {
			if(StringUtils.isNotBlank(licenseNo)){
				giftCardService.washCardPaymentNumQry(licenseNo, resJson);
			}else{
				resJson.put("errMsg", "参数错误");
			}	
		} catch (Exception e) {
			resJson.put("errMsg", "程序异常");
			e.printStackTrace();
		}
		return resJson;
	}
	/**
	 * 
			* 描述:查询主页更新提示消息
			* @return
			* @author 赵硕 2018年4月10日 上午10:10:28
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryUpgradeMessage.do" })
	public JSONObject queryUpgradeMessage(){	
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		JSONObject resJson = new JSONObject();
		resJson.put("resCode", "0");//默认失败
		String errMsg = null;
		try {
			Cache cache = cacheManager.getCache("default");
			String cacheKey = "UPGRADE_MESSAGE";
			ValueWrapper valueWrapper = cache!=null?cache.get(cacheKey):null;
			Object res = valueWrapper!=null?valueWrapper.get():null;
			if(res!=null){
				resJson.put("upgradeMessage", (String)res);
				resJson.put("resCode", "1");
			}else{
				String upgradeMessage = giftCardService.getUpgradeMessage();
				cache.put(cacheKey, upgradeMessage);
				resJson.put("resCode", "1");
				resJson.put("upgradeMessage", upgradeMessage);
			}
		} catch (Exception e) {
			errMsg = e.getMessage();
			resJson.put("errMsg", errMsg);
			e.printStackTrace();
		}
		logger.info("调用方法queryUpgradeMessage时间：["+sdf.format(date)+"],返回报文：["+resJson.toString()+"],错误信息：["+errMsg+"]");
		return resJson;
	}
	/**
	 * 
	 * 描述:赠送礼品通知(1070)
	 * @param promotionNumber 礼品序列号
	 * @param licenseNo 车牌号
	 * @param giftSourceFlag
	 * @return
	 * @author 赵硕2018年4月11日 下午3:51:17
	 */
	@ResponseBody
	@RequestMapping(value = { "/giveGift.do" })
	public JSONObject giveGift(HttpServletRequest request,String promotionNumber,String licenseNo,String giftSourceFlag){
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String errMsg = null;
		JSONObject json = new JSONObject();
		json.put("resCode", "0");//默认失败
		String openId =  (String)request.getAttribute(ConstantUtils.OPEN_ID);
		try {
			if(StringUtils.isNotBlank(promotionNumber) && StringUtils.isNotBlank(licenseNo)){
				giftCardService.giveGiftService(promotionNumber, openId, json,licenseNo,giftSourceFlag);
			}else{
				errMsg ="参数错误";
			}
		} catch (Exception e) {
         errMsg = e.getMessage();
         e.printStackTrace();
		}
		json.put("errMsg", errMsg);//默认失败
		logger.info("调用赠送礼品方法giveGift时间：["+sdf.format(date)+"],请求参数：["+request.getQueryString()+"],返回报文：["+json.toString()+"],错误信息：["+errMsg+"]");
		return json;
	}
	/**
	 * 
		* 描述:确认赠送礼品（1073）
		* @param request
		* @param id
		* @param licenseNo
		* @param shareStatus
		* @return
		* @author 赵硕 2018年5月31日 下午6:42:06
	 */
	@RequestMapping(value={"/giveGiftAgree.do"})
	@ResponseBody
	public JSONObject giveGiftAgree(HttpServletRequest request,String id,String shareStatus,String promotionNumber,String licenseNo,String giftSourceFlag){
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String errMsg = null;
		JSONObject json = new JSONObject();
		json.put("resCode", "0");//默认失败
		String openId =  (String)request.getAttribute(ConstantUtils.OPEN_ID);
		String decodeid = CommonUtils.decodeCardNo(id);
		String decodePromotionNumber     = CommonUtils.decodeCardNo(promotionNumber);
		try {
			if(StringUtils.isNotBlank(decodeid) && 
				StringUtils.isNotBlank(shareStatus) &&
				 StringUtils.isNotBlank(decodePromotionNumber) &&
				   StringUtils.isNotBlank(licenseNo) &&
					 StringUtils.isNotBlank(giftSourceFlag) &&
					  StringUtils.isNotBlank(openId)){
				       giftCardService.giveGiftAgree(decodeid, shareStatus , json , decodePromotionNumber, licenseNo, giftSourceFlag,openId);
			}else{
				errMsg ="参数错误";
			}
		} catch (Exception e) {
         errMsg = e.getMessage();
         e.printStackTrace();
		}
		json.put("errMsg", errMsg);//默认失败
		logger.info("调用确认赠送礼品giveGiftAgree时间：["+sdf.format(date)+"],请求参数：["+request.getQueryString()+"],返回报文：["+json.toString()+"],错误信息：["+errMsg+"]");
		return json;
	}
	
	/**
	 * 
	 * 描述:验证礼品（1068）
	 * @param request
	 * @param id （礼品id通过1070获取）
	 * @param promotionNumber 礼品序列号
	 * @param cardName 礼品名称
	 * @param shareUserName 分享人微信名称
	 * @param shareOpenId 分享人openid
	 * @param giftSourceFlag
	 * @return
	 * @author 朱久满 2018年5月18日 上午10:29:45
	 */
	@RequestMapping(value = { "/receiveGiftsVerify.do" })
	public String receiveGiftsVerify(HttpServletRequest request,String id,String promotionNumber,String cardName,String shareUserName,String shareOpenId,String giftSourceFlag){
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String errMsg = null;
		String openId =  (String)request.getAttribute(ConstantUtils.OPEN_ID);
		String jumpPage = "/dzsw/redirectUrl";
		HashMap<String, Object>	resMap = new HashMap<String, Object>();
		try{
			if(StringUtils.isNotBlank(id) &&  StringUtils.isNotBlank(openId)&& StringUtils.isNotBlank(promotionNumber) && StringUtils.isNotBlank(shareOpenId)){
			String decodeid = CommonUtils.decodeCardNo(id);
			resMap=giftCardService.verifyReceiveGifts(openId, decodeid);
			String resCode = (String)resMap.get("resCode");
				if("1".equals(resCode)){
					request.setAttribute("id", id);
					request.setAttribute("promotionNumber", promotionNumber);
					if(StringUtils.isNotBlank(cardName)){
						cardName = URLDecoder.decode(cardName, "UTF-8");
						if(StringUtils.isNotBlank(shareUserName)){
							shareUserName = URLDecoder.decode(shareUserName, "UTF-8");
						}
						request.setAttribute("shareUserName", shareUserName);
						request.setAttribute("cardName", cardName);
						request.setAttribute("shareOpenId", shareOpenId);
						request.setAttribute("giftSourceFlag", giftSourceFlag);
					}
				 	jumpPage = "dzsw/giftshare/getGift";
				}else{
					errMsg = (String)resMap.get("resMsg");
				}
			}else{
			 errMsg = "参数错误";
			}
		} catch (Exception e) {
			errMsg = e.getMessage();
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(errMsg)){
			request.setAttribute("redirectUrl", (String)request.getSession().getServletContext().getAttribute("httpBasePath")+"dzsw/dszh/main.jsp");
			request.setAttribute("errMsg", errMsg);
		}
		logger.info("调用礼品分享连接校验方法receiveGiftsVerify时间：["+sdf.format(date)+"],请求参数：["+request.getQueryString()+",转码后cardName="+cardName+"],返回报文：["+resMap.toString()+"],错误信息：["+errMsg+"]");
		return jumpPage;
	}
	/**
	 * 
	 * 描述:好友领取礼品（1069）加分享人openid
	 * @param request
	 * @param id 礼品id（通过1070获取）
	 * @param licenseNo  车牌号
	 * @param identifyType 证件号
	 * @param identifyNo 证件类型
	 * @param phoneNumber 手机号
	 * @param promotionNumber 礼品序列号
	 * @param shareOpenId 分享人openid 
	 * @return
	 * @author 朱久满 2018年4月16日 下午2:51:47
	 */
	@ResponseBody
	@RequestMapping(value = { "/friendGetGift.do" })
	public JSONObject friendGetGift(HttpServletRequest request,String id,
			String licenseNo,String identifyType,
			String identifyNo,String phoneNumber,
			String promotionNumber,String valiDateCode,String shareOpenId,String giftSourceFlag){
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String errMsg = null;
		String wxUserName = null;
		String openId =  (String)request.getAttribute(ConstantUtils.OPEN_ID);
		JSONObject resJson = new JSONObject();
		resJson.put("resCode","0");//默认失败
		try {
			if( StringUtils.isNotBlank(id) && 
				 StringUtils.isNotBlank(openId)&&
				  StringUtils.isNotBlank(licenseNo)&&
				   StringUtils.isNotBlank(identifyType)&&
				    StringUtils.isNotBlank(identifyNo)&&
				     StringUtils.isNotBlank(phoneNumber)&&
			          StringUtils.isNotBlank(promotionNumber)&&
			           StringUtils.isNotBlank(valiDateCode)&&
			            StringUtils.isNotBlank(shareOpenId)&&
			             StringUtils.isNotBlank(giftSourceFlag) ){
				wxUserName =  WeixinUtil.getWxUserName(openId);//微信昵称
		       FriendGetGiftVo vo = new FriendGetGiftVo();
								vo.setFriendName(wxUserName);
								 vo.setId(CommonUtils.decodeCardNo(id));
								  vo.setIdentifyNo(identifyNo);
								   vo.setIdentifyType(identifyType);
								    vo.setLicenseNo(licenseNo.toUpperCase());
								     vo.setOpenId(openId);
								      vo.setPhoneNumber(phoneNumber);
								       vo.setPromotionNumber(CommonUtils.decodeCardNo(promotionNumber));
								        vo.setValiDateCode(valiDateCode);
								         vo.setShareOpenId(CommonUtils.decodeCardNo(shareOpenId));
								          vo.setGiftSourceFlag(giftSourceFlag);
				giftCardService.getGiftService(resJson, vo);
			}else{
				errMsg = "参数错误";
			}
		} catch (Exception e) {
          errMsg = e.getMessage();
          e.printStackTrace();
		}
		if(StringUtils.isNotBlank(errMsg)){
			resJson.put("errMsg", errMsg);//详细错误信息
		}
		logger.info("调用好友领取礼品方法friendGetGift时间：["+sdf.format(date)+"],请求参数：[id:"+id+",licenseNo:"+licenseNo+",identifyType:"+identifyType+",identifyNo:"+identifyNo+",phoneNumber:"+phoneNumber+",promotionNumber:"+promotionNumber+",openId:"+openId+",friendName:"+wxUserName+"],返回报文resJson：["+resJson.toString()+"],错误信息：["+errMsg+"]");
		return resJson;
	}
	/**
	 * 
			* 描述:领取礼品发送验证码
			* @param request
			* @param identifyType 证件类型
			* @param identifyNo 证件号
			* @param licenseNo 车牌号
			* @param phoneNumber 手机号
			* @return
			* @author 赵硕  2018年4月17日 下午3:40:48
	 */
	@RequestMapping(value={"getPhoneVerifyCodeForGetShareGift.do"})
	@ResponseBody
	public JSONObject getPhoneVerifyCodeForGetShareGift(HttpServletRequest request,String identifyType,String identifyNo,String licenseNo,String phoneNumber){
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String errMsg = null;
		String operateType = "getShareGift";
		JSONObject reqData = new JSONObject();
		JSONObject resJson = new JSONObject();//响应Json
		resJson.put("resCode", "0");//默认失败
		try {
			if(StringUtils.isNotBlank(identifyType) && 
					StringUtils.isNotBlank(identifyNo) &&
					    StringUtils.isNotBlank(licenseNo)&&
					         StringUtils.isNotBlank(phoneNumber)){
				reqData.put("identifytype", identifyType);
				reqData.put("identifyno", identifyNo);
				reqData.put("licenseno", licenseNo.toUpperCase());
				reqData.put("phoneNumber", phoneNumber);
				resJson = giftCardService.getPhoneVerifyCode(operateType, reqData.toString());
			}else{
				errMsg = "参数错误";
			}
		} catch (EpiccException e) {
			e.printStackTrace();
			errMsg = e.getMessage();
		}
		if(StringUtils.isNotBlank(errMsg)){
			resJson.put("errMsg", errMsg);//详细错误信息
		}
		logger.info("调用礼品领取发送验证码方法getPhoneVerifyCodeForGetShareGift时间：["+sdf.format(date)+"],请求参数：["+request.getQueryString()+"],返回报文resJson：["+resJson.toString()+"],错误信息：["+errMsg+"]");
		return resJson;
	}
	/**
	 * 
	 * 描述:礼品寄卖通知(1074)
	 * @return
	 * @author 赵硕  2018年6月6日 下午2:41:16
	 */
	@RequestMapping(value={"giftConsignNotice.do"})
	@ResponseBody
	public JSONObject giftConsignNotice(HttpServletRequest request,String promotionNumber,String cardId,String cardPass,String giftName,String giftType,String consignPrice){
		JSONObject resJson = new JSONObject();//响应Json
		resJson.put("resCode", "0");//默认失败
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String errMsg = null;
		String openId =  (String)request.getAttribute(ConstantUtils.OPEN_ID);
		try {
			if(StringUtils.isNotBlank(promotionNumber) &&
			   StringUtils.isNotBlank(cardId) && 
			   StringUtils.isNotBlank(cardPass) &&
			   StringUtils.isNotBlank(giftName) &&
			   StringUtils.isNotBlank(giftType) &&
			   StringUtils.isNotBlank(consignPrice)){
				GiftConsignVo vo = new GiftConsignVo();
				vo.setCardId(cardId);
				vo.setCardPass(cardPass);
				vo.setConsignPrice(consignPrice);
				vo.setGiftName(giftName);
				vo.setGiftType(giftType);
				vo.setPromotionNumber(promotionNumber);
				vo.setOpenId(openId);
				giftCardService.consignGift(resJson, vo);	
			}else{
				errMsg = "参数错误";
			}
		} catch (Exception e) {
			e.printStackTrace();
			errMsg = e.getMessage();
		}
		if(StringUtils.isNotBlank(errMsg)){
			resJson.put("errMsg", errMsg);//详细错误信息
		}
		logger.info("调用礼品寄卖通知方法giftConsignNotice时间：["+sdf.format(date)+"],请求参数：["+request.getQueryString()+"],返回报文resJson：["+resJson.toString()+"],错误信息：["+errMsg+"]");
		return resJson;
	}
	
	/**
	 * 
			* 描述:好友测算接口
			* @param request
			* @param licenseNo
			* @param identifyType
			* @param identifyNo
			* @param phoneNumber
			* @param valiDateCode
			* @return
			* @author 朱久满 2018年6月21日 下午4:49:46
	 */
	@RequestMapping(value={"friendRecommend.do"})
	@ResponseBody
	public JSONObject friendRecommend(HttpServletRequest request,String licenseNo,String identifyType,
			                          String identifyNo,String phoneNumber,String valiDateCode,String referrerOpenid){
		JSONObject resJson = new JSONObject();//响应Json
		resJson.put("resCode", "0");//默认失败
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String errMsg = null;
		String openId =  (String)request.getAttribute(ConstantUtils.OPEN_ID);
		try {
			if(StringUtils.isNotBlank(licenseNo) &&
			   StringUtils.isNotBlank(identifyType) && 
			   StringUtils.isNotBlank(identifyNo) &&
			   StringUtils.isNotBlank(phoneNumber) &&
			   StringUtils.isNotBlank(valiDateCode) &&
			   StringUtils.isNotBlank(referrerOpenid) &&
			   StringUtils.isNotBlank(openId)){
				FriendRecommendVo vo = new FriendRecommendVo();
				  vo.setIdentifyNo(identifyNo);
				  vo.setIdentifyType(identifyType);
				  vo.setLicenseNo(licenseNo);
				  vo.setMeasurependid(openId);
				  vo.setPhoneNo(phoneNumber);
				  vo.setReferrerOpenid(CommonUtils.decodeOpenId(referrerOpenid));
				  vo.setVerifyCode(valiDateCode);
				giftCardService.friendRecommendSer(resJson, vo);	
			}else{
				errMsg = "参数错误";
			}
		} catch (Exception e) {
			e.printStackTrace();
			errMsg = e.getMessage();
		}
		if(StringUtils.isNotBlank(errMsg)){
			resJson.put("errMsg", errMsg);//详细错误信息
		}
		logger.info("调用好友测算接口方法friendRecommend时间：["+sdf.format(date)+"],请求参数：["+request.getQueryString()+"],返回报文resJson：["+resJson.toString()+"],错误信息：["+errMsg+"]");
		return resJson;
		
	}
	
	

	/**
	 * 
			* 描述:非人保好友测算接口
			* @param request
			* @param licenseNo
			* @param identifyType
			* @param identifyNo
			* @param phoneNumber
			* @param valiDateCode
			* @return
			* @author 朱久满 2018年6月21日 下午4:49:46
	 */
	@RequestMapping(value={"noNmemberRecommend.do"})
	@ResponseBody
	public JSONObject noNmemberRecommend(HttpServletRequest request,String licenseNo,String identifyType,
			                          String identifyNo,String phoneNumber,String referrerOpenid,
			                          String chassisNumber,String debutDate ,String engineNo ,String owner){
		JSONObject resJson = new JSONObject();//响应Json
		resJson.put("resCode", "0");//默认失败
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String errMsg = null;
		String openId =  (String)request.getAttribute(ConstantUtils.OPEN_ID);
		try {
			if(StringUtils.isNotBlank(licenseNo) &&
			   StringUtils.isNotBlank(phoneNumber) &&
			   StringUtils.isNotBlank(referrerOpenid) &&
			   StringUtils.isNotBlank(openId)  &&	
			   StringUtils.isNotBlank(chassisNumber) &&
			   StringUtils.isNotBlank(debutDate) &&
			   StringUtils.isNotBlank(engineNo) &&
			   StringUtils.isNotBlank(owner) 
					){
				DrivingInfoVo vo = new DrivingInfoVo();
				  vo.setChassisNumber(chassisNumber);
				  vo.setDebutDate(debutDate);
				  vo.setEngineNo(engineNo);
				  vo.setLicenseNo(licenseNo);
				  vo.setMeasureOpendid(openId);
				  vo.setOwner(owner);
				  vo.setPhoneNo(phoneNumber);
				  vo.setReferrerOpenid(CommonUtils.decodeOpenId(referrerOpenid));
				giftCardService.noNmemberRecommendSer(resJson, vo);	
			}else{
				errMsg = "参数错误";
			}
		} catch (Exception e) {
			e.printStackTrace();
			errMsg = e.getMessage();
		}
		if(StringUtils.isNotBlank(errMsg)){
			resJson.put("errMsg", errMsg);//详细错误信息
		}
		logger.info("调用非人保好友测算接口方法noNmemberRecommend时间：["+sdf.format(date)+"],请求参数：["+request.getQueryString()+"],返回报文resJson：["+resJson.toString()+"],错误信息：["+errMsg+"]");
		return resJson;
		
	}
	
}
