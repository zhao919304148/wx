package com.wap.trans.entity.tr_1010;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("MATERIAL_DATA")
public class ResTrans1010MaterialDataEntity {
	@XStreamAlias("MATERIALNAME")
	private String materialname;// 辅料名称
	@XStreamAlias("MATERIALFEE")
	private String materialfee;// 辅料价格

	public String getMaterialname() {
		return materialname;
	}

	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}

	public String getMaterialfee() {
		return materialfee;
	}

	public void setMaterialfee(String materialfee) {
		this.materialfee = materialfee;
	}

}
