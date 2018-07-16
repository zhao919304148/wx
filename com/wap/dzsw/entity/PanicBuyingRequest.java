package com.wap.dzsw.entity;

import java.util.List;

/**
 * 
		
	* 描述:
	*	礼品抢购请求VO
	* @author 许宝众
	* @version 1.0
	* @since 2017年9月11日 下午1:08:36
 */
public class PanicBuyingRequest {
	private String openid;
	private String username;
	private List<CarDataEntity> userCarList;
	private String cardtype;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<CarDataEntity> getUserCarList() {
		return userCarList;
	}
	public void setUserCarList(List<CarDataEntity> userCarList) {
		this.userCarList = userCarList;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
}
