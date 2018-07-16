package com.wap.kfcenter.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.commman.client.SimpleClient;
import com.wx.util.Md5Util;
import com.wx.util.WeixinUtil;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//WeixinUtil.getUserNameFormPICC("oXHv4jmszxDgkRolbiTzcA0lA8iA");
		//--------------------------测试连接总公司接口----------------------------------
		//String requestUrl = "http://11.207.3.162:7810/wx_interface/SMSPOpeningInterfaceService.aspx";
		String requestUrl = "http://10.133.12.20:7810/wx_interface/SMSPOpeningInterfaceService.aspx";
		Map<String, String> returnmap = new HashMap<String, String>();
		Map<String,Map<String,String> >data = new HashMap<String, Map<String,String>>();
		Map<String ,String > dataHead = getRequestHeadPICC("GetUserName");
		Map<String ,String > dataBody = new HashMap<String,String>();
		dataBody.put("openid", "oXHv4jmszxDgkRolbiTzcA0lA8iA");
		data.put("head", dataHead);
		data.put("body", dataBody);
		JSONObject jsonObject = new JSONObject();
		jsonObject= JSONObject.fromObject(data);
		jsonObject = WeixinUtil.httpRequestPICC(requestUrl, "POST", jsonObject.toString());
		String retcode = "";
		String retmsg = "";
		String seqno = "";
		if (null != jsonObject) {
			Map<String,Object> returnHead = (Map<String,Object>)jsonObject.get("head");
			Map<String,Object> returnBody = (Map<String,Object>)jsonObject.get("body");
			try {
				if(returnHead != null){
					retcode = returnHead.get("retcode").toString();
					retmsg = returnHead.get("retmsg").toString();
					seqno = returnHead.get("seqno").toString();
					returnmap.put("retcode", retcode);
					returnmap.put("retmsg", retmsg);
					if(dataHead.get("seqno").equals(seqno)){
						if("0".equals(retcode)){
							if(returnBody != null){
								returnmap.put("openid", returnBody.get("openid").toString());
								returnmap.put("username", returnBody.get("username").toString());
								returnmap.put("identifyno", returnBody.get("identifyno").toString());
								returnmap.put("identifytype", returnBody.get("identifytype").toString());
								System.out.println("return openid=====" + returnBody.get("openid").toString());
								System.out.println("return openid===" + returnBody.get("openid"));
								System.out.println("return username===" + returnBody.get("username"));
								System.out.println("return identifyno===" + returnBody.get("identifyno"));
								System.out.println("return identifytype===" + returnBody.get("identifytype"));
							}else{
								returnmap.put("retcode", "1");
								returnmap.put("retmsg", "接口返回报文体信息为空！");
								System.out.println("接口返回报文体信息为空！");
							}
						}
					}else{
						returnmap.put("retcode", "1");
						returnmap.put("retmsg", "接口返回报文信息与请求信息不是一对！");
						System.out.println("接口返回报文信息与请求信息不是一对！");
					}
				}else{
					returnmap.put("retcode", "1");
					returnmap.put("retmsg", "接口返回报文头信息错误！");
					System.out.println("接口返回报文头信息错误！");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//--------------------------测试连接总公司接口----------------------------------
		
		//--------------------------测试本地接口-------------------------------------
		//String sgaddress = "http://12.1.80.68:5709/piccbjplat/services/WxlpInterManage";
//		String sgaddress = "http://localhost:7001/piccbjplat/services/WxKfInterManage";
//		//String namespaceURI = "http://localhost:7001/piccbjplat/services/WxKfInterManage";
//		String namespaceURI = "http://facade.service.piccbjwx.picc.com";
//		String xml = getXml();
//		String fromHostXml="";
//		try {
//			fromHostXml = SimpleClient.offerChannelHandler(xml,sgaddress,namespaceURI,"getWxkfActiveExchange");
//			//result = HTTPRequestUtil.sendCxfClient(new URL(url), new QName(namespaceURI), new QName(namespaceURI), "getWxkfActiveExchange", xml);
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(StringUtils.isNotEmpty(fromHostXml)){
//			System.out.println("返回报文：" +fromHostXml);
//		}else{
//			System.out.println("没有返回报文");
//		}
		//----------------------------测试本地接口---------------------------------------

	}
	
	private static Map<String,String> getRequestHeadPICCTest(String method){
    	Date currDate = new Date();
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMMddHHmmss");
		String flowintime = smf.format(currDate);
		String millis = String.valueOf(System.currentTimeMillis());
		String seqno=flowintime+millis.substring(millis.length()-5);
		Map<String ,String > dataHead = new HashMap<String,String>();
		dataHead.put("cmd", method);
		dataHead.put("seqno", seqno);
		dataHead.put("flowintime", flowintime);
		dataHead.put("userid", "picc-bj");
		dataHead.put("token", Md5Util.MD5("picc-bj"+flowintime+"wx6b1ac82950f50fa5").toUpperCase());
		dataHead.put("request_id", "");
		dataHead.put("server_version", "");
		return dataHead;
    }
	
	private static Map<String,String> getRequestHeadPICC(String method){
    	Date currDate = new Date();
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMMddHHmmss");
		String flowintime = smf.format(currDate);
		String millis = String.valueOf(System.currentTimeMillis());
		String seqno=flowintime+millis.substring(millis.length()-5);
		Map<String ,String > dataHead = new HashMap<String,String>();
		dataHead.put("cmd", method);
		dataHead.put("seqno", seqno);
		dataHead.put("flowintime", flowintime);
		dataHead.put("userid", "picc-bj");
		dataHead.put("token", Md5Util.MD5("picc-bj"+flowintime+"wx953a63ae6f5582f1").toUpperCase());
		dataHead.put("request_id", "");
		dataHead.put("server_version", "");
		return dataHead;
    }
	
	private static String getXml(){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		sb.append("<PACKET type=\"REQUEST\" version=\"1.0\">");
		sb.append("<HEAD>");
		sb.append("<TRANS_TYPE>1017</TRANS_TYPE>");
		sb.append("<TRANS_USER>piccbjwxid</TRANS_USER>");
		sb.append("<TRANS_PWD>piccbjwx@123</TRANS_PWD>");
		sb.append("<SERIALNO>12334556677</SERIALNO>");
		sb.append("</HEAD>");
		sb.append("<BODY>");
		sb.append("<BASE_PART>");
		sb.append("<OPENID>oXHv4jmszxDgkRolbiTzcA0lA8iA</OPENID>");
		sb.append("<POLICYNO>PJAZ201611010000000909</POLICYNO>");
		sb.append("<IDENTIFYTYPE>01</IDENTIFYTYPE>");
		sb.append("<IDENTIFYNUMBER>230223198301203034</IDENTIFYNUMBER>");
		sb.append("</BASE_PART>");
		sb.append("<EXCHANGE_LIST>");
		sb.append("<EXCHANGE_DATA>");
		sb.append("<EXCHANGEPRICE>100</EXCHANGEPRICE>");
		sb.append("<EXCHANGENUM>2</EXCHANGENUM>");
		sb.append("</EXCHANGE_DATA>");
		sb.append("<EXCHANGE_DATA>");
		sb.append("<EXCHANGEPRICE>200</EXCHANGEPRICE>");
		sb.append("<EXCHANGENUM>3</EXCHANGENUM>");
		sb.append("</EXCHANGE_DATA>");
		sb.append("</EXCHANGE_LIST>");
		sb.append("<CZ_LIST>");
		sb.append("<CZ_DATA>");
		sb.append("<CZPRICE>30</CZPRICE>");
		sb.append("<CZNUM>3</CZNUM>");
		sb.append("</CZ_DATA>");
		sb.append("<CZ_DATA>");
		sb.append("<CZPRICE>20</CZPRICE>");
		sb.append("<CZNUM>2</CZNUM>");
		sb.append("</CZ_DATA>");
		sb.append("</CZ_LIST>");
		sb.append("</BODY>");
		sb.append("</PACKET>");
		return sb.toString();
	}

}
