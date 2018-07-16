package com.wap.kfcenter.entity;

/**
 * 
 * 描述:客服活动获取保单信息列表请求实体类
		* @author 吕亮
		* @version 1.0
		* @since 2016年8月5日 上午10:09:50
 */
public class PolicyQueryEntity {
	
	//openid
	private String openid = "";
	//证件类型
	private String identifyType = "";
	//证件号
	private String identifyNo = "";
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getIdentifyType() {
		return identifyType;
	}
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}
	public String getIdentifyNo() {
		return identifyNo;
	}
	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}
}
