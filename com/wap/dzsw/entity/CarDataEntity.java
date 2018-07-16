package com.wap.dzsw.entity;

import java.util.List;

import com.wap.trans.entity.tr_1038.ResTrans1038IdentifyDateEntity;


public class CarDataEntity {
	
	private String licenseNo; //车牌号
	private List<IdentifyDateEntity> identifyDateEntityList;//证件信息集合
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public List<IdentifyDateEntity> getIdentifyDateEntityList() {
		return identifyDateEntityList;
	}
	public void setIdentifyDateEntityList(
			List<IdentifyDateEntity> identifyDateEntityList) {
		this.identifyDateEntityList = identifyDateEntityList;
	}
	
}
