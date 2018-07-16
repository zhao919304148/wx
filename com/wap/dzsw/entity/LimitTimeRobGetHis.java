package com.wap.dzsw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
		
		* 描述:
		*		限时抢购礼品领取记录表，微信端通过唯一约束保证：一个用户一天只能抢到一个券
		* @author 许宝众
		* @version 1.0
		* @since 2017年9月14日 下午1:31:35
 */
@Entity
@Table(name="limit_time_rob_get_his")
public class LimitTimeRobGetHis {
	private Long id;
	private String openid;
	private Date getdate;
	private String cardtype;//
	private String cardno;
	private Date inserttimeforhis;// 
	private Date operatetimeforhis;//
	
	@Id
	@Column(name="id",nullable=false,unique=true)
	@SequenceGenerator(name="generator",allocationSize=1,sequenceName="seq_limit_time_rob_get_his")
	@GeneratedValue(strategy =GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="openid")
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	@Column(name="getdate")
	public Date getGetdate() {
		return getdate;
	}
	public void setGetdate(Date getdate) {
		this.getdate = getdate;
	}
	@Column(name="cardtype")
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	@Column(name="cardno")
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
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
