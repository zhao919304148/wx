package com.wap.trans.entity.tr_1056;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("responseBodyVo")
public class ResTrans1056BodyEntity {
	
	//分机号
	@XStreamAlias("extensionno")
	private String extensionno;

	public String getExtensionno() {
		return extensionno;
	}

	public void setExtensionno(String extensionno) {
		this.extensionno = extensionno;
	}

	
}
