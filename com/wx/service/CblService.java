package com.wx.service;

import com.wx.util.ConfigUtil;
import com.wx.util.CookieUtil;
import com.wx.util.HttpRequestUtil;

import net.sf.json.JSONObject;

public class CblService {
	public String url=ConfigUtil.getString("serviceurl");//调用的service地址
	
	
	/**
	 * 获取商家
	 * @param cityId
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public JSONObject getShopList(String cityId,String longitude,String latitude){
		String currPage="1";
		String pageSize="10";
		longitude=longitude==null ? "0" : longitude;
		latitude=latitude==null? "0" : latitude;
		JSONObject jsonShop=new JSONObject();
		jsonShop.element("shopName", "");
		jsonShop.element("cityId", cityId);
		jsonShop.element("currPage", currPage);
		jsonShop.element("pageSize", pageSize);
		jsonShop.element("flag", "0");
		jsonShop.element("longitude", longitude);
		jsonShop.element("latitude", latitude);
		jsonShop.element("sortType", "0");
		String shopResult=HttpRequestUtil.sendHttpPostRequest(url, "type=3&methodName=searchWashShop&paramStr="+jsonShop
				);
		jsonShop=new JSONObject();
		jsonShop=JSONObject.fromObject(shopResult);
		return jsonShop;
	}
	/**
	 * 获取账户信息
	 * @param userId
	 * @return
	 */
	public JSONObject getAccount(String userId){
		String data="type=3&methodName=queryMoney&paramStr={\"userId\":\""+userId+"\"}";
		String result=HttpRequestUtil.sendHttpPostRequest(url, data);
		JSONObject json=JSONObject.fromObject(result);
		JSONObject jsonContent=json.getJSONObject("content");
		return jsonContent;
	}
	/**
	 * 保存订单
	 */
	public JSONObject saveOrder(String orderNo,String userId,String phone,String money,String nickname,String otherUserId,String account){
		JSONObject jsonParam=new JSONObject();
		jsonParam.element("orderNo", orderNo);
		jsonParam.element("userId", userId);
		jsonParam.element("phone", phone);
		jsonParam.element("money", money);
		jsonParam.element("nickname", nickname);
		jsonParam.element("otherUserId", otherUserId);
		jsonParam.element("account", account);
		String data="type=3&methodName=savePayOrderNo&paramStr="+jsonParam;
		String orderResult=HttpRequestUtil.sendHttpPostRequest(url, data);
		JSONObject result=JSONObject.fromObject(orderResult);
		return result;
	}
}
