package com.commman.client;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.log4j.Logger;

import com.sys.exception.EpiccException;



/**
 * 客户端调用接口方法
 * @author  
 * @version 2.0
 */
public class SimpleClient {	
	static Logger logger = Logger.getLogger(SimpleClient.class);
	public static String offerChannelHandler(String reqPostXml,final String targetURI ,final String namespaceURI, final String localPart) throws EpiccException {
		String responseXml="";
		// 使用RPC方式调用WebService        
		RPCServiceClient serviceClient=null;
		Options options=null;
		EndpointReference targetEPR=null;
		Object[] opAddEntryArgs=null;
		QName opAddEntry=null;
		try {
			serviceClient = new RPCServiceClient();
	        options = serviceClient.getOptions();
	        targetEPR = new EndpointReference(targetURI);
	        options.setTo(targetEPR);
	        logger.warn("请求报文："+reqPostXml);
	        //  指定方法的参数值
	        opAddEntryArgs = new Object[] {reqPostXml};
	       
	        //  指定方法返回值的数据类型的Class对象
	        Class[] classes = new Class[] {String.class};
	       
	        //  指定要调用的方法及WSDL文件的命名空间
	        opAddEntry = new QName(namespaceURI, localPart);
	        
	        // 调用方法并输出该方法的返回值
	        responseXml = (String) serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];
	        logger.warn(responseXml);
	     
		} catch (AxisFault e) {
			System.out.println("=============="+e.getMessage());
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new EpiccException("接口异常 ："+e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new EpiccException("接口异常 ："+e.getMessage());
		} finally {
			try {
				serviceClient.cleanupTransport();
				serviceClient.cleanup();
				serviceClient = null;
				if (options != null) {
					options = null;
				}
				if (targetEPR != null) {
					targetEPR = null;
				}
				if (opAddEntry != null) {
					opAddEntry = null;
				}
				if (opAddEntryArgs != null) {
					opAddEntryArgs = null;
				}
			} catch (AxisFault e) {
				e.printStackTrace();
			}
		}
		return responseXml;
	
	}
}