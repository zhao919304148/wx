package com.wap.trans.entity.tr_1027;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BASE_PART")
public class ResTrans1027BasePartEntity {
 
	@XStreamAlias("VALADATE_CODE")
	private String valadateCode;   //验证码
	/**操作类型<br>
	 * 01：油卡充值
	 */
	@XStreamAlias("OPERATE_TYPE")
	private String operateType;

	public String getValadateCode() {
		return valadateCode;
	}

	public void setValadateCode(String valadateCode) {
		this.valadateCode = valadateCode;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	
}
