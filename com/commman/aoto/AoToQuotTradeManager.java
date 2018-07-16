/**
 * @FileName  :TradeManagerExtDemo.java
 * @Author     : huanghui
 * @CreateDate : Nov 6, 2008
 * @CopyRight  : XXX 
 * @LastDate   : $Date: 2014/10/16 09:12:01 $
 * @LastAuthor : $Author: liminghui $
 * @Descript   : 
 */
package com.commman.aoto;

import org.apache.log4j.Logger;

import com.commman.trademanager.entity.XmlBaseInfoEntity;
import com.sys.run.SystemParameter;

 /**
 * @ClassName  : TradeManagerExtDemo.java
 * @Brief      : [交易管理器的使用deno']
 * @Author     : huanghui
 * @CreateDate : Nov 6, 2008
 * @CopyRight  : Copyright 2008 QuanJiang Techonogy Co.,Ltd. All rights reserved.
 * @Descript   :[交易管理器的使用deno'] 
 *        No.1 : 特别申明：
 *             交易管理器在整个应用中只保留一个实例，所以如果实例已经创建了，后面的new方法不能改变其结果
 *            
 */
public class AoToQuotTradeManager {

	TradeManager tm = null ;  //交易管理器	
	private Logger logger=Logger.getLogger("InitTradeManager.class");
	
	
	/**
	 * 初始化交易管理器
	 * public TradeManager(XmlBaseInfoEntity xbi, String trfile, String commfile)
	 */
	public TradeManager initTradeManagerAdviance() {
		
		// TODO Auto-generated constructor stub
		 try {		
			String trfile = SystemParameter.getInstance().getTradeFile() ; // "D:\\workspace\\flexserver\\WebRoot\\WEB-INF\\config\\xml\\trdef.xml";     //交易配置
		    String commfile = SystemParameter.getInstance().getCommFile() ; //通讯区配置
			XmlBaseInfoEntity xbi = new XmlBaseInfoEntity();
			//用户扩展XmlBaseInfoEntity
			xbi.reset();     //数据复位
			//xml流的根
			xbi.setXmlRoot("/PACKET");
		    //响应码节点（含路径）
			xbi.setResponseCodeAttr("/PACKET/HEAD/RESPONSECODE" );
		    //错误消息节点（含路径）
			xbi.setErrorMessageAttr("/PACKET/HEAD/ERRORMESSAGE");
		    //主机交易码节点(含路径)
			xbi.setHostCodeAttr("/PACKET/HEAD/TRANSCODE");
		    
			//设置成功标志的值，如果不在此范围解析返回xml中的head时均定为错误 ，
			// []中的值表示成功标志
			xbi.getResponseSuccFlag().append("[0]");
			xbi.getResponseSuccFlag().append("[000]");
			xbi.getResponseSuccFlag().append("[0801]");			
		   //使用系统缺省的XmlBaseInfoEntity
			tm = TradeManager.getInstance(trfile, commfile,xbi);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("初始化交易管理器失败"+e.getMessage());
		}
		return tm ;
	}
		

	static public void main(String args[]) {
		AoToQuotTradeManager doRevDataService = new AoToQuotTradeManager();
    //	String xml ="<?xml version=\"1.0\" encoding=\"GB2312\"?>  <PACKET type=\"REQUEST\" version=\"1.0\"><HEAD><TRANSTYPE>SNY</TRANSTYPE><TRANSCODE>1101</TRANSCODE><USER>FLEXITM</USER><PASSWORD>password</PASSWORD><SVCSEQNO></SVCSEQNO></HEAD><BODY><REPORTAUTH>0</REPORTAUTH><RPTYEAR></RPTYEAR><RPTMONTH></RPTMONTH><RPTDAY></RPTDAY><STARTDATE></STARTDATE><ENDDATE></ENDDATE></BODY></PACKET>" ;
    	doRevDataService.initTradeManagerAdviance();
    }
	
	
	
}
