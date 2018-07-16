package com.wap.lipei.entity;

/**
 * 
 * 
 * 描述:定损明细清单
 * 
 * @author 朱久满
 * @version 1.0
 * @since 2015年12月30日 上午9:58:19
 */
public class DeflossDetailEntity {
	private String compname;// 换件项目名称/修理项目名称/零部件名称
	private String sumprice;// 换件项目价格/修理项目价格/零部件价格
	private String quantity;// 数量
	private String materialname;// 辅料名称
	private String materialfee;// 辅料价格

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
