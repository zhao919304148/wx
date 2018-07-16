package com.wap.trans.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commman.trademanager.entity.DataQueueEntity;
import com.commman.trademanager.entity.TradeEntity;
import com.commman.webservice.AoToQuotCallRemoteService;
import com.sys.exception.EpiccException;
import com.wap.trans.entity.tr_1001.ReqTrans1001Entity;
import com.wap.trans.entity.tr_1001.ResTrans1001Entity;
import com.wap.trans.entity.tr_1002.ReqTrans1002Entity;
import com.wap.trans.entity.tr_1002.ResTrans1002Entity;
import com.wap.trans.entity.tr_1003.ReqTrans1003Entity;
import com.wap.trans.entity.tr_1003.ResTrans1003Entity;
import com.wap.trans.entity.tr_1004.ReqTrans1004Entity;
import com.wap.trans.entity.tr_1004.ResTrans1004Entity;
import com.wap.trans.entity.tr_1005.ReqTrans1005Entity;
import com.wap.trans.entity.tr_1005.ReqTrans1005FileEntity;
import com.wap.trans.entity.tr_1005.ResTrans1005Entity;
import com.wap.trans.entity.tr_1006.ReqTrans1006Entity;
import com.wap.trans.entity.tr_1006.ResTrans1006Entity;
import com.wap.trans.entity.tr_1007.ReqTrans1007Entity;
import com.wap.trans.entity.tr_1007.ResTrans1007Entity;
import com.wap.trans.entity.tr_1008.ReqTrans1008Entity;
import com.wap.trans.entity.tr_1008.ResTrans1008Entity;
import com.wap.trans.entity.tr_1009.ReqTrans1009Entity;
import com.wap.trans.entity.tr_1009.ResTrans1009Entity;
import com.wap.trans.entity.tr_1010.ReqTrans1010Entity;
import com.wap.trans.entity.tr_1010.ResTrans1010Entity;
import com.wap.trans.entity.tr_1011.ReqTrans1011Entity;
import com.wap.trans.entity.tr_1011.ResTrans1011Entity;
import com.wap.trans.entity.tr_1012.ReqTrans1012Entity;
import com.wap.trans.entity.tr_1012.ReqTrans1012FileEntity;
import com.wap.trans.entity.tr_1012.ResTrans1012Entity;
import com.wap.trans.entity.tr_1013.ReqTrans1013Entity;
import com.wap.trans.entity.tr_1013.ResTrans1013Entity;
import com.wap.trans.entity.tr_1014.ReqTrans1014Entity;
import com.wap.trans.entity.tr_1014.ResTrans1014Entity;
import com.wap.trans.entity.tr_1015.ReqTrans1015Entity;
import com.wap.trans.entity.tr_1015.ResTrans1015Entity;
import com.wap.trans.entity.tr_1016.ReqTrans1016Entity;
import com.wap.trans.entity.tr_1016.ResTrans1016Entity;
import com.wap.trans.entity.tr_1017.ReqTrans1017CzEntity;
import com.wap.trans.entity.tr_1017.ReqTrans1017Entity;
import com.wap.trans.entity.tr_1017.ReqTrans1017ExchangeEntity;
import com.wap.trans.entity.tr_1017.ResTrans1017Entity;
import com.wap.trans.entity.tr_1018.ReqTrans1018Entity;
import com.wap.trans.entity.tr_1018.ResTrans1018Entity;
import com.wap.trans.entity.tr_1020.ReqTrans1020Entity;
import com.wap.trans.entity.tr_1020.ResTrans1020Entity;
import com.wap.trans.entity.tr_1021.ReqTrans1021Entity;
import com.wap.trans.entity.tr_1021.ResTrans1021Entity;
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
import com.wap.trans.entity.tr_1028.ReqTrans1028Entity;
import com.wap.trans.entity.tr_1028.ResTrans1028Entity;
import com.wap.trans.entity.tr_1029.ReqTrans1029Entity;
import com.wap.trans.entity.tr_1029.ResTrans1029Entity;
import com.wap.trans.entity.tr_1029.ScoreOrder;
import com.wap.trans.entity.tr_1030.ReqTrans1030Entity;
import com.wap.trans.entity.tr_1030.ResTrans1030Entity;
import com.wap.trans.entity.tr_1031.ReqTrans1031Entity;
import com.wap.trans.entity.tr_1031.ResTrans1031Entity;
import com.wap.trans.entity.tr_1032.ReqTrans1032Entity;
import com.wap.trans.entity.tr_1032.ResTrans1032Entity;
import com.wap.trans.entity.tr_1033.ReqTrans1033Entity;
import com.wap.trans.entity.tr_1033.ResTrans1033Entity;
import com.wap.trans.entity.tr_1034.ReqTrans1034Entity;
import com.wap.trans.entity.tr_1034.ResTrans1034Entity;
import com.wap.trans.entity.tr_1035.ReqTrans1035Entity;
import com.wap.trans.entity.tr_1035.ResTrans1035Entity;
import com.wap.trans.entity.tr_1036.ReqTrans1036Entity;
import com.wap.trans.entity.tr_1036.ResTrans1036Entity;
import com.wap.trans.entity.tr_1037.ReqTrans1037Entity;
import com.wap.trans.entity.tr_1037.ReqTrans1037FileEntity;
import com.wap.trans.entity.tr_1037.ResTrans1037Entity;
import com.wap.trans.entity.tr_1038.ReqTrans1038Entity;
import com.wap.trans.entity.tr_1038.ResTrans1038Entity;
import com.wap.trans.entity.tr_1039.ReqTrans1039Entity;
import com.wap.trans.entity.tr_1039.ResTrans1039Entity;
import com.wap.trans.entity.tr_1040.ReqTrans1040Entity;
import com.wap.trans.entity.tr_1040.ResTrans1040Entity;
import com.wap.trans.entity.tr_1041.ReqTrans1041Entity;
import com.wap.trans.entity.tr_1041.ResTrans1041Entity;
import com.wap.trans.entity.tr_1042.ReqTrans1042Entity;
import com.wap.trans.entity.tr_1042.ResTrans1042Entity;
import com.wap.trans.entity.tr_1043.ReqTrans1043Entity;
import com.wap.trans.entity.tr_1043.ResTrans1043Entity;
import com.wap.trans.entity.tr_1044.ReqTrans1044Entity;
import com.wap.trans.entity.tr_1044.ResTrans1044Entity;
import com.wap.trans.entity.tr_1045.ReqTrans1045Entity;
import com.wap.trans.entity.tr_1045.ResTrans1045Entity;
import com.wap.trans.entity.tr_1046.ReqTrans1046Entity;
import com.wap.trans.entity.tr_1046.ResTrans1046Entity;
import com.wap.trans.entity.tr_1047.ReqTrans1047Entity;
import com.wap.trans.entity.tr_1047.ResTrans1047Entity;
import com.wap.trans.entity.tr_1048.ReqTrans1048Entity;
import com.wap.trans.entity.tr_1048.ResTrans1048Entity;
import com.wap.trans.entity.tr_1049.ReqTrans1049CarDataEntity;
import com.wap.trans.entity.tr_1049.ReqTrans1049Entity;
import com.wap.trans.entity.tr_1049.ResTrans1049Entity;
import com.wap.trans.entity.tr_1050.ReqTrans1050Entity;
import com.wap.trans.entity.tr_1050.ResTrans1050Entity;
import com.wap.trans.entity.tr_1051.ReqTrans1051Entity;
import com.wap.trans.entity.tr_1051.ResTrans1051Entity;
import com.wap.trans.entity.tr_1052.ReqTrans1052Entity;
import com.wap.trans.entity.tr_1052.ResTrans1052Entity;
import com.wap.trans.entity.tr_1053.ReqTrans1053Entity;
import com.wap.trans.entity.tr_1053.ResTrans1053Entity;
import com.wap.trans.entity.tr_1054.ReqTrans1054BodyEntity;
import com.wap.trans.entity.tr_1054.ReqTrans1054RequestEntity;
import com.wap.trans.entity.tr_1054.ResTrans1054Entity;
import com.wap.trans.entity.tr_1055.ReqTrans1055BodyEntity;
import com.wap.trans.entity.tr_1055.ReqTrans1055RequestEntity;
import com.wap.trans.entity.tr_1055.ResTrans1055Entity;
import com.wap.trans.entity.tr_1056.ReqTrans1056BodyEntity;
import com.wap.trans.entity.tr_1056.ReqTrans1056RequestEntity;
import com.wap.trans.entity.tr_1056.ResTrans1056Entity;
import com.wap.trans.entity.tr_1057.ReqTrans1057Entity;
import com.wap.trans.entity.tr_1057.ResTrans1057Entity;
import com.wap.trans.entity.tr_1058.ReqTrans1058Entity;
import com.wap.trans.entity.tr_1058.ResTrans1058Entity;
import com.wap.trans.entity.tr_1059.ReqTrans1059Entity;
import com.wap.trans.entity.tr_1059.ResTrans1059Entity;
import com.wap.trans.entity.tr_1060.ReqTrans1060Entity;
import com.wap.trans.entity.tr_1060.ResTrans1060Entity;
import com.wap.trans.entity.tr_1061.ReqTrans1061Entity;
import com.wap.trans.entity.tr_1061.ResTrans1061Entity;
import com.wap.trans.entity.tr_1062.ReqTrans1062Entity;
import com.wap.trans.entity.tr_1062.ResTrans1062Entity;
import com.wap.trans.entity.tr_1063.ReqTrans1063Entity;
import com.wap.trans.entity.tr_1063.ResTrans1063Entity;
import com.wap.trans.entity.tr_1064.ReqTrans1064Entity;
import com.wap.trans.entity.tr_1064.ResTrans1064Entity;
import com.wap.trans.entity.tr_1065.ReqTrans1065Entity;
import com.wap.trans.entity.tr_1065.ResTrans1065Entity;
import com.wap.trans.entity.tr_1066.ReqTrans1066Entity;
import com.wap.trans.entity.tr_1066.ResTrans1066Entity;
import com.wap.trans.entity.tr_1067.ReqTrans1067Entity;
import com.wap.trans.entity.tr_1067.ResTrans1067Entity;
import com.wap.util.TransUtil;
import com.wap.util.XmlUtil;
import com.wx.util.WeixinUtil;

