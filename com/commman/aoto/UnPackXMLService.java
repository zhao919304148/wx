package com.commman.aoto;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.commman.trademanager.entity.DataQueueEntity;
import com.commman.trademanager.entity.TradeEntity;
import com.commman.trademanager.entity.XmlBaseInfoEntity;
import com.commman.trademanager.xmlman.entity.CommCell;
import com.commman.trademanager.xmlman.entity.Item;
import com.commman.trademanager.xmlman.entity.Trade;
import com.commman.trademanager.xmlman.service.XmlElement;
import com.commman.trademanager.xmlman.service.XmlException;
import com.commman.trademanager.xmlman.service.XmlParser;

public class UnPackXMLService {

	private com.commman.aoto.TradeManager4XML tm = null ;
	private Logger logger=Logger.getLogger("UnPackXMLService.class"); 
	
    /**
	 * @param tm
	 * @param logger
	 */
	public UnPackXMLService() {
		super();
		try {
			this.tm = TradeManager4XML.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("初始化交易管理器失败："+e.getMessage());
		}
	}


	/**
     * 根据交易上行报文对上行的XML流解析
     * @param trCode
     * @param strXML
     * @param unpackHead  ：true表示解包头，用于应答包的解析
     *                      false 不解包头，用于flex系统内部的请求交易
     * @param XmlBaseInfoEntity :xml基本信息包
     * @return
     */
    public TradeEntity unPackXML(Trade trade,String strXML,boolean unPackHead,XmlBaseInfoEntity xbi){
    	TradeEntity trEntity = new TradeEntity();
		XmlParser parse = new XmlParser();	//解析xml流		
		String xmlBasePath = xbi.getXmlRoot() ;
		try {
			parse.createDoc( strXML );
			
			if(unPackHead){
				//解包头
		        if(UnPackHead(trEntity,parse,xbi) < 0){
		        	return trEntity ;  //交易结果失败，放弃xml正文解析
		        }
				
			}
	    	
	    	//根据交易上行报文对上行的XML流解析
	        //循环解析通讯区,通讯区不允许嵌套
			
			int commLen = trade.getUpPack().getCommCellList().size(); 
			for(int i=0;i<commLen;i++){
				//取交易通讯区
				CommCell ccell = (CommCell)trade.getUpPack().getCommCellList().get(i);
			    if(logger.isInfoEnabled()){
					logger.info("--------通讯区[ID："+ccell.getCommId()+" NAME:"+ccell.getCommName()+"]解包开始--------------");
			    }
				//取通讯区items
				List items = tm.findCommItemsByCommId(ccell.getCommId()) ;
				if(items.size()<1) continue ;
				//检查item是否为 tab 括号
				Item item = (Item)items.get(0);
				if("".equals(item.getCtype())){
					item.setCtype("CELL");
				}
				//tab标签通讯区只能有一条item
				if("TAB".equalsIgnoreCase(item.getCtype())){
					//标签项处理
				if(item.getTattr().indexOf("/")<0){
						//开始标签
						xmlBasePath += "/"+item.getTattr();
					}else{
						//结束标签，去掉路径中的标签项
						//logger.info("base1:"+xmlBasePath + " tabidx:"+xmlBasePath.indexOf(item.getTattr()));
						xmlBasePath = xmlBasePath.substring(0,xmlBasePath.indexOf(item.getTattr()));
					}
					/*if(logger.isInfoEnabled()){
						logger.info("解标签项["+item.getTattr()+"]xmlPath["+xmlBasePath+"]") ;
					}*/					
					continue ;
				}
				
				unPackComm(items, trEntity, strXML,parse,xmlBasePath);
				if(logger.isInfoEnabled()){
					logger.info("--------通讯区[ID："+ccell.getCommId()+"]解包结束--------------");
				}
				
			}
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			trEntity.setErrCode("9999");
			trEntity.setErrMsg("主机应答报文解析失败!"+e.getMessage());
			logger.error("unPackXML xml流解析失败！"+e.getMessage());
			
		}
		 catch (Throwable e) {
			// TODO Auto-generated catch block
				trEntity.setErrCode("9998");
				trEntity.setErrMsg("交易解析失败!"+e.getMessage());
				logger.error("unPackComm ！"+e.getMessage());
		}
		
    	return trEntity ;
    }
	

