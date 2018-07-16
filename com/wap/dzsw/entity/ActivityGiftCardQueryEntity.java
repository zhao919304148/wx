package com.wap.dzsw.entity;

public class ActivityGiftCardQueryEntity {

	private Integer pageno;
	private Integer pagesize=10;
	private String  openid;
	public Integer getPageno() {
		return pageno;
	}
	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}
	public Integer getPagesize() {
		return pagesize;
	}
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	@Override
	public String toString() {
		return "ActivityGiftCardQueryEntity [pageno=" + pageno + ", pagesize="
				+ pagesize + ", openid=" + openid + "]";
	}
	
}
