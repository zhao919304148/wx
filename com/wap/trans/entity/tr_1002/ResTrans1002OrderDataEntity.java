package com.wap.trans.entity.tr_1002;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ORDER_DATA")
public class ResTrans1002OrderDataEntity {
	@XStreamAlias("CONSIGNEE")
	private String consignee; // 收件人
	@XStreamAlias("ADDRESS")
	private String address; // 地址
	@XStreamAlias("ORDERNO")
	private String orderno; // 订单号
	@XStreamAlias("ISCARCHECK")
	private String iscarcheck; // 是否验车
	@XStreamAlias("LICENSENO")
	private String licenseno; // 车牌号
	@XStreamAlias("ORDERSTATUS")
	private String orderstatus; // 订单状态
	@XStreamAlias("MODIFYTIME")
	private String modifytime; // 修改次数

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getIscarcheck() {
		return iscarcheck;
	}

	public void setIscarcheck(String iscarcheck) {
		this.iscarcheck = iscarcheck;
	}

	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public String getModifytime() {
		return modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}


	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

}
