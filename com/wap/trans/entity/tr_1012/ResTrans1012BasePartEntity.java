package com.wap.trans.entity.tr_1012;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BASE_PART")
public class ResTrans1012BasePartEntity {
	@XStreamAlias("REGISTNO")
	private String registno;// 报案号
	@XStreamAlias("LICENSENO")
	private String licenseno;// 车牌号
	@XStreamAlias("PAYEENAME")
	private String payeename;// 收款人
	@XStreamAlias("BANKNO")
	private String bankno;// 银行卡号
	@XStreamAlias("BANKNAME")
	private String bankname;// 开户行
	@XStreamAlias("BANKNAMEZH")
	private String banknamezh;// 开户行支行
	@XStreamAlias("BANKNAMEFLC")
	private String banknameflc;// 开户行分理处

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
