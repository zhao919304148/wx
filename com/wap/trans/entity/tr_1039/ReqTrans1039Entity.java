package com.wap.trans.entity.tr_1039;

/**
 * 
		
		* 描述:修改参数
		*	车辆解绑
		* @author 赵硕
		* @version 1.0
		* @since 2017年6月27日 下午2:25:00
 */
public class ReqTrans1039Entity {
	/**用户openid**/
	private String openId;
	/**解绑车牌，多个用英文逗号隔开**/
	private String licenseNoList;
	/**用户名称**/
	private String userName;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getLicenseNoList() {
		return licenseNoList;
	}
	public void setLicenseNoList(String licenseNoList) {
		this.licenseNoList = licenseNoList;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
