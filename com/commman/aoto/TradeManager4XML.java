package com.commman.aoto;

import java.util.List;

import org.apache.log4j.Logger;

import com.commman.trademanager.entity.TradeEntity;
import com.commman.trademanager.entity.XmlBaseInfoEntity;
import com.commman.trademanager.xmlman.conf.LoadComm;
import com.commman.trademanager.xmlman.entity.Comm;
import com.commman.trademanager.xmlman.entity.CommCell;
import com.commman.trademanager.xmlman.entity.Trade;
import com.commman.trademanager.xmlman.entity.TradeXml;
import com.commman.trademanager.xmlman.service.PackXML;
import com.commman.trademanager.xmlman.service.UnPackXMLService;

 /**
 * @ClassName  : TradeManager.java
 * @Brief      : [XML交易管理器]
 * @Author     : huanghui
 * @CreateDate : Oct 27, 2008
 * @CopyRight  : 
 * @Descript   :[class struct descript] 
 *        No.1 :     
 */
public class TradeManager4XML {
	
	/**
	 * 同步对象
	 */
	private static final Object mutexObject = new Object();
	/**
	 * 唯一实例
	 */
	private static TradeManager4XML instance = null;
		
	private String trfile = "F:\\tomcat6\\webapps\\flexitm\\WebRoot\\WEB-INF\\config\\xml\\trdef.xml";
	private String commfile = "F:\\tomcat6\\webapps\\flexitm\\WebRoot\\WEB-INF\\config\\xml\\commdef.xml";
	private List trList = null ;  //交易队列
	private List commList =null; //通讯区队列
	private XmlBaseInfoEntity xbi=null ;
	
	
	private Logger logger=Logger.getLogger("TradeManager4XML.class"); 
	
	/**
	 * 构造函数禁止外部构造
	 */
	private TradeManager4XML(String trDefXml,String commDefXml,XmlBaseInfoEntity xbi ){
		this.trfile = trDefXml ;
		this.commfile=commDefXml ;
		this.xbi = xbi ;
	}
		
	private TradeManager4XML(){
	}

	/**
	 * 获取唯一事例
	 * @param trDefXml  : 交易配置文件（含完整路径）
	 * @param commDefXml: 通讯配置文件（含完整路径）
	 */
	public static TradeManager4XML getInstance(String trDefXml,String commDefXml,XmlBaseInfoEntity xbi) throws Exception{
		
		//注意这样写的理由，为了保证只有一个初始化过程
		synchronized( mutexObject ){
			if( instance != null ){	
				return instance;
			}
			TradeManager4XML ins = new TradeManager4XML(trDefXml,commDefXml,xbi );
			try {
				ins.init();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				throw new Exception(e.getMessage()) ;
			}
			instance = ins;
		}
		return instance;
	}

	
	/**
	 * 获取唯一事例，如果没有初始
	 * @param trDefXml  : 交易配置文件（含完整路径）
	 * @param commDefXml: 通讯配置文件（含完整路径）
	 */
	public static TradeManager4XML getInstance() throws Exception{
		
		//注意这样写的理由，为了保证只有一个初始化过程
		synchronized( mutexObject ){
			if( instance != null ){	
				return instance;
			}else{
				throw new Exception("交易队列没有初始化，请调用[TradeManager4XML.getInstance(String trDefXml,String commDefXml,XmlBaseInfoEntity xbi)]") ;
			}
		}
	}
	
	private void init()throws Throwable{
		
		try {

			this.trList = ((com.commman.aoto.LoadTrade)com.commman.aoto.LoadTrade.getInstance(trfile).clone()).getTrades();
			this.commList = ((com.commman.aoto.LoadComm)com.commman.aoto.LoadComm.getInstance(commfile).clone()).getCommList();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("交易系统初始化失败，请与管理员联系！"+e.getMessage());
			throw new Exception("交易队列不存在，请与管理员联系！");
		}
	}
	/**
	 * 根据交易码获取交易实例
	 * @param trCode
	 * @return
	 */
	public Trade findTradeByTrCode(String trCode)throws Throwable{
		if  (trList== null){
			logger.fatal("交易队列不存在，请与管理员联系！");
			throw new Exception("交易队列不存在！");
		}
			 
		Trade tr=null ;
		for(int i=0;i<trList.size();i++){
			tr =(Trade)trList.get(i);
			if(trCode.equalsIgnoreCase(tr.getTrCode())){
				return tr ;
			}			
		}		
		logger.fatal("交易码["+ trCode+"]配置不存在！");
		throw new Exception("交易码配置不存在！");
	}
	
