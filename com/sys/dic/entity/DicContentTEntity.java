package com.sys.dic.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

//字典定义表
@Entity
@Table(name = "s_dic_content")
public class DicContentTEntity {


	//内容序号
	@Id
	@Column(name="bid",insertable = true, updatable = true,scale=0)
	private Long bid;
	
	//字典类型
	@Column(name = "dicTypeId", length = 30)	
	private String dicTypeId;

	//内容ID
	@Column(name = "dicId", length = 20)	
	private String dicId;
	
	//内容值
	@Column(name = "idValue", length = 100)
	private String idValue;
	
    //内容值2(备用字段)
	@Column(name = "idValue2", length = 200)	
	private String idValue2;
	
	//创建人
	@Column(name = "creater", length = 20)	
	private String creater;
	
	//创建时间
	@Column(name = "createDate",length = 20)
	private Date createDate;
	
	//有效标志
	@Column(name = "validFlag",length = 1)
	private String validFlag;
	
	//有效标志
	@Column(name = "displayIndex")
	private int displayIndex;

	//行锁
    @Version
    @Column(name = "row_version")
    private int version ;
	
	public String getDicTypeId() {
		return dicTypeId;
	}
	public void setDicTypeId(String dicTypeId) {
		this.dicTypeId = dicTypeId;
	}
	public String getDicId() {
		return dicId;
	}
	public void setDicId(String dicId) {
		this.dicId = dicId;
	}
	public String getIdValue() {
		return idValue;
	}
	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}
	public String getIdValue2() {
		return idValue2;
	}
	public void setIdValue2(String idValue2) {
		this.idValue2 = idValue2;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public void setBid(Long bid) {
		this.bid = bid;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getBid() {
		return bid;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}

}