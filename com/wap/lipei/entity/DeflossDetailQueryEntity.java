package com.wap.lipei.entity;

/**
 * 
 * 
 * 描述:定损明细清单查询条件实体类
 * 
 * @author 朱久满
 * @version 1.0
 * @since 2015年12月30日 上午10:17:40
 */
public class DeflossDetailQueryEntity {
	private String prpldeflossmainid;// 定损任务主键ID
	private String pageno;// 页码
	private String pagesize;// 每页数据条数

	public String getPrpldeflossmainid() {
		return prpldeflossmainid;
	}

	public void setPrpldeflossmainid(String prpldeflossmainid) {
		this.prpldeflossmainid = prpldeflossmainid;
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

}
