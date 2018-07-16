package com.wap.trans.entity.tr_1015;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("NETWORK_DATA")
public class ResTrans1015NetWorkEntity {
	@XStreamAlias("LONGITUDE")
	private String longitude;// 经度
	@XStreamAlias("LATITUDE")
	private String latitude;// 纬度
	@XStreamAlias("TYPE")
	private String type;// 网点类型
	@XStreamAlias("DISTANCE")
	private String distance;// 距离
	@XStreamAlias("NAME")
	private String name;// 网点名称
	@XStreamAlias("ADDRESS")
	private String address;// 网点地址
	@XStreamAlias("TELEPHONE")
	private String telephone;// 网点电话
	@XStreamAlias("WORKTIME")
	private String workTime; //网店营业时间

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
