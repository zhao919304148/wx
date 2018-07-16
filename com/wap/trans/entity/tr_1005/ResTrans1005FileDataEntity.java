package com.wap.trans.entity.tr_1005;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("FILE_DATA")
public class ResTrans1005FileDataEntity {
	@XStreamAlias("ID")
	private String id;// 主键ID
	@XStreamAlias("FILENAME")
	private String filename;// 文件名
	@XStreamAlias("RESULTFLAG")
	private String resultflag;// 保存结果 0：保存失败 1：保存成功

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getResultflag() {
		return resultflag;
	}

	public void setResultflag(String resultflag) {
		this.resultflag = resultflag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
