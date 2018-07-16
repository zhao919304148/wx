package com.wap.dzsw.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sys.dic.SysDicHelper;
import com.sys.dic.entity.BrandEntity;
import com.sys.exception.EpiccException;
import com.wap.dzsw.entity.BrandKeFu;
import com.wap.dzsw.entity.BrandNetwork;
import com.wap.dzsw.entity.CancellationOrderEntity;
import com.wap.dzsw.entity.MoveTheCar;
import com.wap.dzsw.entity.NetworkQueryVo;
import com.wap.dzsw.entity.NetworkVo;
import com.wap.dzsw.entity.ServiceReOrInquiryEntity;
import com.wap.dzsw.entity.ServiceReservationOrderEntity;
import com.wap.dzsw.entity.UpdateOrder;
import com.wap.dzsw.entity.Verification;
import com.wap.trans.entity.ResponseHeadEntity;
import com.wap.trans.entity.tr_1022.ReqTrans1022Entity;
import com.wap.trans.entity.tr_1022.ResTrans1022Entity;
import com.wap.trans.entity.tr_1023.ReqTrans1023Entity;
import com.wap.trans.entity.tr_1023.ResTrans1023Entity;
import com.wap.trans.entity.tr_1024.ReqTrans1024Entity;
import com.wap.trans.entity.tr_1024.ResTrans1024Entity;
import com.wap.trans.entity.tr_1025.ReqTrans1025Entity;
import com.wap.trans.entity.tr_1025.ResTrans1025Entity;
import com.wap.trans.entity.tr_1026.ReqTrans1026Entity;
import com.wap.trans.entity.tr_1026.ResTrans1026Entity;
import com.wap.trans.entity.tr_1027.ReqTrans1027Entity;
import com.wap.trans.entity.tr_1027.ResTrans1027Entity;
import com.wap.trans.entity.tr_1042.ReqTrans1042Entity;
import com.wap.trans.entity.tr_1042.ResTrans1042Entity;
import com.wap.trans.entity.tr_1043.ReqTrans1043Entity;
import com.wap.trans.entity.tr_1043.ResTrans1043Entity;
import com.wap.trans.entity.tr_1045.ReqTrans1045Entity;
import com.wap.trans.entity.tr_1045.ResTrans1045Entity;
import com.wap.trans.entity.tr_1060.ReqTrans1060Entity;
import com.wap.trans.entity.tr_1060.ResTrans1060Entity;
import com.wap.trans.entity.tr_1063.ReqTrans1063Entity;
import com.wap.trans.entity.tr_1063.ResTrans1063Entity;
import com.wap.trans.entity.tr_1064.ReqTrans1064Entity;
import com.wap.trans.entity.tr_1064.ResTrans1064Entity;
import com.wap.trans.entity.tr_1065.ReqTrans1065Entity;
import com.wap.trans.entity.tr_1065.ResTrans1065Entity;
import com.wap.trans.entity.tr_1067.ReqTrans1067Entity;
import com.wap.trans.entity.tr_1067.ResTrans1067Entity;
import com.wap.trans.service.TransService;
import com.wap.util.TransUtil;
import com.wap.wx_interface.entity.custom.DzmRegistVo;
import com.wap.wx_interface.entity.custom.MaintainOrderRequest;
import com.wap.wx_interface.entity.custom.ValidateWashCodeRequestMessageVo;
import com.wap.wx_interface.entity.custom.YmOrderVo;

import core.db.dao.IBaseService;

@Service("fuWuService")
public class FuWuService<E> {

	private static final Log logger = LogFactory.getLog(FuWuService.class);
	
	@Autowired(required = false)
	private TransService transService = null;
	
	public void setTransService(TransService transService) {
		this.transService = transService;
	}
	@Autowired(required = false)
	private IBaseService baseService = null;
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
	@Autowired(required = false)
	private JdbcTemplate jdbcTemplate = null;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private GiftCardService giftCardService;
	
