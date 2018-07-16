package com.wap.trans.entity.tr_1054;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("responseBodyVo")
public class ResTrans1054BodyEntity {
	
	//分机号
	@XStreamAlias("EXTENSIONNO")
	private String extensionno;

	public String getExtensionno() {
		return extensionno;
	}

	public void setExtensionno(String extensionno) {
		this.extensionno = extensionno;
	}
	
}
