package com.wap.trans.entity.tr_1009;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("REPAIR_DATA")
public class ResTrans1009RepairDataEntity {
	@XStreamAlias("COMPNAME")
	private String compname;// 换件项目名称
	@XStreamAlias("SUMDEFLOSS")
	private String sumprice;// 换件项目价格

	public String getCompname() {
		return compname;
	}

	public void setCompname(String compname) {
		this.compname = compname;
	}

	public String getSumprice() {
		return sumprice;
	}

	public void setSumprice(String sumprice) {
		this.sumprice = sumprice;
	}

}
