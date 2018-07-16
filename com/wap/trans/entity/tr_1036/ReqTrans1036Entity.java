package com.wap.trans.entity.tr_1036;

/**
 * 
		
		* 描述:投保手机号变更（原手机号可用）
		*
		* @author 骆利锋
		* @version 1.0
		* @since 2017年6月5日 上午9:11:27
 */
public class ReqTrans1036Entity {
	
	/**证件类型**/
	private String identifyType;
	/**证件号码**/
	private String identifyNo;
	/**车牌号码**/
	private String licenseNo;
	/**用户名**/
	private String userName;
	/**验证码**/
	private String valiDateCode;
	/**变更后的手机号**/
	private String modifyPhoneNumber;
	/**用户openid**/
	private String openId;
	
	public String getModifyPhoneNumber() {
		return modifyPhoneNumber;
	}
	public void setModifyPhoneNumber(String modifyPhoneNumber) {
		this.modifyPhoneNumber = modifyPhoneNumber;
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
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getValiDateCode() {
		return valiDateCode;
	}
	public void setValiDateCode(String valiDateCode) {
		this.valiDateCode = valiDateCode;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
