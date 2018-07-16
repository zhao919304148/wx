package com.wap.post.entity;

public class PostQueryEntity {
	private String usercode;//用户代码
	private String comcode;//归属机构
	private String usertype;//用户类型
	private String orderstatus;//订单状态
	private String orderno; // 订单号
	private String licenseno; // 车牌号
	private String chargedate; // 预定收费日期/预订送单日期
	private String modifydate; // 修改收费日期/修改送单日期
	private String barcode; // 条码
	private String courier; // 送单员代码
	private String queryflag; // 查询标识
	private Integer pageno;//页码
	private Integer pagesize;//每页数据条数 

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public String getChargedate() {
		return chargedate;
	}

	public void setChargedate(String chargedate) {
		this.chargedate = chargedate;
	}

	public String getModifydate() {
		return modifydate;
	}

	public void setModifydate(String modifydate) {
		this.modifydate = modifydate;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
	}

	public String getQueryflag() {
		return queryflag;
	}

	public void setQueryflag(String queryflag) {
		this.queryflag = queryflag;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getComcode() {
		return comcode;
	}

	public void setComcode(String comcode) {
		this.comcode = comcode;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public Integer getPageno() {
		return pageno;
	}

	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

}