	/**
     * 根据交易上行报文对上行的XML流解析
     * @param trCode
     * @param strXML
     * @param unpackHead  ：true表示解包头，用于应答包的解析
     *                      false 不解包头，用于flex系统内部的请求交易
     * @param XmlBaseInfoEntity :xml基本信息包
     * @return
     */
    public TradeEntity unPackXML(List commCellList,String strXML,boolean unPackHead,XmlBaseInfoEntity xbi)throws Exception{
    	TradeEntity trEntity = new TradeEntity();
		XmlParser parse = new XmlParser();	//解析xml流		
		String xmlBasePath = xbi.getXmlRoot() ;
		try {
			parse.createDoc( strXML );
			
			if(unPackHead){
				//解包头
		        if(UnPackHead(trEntity,parse,xbi) < 0){
		        	return trEntity ;  //交易结果失败，放弃xml正文解析
		        }
				
			}
	    	
	    	//根据交易上行报文对上行的XML流解析
	        //循环解析通讯区,通讯区不允许嵌套
			
			int commLen = commCellList.size(); 
			for(int i=0;i<commLen;i++){
				//取交易通讯区
				CommCell ccell = (CommCell)commCellList.get(i);
			    /*if(logger.isInfoEnabled()){
					logger.info("--------通讯区[ID："+ccell.getCommId()+" NAME:"+ccell.getCommName()+"]解包开始--------------");
			    }*/
				//取通讯区items
				List items = tm.findCommItemsByCommId(ccell.getCommId()) ;
				if(items.size()<1) continue ;
				//检查item是否为 tab 括号
				Item item = (Item)items.get(0);
				if("".equals(item.getCtype())){
					item.setCtype("CELL");
				}
				//tab标签通讯区只能有一条item
				if("TAB".equalsIgnoreCase(item.getCtype())){
					//标签项处理
				if(item.getTattr().indexOf("/")<0){
						//开始标签
						xmlBasePath += "/"+item.getTattr();
					}else{
						//结束标签，去掉路径中的标签项
						//logger.info("base1:"+xmlBasePath + " tabidx:"+xmlBasePath.indexOf(item.getTattr()));
						xmlBasePath = xmlBasePath.substring(0,xmlBasePath.indexOf(item.getTattr()));
					}
					/*if(logger.isInfoEnabled()){
						logger.info("解标签项["+item.getTattr()+"]xmlPath["+xmlBasePath+"]") ;
					}*/					
					continue ;
				}
				
				unPackComm(items, trEntity, strXML,parse,xmlBasePath);
				/*if(logger.isInfoEnabled()){
					logger.info("--------通讯区[ID："+ccell.getCommId()+"]解包结束--------------");
				}*/
				
			}
		} catch (XmlException e) {
			logger.error("unPackXML xml流解析失败！"+e.getMessage());
			throw new Exception("报文格式不正确！");
			
		} catch (Throwable e) {
			logger.error("unPackComm ！"+e.getMessage());
			throw new Exception("XML流解析失败,未知异常！");
		}
		
    	return trEntity ;
    }
		
	/**
	 * 根据通讯区定义解析报文
	 * @param items 
	 * @param toTMData
	 * @param fromHostXml
	 * @return
	 */
	public int unPackComm(List items,TradeEntity trEntity,String fromHostXml,XmlParser parse,String xmlBasePath){
		int rtn = 0 ;
        
		//组上行通讯报
		//toHXml.setTHXml("<?xml version=\"1.0\" encoding=\"GBK\"?> \n\r <PACKET type=\"REQUEST\" version=\"1.0\">");
        for (int i = 0; i < items.size(); i++) {
			Item item =(Item)items.get(i);			
			//解包ti
			//首层 
			if(item.getItems()==null||item.getItems().size()==0)
			    rtn =UnPackCell(xmlBasePath,item,trEntity.getSingleMap(),parse,0);
			else
			    rtn = UnPackNode(xmlBasePath,item,trEntity,parse); 
	        if(rtn < 0){
	        	return rtn ;
	        }
		}
        
		return 0 ;
	}

	/*
	 * 阳光财产险中间业务平台返回报文头格式
	<PACKET type="RESPONSE" version="1。0">
		<HEAD>
		<TRANSTYPE>SNY</TRANSTYPE>
		<TRANSCODE>81000</TRANSCODE>
		< RESPONSECODE>0000</ RESPONSECODE>
		<ERRORMESSAGE>成功</ ERRORMESSAGE>
		< SVCSEQNO >2006070700001</ SVCSEQNO >
		</HEAD>
		...

*/

