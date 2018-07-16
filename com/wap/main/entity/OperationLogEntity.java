package com.wap.main.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 
 * 描述:操作记录实体类
 * 
 * @author 朱久满
 * @version 1.0
 * @since 2016年2月29日 下午4:03:17
 */
@Entity
@Table(name = "s_rbac_OperationLog")
public class OperationLogEntity {
	// 主键
	@Id
	@Column(name = "ids", length = 30)
	private String ids;
	// 微信唯一标识
	@Column(name = "OPENID", length = 100)
	private String openid;
	// 操作时间
	@Column(name = "CREATETIME", length = 30)
	private Date createtime;
	// 操作描述
	@Column(name = "TITLE", length = 30)
	private String title;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
