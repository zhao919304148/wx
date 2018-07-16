package com.wap.dzsw.entity;

import java.util.List;

/**
 * 
		
		* 描述:
		*	车主节2017活动礼品信息Vo
		* @author 许宝众
		* @version 1.0
		* @since 2017年9月20日 下午2:09:14
 */
public class Czj2017GiftVo {
	private List<Czj2017GiftItemVo> robGifts;//每日抢礼品，第一个是当前正在进行的活动，或者将要进行的活动、第二个是下一轮活动礼品
	private List<Czj2017GiftItemVo> perdayGifts;//所有每日领取礼品信息
	public List<Czj2017GiftItemVo> getRobGifts() {
		return robGifts;
	}
	public void setRobGifts(List<Czj2017GiftItemVo> robGifts) {
		this.robGifts = robGifts;
	}
	public List<Czj2017GiftItemVo> getPerdayGifts() {
		return perdayGifts;
	}
	public void setPerdayGifts(List<Czj2017GiftItemVo> perdayGifts) {
		this.perdayGifts = perdayGifts;
	}
}
