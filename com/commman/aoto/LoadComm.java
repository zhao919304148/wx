package com.commman.aoto;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.digester.Digester;

import com.commman.trademanager.xmlman.entity.Comm;
import com.commman.trademanager.xmlman.entity.Trade;

 /**
 * @ClassName  : LoadComm.java
 * @Brief      : [加载通讯区配置]
 * @Author     : huanghui
 * @CreateDate : Oct 27, 2008
 * @CopyRight  : .
 * @Descript   :[交易配置得加载] 
 *        No.1 : 加载交易配置
 *        No.2 : 加载通讯区配置    
 */
public class LoadComm {

	/**
	 * 同步对象
	 */
	private static final Object mutexObject = new Object();
	/**
	 * 唯一实例
	 */
	private static LoadComm instance = null;
	
	private String commDefXml = "c://itmconf//commdef.xml";   //通讯区配置文件
	
	private List commList = new ArrayList();  //通讯区对列
	
	//private CommQueue commQueue = new CommQueue();
	
	/**
	 * 构造函数禁止外部构造
	 */
	private LoadComm(String commDefXml ){
		this.commDefXml=commDefXml ;
	}
	private LoadComm(){
		
	}
	
	public Object clone(){
		
		LoadComm lc = new LoadComm();
		
		for( int i=0;i<commList.size();i++ ){
			
			lc.addComm(((Comm)((Comm)commList.get(i)).clone()));
		}
		return (Object)lc;
	}

	/**
	 * toString 方法
	 */
	public String toString(){
		
		StringBuilder toS = new StringBuilder("<comms>\n\r");
		
		for( int i=0; i<commList.size();i++ ){
			
			toS.append((Comm)commList.get(i)).toString(); 
		}
		
		toS.append("</comms>\n\r");
		
		return toS.toString();
	}
	
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main( String[] args ){
		
		try{
			String commfile = "D:\\SSH2ENV\\workspace\\flexserver\\WebRoot\\WEB-INF\\config\\xml\\commdef.xml";
			LoadComm  tf = LoadComm.getInstance( commfile);
			System.out.println( tf.toString() );
			
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
	public static LoadComm getInstance(String commDefXml) throws Exception{
		
		//注意这样写的理由，为了保证只有一个初始化过程
		synchronized( mutexObject ){
			if( instance != null ){	
				return instance;
			}
			LoadComm ins = new LoadComm(commDefXml );
			ins.init();
			instance = ins;
		}
		return instance;
	}
	
	/**
	 * 增加一个COMM
	 * @param menu
	 */
	public void addComm( Comm comm){
	
		this.commList.add(comm);
	}


	
	public List getCommList() {
		return commList;
	}
	public void setCommList(List commList) {
		this.commList = commList;
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
	//	buildTrade(this.trDefXml); //构建交易对象
		buildComm(this.commDefXml);  //构建通讯区
	}
		


	/**
	 * 初始化
	 * com.commman.trademanager.xmlman.entity
	 * @throws Exception
	 */
	private void buildComm(String commDefXml) throws Exception{
	
		Digester digester = new Digester();
		
		String itemObj = "com.commman.trademanager.xmlman.entity.Item" ;
		String commObj = "com.commman.trademanager.xmlman.entity.Comm";
		
		//This  method pushes this (SampleDigester) class to the Digesters
	    //object stack making its method s available to processing rules.
		digester.push(this);
		
		//通讯区构造
		{
			digester.addObjectCreate("comms/comm", commObj);
			digester.addSetProperties("comms/comm");			

				//item层次1
				{	
					digester.addObjectCreate("comms/comm/item",itemObj);
					digester.addSetProperties( "comms/comm/item" );
					
					//item层次2
					{
						digester.addObjectCreate("comms/comm/item/item",itemObj);
						digester.addSetProperties( "comms/comm/item/item" );
						
						//item层次3
						{
							digester.addObjectCreate("comms/comm/item/item/item",itemObj);
							digester.addSetProperties( "comms/comm/item/item/item" );
							
							//item层次4
							{
								digester.addObjectCreate("comms/comm/item/item/item/item",itemObj);
								digester.addSetProperties( "comms/comm/item/item/item/item" );
								
								//item层次5
								{
									digester.addObjectCreate("comms/comm/item/item/item/item/item",itemObj);
									digester.addSetProperties( "comms/comm/item/item/item/item/item" );
									{
										//item层次6
										digester.addObjectCreate("comms/comm/item/item/item/item/item/item",itemObj);
										digester.addSetProperties( "comms/comm/item/item/item/item/item/item" );
										digester.addSetNext("comms/comm/item/item/item/item/item/item","addItem",itemObj);
										
									}
									digester.addSetNext("comms/comm/item/item/item/item/item","addItem",itemObj);
								}
					
								digester.addSetNext("comms/comm/item/item/item/item","addItem",itemObj);
							
							}
				
							digester.addSetNext("comms/comm/item/item/item","addItem",itemObj);
						}
			
						digester.addSetNext("comms/comm/item/item","addItem",itemObj);
					}
					
					digester.addSetNext("comms/comm/item","addItem",itemObj);
				}
				digester.addSetNext("comms/comm","addComm",commObj);
			}
		digester.parse(new File(commDefXml));	
	
	}

	
	
}
