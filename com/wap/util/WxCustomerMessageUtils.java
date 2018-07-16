package com.wap.util;

/**
 * 微信客服消息模板工具类
		
* 描述:
*
* @author 许宝众
* @version 1.0
* @since 2017年6月13日 下午1:53:51
 */
public class WxCustomerMessageUtils {
	/**悦点商城：生成新订单**/
	public static final String TYPE_JF_CREATE_ORDER="createJfOrder";
	/**悦点商城：取消新订单**/
	public static final String TYPE_JF_CANCEL_ORDER="cancelJfOrder";
	/**悦点商城：订单发货**/
	public static final String TYPE_JF_ORDER_DELIVER="deliverJfOrder";
	/**
	 * 
			* 描述:
			* 	客服消息模板方法
			* @param type
			* @param params
			* @return
			* @author 许宝众 2017年6月13日 下午1:55:20
	 */
	public static String template(String type,Object... params){
		StringBuffer sb=new StringBuffer();
		switch (type) {
			case TYPE_JF_CREATE_ORDER:
				sb.append("尊敬的客户您好，您在【悦驾俱乐部】成功兑换了商品，点击进入<a href=\""+params[0]+"\">我的订单</a>查看订单详情。");
				break;
			case TYPE_JF_CANCEL_ORDER:
				sb.append("尊敬的客户您好，您已成功取消在【悦驾俱乐部】兑换的商品，点击进入<a href=\""+params[0]+"\">我的订单</a>查看取消订单详情。");
				break;
			case TYPE_JF_ORDER_DELIVER:
				sb.append("尊敬的客户您好，您在【悦驾俱乐部】兑换的商品已发货，点击进入<a href=\""+params[0]+"\">我的订单</a>查看物流信息。");
				break;
		}
		return sb.toString();
	}
}
