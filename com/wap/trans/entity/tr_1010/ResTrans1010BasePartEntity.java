package com.wap.trans.entity.tr_1010;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BASE_PART")
public class ResTrans1010BasePartEntity {
	@XStreamAlias("PRPLDEFLOSSMAINID")
	private String prpldeflossmainid;// 定损任务主键ID
	@XStreamAlias("SUMPRICE")
	private String sumprice;// 总价格
	@XStreamAlias("PAGENO")
	private String pageno;// 页码
	@XStreamAlias("PAGESIZE")
	private String pagesize;// 每页数据条数
	@XStreamAlias("PAGECOUNT")
	private String pagecount;// 总页数

	public String getPrpldeflossmainid() {
		return prpldeflossmainid;
	}

	public void setPrpldeflossmainid(String prpldeflossmainid) {
		this.prpldeflossmainid = prpldeflossmainid;
	}

	public String getSumprice() {
		return sumprice;
	}

	public void setSumprice(String sumprice) {
		this.sumprice = sumprice;
	}

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

}