	/**
	 * 
			* 描述:订单提交
			* @param serviceReservationOrderEntity1023
			* @return
			* @throws EpiccException
			* @author 李朝晖 2016年11月22日 上午10:41:10
	 */
	public Map getServiceReOrder(ServiceReservationOrderEntity   serviceReservationOrderEntity) throws EpiccException{
		
		System.out.println("no=="+serviceReservationOrderEntity.getCardNo());
		Map map = new HashMap();
		
		ReqTrans1023Entity reqTrans1023Rntity = new ReqTrans1023Entity();
		TransUtil.copyObject(serviceReservationOrderEntity, reqTrans1023Rntity);  //配值
		System.out.println("openid=="+reqTrans1023Rntity.getOpenId());
		ResTrans1023Entity resTrans1023Entity = transService.executeTrans1023(reqTrans1023Rntity);
		
		if(null == resTrans1023Entity){
			map.put("res", "0");
			map.put("msg", "1023服务预约接口异常！");
			
			return map;
		}
		
		if("0".equals(resTrans1023Entity.getResuestHeadEntity().getResponse_code().trim())){
			map.put("res", "0");
			map.put("msg", resTrans1023Entity.getResuestHeadEntity().getResponse_message().trim());
			return map;
		}
		map.put("res", "1");
		map.put("msg", "成功");
		
		return map;
	}
	
	/**
	 * 
			* 描述:服务预约订单接口查询1024
			* @param serviceReOrInquiry
			* @return
			* @throws EpiccException
			* @author 李朝晖 2016年11月25日 上午11:29:45
	 */
	public Map getSeReOrInquiry(ServiceReOrInquiryEntity  serviceReOrInquiry) throws EpiccException{
		
		Map map = new HashMap();
		if(null == serviceReOrInquiry){
			map.put("res", "0");
			map.put("msg", "请求参数不能为空");
			
			return map;
		}
		
		ReqTrans1024Entity  reqTrans1024Entity   =  new ReqTrans1024Entity();
		TransUtil.copyObject(serviceReOrInquiry, reqTrans1024Entity);   //配值
		ResTrans1024Entity resTrans1024Entity = transService.executeTrans1024(reqTrans1024Entity);
		if("0".equals(resTrans1024Entity)){
			map.put("res", "0");
			map.put("msg", "1024服务预约查询接口异常");
			
			return map;
		}
		if("0".equals(resTrans1024Entity.getResuestHeadEntity().getResponse_code().trim())){
			
			map.put("res", "0");
			map.put("msg", resTrans1024Entity.getResuestHeadEntity().getResponse_message().trim());
			
			return map;
		}
		
		map.put("res", "1");
		map.put("msg", "成功");
		map.put("resTrans1024BasePartEntity", resTrans1024Entity.getResTrans1024BodyEntity().getResTrans1024BasePartEntity());
		
		return map;
		
	}
	
	/**
	 * 
			* 描述:订单取消接口1025
			* @param cancell
			* @return
			* @throws EpiccException
			* @author 李朝晖 2016年12月6日 上午9:59:50
	 */
	public Map getCancellation(CancellationOrderEntity   cancell) throws EpiccException{
		
		Map map = new HashMap();
		
		
		ReqTrans1025Entity reqTrans1025Entity = new ReqTrans1025Entity();
		TransUtil.copyObject(cancell, reqTrans1025Entity);
		ResTrans1025Entity resTrans1025Entity = transService.executeTrans1025(reqTrans1025Entity);
		
		if("0".equals(resTrans1025Entity)){
			map.put("res", "0");
			map.put("mag", "1025服务预约查询接口异常");
		}
		
		if("0".equals(resTrans1025Entity.getResuestHeadEntity().getResponse_code().trim())){
			
			map.put("res", "0");
			map.put("msg", resTrans1025Entity.getResuestHeadEntity().getResponse_message().trim());
			
			return map;
		}
		
		map.put("res", "1");
		map.put("msg", "成功");
		map.put("resTrans1025Entity", resTrans1025Entity);
		
		
		return map;
		
		
	}
	
	
	/**
	 * 
			* 描述:挪车服务接口
			* @param moveTheCar
			* @return
			* @author 李朝晖 2016年12月9日 下午2:21:09
			* @throws EpiccException 
	 */
	public Map getMoveTheCar(MoveTheCar moveTheCar) throws EpiccException{
		
		Map map = new HashMap();
		
		ReqTrans1026Entity reqTrans1026Entity = new ReqTrans1026Entity();
		
		TransUtil.copyObject(moveTheCar, reqTrans1026Entity);
		ResTrans1026Entity resTrans1026Entity = transService.executeTrans1026(reqTrans1026Entity);
		if(null == resTrans1026Entity){
			map.put("res", "1");
			map.put("msg", "1026服务预约接口异常");
			return map;
		}
		if("0".equals(resTrans1026Entity.getResuestHeadEntity().getResponse_code().trim())){
			map.put("res", "1");
			map.put("msg", resTrans1026Entity.getResuestHeadEntity().getResponse_message().trim());
			return map;
		}
		
		map.put("res", "0");
		map.put("msg", "成功");
		
		return map;
		
	}
	
