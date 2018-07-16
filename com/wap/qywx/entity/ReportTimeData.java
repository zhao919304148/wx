package com.wap.qywx.entity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class ReportTimeData {
	
	@JSONField(name="updatetime")
    private String updatetime = "";
    //保单特别约定
    @JSONField(name="reportdata")
    private List<TimeInfo> reportdata ;
    
    public String getUpdatetime() {
        return updatetime;
    }
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
    public List<TimeInfo> getReportdata() {
        return reportdata;
    }
    public void setReportdata(List<TimeInfo> reportdata) {
        this.reportdata = reportdata;
    } 

}
