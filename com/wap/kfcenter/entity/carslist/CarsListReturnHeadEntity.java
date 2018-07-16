package com.wap.kfcenter.entity.carslist;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 
 * 描述:车辆列表返回报文body中data
 * @author 戴元守
 * @version 1.0
 * @since 2017年9月15日 下午4:50:17
 */
public class CarsListReturnHeadEntity {
	
	@JSONField(name="userid")
    private String userid = "";
    @JSONField(name="seqno")
    private String seqno = "";
    @JSONField(name="retcode")
    private String retcode = "";
    @JSONField(name="retmsg")
    private String retmsg = "";
    
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSeqno() {
		return seqno;
	}
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	public String getRetcode() {
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	public String getRetmsg() {
		return retmsg;
	}
	public void setRetmsg(String retmsg) {
		this.retmsg = retmsg;
	}
}