	/**
	 * 
			* 描述: 验证码
			* @param verCation
			* @return
			* @throws EpiccException
			* @author 李朝晖 2017年1月6日 上午11:08:03
	 */
	public Map verification(Verification  verCation) throws EpiccException{
		
		Map map = new HashMap();
		
		ReqTrans1027Entity reqTrans1027Entity = new ReqTrans1027Entity();
		
		TransUtil.copyObject(verCation, reqTrans1027Entity);
		ResTrans1027Entity resTrans1027Entity = transService.executeTrans1027(reqTrans1027Entity);
		if(null == resTrans1027Entity){
			map.put("resCode", "1");
			map.put("msg", "1027服务预约接口异常");
			return map;
		}
		if("0".equals(resTrans1027Entity.getResuestHeadEntity().getResponse_code().trim())){
			map.put("resCode", "1");
			map.put("msg", resTrans1027Entity.getResuestHeadEntity().getResponse_message().trim());
			return map;
		}
		
		map.put("resCode", "0");
		map.put("msg", "成功");
		map.put("ValadateCode;", resTrans1027Entity.getResTrans1027BodyEntity().getResTrans1027BasePartEntity().getValadateCode());
		
		return map;
		
	}
	
	/**
	 * 
			* 描述:  礼品状态修改
			* @param UOrder
			* @return
			* @throws EpiccException
			* @author 李朝晖2016年12月20日 下午3:48:13
	 */

	public Map<String, String> getupdateOrder(UpdateOrder UOrder) throws EpiccException{
		/*if("01".equals(UOrder.getCardStatus())){
			UOrder.setCardStatus("1");
		}else if("02".equals(UOrder.getCardStatus())){
			UOrder.setCardStatus("0");
		}*/
		Map<String, String> map = new HashMap<String, String>();
		ReqTrans1022Entity reqTrans1022Entity = new ReqTrans1022Entity();
		TransUtil.copyObject(UOrder, reqTrans1022Entity);
		ResTrans1022Entity resTrans1022Entity = transService.executeTrans1022(reqTrans1022Entity);
		if(null == resTrans1022Entity){
			map.put("res", "0");
			map.put("msg", "1022服务预约接口异常");
			return map;
		}
		
		if("0".equals(resTrans1022Entity.getResuestHeadEntity().getResponse_code().trim())){
			map.put("res", "0");
			map.put("msg", resTrans1022Entity.getResuestHeadEntity().getResponse_message().trim());
			return map;
		}
		
		map.put("res", "1");
		map.put("msg", "成功");
		
		return map;
		
	}

