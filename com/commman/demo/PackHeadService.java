/**
 * 
 */
package com.commman.demo;

import java.util.HashMap;

import com.commman.init.InitTradeManager;
import com.commman.trademanager.pub.TradeManager;


/**
 * 打包交易头文件
 * @author liuwei
 *
 */
public class PackHeadService {

	public HashMap packHeadCarIns(String trCode)throws Throwable{
		InitTradeManager initTradeManager = new InitTradeManager() ;
		
        TradeManager tr = initTradeManager.initTradeManagerAdviance();
       
		HashMap headMap = new HashMap();

		/** 头文件 **/
		headMap.put("TRANSTYPE", "SNY");
		headMap.put("TRANSCODE", tr.getHostCodeByTrCode(trCode)); //传递 本地交易码，转换成主机交易码
		headMap.put("USER", "FLEXITM");
		headMap.put("PASSWORD", "password");
		headMap.put("SVCSEQNO", "");
		
		return headMap ;
		
	}
}
