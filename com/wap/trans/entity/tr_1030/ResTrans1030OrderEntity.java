package com.wap.trans.entity.tr_1030;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ORDERINFO")
public class ResTrans1030OrderEntity {
	@XStreamAlias("ORDERNO")
	private String orderNo;//订单号
	@XStreamAlias("GOODNAME")
	private String goodName;//礼品名称
	@XStreamAlias("COUNT")
	private String count;//礼品数量
	@XStreamAlias("PRICE")
	private String price;//商品单价
	@XStreamAlias("SCORE")
	private String score;//订单积分额
	@XStreamAlias("CASH")
	private String cash;//订单现金
	@XStreamAlias("DATETIME")
	private String dateTime;//订单时间
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getCash() {
		return cash;
	}
	public void setCash(String cash) {
		this.cash = cash;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	@Override
	public String toString() {
		return "ScoreOrder [orderNo=" + orderNo + ", goodName=" + goodName + ", count=" + count + ", price=" + price
				+ ", score=" + score + ", cash=" + cash + ", dateTime=" + dateTime + "]";
	}
	
}
