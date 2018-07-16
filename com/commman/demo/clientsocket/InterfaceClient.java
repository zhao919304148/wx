/**
 * 文件名：InterfaceClient.java
 * 描  述：接口程序，客户端
 * 设计人：韩林平
 * 日  期：2003-02-11
 */
 
package com.commman.demo.clientsocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.StringTokenizer;
public class InterfaceClient extends Thread{ 
//-----------------------------------------------------------------------------
    
	private  String sendStr = "";
	
	
     /**
	 * 
	 */
	public InterfaceClient(String sendStr) {
		//super();
		// TODO Auto-generated constructor stub
		this.sendStr = sendStr ;
		this.start();
	}
	public String getQuery(String strSendData)
     {
     	 String         strResult      = "";
         Socket         mySocket          = null;
	 BufferedReader receiveFromServer = null;
	 PrintWriter    sendToServer      = null;

         String         strServerIP       = "192.168.1.103";
         int         iServerPort     = 2502 ;

	 try
	 {	
	 //	System.out.println(strServerIP+" "+iServerPort);	
		 
		mySocket = new Socket(strServerIP,iServerPort);	
	//	System.out.println("进入发送线程 socket :"+ mySocket);
		sendToServer = new PrintWriter(mySocket.getOutputStream(),true);	
		sendToServer.println(strSendData);
		System.out.println("CLIENT 发送："+strSendData);
		
		receiveFromServer = new BufferedReader(new InputStreamReader(mySocket.getInputStream(),"GBK"));			 
		strResult=receiveFromServer.readLine();		
		System.out.println("CLIENT 返回："+strResult);
		
		sendToServer.close();
		receiveFromServer.close();
		mySocket.close();			  
	  }
	  catch (IOException e)
	  {
			System.out.println("client error--"+e.getMessage());
			return null;
	  }
          return strResult;         
     }
//-----------------------------------------------------------------------------
     //传入一行参数向服务器端查询返回多行结果
     public LinkedList getQuerySet(String strSendData)
     {
     	 LinkedList     datalist          = new LinkedList();     	 
     	 String         strResult         = "";
         Socket         mySocket          = null;
	 BufferedReader receiveFromServer = null;
	 PrintWriter    sendToServer      = null;
     String         strServerIP       = "127.0.0.1";
     int         iServerPort     = 2502 ;
	 try
	 {	
	 	System.out.println(strServerIP+" "+iServerPort);			 
		mySocket = new Socket(strServerIP,iServerPort);	
			 
		receiveFromServer = new BufferedReader(new InputStreamReader(mySocket.getInputStream(),"ISO-8859-1"));				 
		sendToServer = new PrintWriter(mySocket.getOutputStream(),true);	
		sendToServer.println(strSendData);
		strResult=receiveFromServer.readLine();	
		StringTokenizer tokens;
        tokens = new StringTokenizer(strResult);
        String strRowNumber = tokens.nextToken("#");
		int iRowNumber=Integer.parseInt(strRowNumber);
		for(int i=1;i<=iRowNumber;i++)
		{
		     String  strRowData = tokens.nextToken("#");			
		     datalist.add(strRowData);
		}				
		sendToServer.close();
		receiveFromServer.close();
		mySocket.close();			  
	  }
	  catch (IOException e)
	  {
			System.out.println("client error--"+e.getMessage());
			return null;
	  }
          return datalist;         
     }
    
     
     
//-----------------------------------------------------------------------------
     static public void main(String args[]) {
//    	 for(int i=0 ;i< 2 ;i++){
    	 String sendStr = 
    	 		"<?xml version='1.0' encoding='GB2312'?>" +
    	 		"<PACKET type='REQUEST' version='1.0'>" +
	    	 		"<HEAD>" +
		    	 		"<TRANSTYPE>SYN</TRANSTYPE>" +
		    	 		"<TRANSCODE>100</TRANSCODE>" +
		    	 		"<USER></USER>" +
		    	 		"<PASSWORD></PASSWORD>" +
		    	 		"<SVCSEQNO></SVCSEQNO>" +
	    	 		"</HEAD>" +
	    	 		"<BODY>" +
	    	 		    "<TEAMID>1102</TEAMID>" +
	    	 		    "<AGENTID>HUANGHUI</AGENTID>" +
	    	 		"</BODY>" +
    	 		"</PACKET>";

    	 
//    	 InterfaceClient interfaceClient = new InterfaceClient(sendStr) ;
    	//	 InterfaceClient interfaceClient = new InterfaceClient("测试数据socket第【"+i+"】次") ;
    	    //String rtn = interfaceClient.getQuery("测试数据socket第【"+i+"】次");
    	    //System.out.print("服务器返回第【 "+i+"】："+ rtn);
  //  	 }
     }
	public void run() {
		// TODO Auto-generated method stub
		getQuery(this.sendStr) ;
	}
}