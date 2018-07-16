package com.wap.kfcenter.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="zzkf_wx_selfhelp")
public class ZzkfWxSelfHelp {
	/**id**/
	private Long id;
	/**问题**/
	private String question;
	/**答案**/
	private String solution;
	/**问题类型 0-搜索 1-福袋问题 2-微信公众号常见问题 3-电子保单常见问题 4-交强险常见问题 5-承保类咨询 6-理赔手续问题7-理赔分部地址**/
	private String questionType;
	/**点赞、浏览次数**/
	private String good;
	/**不感兴趣**/
	private String bad;
	/**创建者代码**/
	private String creatorCode;
	/**创建者姓名**/
	private String creatorName;
	/**操作员代码**/
	private String operatorCode;
	/**操作员姓名**/
	private String operatorName;
	/**是纯文字或者链接**/
	private String isText;
	/**问题是否有效**/
	private String validFlag;
	/**创建日期**/
	private Date insertTimeForHis;
	/**修改日期**/
	private Date operateTimeForHis;
	
	@Id
	@Column
	@SequenceGenerator(allocationSize=1,name="generator",sequenceName="seq_zzkf_wx_selfhelp")
	@GeneratedValue(generator="generator",strategy=GenerationType.SEQUENCE)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Column
	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	@Column
	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	@Column
	public String getGood() {
		return good;
	}

	public void setGood(String good) {
		this.good = good;
	}

	@Column
	public String getBad() {
		return bad;
	}

	public void setBad(String bad) {
		this.bad = bad;
	}

	@Column
	public String getCreatorCode() {
		return creatorCode;
	}

	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}

	@Column
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Column
	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	@Column
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	@Column
	public String getIsText() {
		return isText;
	}

	public void setIsText(String isText) {
		this.isText = isText;
	}
	
	@Column
	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
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

}
