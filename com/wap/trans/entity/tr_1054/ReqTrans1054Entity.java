package com.wap.trans.entity.tr_1054;

public class ReqTrans1054Entity {
	
	//车牌号
	private String licenseno;
	//客户姓名
	private String owenername;
	//联系电话
	private String telno;
	//联系地址(非必填)
	private String oweneraddress;
	//分机号
	private String extensionno;
	//渠道类型
	private String channeltype;
	//渠道唯一标识(非必填)
	private String channelno;				  
	
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
	public String getChanneltype() {
		return channeltype;
	}
	public void setChanneltype(String channeltype) {
		this.channeltype = channeltype;
	}
	public String getChannelno() {
		return channelno;
	}
	public void setChannelno(String channelno) {
		this.channelno = channelno;
	}
	
}
