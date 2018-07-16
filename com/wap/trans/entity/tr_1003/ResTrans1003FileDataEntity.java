package com.wap.trans.entity.tr_1003;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("FILE_DATA")
public class ResTrans1003FileDataEntity {
	@XStreamAlias("ID")
	private String id;// 主键ID
	@XStreamAlias("FILENAME")
	private String filename;// 文件名

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

}
