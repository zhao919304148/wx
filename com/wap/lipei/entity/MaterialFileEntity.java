package com.wap.lipei.entity;

/**
 * 
 * 
 * 描述:索赔材料图片实体类
 * 
 * @author 朱久满
 * @version 1.0
 * @since 2015年12月30日 下午3:20:11
 */
public class MaterialFileEntity {
	private String media_id;// 图片缓存ID
	private String id;// 图片主键ID
	private String phototype;// 照片类型
	private String filename;// 文件名称
	private String auditflag;// 审核标识

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAuditflag() {
		return auditflag;
	}

	public void setAuditflag(String auditflag) {
		this.auditflag = auditflag;
	}

}
