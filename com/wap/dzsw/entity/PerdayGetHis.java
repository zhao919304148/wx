package com.wap.dzsw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
		
		* 描述:
		*	活动期间每日领取礼券，活动期间每人最多只能领取1中礼券中的一个礼券。即用户与礼券不能重复
		* @author 许宝众
		* @version 1.0
		* @since 2017年9月14日 下午1:52:54
 */
@Entity
@Table(name="perday_get_his")
public class PerdayGetHis {
	private Long id;
	private Date getdate;//datetime year to second,											---**获取日期
	private String openid;//varchar(60),
	private String cardtype; //varchar(30),
	private String cardno; //varchar(30),															---**卡号
	private String isqueue;// varchar(1),															--是否已推送入队列
	private Date inserttimeforhis;//  datetime year to second default current year to second, --**数据创建时间
	private Date operatetimeforhis;// datetime year to second default current year to second,  --**数据库操作时间
	@Id
	@Column(name="id",nullable=false,unique=true)
	@SequenceGenerator(name="generator",allocationSize=1,sequenceName="seq_perday_get_his")
	@GeneratedValue(generator="generator",strategy=GenerationType.SEQUENCE)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getGetdate() {
		return getdate;
	}
	public void setGetdate(Date getdate) {
		this.getdate = getdate;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getIsqueue() {
		return isqueue;
	}
	public void setIsqueue(String isqueue) {
		this.isqueue = isqueue;
	}
	@Column(name="inserttimeforhis",insertable=true,updatable=false)
	public Date getInserttimeforhis() {
		return inserttimeforhis;
	}
	public void setInserttimeforhis(Date inserttimeforhis) {
		this.inserttimeforhis = inserttimeforhis;
	}
	@Column(name="operatetimeforhis",insertable=false,updatable=true)
	public Date getOperatetimeforhis() {
		return operatetimeforhis;
	}
	public void setOperatetimeforhis(Date operatetimeforhis) {
		this.operatetimeforhis = operatetimeforhis;
	}
}

