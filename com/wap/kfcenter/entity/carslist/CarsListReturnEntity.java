package com.wap.kfcenter.entity.carslist;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 描述:车辆列表返回报文
 * @author 戴元守
 * @version 1.0
 * @since 2017年9月15日 下午4:50:17
 */
public class CarsListReturnEntity {
	
	@JSONField(name="head")
	private CarsListReturnHeadEntity head;
	@JSONField(name="body")
	private CarsListReturnBodyEntity body;
	
	public CarsListReturnHeadEntity getHead() {
		return head;
	}
	public void setHead(CarsListReturnHeadEntity head) {
		this.head = head;
	}
	public CarsListReturnBodyEntity getBody() {
		return body;
	}
	public void setBody(CarsListReturnBodyEntity body) {
		this.body = body;
	}

}
