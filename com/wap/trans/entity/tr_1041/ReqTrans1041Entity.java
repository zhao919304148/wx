package com.wap.trans.entity.tr_1041;

/**
 * 
		
		* 描述:
		*	生成洗车随机码
		* @author 骆利锋
		* @version 1.0
		* @since 2017年7月10日 下午2:25:00
 */
public class ReqTrans1041Entity {
	/**礼品序列号**/
	private String goodsSeqNumber;
	/**微信openid**/
	private String openId;
	public String getGoodsSeqNumber() {
		return goodsSeqNumber;
	}

	public void setGoodsSeqNumber(String goodsSeqNumber) {
		this.goodsSeqNumber = goodsSeqNumber;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
