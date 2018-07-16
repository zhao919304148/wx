package com.wap.trans.entity.tr_1008;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("COMPONENT_DATA")
public class ResTrans1008ComponentDataEntity {
	@XStreamAlias("COMPNAME")
	private String compname;// 换件项目名称
	@XStreamAlias("SUMPRICE")
	private String sumprice;// 换件项目价格
	@XStreamAlias("QUANTITY")
	private String quantity;// 数量

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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
