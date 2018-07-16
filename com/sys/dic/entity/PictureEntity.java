package com.sys.dic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
		
		* 描述:图片字典表
		*
		* @author ZN
		* @version 1.0
		* @since 2015-12-10 下午3:53:00
 */
@Entity
@Table(name = "s_dic_picture")
public class PictureEntity {

	@Id
	@Column(name="pid",length = 30)
	private String pid;//主键
	
	@Column(name = "dicId", length = 500)
	private String dicId;//图片存放的地址
	
	@Column(name = "idValue", length = 20)
	private String idValue;//图片名称
	
	@Column(name = "idUrl", length = 500)
	private String idUrl;//图片指向的地址
	
	@Column(name = "dicTypeId", length = 20)
	private String dicTypeId;//图片的类型
	
	@Column(name = "validflag", length = 2)
	private String validflag;//是否有效
	
	@Column(name = "indexNum", length = 2)
	private String indexNum;//排序

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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

	public String getIdUrl() {
		return idUrl;
	}

	public void setIdUrl(String idUrl) {
		this.idUrl = idUrl;
	}

	public String getDicTypeId() {
		return dicTypeId;
	}

	public void setDicTypeId(String dicTypeId) {
		this.dicTypeId = dicTypeId;
	}

	public String getValidflag() {
		return validflag;
	}

	public void setValidflag(String validflag) {
		this.validflag = validflag;
	}

	public String getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(String indexNum) {
		this.indexNum = indexNum;
	}
	
}
