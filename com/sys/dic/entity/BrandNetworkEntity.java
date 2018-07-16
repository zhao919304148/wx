package com.sys.dic.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * 描述:品牌与网点关系表
 *
 * @author 赵硕
 * @version 1.0
 * @since 2018年3月27日 下午4:38:08
 */
@Entity
@Table(name = "s_dic_brand_network")
public class BrandNetworkEntity {
	@SequenceGenerator(name = "generator",allocationSize = 1,sequenceName = "seq_s_dic_brand_network")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "id", unique = true, nullable = false, scale = 0)
	private Long id;
	
	@Column(name="brandid",length = 11)
	private String brandId;   //品牌id
	
	
	@Column(name = "networktype", length = 11)
	private String networkType;   //网点类型
	
	@Column(name = "networktypename", length = 200)
	private String networkTypeName;//网点类型名称
	
	@Column(name = "maxcount", length = 11)
	private String maxCount;     //最大预约数
	
	@Column(name = "is4s")
	private int is4S;     //最大预约数

	@Column(name="flag")
	private String flag; 
	
	@Column(name="brandtype")
	private String brandType;
	
	@Column(name="inserttimeforhis", length = 3594)
	private Date insertTimeForHis; //创建时间
	
	@Column(name="operatetimeforhis", length = 3594)
	private Date operateTimeForHis; //操作时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getNetworkTypeName() {
		return networkTypeName;
	}

	public void setNetworkTypeName(String networkTypeName) {
		this.networkTypeName = networkTypeName;
	}

	public String getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(String maxCount) {
		this.maxCount = maxCount;
	}

	public int getIs4S() {
		return is4S;
	}

	public void setIs4S(int is4s) {
		is4S = is4s;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getBrandType() {
		return brandType;
	}

	public void setBrandType(String brandType) {
		this.brandType = brandType;
	}

	public Date getInsertTimeForHis() {
		return insertTimeForHis;
	}

	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}

	public Date getOperateTimeForHis() {
		return operateTimeForHis;
	}

	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}
}
