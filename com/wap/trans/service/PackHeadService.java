package com.wap.trans.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commman.init.InitTradeManager;
import com.commman.trademanager.pub.TradeManager;
import com.sys.dic.SysDicHelper;
import com.sys.dic.entity.DicContentEntity;
import com.sys.exception.EpiccException;
import com.sys.service.GetBussinessNoService;
import com.wap.trans.entity.RequestHead95518;
import com.wap.trans.entity.RequestHeadEntity;
import com.wap.util.TransUtil;

import core.db.dao.IBaseService;
import core.db.query.HQLQuery;

/**
 * 此类用于生成交易头文件
 * @author ZJM
 *
 */
@Service("packHeadService")
@SuppressWarnings("unchecked")
public class PackHeadService {
	private static final Log logger = LogFactory.getLog(PackHeadService.class);
	@Autowired(required = false)
	private IBaseService baseService = null ;
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
	/**
	 * 交易头打包
	 * @param head
	 * @return
	 * @author ZJM
	 * @throws Throwable
	 */
	private HashMap<String,Object> packHead(RequestHeadEntity head)throws EpiccException{
        
		HashMap headMap = new HashMap();
		if(head==null){
			EpiccException ex= new EpiccException();
			ex.setErrMess("交易头生成失败！原因：交易用户名/密码获取失败！,请检查统一登录是否配置！");
			logger.info("交易头生成失败！原因：交易用户名/密码获取失败！,请检查统一登录是否配置！");
			throw ex;
		}else if(head.getTrans_type().equals("")){
			EpiccException ex= new EpiccException();
			ex.setErrMess("交易头生成失败！原因：交易编号获取失败！,请检查交易编号是否配置！");
			logger.info("交易头生成失败！原因：交易编号获取失败！,请检查交易编号是否配置！");
			throw ex;
		}
		return TransUtil.ObjToMap(head, headMap) ;
	}

	/**
	 * 
	 * @param trCode 交易代码，根据XML定义的
	 * @author ZJM
	 * @return
	 * @throws Throwable
	 */
	public HashMap<String,Object> getHeadHashMap(String trCode) throws EpiccException{
		return this.packHead(this.getHeadEnity(trCode));
	}

	private RequestHeadEntity getHeadEnity(String trCode) throws EpiccException {
		RequestHeadEntity head=new RequestHeadEntity();
		/*设置交易代码*/
		head.setTrans_type(trCode);
		//交易识别号，同步和非同步的都加上
		head.setSerialno(GetBussinessNoService.GetBussinessNo("JIAOYI"));
		/*设置交易用户名，密码*/
		//从字典表中获取,对应交易的用户名和密码(value1:用户名 value2:密码)
		DicContentEntity dic = SysDicHelper.getInstance().getDicContentByDicTypeAndDicId("TRANS_USER_PWD", trCode);
		if(null==dic || null == dic.getIdValue() || null == dic.getIdValue2()){
			throw new EpiccException("请检查"+trCode+"交易接口用户名或密码是否成功配置");
		}
		head.setTrans_user(dic.getIdValue());
		head.setTrans_pwd(dic.getIdValue2());
		return head;
	}
	
	/**
	 * 95518交易头打包
	 * @param head
	 * @return
	 * @author ZJ
	 * @throws Throwable
	 */
	private RequestHead95518 packHeadFor95518(RequestHead95518 head)throws EpiccException{
		if(head==null){
			EpiccException ex= new EpiccException();
			ex.setErrMess("交易头生成失败！原因：交易用户名/密码获取失败！,请检查统一登录是否配置！");
			logger.info("交易头生成失败！原因：交易用户名/密码获取失败！,请检查统一登录是否配置！");
			throw ex;
		}else if(head.getRequestType().equals("")){
			EpiccException ex= new EpiccException();
			ex.setErrMess("交易头生成失败！原因：交易编号获取失败！,请检查交易编号是否配置！");
			logger.info("交易头生成失败！原因：交易编号获取失败！,请检查交易编号是否配置！");
			throw ex;
		}
		return head ;
	}
	/**
	 * 
			* 描述:
			* @param trCode  接口号
			* @param requestType  交易码
			* @return
			* @throws EpiccException
			* @author zhangjian2017年12月28日 上午10:12:54
	 */
	public RequestHead95518 getHeadFor95518(String trCode, String requestType) throws EpiccException{
		return this.packHeadFor95518(this.getHeadEnityFor95518(trCode,requestType));
	}

	public RequestHead95518 getHeadEnityFor95518(String trCode, String requestType) throws EpiccException {
		RequestHead95518 head = new RequestHead95518();
		/*设置交易码*/
		head.setRequestType(requestType);
		logger.info(requestType);
		/*设置UUID*/
		String requestUuid = UUID.randomUUID().toString();
		head.setUuid(requestUuid);
		logger.info(requestUuid);
		/*设置交易用户名，密码*/
		//从字典表中获取,对应交易的用户名和密码(value1:用户名 value2:密码)
		DicContentEntity dic = SysDicHelper.getInstance().getDicContentByDicTypeAndDicId("TRANS_USER_PWD", trCode);
		logger.info(dic);
		if(null==dic || null == dic.getIdValue() || null == dic.getIdValue2()){
			throw new EpiccException("请检查"+trCode+"交易接口用户名或密码是否成功配置");
		}
		head.setUser(dic.getIdValue());
		head.setPassword(dic.getIdValue2());
		/*设置发送者用户代码，服务提供方接口版本号*/
		//从字典表中获取,对应交易的用户名和密码(value1:服务提供方接口版本号 value2:发送者用户代码)
		DicContentEntity dict = SysDicHelper.getInstance().getDicContentByDicTypeAndDicId("TRANS_VERSION_SENDER", trCode);
		if(null==dict || null == dict.getIdValue() || null == dict.getIdValue2()){
			throw new EpiccException("请检查"+trCode+"交易接口服务提供方接口版本号或发送者用户代码是否成功配置");
		}
		head.setServerVersion(dic.getIdValue());
		head.setSender(dic.getIdValue2());
		/*设置发送时间*/
		Calendar cal = Calendar.getInstance(); 
		Date date = cal.getTime();
		String flowInTime = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(date);
		head.setFlowintime(flowInTime);
		
		return head;
	}
	
}
