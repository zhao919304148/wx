package com.wap.dzsw.entity;
/**
 * 
		
		* 描述:
		*	车主节2017活动礼品具体礼品信息Vo
		* @author 许宝众
		* @version 1.0
		* @since 2017年9月20日 下午2:09:14
 */
public class Czj2017GiftItemVo {
	private String cardType;//礼品类型
	private String cardTypeName;//礼品名称
	private String beginTime;//活动开始时间 yyyy-MM-dd HH:mm:ss
	private String endTime;//活动结束时间 yyyy-MM-dd HH:mm:ss
	private Integer remain;//剩余库存
	private Integer amount;//总数量
	private String activityStatus;//1 进行中  0 未开始
	private boolean acquired;//已领取，对阵每日领取
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardTypeName() {
		return cardTypeName;
	}
	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getRemain() {
		return remain;
	}
	public void setRemain(Integer remain) {
		this.remain = remain;
	}
	public String getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}
	public boolean isAcquired() {
		return acquired;
	}
	public void setAcquired(boolean acquired) {
		this.acquired = acquired;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
