package com.wap.kfcenter.entity;

public class DbServicelistEntity {
	//页面展示部分
	private String servicecode;     //服务代码
	private String servicename;     // 服务名
	private int totalstock;           // 总数
	private int remainstock;             // 剩余数
	private String enddate;         // 到期时间
	private String validflag;       // 是否可以预约  0不可预约，1可预约
	
	
	public String getServicecode() {
		return servicecode;
	}
	public void setServicecode(String servicecode) {
		this.servicecode = servicecode;
	}
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	public int getTotalstock() {
		return totalstock;
	}
	public void setTotalstock(int totalstock) {
		this.totalstock = totalstock;
	}
	public int getRemainstock() {
		return remainstock;
	}
	public void setRemainstock(int remainstock) {
		this.remainstock = remainstock;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getValidflag() {
		return validflag;
	}
	public void setValidflag(String validflag) {
		this.validflag = validflag;
	}
	

}
