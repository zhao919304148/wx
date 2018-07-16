package com.wap.kfcenter.entity.carslist;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 描述:车辆列表返回报文head
 * @author 戴元守
 * @version 1.0
 * @since 2017年9月15日 下午4:50:17
 */
public class CarsListReturnBodyEntity {
	
	@JSONField(name="username")
	private String username = "";
	@JSONField(name="data")
	private List<CarsListReturnDataEntity> dataList;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<CarsListReturnDataEntity> getDataList() {
		return dataList;
	}
	public void setDataList(List<CarsListReturnDataEntity> dataList) {
		this.dataList = dataList;
	}
	
}
