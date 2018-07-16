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
		
		* 描述:客服网点
		*
		* @author qex
		* @version 1.0
		* @since 2017年1月13日 上午11:30:02
 */
@Entity
@Table(name = "s_dic_kefu_network")
public class KeFuNetwork {
	@SequenceGenerator(name = "generator",allocationSize = 1,sequenceName = "seq_s_dic_kefu_network")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "id", unique = true, nullable = false, scale = 0)
	private Long id;
	@Column(name="brandId",length = 11)
	private String brandId;
	@Column(name="brandName",length = 20)
	private String brandName;
	@Column(name="bfirstletter",length = 20)
	private String bfirstletter;
	@Column(name="majorBrandId",length = 11)
	private String majorBrandId;
	@Column(name="networkName",length = 11)
	private String networkName;
	@Column(name="networkPhnoe",length = 60)
	private String networkPhnoe;
	@Column(name="networkAddress",length = 200)
	private String networkAddress;
	@Column(name="networkTypeName",length = 150)
	private String networkTypeName;
	@Column(name="networkType",length = 11)
	private String networkType;
	@Column(name="flag")
	private String flag;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
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
	public String getMajorBrandId() {
		return majorBrandId;
	}
	public void setMajorBrandId(String majorBrandId) {
		this.majorBrandId = majorBrandId;
	}
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public String getNetworkPhnoe() {
		return networkPhnoe;
	}
	public void setNetworkPhnoe(String networkPhnoe) {
		this.networkPhnoe = networkPhnoe;
	}
	public String getNetworkAddress() {
		return networkAddress;
	}
	public void setNetworkAddress(String networkAddress) {
		this.networkAddress = networkAddress;
	}
	public String getNetworkTypeName() {
		return networkTypeName;
	}
	public void setNetworkTypeName(String networkTypeName) {
		this.networkTypeName = networkTypeName;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	
}