	/**
	 * 
			* 描述: 客服网点查询
			* @param networkType
			* @throws Exception
			* @author qex2017年1月13日 下午12:05:16
	 */
//	public List<BrandNetwork> getNetworkService(String networkType) throws Exception{
//		List<KeFuNetwork> list = hibernateTemplate.find("from KeFuNetwork where networktype = ? and flag='1' order by bfirstletter",networkType);
//		List<BrandNetwork> list2 = new ArrayList<BrandNetwork>();
//		for (KeFuNetwork keFuNetwork : list) {
//			BrandNetwork brandNetwork = new BrandNetwork();
//			TransUtil.copyObject(keFuNetwork, brandNetwork);
//			brandNetwork.setNetworkPhone(keFuNetwork.getNetworkPhnoe());
//			list2.add(brandNetwork);
//		}
//		return list2;
//	}

//	public List<BrandKeFu> getNetworkBrand(String networkType) throws Exception{
//		baseService.evictObject(KeFuNetwork.class);
//		Map<String,BrandKeFu> map = new HashMap<String, BrandKeFu>();
//		Map<String,KeFuNetwork> map2 = new HashMap<String, KeFuNetwork>();
//		//品牌list
//		List<BrandKeFu> brandList = new ArrayList<BrandKeFu>();
//		//网点list
//		List<KeFuNetwork> list = hibernateTemplate.find("from KeFuNetwork where networktype = ? and flag='1' order by bfirstletter", networkType);
//		//去除重复的品牌
//		for (KeFuNetwork keFuNetwork : list) {
//			BrandKeFu brandKeFu = new BrandKeFu();
//			TransUtil.copyObject(keFuNetwork, brandKeFu);
//			map.put(keFuNetwork.getBrandId(), brandKeFu);
//		}
//		//给每个品牌增加网点信息
//		for(String key : map.keySet()){
////			System.out.println("key:"+key);
//			for (KeFuNetwork keFuNetwork : list) {
//				//品牌id相同，就增加到品牌list
//				if(key.equals(keFuNetwork.getBrandId())){
//					BrandNetwork brandNetwork = new BrandNetwork();
//					TransUtil.copyObject(keFuNetwork, brandNetwork);
//					brandNetwork.setNetworkPhone(keFuNetwork.getNetworkPhnoe());
//					List<BrandNetwork> dataList = map.get(key).getDataList();
//					if(dataList==null){
//						dataList = new ArrayList<BrandNetwork>();
//						map.get(key).setDataList(dataList);
//					}
//					dataList.add(brandNetwork);
//				}
//				continue;
//			}
//			System.out.println("map.get(key).getDataList():"+map.get(key).getDataList());
//			System.out.println("map.get(key):"+map.get(key));
//			brandList.add(map.get(key));
//		}
//		ComparatorChain comparatorChain = new ComparatorChain();
//		comparatorChain.addComparator(new BeanComparator("bfirstletter"), false);
//		Collections.sort(brandList,comparatorChain);
//		return brandList;
//	}
	/**
	 * 
			* 描述:喷漆 品牌网点
			* @param networkType
			* @author qex 2017年3月2日 下午1:21:39
	 */
	public List<BrandKeFu> getNetworkBrandPenQi(String networkType) {
		List<BrandKeFu> list = new ArrayList<BrandKeFu>();
		List<BrandEntity> brandList = SysDicHelper.getInstance().getBrandList();
		NetworkQueryVo queryVo = new NetworkQueryVo();
		queryVo.setNetworkType(networkType);
		//所有喷漆网点
		List<NetworkVo> networkList = 	giftCardService.getNetwork(queryVo);
		//遍历品牌，将网点和品牌绑定
		for (BrandEntity brand : brandList) {
			//前台数据结构
			BrandKeFu brandKeFu = new BrandKeFu();
			brandKeFu.setBfirstletter(brand.getBfirstletter());
			brandKeFu.setBrandId(brand.getBrandid());
			brandKeFu.setBrandName(brand.getBrandName());
			List<BrandNetwork> dataList = new ArrayList<BrandNetwork>();
			brandKeFu.setDataList(dataList );
			for (NetworkVo ntw : networkList) {
				if(brand.getBrandid().equals(ntw.getBrandId())||"0".equals(ntw.getBrandId())){
					BrandNetwork bntw = new BrandNetwork();
					TransUtil.copyObject(ntw, bntw);
					dataList.add(bntw);
				}
			}
			list.add(brandKeFu);
		}
		ComparatorChain comparatorChain = new ComparatorChain();
		comparatorChain.addComparator(new BeanComparator("bfirstletter"), false);
		Collections.sort(list,comparatorChain);
		return list;
	}
	/**
	 * 
			* 描述:玻璃网点
			* @param networkType
			* @return
			* @author 权恩喜 2017年3月2日 下午1:52:34
	 */
	public List<BrandNetwork> getNetworkServiceBoLi(String networkType) {
		NetworkQueryVo queryVo = new NetworkQueryVo();
		queryVo.setNetworkType(networkType);
		List<NetworkVo> networkList = giftCardService.getNetwork(queryVo);
		List<BrandNetwork> list = new ArrayList<BrandNetwork>();
		for (NetworkVo ntw : networkList) {
			BrandNetwork brandNetwork = new BrandNetwork();
			TransUtil.copyObject(ntw, brandNetwork);
			brandNetwork.setNetworkPhone(ntw.getNetworkPhone());
			list.add(brandNetwork);
		}
		return list;
	}
	/**
	 * 
			* 描述:
			* 校验洗车码
			* 接口1042
			* @param messageVo
			* @return
			* @author 许宝众 2017年7月11日 下午12:34:32
	 */
	public com.alibaba.fastjson.JSONObject validateWashCarCode(ValidateWashCodeRequestMessageVo messageVo) {
		com.alibaba.fastjson.JSONObject resMap = new com.alibaba.fastjson.JSONObject();
		String resCode="resCode";
		String errMsg = "errMsg";
		resMap.put(resCode,"-99");
		resMap.put(errMsg,"未知错误");
		
		ReqTrans1042Entity reqTrans1042Entity = new ReqTrans1042Entity();
		TransUtil.copyObject(messageVo, reqTrans1042Entity);
		logger.info("reqTrans1042Entity:"+reqTrans1042Entity.toString());
		ResTrans1042Entity executeTrans1042=null;
		try {
			executeTrans1042 = transService.executeTrans1042(reqTrans1042Entity);
			ResponseHeadEntity resHeadEntity = executeTrans1042.getResHeadEntity();
			String response_code = resHeadEntity.getResponse_code();
			String response_message = resHeadEntity.getResponse_message();
			if("1".equals(response_code.trim())){
				resMap.put(resCode,"1");
				resMap.put(errMsg,"成功");
			}else{
				resMap.put(resCode,response_code);
				resMap.put(errMsg,response_message);
			}
		} catch (EpiccException e) {
			logger.info("1042交易服务异常：",e);
		}
		return resMap;
	}
	/**
	 * 
			* 描述:查询人保洗车订单
			* @param messageVo
			* 		三方订单号、服务商代码
			* @return
			* @author 许宝众2017年7月19日 上午11:12:27
	 */
	public com.alibaba.fastjson.JSONObject getWashCarOrder(ValidateWashCodeRequestMessageVo messageVo) {
		com.alibaba.fastjson.JSONObject resJson = new com.alibaba.fastjson.JSONObject();
		String resCode="resCode";
		String errMsg = "errMsg";
		resJson.put(resCode,"-99");
		resJson.put(errMsg,"未知错误");
		
		ReqTrans1043Entity reqTrans1043Entity = new ReqTrans1043Entity();
		reqTrans1043Entity.setCompanyCode(messageVo.getCompanyCode());
		reqTrans1043Entity.setThirdOrderId(messageVo.getThirdOrderId());
		logger.info("reqTrans1043Entity:"+reqTrans1043Entity.toString());
		ResTrans1043Entity executeTrans1043=null;
		try {
			executeTrans1043 = transService.executeTrans1043(reqTrans1043Entity);
			ResponseHeadEntity resHeadEntity = executeTrans1043.getResHeadEntity();
			String response_code = resHeadEntity.getResponse_code();
			String response_message = resHeadEntity.getResponse_message();
			if("1".equals(response_code.trim())){
				resJson.put(resCode,"1");
				resJson.put("orderInfo", JSON.toJSON(executeTrans1043.getBodyEntity()));
			}else{
				resJson.put(resCode,response_code);
				resJson.put(errMsg,response_message);
			}
		} catch (EpiccException e) {
			logger.info("1043交易服务异常：",e);
		}
		return resJson;
	}
	
