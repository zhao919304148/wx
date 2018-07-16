package com.wap.trans.entity.tr_1055;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("MovecarMain")
public class ResTrans1055MovecarMainDataEntity {
	
	//车牌号
	@XStreamAlias("LICENSENO")
	private String licenseno;
	
	//客户姓名
	@XStreamAlias("OWENERNAME")
	private String owenername;
	
	//联系电话
	@XStreamAlias("TELNO")
	private String telno;
	
	//联系地址
	@XStreamAlias("OWENERADDRESS")
	private String oweneraddress;
	
	//分机号
	@XStreamAlias("EXTENSIONNO")
	private String extensionno;
	
	//注册时间
	@XStreamAlias("INSERTTIMEFORHIS")
	private String inserttimeforhis;
	
	//是否注册 	0-注销    1-注册
	@XStreamAlias("STATUS1")
	private String status1;
	
	//是否启用	0-否	  1-是
	@XStreamAlias("STATUS2")
	private String status2;

	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public String getOwenername() {
		return owenername;
	}

	public void setOwenername(String owenername) {
		this.owenername = owenername;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getOweneraddress() {
		return oweneraddress;
	}

	public void setOweneraddress(String oweneraddress) {
		this.oweneraddress = oweneraddress;
	}

	public String getExtensionno() {
		return extensionno;
	}

	public void setExtensionno(String extensionno) {
		this.extensionno = extensionno;
	}

	public String getInserttimeforhis() {
		return inserttimeforhis;
	}

	public void setInserttimeforhis(String inserttimeforhis) {
		this.inserttimeforhis = inserttimeforhis;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

}
