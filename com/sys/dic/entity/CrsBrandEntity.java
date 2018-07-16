package com.sys.dic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
       /**
		* 描述:与客户关系管理平台同步的品牌、车型表
		*
		* @author fenghairui
		* @version 1.0
		* @since 2017年11月7日 下午13:15
        */
@Entity
@Table(name = "s_dic_crsbrand")
public class CrsBrandEntity {

	@Id
	@Column(name="brandid",length = 30)
	private String brandid;  //品牌id
	
	@Column(name="brandname",length = 255)
	private String brandname;  //品牌名称
	
	@Column(name = "crs_servicecode", length = 30)
	private String crs_servicecode;  //客户关系管理平台中服务代码  CS00000017代办保养

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getCrs_servicecode() {
		return crs_servicecode;
	}

	public void setCrs_servicecode(String crs_servicecode) {
		this.crs_servicecode = crs_servicecode;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	
}
