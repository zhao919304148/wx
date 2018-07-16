package com.wap.trans.entity.tr_1032;
/**
 * 
		
* 描述:
*	油卡礼品提交确认
* @author 许宝众
* @version 1.0
* @since 2017年5月15日 下午5:40:01
 */
public class ReqTrans1032Entity {
	/**礼品序列号**/
	private String goodsSeqNumber;
	/**用户录入验证码**/
	private String validateCode;

	public String getGoodsSeqNumber() {
		return goodsSeqNumber;
	}

	public void setGoodsSeqNumber(String goodsSeqNumber) {
		this.goodsSeqNumber = goodsSeqNumber;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	@Override
	public String toString() {
		return "ReqTrans1032Entity [goodsSeqNumber=" + goodsSeqNumber
				+ ", validateCode=" + validateCode + "]";
	}
}