import core.db.dao.IBaseService;

@Service("transService")
public class TransService {
	private static final Log logger = LogFactory.getLog(TransService.class);

	@Autowired(required = false)
	private IBaseService baseService = null;

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	// 打包用 service
	@Autowired(required = false)
	private static PackHeadService packHeadService = new PackHeadService();

	public void setPackHeadService(PackHeadService packHeadService) {
		this.packHeadService = packHeadService;
	}
	/**
	 * 
			* 描述:1001 落地用户登录验证接口
			* @param reqTrans1001Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午3:53:30
	 */
	public ResTrans1001Entity executeTrans1001(ReqTrans1001Entity reqTrans1001Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1001"));
		TransUtil.ObjToMap(reqTrans1001Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1001Entity resTrans1001Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1001",
				toHostData);
		try {
			resTrans1001Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1001Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1001接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1001Entity;
	}
	
	/**
	 * 
			* 描述:1002 配送单列表查询接口
			* @param reqTrans1002Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午4:08:30
	 */
	public ResTrans1002Entity executeTrans1002(ReqTrans1002Entity reqTrans1002Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1002"));
		TransUtil.ObjToMap(reqTrans1002Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1002Entity resTrans1002Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1002",toHostData);
		try {
			resTrans1002Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1002Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1002接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1002Entity;
	}
	
	/**
	 * 
			* 描述:1003 配送单详情查询接口
			* @param reqTrans1003Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午4:09:30
	 */
	public ResTrans1003Entity executeTrans1003(ReqTrans1003Entity reqTrans1003Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1003"));
		TransUtil.ObjToMap(reqTrans1003Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1003Entity resTrans1003Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1003",
				toHostData);
		try {
			resTrans1003Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1003Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1003接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1003Entity;
	}
	
	/**
	 * 
			* 描述:1004 配送单修改接口
			* @param reqTrans1004Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午4:11:13
	 */
	public ResTrans1004Entity executeTrans1004(ReqTrans1004Entity reqTrans1004Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1004"));
		TransUtil.ObjToMap(reqTrans1004Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1004Entity resTrans1004Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1004",
				toHostData);
		try {
			resTrans1004Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1004Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1004接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1004Entity;
	}
	
	/**
	 * 
			* 描述:1005 验车照片上传接口
			* @param reqTrans1005Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午4:11:37
	 */
	public ResTrans1005Entity executeTrans1005(ReqTrans1005Entity reqTrans1005Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1005"));
		hashMap.put("ORDERNO", reqTrans1005Entity.getOrderno());
		hashMap.put("USERCODE", reqTrans1005Entity.getUsercode());
		hashMap.put("COMCODE", reqTrans1005Entity.getComcode());
		hashMap.put("ISCARHURT", reqTrans1005Entity.getIscarhurt());
		toHostData.setSingleMap(hashMap);
		
		List<HashMap<String,Object>> list=new LinkedList<HashMap<String,Object>>();
		for (ReqTrans1005FileEntity file : reqTrans1005Entity.getReqTrans1005FileEntityList()) {
			hashMap = new HashMap<String, Object>();
			TransUtil.ObjToMap(file, hashMap);
			list.add(hashMap);
		}
		DataQueueEntity dataQueue=new DataQueueEntity("FILE_DATA",list);
		toHostData.addDataQueue(dataQueue);
		/* 产生交易 */
		ResTrans1005Entity resTrans1005Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1005",
				toHostData);
		try {
			resTrans1005Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1005Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1005接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1005Entity;
	}
	
	/**
	 * 
			* 描述:1006 报案列表查询接口
			* @param reqTrans1006Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午4:11:57
	 */
	public ResTrans1006Entity executeTrans1006(ReqTrans1006Entity reqTrans1006Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1006"));
		TransUtil.ObjToMap(reqTrans1006Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1006Entity resTrans1006Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1006",toHostData);
		try {
			resTrans1006Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1006Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1006接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1006Entity;
	}
	
	/**
	 * 
			* 描述:1007 案件详情查询接口
			* @param reqTrans1007Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午4:12:53
	 */
	public ResTrans1007Entity executeTrans1007(ReqTrans1007Entity reqTrans1007Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1007"));
		TransUtil.ObjToMap(reqTrans1007Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1007Entity resTrans1007Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1007",toHostData);
		try {
			resTrans1007Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1007Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1007接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1007Entity;
	}
	
	/**
	 * 
			* 描述:1008 定损明细 - 换件清单查询接口
			* @param reqTrans1008Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午4:13:15
	 */
	public ResTrans1008Entity executeTrans1008(ReqTrans1008Entity reqTrans1008Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1008"));
		TransUtil.ObjToMap(reqTrans1008Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1008Entity resTrans1008Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1008",toHostData);
		try {
			resTrans1008Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1008Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1008接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1008Entity;
	}
	
	/**
	 * 
			* 描述:1009 定损明细 - 修理费用清单查询接口
			* @param reqTrans1009Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午4:13:58
	 */
	public ResTrans1009Entity executeTrans1009(ReqTrans1009Entity reqTrans1009Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1009"));
		TransUtil.ObjToMap(reqTrans1009Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1009Entity resTrans1009Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1009",toHostData);
		try {
			resTrans1009Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1009Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1009接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1009Entity;
	}
	
	/**
	 * 
			* 描述:1010 定损明细 - 辅料清单查询接口
			* @param reqTrans1010Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午4:14:45
	 */
	public ResTrans1010Entity executeTrans1010(ReqTrans1010Entity reqTrans1010Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1010"));
		TransUtil.ObjToMap(reqTrans1010Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1010Entity resTrans1010Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1010",toHostData);
		try {
			resTrans1010Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1010Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1010接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1010Entity;
	}
	
	/**
	 * 
			* 描述:1011 定损明细 - 待检测零部件清单查询接口
			* @param reqTrans1011Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午4:15:06
	 */
	public ResTrans1011Entity executeTrans1011(ReqTrans1011Entity reqTrans1011Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1011"));
		TransUtil.ObjToMap(reqTrans1011Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1011Entity resTrans1011Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1011",toHostData);
		try {
			resTrans1011Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1011Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1011接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1011Entity;
	}
	
	/**
	 * 
			* 描述:1012 上传索赔材料接口
			* @param reqTrans1012Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午4:15:52
	 */
	public ResTrans1012Entity executeTrans1012(ReqTrans1012Entity reqTrans1012Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1012"));
		TransUtil.ObjToMap(reqTrans1012Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		List<HashMap<String,Object>> list=new LinkedList<HashMap<String,Object>>();
		if(reqTrans1012Entity.getReqTrans1012FileEntityList()!=null){
			for (ReqTrans1012FileEntity file : reqTrans1012Entity.getReqTrans1012FileEntityList()) {
				hashMap = new HashMap<String, Object>();
				TransUtil.ObjToMap(file, hashMap);
				list.add(hashMap);
			}
		}
		DataQueueEntity dataQueue=new DataQueueEntity("FILE_DATA",list);
		toHostData.addDataQueue(dataQueue);
		/* 产生交易 */
		ResTrans1012Entity resTrans1012Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1012",toHostData);
		try {
			resTrans1012Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1012Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1012接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1012Entity;
	}
	
	/**
	 * 
			* 描述:1013 查询索赔材料接口
			* @param reqTrans1013Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午4:16:09
	 */
	public ResTrans1013Entity executeTrans1013(ReqTrans1013Entity reqTrans1013Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1013"));
		TransUtil.ObjToMap(reqTrans1013Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1013Entity resTrans1013Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1013",toHostData);
		try {
			resTrans1013Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1013Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1013接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1013Entity;
	}
	
	/**
	 * 
			* 描述:1014 同步报案数据接口
			* @param reqTrans1014Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2016年3月4日 下午4:11:57
	 */
	public ResTrans1014Entity executeTrans1014(ReqTrans1014Entity reqTrans1014Entity) throws EpiccException {
		ResTrans1014Entity resTrans1014Entity = null;
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1014"));
		TransUtil.ObjToMap(reqTrans1014Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1014",toHostData);
		try {
			resTrans1014Entity = XmlUtil.toBean(fromHostXml, ResTrans1014Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1014接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1014Entity;
	}
	
	/**
	 * 
			* 描述:服务网点查询接口
			* @param reqTrans1015Entity
			* @return
			* @author 朱久满 2016年3月22日 下午2:48:58
	 * @throws EpiccException 
	 */
	public ResTrans1015Entity executeTrans1015(ReqTrans1015Entity reqTrans1015Entity) throws EpiccException {
		ResTrans1015Entity resTrans1015Entity = null;
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1015"));
		TransUtil.ObjToMap(reqTrans1015Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1015",toHostData);
		try {
			resTrans1015Entity = XmlUtil.toBean(fromHostXml, ResTrans1015Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常 ：1015接口交易失败！"+e.getMessage());
		}
		return resTrans1015Entity;
	}
	
	/**
	 * 
			* 描述:1016 客服活动获取保单信息列表接口
			* @param reqTrans1016Entity
			* @return
			* @throws EpiccException
			* @author 吕亮 2016年08月05日 下午11:11:57
	 */
	public ResTrans1016Entity executeTrans1016(ReqTrans1016Entity reqTrans1016Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1016"));
		TransUtil.ObjToMap(reqTrans1016Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1016Entity resTrans1016Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1016",toHostData);
		try {
			resTrans1016Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1016Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1016接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1016Entity;
	}
	
	/**
	 * 
			* 描述:1017 客服活动获取兑换券信息列表接口
			* @param reqTrans1017Entity
			* @return
			* @throws EpiccException
			* @author 吕亮 2016年08月05日 下午11:11:57
	 */
	public ResTrans1017Entity executeTrans1017(ReqTrans1017Entity reqTrans1017Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1017"));
		TransUtil.ObjToMap(reqTrans1017Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		List<HashMap<String,Object>> elist=new LinkedList<HashMap<String,Object>>();
		if(reqTrans1017Entity.getReqTrans1017ExchangeEntityList()!=null){
			for (ReqTrans1017ExchangeEntity exchange : reqTrans1017Entity.getReqTrans1017ExchangeEntityList()) {
				hashMap = new HashMap<String, Object>();
				TransUtil.ObjToMap(exchange, hashMap);
				elist.add(hashMap);
			}
		}
		DataQueueEntity edataQueue=new DataQueueEntity("EXCHANGE_DATA",elist);
		toHostData.addDataQueue(edataQueue);
		
		List<HashMap<String,Object>> clist=new LinkedList<HashMap<String,Object>>();
		if(reqTrans1017Entity.getReqTrans1017CzEntityList()!=null){
			for (ReqTrans1017CzEntity cz : reqTrans1017Entity.getReqTrans1017CzEntityList()) {
				hashMap = new HashMap<String, Object>();
				TransUtil.ObjToMap(cz, hashMap);
				clist.add(hashMap);
			}
		}
		DataQueueEntity cdataQueue=new DataQueueEntity("CZ_DATA",clist);
		toHostData.addDataQueue(cdataQueue);
		
		/* 产生交易 */
		ResTrans1017Entity resTrans1017Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1017",toHostData);
		try {
			resTrans1017Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1017Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1017接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1017Entity;
	}
	
	/**
	 * 
			* 描述:根据保单号查询兑换码及充值卡信息
			* @param reqTrans1018Entity
			* @return
			* @throws EpiccException
			* @author 朱久满 2016年8月22日 下午6:06:47
	 */
	public ResTrans1018Entity executeTrans1018(ReqTrans1018Entity reqTrans1018Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1018"));
		TransUtil.ObjToMap(reqTrans1018Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1018Entity resTrans1018Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1018",toHostData);
		try {
			resTrans1018Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1018Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1018接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1018Entity;
	}
	
	/**
	 * 
			* 描述:1017 客服活动获取兑换券信息列表接口
			* 该方法需要调用接口平台中的另外一个接口方法，以实现绑定有礼功能
			* @param reqTrans1017Entity
			* @return
			* @throws EpiccException
			* @author 吕亮 2016年10月20日 下午11:11:57
	 */
	public ResTrans1017Entity executeTransBoundGetGift(ReqTrans1017Entity reqTrans1017Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1017"));
		TransUtil.ObjToMap(reqTrans1017Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		List<HashMap<String,Object>> elist=new LinkedList<HashMap<String,Object>>();
		if(reqTrans1017Entity.getReqTrans1017ExchangeEntityList()!=null){
			for (ReqTrans1017ExchangeEntity exchange : reqTrans1017Entity.getReqTrans1017ExchangeEntityList()) {
				hashMap = new HashMap<String, Object>();
				TransUtil.ObjToMap(exchange, hashMap);
				elist.add(hashMap);
			}
		}
		DataQueueEntity edataQueue=new DataQueueEntity("EXCHANGE_DATA",elist);
		toHostData.addDataQueue(edataQueue);
		
		List<HashMap<String,Object>> clist=new LinkedList<HashMap<String,Object>>();
		if(reqTrans1017Entity.getReqTrans1017CzEntityList()!=null){
			for (ReqTrans1017CzEntity cz : reqTrans1017Entity.getReqTrans1017CzEntityList()) {
				hashMap = new HashMap<String, Object>();
				TransUtil.ObjToMap(cz, hashMap);
				clist.add(hashMap);
			}
		}
		DataQueueEntity cdataQueue=new DataQueueEntity("CZ_DATA",clist);
		toHostData.addDataQueue(cdataQueue);
		
		/* 产生交易 */
		ResTrans1017Entity resTrans1017Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1019",toHostData);
		try {
			resTrans1017Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1017Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1017接口返回报文解析失败(调用1019接口)！"+e.getMessage());
		}
		return resTrans1017Entity;
	}
	
	/**
	 * 
			* 描述:1020 客服活动绑定有礼已领取礼品人数接口
			* @param reqTrans1020Entity
			* @return
			* @throws EpiccException
			* @author 吕亮 2016年10月27日 上午10:11:57
	 */
	public ResTrans1020Entity executeTrans1020(ReqTrans1020Entity reqTrans1020Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1020"));
		TransUtil.ObjToMap(reqTrans1020Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1020Entity resTrans1020Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1020",toHostData);
		try {
			resTrans1020Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1020Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1020接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1020Entity;
	}

	
	/**
	 * 
			* 描述:1023服务预约订单提交接口请求
			* @param reqTrans1023Entity
			* @return
			* @author 李朝晖 2016年11月22日 上午9:22:04
			* @throws EpiccException 
	 */
	public ResTrans1023Entity  executeTrans1023(ReqTrans1023Entity reqTrans1023Entity) throws EpiccException{
		
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1023"));
		TransUtil.ObjToMap(reqTrans1023Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1023Entity resTrans1023Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1023",toHostData);
		System.out.println("fromHostXml================"+fromHostXml);
		/*String fromHostXml =  "";
		StringBuffer stringBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		stringBuffer.append("<PACKET version=\"1.0\">")
		            .append("<HEAD>")
		            .append("<TRANS_TYPE>1023</TRANS_TYPE>")
		            .append("<RESPONSE_CODE>1</RESPONSE_CODE>")
		            .append("<RESPONSE_MESSAGE>1</RESPONSE_MESSAGE>")
		            .append("<SERIALNO>1</SERIALNO>")
		            .append("</HEAD>")
		    		.append("</PACKET>");
		fromHostXml= stringBuffer.toString();*/
		
		try {
			resTrans1023Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1023Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1023接口返回报文解析失败！"+e.getMessage());
		}
		
		return resTrans1023Entity;
		
	}

	/**
	 * 
			* 描述:
			* @param reqTrans1021Entity
			* @return
			* @author 权恩喜 2016年11月22日 下午1:25:57
			* @throws EpiccException 
	 */
	public ResTrans1021Entity executeTrans1021(ReqTrans1021Entity reqTrans1021Entity) throws EpiccException {
		 TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1021"));
		TransUtil.ObjToMap(reqTrans1021Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1021Entity resTrans1021Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		System.out.println("callRemoteService=="+callRemoteService);
		String  fromHostXml = callRemoteService.aotodoTradeXml("1021",toHostData);
		System.out.println("fromHostXml="+fromHostXml);
		/*StringBuffer stringBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		stringBuffer.append("<PACKET version=\"1.0\">")
		.append("<HEAD>")
		.append("<TRANS_TYPE>1021</TRANS_TYPE>")
		.append("<RESPONSE_CODE>1</RESPONSE_CODE>")
		.append("<RESPONSE_MESSAGE>1</RESPONSE_MESSAGE>")
		.append("<SERIALNO>1</SERIALNO>")
		.append("</HEAD>")
		.append("<BODY>")
		.append("<BASE_PART>")
		.append("<PAGENO>1</PAGENO>")
		.append("<PAGESIZE>2</PAGESIZE>")
		.append("<PAGECOUNT>1</PAGECOUNT>")
		.append("</BASE_PART>")
		.append("<CARD_LIST>")
		
		.append("<CARD_DATA>")
		.append("<CARDNO>1234567890</CARDNO>")
		.append("<CARDPASS>1234567890</CARDPASS>")
		.append("<CARDTYPE>1004</CARDTYPE>")
		.append("<CARDTYPENAME>上门代办验车</CARDTYPENAME>")
		.append("<LICENSENO>京123456</LICENSENO>")
		.append("<PHONENUMBER>13888888888</PHONENUMBER>")
		.append("<VALIDDATE>20161212</VALIDDATE>")
		.append("<CARDSTATUS>1</CARDSTATUS>")
		.append("</CARD_DATA>")
		
		.append("<CARD_DATA>")
		.append("<CARDNO>1234567891</CARDNO>")
		.append("<CARDPASS>1234567890</CARDPASS>")
		.append("<CARDTYPE>1004</CARDTYPE>")
		.append("<CARDTYPENAME>上门代办验车</CARDTYPENAME>")
		.append("<LICENSENO>京123456</LICENSENO>")
		.append("<PHONENUMBER>13888888888</PHONENUMBER>")
		.append("<VALIDDATE>20161212</VALIDDATE>")
		.append("<CARDSTATUS>0</CARDSTATUS>")
		.append("</CARD_DATA>")
		
		.append("<CARD_DATA>")
		.append("<CARDNO>1234567891</CARDNO>")
		.append("<CARDPASS>1234567890</CARDPASS>")
		.append("<CARDTYPE>1010</CARDTYPE>")
		.append("<CARDTYPENAME>上门代办理赔</CARDTYPENAME>")
		.append("<LICENSENO>京123456</LICENSENO>")
		.append("<PHONENUMBER>13888888888</PHONENUMBER>")
		.append("<VALIDDATE>20161212</VALIDDATE>")
		.append("<CARDSTATUS>0</CARDSTATUS>")
		.append("</CARD_DATA>")
		
		.append("<CARD_DATA>")
		.append("<CARDNO>1234567891</CARDNO>")
		.append("<CARDPASS>1234567890</CARDPASS>")
		.append("<CARDTYPE>1010</CARDTYPE>")
		.append("<CARDTYPENAME>上门代办理赔</CARDTYPENAME>")
		.append("<LICENSENO>京123456</LICENSENO>")
		.append("<PHONENUMBER>13888888888</PHONENUMBER>")
		.append("<VALIDDATE>20161212</VALIDDATE>")
		.append("<CARDSTATUS>1</CARDSTATUS>")
		.append("</CARD_DATA>")
		
		.append("<CARD_DATA>")
		.append("<CARDNO>1234567891</CARDNO>")
		.append("<CARDPASS>1234567890</CARDPASS>")
		.append("<CARDTYPE>1011</CARDTYPE>")
		.append("<CARDTYPENAME>现场代办验车</CARDTYPENAME>")
		.append("<LICENSENO>京123456</LICENSENO>")
		.append("<PHONENUMBER>13888888888</PHONENUMBER>")
		.append("<VALIDDATE>20161212</VALIDDATE>")
		.append("<CARDSTATUS>0</CARDSTATUS>")
		.append("</CARD_DATA>")
		
		.append("<CARD_DATA>")
		.append("<CARDNO>1234567890</CARDNO>")
		.append("<CARDPASS>1234567890</CARDPASS>")
		.append("<CARDTYPE>1007</CARDTYPE>")
		.append("<CARDTYPENAME>喷漆修复服务券</CARDTYPENAME>")
		.append("<LICENSENO>京123456</LICENSENO>")
		.append("<PHONENUMBER>13888888888</PHONENUMBER>")
		.append("<VALIDDATE>20161212</VALIDDATE>")
		.append("<CARDSTATUS>0</CARDSTATUS>")
		.append("</CARD_DATA>")
		.append("<CARD_DATA>")
		.append("<CARDNO>1234567890</CARDNO>")
		.append("<CARDPASS>1234567890</CARDPASS>")
		.append("<CARDTYPE>1007</CARDTYPE>")
		.append("<CARDTYPENAME>喷漆修复服务券</CARDTYPENAME>")
		.append("<LICENSENO>京123456</LICENSENO>")
		.append("<PHONENUMBER>13888888888</PHONENUMBER>")
		.append("<VALIDDATE>20161212</VALIDDATE>")
		.append("<CARDSTATUS>1</CARDSTATUS>")
		.append("</CARD_DATA>")
		.append("<CARD_DATA>")
		.append("<CARDNO>1234567890</CARDNO>")
		.append("<CARDPASS>1234567890</CARDPASS>")
		.append("<CARDTYPE>1009</CARDTYPE>")
		.append("<CARDTYPENAME>玻璃修复服务券</CARDTYPENAME>")
		.append("<LICENSENO>京123456</LICENSENO>")
		.append("<PHONENUMBER>13888888888</PHONENUMBER>")
		.append("<VALIDDATE>20161212</VALIDDATE>")
		.append("<CARDSTATUS>0</CARDSTATUS>")
		.append("</CARD_DATA>")
		
		.append("<CARD_DATA>")
		.append("<CARDNO>1234567890</CARDNO>")
		.append("<CARDPASS>1234567890</CARDPASS>")
		.append("<CARDTYPE>1003</CARDTYPE>")
		.append("<CARDTYPENAME>爱车保养服务券</CARDTYPENAME>")
		.append("<LICENSENO>京123456</LICENSENO>")
		.append("<PHONENUMBER>13888888888</PHONENUMBER>")
		.append("<VALIDDATE>20161212</VALIDDATE>")
		.append("<CARDSTATUS>0</CARDSTATUS>")
		.append("</CARD_DATA>")
		
		.append("</CARD_LIST>")
		.append("</BODY>")
		.append("</PACKET>");
		fromHostXml= stringBuffer.toString();*/
		try {
			resTrans1021Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1021Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1021接口返回报文解析失败！"+e.getMessage());
		}
		
		return resTrans1021Entity;
	}

	/**
	 * 
			* 描述:服务预约订单查询接口
			* @param reqTrans1024Entity
			* @return
			* @throws EpiccException
			* @author 李朝晖 2016年11月25日 上午9:45:21
	 */
	public ResTrans1024Entity executeTrans1024(ReqTrans1024Entity reqTrans1024Entity) throws EpiccException{
		
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1024"));
		TransUtil.ObjToMap(reqTrans1024Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/* 产生交易 */
		ResTrans1024Entity resTrans1024Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1024",toHostData);
		System.out.println("fromHostXml:"+fromHostXml);
	/*	String fromHostXml =  "";
		StringBuffer stringBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		stringBuffer.append("<PACKET version=\"1.0\">")
		            .append("<HEAD>")
		            .append("<TRANS_TYPE>1024</TRANS_TYPE>")
		            .append("<RESPONSE_CODE>1</RESPONSE_CODE>")
		            .append("<RESPONSE_MESSAGE>1</RESPONSE_MESSAGE>")
		            .append("<SERIALNO>1</SERIALNO>")
		            .append("</HEAD>")
		            .append("<BODY>")
		            .append("<BASE_PART>")
		            .append("<ORDERNO>1234567890</ORDERNO>")
		            .append("<FWTYPE>1004</FWTYPE>")
		            .append("<PHONENUMBER>13888888888</PHONENUMBER>")
		            .append("<LICENSENO>京123456</LICENSENO>")
		            .append("<FWADDRESS>北京</FWADDRESS>")
		            .append("<FWDATE>20161220</FWDATE>")
		            .append("<NETWORKNAME>1</NETWORKNAME>")
		            .append("<NETWORKPHONE>123123</NETWORKPHONE>")
		            .append("<NETWORKADDRESS>北京123</NETWORKADDRESS>")
		            .append("<FWSTATUS>1</FWSTATUS>")
		            .append("</BASE_PART>")
		            .append("</BODY>")
		            .append("</PACKET>");
		fromHostXml= stringBuffer.toString();*/
		try {
			resTrans1024Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1024Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1024接口返回报文解析失败！"+e.getMessage());
		}
		
		return resTrans1024Entity;
	}

	/**
	 * 
			* 描述:礼品状态修改
			* @param reqTrans1022Entity
			* @return
			* @author 权恩喜 2016年11月25日 下午1:25:57
			* @throws EpiccException 
	 */
	public ResTrans1022Entity executeTrans1022(ReqTrans1022Entity reqTrans1022Entity) throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1022"));
		TransUtil.ObjToMap(reqTrans1022Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1022Entity resTrans1022Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1022",toHostData);
		
		/*String fromHostXml = "";
		StringBuffer stringBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		stringBuffer.append("<PACKET version=\"1.0\">")
		            .append("<HEAD>")
		            .append("<TRANS_TYPE>1022</TRANS_TYPE>")
		            .append("<RESPONSE_CODE>1</RESPONSE_CODE>")
		            .append("<RESPONSE_MESSAGE>1</RESPONSE_MESSAGE>")
		            .append("<SERIALNO>1</SERIALNO>")
		            .append("</HEAD>")
		            .append("</PACKET>");
		fromHostXml= stringBuffer.toString();*/
		
		try {
			resTrans1022Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1022Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1022接口返回报文解析失败！"+e.getMessage());
		}
		
		return resTrans1022Entity;
	}
	
	/**
	 * 
			* 描述:订单取消接口
			* @param reqTrans1025Entity
			* @return
			* @throws EpiccException
			* @author 李朝晖 2016年12月6日 上午9:27:32
	 */
	public ResTrans1025Entity executeTrans1025(ReqTrans1025Entity reqTrans1025Entity) throws EpiccException{
		
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1025"));
		TransUtil.ObjToMap(reqTrans1025Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/* 产生交易 */
		ResTrans1025Entity resTrans1025Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1025",toHostData);
		
		/*String fromHostXml = "";
		StringBuffer stringBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		stringBuffer.append("<PACKET version=\"1.0\">")
		            .append("<HEAD>")
		            .append("<TRANS_TYPE>1025</TRANS_TYPE>")
		            .append("<RESPONSE_CODE>1</RESPONSE_CODE>")
		            .append("<RESPONSE_MESSAGE>1</RESPONSE_MESSAGE>")
		            .append("<SERIALNO>1</SERIALNO>")
		            .append("</HEAD>")
		            .append("</PACKET>");
		fromHostXml= stringBuffer.toString();*/
		
		try {
			resTrans1025Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1025Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1025接口返回报文解析失败！"+e.getMessage());
		}
		
		return resTrans1025Entity;
		
	}
	/**
	 * 
			* 描述:挪车服务开通接口
			* @param reqTrans1026Entity
			* @return
			* @throws EpiccException
			* @author 李朝晖 2016年12月9日 下午2:06:08
	 */
	public ResTrans1026Entity executeTrans1026(ReqTrans1026Entity reqTrans1026Entity) throws EpiccException{
		
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1026"));
		TransUtil.ObjToMap(reqTrans1026Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/* 产生交易 */
		ResTrans1026Entity resTrans1026Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1026",toHostData);
		
		/*String fromHostXml = "";
		StringBuffer stringBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		stringBuffer.append("<PACKET version=\"1.0\">")
		            .append("<HEAD>")
		            .append("<TRANS_TYPE>1026</TRANS_TYPE>")
		            .append("<RESPONSE_CODE>1</RESPONSE_CODE>")
		            .append("<RESPONSE_MESSAGE>1</RESPONSE_MESSAGE>")
		            .append("<SERIALNO>1</SERIALNO>")
		            .append("</HEAD>")
		            .append("</PACKET>");
		fromHostXml= stringBuffer.toString();*/
		
		try {
			resTrans1026Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1026Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1026接口返回报文解析失败！"+e.getMessage());
		}
		
		return resTrans1026Entity;
	}
	/**
	 * 
			* 描述: 获取验证码
			* @param reqTrans1027Entity
			* @return
			* @throws EpiccException
			* @author qex2017年1月19日 下午3:34:44
	 */
	public ResTrans1027Entity executeTrans1027(ReqTrans1027Entity reqTrans1027Entity) throws EpiccException{
		
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1027"));
		TransUtil.ObjToMap(reqTrans1027Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/* 产生交易 */
		ResTrans1027Entity resTrans1027Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1027",toHostData);
		
		try {
			resTrans1027Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1027Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1027接口返回报文解析失败！"+e.getMessage());
		}
		
		return resTrans1027Entity;
	}
	/**
	 * 
			* 描述:积分余额查询
			* @param reqTrans1028Entity
			* @return
			* @throws EpiccException
			* @author qex 2017年1月19日 下午3:35:06
	 */
	public ResTrans1028Entity executeTrans1028(ReqTrans1028Entity reqTrans1028Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1028"));
		TransUtil.ObjToMap(reqTrans1028Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1028Entity resTrans1028Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1028",toHostData);
		try {
			resTrans1028Entity = XmlUtil.toBean(fromHostXml,ResTrans1028Entity.class);
		} catch (Exception e) {
			System.out.println("1028erroe:"+e.getMessage());
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1028接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1028Entity;
	}
	/**
	 * 
	 * 描述:积分消费
	 * @param reqTrans1029Entity
	 * @return
	 * @throws EpiccException
	 * @author qex 2017年1月19日 下午3:35:06
	 */
	public ResTrans1029Entity executeTrans1029(ReqTrans1029Entity reqTrans1029Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1029"));
		System.out.println("executeTrans1029:"+reqTrans1029Entity);
		TransUtil.ObjToMap(reqTrans1029Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		List<HashMap<String,Object>> elist= new LinkedList<HashMap<String,Object>>();
		if(reqTrans1029Entity.getOrderList()!=null){
			for (ScoreOrder exchange : reqTrans1029Entity.getOrderList()) {
				hashMap = new HashMap<String, Object>();
				TransUtil.ObjToMap(exchange, hashMap);
				elist.add(hashMap);
			}
		}
		DataQueueEntity edataQueue=new DataQueueEntity("DATA",elist);
		toHostData.addDataQueue(edataQueue);
		/* 产生交易 */
		ResTrans1029Entity resTrans1029Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1029",toHostData);
		try {
			resTrans1029Entity = XmlUtil.toBean(fromHostXml,ResTrans1029Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1029接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1029Entity;
	}
	/**
	 * 
	 * 描述:积分消费撤销
	 * @param reqTrans1029Entity
	 * @return
	 * @throws EpiccException
	 * @author qex 2017年1月19日 下午3:35:06
	 */
	public ResTrans1030Entity executeTrans1030(ReqTrans1030Entity reqTrans1030Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1030"));
		TransUtil.ObjToMap(reqTrans1030Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1030Entity resTrans1030Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1030",toHostData);
		try {
			resTrans1030Entity = XmlUtil.toBean(fromHostXml,ResTrans1030Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1030接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1030Entity;
	}
	/**
	 * 
	 * 描述:积分消费撤销
	 * @param reqTrans1029Entity
	 * @return
	 * @throws EpiccException
	 * @author qex 2017年1月19日 下午3:35:06
	 */
	public ResTrans1031Entity executeTrans1031(ReqTrans1031Entity reqTrans1031Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1031"));
		TransUtil.ObjToMap(reqTrans1031Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1031Entity resTrans1031Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1031",toHostData);
		try {
			resTrans1031Entity = XmlUtil.toBean(fromHostXml,ResTrans1031Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1031接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1031Entity;
	}

	
	public static void main(String[] args) throws EpiccException  {
		String fromHostXml =  "";
		StringBuffer stringBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		stringBuffer.append("<PACKET version=\"1.0\">")
		            .append("<HEAD>")
		            .append("<TRANS_TYPE>1023</TRANS_TYPE>")
		            .append("<RESPONSE_CODE>1</RESPONSE_CODE>")
		            .append("<RESPONSE_MESSAGE>1</RESPONSE_MESSAGE>")
		            .append("<SERIALNO>1</SERIALNO>")
		            .append("</HEAD>")
		    		.append("</PACKET>");
		fromHostXml= stringBuffer.toString();
		
		ResTrans1023Entity resTrans1023Entity;
		try {
			resTrans1023Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1023Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1023接口返回报文解析失败！"+e.getMessage());
		}
		System.out.println(resTrans1023Entity.getResuestHeadEntity().getResponse_message());
	}
	/**
	 * 
	 * 描述:请求电销落地系统，发送油卡充值卡卡密短信至用户手机
	 * @param reqTrans1032Entity
	 * @return
	 * @author 许宝众2017年4月21日 下午12:22:03
	 */
	public ResTrans1032Entity executeTrans1032(ReqTrans1032Entity reqTrans1032Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1032"));
		TransUtil.ObjToMap(reqTrans1032Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1032Entity resTrans1032Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1032",toHostData);
		try {
			resTrans1032Entity = XmlUtil.toBean(fromHostXml,ResTrans1032Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1032接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1032Entity;
	}
	
	/**
	 * 
			* 描述:关联投保人车辆，获取投保人手机号
			* @param reqTrans1033Entity
			* @return resTrans1033Entity
			* @throws EpiccException
			* @author 骆利锋 2017年6月2日 上午10:31:08
	 */
	public ResTrans1033Entity executeTrans1033(ReqTrans1033Entity reqTrans1033Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1033"));
		TransUtil.ObjToMap(reqTrans1033Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/**产生交易**/
		ResTrans1033Entity resTrans1033Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1033", toHostData);
		try {
			resTrans1033Entity = XmlUtil.toBean(fromHostXml, ResTrans1033Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1033接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1033Entity;
	}
	
	/**
	 * 
			* 描述:获取手机校验码
			* @param reqTrans1034Entity
			* @return resTrans1034Entity
			* @throws EpiccException
			* @author 骆利锋 2017年6月2日 上午10:31:08
	 */
	public ResTrans1034Entity executeTrans1034(ReqTrans1034Entity reqTrans1034Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1034"));
		TransUtil.ObjToMap(reqTrans1034Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/**产生交易**/
		ResTrans1034Entity resTrans1034Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1034", toHostData);
		try {
			resTrans1034Entity = XmlUtil.toBean(fromHostXml, ResTrans1034Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1034接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1034Entity;
	}
	
	/**
	 * 
			* 描述:关联投保车辆，绑定（提交）用户信息
			* @param reqTrans1035Entity
			* @return resTrans1035Entity
			* @throws EpiccException
			* @author 骆利锋 2017年6月5日 上午10:31:08
	 */
	public ResTrans1035Entity executeTrans1035(ReqTrans1035Entity reqTrans1035Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1035"));
		TransUtil.ObjToMap(reqTrans1035Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/**产生交易**/
		ResTrans1035Entity resTrans1035Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1035", toHostData);
		try {
			resTrans1035Entity = XmlUtil.toBean(fromHostXml, ResTrans1035Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1035接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1035Entity;
	}
	
	/**
	 * 
			* 描述:投保手机号变更（原手机号可用）
			* @param reqTrans1036Entity
			* @return resTrans1036Entity
			* @throws EpiccException
			* @author 骆利锋 2017年6月5日 上午10:31:08
	 */
	public ResTrans1036Entity executeTrans1036(ReqTrans1036Entity reqTrans1036Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1036"));
		TransUtil.ObjToMap(reqTrans1036Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/**产生交易**/
		ResTrans1036Entity resTrans1036Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1036", toHostData);
		try {
			resTrans1036Entity = XmlUtil.toBean(fromHostXml, ResTrans1036Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1036接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1036Entity;
	}
	
	/**
	 * 
			* 描述:投保手机号变更（提交资料审核）
			* @param reqTrans1037Entity
			* @return resTrans1037Entity
			* @throws EpiccException
			* @author 骆利锋 2017年6月5日 上午10:31:08
	 */
	public ResTrans1037Entity executeTrans1037(ReqTrans1037Entity reqTrans1037Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1037"));
		hashMap.put("IDENTIFYTYPE", reqTrans1037Entity.getIdentifyType());
		hashMap.put("IDENTIFYNO", reqTrans1037Entity.getIdentifyNo());
		hashMap.put("LICENSENO", reqTrans1037Entity.getLicenseNo());
		hashMap.put("USERNAME", reqTrans1037Entity.getUserName());
		hashMap.put("MODIFYPHONENUMBER", reqTrans1037Entity.getModifyPhoneNumber());
		toHostData.setSingleMap(hashMap);
		
		List<HashMap<String,Object>> list=new LinkedList<HashMap<String,Object>>();
		for (ReqTrans1037FileEntity file : reqTrans1037Entity.getReqTrans1037FileEntityList()) {
			hashMap = new HashMap<String, Object>();
			TransUtil.ObjToMap(file, hashMap);
			list.add(hashMap);
		}
		DataQueueEntity dataQueue = new DataQueueEntity("FILE_DATA", list);
		toHostData.addDataQueue(dataQueue);
		/**产生交易**/
		ResTrans1037Entity resTrans1037Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1037", toHostData);
		try {
			resTrans1037Entity = XmlUtil.toBean(fromHostXml, ResTrans1037Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1037接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1037Entity;
	}
	
	/**
	 * 
			* 描述:电商福袋登陆，获取用户基本信息
			* @param reqTrans1038Entity
			* @return resTrans1038Entity
			* @throws EpiccException
			* @author 骆利锋 2017年6月5日 上午10:31:08
	 */
	public ResTrans1038Entity executeTrans1038(ReqTrans1038Entity reqTrans1038Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1038"));
		TransUtil.ObjToMap(reqTrans1038Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1038Entity resTrans1038Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1038", toHostData);
		try {
			resTrans1038Entity = XmlUtil.toBean(fromHostXml, ResTrans1038Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1038接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1038Entity;
	}
	
	/**
	 * 
			* 描述:电商福袋解绑车辆
			* @param reqTrans1038Entity
			* @return resTrans1038Entity
			* @throws EpiccException
			* @author 许宝众 2017年6月5日 上午10:31:08
	 */
	public ResTrans1039Entity executeTrans1039(ReqTrans1039Entity reqTrans1039Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1039"));
		TransUtil.ObjToMap(reqTrans1039Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1039Entity resTrans1039Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1039", toHostData);
		try {
			resTrans1039Entity = XmlUtil.toBean(fromHostXml, ResTrans1039Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1039接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1039Entity;
	}
	
	/**
	 * 
			* 描述:洗车网点查询
			* @param reqTrans1040Entity
			* @return resTrans1040Entity
			* @throws EpiccException
			* @author 骆利锋 2017年7月10日 上午10:31:08
	 */
	public ResTrans1040Entity executeTrans1040(ReqTrans1040Entity reqTrans1040Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1040"));
		TransUtil.ObjToMap(reqTrans1040Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1040Entity resTrans1040Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1040", toHostData);
		try {
			resTrans1040Entity = XmlUtil.toBean(fromHostXml, ResTrans1040Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1040接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1040Entity;
	}
	
	/**
	 * 
			* 描述:生成洗车随机码
			* @param reqTrans1041Entity
			* @return resTrans1041Entity
			* @throws EpiccException
			* @author 骆利锋 2017年7月10日 上午10:31:08
	 */
	public ResTrans1041Entity executeTrans1041(ReqTrans1041Entity reqTrans1041Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1041"));
		TransUtil.ObjToMap(reqTrans1041Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1041Entity resTrans1041Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1041", toHostData);
		try {
			resTrans1041Entity = XmlUtil.toBean(fromHostXml, ResTrans1041Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1041接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1041Entity;
	}
	
	/**
	 * 
			* 描述:
			* 	校验洗车码
			* @param reqTrans1042Entity
			* @return
			* @throws EpiccException
			* @author 许宝众 2017年7月11日 下午12:45:57
	 */
	public ResTrans1042Entity executeTrans1042(ReqTrans1042Entity reqTrans1042Entity)throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1042"));
		TransUtil.ObjToMap(reqTrans1042Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1042Entity resTrans1042Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1042", toHostData);
		try {
			resTrans1042Entity = XmlUtil.toBean(fromHostXml, ResTrans1042Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1042接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1042Entity;
	}
	/**
	 * 
			* 描述:
			* 	洗车验证交易订单查询
			* @param reqTrans1043Entity
			* @return
			* @author 许宝众 2017年7月19日 上午11:27:27
	 */
	public ResTrans1043Entity executeTrans1043(ReqTrans1043Entity reqTrans1043Entity)throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1043"));
		TransUtil.ObjToMap(reqTrans1043Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1043Entity resTrans1043Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1043", toHostData);
		try {
			resTrans1043Entity = XmlUtil.toBean(fromHostXml, ResTrans1043Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1042接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1043Entity;
	}
	
	/**
	 * 
			* 描述:洗车记录查询
			* @param reqTrans1044Entity
			* @return resTrans1044Entity
			* @throws EpiccException
			* @author 骆利锋 2017年7月10日 上午10:31:08
	 */
	public ResTrans1044Entity executeTrans1044(ReqTrans1044Entity reqTrans1044Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1044"));
		TransUtil.ObjToMap(reqTrans1044Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1044Entity resTrans1044Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1044", toHostData);
		try {
			resTrans1044Entity = XmlUtil.toBean(fromHostXml, ResTrans1044Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1044接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1044Entity;
	}
	
	/**
	 * 
			* 描述:亿美礼品兑换同步卡号状态
			* @param reqTrans1045Entity
			* @return resTrans1045Entity
			* @throws EpiccException
			* @author 骆利锋 2017年7月10日 上午10:31:08
	 */
	public ResTrans1045Entity executeTrans1045(ReqTrans1045Entity reqTrans1045Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1045"));
		TransUtil.ObjToMap(reqTrans1045Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1045Entity resTrans1045Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1045", toHostData);
		try {
			resTrans1045Entity = XmlUtil.toBean(fromHostXml, ResTrans1045Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1045接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1045Entity;
	}
	
	/**
	 * 
			* 描述:活动礼品列表
			* @param reqTrans1046Entity
			* @return
			* @throws EpiccException
			* @author lxp 2017年9月20日 下午2:41:46
	 */
	public ResTrans1046Entity executeTrans1046(ReqTrans1046Entity reqTrans1046Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1046"));
		TransUtil.ObjToMap(reqTrans1046Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1046Entity resTrans1046Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		System.out.println("callRemoteService=="+callRemoteService);
		String  fromHostXml = callRemoteService.aotodoTradeXml("1046",toHostData);
		System.out.println("fromHostXml="+fromHostXml);
		try {
			resTrans1046Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1046Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1046接口返回报文解析失败！"+e.getMessage());
		}
		
		return resTrans1046Entity;
	}
	/**
	 * 
			* 描述:受邀有礼
			* @param reqTrans1047Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年10月19日 上午10:04:06
	 */
	public ResTrans1047Entity executeTrans1047(ReqTrans1047Entity reqTrans1047Entity)throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1047"));
		TransUtil.ObjToMap(reqTrans1047Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1047Entity resTrans1047Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.callHttpPostXml("1047", toHostData, "UTF-8");
		try {
			resTrans1047Entity = XmlUtil.toBean(fromHostXml, ResTrans1047Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1047接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1047Entity;
	}
	
	/**
	 * 
			* 描述:受邀有礼注册
			* @param reqTrans1048Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年10月19日 下午4:43:29
	 */
	public ResTrans1048Entity executeTrans1048(ReqTrans1048Entity reqTrans1048Entity)throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1048"));
		TransUtil.ObjToMap(reqTrans1048Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1048Entity resTrans1048Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.callHttpPostXml("1048", toHostData, "UTF-8");
		try {
			resTrans1048Entity = XmlUtil.toBean(fromHostXml, ResTrans1048Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1048接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1048Entity;
	}
	/**
	 * 
			* 描述:车主秘书注册信息查询
			* @param reqTrans1049Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年10月20日 下午1:47:02
	 */
	public ResTrans1049Entity executeTrans1049(ReqTrans1049Entity reqTrans1049Entity)throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1049"));
		TransUtil.ObjToMap(reqTrans1049Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		List<HashMap<String,Object>> list=new LinkedList<HashMap<String,Object>>();
		for (ReqTrans1049CarDataEntity carData : reqTrans1049Entity.getReqTrans1049CarDataList()) {
			hashMap = new HashMap<String, Object>();
			TransUtil.ObjToMap(carData, hashMap);
			list.add(hashMap);
		}
		DataQueueEntity dataQueue=new DataQueueEntity("CAR_DATA",list);
		toHostData.addDataQueue(dataQueue);
		
		/**产生交易**/
		ResTrans1049Entity resTrans1049Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.callHttpPostXml("1049", toHostData, "UTF-8");
		try {
			resTrans1049Entity = XmlUtil.toBean(fromHostXml, ResTrans1049Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1049接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1049Entity;
	}
	/**
	 * 
			* 描述:车主秘书注册、修改信息
			* @param reqTrans1050Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年10月20日 下午1:48:59
	 */
	public ResTrans1050Entity executeTrans1050(ReqTrans1050Entity reqTrans1050Entity)throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1050"));
		TransUtil.ObjToMap(reqTrans1050Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1050Entity resTrans1050Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.callHttpPostXml("1050", toHostData, "UTF-8");
		try {
			resTrans1050Entity = XmlUtil.toBean(fromHostXml, ResTrans1050Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1050接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1050Entity;
	}
	/**
	 * 
			* 描述:事故救援预约登记信息保存
			* @param reqTrans1051Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年10月20日 下午1:51:42
	 */
	public ResTrans1051Entity executeTrans1051(ReqTrans1051Entity reqTrans1051Entity)throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1051"));
		TransUtil.ObjToMap(reqTrans1051Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1051Entity resTrans1051Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1051", toHostData);
		try {
			resTrans1051Entity = XmlUtil.toBean(fromHostXml, ResTrans1051Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1051接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1051Entity;
	}
	/**
	 * 
			* 描述:服务预约信息查询
			* @param reqTrans1052Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年10月20日 下午1:54:16
	 */
	public ResTrans1052Entity executeTrans1052(ReqTrans1052Entity reqTrans1052Entity)throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1052"));
		TransUtil.ObjToMap(reqTrans1052Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1052Entity resTrans1052Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1052", toHostData);
		try {
			resTrans1052Entity = XmlUtil.toBean(fromHostXml, ResTrans1052Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1052接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1052Entity;
	}
	/**
	 * 
			* 描述:服务预约信息注册
			* @param reqTrans1053Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年10月20日 下午1:55:21
	 */
	public ResTrans1053Entity executeTrans1053(ReqTrans1053Entity reqTrans1053Entity)throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1053"));
		TransUtil.ObjToMap(reqTrans1053Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1053Entity resTrans1053Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1053", toHostData);
		try {
			resTrans1053Entity = XmlUtil.toBean(fromHostXml, ResTrans1053Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1053接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1053Entity;
	}
	/**
	 * 
			* 描述:注册挪车服务
			* @param reqTrans1054Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年11月28日 下午2:34:40
	 */
	public ResTrans1054Entity executeTrans1054(ReqTrans1054BodyEntity reqBody)throws EpiccException{

		ReqTrans1054RequestEntity reqEntity = new ReqTrans1054RequestEntity();
		reqEntity.setHead(packHeadService.getHeadFor95518("1054", "03430018"));
		reqEntity.setBody(reqBody);
		String reqXml = XmlUtil.toXml(reqEntity);
		System.out.println(reqXml);
		String url = WeixinUtil.getUrlFor95518("1054");
		String resXml = WeixinUtil.sendHttpReqeust(url, reqXml, false, "GBK");
//		if(StringUtils.isNotBlank(resXml)){
//			ResTrans1054Entity resEntity = XmlUtil.toBean(resXml, ResTrans1054Entity.class);
//		}else {
//			
//		}
		ResTrans1054Entity resEntity = new ResTrans1054Entity();
		try {
			resEntity = XmlUtil.toBean(resXml, ResTrans1054Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1054接口返回报文解析失败！"+e.getMessage());
		}

		return resEntity;
	}
	/**
	 * 
			* 描述:查询已配置挪车服务
			* @param reqTrans1055Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年11月28日 下午2:37:52
	 */
	public ResTrans1055Entity executeTrans1055(ReqTrans1055BodyEntity reqBody)throws EpiccException{
		ReqTrans1055RequestEntity reqEntity = new ReqTrans1055RequestEntity();
		reqEntity.setHead(packHeadService.getHeadFor95518("1054", "03430020"));
		reqEntity.setBody(reqBody);
		String reqXml = XmlUtil.toXml(reqEntity);
		System.out.println(reqXml);
		String url = WeixinUtil.getUrlFor95518("1054");
		String resXml = WeixinUtil.sendHttpReqeust(url, reqXml, false, "GBK");
		ResTrans1055Entity resEntity = new ResTrans1055Entity();
		try {
			resEntity = XmlUtil.toBean(resXml, ResTrans1055Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1055接口返回报文解析失败！"+e.getMessage());
		}
		return resEntity;
	}
	/**
	 * 
			* 描述:获取可使用分机号
			* @param reqTrans1056Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年11月28日 下午2:39:27
	 */
	public ResTrans1056Entity executeTrans1056(ReqTrans1056BodyEntity reqBody)throws EpiccException{
		ReqTrans1056RequestEntity reqEntity = new ReqTrans1056RequestEntity();
		reqEntity.setHead(packHeadService.getHeadFor95518("1054", "03430021"));
		reqEntity.setBody(reqBody);
		String reqXml = XmlUtil.toXml(reqEntity);
		System.out.println(reqXml);
		String url = WeixinUtil.getUrlFor95518("1054");
		String resXml = WeixinUtil.sendHttpReqeust(url, reqXml, false, "GBK");
		ResTrans1056Entity resEntity = new ResTrans1056Entity();
		try {
			resEntity = XmlUtil.toBean(resXml, ResTrans1056Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1055接口返回报文解析失败！"+e.getMessage());
		}
		return resEntity;
	}
	/**
	 * 
			* 描述:
			* 	保养礼品 校验卡号密码 生成订单
			* @param reqTrans1042Entity
			* @return
			* @throws EpiccException
			* @author 许宝众 2017年7月11日 下午12:45:57
	 */
	public ResTrans1060Entity executeTrans1060(ReqTrans1060Entity reqTrans1060Entity)throws EpiccException {
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1060"));
		TransUtil.ObjToMap(reqTrans1060Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/**产生交易**/
		ResTrans1060Entity resTrans1060Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1060", toHostData);
		try {
			resTrans1060Entity = XmlUtil.toBean(fromHostXml, ResTrans1060Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1060接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1060Entity;
	}
	/**
	 * 
			* 描述:活动礼品列表
			* @param reqTrans1059Entity
			* @return
			* @throws EpiccException
			* @author lxp 2017年12月12日 上午11:25:46
	 */
	public ResTrans1059Entity executeTrans1059(ReqTrans1059Entity reqTrans1059Entity) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 生成交易请求对象 */
		hashMap.putAll(packHeadService.getHeadHashMap("1059"));
		TransUtil.ObjToMap(reqTrans1059Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		/* 产生交易 */
		ResTrans1059Entity resTrans1059Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String  fromHostXml = callRemoteService.aotodoTradeXml("1059",toHostData);
		logger.info("fromHostXml="+fromHostXml);
		try {
			resTrans1059Entity = XmlUtil.toBean(fromHostXml,
					ResTrans1059Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EpiccException("系统异常 ：1059接口返回报文解析失败！"+e.getMessage());
		}
		
		return resTrans1059Entity;
	}
	/**
	 * 
	 * 描述:维修保养代金券多次消费
	 * @param reqTrans1057Entity
	 * @return
	 * @throws EpiccException
	 * @author 赵硕 2017年12月08日 下午10:12:00
	 */
	public ResTrans1057Entity executeTrans1057(ReqTrans1057Entity reqTrans1057Entity)throws EpiccException{

		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1057"));
		TransUtil.ObjToMap(reqTrans1057Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1057Entity resTrans1057Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1057", toHostData);
		try {
			resTrans1057Entity = XmlUtil.toBean(fromHostXml, ResTrans1057Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常 ：1057接口返回报文解析失败"+e.getMessage());
			throw new EpiccException("系统异常 ：1057接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1057Entity;
	
		
		
	}
	
	/**
	 * 
			* 描述:查询维修保养代金劵使用记录
			* @param reqTrans1058Entity
			* @return
			* @throws EpiccException
			* @author 赵硕 2017年12月07日 下午14:44:00
	 */
	public ResTrans1058Entity executeTrans1058(ReqTrans1058Entity reqTrans1058Entity)throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1058"));
		TransUtil.ObjToMap(reqTrans1058Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1058Entity resTrans1058Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1058", toHostData);
		try {
			resTrans1058Entity = XmlUtil.toBean(fromHostXml, ResTrans1058Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常 ：1058接口返回报文解析失败"+e.getMessage());
			throw new EpiccException("系统异常 ：1058接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1058Entity;
	}
	
	/**
	 * 
			* 描述:全年保养劵使用记录查询
			* @param reqTrans1061Entity
			* @return
			* @throws EpiccException
			* @author 赵硕 2017年12月18日 下午1:22:48
	 */
	public ResTrans1061Entity executeTrans1061(ReqTrans1061Entity reqTrans1061Entity)throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1061"));
		TransUtil.ObjToMap(reqTrans1061Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1061Entity resTrans1061Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1061", toHostData);
		try {
			resTrans1061Entity = XmlUtil.toBean(fromHostXml, ResTrans1061Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常 ：1061接口返回报文解析失败"+e.getMessage());
			throw new EpiccException("系统异常 ：1061接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1061Entity;
	}
	/**
	 * 
			* 描述:取消全年保养订单
			* @param reqTrans1062Entity
			* @return
			* @throws EpiccException
			* @author 赵硕  2017年12月20日 下午1:36:17
	 */
	public ResTrans1062Entity executeTrans1062(ReqTrans1062Entity reqTrans1062Entity)throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1062"));
		TransUtil.ObjToMap(reqTrans1062Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1062Entity resTrans1062Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1062", toHostData);
		try {
			resTrans1062Entity = XmlUtil.toBean(fromHostXml, ResTrans1062Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常 ：1062接口返回报文解析失败"+e.getMessage());
			throw new EpiccException("系统异常 ：1062接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1062Entity;
	}
	/**
	 * 
			* 描述: 订单取消接口
			* @param reqTrans1063Entity
			* @return
			* @throws EpiccException
			* @author han 2018年1月30日 下午1:55:02
	 */
	public ResTrans1063Entity executeTrans1063(ReqTrans1063Entity reqTrans1063Entity)throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1063"));
		TransUtil.ObjToMap(reqTrans1063Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1063Entity resTrans1063Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1063", toHostData);
		try {
			resTrans1063Entity = XmlUtil.toBean(fromHostXml, ResTrans1063Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常 ：1063接口返回报文解析失败"+e.getMessage());
			throw new EpiccException("系统异常 ：1063接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1063Entity;
	}
	/**
	 * 
			* 描述:电子码注册
			* @param reqTrans1064Entity
			* @return
			* @throws EpiccException
			* @author 赵硕  2017年01月30日 下午15:54:17
	 */
	public ResTrans1064Entity executeTrans1064(ReqTrans1064Entity reqTrans1064Entity)throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1064"));
		TransUtil.ObjToMap(reqTrans1064Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1064Entity resTrans1064Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1064", toHostData);
		try {
			resTrans1064Entity = XmlUtil.toBean(fromHostXml, ResTrans1064Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常 ：1064接口返回报文解析失败"+e.getMessage());
			throw new EpiccException("系统异常 ：1064接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1064Entity;
	}
	/**
	 * 
			* 描述:交通事故快速处理协议书发送
			* @param reqTrans1065Entity
			* @return
			* @throws EpiccException
			* @author 赵硕 2018年2月8日 上午11:09:23
	 */
	public ResTrans1065Entity executeTrans1065(ReqTrans1065Entity reqTrans1065Entity)throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap("1065"));
		TransUtil.ObjToMap(reqTrans1065Entity, hashMap);
		toHostData.setSingleMap(hashMap);
		
		/**产生交易**/
		ResTrans1065Entity resTrans1065Entity = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml("1065", toHostData);
		try {
			resTrans1065Entity = XmlUtil.toBean(fromHostXml, ResTrans1065Entity.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常 ：1065接口返回报文解析失败"+e.getMessage());
			throw new EpiccException("系统异常 ：1065接口返回报文解析失败！"+e.getMessage());
		}
		return resTrans1065Entity;
	}	
	/**
	 * 
			* 描述:
			* 通用的执行指定交易类型WebService方法
			* @param transCode
			* @param req
			* @param resClazz
			* @return
			* @throws EpiccException
			* @author 许宝众 2018年2月9日 上午9:35:52
	 */
	public <T,R> R genericExecuteTrans(String transCode,T req,Class<R> resClazz) throws EpiccException{
		TradeEntity toHostData = new TradeEntity();
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		/**生成交易请求对象**/
		hashMap.putAll(packHeadService.getHeadHashMap(transCode));
		TransUtil.ObjToMap(req, hashMap);
		toHostData.setSingleMap(hashMap);
		/**产生交易**/
		R res = null;
		AoToQuotCallRemoteService callRemoteService = new AoToQuotCallRemoteService();
		String fromHostXml = callRemoteService.aotodoTradeXml(transCode, toHostData);
		try {
			res = XmlUtil.toBean(fromHostXml, resClazz);
		} catch (Exception e) {
			logger.error("系统异常 ："+transCode+"接口返回报文解析失败"+e.getMessage());
			throw new EpiccException("系统异常 ："+transCode+"接口返回报文解析失败！"+e.getMessage());
		}
		return res;
	}
	
}
