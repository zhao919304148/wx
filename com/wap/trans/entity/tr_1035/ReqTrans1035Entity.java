package com.wap.trans.entity.tr_1035;

/**
 * 
		
		* 描述:关联投保车辆，绑定（提交）用户信息
		*
		* @author 骆利锋
		* @version 1.0
		* @since 2017年6月5日 上午8:40:14
 */
public class ReqTrans1035Entity {
	
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
	/**微信id**/
	private String openId;
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
