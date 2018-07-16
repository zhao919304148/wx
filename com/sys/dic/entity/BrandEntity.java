package com.sys.dic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
		
		* 描述:品牌表
		*
		* @author lichaohui
		* @version 1.0
		* @since 2016年12月5日 下午4:28:25
 */
@Entity
@Table(name = "s_dic_brand")
public class BrandEntity {

	@Id
	@Column(name="brandid",length = 11)
	private String brandid;  //品牌id
	
	@Column(name = "brandName", length = 255)
	private String brandName;  //品牌名称
	
	@Column(name = "bfirstletter", length = 255)
	private String bfirstletter;  //品牌首字母

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


	public String getBfirstletter() {
		return bfirstletter;
	}


	public void setBfirstletter(String bfirstletter) {
		this.bfirstletter = bfirstletter;
	}
	
	
}
