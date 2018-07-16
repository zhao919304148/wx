package com.wap.trans.entity.tr_1038;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CAR_DATA")
public class ResTrans1038CarDataEntity {
	@XStreamAlias("LICENSENO")
	private String licenseNo; //车牌号
	@XStreamAlias("IDENTIFYLIST")
	private List<ResTrans1038IdentifyDateEntity> resTrans1038IdentifyDateEntityList;
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public List<ResTrans1038IdentifyDateEntity> getResTrans1038IdentifyDateEntityList() {
		return resTrans1038IdentifyDateEntityList;
	}
	public void setResTrans1038IdentifyDateEntityList(
			List<ResTrans1038IdentifyDateEntity> resTrans1038IdentifyDateEntityList) {
		this.resTrans1038IdentifyDateEntityList = resTrans1038IdentifyDateEntityList;
	}
	
}
