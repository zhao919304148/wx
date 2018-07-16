package com.wap.trans.entity.tr_1012;

import java.util.List;

public class ReqTrans1012Entity {
	private String registno;// 报案号
	private String licenseno;// 车牌号
	private String payeename;// 收款人
	private String bankno;// 银行卡号
	private String bankname;// 开户行
	private String banknamezh;// 开户行支行
	private String banknameflc;// 开户行分理处
	private String access_token;// 微信公众号唯一凭据
	private String openid;// 客户微信号唯一标识
	private List<ReqTrans1012FileEntity> reqTrans1012FileEntityList;// 文件列表

	public String getRegistno() {
		return registno;
	}

	public void setRegistno(String registno) {
		this.registno = registno;
	}

	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public String getPayeename() {
		return payeename;
	}

	public void setPayeename(String payeename) {
		this.payeename = payeename;
	}

	public String getBankno() {
		return bankno;
	}

	public void setBankno(String bankno) {
		this.bankno = bankno;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public List<ReqTrans1012FileEntity> getReqTrans1012FileEntityList() {
		return reqTrans1012FileEntityList;
	}

	public void setReqTrans1012FileEntityList(
			List<ReqTrans1012FileEntity> reqTrans1012FileEntityList) {
		this.reqTrans1012FileEntityList = reqTrans1012FileEntityList;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBanknamezh() {
		return banknamezh;
	}

	public void setBanknamezh(String banknamezh) {
		this.banknamezh = banknamezh;
	}

	public String getBanknameflc() {
		return banknameflc;
	}

	public void setBanknameflc(String banknameflc) {
		this.banknameflc = banknameflc;
	}

}
