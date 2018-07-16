package com.commman.webservice;

import org.apache.log4j.Logger;

import com.commman.aoto.AoToQuotInitTradeManager;
import com.commman.client.SimpleClient;
import com.commman.trademanager.entity.TradeEntity;
import com.commman.trademanager.pub.TradePub;
import com.sys.dic.SysDicHelper;
import com.sys.dic.entity.DicContentEntity;
import com.sys.exception.EpiccException;
import com.wx.util.WeixinUtil;

 /**
 * @ClassName  : CallRemoteService.java
 * @Brief      : [远程web接口服务]
 * @Author     : huanghui
 * @CreateDate : Oct 30, 2008
 * @CopyRight  : 
 * @Descript   :[class struct descript] 
 *        No.1 :     
 */
public class AoToQuotCallRemoteService {

	private com.commman.aoto.TradeManager tradeManager = null ;  //交易管理器
	private Logger logger=Logger.getLogger("AoToQuotCallRemoteService.class"); 
	
	/**
	 * 初始化交易管理器
	 */
	public AoToQuotCallRemoteService() {
		super();
		 try {
			 AoToQuotInitTradeManager initTradeManager = new AoToQuotInitTradeManager();
			 tradeManager =initTradeManager.initTradeManagerAdviance();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("初始化交易管理器失败"+e.getMessage());
		}
	}
	
	/**
     * 本地调用远程服务
     * @param trCode
     * @param toHostData
     * @return
	 * @throws ItmException 
     */
	public String aotodoTradeXml(String trCode, TradeEntity toHostData) throws EpiccException{
		String toHostXml = "";
		String errmsg="";
		String fromHostXml="";
		try {
			toHostXml = tradeManager.packRequestXMLforClient(trCode, toHostData);
			//加xml头信息
			toHostXml = TradePub.addXmlHead(toHostXml);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("交易"+ trCode+"打包错误，请检查格式:"+e.getMessage() );
			throw new EpiccException("交易"+ trCode+"打包错误，请检查格式:"+e.getMessage());
		}
		
		try {
			String sgaddress =SysDicHelper.getInstance().getValueByDicTypeAndDicId("TRANS_ADDRESS", trCode).trim();
			if (null == sgaddress) {
				throw new EpiccException("请检查交易" + trCode + "请求地址是否成功配置");
			}
			DicContentEntity dic =SysDicHelper.getInstance().getDicContentByDicTypeAndDicId("TRANS_NAMESPACE", trCode);
			if(null == dic || null == dic.getIdValue() || null == dic.getIdValue2()){
				throw new EpiccException("请检查交易"+ trCode+"请求命名空间是否成功配置");
			}
			fromHostXml=SimpleClient.offerChannelHandler(toHostXml,sgaddress,dic.getIdValue(),dic.getIdValue2());
		} catch (EpiccException e) {
			errmsg=e.getErrMess();
			logger.error(errmsg);
			throw new EpiccException(errmsg);
		}
		return fromHostXml ;
		
	}
	
	/**
	 * 描述:通过http post方式调用接口
	 * @param trCode
	 * @param toHostData
	 * @return xml
	 * @throws EpiccException
	 * @author 吕亮 2017年10月27日 下午7:07:12
	 */
	public String callHttpPostXml(String trCode, TradeEntity toHostData, String encoding) throws EpiccException{
		String toHostXml = "";
		String errmsg="";
		String fromHostXml="";
		try {
			toHostXml = tradeManager.packRequestXMLforClient(trCode, toHostData);
			//加xml头信息
			toHostXml = TradePub.addXmlHead(toHostXml);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("交易"+ trCode+"打包错误，请检查格式:"+e.getMessage() );
			throw new EpiccException("交易"+ trCode+"打包错误，请检查格式:"+e.getMessage());
		}
		try {
			String sgaddress =SysDicHelper.getInstance().getValueByDicTypeAndDicId("TRANS_ADDRESS", trCode).trim();
			if (null == sgaddress) {
				throw new EpiccException("请检查交易" + trCode + "请求地址是否成功配置");
			}
			fromHostXml = WeixinUtil.sendHttpReqeust(sgaddress,toHostXml, false, encoding);
		} catch (EpiccException e) {
			errmsg=e.getErrMess();
			logger.error(errmsg);
			throw new EpiccException(errmsg);
		}
		return fromHostXml ;
		
	}
	
//	public static void main(String[] args) {
//		String reqURL = "http://12.1.37.65:3003/WebContent/servlet/DispatcherServletInter/customerCarInfo";
//		String xml = WeixinUtil.sendHttpReqeust(reqURL, xml(), false);
//		System.out.println(xml);
//		String resXml = HttpRequestUtil.sendHttpPostRequest(reqURL, xml());
//		System.out.println(resXml);
//	}
	
//	private static String xml(){
//		StringBuffer xml = new StringBuffer();
//		xml.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
//		xml.append("<PACKET type=\"REQUEST\" version=\"1.0\">");
//		xml.append("<HEAD>");
//		xml.append("<TRANS_TYPE>1047</TRANS_TYPE>");
//		xml.append("<TRANS_USER>cardm_wx</TRANS_USER>");
//		xml.append("<TRANS_PWD>cardm_wx_bj1100</TRANS_PWD>");
//		xml.append("<SERIALNO>1000000000000001</SERIALNO>");
//		xml.append("</HEAD>");
//		xml.append("<BODY>");
//		xml.append("<BASE_PART>");
//		xml.append("<LICENSENO>京JH2510</LICENSENO>");
//		xml.append("<LICENSECOLORCODE>01</LICENSECOLORCODE>");
//		xml.append("<INTERUSE>01</INTERUSE>");
//		xml.append("<CHECKCODE>1234561</CHECKCODE>");
//		xml.append("</BASE_PART>");
//		xml.append("</BODY>");
//		xml.append("</PACKET>");
//		return xml.toString();
//	}
}


