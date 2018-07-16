package com.wap.lipei.entity;

import java.util.List;

/**
 * 
 * 
 * 描述:封装定损明细清单页面数据bean
 * 
 * @author 朱久满
 * @version 1.0
 * @since 2015年12月30日 上午10:08:51
 */
public class DeflossDetailPageEntity {
	private String prpldeflossmainid;// 定损任务主键ID
	private String sumprice;// 总价格
	private String pageno;// 页码
	private String pagesize;// 每页数据条数
	private String pagecount;// 总页数
	private List<DeflossDetailEntity> deflossDetailList;// 清单列表

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

	public List<DeflossDetailEntity> getDeflossDetailList() {
		return deflossDetailList;
	}

	public void setDeflossDetailList(List<DeflossDetailEntity> deflossDetailList) {
		this.deflossDetailList = deflossDetailList;
	}

}
