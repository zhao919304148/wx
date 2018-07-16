package com.wap.dzsw.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
	* 
	* 描述:网点信息Vo
	*
	* @author 赵硕
	* @version 1.0
	* @since 2018年3月27日 下午5:02:15
*/
public class NetworkVo {
	
	private String brandId;   //品牌id
	
	private String brandType;   //服务品牌类型  --1 专修品牌  --0 兼修品牌
	
	private String parentNetworkJobid;  //上级工号
	
	private String networkJobid;   //网点工号
	
	private String networkName;   //网点名称
	
	private String networkPhone;   //服务电话
	
	private String recvmsgPhone;    //短信接收人
	
	private String networkAddress;   //店址
	
	private String longitude;      //经度
	
	private String latitude;      //维度
	
	private Double distance;    //当前距离
	
	private String networkType;   //网点类型
	
	private String networkTypeName; //网点类型名称
	
	private String maxCount;     //最大预约数
	
	private int is4S;     //--是否是4S店,0 是4S店,1是 修理厂

	private String flag;

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandType() {
		return brandType;
	}

	public void setBrandType(String brandType) {
		this.brandType = brandType;
	}

	public String getParentNetworkJobid() {
		return parentNetworkJobid;
	}

	public void setParentNetworkJobid(String parentNetworkJobid) {
		this.parentNetworkJobid = parentNetworkJobid;
	}

	public String getNetworkJobid() {
		return networkJobid;
	}

	public void setNetworkJobid(String networkJobid) {
		this.networkJobid = networkJobid;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getNetworkPhone() {
		return networkPhone;
	}

	public void setNetworkPhone(String networkPhone) {
		this.networkPhone = networkPhone;
	}

	public String getRecvmsgPhone() {
		return recvmsgPhone;
	}

	public void setRecvmsgPhone(String recvmsgPhone) {
		this.recvmsgPhone = recvmsgPhone;
	}

	public String getNetworkAddress() {
		return networkAddress;
	}

	public void setNetworkAddress(String networkAddress) {
		this.networkAddress = networkAddress;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
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
	
	
	
	
}
