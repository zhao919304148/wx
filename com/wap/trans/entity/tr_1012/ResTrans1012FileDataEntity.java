package com.wap.trans.entity.tr_1012;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("FILE_DATA")
public class ResTrans1012FileDataEntity {
	@XStreamAlias("FILENAME")
	private String filename;// 文件名称
	@XStreamAlias("ID")
	private String id;// 文件主键
	@XStreamAlias("PHOTOTYPE")
	private String phototype;// 照片类型

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhototype() {
		return phototype;
	}

	public void setPhototype(String phototype) {
		this.phototype = phototype;
	}

}
