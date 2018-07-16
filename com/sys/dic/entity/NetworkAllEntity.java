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
 * 描述:网点表
 *
 * @author 赵硕
 * @version 1.0
 * @since 2018年3月27日 下午4:24:43
 */
@Entity
@Table(name = "s_dic_networkall")
public class NetworkAllEntity {
	@SequenceGenerator(name = "generator",allocationSize = 1,sequenceName = "seq_s_dic_networkall")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "id", unique = true, nullable = false, scale = 0)
	private Long id;
	
	@Column(name = "parentnetworkjobid", length = 20)
	private String parentNetworkJobid;  //上级工号
	
	@Column(name = "networkjobid", length = 20)
	private String networkJobid;   //网点工号
	
	@Column(name = "networkname", length = 100)
	private String networkName;   //网点名称
	
	@Column(name = "networkphone", length = 20)
	private String networkPhone;   //服务电话
	
	@Column(name = "recvmsgphone", length = 20)
	private String recvmsgPhone;    //短信接收人
	
	@Column(name = "networkaddress", length = 255)
	private String networkAddress;   //店址
	
	@Column(name = "longitude", length = 20)
	private String longitude;      //经度
	
	@Column(name = "latitude", length = 20)
	private String latitude;      //维度
	
	@Column(name = "maxCount", length = 11)
	private String maxCount;     //最大预约数
	
	@Column(name = "is4S")
	private int is4S;     //最大预约数

	@Column(name="flag")
	private String flag; 
	
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
