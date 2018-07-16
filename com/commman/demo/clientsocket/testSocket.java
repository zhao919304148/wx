package com.commman.demo.clientsocket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class testSocket {
	//　　表示新建了一个线程池，线程池里面有10个线程为任务队列服务。
  	ExecutorService clientpool = Executors.newFixedThreadPool(50);
	
	private  void send(String str){
		
		clientpool.execute(new InterfaceClient(str));	
	}
	
	static public void main(String args[]) {
   	 for(int i=0 ;i< 5000 ;i++){
   		testSocket tsocket = new testSocket();
   		System.out.println("开始获取线程第【"+i+"】次");
	    tsocket.send("测试数据socket第【"+i+"】次");
   		System.out.println("获取线程成功第【"+i+"】次");
	 }
	}

	   
}
