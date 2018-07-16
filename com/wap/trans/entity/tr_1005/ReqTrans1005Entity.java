package com.wap.trans.entity.tr_1005;

import java.util.List;

public class ReqTrans1005Entity {
	private String orderno;// 订单号
	private String usercode;// 用户代码
	private String comcode;// 机构代码
	private String iscarhurt;// 是否有伤
	private List<ReqTrans1005FileEntity> reqTrans1005FileEntityList;// 文件列表

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public List<ReqTrans1005FileEntity> getReqTrans1005FileEntityList() {
		return reqTrans1005FileEntityList;
	}

	public void setReqTrans1005FileEntityList(
			List<ReqTrans1005FileEntity> reqTrans1005FileEntityList) {
		this.reqTrans1005FileEntityList = reqTrans1005FileEntityList;
	}

	public String getComcode() {
		return comcode;
	}

	public void setComcode(String comcode) {
		this.comcode = comcode;
	}

	public String getIscarhurt() {
		return iscarhurt;
	}

	public void setIscarhurt(String iscarhurt) {
		this.iscarhurt = iscarhurt;
	}

}
