
package com.commman.demo;

import java.util.HashMap;

import com.commman.trademanager.entity.DataQueueEntity;
import com.commman.trademanager.entity.TradeEntity;

 /**
 * @ClassName  : Trade2xls.java
 * @Brief      : [交易对象应用]
 * @Author     : huanghui
 * @CreateDate : Nov 5, 2008
 * @CopyRight  : 
 * @Descript   :[class struct descript] 
 *        No.1 :     
 */
public class TradeEntityUseDemo {
	


	public static void main(String[] args) throws Exception {
		
		  /**
		   * 交易对象中的单值demo
		   * 
		   * 注意 ： single 对应xml中的属性名称只能出现一次，
		   *   如果同样的名称会出现多个地方，必须以数据队列方式打包/解包
		   */
		   
		   TradeEntity  tr = new TradeEntity();
		   //RptSvcService rptSvcService = new RptSvcService();
		   HashMap sigMap = new HashMap();
		   sigMap.put("name", "测试人员");
		   sigMap.put("date", "2008-10-11");
		   sigMap.put("age", "23");
		   tr.setSingleMap(sigMap);
		   
		   
			  /**
			   * 交易对象中的循环数【队列】demo
			   * 
			   * 注意 ： single 对应xml中的属性名称只能出现一次，
			   *   如果同样的名称会出现多个地方，必须以数据队列方式打包/解包
			   */
			   
			   TradeEntity  trQue = new TradeEntity();
			   /**
			    * DataQueueEntity 表示交易对象中的队列
			    * 一个交易中会有多个队列，以队列数组方式存放
			    */
			   DataQueueEntity driverQ = new DataQueueEntity();
			   driverQ.setQueueName("driverList");
			   for(int i=0;i<20;i++){			   
				   HashMap duMap = new HashMap();
				   duMap.put("编号", "10035"+i);
				   duMap.put("姓名", "driver人员"+i);
				   duMap.put("日期", "2008-10-11");
				   duMap.put("年龄", "23"+i);
				   
				   //向队列中增加一条记录
				   driverQ.addRecord(duMap);
			   }	
			   //获取某队列的长度，如驾驶员个数
			   int driverNum = driverQ.length();
			   
			   //向交易实例中增加一个队列
			   trQue.addDataQueue(driverQ);
			   
			   //获取交易实例中的队列个数
			   int dqNum = trQue.lengthDQA();
		
		//   trEntity =  rptSvcService.MainService(1001,null);
		  
		
	}

		

}