	/**
	 * 根据主机交易码查询第一个交易实例
	 * @param hostCode
	 * @return
	 * @throws Throwable
	 */
	public Trade findTradeByHostCode(String hostCode)throws Throwable{
		if  (trList== null){
			logger.fatal("交易队列不存在，请与管理员联系！");
			throw new Exception("交易队列不存在，请与管理员联系！");
		}
			 
		Trade tr=null ;
		for(int i=0;i<trList.size();i++){
			tr =(Trade)trList.get(i);
			if(hostCode.equalsIgnoreCase(tr.getHostCode())){
				return tr ;
			}			
		}		
		logger.fatal("主机交易码["+ hostCode+"]配置不存在！");
		throw new Exception("主机交易码["+ hostCode+"]配置不存在，请与管理员联系！");
	}
		
	/**
	 * 取通讯区得items
	 * @param commId
	 * @return
	 */
    public List findCommItemsByCommId(String commId){    	
    	int len = commList.size();
    	for(int i=0;i<len;i++){
    		Comm comm = null ;
    		comm =(Comm)commList.get(i);
    		if(commId.equalsIgnoreCase(comm.getCommId().toUpperCase())){
    			//
    			return comm.getItems();
    		}
    	}
    	logger.error("通讯区["+commId+"]不存在！");
    	return null ;
    }
    /**
     * 根据交易码打请求XML报文
     * @param trCode
     * @param trEntity
     * @return  xml 报文
     */
    public String packRequestXML(String trCode,TradeEntity trEntity)throws Throwable{
    	TradeXml trXml = new TradeXml();
    	PackXML packXML = new PackXML();
    	//打包，选择下行报文格式
    	try {
			Trade  trade = findTradeByTrCode(trCode);
			/*if(logger.isInfoEnabled()){
				logger.info("--------交易[HostCode:"+trade.getHostCode()+" TRNAME:"+trade.getTrName()+" PACKNAME:"+trade.getDownPack().getName()+ "]请求XML打包开始--------------");
			}*/

			int commLen = trade.getDownPack().getCommCellList().size(); 
			for(int i=0;i<commLen;i++){
				CommCell ccell = (CommCell)trade.getDownPack().getCommCellList().get(i);
				/*if(logger.isInfoEnabled()){
					logger.info("--------通讯区[ID："+ccell.getCommId()+" NAME:"+ccell.getCommName()+"]打包开始--------------");
				}*/
				packXML.PackComm(findCommItemsByCommId(ccell.getCommId()), trEntity, trXml);
				/*if(logger.isInfoEnabled()){
					logger.info("--------通讯区[ID："+ccell.getCommId()+"]打包结束--------------");
				}*/
			}
			/*if(logger.isInfoEnabled()){
				logger.info("--------交易[HostCode:"+trade.getHostCode()+" NAME:"+trade.getTrName()+"]请求XML打包结束--------------");
			}*/
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			logger.fatal("Call PackXML Except:"+e.getMessage());
			throw new Exception();
		}
    	
    	return trXml.getTradeXml() ;
    }
    
