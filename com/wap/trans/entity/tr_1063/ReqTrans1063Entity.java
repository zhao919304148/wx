package com.wap.trans.entity.tr_1063;
/**
 * 
		
		* 描述:取消订单接口vo
		*
		* @author han
		* @version 1.0
		* @since 2017年12月20日 下午1:50:00
 */
public class ReqTrans1063Entity {
	private String thirdOrderNo;//订单号
	
	private String comCode;//服务商代码

	public String getThirdOrderNo() {
		return thirdOrderNo;
	}

	public String getComCode() {
		return comCode;
	}

	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

}
