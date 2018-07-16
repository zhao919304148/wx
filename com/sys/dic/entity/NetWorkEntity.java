package com.sys.dic.entity;

import java.util.Date;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * 
		
		* 描述:网点表
		*
		* @author 李朝晖
		* @version 1.0
		* @since 2016年12月5日 下午4:14:58
 */
@Entity
@Table(name = "s_dic_network")
public class NetWorkEntity {
	
	@SequenceGenerator(name = "generator",allocationSize = 1,sequenceName = "seq_s_dic_network")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "id", unique = true, nullable = false, scale = 0)
	private Long id;
	
	@Column(name="brandId",length = 11)
	private String brandId;   //品牌id
	
	@Column(name="majorBrandId",length = 11)
	private String majorBrandId; //专修品牌id
	
	@Column(name = "parentNetworkJobid", length = 20)
	private String parentNetworkJobid;  //上级工号
	
	@Column(name = "networkJobid", length = 20)
	private String networkJobid;   //网点工号
	
	@Column(name = "networkName", length = 100)
	private String networkName;   //网点名称
	
	@Column(name = "networkPhone", length = 20)
	private String networkPhone;   //服务电话
	
	@Column(name = "recvmsgPhone", length = 20)
	private String recvmsgPhone;    //短信接收人
	
	@Column(name = "networkAddress", length = 255)
	private String networkAddress;   //店址
	
	@Column(name = "longitude", length = 20)
	private String longitude;      //经度
	
	@Column(name = "latitude", length = 20)
	private String latitude;      //维度
	
	@Column(name = "distance", length = 11)
	private Double distance;    //当前距离
	
	@Column(name = "networkType", length = 11)
	private String networkType;   //网点类型
	
	@Column(name = "maxCount", length = 11)
	private String maxCount;     //最大预约数
	
	@Column(name = "is4S")
	private int is4S;     //最大预约数

	@Column(name="flag")
	private String flag; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getIs4S() {
		return is4S;
	}

	public void setIs4S(int is4s) {
		is4S = is4s;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
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

	public String getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(String maxCount) {
		this.maxCount = maxCount;
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

	public String getMajorBrandId() {
		return majorBrandId;
	}

	public void setMajorBrandId(String majorBrandId) {
		this.majorBrandId = majorBrandId;
	}

	public String getParentNetworkJobid() {
		return parentNetworkJobid;
	}

	public void setParentNetworkJobid(String parentNetworkJobid) {
		this.parentNetworkJobid = parentNetworkJobid;
	}

	
}
