package com.wap.qywx.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class TimeInfo {

	@JSONField(name="ComCode")
    private String comCode = "";
	@JSONField(name="ComName")
    private String comName = "";
	@JSONField(name="ArticleCode")
    private String articleCode = "";
	@JSONField(name="ArticleName")
    private String articleName = "";
    @JSONField(name="QdbfDay")
    private String qdbfDay = "";
    @JSONField(name="QdbfDayLast")
    private String qdbfDayLast = "";
    @JSONField(name="BdbfDay")
    private String bdbfDay = "";
    @JSONField(name="BdbfDayLast")
    private String bdbfDayLast = "";
    @JSONField(name="PolicyNumDay")
    private String policyNumDay = "";
    @JSONField(name="PolicyNumLast")
    private String policyNumLast = "";
    
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getArticleCode() {
		return articleCode;
	}
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}
	public String getQdbfDay() {
		return qdbfDay;
	}
	public void setQdbfDay(String qdbfDay) {
		this.qdbfDay = qdbfDay;
	}
	public String getQdbfDayLast() {
		return qdbfDayLast;
	}
	public void setQdbfDayLast(String qdbfDayLast) {
		this.qdbfDayLast = qdbfDayLast;
	}
	public String getBdbfDay() {
		return bdbfDay;
	}
	public void setBdbfDay(String bdbfDay) {
		this.bdbfDay = bdbfDay;
	}
	public String getBdbfDayLast() {
		return bdbfDayLast;
	}
	public void setBdbfDayLast(String bdbfDayLast) {
		this.bdbfDayLast = bdbfDayLast;
	}
	public String getArticleName() {
		return articleName;
	}
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}
	public String getPolicyNumDay() {
		return policyNumDay;
	}
	public void setPolicyNumDay(String policyNumDay) {
		this.policyNumDay = policyNumDay;
	}
	public String getPolicyNumLast() {
		return policyNumLast;
	}
	public void setPolicyNumLast(String policyNumLast) {
		this.policyNumLast = policyNumLast;
	}
}
