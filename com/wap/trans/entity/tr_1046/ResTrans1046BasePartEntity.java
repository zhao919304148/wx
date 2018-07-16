package com.wap.trans.entity.tr_1046;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1046BasePartEntity {
	
	@XStreamAlias("OPENID")
	private String openid;
	
	@XStreamAlias("PAGENO")
	private Integer pageno;
	
	@XStreamAlias("PAGESIZE")
	private Integer pagesize;
	
	@XStreamAlias("PAGECOUNT")
	private Integer pagecount;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
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

	public Integer getPagecount() {
		return pagecount;
	}

	public void setPagecount(Integer pagecount) {
		this.pagecount = pagecount;
	}
	
}
