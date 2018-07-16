package com.wx.util;

import net.sf.json.JSONObject;

import com.wx.service.CblService;

public class BaseUtil {
	CblService cbl=new CblService();
	String url=ConfigUtil.getString("serviceurl");
	String newurl =ConfigUtil.getString("new_serviceurl");
	String cityId=ConfigUtil.getString("cityId");
	JSONObject paramStr=new JSONObject();//²ÎÊý
	String data=new String();
	
}
