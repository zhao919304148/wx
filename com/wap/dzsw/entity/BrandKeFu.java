package com.wap.dzsw.entity;

import java.util.List;

import net.sf.json.JSONObject;

public class BrandKeFu {

	private String brandId;
	private String brandName;
	private String bfirstletter;
	private String majorBrandId;
	private List<BrandNetwork> dataList;
	public String getMajorBrandId() {
		return majorBrandId;
	}
	public void setMajorBrandId(String majorBrandId) {
		this.majorBrandId = majorBrandId;
	}
	
	public List<BrandNetwork> getDataList() {
		return dataList;
	}
	public void setDataList(List<BrandNetwork> dataList) {
		this.dataList = dataList;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBfirstletter() {
		return bfirstletter;
	}
	public void setBfirstletter(String bfirstletter) {
		this.bfirstletter = bfirstletter;
	}
	
}