	/**
	 * 亿美礼品兑换同步卡号状态
			* 描述:亿美礼品兑换同步卡号状态
			* @param yOrderVo
			* @return
			* @author 朱久满 2017年7月30日 上午10:46:30
	 */
	public com.alibaba.fastjson.JSONObject updateYmOrderStatus(YmOrderVo ymOrderVo) {
		com.alibaba.fastjson.JSONObject resJson = new com.alibaba.fastjson.JSONObject();
		String resCode="resCode";
		String errMsg = "errMsg";
		resJson.put(resCode,"-99");
		resJson.put(errMsg,"未知错误");
		
		ReqTrans1045Entity reqTrans1045Entity = new ReqTrans1045Entity();
		reqTrans1045Entity.setMobile(ymOrderVo.getMobile());;
		reqTrans1045Entity.setGiftType(ymOrderVo.getGiftType());
		reqTrans1045Entity.setCardNo(ymOrderVo.getCardNo());
		reqTrans1045Entity.setCardStatus(ymOrderVo.getCardStatus());
		reqTrans1045Entity.setYmOrderNo(ymOrderVo.getYmOrderNo());
		reqTrans1045Entity.setOrderAmount(ymOrderVo.getOrderAmount());
		logger.info("reqTrans1045Entity:"+reqTrans1045Entity.toString());
		ResTrans1045Entity executeTrans1045=null;
		try {
			executeTrans1045 = transService.executeTrans1045(reqTrans1045Entity);
			ResponseHeadEntity resHeadEntity = executeTrans1045.getResHeadEntity();
			String response_code = resHeadEntity.getResponse_code();
			String response_message = resHeadEntity.getResponse_message();
			resJson.put(resCode,response_code);
			resJson.put(errMsg,response_message);
		} catch (EpiccException e) {
			logger.info("1045交易服务异常：",e);
		}
		return resJson;
	}
	
