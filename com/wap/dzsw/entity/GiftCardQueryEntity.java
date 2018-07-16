package com.wap.dzsw.entity;

public class GiftCardQueryEntity {

	
	private String identifyType;
	private String identifyNo;
	private String giftType;
	private Integer pageNo;
	private Integer pageSize=10;
	private Integer pageCount;
	private String openId;
	/**车牌号列表**/
	private String licenseNoList;
	
	public String getLicenseNoList() {
		return licenseNoList;
	}
	public void setLicenseNoList(String licenseNoList) {
		this.licenseNoList = licenseNoList;
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
	
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getGiftType() {
		return giftType;
	}
	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}
	@Override
	public String toString() {
		return "GiftCardQueryEntity [identifyType=" + identifyType + ", identifyNo=" + identifyNo + ", giftType="
				+ giftType + ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", pageCount=" + pageCount + ", openId="
				+ openId + "]";
	}
	
}
