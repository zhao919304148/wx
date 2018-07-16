package com.wap.trans.entity.tr_1031;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1031BodyBasePart {
	@XStreamAlias("PAGENO")
	private String pageno; // 页码
	@XStreamAlias("PAGESIZE")
	private String pagesize; // 每页数据条数
	@XStreamAlias("PAGECOUNT")
	private String pagecount; // 总页数
	@XStreamAlias("TOTALCOUNT")
	private String totalcount; // 总页数

	public String getPageno() {
		return pageno;
	}

	public void setPageno(String pageno) {
		this.pageno = pageno;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public String getPagecount() {
		return pagecount;
	}

	public void setPagecount(String pagecount) {
		this.pagecount = pagecount;
	}

	public String getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(String totalcount) {
		this.totalcount = totalcount;
	}
}
