package com.sys.dic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
		
		* 描述:区域表
		*
		* @author lichoahui
		* @version 1.0
		* @since 2016年12月5日 下午4:24:56
 */
@Entity
@Table(name = "s_dic_area")
public class AreaEntity {

	@Column(name="pid",length = 11)
	private String pid;  //区域上级ID
	
	@Id
	@Column(name="id",length = 255)
	private String id;  //区域id
	
	@Column(name="areaName",length = 255)
	private String areaName;  //区域名称

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	
}