    /**
     * 根据交易码打应答XML报文
     * @param trCode
     * @param trEntity
     * @return  xml 报文
     */
    public String packReponseXML(String trCode,TradeEntity trEntity)throws Throwable{
    	TradeXml trXml = new TradeXml();
    	PackXML packXML = new PackXML();
    	//打包，选择下行报文格式
    	try {
			Trade  trade = findTradeByTrCode(trCode);
			if(logger.isInfoEnabled()){
				logger.info("--------交易[HostCode:"+trade.getHostCode()+" NAME:"+trade.getTrName()+" PACKNAME:"+trade.getUpPack().getName()+"]应答XML打包开始--------------");
			}
			
			int commLen = trade.getUpPack().getCommCellList().size(); 
			for(int i=0;i<commLen;i++){
				CommCell ccell = (CommCell)trade.getUpPack().getCommCellList().get(i);
				if(logger.isInfoEnabled()){
					logger.info("--------通讯区[ID："+ccell.getCommId()+" NAME:"+ccell.getCommName()+"]打包开始--------------");
				}
				packXML.PackComm(findCommItemsByCommId(ccell.getCommId()), trEntity, trXml);
				if(logger.isInfoEnabled()){
					logger.info("--------通讯区[ID："+ccell.getCommId()+"]打包结束--------------");
				}
			}
			if(logger.isInfoEnabled()){
				logger.info("--------交易[HostCode:"+trade.getHostCode()+" NAME:"+trade.getTrName()+"]应答XML打包结束--------------");
			}
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			logger.fatal("Call PackXML Except:"+e.getMessage());
			throw new Exception("Call PackXML Except:"+e.getMessage());
		}
    	
    	return trXml.getTradeXml() ;
    }
       
    
    /**
     * 根据交易码以请求报文格式解析XML流
     * @param trCode
     * @param strXML
     * @param 
     * @return 返回交易对象
     */
    public TradeEntity unPackRequestXML(String trCode,String strXML)throws Throwable{
    	TradeEntity trEntity = new TradeEntity();
    	UnPackXMLService unPackXML = new UnPackXMLService();
    	Trade trade = null ;
    	//根据交易上行报文对上行的XML流解析
    	try {
			 trade = findTradeByTrCode(trCode);
    	} catch (Throwable e) {
			// TODO Auto-generated catch block
    		logger.fatal("获取交易配置失败,交易码["+trCode+"]"+e.getMessage());
			throw new Exception("获取交易配置失败,交易码["+trCode+"]"+e.getMessage());
		}
		if(logger.isInfoEnabled()){
			logger.info("--------解析交易[HostCode:"+trade.getHostCode()+" NAME:"+trade.getTrName()+" PACKNAME:"+trade.getDownPack().getName()+"]请求XML开始--------------");
		}
		//请求报文中不会有响应信息
    	trEntity =	unPackXML.unPackXML(trade.getDownPack().getCommCellList(),strXML,false,xbi);
		if(logger.isInfoEnabled()){
			logger.info("--------解析交易[HostCode:"+trade.getHostCode()+"]请求XML结束--------------");
		}
		
    	return trEntity ;
    }
        
    
    /**
     * 根据交易码以应答报文格式解析XML流
     * 交易中的上行报文解析
     * @param trCode
     * @param strXML
     * @param unPackHead :true  需要解包头，false不解
     * @return 返回交易对象
     */
    public TradeEntity unPackReponseXML(String trCode,String strXML,boolean unPackHead)throws Throwable{
    	TradeEntity trEntity = new TradeEntity();
    	com.commman.aoto.UnPackXMLService unPackXML = new com.commman.aoto.UnPackXMLService();
    	Trade trade = null ;
    	//根据交易上行报文对上行的XML流解析
    	try {
			 trade = findTradeByTrCode(trCode);
    	} catch (Throwable e) {
			// TODO Auto-generated catch block
    		logger.fatal("获取交易配置失败,交易码["+trCode+"]"+e.getMessage());
			throw new Exception("获取交易配置失败:"+e.getMessage());
		}
		/*if(logger.isInfoEnabled()){
			logger.info("--------解析交易[HostCode:"+trade.getHostCode()+" NAME:"+trade.getTrName()+" PACKNAME:"+trade.getUpPack().getName()+"]应答XML开始，是否解响应结果["+unPackHead+"]--------------");
		}*/
		//请求报文中不会有响应信息
    	trEntity =	unPackXML.unPackXML(trade.getUpPack().getCommCellList(),strXML,unPackHead,xbi);
		/*if(logger.isInfoEnabled()){
			logger.info("--------解析交易[HostCode:"+trade.getHostCode()+"]应答XML结束--------------");
		}*/
    	return trEntity ;
    }
      
       
    /**
     * 获取XML中的主机交易吗
     * @param fromHostXml
     * @return
     */
	public String getHostCodeFromXML(String fromHostXml){
		String hostCode = "";
		UnPackXMLService unPackXML = new UnPackXMLService();		
		hostCode = unPackXML.getHostTrCode(fromHostXml,xbi.getHostCodeAttr());
		if(logger.isInfoEnabled()){
			logger.info("getHostCode succ:"+hostCode+" hostAttr:"+xbi.getHostCodeAttr());
		}
		return hostCode ;
		
	}
	
	/**
	 * 根据交易码获取主机交易码
	 * @param trCode
	 * @return
	 * @throws Throwable
	 */
	public  String getHostCodeByTrCode(String trCode)throws Throwable{
		Trade tr = findTradeByTrCode(trCode);
		return tr.getHostCode();		
	}
    
	/**
	 * 根据主机交易码获取交易码
	 * @param hostCode
	 * @return
	 * @throws Throwable
	 */
	public String getTrCodeByHostCode(String hostCode)throws Throwable{
		Trade tr = findTradeByHostCode(hostCode);
		return tr.getTrCode();
	}

}
