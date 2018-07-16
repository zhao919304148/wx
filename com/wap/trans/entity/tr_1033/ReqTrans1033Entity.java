package com.wap.trans.entity.tr_1033;

/**
 * 
		
		* 描述:关联投保人车辆，获取投保人手机号
		*
		* @author 骆利锋
		* @version 1.0
		* @since 2017年6月2日 上午9:06:12
 */
public class ReqTrans1033Entity {
	
	/**证件类型**/
	private String identifyType;
	/**证件号码**/
	private String identifyNo;
	/**车牌号码**/
	private String licenseNo;
	
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
	
}
