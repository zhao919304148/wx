package com.wap.trans.entity.tr_1049;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SERVICE_DATA")
public class ResTrans1049ServiceDataEntity {
	
	@XStreamAlias("LICENSENO")
	private String licenseno;			//车牌号
	
	@XStreamAlias("LICENSECOLORCODE")
	private String licensecolorcode;	//号牌底色
	
	@XStreamAlias("ENGINENO")
	private String engineno;			//发动机号
	
	@XStreamAlias("CUSTOMERNAME")
	private String customername;		//被保险人名称
	
	@XStreamAlias("IDENTITYNO")
	private String identityno;			//被保险人证件号
	
	@XStreamAlias("PHONENUMBER")
	private String phonenumber;			//被保险人手机号
	
	@XStreamAlias("INPUTTIME")
	private String inputtime;			//注册时间
	
	@XStreamAlias("MODIFYTIME")
	private String modifytime;			//最后修改时间
	
	@XStreamAlias("CANCELTIME")
	private String canceltime;			//取消恢复时间
	
	@XStreamAlias("VALIDSTATUS")
	private String validstatus;			//有效状态
	
	@XStreamAlias("FLAG")
	private String flag;				//服务类型

	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public String getLicensecolorcode() {
		return licensecolorcode;
	}

	public void setLicensecolorcode(String licensecolorcode) {
		this.licensecolorcode = licensecolorcode;
	}

	public String getEngineno() {
		return engineno;
	}

	public void setEngineno(String engineno) {
		this.engineno = engineno;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getIdentityno() {
		return identityno;
	}

	public void setIdentityno(String identityno) {
		this.identityno = identityno;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getInputtime() {
		return inputtime;
	}

	public void setInputtime(String inputtime) {
		this.inputtime = inputtime;
	}

	public String getModifytime() {
		return modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	public String getCanceltime() {
		return canceltime;
	}

	public void setCanceltime(String canceltime) {
		this.canceltime = canceltime;
	}

	public String getValidstatus() {
		return validstatus;
	}

	public void setValidstatus(String validstatus) {
		this.validstatus = validstatus;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