	/***
	 *  
	 * 描述:
	 * 处理保养服务商的生成订单请求，校验成功，保存订单返回响应
	 * @param fwsCode
	 * 			服务商代码（落地工号）
	 * @param requestVo
	 * 
	 * @param resJson
	 * @author 许宝众 2017年12月18日 下午1:00:29
	 * @throws EpiccException 
	 */
	public void validateAndSaveMaintainOrder(String fwsCode,MaintainOrderRequest requestVo,com.alibaba.fastjson.JSONObject resJson) throws EpiccException {
		ReqTrans1060Entity reqEntity = new ReqTrans1060Entity();
//		用户id	字符串	Y	微信openId加密后
		String userId = requestVo.getUserId();
//		卡号	字符串	Y	加密卡号
		String cardId = requestVo.getCardId();
//		卡密	字符串	Y	
		String cardPwd = requestVo.getCardPwd();
//		保养方式	字符串	Y	0：上门保养1：到店保养
		String maintainType = requestVo.getMaintainType();
//		服务车牌号	字符串	Y	
		String licenseNo = requestVo.getLicenseNo();
//		客户手机号	字符串	Y	预约手机号
		String userMobile = requestVo.getUserMobile();
//		服务日期	日期	Y	格式：yyyy-MM-dd
		String serviceDate = requestVo.getServiceDate();
//		网点名称	字符串	N	到店保养时有值
		String networkName = requestVo.getNetworkName();
//		服务地址	字符串	N	上门保养时有值
		String serviceAddress = requestVo.getServiceAddress();
//		机油型号	字符串	Y	
		String oilType = requestVo.getOilType();
//		机滤型号		Y	
		String machineType = requestVo.getMachineType();
//		增值服务		N	多值，用英文分号隔开
		String addServices = requestVo.getAddServices();
//  	第三方订单号    Y
		String thirdOrderNo = requestVo.getThirdOrderNo();
//		是否为后端调用
		String isBackend = requestVo.getIsBackend();
//		 是否为实体卡
		String isRealCard = requestVo.getIsRealCard();
		String openId = requestVo.getUserId();
		String cardNo = requestVo.getCardId();
		reqEntity.setOpenId(openId);
		reqEntity.setCardId(cardNo);
		reqEntity.setFwsCode(fwsCode);
		reqEntity.setPassword(cardPwd);
		reqEntity.setMaintainType(maintainType);
		reqEntity.setLicenseNo(licenseNo);
		reqEntity.setReservePhone(userMobile);
		reqEntity.setServiceDate(serviceDate);
		reqEntity.setNetworkName(networkName);
		reqEntity.setServiceAddress(serviceAddress);
		reqEntity.setOilType(oilType);
		reqEntity.setMachineType(machineType);
		reqEntity.setAddService(addServices);
		reqEntity.setThirdOrderNo(thirdOrderNo);
		reqEntity.setIsBackend(isBackend);
		reqEntity.setIsRealCard(isRealCard);
		logger.info("reqTrans1060Entity:"+JSON.toJSONString(reqEntity));
		ResTrans1060Entity resTrans1060=null;
		try {
			resTrans1060 = transService.executeTrans1060(reqEntity);
			ResponseHeadEntity resHeadEntity = resTrans1060.getHead();
			String response_code = resHeadEntity.getResponse_code();
			String response_message = resHeadEntity.getResponse_message();
			resJson.put("resCode", response_code);
			resJson.put("resMsg", response_message);
			if("1".equals(response_code)){
				//组织成功返回信息
				resJson.put("message", com.alibaba.fastjson.JSONObject.toJSON(resTrans1060.getBody().getBasePart()));
			}
		} catch (EpiccException e) {
			logger.info("1060交易服务异常：",e);
			throw e;
		}
	}
	
