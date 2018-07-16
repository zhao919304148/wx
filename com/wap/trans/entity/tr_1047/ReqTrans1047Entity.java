package com.wap.trans.entity.tr_1047;

public class ReqTrans1047Entity {
	
	private String licenseno;		    //车牌号
	private String licensecolorcode;	//号牌底色
	private String interuse;            //接口调用者，01：受邀有礼，主要是控制是否需要传验证码，严格讲都要传验证码
	private String checkcode;           //验证码，不能只传个车牌号就能把用户信息获取到，没有验证码有风险
	
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
	public String getInteruse() {
		return interuse;
	}
	public void setInteruse(String interuse) {
		this.interuse = interuse;
	}
	public String getCheckcode() {
		return checkcode;
	}
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
}