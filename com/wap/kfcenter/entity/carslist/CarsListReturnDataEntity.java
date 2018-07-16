package com.wap.kfcenter.entity.carslist;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 
 * 描述:车辆列表返回报文body中data
 * @author 戴元守
 * @version 1.0
 * @since 2017年9月15日 下午4:50:17
 */
public class CarsListReturnDataEntity {
	
	@JSONField(name="illegalnum")
	private String illegalnum = "";
	@JSONField(name="licenseno")
	private String licenseno = "";
	@JSONField(name="frameno")
	private String frameno = "";
	@JSONField(name="daarisktype")
	private String daarisktype = "";
	@JSONField(name="daastartdate")
	private Date daastartdate;
	@JSONField(name="daaenddate")
	private Date daaenddate;
	@JSONField(name="dzarisktype")
	private String dzarisktype = "";
	@JSONField(name="dzastartdate")
	private Date dzastartdate;
	@JSONField(name="dzaenddate")
	private Date dzaenddate;
	
	public String getIllegalnum() {
		return illegalnum;
	}
	public void setIllegalnum(String illegalnum) {
		this.illegalnum = illegalnum;
	}
	public String getLicenseno() {
		return licenseno;
	}
	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}
	public String getFrameno() {
		return frameno;
	}
	public void setFrameno(String frameno) {
		this.frameno = frameno;
	}
	public String getDaarisktype() {
		return daarisktype;
	}
	public void setDaarisktype(String daarisktype) {
		this.daarisktype = daarisktype;
	}
	public Date getDaastartdate() {
		return daastartdate;
	}
	public void setDaastartdate(Date daastartdate) {
		this.daastartdate = daastartdate;
	}
	public Date getDaaenddate() {
		return daaenddate;
	}
	public void setDaaenddate(Date daaenddate) {
		this.daaenddate = daaenddate;
	}
	public String getDzarisktype() {
		return dzarisktype;
	}
	public void setDzarisktype(String dzarisktype) {
		this.dzarisktype = dzarisktype;
	}
	public Date getDzastartdate() {
		return dzastartdate;
	}
	public void setDzastartdate(Date dzastartdate) {
		this.dzastartdate = dzastartdate;
	}
	public Date getDzaenddate() {
		return dzaenddate;
	}
	public void setDzaenddate(Date dzaenddate) {
		this.dzaenddate = dzaenddate;
	}

}