	/**
	 * 
			* 描述:
			* @param comCode
			* @param requestVo
			* @param resJson
			* @author  2018年4月2日 下午3:48:26
	 * @throws EpiccException 
	 */
	public void validateAndUpdateMaintainOrder(String comCode,
			MaintainOrderRequest requestVo,
			com.alibaba.fastjson.JSONObject resJson) throws EpiccException {
		ReqTrans1067Entity reqEntity = new ReqTrans1067Entity();
//		我方订单号
		String orderNo = requestVo.getOrderNo();
//		保养方式
		String maintainType = requestVo.getMaintainType();
//		服务车牌号	字符串	Y	
		String licenseNo = requestVo.getLicenseNo();
//		客户手机号	字符串	Y	预约手机号
		String userMobile = requestVo.getUserMobile();
//		服务日期	日期	Y	格式：yyyy-MM-dd
		String serviceDate = requestVo.getServiceDate();
//		网点名称	字符串	N	到店保养时有值
		String networkName = requestVo.getNetworkName();
//		服务地址	字符串	N	上门保养时有值
		String serviceAddress = requestVo.getServiceAddress();
//		机油型号	字符串	Y	
		String oilType = requestVo.getOilType();
//		机滤型号		Y	
		String machineType = requestVo.getMachineType();
//		增值服务		N	多值，用英文分号隔开
		String addServices = requestVo.getAddServices();
		reqEntity.setOrderNo(orderNo);
		reqEntity.setMaintainType(maintainType);
		reqEntity.setLicenseNo(licenseNo);
		reqEntity.setReservePhone(userMobile);
		reqEntity.setServiceDate(serviceDate);
		reqEntity.setNetworkName(networkName);
		reqEntity.setServiceAddress(serviceAddress);
		reqEntity.setOilType(oilType);
		reqEntity.setMachineType(machineType);
		reqEntity.setAddService(addServices);
		reqEntity.setFwsCode(comCode);
		logger.info("reqTrans1067Entity:"+JSON.toJSONString(reqEntity));
		ResTrans1067Entity resTrans1067=null;
		try {
			resTrans1067 = transService.genericExecuteTrans("1067", reqEntity, ResTrans1067Entity.class);
			ResponseHeadEntity resHeadEntity = resTrans1067.getHead();
			String response_code = resHeadEntity.getResponse_code();
			String response_message = resHeadEntity.getResponse_message();
			resJson.put("resCode", response_code);
			resJson.put("resMsg", response_message);
		} catch (EpiccException e) {
			logger.info("1067交易服务异常：",e);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
			* 描述: 订单取消接口
			* @param comCode
			* 			服务商代码
			* @param thirdOrderNo
			* 			三方订单号
			* @param resJson
			* @author han 2018年1月30日 下午1:49:57
	 */
	public void cancelMaintainOrder(String comCode,
			String thirdOrderNo,
			com.alibaba.fastjson.JSONObject resJson)throws EpiccException {
		ReqTrans1063Entity reqEntity = new ReqTrans1063Entity();
		reqEntity.setComCode(comCode);
		reqEntity.setThirdOrderNo(thirdOrderNo);
		logger.info("reqTrans1063Entity:"+JSON.toJSONString(reqEntity));
		ResTrans1063Entity resTrans1063=null;
		try {
			resTrans1063 = transService.executeTrans1063(reqEntity);
			ResponseHeadEntity resHeadEntity = resTrans1063.getResuestHeadEntity();
			String response_code = resHeadEntity.getResponse_code();
			String response_message = resHeadEntity.getResponse_message();
			resJson.put("resCode", response_code);
			resJson.put("resMsg", response_message);
		} catch (EpiccException e) {
			logger.info("1063交易服务异常：",e);
			throw e;
		}
	}
	/**
	 * 
	 * 描述:电子码注册
	 * @param dzmRegist
	 * @param resJson
	 * @throws Exception
	 * @author 赵硕  2018年1月30日 下午4:14:04
	 */
	public void cardRegist(DzmRegistVo dzmRegist,com.alibaba.fastjson.JSONObject resJson)throws Exception{
		ReqTrans1064Entity reqTrans1064Entity = new ReqTrans1064Entity();
		TransUtil.copyObject(dzmRegist, reqTrans1064Entity);
		logger.info("reqTrans1064Entity: "+com.alibaba.fastjson.JSONObject.toJSONString(reqTrans1064Entity));
		try {
		    ResTrans1064Entity  resTrans1064Entity= transService.executeTrans1064(reqTrans1064Entity);
			ResponseHeadEntity resHeadEntity = resTrans1064Entity.getResuestHeadEntity();
			String response_code = resHeadEntity.getResponse_code();
			String response_message = resHeadEntity.getResponse_message();
			resJson.put("resCode", response_code);
			resJson.put("resMsg", response_message);
		} catch (EpiccException e) {
			logger.info("1064交易服务异常：",e);
			throw e;
		}
		
	}
	/**
	 * 
	 * 描述:交通事故快速处理协议书
	 * @param emailAddress
	 * @param resJson
	 * @throws Exception
	 * @author 赵硕  2018年2月8日 上午11:11:42
	 */
	public void sendFastProcessingEmail(String openId,String emailAddress,JSONObject resJson)throws Exception{
		ReqTrans1065Entity reqTrans1065Entity = new ReqTrans1065Entity();
		reqTrans1065Entity.setEmailAddress(emailAddress);
		reqTrans1065Entity.setOpenId(openId);	
		logger.info("reqTrans1065Entity: "+com.alibaba.fastjson.JSONObject.toJSONString(reqTrans1065Entity));
		try {
		    ResTrans1065Entity  resTrans1065Entity= transService.executeTrans1065(reqTrans1065Entity);
			ResponseHeadEntity resHeadEntity = resTrans1065Entity.getResuestHeadEntity();
			String response_code = resHeadEntity.getResponse_code();
			String response_message = resHeadEntity.getResponse_message();
			resJson.put("resCode", response_code);
			resJson.put("resMsg", response_message);
		} catch (EpiccException e) {
			logger.info("1065交易服务异常：",e);
			throw e;
		}
	}
	
}
