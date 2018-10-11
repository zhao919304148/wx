/**
 * @FileName  :PubFunc.java
 * @Author     : huanghui
 * @CreateDate : Nov 10, 2008
 * @CopyRight  : XXX 
 * @LastDate   : $Date: 2010/10/18 02:10:23 $
 * @LastAuthor : $Author: sunshine $
 * @Descript   : 
 */
package com.commman.util;

import com.commman.trademanager.entity.TradeEntity;

 /**
 * @ClassName  : PubFunc.java
 * @Brief      : [brief of the class]
 * @Author     : huanghui
 * @CreateDate : Nov 10, 2008
 * @CopyRight  : 
 * @Descript   :[class struct descript] 
 *        No.1 :     
 */
public class PubFunc {

    /**
     * xml方式打错误包
     * @param hostCode
     * @param errCode
     * @param errMsg
     * @return
     */
	public static String packErrMsg4Xml(String hostCode,String errCode,String errMsg){
		
	      return "<?xml version='1.0' encoding='GB2312'?>" +
			"<PACKET type='RESPONSE' version='1.0'>" +
			"<HEAD>" +
				"<TRANSTYPE>SNY</TRANSTYPE>" +
				"<TRANSCODE>"+hostCode.trim()+"</TRANSCODE>" +
				"<RESPONSECODE>" +
				errCode +
				"</RESPONSECODE>" +
				"<ERRORMESSAGE>" +
				errMsg+
				"</ERRORMESSAGE>" +
				"<SVCSEQNO></SVCSEQNO>" +
			"</HEAD>" +
			"</PACKET>";
		}
		
    /**
     * 交易对象打错误信息
     * @param trEntity
     * @param errCode
     * @param errMsg
     * @return
     */
	public static TradeEntity packErrMsg4Trade(TradeEntity trEntity,String errCode,String errMsg){
		trEntity.setErrCode(errCode);
		trEntity.setErrMsg(errMsg);
		return trEntity ;
		
	}
	
	/**
	 * 左边补0到指定长度的函数
	 * @param s
	 * @param length
	 * @param chr
	 * @return
	 */
	static public String leftAppendChr(String s,int length,String chr ){
		
		if( s == null ){
			s="";
		}		
		if( s.length() >length ){
			
			return s.substring(0,length);
		}		
		while( s.length() < length ){
			
			s = chr+s;
		}		
		return s;
	} 	   

}