	public String getHostTrCode(String fromHostXml,String hostCodeAttr){
		String trCode="-1";
		if ("".equals(hostCodeAttr)){
			hostCodeAttr = "/PACKET/HEAD/TRANSCODE";
		}
		XmlParser parse = new XmlParser();			
		try {
			parse.createDoc( fromHostXml );
			trCode = ParseCell(parse,hostCodeAttr,0);
		} catch (XmlException e) {
			// TODO Auto-generated catch block
            logger.error("取主机交易码失败，XML路径："+hostCodeAttr);
		}        
		return trCode ;
	}
	
	
	
	/**
	 * 解返回包头
	 * @param toItmData    :返回电销控制层的数据
	 * @param fromHostPack :主机返回的xml数据
	 * @return
	 */
	public int UnPackHead(TradeEntity trEntity,XmlParser parse,XmlBaseInfoEntity xbi){
		
		String errCode =ParseCell(parse,xbi.getResponseCodeAttr(),0);
		if(errCode == null||"".equals(errCode)){ //不存在，表示错误
			errCode = "9999";
            trEntity.setErrCode(errCode);
            trEntity.setErrMsg("系统错误，应答包响应码不存在！");
            return -1 ;
		}	
		
		String succFlag = xbi.getResponseSuccFlag().toString();
		//错误码, 如果=0000表示成功,否则表示具体错误代码 .
		/*if(logger.isInfoEnabled()){
			logger.info("解析返回包头："+succFlag + " ERRCODE:" +"["+errCode.trim()+"]");
		}*/
		int pos = succFlag.indexOf("["+errCode.trim()+"]");
		if(xbi.getResponseSuccFlag().toString().indexOf("["+errCode.trim()+"]")>= 0){
			//成功
        	trEntity.setErrCode("0");
        	trEntity.setErrMsg(ParseCell(parse,xbi.getErrorMessageAttr(),0));        	
		}else{
	           trEntity.setErrCode(errCode);
	           trEntity.setErrMsg(ParseCell(parse,xbi.getErrorMessageAttr(),0));
			   return -1 ;
		}
	
		return 0 ;
	}
	
	
	
	/**
	 * 解析单个属性
	 * @param parse
	 * @param fldName
	 * @return
	 */
	public String ParseCell(XmlParser parse,String fldName,int idx){
        List L1;
		try {
           String attrVal = "";			
			L1 = parse.getElements(fldName);
			if( L1 != null && L1.size() > 0 ){
				if(idx >= L1.size())idx = 0 ;
				XmlElement element = (XmlElement)L1.get( idx );
				attrVal = element.getValue()==null?"":element.getValue();
				return  attrVal ;
		    }
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			logger.error("ParseCell attrName:"+ fldName +"]异常,原因:"+ e.getMessage());
		}	
		return "" ; 
	}

    /**
     * 根据交易通讯区定义解属性值
     * @param item
     * @param has
     * @param toHXml
     * @return
     */
	public 	int UnPackCell(String preNodeStr,Item item,HashMap has,XmlParser parse,int idx){
		//pack cell	
			String tAttr = item.getTattr().trim() ;  //电销中对应的属性名称
			String hAttr = item.getTattr().trim() ;  //上送主机的属性名称
			String dataType = item.getPtype();       //数据类型  char ，number 
			String attVal = ParseCell(parse,preNodeStr+"/"+hAttr,idx);
			if("NUMBER".equalsIgnoreCase(dataType)&& "".equals(attVal))
				attVal = "0";
			/*if(logger.isInfoEnabled()){
				logger.info("UnPackCell attrName:" + hAttr + " val:" + attVal + " posIdx:" + idx);
			}*/
			has.put(tAttr,attVal);
		    return 0 ;	
	}	

	public 	int UnPackNode(String preNodeStr,Item item,TradeEntity trEntity,XmlParser parse){

//		if(item.getHattr().equalsIgnoreCase("HEAD")) return 0;
		
		//
		preNodeStr += "/"+item.getHattr();
		
		//String preNodeStr = 
		for(int i=0;i<item.getItems().size();i++){
			Item item1 = (Item)item.getItems().get(i);
		
			
			
			if(item1.getCtype()==null || item1.getCtype().equalsIgnoreCase("CELL")){
				//组cell报
				UnPackCell(preNodeStr,item1,trEntity.getSingleMap(),parse,0);
			}
			
			//组node报, 该节点在 toTr 包里没有对应数据
			if(item1.getCtype().equalsIgnoreCase("NODE")){
			//	UnPackNode(preNodeStr+"/"+item1.getHattr(),item1,trEntity,parse);
				UnPackNode(preNodeStr,item1,trEntity,parse);
			}
			
			//组loopNode报
			if( item1.getCtype().equalsIgnoreCase("LOOPNODE")){
	//			UnPackLoopNode(preNodeStr+"/"+item1.getHattr(),item1,trEntity,parse);
				UnPackLoopNode(preNodeStr,item1,trEntity,parse);
			}
		}						
		
		return 0 ;
		
	}
	
