package com.wap.trans.entity.tr_1040;

/**
 * 
		
		* 描述:
		*	洗车网点查询
		* @author 骆利锋
		* @version 1.0
		* @since 2017年7月10日 下午2:25:00
 */
public class ReqTrans1040Entity {
	/**查询类型**/
	private String queryType;
	/**查询内容**/
	private String queryContent;
	/**经度**/
	private String longitude;
	/**纬度**/
	private String latitude;
	/**网点类型**/
	private String networkType;
	
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getQueryContent() {
		return queryContent;
	}
	public void setQueryContent(String queryContent) {
		this.queryContent = queryContent;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
}
