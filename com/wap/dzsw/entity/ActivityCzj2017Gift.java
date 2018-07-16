package com.wap.dzsw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
		
		* 描述:
		*	2017车主节活动日程表
		* @author 许宝众
		* @version 1.0
		* @since 2017年9月20日 上午9:01:51
 */
@Entity
@Table(name="activity_czj2017_gift")
public class ActivityCzj2017Gift {
	private Long id;
	private String cardType;// varchar(30),											--**礼品类型
	private String cardTypeName;//cardtypename varchar(30),										--**礼品名称
	private Integer amount;// int,														--**礼品总数量
	private Date beginTime;// datetime year to second,								--**活动开始时间
	private Date endTime;//  datetime year to second,								--**活动结束时间
	private String activityType;// varchar(1),										--**活动类型 --** 0 ：每日领取 --** 1 ：每日抢券
	private Date insertTimeForHis;
	private Date operateTimeForHis;
	@Id
	@Column(name="id")
	@SequenceGenerator(allocationSize=1,name="generator",sequenceName="seq_activity_czj2017_gift")
	@GeneratedValue(generator="generator",strategy=GenerationType.SEQUENCE)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	@Column(name="insertTimeForHis",insertable=true,updatable=false)
	public Date getInsertTimeForHis() {
		return insertTimeForHis;
	}
	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}
	@Column(name="operateTimeForHis",insertable=false,updatable=true)
	public Date getOperateTimeForHis() {
		return operateTimeForHis;
	}
	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}
	@Override
	public String toString() {
		return "ActivityCzj2017Gift [id=" + id + ", cardType=" + cardType
				+ ", cardTypeName=" + cardTypeName + ", amount=" + amount
				+ ", beginTime=" + beginTime + ", endTime=" + endTime
				+ ", activityType=" + activityType + ", insertTimeForHis="
				+ insertTimeForHis + ", operateTimeForHis=" + operateTimeForHis
				+ "]";
	}
}