	public 	int UnPackLoopNode(String preNodeStr,Item item,TradeEntity trEntity,XmlParser parse){

	        //判断 toTr 数据是否存在，如果没有则跳过组报
		   String tAttr = item.getTattr().trim();
		   
		   //循环数据
		   //一个数据队列只能有一种类型的明细 , 如 cars.car  
			List loopAttr = new LinkedList();
			String fldName = item.getHattr().trim();
			
			//取明细笔数 
			//取循环列的根节点名称
	        List L1;
	        List children = null ;
	        XmlElement element = null ;
			try {
			//	L1 = parse.getElements(preNodeStr +"/"+ fldName);
				L1 = parse.getElements(preNodeStr);
				if( L1 != null && L1.size() > 0 ){
					element = (XmlElement)L1.get( 0 );		
			    }else
			    {
			    	//System.out.println("UpackCell["+ fldName +"]异常,原因:"+ preNodeStr + "无下级节点");
			    	return  -1 ;
			    }
	            children = element.getChildren();
				
			} catch (XmlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("UpackCell["+ fldName +"]异常,原因:"+ e.getMessage());
				trEntity.setErrCode("9999");
				trEntity.setErrMsg("UpackCell["+ fldName +"]异常,原因:"+ e.getMessage());
				return -1 ;
			}	
			
			//取子节点list并遍历子节点的属性值			
			preNodeStr += "/"+ fldName ;
			for (int childrenNum = 0; childrenNum < children.size(); childrenNum++) {
				//取子节点
				XmlElement element1 = (XmlElement)children.get(childrenNum);
	            HashMap has = new HashMap();
				for(int i=0;i<item.getItems().size();i++){
					Item item1 = (Item)item.getItems().get(i);	
					//preNodeStr += "/"+ item1.getHattr();
					
					if(item1.getCtype()==null || item1.getCtype().equalsIgnoreCase("CELL")){
						//组cell报
						UnPackCell(preNodeStr,item1,has,parse,childrenNum);
					}
					
					//组node报, 该节点在 toTr 包里没有对应数据
					if(item1.getCtype().equalsIgnoreCase("NODE")){
						System.out.println("UnPack LoopNode Inner node ["+item1.getHattr()+"] is abounded !");
					//	UnPackNode(item1,trEntity,parse);
					}
					
					//组loopNode报
					if( item1.getCtype().equalsIgnoreCase("LOOPNODE")){
						System.out.println("UnPack LoopNode Inner loopNode ["+item1.getHattr()+"] is abounded !");

						//UnPackNode(item1,trEntity,parse);
					}
				}
				loopAttr.add(has);
				
			}
			//
            //打返回tm的数据列
			DataQueueEntity dataQueue= new DataQueueEntity(tAttr,loopAttr);
			trEntity.addDataQueue(dataQueue);
			return 0;
	}
	
	
	
	
	/**
	 * 解产险核心系统主动上送报文
	 *   含请求头,这里只解body
	 * @param items
	 * @param trEntity
	 * @param fromHostXml
	 * @return
	 */
	public int UnPackHostBodyXml(List items,TradeEntity trEntity,String fromHostXml){
		int rtn = 0 ;
		XmlParser parse = new XmlParser();			
		try {
			parse.createDoc( fromHostXml );
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			trEntity.setErrCode("9999");
			trEntity.setErrMsg("主机应答报文解析失败!");
			return -1 ;
		}
        
		//toHXml.setTHXml("<?xml version=\"1.0\" encoding=\"GBK\"?> \n\r <PACKET type=\"REQUEST\" version=\"1.0\">");
        String preNodeStr = "/PACKET" ;
        for (int i = 0; i < items.size(); i++) {
			Item item =(Item)items.get(i);			
			//解包ti
			//首层 
			
			rtn = UnPackNode(preNodeStr,item,trEntity,parse);
	        if(rtn < 0){
	        	return rtn ;
	        }
		}
        
		return 0 ;
	}


	
	
	
	
	
}
