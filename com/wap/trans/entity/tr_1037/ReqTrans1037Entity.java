package com.wap.trans.entity.tr_1037;

import java.util.List;

/**
 * 
		
		* 描述:投保手机号变更（提交资料审核）
		*
		* @author 骆利锋
		* @version 1.0
		* @since 2017年6月5日 上午9:15:55
 */
public class ReqTrans1037Entity {
	
	/**证件类型**/
	private String identifyType;
	/**证件号码**/
	private String identifyNo;
	/**车牌号码**/
	private String licenseNo;
	/**用户名**/
	private String userName;
	/**变更后的手机号**/
	private String modifyPhoneNumber;
	/**文件列表**/
	private List<ReqTrans1037FileEntity> reqTrans1037FileEntityList;
	
	public List<ReqTrans1037FileEntity> getReqTrans1037FileEntityList() {
		return reqTrans1037FileEntityList;
	}
	public void setReqTrans1037FileEntityList(
			List<ReqTrans1037FileEntity> reqTrans1037FileEntityList) {
		this.reqTrans1037FileEntityList = reqTrans1037FileEntityList;
	}
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
	
}
