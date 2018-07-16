package com.wap.trans.entity.tr_1013;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("FILE_DATA")
public class ResTrans1013FileDataEntity {
	@XStreamAlias("ID")
	private String id;// 文件主键(唯一标识)
	@XStreamAlias("FILENAME")
	private String filename;// 文件名称
	@XStreamAlias("PHOTOTYPE")
	private String phototype;// 照片类型
	@XStreamAlias("AUDITFLAG")
	private String auditflag;// 审核标识

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

	public String getAuditflag() {
		return auditflag;
	}

	public void setAuditflag(String auditflag) {
		this.auditflag = auditflag;
	}

}
