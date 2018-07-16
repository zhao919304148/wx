package com.commman.aoto;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.digester.Digester;

import com.commman.trademanager.xmlman.entity.Trade;

 /**
 * @ClassName  : LoadTrade.java
 * @Brief      : [交易配置得加载]
 * @Author     : huanghui
 * @CreateDate : Oct 27, 2008
 * @CopyRight  : .
 * @Descript   :[交易配置得加载] 
 *        No.1 : 加载交易配置
 *        No.2 : 加载通讯区配置    
 */
public class LoadTrade {

	/**
	 * 同步对象
	 */
	private static final Object mutexObject = new Object();

	/**
	 * 唯一实例
	 */
	private static LoadTrade instance = null;
	
	private String trDefXml = "c://itmconf//trdef.xml";       //交易配置文件

	private List trades = new ArrayList();    //交易队列

	/**
	 * 构造函数禁止外部构造
	 */
	private LoadTrade(String trDefXml){
		this.trDefXml = trDefXml ;
	}
	private LoadTrade(){
		
	}
	
	public Object clone(){
		
		LoadTrade tradeConfig = new LoadTrade();
		
		for( int i=0;i<trades.size();i++ ){
			
			tradeConfig.addTrade(((Trade)((Trade)trades.get(i)).clone()));
		}
		return (Object)tradeConfig;
	}

	/**
	 * toString 方法
	 */
	public String toString(){
		
		StringBuilder toS = new StringBuilder("<trades>\n\r");
		
		for( int i=0; i<trades.size();i++ ){
			
			toS.append((Trade)trades.get(i)).toString(); 
		}
		
		toS.append("</trades>\n\r");
		
		return toS.toString();
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main( String[] args ){
		
		try{
			
			String trfile = "D:\\SSH2ENV\\workspace\\flexserver\\WebRoot\\WEB-INF\\config\\xml\\trdef.xml";
			LoadTrade  tf = LoadTrade.getInstance(trfile);
			System.out.println(tf.toString());
			
		}catch( Exception e){
			
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取唯一事例
	 * 
	 * @return 
	 * @throws Exception
	 */
	public static LoadTrade getInstance(String trDefXml) throws Exception{
		
		//注意这样写的理由，为了保证只有一个初始化过程
		synchronized( mutexObject ){
			if( instance != null ){	
				return instance;
			}
			LoadTrade ins = new LoadTrade(trDefXml );
			ins.init();
			instance = ins;
		}
		return instance;
	}
	
	/**
	 * 增加一个菜单
	 * @param menu
	 */
	public void addTrade( Trade trade){
	
		this.trades.add(trade);
	}


	
	public List getTrades() {
		return trades;
	}
	public void setTrades(List trades) {
		this.trades = trades;
	}
	
	/**
	 * 初始化
	 * @throws Exception
	 */
	/**
	 * 初始化
	 * @throws Exception
	 */
	private  synchronized void init() throws Exception{
		buildTrade(this.trDefXml); //构建交易对象
	}
		
	
	
	private void buildTrade(String trDefXml) throws Exception{
	
		Digester digester = new Digester(); 
		
		String tradeObj = "com.commman.trademanager.xmlman.entity.Trade";
		String commCellObj = "com.commman.trademanager.xmlman.entity.CommCell";
		String downObj = "com.commman.trademanager.xmlman.entity.DownPack";
		String upObj = "com.commman.trademanager.xmlman.entity.UpPack";

		digester.push(this);
		
		//交易配置
		{
			digester.addObjectCreate("trades/trade", tradeObj);
			digester.addSetProperties("trades/trade");			

			//downpack
			{
				digester.addObjectCreate("trades/trade/downpack",downObj);
				digester.addSetProperties( "trades/trade/downpack" );
				
				//item层次1
				{	
					digester.addObjectCreate("trades/trade/downpack/comms/comm",commCellObj);
					digester.addSetProperties( "trades/trade/downpack/comms/comm" );
					//item层次2			
					
					digester.addSetNext("trades/trade/downpack/comms/comm","addCommCell",commCellObj);
				}
				
				digester.addSetNext("trades/trade/downpack", "setDownPack", downObj);
			}
			
			//uppack
			{
				digester.addObjectCreate("trades/trade/uppack",upObj);
				digester.addSetProperties( "trades/trade/uppack" );
				
				//item层次1
				{	
					digester.addObjectCreate("trades/trade/uppack/comms/comm",commCellObj);
					digester.addSetProperties( "trades/trade/uppack/comms/comm" );
					//item层次2
					digester.addSetNext("trades/trade/uppack/comms/comm","addCommCell",commCellObj);
				}
				digester.addSetNext("trades/trade/uppack", "setUpPack", upObj);
			}
			digester.addSetNext("trades/trade", "addTrade", tradeObj);
		}
		digester.parse(new File(trDefXml));	
	}
}
