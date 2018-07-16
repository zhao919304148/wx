package com.wap.dzsw.entity;

public class CarPhotoEntity {
	private String id;// 主键ID
	private String fileName;// 文件名(含扩展名)
	private String base64Code;// BASE64码

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBase64Code() {
		return base64Code;
	}

	public void setBase64Code(String base64Code) {
		this.base64Code = base64Code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
