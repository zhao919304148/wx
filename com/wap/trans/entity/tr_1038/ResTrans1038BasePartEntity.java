package com.wap.trans.entity.tr_1038;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BASE_PART")
public class ResTrans1038BasePartEntity {

	@XStreamAlias("PAGENO")
	private int pageNo;   
	
	@XStreamAlias("PAGESIZE")
	private int pageSize;
	
	@XStreamAlias("PAGECOUNT")
	private int pageCount;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	
}
