package com.wap.trans.entity.tr_1042;

/**
 * 
		
		* 描述:
		*	校验洗车码
		* @author 许宝众
		* @version 1.0
		* @since 2017年7月5日 上午11:15:27
 */
public class ReqTrans1042Entity {
	private String companyCode;		//服务商代码
	private String washCarCode;		//	washCarCode	洗车码	String		是	待验证洗车码
	private String networkName;		//	networkName	网点名称	String		是	消费网点名称
	private String networkAddress;	//	networkAddress	网店地址	String		是	消费网点地址
	private String transportTime;	//	transportTime	传输时间	String 		是	日期格式：
									//	yyyy-MM-dd HH:mm:ss.SSS
									//	如:2017-07-07 08:08:08.888
	private String thirdOrderId;
	private String sign;//	sign	签名	String	32	是	签名。对签名字符串进行MD5 加密后大写，见下方签名模板，验签时使用。
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getWashCarCode() {
		return washCarCode;
	}
	public void setWashCarCode(String washCarCode) {
		this.washCarCode = washCarCode;
	}
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public String getNetworkAddress() {
		return networkAddress;
	}
	public void setNetworkAddress(String networkAddress) {
		this.networkAddress = networkAddress;
	}
	public String getTransportTime() {
		return transportTime;
	}
	public void setTransportTime(String transportTime) {
		this.transportTime = transportTime;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getThirdOrderId() {
		return thirdOrderId;
	}
	public void setThirdOrderId(String thirdOrderId) {
		this.thirdOrderId = thirdOrderId;
	}
	@Override
	public String toString() {
		return "ReqTrans1042Entity [companyCode=" + companyCode
				+ ", washCarCode=" + washCarCode + ", networkName="
				+ networkName + ", networkAddress=" + networkAddress
				+ ", transportTime=" + transportTime + ", thirdOrderId="
				+ thirdOrderId + ", sign=" + sign + "]";
	}
}
