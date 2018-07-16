package com.wap.trans.entity.tr_1005;

public class ReqTrans1005FileEntity {
	private String id;// 主键ID
	private String filename;// 文件名
	private String base64code;// BASE64编码

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getBase64code() {
		return base64code;
	}

	public void setBase64code(String base64code) {
		this.base64code = base64code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
