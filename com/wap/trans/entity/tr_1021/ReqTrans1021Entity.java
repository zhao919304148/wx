package com.wap.trans.entity.tr_1021;

public class ReqTrans1021Entity {

	/**openid*/
	private String openId;
	
	/**证件类型*/
	private String identifyType; 
	
	/**证件号*/
	private String identifyNo;
	
	/**礼品状态*/
	private String giftType; 
	
	/**页码*/
	private int pageNo;
	
	/**每页数据条数*/
	private int pageSize;
	
	/**条件查询礼品类型名称*/
	private String cardTypeNameCondition;
	
	/**车牌号列表**/
	private String licenseNoList;

	public String getCardTypeNameCondition() {
		return cardTypeNameCondition;
	}

	public void setCardTypeNameCondition(String cardTypeNameCondition) {
		this.cardTypeNameCondition = cardTypeNameCondition;
	}

	public String getLicenseNoList() {
		return licenseNoList;
	}

	public void setLicenseNoList(String licenseNoList) {
		this.licenseNoList = licenseNoList;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	
	
}
