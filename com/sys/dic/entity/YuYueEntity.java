package com.sys.dic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
		
		* 描述:预约表
		*
		* @author lichaohui
		* @version 1.0
		* @since 2016年12月5日 下午4:25:19
 */
@Entity
@Table(name = "s_dic_yuyue")
public class YuYueEntity {
	
	@Id
	@Column(name="id")
	@SequenceGenerator(name="generator",allocationSize=1,sequenceName="yuyue_sequence")
	@GeneratedValue(strategy =GenerationType.SEQUENCE, generator = "generator")
	private int id;
	
	@Column(name="networkJobid",length = 11)
	private String networkJobid;  //网点id
	
	@Column(name="networkType",length = 20)
	private String networkType;//预约服务类型

	@Column(name="yuyueDate",length = 20)
	private String yuyueDate;     //日期
	
	@Column(name="yuyueCount")
	private int yuyueCount;   //预约个数/日

	public String getNetworkJobid() {
		return networkJobid;
	}

	public void setNetworkJobid(String networkJobid) {
		this.networkJobid = networkJobid;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getYuyueDate() {
		return yuyueDate;
	}

	public void setYuyueDate(String yuyueDate) {
		this.yuyueDate = yuyueDate;
	}

	public int getYuyueCount() {
		return yuyueCount;
	}

	public void setYuyueCount(int yuyueCount) {
		this.yuyueCount = yuyueCount;
	}


	@Override
	public String toString() {
		return "YuYueEntity [id=" + id + ", networkJobid=" + networkJobid + ", networkType=" + networkType
				+ ", yuyueDate=" + yuyueDate + ", yuyueCount=" + yuyueCount + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
