/**
 * @FileName  :TradeManager.java
 * @Author     : huanghui
 * @CreateDate : Oct 29, 2008
 * @CopyRight  : XXX 
 * @LastDate   : $Date: 2014/10/16 09:12:02 $
 * @LastAuthor : $Author: liminghui $
 * @Descript   : 
 */
package com.commman.aoto;

import org.apache.log4j.Logger;

import com.commman.trademanager.entity.TradeEntity;
import com.commman.trademanager.entity.XmlBaseInfoEntity;
import com.commman.trademanager.xmlman.entity.Trade;

 /**
 * @ClassName  : TradeManager.java
 * @Brief      : [brief of the class]
 * @Author     : huanghui
 * @CreateDate : Oct 29, 2008
 * @CopyRight  : 
 * @Descript   :[class struct descript] 
 *        No.1 :     
 */
public class TradeManager {
	
	private Logger logger=Logger.getLogger("TradeManager.class"); 
	private TradeManager4XML tmx ;  //xml交易管理器
	private XmlBaseInfoEntity xbi= null ; //xml基本信息
	private static TradeManager instance = null ;
	/**
	 * 同步对象
	 */
	private static final Object mutexObject = new Object();
	
	
	String trfile = null;     //交易配置
	String commfile = null;   //通讯区配置
	
	/**
	 * @param xbi
	 * @param trfile
	 * @param commfile
	 */
	private TradeManager( String trfile, String commfile,XmlBaseInfoEntity xbi)  throws Exception{
		super();
		this.xbi = xbi;	
		this.trfile = trfile;
		this.commfile = commfile;
		if(xbi == null ||"".equals(trfile)||trfile==null||"".equals(commfile)||commfile==null ){
			throw new Exception("交易队列初始化失败，调用[TradeManager.getInstance(...)参数异常，请检查") ;
		}
		
		 try
			{
				tmx = TradeManager4XML.getInstance(trfile, commfile,xbi);
			} catch (Exception e) {
				// TODO Auto-generated catch block				
				throw new Exception(e.getMessage());
			}
		
	}

	/**
	 * 获取交易管理器
	 * @param trDefXml
	 * @param commDefXml
	 * @param xbi
	 * @return
	 * @throws Exception
	 */
	public static TradeManager getInstance(String trDefXml,String commDefXml,XmlBaseInfoEntity xbi) throws Exception{
		
		//注意这样写的理由，为了保证只有一个初始化过程
		synchronized( mutexObject ){
			if( instance != null ){	
				return instance;
			}
			TradeManager ins = new TradeManager(trDefXml,commDefXml,xbi );
			instance = ins;
		}
		return instance;
	}
 


	/**
	 * 打请求XML报文 -- 客户端打包调用方法
	 * @param trCode
	 * @param trEntity
	 * @return
	 * @throws Throwable
	 */
    public String packRequestXMLforClient(String trCode,TradeEntity trEntity)throws Throwable{
    
    	return tmx.packRequestXML(trCode, trEntity);
    }

    /**
     * 解返回的xml报文，需要解析包头中的响应信息 -- 客户端解包调用方法
     * 解析报文：上行包
     * @param trCode
     * @param strXML
     * @return
     * @throws Throwable
     */
    public TradeEntity unPackReponseXMLforClient(String trCode,String strXML)throws Throwable{    	
    	return tmx.unPackReponseXML(trCode, strXML,true);
    }

    /**
     * 解主机返回的xml报文，不需要解析包头中的响应信息 -- 客户端解包调用方法
     * @param trCode
     * @param strXML
     * @return
     * @throws Throwable
     */
    public TradeEntity unPackReponseXMLNoParseHeadforClient(String trCode,String strXML)throws Throwable{    	
    	return tmx.unPackReponseXML(trCode, strXML,false);
    }   
    
	/**
	 * 打返回给客户端的应答XML报文 -- 服务端打包调用方法
	 * @param trCode
	 * @param trEntity
	 * @return
	 * @throws Throwable
	 */
    public String packReponseXMLforServer(String trCode,TradeEntity trEntity)throws Throwable{
    
    	return tmx.packReponseXML(trCode, trEntity);
    }

    /**
     * 解客户端的请求xml报文，需要解析包头中的响应信息
     * @param trCode
     * @param strXML
     * @return
     * @throws Throwable
     */
    public TradeEntity unPackRequestXMLforServer(String trCode,String strXML)throws Throwable{    	
    	return tmx.unPackRequestXML(trCode, strXML);
    }

    /**
     * 获取XML中的主机交易吗
     * @param fromHostXml
     * @return
     */
	public String getHostCodeFromXML(String fromHostXml){
		return tmx.getHostCodeFromXML(fromHostXml);
	}
     
	/**
	 * 根据本地交易码获取主机交易码
	 * @param trCode
	 * @return
	 * @throws Throwable
	 */
	public String getHostCodeByTrCode(String trCode)throws Throwable{
		return tmx.getHostCodeByTrCode(trCode);		
	}

	/**
	 * 根据主机交易码获取本地交易码（注意，如果主机码重复系统取第一个交易实例）
	 * @param hostCode
	 * @return
	 * @throws Throwable
	 */
	public String getTrCodeByHostCode(String hostCode)throws Throwable{
		return tmx.getTrCodeByHostCode(hostCode);
	}

	/**
	 * 获取交易实例（根据内部交易码）
	 * @param trCode
	 * @return
	 * @throws Throwable
	 */
	public Trade findTradeByTrCode(String trCode)throws Throwable{
		return tmx.findTradeByTrCode(trCode);
	}
	
	/**
	 * 获取交易实例（根据外部－主机交易码）
	 * @param hostCode
	 * @return
	 * @throws Throwable
	 */
	public Trade findTradeByHostCode(String hostCode)throws Throwable{
		return tmx.findTradeByHostCode(hostCode);
	}
}
